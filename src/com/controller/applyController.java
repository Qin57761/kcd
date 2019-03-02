package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.model.apply;
import com.model.apply_mx;
import com.model.authorize;
import com.model.newAdd.pdfdownload;
import com.model1.admin;
import com.service.applyService;
import com.service.apply_mxService;
import com.service.authorizeService;
import com.service.companyService;
import com.service.gsrykhService;
import com.service.mdxxService;
import com.service.newAdd.PDFdownloadService;
import com.service1.adminService;
import com.util.Download;
import com.util.PDFUtil;
import com.util.Page;
import com.util.creditutil;
import com.util.jsonutil;
import com.util.md5util;
import com.util.pathutil;
import com.util.pdfreader;

@Controller
public class applyController {
	    @Autowired
		private authorizeService authorizeservice;
	    @Autowired
		private applyService applyservice;
	    @Autowired
		private companyService companyservice;
	    @Autowired
	    private mdxxService mdxxservice;
	    @Autowired
	    private gsrykhService khgsservice;
	    @Autowired
	    private adminService adminservice;
	    @Autowired
	    private PDFdownloadService PDFdownloadService;
	  //json ������
	    jsonutil ju=new jsonutil();
	    
	    @RequestMapping(value="/download.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	    @ResponseBody
	    public String download(HttpServletRequest request,
	    		HttpServletResponse response,
	    		String fileName	      		
 	    		){
	    	Map am= applyservice.fapplybyname(fileName);
	    	String path=creditutil.timetofile(am.get("addtime").toString());
	  		try {
				Download.downloadFile(request,response,path,fileName);				    	
	    	} catch (Exception e) {
				e.printStackTrace();
			}
	    	return fileName;
	    }
	    
	    @RequestMapping(value="/saveapply.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	    @ResponseBody
	    public String saveapply(HttpServletRequest request,
	    		@RequestParam("apply") MultipartFile apply,
	    		@RequestParam("authorize") MultipartFile authorize,
	    		@RequestParam("acode") String acode
	    		){
			String addtime=creditutil.time().toString();
	    	apply a=new apply();
	    	Map result=new HashMap();
	    	String path;//·��
	    	String url="http://apitest.kcway.net/image/upload/apply/"+creditutil.timefile().toString();
	    	String md5str;//�����MD5�����ļ�
	    	MultipartFile[] files={apply,authorize};
	    	for(int i=0;i<files.length;i++){	    		
	    		  //ͨ��MultipartFile �����ȡ�ļ���ԭ�ļ��� 
				 String fileName =files[i].getOriginalFilename();			 
				 //��ȡ�ļ��ĺ�׺��
				  int i1 = fileName.lastIndexOf(".");
				  UUID randomUUID = UUID.randomUUID();
				  String uuidName = randomUUID.toString().replaceAll("-","");
				  md5str=md5util.getMD5(uuidName,"UTF-8")+fileName.substring(i1);
				 //��ȡ��������·����ַ�����ϴ��ļ��ı����ַ��
//				 String realPath = 
//						 //"C:/Users/tutu/workspace/Xinwen/WebContent/images/";
//						 request.getSession().getServletContext().getRealPath("/image/upload");
				 //��·��ת��Ϊ�ļ��� �� �ж��ļ����Ƿ����
				 String timepath="/opt/javaimg/image/upload/apply"+pathutil.getPath().toString()+creditutil.timefile().toString();
				 File dir = new File(timepath);				 
				 if(!dir.exists()){
				 dir.mkdirs();
				 }
				 //��ȡһ���ļ��ı���·��				
				 String pathtype=pathutil.getPath();				
			      path = timepath+pathtype+md5str;				 				
				 try {
					 File spath= new File(path);
					 spath.setWritable(true,false);
		    		 files[i].transferTo(spath);
		    		 }catch (IOException e) {
		    			e.printStackTrace();  
		    		 }
				// System.out.println(i+"----------"+md5str);
				  if(i==0){
					  //System.out.println("000000000000000000");
				      a.setApplyname(md5str);
				      a.setApplyurl(url+"/"+md5str);
				        }	
				  if(i==1){
					  //System.out.println("111111111111111111");
					  a.setAuthorizename(md5str); 
					  a.setAuthorizeurl(url+"/"+md5str);
				        }
	    	}	    	
	    	   // String url="http://apitest.kcway.net/image/upload/apply/"+creditutil.timefile().toString();
	    	    a.setAcode(acode);
		        a.setAddtime(addtime);
		        a.setAid(1);
		    	applyservice.addapply(a);	
		    	
	    	return jsonutil.toJSONString(a);
	    }
	    @Autowired
	    private apply_mxService apply_mxservice;
	    
	    //��ȡ�հ���Ȩ���������//http://apitest.kcway.net/image/upload/apply/20171206/e5d6c8f39d034a3ea3e4ae090d89f4af.pdf
	    @RequestMapping(value="/getid.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	    @ResponseBody
	    public String getid(HttpServletRequest request,
	    		String ckey,
	    		String num,
	    		String sign,
	    		String time
	    		){
			List<apply> aplist=new ArrayList();
			List<authorize> aulist=new ArrayList();	
			int n=Integer.parseInt(num);
		//	Map cm= companyservice.ckymap(ckey);
			Map mdxx=khgsservice.fgsrykh(ckey);
			List<Map<String,Object>> aplist1=new ArrayList<Map<String,Object>>();
			Map result=new HashMap();
			Map result1=new HashMap();
			if(mdxx!=null&&!mdxx.equals("")&&mdxx.size()>0){
				aplist=applyservice.fapply(1,0,n);
				int count=applyservice.fapplylenth(1);
						System.out.println(aplist.size()+"------"+num);
		        if(aplist.size()>0&&count>n){		    	
		    		 for(apply a : aplist){
		    			 Map m=new HashMap();
		    			 m.put("applyurl",a.getApplyurl());
				    	 m.put("authorizeurl",a.getAuthorizeurl());
				    	 //m.put("applyname",a.getApplyname());
				    	 // m.put("authorizename",a.getAuthorizename());
				    	 m.put("acode",a.getAcode());
				    	 aplist1.add(m);
				    	 apply apply=new apply();
				    	 apply.setAid(2);
				    	 apply.setApply_id(a.getApply_id());
				    	 applyservice.upapply(apply);				    	
		    		 }
		    		 apply_mx apply_mx=new apply_mx();
			    	 apply_mx.setAddtime(creditutil.time());
			    	 apply_mx.setApplynum(num);
			    	 apply_mx.setGsryname(mdxx.get("khrname").toString());
			    	 apply_mx.setKhgsname(mdxx.get("khgsname").toString());
			    	 apply_mx.setRyckey(ckey);
			    	 apply_mx.setUptime(creditutil.time());
			    	 apply_mxservice.addapply_mx(apply_mx);			    	
//			    	 result1.put("applyurl",s.getApplyurl());
//			    	 result1.put("authorizeurl",s.getAuthorizeurl());
//			    	 result1.put("acode",s.getAcode());			    	 
			    	 result.put("errcode","1");
			    	 result.put("errmsg","�ɹ�");
			    	 result.put("result",aplist1);
		    		     
		     }else{
				result.put("errcode","15");
				result.put("errmsg","û�п��õ����������Ȩ��");
				//result.put("result",errorutil.error015());
		    }			
			}else{				
				result.put("errcode","2");
				result.put("errmsg","�û���֤ʧ��");
				//result.put("result",errorutil.error02());
			}
	    	return jsonutil.toJSONString(result).substring(1,jsonutil.toJSONString(result).length()-1);
	    }
	    
	    @RequestMapping(value="/getapply.do",produces = "text/html;charset=UTF-8")
	    @ResponseBody
	    public String getapply(HttpServletRequest request,
	    		String ckey,
	    		String num
	    		){
			List<apply> aplist=new ArrayList();
			List<authorize> aulist=new ArrayList();	
		//	Map cm= companyservice.ckymap(ckey);
			Map mdxx=khgsservice.fgsrykh(ckey);
			List<Map<String,Object>> aplist1=new ArrayList<Map<String,Object>>();
			Map result=new HashMap();
			Map result1=new HashMap();
			if(!ckey.equals("")&&!num.equals("")){
				int n=Integer.parseInt(num);			
			if(mdxx!=null&&!mdxx.equals("")&&mdxx.size()>0){
				aplist=applyservice.fapply(1,0,n);
				int count=applyservice.fapplylenth(1);
				//System.out.println(aplist.size()+"------"+n);
		        if(aplist.size()>0&&count>n){
		        	pdfdownload pdfdownload=new pdfdownload();		
		        	String code="";
		    		 for(apply a : aplist){
		    			 Map m=new HashMap();
		    			 m.put("applyurl",a.getApplyurl());
				    	 m.put("authorizeurl",a.getAuthorizeurl());
				    	 //m.put("applyname",a.getApplyname());
				    	 // m.put("authorizename",a.getAuthorizename());
				    	 m.put("acode",a.getAcode());
				    	 aplist1.add(m);
				    	 apply apply=new apply();
				    	 apply.setAid(2);
				    	 apply.setApply_id(a.getApply_id());
				    	 applyservice.upapply(apply);	
				    	 code +=a.getAcode()+",";
		    		 }			    		 
		    		 pdfdownload.setPDFcode(code);
		    		 if(mdxx.get("khgsname")!=null&&!mdxx.get("khgsname").equals("")){
		    			 pdfdownload.setDownloadCompany(mdxx.get("khgsname").toString());
		    		 }
		    		 if(mdxx.get("khgsname")!=null&&!mdxx.get("khgsname").equals("")){
		    			 pdfdownload.setDownloadCzr(mdxx.get("khrname").toString());
		    		 }
		    		 pdfdownload.setDownloadNum(n);
		    		 pdfdownload.setStatus(0);
		    		 pdfdownload.setDownloadTime(creditutil.time());
		    		 pdfdownload.setUpdateTime(creditutil.time());
		    		 //pdfdownload.setPDFurl("");
		    		 PDFdownloadService.addPDFdownload(pdfdownload);
//			    	 result1.put("applyurl",s.getApplyurl());
//			    	 result1.put("authorizeurl",s.getAuthorizeurl());
//			    	 result1.put("acode",s.getAcode());			    	 
			    	 result.put("errcode","1");
			    	 result.put("errmsg","�ɹ�");
			    	 result.put("result",aplist1);		    		     
		     }else{
				result.put("errcode","15");
				result.put("errmsg","û�п��õ����������Ȩ��");
				//result.put("result",errorutil.error015());
		    }			
			}else{				
				result.put("errcode","2");
				result.put("errmsg","�û���֤ʧ��");
				//result.put("result",errorutil.error02());
			}
			}else{				
				result.put("errcode","3");
				result.put("errmsg","�ύ�ֶβ�����");
				//result.put("result",errorutil.error02());
			}
	    	return jsonutil.toJSONString(result).substring(1,jsonutil.toJSONString(result).length()-1);	
	    }
	    
	    //ѡ���ļ��󣬱�š������������ı�����  ,
	    @RequestMapping(value="/showCode.do",produces = "multipart/form-data;charset=UTF-8")	
	    @ResponseBody
	    public String showCode(MultipartFile file,
	    	HttpServletRequest request){
	    	PDFUtil pu = new PDFUtil();
	    	String f = file.getOriginalFilename();
	    	System.out.println("�ļ���"+f);
	    	String realPath ="/opt/javaimg/image/upload";
	    	File dir = new File(realPath+pathutil.getPath()+"apply"+pathutil.getPath()+creditutil.timefile());				
	    	System.out.println(dir);
	    	 if(!dir.exists()){
	    		dir.mkdirs();
			 } 
	    	UUID randomUUID = UUID.randomUUID();
			String uuidName =creditutil.timefile()+randomUUID.toString().replaceAll("-","").substring(0,8);	
	    	String fnpath=realPath+pathutil.getPath()+"apply"+pathutil.getPath()+creditutil.timefile()+pathutil.getPath()+uuidName+".pdf";
	    		try {
	  				File spath=new File(fnpath);
		   			spath.setWritable(true,false);	    		
					file.transferTo(spath);
	    		} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	  
	    		Map result=new HashMap<>();
	    	    Map m =new HashMap<>();
	    	    Map m1 =new HashMap<>();
	    	    List<Map>  ml=new ArrayList<Map>();
			try {
				m = pdfreader.readFdfbyPageandAll(fnpath);
				List<String> ls=pdfreader.forcode(m);
				result.put("count",ls.size()/2);
				result.put("uuidName",uuidName);
				 for(int i=0;i<ls.size();i++){
					 Map cm =new HashMap<>();
					 cm.put("code",ls.get(i)); 
					 ml.add(cm);
				 }  
				result.put("cl",ml);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return jsonutil.toJSONObject(result).toString();
	    }

		//�����������Ȩ���ļ�
	    @RequestMapping(value="/scapply.do",produces = "multipart/form-data;charset=UTF-8")	    
	    public String scapply(
	    		HttpServletRequest request,
	    		String apply_address,
	    		String pn,
	    		String aname
	    		){
	    	String realPath ="/opt/javaimg/image/upload";
	    	String ctime=pn.substring(0,8);
	    	String n1Path=realPath+"/apply/"+ctime+"/"+pn+".pdf";
//	    	System.err.println("apply_address:"+apply_address);//������ ����
//	    	System.err.println("pn:"+pn);//PDF����
//	    	System.err.println("n1Path:"+n1Path);//pdf��ַ
	       int gemsid=0;	      
	    	Map m = null;
	    	if(apply_address!=null&&!apply_address.equals("")){
			try {
			m = pdfreader.readFdfbyPageandAll(n1Path);
			List<String> ls=pdfreader.forcode(m);			
			for(int i=1;i<=ls.size();i=i+2){
				String code=ls.get(i);
				List<apply> al=applyservice.findbyacodeandtype(code,Integer.parseInt(apply_address));
				if(al.size()<=0){
				//int s=0;
				//int e=0;
				UUID randomUUID = UUID.randomUUID();
				String uuidName =creditutil.timefile()+randomUUID.toString().replaceAll("-","").substring(0,10);	
				String npath=realPath+"/apply/"+creditutil.timefile()+"/"+uuidName+".pdf";				
				pdfreader.partitionPdfFile(n1Path,npath,i,i+1);
				apply a=new apply();
				a.setAddtime(creditutil.time());//���ʱ��
				a.setAcode(code);//����
				a.setApply_address(Integer.parseInt(apply_address));//������ 1�䵱�� 2�쳵��
				a.setAid(1);//1 δʹ�� 2 ��ʹ��
				a.setAdmin_name(aname);
				a.setUptime(creditutil.time());//����ʱ��
				a.setApplyname(uuidName+".pdf");//����������
				String url="http://apitest.kcway.net/image/upload/apply/"+ctime+"/"+uuidName+".pdf";
				a.setApplyurl(url);//������
				a.setAuthorizename(uuidName+".pdf");//��Ȩ������
				a.setAuthorizeurl(url);//
				applyservice.addapply(a);
				}
			}	
			request.setAttribute("succode","��ӳɹ�");			
			} catch (Exception e) {
			request.setAttribute("succode","���ʧ��");
			e.printStackTrace();
			}
	    	}else{
	    	request.setAttribute("succode","�����̲���Ϊ��");	
	    	}
	    	return "addapply";
	    }

	    @RequestMapping(value="/applylist.do",produces = "multipart/form-data;charset=UTF-8")	    
	    public String applylist(HttpServletRequest request){	
         	  String size=request.getParameter("size");
	    	  String pagenow=request.getParameter("pagenow");
	    	  int s;
	    	  if(size!=null){
	    		  s=Integer.parseInt(size);
	    	  }else{
	    		  s=10;
	    	  }	    	 
	    	  int totalCount;
	    	  List<apply> alist=new ArrayList<>();
	    	  Page page;
	    	  totalCount =applyservice.alllenth();
	            //System.out.println("������"+totalCount+"��ǰҳ����"+pageNow);
				if (pagenow!=null) {				
					//System.out.println(0);
					page = new Page(totalCount, Integer.parseInt(pagenow),s);
					alist=this.applyservice.allapply(page.getStartPos(),  page.getPageSize());									
				} else {
					//System.out.println(1);
					page = new Page(totalCount, 1,s);					
					alist=this.applyservice.allapply(page.getStartPos(),  page.getPageSize());				   
				}				
				int q=totalCount%s;
		    	int w=0;
		    	if(q==0){
		    		w=totalCount/s;
		    		
		    	}else{
		    		w=totalCount/s+1;
		    	}    		    
		    	request.setAttribute("w",w);
		    	request.setAttribute("pagenow",pagenow);
		    	request.setAttribute("size",s);
				request.setAttribute("totalCount",totalCount);
		    	request.setAttribute("alist", alist);	    		    	
	    	return "addapplyrecord";
          }
	    //��ѯ�쳵���ļ�  
	    @RequestMapping(value="/KCDapply.do",produces = "multipart/form-data;charset=UTF-8")	    
	    public String KCDapply(HttpServletRequest request){
	    	  //�� url_fenlie == 20=>�쳵���ļ�      21=>�䵱���ļ�
	    	  String fl = request.getParameter("fl");
	    	  //int url_fenlei = Integer.parseInt(fl.substring(0, fl.indexOf('?')));
	    	  int url_fenlei = Integer.parseInt(fl.replace("?v=4.0", ""));
	    	  //��ҳ
	    	  String size=request.getParameter("size");
	    	  String pagenow=request.getParameter("pagenow");
	    	  int s;
	    	  if(size!=null){
	    		  s=Integer.parseInt(size);
	    	  }else{
	    		  s=10;
	    	  }	    	 
	    	  int totalCount;
	    	  List<apply> alist=new ArrayList<apply>();
	    	  Page page;
	    	  totalCount =applyservice.KCDCounts(url_fenlei);
	            //System.out.println("������"+totalCount+"��ǰҳ����"+pageNow);
				if (pagenow!=null) {				
					//System.out.println(0);
					page = new Page(totalCount, Integer.parseInt(pagenow),s);
					alist=this.applyservice.KCDapply(page.getStartPos(),page.getPageSize(),url_fenlei);					
				} else {
					System.out.println(1);
					page = new Page(totalCount, 1,s);					
					alist=this.applyservice.KCDapply(page.getStartPos(), page.getPageSize(),url_fenlei);			   
					pagenow="1";
				}				
				int q=totalCount%s;
		    	int w=0;
		    	if(q==0){
		    		w=totalCount/s;
		    	}else{ 
		    		w=totalCount/s+1;
		    	}    		    
		    	System.out.println(w+"----"+pagenow);
		    	request.setAttribute("fl",url_fenlei);
		    	request.setAttribute("w",w);
		    	request.setAttribute("pagenow",pagenow);
		    	request.setAttribute("size",s);
				request.setAttribute("totalCount",totalCount);
		    	request.setAttribute("alist", alist);	    		    	
	    	return "addapplyrecord";
        }
}
