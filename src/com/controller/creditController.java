package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.model.img;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.model.company;
import com.model.credit;
import com.model.history;
import com.model.pdfover;
import com.service.bbService;
import com.service.companyService;
import com.service.creditService;
import com.service.customerService;
import com.service.grywlsService;
import com.service.historyService;
import com.service.imgService;
import com.service.mdxxService;
import com.service.pdfoverService;
import com.service.ywService;
import com.util.Base64Test;
import com.util.creditutil;
import com.util.errorutil;
import com.util.jsonutil;
import com.util.pathutil;

@Controller
public class creditController {
	    @Autowired
	    private creditService creditService;
	    @Autowired
	    private historyService hts;
	    @Autowired
	    private pdfoverService pfs;
	    @Autowired
	    private grywlsService grs;
	    @Autowired
	    private imgService imgservice;
	    @Autowired
	    private companyService cps;
	    @Autowired
	    private customerService cs;
	    @Autowired
	    private ywService ywservice;
	    @Autowired
	    private bbService bbservice;
	    @Autowired
	    private mdxxService mdxxservice;
	    //json ������
	    jsonutil ju=new jsonutil();
	    
	    Base64Test basetest=new Base64Test();
	    //��ȡpdf�ļ� ���浽 ��kcd_pdfover ��
	    
	    
	   
	    

	    
	    //ɾ������ ����id ����
	    @RequestMapping(value="/del.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	    @ResponseBody
	    public String del(HttpServletRequest request){
	    	
	    	
	    	creditService.delcredit(1);
	    	
	    	
			return "ɾ���ɹ���";
	    	
	    	
	    	
	    	
	    }
	    
	    
	    //����  ��ѯ��������
	    @RequestMapping(value="/push.do",produces = "text/html;charset=UTF-8")
	    @ResponseBody
	    public String push(HttpServletRequest request,String orderNo,
	    	String pdfurl,String addtime)
	    {
	      String retCode;
	      String  retMsg;
	      Map result=new HashMap();
           if(pdfurl!=null&&!pdfurl.equals("")){        	   
        	   retCode="1";
        	   retMsg="�ɹ�";        	
           }else{
        	   retCode="2";
        	   retMsg="ʧ��";        	         	           	   
           }	
               result.put("retCode",retCode);
    	       result.put("retMsg",retMsg);
	           return jsonutil.toJSONString(result) ;
	    }
	    
	    
	    
	    //���� �༭
	    @RequestMapping(value="/update.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	    @ResponseBody
	    public String update(
	    		//@RequestParam("front") MultipartFile front,
	    		//@RequestParam("opposite") MultipartFile opposite,
	    		//@RequestParam("apply") MultipartFile apply,
	    		//@RequestParam("authorize") MultipartFile authorize,
	    		//@RequestParam("hz")MultipartFile hz,
	    		 @RequestParam("id")int id,
	    		 @RequestParam("name")String name,
	    		//@RequestParam("IDcard_num") String IDcard_num,
	    		//@RequestParam("phone_num") String phone_num,
	    		//@RequestParam("authorize_num") String authorize_num,
	    		//@RequestParam("sum_bit") String sum_bit,
	    		HttpServletRequest request) {	    		    		    	
	    	    credit credit=new credit();
	    	    credit.setId(id);
	    	    credit.setName(name);
	    	    String time=creditutil.time();
		    	credit.setAdd_time(time);
	    	    HashMap cm=new HashMap();	    	   
//	    	    cm.put("id", id);
//	    	    cm.put("name", name);
//	    	    cm.put("time", time);
	    	    creditService.upcredit(credit);
			
	    	    return cm.toString() ;
			

		}
	    //��� ��ѯ  sum_bit Ϊ 1 Ϊ�ύ��ѯ Ϊ 0 ��ݸ�
	    @RequestMapping(value="/add.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	    @ResponseBody
	    public String add(
	    		@RequestParam("front") MultipartFile front,
	    		@RequestParam("opposite") MultipartFile opposite,
	    		@RequestParam("apply") MultipartFile apply,
	    		@RequestParam("authorize") MultipartFile authorize,
	    		@RequestParam("hz") MultipartFile hz,
	    		@RequestParam("name") String name,
	    		@RequestParam("IDcard_num") String IDcard_num,
	    		@RequestParam("phone_num") String phone_num,
	    		@RequestParam("authorize_num") String authorize_num,
	    		@RequestParam("sum_bit") String sum_bit,
	    		@RequestParam("company") String company,
	    		@RequestParam("username") String username,
	    		@RequestParam("url")String url,
	    		@RequestParam("ly") String ly,
	    		HttpServletRequest request){	    	
	    	    credit credit=new credit();
	    	    credit credit1=new credit();
	    	    Map ddmap=new HashMap();
	    	    Map result=new HashMap();
	    	    if(!company.equals("")&&!username.equals("")){
	    	    	// credit creditlist= creditService.findcreditbyname(name, IDcard_num, phone_num);
	    	    	 //MultipartHttpServletRequest mtpreq = (MultipartHttpServletRequest) request;
	    	 		 //ͨ�� mtpreq ��ȡ�ļ����е��ļ�
	    	 		// MultipartFile front = mtpreq.getFile("front");//������
	    	 		// MultipartFile opposite = mtpreq.getFile("opposite");//������
	    	 		// MultipartFile apply = mtpreq.getFile("apply");//������
	    	 		// MultipartFile authorize = mtpreq.getFile("authorize");//��Ȩ��
	    	 		// MultipartFile hz = mtpreq.getFile("hz");//��Ӱ
	                  String f1 = null,o1 = null,a1 = null,a2 = null,h1 = null;
	                  String type=null;// �ļ�����
	    	    	 if(!front.equals("")&&front!=null
	    	    			 &&!opposite.equals("")&&opposite!=null
	    	    			 &&!apply.equals("")&&apply!=null
	    	    			 &&!authorize.equals("")&&authorize!=null
	    	    			 &&!hz.equals("")&&hz!=null
	    	    			 &&!name.equals("")
	    	    			 &&!IDcard_num.equals("")
	    	    			 &&!phone_num.equals("")
	    	    			 &&!authorize_num.equals("")
	    	    			 &&!url.equals("")
	    	    			 ){
	    	    		 //System.out.println(front+"file----------------------");
	    	 	    	 MultipartFile[] files={front,opposite,apply,authorize,hz};
	    	 	    	 String realPath =request.getSession().getServletContext().getRealPath("/image/upload");
	    	 				 //"C:/Users/tutu/workspace/Xinwen/WebContent/images/";
	    	 	    	 //��·��ת��Ϊ�ļ��� �� �ж��ļ����Ƿ����
	    				 File dir = new File(realPath);
	    				 if(!dir.exists()){
	    				 dir.mkdirs();
	    				 }
	    	 	   		 for(int is=0;is<files.length;is++){	 	   			 	 	   			 	 	   			
	    	 	    	//ͨ��MultipartFile �����ȡ�ļ���ԭ�ļ��� 
	    	 	       	 String fileName = files[is].getOriginalFilename();
	    	 	       	 
	    	 	       	 UUID randomUUID = UUID.randomUUID();
	    	 	   		 //��ȡ�ļ��ĺ�׺��
	    	 	   		    int i = fileName.lastIndexOf(".");
	    	 	   		    if(i==-1){
	    	 	   		     result.put("errorcode","03");
		    	    	     result.put("errormsg","�ύʧ��");
		    	    	     result.put("result",errorutil.error03());		    	    	     
		    	    	     return jsonutil.toJSONString(result);
	    	 	   		    }

	    	 	   		    type=fileName.substring(i);	    	 	   			 	   			 
	    	 	   			String uuidName = randomUUID.toString().replaceAll("-","")+type;
	    	 	   		     pathutil pu=new pathutil();
	    				     String pathtype=pathutil.getPath();	    				
	    		 	   		//��ȡһ���ļ��ı���·��
	    		 	   		 String path = realPath+pathtype+uuidName;
	    		 	   		 // Ϊ�ļ���������п���һ���µĿռ�,*û������
	    		 	   		 // File newFile = new File(path); 		
	    		 	   		 //System.err.println("-----��������·����ַΪ��:"+realPath);
	    		 	   		 //System.err.println("-----ͼƬ����Ϊ��:"+fileName);
	    		 	   		// System.err.println("-----ͼƬ������Ϊ��:"+uuidName);
	    		 	   		if(is==0){
	    		 	   			f1=uuidName;
	    		 	   		}else if (is==1) {
	    		 	   			o1=uuidName;
	    		 			} else if (is==2) {
	    		 				a1=uuidName;
	    		 			} else if (is==3) {
	    		 				a2=uuidName;
	    		 			} else if (is==4) {
	    		 				h1=uuidName;
	    		 			} 	   		   			    		    		    
	    		 	   		// System.err.println("-----ͼƬ��·��Ϊ��:"+path);
	    		 	   		 try {
	    		 	   		File spath=new File(path);
	    		 	   			spath.setWritable(true,false);
	    			    		
	    		 	   			files[is].transferTo(spath);
	    		 	   	    		 } catch (IllegalStateException e) {
	    		 	   	    		
	    		 	   	    			 
	    		 	   	    		 e.printStackTrace();
	    		 	   	    		 } catch (IOException e) {
	    		 	   	    	 
	    		 	   	    		 result.put("errorcode","05");
	    		    	    	     result.put("errormsg","�ύʧ��");
	    		    	    	     result.put("result",errorutil.error05());		    	    	     
	    		    	    	     return jsonutil.toJSONString(result);
	    		 	   	    		 }
	    		 	    		
	    		 	    	}	 	   		
	    	 	   	    credit.setName(name);
	    		    	credit.setIDcard_num(IDcard_num);
	    		    	credit.setPhone_num(phone_num);
	    		    	credit.setAuthorize_num(authorize_num);	
	    		    	credit.setFront(f1);
	    		    	credit.setAuthorize(a2);
	    		    	credit.setApply(a1);
	    		    	credit.setHz(h1);
	    		    	credit.setOpposite(o1);
	    		    	credit.setUrl(url);
	    		    	credit.setShzt("4");
	    		    	String time=creditutil.time();
	    		    	credit.setAdd_time(time);
	    		    	//��ʷ��¼
	    		    	history hty=new history();
	    		    	if(!ly.equals("")){
	    		    	  hty.setLy(ly);
	    		    	}	    		    			    	
	    		    	hty.setUid(String.valueOf(credit.getId()));
	    		    	hty.setZt(sum_bit);
//	    		    	grywls gr=new grywls();
//	    		    	gr.setCxyw("��������ϵͳ");
//	    		    	gr.setCzr("������");
//	    		    	gr.setSxkd("100");		    			    			    	
//	    		    	grs.addgrywls(gr);//��Ӹ�����ˮ
                        credit.setSum_bit(sum_bit);		    		    		
	    		    	company c=new company();
	    		    	c.setCompany(company);
	    		        Map cpsm=cps.findcompany(company);
	    		        String beans= (String) cpsm.get("beans");
	    		        int kd=Integer.parseInt(beans)-100;
	    		         if(kd>0){
	    		        	 try {
	   	    		             c.setBeans(String.valueOf(kd));	    		       
	   	    		    	     creditService.save(credit);//��Ӳ�ѯ��Ϣ		    	    		             
	   	    	 	   			 hts.hsava(hty);//��Ӳ�ѯ��¼
	   	    	 	   		     pdfover pf=new pdfover();
	   	    	 	   		     pf.setUid(String.valueOf(credit.getId()));
	   	    	 	   			 pfs.addpdf(pf);
	   	    	 	   			 if(credit.getSum_bit().equals("1")){
	   	    	 	   				cps.upcompany(c);	 	   				 
	   	    	 	   			 }	    	 	   			 	    	 	   		 
	   	    	 	   		     ddmap.put("dd",credit.getId());
	   		    	    	     ddmap.put("ddbs", "�ύ��ѯ");
	   		    	    	     if(!ly.equals("")&&ly!=null){
	   		    	    	    	 ddmap.put("ly",ly); 
	   		    	    	     }
	   		    	    	    
	   		    	    	     result.put("errcode","01");
	   		    	    	     result.put("errmsg","�ύ�ɹ�");
	   		    	    	     credit1.setId(credit.getId());
	   		    	    	     credit1.setSum_bit("4");
	   		    	    	     creditService.upsubmit(credit1);
	   		    	    	     result.put("result", ddmap);		    	    	     
	   		    	    	     return jsonutil.toJSONString(result);
	    		        	
	    		        	 } catch (Exception e) {
	    		        		 result.put("errcode","05");
	   		    	    	     result.put("errmsg","�ύʧ��");
	   		    	    	     result.put("result",errorutil.error05());		    	    	     
	   		    	    	     return jsonutil.toJSONString(result);
								}
	    		         }else{
	    		        	 
	    		        	 result.put("errcode","04");
		    	    	     result.put("errmsg","�ύʧ��");
		    	    	     result.put("result",errorutil.error04());		    	    	     
		    	    	     return jsonutil.toJSONString(result);	    	 
	    		        	 
	    		         }
	    		     
	    	    	 }else{	    	    		 	    	    		     
		    	    	     result.put("errcode","03");
		    	    	     result.put("errmsg","�ύʧ��");
		    	    	     result.put("result",errorutil.error03());		    	    	     
		    	    	     return jsonutil.toJSONString(result);	    	    		 
	    	    	 }

	    	    }else {
   	    	     result.put("errcode","02");
   	    	     result.put("errmsg","�ύʧ��");
   	    	     result.put("result",errorutil.error02());   	    	     
   	    	     return jsonutil.toJSONString(result);
				}
				
	    	    }
	    
	    
	    
	    
	   
	    
	    //��� ��ѯ  sum_bit Ϊ 1 Ϊ�ύ��ѯ Ϊ 0 ��ݸ�
	    @RequestMapping(value="/tutobase.do",produces = "text/html;charset=UTF-8")
	    //@ResponseBody
	    public String tutobase(@RequestParam("tu1") String tu1,
	    		@RequestParam("tu2") String tu2,
	    		@RequestParam("tu3") String tu3,
	    		@RequestParam("tu4") String tu4,
	    		@RequestParam("tu5") String tu5,
	    		HttpServletRequest request){
	    	Base64Test base=new Base64Test();
	    	System.out.println(tu1);
	       String t1=Base64Test.GetImageStr(tu1);
	       String t2=Base64Test.GetImageStr(tu2);
	       String t3=Base64Test.GetImageStr(tu3);
	       String t4=Base64Test.GetImageStr(tu4);
	       String t5=Base64Test.GetImageStr(tu5);	  	 
	      // String realPath =request.getSession().getServletContext().getRealPath("/image/upload");
	      // realPath=realPath.replace("\\","/").toString();
	       //System.out.println(realPath);
	       String path="/opt/javaimg/image/upload/img/"+creditutil.timefile();
	       File file=new File(path);
	       if(!file.exists()){
	    	   file.mkdirs();
	       }
	       String imgName = null;
	     
	    	   UUID randomUUID = UUID.randomUUID();
	  	       imgName=randomUUID.toString().replace("-","")+creditutil.timefile()+".jpg";
	  	     Base64Test.decodeBase64ToImage(t1,path.toString(),imgName);
	      
	  
	      
	      
	       
	    	
	    	
	    	
	    	
	    	return "image/upload/img/"+creditutil.timefile()+"/"+imgName;
	    	
	    	
	    	
	    	
	    	
	    }    
	    
	    
	    
	    //��� ��ѯ  sum_bit Ϊ 1 Ϊ�ύ��ѯ Ϊ 0 ��ݸ�
	    @RequestMapping(value="/tobase64.do",produces = "text/html;charset=UTF-8")
	    @ResponseBody
	    public String tobase64(@RequestParam("fronttobase") String fronttobase,
	    		@RequestParam("oppositetobase") String oppositetobase,
	    		@RequestParam("applytobase") String applytobase,
	    		@RequestParam("authorizetobase") String authorizetobase,
	    		@RequestParam("hztobase") String hztobase,
	    		HttpServletRequest request){
	    	Base64Test base=new Base64Test();
	    	  img imgmodel=new img();
	    	  Map result=new HashMap();
	    	  String realPath =request.getSession().getServletContext().getRealPath("/image/upload");		      
	    	  if(realPath!=null&&fronttobase!=null&&
		    		   oppositetobase!=null&&
		    		   applytobase!=null&&
		    		   authorizetobase!=null&& 
		    		   hztobase!=null){
	    	   String frontname=Base64Test.GenerateImage(fronttobase, realPath);
		       String oppositename=Base64Test.GenerateImage(oppositetobase, realPath);
		       String applyname=Base64Test.GenerateImage(applytobase, realPath);
		       String authorizename=Base64Test.GenerateImage(authorizetobase, realPath);
		       String hzname=Base64Test.GenerateImage(hztobase, realPath);	      
		       
		       imgmodel.setAddtime(creditutil.time().toString());
		       imgmodel.setFrontimg(frontname);
		       imgmodel.setOppositeimg(oppositename);
		       imgmodel.setApplyimg(applyname);
		       imgmodel.setAuthorizeimg(authorizename);
		       imgmodel.setHzimg(hzname);
		       imgmodel.setHttppath("http://localhost:8080/kcd/image/upload");
		       imgmodel.setImgpath(realPath);
		       imgservice.addimg(imgmodel);
		     
		       result.put("errcode", "01");
		       result.put("errmsg", "�ύ�ɹ�");
		       result.put("result",imgmodel);
	    	  }
			   return jsonutil.toJSONString(result);	      			    	   				
	    	    }
	    }
	  
