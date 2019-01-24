package com.controller.user;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.http.phonecall;
import com.model.gskh;
import com.model.gsrykh;
import com.model.txl;
import com.service.gskhService;
import com.service.gsrykhService;
import com.service.mdxxService;
import com.service.txlService;
import com.util.creditutil;

@Controller
public class pcallController {
     
	
	@Autowired
    private mdxxService mdxxservice;
	
	@Autowired
    private gsrykhService khgsservice;
	
	@Autowired 
	private gskhService gskhservice;
	
	@Autowired 
	private txlService txlservice;
	
	//��ȡH5ҳ����Ϣ
	@RequestMapping(value="/toh5html.do",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    @ResponseBody
	public String toh5tml(HttpServletRequest request,
			String callback_url,
			String success_url,
			String failed_url,
			String name,
			String cert_no,
			String contact_list,
			String show_nav_bar,
			String phone_no,
			String ckey
			){
		phonecall phonecall=new phonecall();		
		String result = null;
		 Map mkey= khgsservice.fgsrykh(ckey);		
		if(mkey!=null&&!mkey.equals("")&&mkey.size()>0){
			 Map gm=gskhservice.fgsbyid(Integer.parseInt(mkey.get("gsid").toString()));
			 int kd=Integer.parseInt(gm.get("kd").toString());
		  if(kd>10){			  		  
		try {			
			result=com.http.phonecall.geth5(callback_url,success_url,failed_url,name,cert_no,contact_list, show_nav_bar, phone_no);
	        gskh gskh=new gskh();
	        gskh.setId(Integer.parseInt(gm.get("id").toString()));
	        gskh.setKd(kd-10);
	        gskhservice.upgskhkd(gskh);
	        txl txl=new txl();
	        txl.setContact_list(contact_list);
	        txl.setCxlx("ͨѶ¼H5ҳ���ѯ");
	        txl.setIdcard(cert_no);
	        txl.setKhid(Integer.parseInt(mkey.get("khid").toString()));
		    txl.setEtime1(creditutil.time());
		    txl.setEtime2(creditutil.time());
		    txl.setName(name);
		    txl.setPhonenum(phone_no);
		    txl.setStime(creditutil.time());
		    txlservice.addtxl(txl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  }else{
				 result="{\"errcode\":\"03\",\"errormsg\":\"����\"}";					
			}
		}else{
			 result="{\"errcode\":\"02\",\"errormsg\":\"�û���֤ʧ��\"}";			
		}
			
		return result;					
	    }
	
	
	//1����ȡ��Ӫ����֤��
	@RequestMapping(value="/getcapcha.do",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    @ResponseBody
	public String getcapcha(HttpServletRequest request,
			String phone_no,
			String name ,
			String cert_no ,
			String contact_list,
			String ckey
			){
		phonecall phonecall=new phonecall();
		
		 String result = null;
		 Map mkey=  khgsservice.fgsrykh(ckey);
		
		 if(mkey!=null&&!mkey.equals("")&&mkey.size()>0){	
			 Map gm=gskhservice.fgsbyid(Integer.parseInt(mkey.get("gsid").toString()));
			 int kd=Integer.parseInt(gm.get("kd").toString());
			 if(kd>10){				 			
		try {
			
			result=com.http.phonecall.getCapcha(phone_no, name, cert_no, contact_list);
		        gskh gskh=new gskh();
	            gskh.setId(Integer.parseInt(gm.get("id").toString()));
	            gskh.setKd(kd-10);
	            gskhservice.upgskhkd(gskh);
			    txl txl=new txl();
		        txl.setContact_list(contact_list);
		        txl.setCxlx("ͨѶ¼API��ѯ");
		        txl.setIdcard(cert_no);
		        txl.setKhid(Integer.parseInt(mkey.get("khid").toString()));
			    txl.setEtime1(creditutil.time());
			    txl.setEtime2(creditutil.time());
			    txl.setName(name);
			    txl.setPhonenum(phone_no);
			    txl.setStime(creditutil.time());
			    txlservice.addtxl(txl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			 }else{
				 result="{\"errcode\":\"03\",\"errormsg\":\"����\"}";
					
				
			}
			}else{
				 result="{\"errcode\":\"02\",\"errormsg\":\"�û���֤ʧ��\"}";
					
				
			}
		return result;					
	}
	
	//2��Ӫ��������Ȩ��½ 
	@RequestMapping(value="/authorizelogin.do",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    @ResponseBody
	public String authorizelogin(HttpServletRequest request,
			String phone_no,
			String passwd ,
			String capcha ,
			String callback ,
			String ckey
			){
		phonecall phonecall=new phonecall();
		
		String result = null;
		Map mkey= khgsservice.fgsrykh(ckey);
		
		 if(mkey!=null&&!mkey.equals("")&&mkey.size()>0){	
			 Map gm=gskhservice.fgsbyid(Integer.parseInt(mkey.get("gsid").toString()));
			 int kd=Integer.parseInt(gm.get("kd").toString());
			 if(kd>10){				 			
		try {
			
			result=com.http.phonecall.login(phone_no, passwd, capcha, callback);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 }else{
			 result="{\"errcode\":\"03\",\"errormsg\":\"����\"}";
			 
		 }	
		 }else{
			 result="{\"errcode\":\"02\",\"errormsg\":\"�û���֤ʧ��\"}";
				 
		 }
		return result;					
	}
	//3����ȡ�굥ͼƬ��֤�� 
	  @RequestMapping(value="/toimage.do",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	  @ResponseBody
	  public String toimage(HttpServletRequest request,
			     String phone_no,
				 String ckey ){
		
			phonecall phonecall=new phonecall();
			
			String result = null;
			Map mkey=  khgsservice.fgsrykh(ckey);
			
			 if(mkey!=null&&!mkey.equals("")&&mkey.size()>0){
				 Map gm=gskhservice.fgsbyid(Integer.parseInt(mkey.get("gsid").toString()));
				 int kd=Integer.parseInt(gm.get("kd").toString());
				 if(kd>10){
					 
				
			try {
				
				result=com.http.phonecall.image(phone_no);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
				 }else{
					 result="{\"errcode\":\"03\",\"errormsg\":\"����\"}";
				 }
			 }else{
				 result="{\"errcode\":\"02\",\"errormsg\":\"�û���֤ʧ��\"}";
					 
			 }
			return result;	
												
		}
	
	
	//4�����������֤����Ȩ 
	@RequestMapping(value="/getsmscode.do",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    @ResponseBody
	public String getsmscode(HttpServletRequest request,
			String phone_no,
			String ckey
			){
		phonecall phonecall=new phonecall();
		
		String result = null;
		Map mkey=  khgsservice.fgsrykh(ckey);
		int kd=Integer.parseInt(mkey.get("kd").toString());
		 if(mkey!=null&&!mkey.equals("")&&mkey.size()>0){
			 if(kd>10){				 			
		try {
			
			result=com.http.phonecall.getSmsCode(phone_no);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			 }else{
				 result="{\"errcode\":\"03\",\"errormsg\":\"����\"}";
			 }
		 }else{
			 result="{\"errcode\":\"02\",\"errormsg\":\"�û���֤ʧ��\"}";
				 
		 }
		return result;					
	}		
	    //5�굥������֤����֤ 
		@RequestMapping(value="/verifysmscode.do",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	    @ResponseBody
		public String verifysmscode(HttpServletRequest request,
				String phone_no, 
				String sms_code , 
				String name, 
				String cert_no , 
				String capcha ,
				String ckey
				){
			phonecall phonecall=new phonecall();
			
			String result = null;
			Map mkey= khgsservice.fgsrykh(ckey);
			
			 if(mkey!=null&&!mkey.equals("")&&mkey.size()>0){	
				 Map gm=gskhservice.fgsbyid(Integer.parseInt(mkey.get("gsid").toString()));
				 int kd=Integer.parseInt(gm.get("kd").toString());
				 if(kd>10){
					 
				
			try {
				
				result=com.http.phonecall.verifySmsCode(phone_no, sms_code, name, cert_no, capcha);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
				 }else{
					 result="{\"errcode\":\"03\",\"errormsg\":\"����\"}";	 
				 }
			 }else{
				 result="{\"errcode\":\"02\",\"errormsg\":\"�û���֤ʧ��\"}";
					 
			 }
			return result;					
		}
	  
		//���ݲ�ѯ 1������ token ��ѯ�������� 
		@RequestMapping(value="/analyzeddata.do",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	    @ResponseBody
		public String analyzeddata(HttpServletRequest request,
			 String token,
			 String ckey
				){
			phonecall phonecall=new phonecall();
			//String url="http://e.apix.cn/apixanalysis/mobile/retrieve/phone/data/query";
			String result = null;

			gsrykh khgs=new gsrykh();
			Map mkey= khgsservice.fgsrykh(ckey);

			 if(mkey!=null&&!mkey.equals("")&&mkey.size()>0){						 				
				try {
					
					result=com.http.phonecall.analyzedData(token);
					txl txl=new txl();
					txl.setEtime1(creditutil.time().toString());
					txl.setKhid(Integer.parseInt(mkey.get("khid").toString()));
					txlservice.uptime(txl);
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }else{
			 result="{\"errcode\":\"02\",\"errormsg\":\"�û���֤ʧ��\"}";
				 
		 }
	
			return result;					
		}
		//���ݲ�ѯ 2������ token ��ѯԭʼ����
		@RequestMapping(value="/querydata.do",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	    @ResponseBody
		public String querydata(HttpServletRequest request,
			 String token,
			 String ckey
				){
			phonecall phonecall=new phonecall();
			gsrykh khgs=new gsrykh();
			//String url="http://e.apix.cn/apixanalysis/mobile/retrieve/phone/data";
			String result = null;

			 Map mkey=  khgsservice.fgsrykh(ckey);			
			 if(mkey!=null&&!mkey.equals("")&&mkey.size()>0){								 				
				try {					
					result=com.http.phonecall.queryData(token);
					txl txl=new txl();
					txl.setEtime1(creditutil.time().toString());
					txl.setKhid(Integer.parseInt(mkey.get("khid").toString()));
					txlservice.uptime(txl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 }else{
					 result="{\"errcode\":\"02\",\"errormsg\":\"�û���֤ʧ��\"}"; 
				 }
	
			return result;					
		}
}
