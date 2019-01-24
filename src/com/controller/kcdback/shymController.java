package com.controller.kcdback;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.http.saassfzsbhttp;
import com.http.zxhttp;
import com.model.credit;
import com.model.gsrykh;
import com.model.history;
import com.model.img;
import com.model.pdfover;
import com.model.index.zxexcel;
import com.service.creditService;
import com.service.gskhService;
import com.service.gsrykhService;
import com.service.historyService;
import com.service.imgService;
import com.service.pdfoverService;
import com.util.creditutil;
import com.util.imgutil;
import com.util.imgutil.ImageCut;
import com.util.jsonutil;
import com.util.pathutil;
import com.util.img.filelengthutil;
import com.util.img.zxexcelutil;

import junit.framework.Assert;

@Controller
public class shymController {

	
	@Autowired
	private imgService imgservice;
	@Autowired
	private creditService creditservice;
	@Autowired
	private historyService historyservice;
	@Autowired
	private pdfoverService pdfoverservice;
	
	@Autowired
	private gskhService gskhservice;
	@Autowired
	private gsrykhService gsrykhservice;
	
	/**
	 * ���»���״̬
	 * @return
	 */
	@RequestMapping(value="/returntype.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String returntype(String return_type,String id){
		credit c=new credit();
		c.setReturn_type(Integer.parseInt(return_type));
		c.setId(Integer.parseInt(id));
		creditservice.upcredit(c);		
		//Map cm=creditservice.findcreditbyid(Integer.parseInt(id));	
        //JSONObject jb=new JSONObject();
        //jb.put("tyid",.toString());	
		String msg=String.valueOf(c.getReturn_type());
		return msg;		
	}
	
	
	/**
	 * ͼƬ��ת
	 * @param imgname
	 * @param timenum
	 * @param fr
	 * @return
	 */
	@RequestMapping(value="/imgsz.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String imgsz(String imgname,String timenum,int fr){				
		  try {
			 BufferedImage src =ImageIO.read(new File("/opt/javaimg/image/upload/img/"+timenum+"/"+imgname));
			 if(fr==1){
				 BufferedImage des=imgutil.Rotate(src,270);
				 Assert.assertNotNull(des);  
			     Assert.assertTrue(ImageIO.write(des,"jpg", new File("/opt/javaimg/image/upload/img/"+timenum+"/"+imgname)));  
			 }
			 if(fr==2){
				 BufferedImage des=imgutil.Rotate(src,90);
				 Assert.assertNotNull(des);  
			     Assert.assertTrue(ImageIO.write(des,"jpg", new File("/opt/javaimg/image/upload/img/"+timenum+"/"+imgname)));  			
			 }		
		     
			 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "s"; 		
	}
	
	/**
	 * ���֤��֤
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value="/sfzsb.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String sfzsb(String filePath){				
		//�Ѳ���ͼƬ�ŵ�D�����棬Ҳ���Էŵ���ĵط�������Ҫ����filePath��Ӧ������·��
				//String filePath="C:/Users/Administrator/Desktop/2/test-idcard.JPG";//����ͼƬ����·��
				File file=null;
				try{
					file=new File(filePath);//Ҫʶ����ļ�·��
				}catch(Exception e){
					System.out.print(">> �ļ�����ʧ�ܣ�");
					return filePath;
				}
				String result ="";
				try {
					result=saassfzsbhttp.Scan(saassfzsbhttp.file2byte(file),filePath.substring(filePath.lastIndexOf(".")+1));					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//��������ӿ�ʶ��
				System.out.print(result);//��ӡ��ʶ����
		return result; 		
	}

	
	@RequestMapping(value="/cjimg.do",produces="text/html;charset=UTF-8")
	public ModelAndView cjimg(
			String uid,
			String name,
			String timenum,String imgname,
			String x,String y,
			String w,String h,
			String imgfid
			) throws FileNotFoundException{	
		///opt/javaimg/image/upload/img/
		//F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/kcd/image/upload/img/
	 String path="/opt/javaimg/image/upload/img/"+timenum+"/"+imgname;	 
	 String mesage;
	 String imgpath;
	 if(imgname.indexOf(".new.jpg") > 0 )
	 {
		 imgpath=path;
	 }else{
		 imgpath=path+".new.jpg";
	 }	
	 try {
		 double x1 = Double.parseDouble(x);
		 double y1 = Double.parseDouble(y);
		 double w1 = Double.parseDouble(w);
		 double h1 = Double.parseDouble(h);
		ImageCut.cutImage1(path,imgpath,(int)x1,(int)y1,(int)w1,(int)h1);
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	 
	 img img=new img();
	 credit c=new credit();
	 if(imgfid.equals("1")){
	   System.out.println("1");
	   c.setFront("2");	
	 }
     if(imgfid.equals("2")){
    	 System.out.println("2");
	    c.setOpposite("2");
	 }
     if(imgfid.equals("3")){
    	 System.out.println("3");
	 c.setApply("2");
     }
     if(imgfid.equals("4")){
    	 System.out.println("4");
    	 c.setAuthorize("2");
     }
     if(imgfid.equals("5")){
    	 System.out.println("5");
	     c.setHz("2");
     }
	 c.setId(Integer.parseInt(uid));
	 creditservice.upcredit(c);
	return new ModelAndView("redirect:/tozximg.do?id="+uid+"&name="+name); 
		
	}	
	/**
	 * �ӿڻص� ״̬����
	 * @param orderNo
	 * @param errmsg
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/ybts.do",produces="text/html;charset=UTF-8")
	public void ybts(String orderNo){
		  String jsonres = null;	
		  String errmsg = null ;
		  System.out.println("ybts----------"+orderNo);
         Map cm=creditservice.findcreditbyid(Integer.parseInt(orderNo));
         if(cm.get("sum_bit").equals("4")){        	 
        	 if(cm.get("shzt").equals("1")){
        		 errmsg="�������";
        	 }else{
        		 errmsg="���ڲ�ѯ";
        	 }
         }
         if(cm.get("sum_bit").equals("5")){        	 
        		 errmsg="��ѯ�ɹ�";
         }
         if(cm.get("sum_bit").equals("4")){
        	 if(cm.get("shzt").equals("1")){
        		 errmsg="����";
        	 }
         }		
         boolean t=true;
          while(t){
			    jsonres=zxhttp.zxxxhd(orderNo,errmsg,"","","","");			
				System.out.println("�������ݣ�"+jsonres);
			    net.sf.json.JSONObject jo=jsonutil.toJSONObject(jsonres);
			    if(jo.get("status")!="1"){
			    	t=true;
			    }else{
			    	t=false;
			    }
          }

	} 
	
	@RequestMapping(value="/sss.do",produces="text/html;charset=UTF-8")
	//@ResponseBody
	public String sss(String orderNo,String errmsg,HttpServletRequest request,HttpServletResponse response){
		System.out.println("------"+orderNo);
		
		
		
		return "hello";		
	}
	@RequestMapping(value="/sss1.do",produces="text/html;charset=UTF-8")
	//@ResponseBody
	public void sss1(String orderNo,String errmsg,HttpServletRequest request,HttpServletResponse response){
	
		for(int i=0;i<10;i++){
			System.out.println(orderNo+"------"+i+1);	
			
		}	
	}
	/**
	 * ������Ϣ
	 * @param request
	 * @param name
	 * @param bc_status
	 * @param c_tag
	 * @param uid
	 * @param remark
	 * @param c_nation
	 * @param c_address
	 * @param c_card_outdate
	 * @param c_card_office
	 * @param c_card_type
	 * @param c_sex
	 * @param c_bs
	 * @param c_oldname
	 * @param c_yb
	 * @param c_card_no
	 * @param c_name
	 * @return
	 */
	@RequestMapping(value="/tjxx.do",produces="text/html;charset=UTF-8")
	public ModelAndView tjxx(HttpServletRequest request,
			String name,String bc_status,
			String c_tag,String uid,
			String remark,String c_nation,
			String c_address,String c_card_outdate,
			String c_card_office,String c_card_type,
			String c_sex,String c_bs,String c_oldname,
			String c_yb,String c_card_no,String c_name
			){
	//String bc_status=request.getParameter("bc_status");
	//String cs_tag=request.getParameter("cs_tag");
	//String uid=request.getParameter("uid");
	credit c=new credit();
	//System.out.println(bc_status);
	c.setSum_bit(bc_status);
	c.setId(Integer.parseInt(uid));
	//System.out.println(c_tag);
	c.setC_nation(c_nation);
	c.setC_address(c_address);
	c.setC_card_outdate(c_card_outdate);
	c.setC_card_office(c_card_office);
	c.setC_card_type(c_card_type);
	c.setC_sex(c_sex);
	c.setC_bs(c_bs);
	c.setC_oldname(c_oldname);
	c.setC_yb(c_yb);
	c.setName(c_name);
	c.setIDcard_num(c_card_no);
	//c.setPhone_num(phone_num);
	c.setUp_time(creditutil.time());
	c.setShzt(c_tag);
	creditservice.upcredit(c);
	history ht=new history();
	ht.setUid(uid);
	System.out.println(remark);
	if(!remark.equals("")&&remark!=null){
		ht.setLy(remark);
	}else{
		if(c_tag.equals("0")){
			ht.setLy("������");	
		}
		if(c_tag.equals("1")){
			ht.setLy("����ͨ��");	
		}
	}		
	ht.setZt(bc_status);
	ht.setHtime(creditutil.time());
	historyservice.hsava(ht);
	  String jsonres = null;	
	  String errmsg = null ;
    if(bc_status.equals("4")){        	 
   	 if(c_tag.equals("1")){
   		 errmsg="�������";
   	 }else{
   		 errmsg="���ڲ�ѯ";
   	 }
    }
    if(bc_status.equals("5")){        	 
   		 errmsg="��ѯ�ɹ�";
    }
    if(bc_status.equals("6")){
   		 errmsg="����";
    }	 
    gsrykh g=new gsrykh();
    Map cm=creditservice.findcreditbyid(Integer.parseInt(uid));
    Map gm=gsrykhservice.fkhbyid(Integer.parseInt(cm.get("mdid").toString()));
    Map pm=pdfoverservice.findbyid(uid);
    if(gm.get("api_reurl")!=null){
    	    if(errmsg.equals("��ѯ�ɹ�")){
    	        System.out.println("�������ݣ�"+uid+"---"+errmsg);	
    	        jsonres=zxhttp.zxxxhd(uid,errmsg,gm.get("api_reurl").toString(),pm.get("pdfurl").toString(),pm.get("pdfname").toString(),creditutil.time().toString());			
    	    	System.out.println("�������ݣ�"+jsonres);
    	    }else{
    	        System.out.println("�������ݣ�"+uid+"---"+errmsg);	
    	        jsonres=zxhttp.zxxxhd(uid,errmsg,gm.get("api_reurl").toString(),null,null,null);			
    	    	System.out.println("�������ݣ�"+jsonres);
    	    }
    }  	
	return new ModelAndView("redirect:/"+name);	
	}
	
	
	@RequestMapping(value="/filesize.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String filesize(String url) throws MalformedURLException{
		Map m=new HashMap<>();
		int s = 0;
		try {
			s = filelengthutil.getFileLength(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.put("fl",s);
		return jsonutil.toJSONString(m).replace("[", "").replace("]","");	
	}
	
	@RequestMapping(value="/pdffileup.do",produces="multipart/form-data;charset=UTF-8")
	@ResponseBody
	public String pdffileup(MultipartFile file,String uid){		
 	    pdfover p=new pdfover();
   	 //String realPath =request.getSession().getServletContext().getRealPath("/image/upload");
   	    System.out.println(uid+"-------");
   	    Map m=new HashMap<>();
		    String fileName = file.getOriginalFilename();
		    UUID randomUUID = UUID.randomUUID();
   		 //��ȡ�ļ��ĺ�׺��
   		    int i = fileName.lastIndexOf(".");
   		    String type=fileName.substring(i);	    	 	   			 	   			 
   			String uuidName = randomUUID.toString().replaceAll("-","")+type;
   		    pathutil pu=new pathutil();
		    String pathtype=pathutil.getPath();	    				
	   		//��ȡһ���ļ��ı���·��
	   		String path = "/opt/javaimg/image/upload/"+creditutil.timefile()+"/"+uuidName;	
	   	    //��·��ת��Ϊ�ļ��� �� �ж��ļ����Ƿ����
			 File dir = new File("/opt/javaimg/image/upload/"+creditutil.timefile());
			 if(!dir.exists()){
			 dir.mkdirs();
			 }
  			try {
  				File spath=new File(path);
	   			spath.setWritable(true,false);	    		
				file.transferTo(spath);
				p.setPdfname(uuidName);
				p.setPdfuptime(creditutil.time());
				p.setPdfurl("http://apitest.kcway.net/image/upload/"+creditutil.timefile()+"/"+uuidName);
				p.setUid(uid);
				pdfoverservice.uppdf(p);
				m.put("uid",uid);
				m.put("fl",file.getSize());
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	   	
		return jsonutil.toJSONString(m).replace("[", "").replace("]","");						
	}
	@RequestMapping(value="/hy.do",produces="multipart/form-data;charset=UTF-8")
	@ResponseBody
	public String hy(String uid,String fid){
		credit credit=new credit();
		if(fid.equals("1")){
		credit.setFront("1");//���֤������
		}
		if(fid.equals("2")){
		credit.setOpposite("1");//���֤������
		}
		if(fid.equals("3")){
		credit.setApply("1");//��������Ƭ
		}
		if(fid.equals("4")){
		credit.setAuthorize("1");//��Ȩ����Ƭ
		}
		if(fid.equals("5")){
		credit.setHz("1");//���֤ �����������Ȩ����Ƭ
		}	
		credit.setId(Integer.parseInt(uid));
		creditservice.upcredit(credit);
		String res ="1";
		return res;	
	}
	
	@RequestMapping(value="/toExcel.do",produces="multipart/form-data;charset=UTF-8")
	public void toExcel(String uid,HttpServletResponse response){		
		Map cm=creditservice.findcreditbyid(Integer.parseInt(uid));
        zxexcel c=new zxexcel();
 if(cm.get("authorize_num")!=null){//��Ȩ����
    c.setCode(cm.get("authorize_num").toString());
        }else{
    c.setCode("");
        }
if(cm.get("c_card_type")!=null){//֤������
	c.setZjlx(cm.get("c_card_type").toString());    	
        }else{
    c.setZjlx("");    
        }
if(cm.get("c_sex")!=null){//�Ա�
	c.setSex(cm.get("c_sex").toString());    	
        }else{
    c.setSex("");   	
        }
if(cm.get("name")!=null){//����
	c.setName(cm.get("name").toString());
}else{
	c.setName("");
}
if(cm.get("c_nation")!=null){//����
	c.setMz(cm.get("c_nation").toString());
}else{
	c.setMz("");
}
if(cm.get("c_address")!=null){//��ַ
	c.setTxaddress(cm.get("c_address").toString());
}else{
	c.setTxaddress("");
}
if(cm.get("IDcard_num")!=null){//���֤��
	c.setIdcard(cm.get("IDcard_num").toString());
}else{
	c.setIdcard("");
}
if(cm.get("c_card_office")!=null){//���֤��֤����
	c.setZjqfjg(cm.get("c_card_office").toString());
}else{
	c.setZjqfjg("");
}
if(cm.get("c_card_outdate")!=null){//���֤��Ч��
	c.setZjdate(cm.get("c_card_outdate").toString());
}else{
	c.setZjdate("");
}
if(cm.get("phone_num")!=null){//�ֻ���
	c.setLxdh(cm.get("phone_num").toString());
}else{
	c.setLxdh("0");
}
if(cm.get("c_bs")!=null){//��ѯ��ʽ
	c.setCxbs("0");
}else{
	c.setCxbs("0");
}
if(cm.get("c_oldname")!=null){//������
	c.setCname(cm.get("c_oldname").toString());
}else{
	c.setCname("");
}
if(cm.get("c_yb")!=null){//ͨѶ��ַ����
	c.setTxcode(cm.get("c_yb").toString());	
}else{
	c.setTxcode("");
}
List<zxexcel> zl=new ArrayList<>();
zl.add(c);
	   try {
zxexcelutil.toxxexcel(zl, response);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
