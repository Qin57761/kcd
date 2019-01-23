package com.controller.user;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.http.jynwdhttp;
import com.model.bb;
import com.model.company;
import com.model.credit;
import com.model.gskh;
import com.model.history;
import com.model.img;
import com.model.pdfover;
import com.model.yw;
import com.service.bbService;
import com.service.companyService;
import com.service.creditService;
import com.service.customerService;
import com.service.grywlsService;
import com.service.gskhService;
import com.service.gsrykhService;
import com.service.historyService;
import com.service.imgService;
import com.service.mdxxService;
import com.service.pdfoverService;
import com.service.ywService;
import com.util.Base64Test;
import com.util.creditutil;
import com.util.jsonutil;

import net.sf.json.JSONObject;

@Controller
public class nwdController {
	
    @Autowired
    private creditService creditservice;
    @Autowired
    private historyService historyservice;
    @Autowired
    private pdfoverService pdfoverservice;
    @Autowired
    private grywlsService grywlsservice;
    @Autowired
    private imgService imgservice;
    @Autowired
    private companyService companyservice;
    @Autowired
    private customerService cs;
    @Autowired
    private ywService ywservice;
    @Autowired
    private bbService bbservice;
    @Autowired
    private mdxxService mdxxservice;
    @Autowired
    private gsrykhService gsrykhservice;
    @Autowired
    private gskhService gskhservice;
    //json ������
    jsonutil ju=new jsonutil();
    
    Base64Test basetest=new Base64Test();
    //��ȡpdf�ļ� ���浽 ��kcd_pdfover ��
	
    /**
     * ���ű�����ʵʱ���ͽӿ�
     * ���APP��ѯ���ű��棬��ѯ���ʵʱ֪ͨ��������ϵͳ
     * �������ѯʧ�ܣ����Ͳ�ѯʧ��ԭ��״̬�뼰״̬����
     *����ѯʧ�ܿ��ܳ��ֵ�ԭ����Ҫ����ṩ��ϸ��״̬���б��磺����4�����������Ѿ���ѯ����ʧ��ԭ�����Ӧ��״̬�뼰��������
     *�������ѯ�ɹ������Ͳ�ѯʧ��ԭ��״̬�롢״̬���������ñ������ص�ַ���͸��������š�
     *����������ü�������API���������ű������ص�ַ��PDF��ַ�����ͣ�������Ͳ��ɹ������ش�����룬
     *��ӷ���᲻���������ͣ����ͼ������������
     *�������ź�������PDF��ַ�����ű���������ء�  	       	      
     * @param orderNo
     * @param errcode
     * @param errmsg
     * @param pdfurl
     * @param addtime
     * @param sign
     * @param time
     * @return
     */
    @RequestMapping(value="/ssts.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ssts(HttpServletRequest request,
    		int id,
    		String api
    		){	   	
    	System.out.println(api+"-----------");
    	if(api.equals("1")){
    		System.out.println("11111111111111");
    	Map cm=creditservice.findcreditbyid(id);
    	Map pm=pdfoverservice.findbyid(String.valueOf(id));
    	Map result=new HashMap<>();    	
    	result.put("orderNo",id);
    	result.put("errcode",cm.get("sum_bit"));
    	if(cm.get("sum_bit").equals("1")){
    		result.put("errmsg","�ݸ���");	
    	}
    	if(cm.get("sum_bit").equals("2")){
    		result.put("errmsg","����ģ��");	
    	}  
    	if(cm.get("sum_bit").equals("3")){
    		result.put("errmsg","�ȴ��ϴ�");	
    	}  
    	if(cm.get("sum_bit").equals("4")){
    		result.put("errmsg","���ڲ�ѯ");	
    	}  
    	if(cm.get("sum_bit").equals("5")){
    		result.put("errmsg","��ѯ���");	
    	}  
    	if(cm.get("sum_bit").equals("6")){
    		result.put("errmsg","����");	
    	}  
    	if(cm.get("sum_bit").equals("7")){
    		result.put("errmsg","�ѳ���");	
    	}
    	if(pm.get("pdfurl")!=null){
    		result.put("pdfurl",pm.get("pdfurl"));
    		result.put("addtime", pm.get("pdfuptime"));
    	}else{
    		result.put("pdfurl","");
    		result.put("addtime","");
    	}        	
    	result.put("sign","0");
    	result.put("time","0");      	
    	JSONObject jsonobject=JSONObject.fromObject(result);
    	String jsonstring=jynwdhttp.httpPost("http://test.creditplatform.jiayincredit.com/personal/kcway/receive-report",jsonobject, false);    	
    	System.out.println(jsonstring);
    	}
    	return "sss";    	  	
    }
    /**
     * 
     * @param opAccount �ύ���ϵĲ�����Ա����
     * @param opName �ύ���ϵĲ�����Ա����
     * @param cuName �ͻ�����
     * @param idCard �ͻ����֤��
     * @param phoneNo �ͻ��ֻ���
     * @param orderNo ���Ψһ�������
     * @param authNo ��Ȩ����
     * @param errcode �����Ƿ�ƥ�䣨��1���ǣ���2����
     * @param errmsg ԭ��
     * @param orderTimeyyyy-MM-dd HH:mm:ss 
     * @param sign ��ǩ����
     * @param time ʱ���
     * @return
     */
    @RequestMapping(value="/yhxxts.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public Map yhxxts(String opAccount,
    		String opName,
    		String cuName,
    		String idCard,
    		String phoneNo,//
    		String orderNo,//
    		String authNo,//
    		String errcode,//
    		String errmsg,//
    		String orderTime,//
    		String sign,//��ǩ����
    		String time//ʱ���
    		){	  
    	Map result=new HashMap<>();    	
    	result.put("opAccount", opAccount);
    	result.put("opName", opName);
    	result.put("cuName", cuName);
    	result.put("idCard", idCard);
    	result.put("phoneNo", phoneNo);
    	result.put("orderNo", orderNo);
    	result.put("authNo", authNo);
    	result.put("errcode", errcode);
    	result.put("errmsg", errmsg);   
    	result.put("orderTime", orderTime);
    	result.put("sign", sign);
    	result.put("time", time);
    	return result;    	  	
    }
    
    
    
    
    
    
    
	 //��ѯ���ű���
    @RequestMapping(value="/findorder.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findorder(HttpServletRequest request,
    		String orderNo,
    		String ckey
    		) {
    	 //String realPath =request.getSession().getServletContext().getRealPath("/image/upload");
    	 //System.out.println("----------"+realPath);
        Map result=new HashMap();
        Map result1=new HashMap();    	
    		 Map cm=gsrykhservice.fgsrykh(ckey);  		 
    		 if(cm!=null&&cm.size()>0&&!cm.equals("")){
    			 result=pdfoverservice.findbyid(orderNo); 
    			 System.out.println(result);
    			 if(result!=null){
    				 result1.put("orderNo", orderNo);
    				 result1.put("errmsg", "�ɹ�");
    				 result1.put("errcode", "01");    				 
    			 }else{
    	    		 result1.put("errmsg", "�޶�����Ϣ");
    				 result1.put("errcode", "013");
    				 //result1.put("result",errorutil.error013());
    	    	}
    			 
    		 }else{
	    		 result1.put("errmsg", "�û���֤ʧ��");
				 result1.put("errcode", "02");
				 //result1.put("result",errorutil.error02());
	    	}       	    	
				return jsonutil.toJSONString(result1).replace("[","").replace("]","");
		
    	

	}
    
   
    //��� ��ѯ  sum_bit Ϊ 1 Ϊ�ύ��ѯ Ϊ 0 ��ݸ�
    @RequestMapping(value="/toadd.do",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String toadd(@RequestParam("fronttobase") String fronttobase,
    		@RequestParam("oppositetobase") String oppositetobase,
    		@RequestParam("applytobase") String applytobase,
    		@RequestParam("authorizetobase") String authorizetobase,
    		@RequestParam("hztobase") String hztobase,
    		@RequestParam("ckey") String ckey,
    		@RequestParam("name") String name,
    		@RequestParam("IDcard_num") String IDcard_num,
    		@RequestParam("phone_num") String phone_num,
    		@RequestParam("authorize_num") String authorize_num,
    		@RequestParam("sum_bit") String sum_bit,
    		@RequestParam("ly") String ly,
    		@RequestParam("sign") String sign,
    		@RequestParam("time") String time,    		
    		HttpServletRequest request){    	  
	        Map result=new HashMap();
	        Map path=new HashMap();
	        Map file=new HashMap();
	        company ccompany=new company();
	        credit credit=new credit();
	        bb bb=new bb();
	        yw yw=new yw();
	        history h=new history();
	        pdfover p=new pdfover();
    	    img imgmodel=new img();
       Map mdxx=gsrykhservice.fgsrykh(ckey);
       if(mdxx!=null&&!mdxx.equals("")&&mdxx.size()>0){
    	  int id=Integer.parseInt(mdxx.get("gsid").toString()); 
    	  Map gskd=gskhservice.fgsbyid(id);
       if(Integer.parseInt(gskd.get("kd").toString())>0&&gskd.get("kd")!=null&&!gskd.get("kd").equals("")){
    	   int kd=Integer.parseInt(gskd.get("kd").toString());	
    	   if(kd>100){    		   
    		   //String realPath =request.getSession().getServletContext().getRealPath("/image/upload");
    		   String path1="/opt/javaimg/image/upload/img/"+creditutil.timefile();
    		 	 File dir = new File(path1);
    		 	 if(!dir.exists()){
    		 		 dir.mkdirs();
    		 	 }				
    		   if(!fronttobase.equals("")&&
    		   		  !oppositetobase.equals("")&&
    		   		  !applytobase.equals("")&&
    		   		  !authorizetobase.equals("")&&
    		   		  !hztobase.equals("")&&
    		       	  !name.equals("")&& 
    		             !IDcard_num.equals("")&& 
    		       	  !phone_num.equals("")&& 
    		       	  !authorize_num.equals("")&& 
    		             !sum_bit.equals("")
    		          ){    		 	    		   
    		       	   String add_time=creditutil.time();
    		   	       credit.setIDcard_num(IDcard_num);
    		   	       credit.setSum_bit(sum_bit);
    		   	       credit.setName(name);
    		   	       credit.setPhone_num(phone_num);
    		       	   credit.setAdd_time(add_time);
    		       	   credit.setAuthorize_num(authorize_num);
    		       	   credit.setMdid(mdxx.get("ckey").toString());
    		       	   credit.setUp_time(creditutil.time());
    		       	   credit.setShzt("4");
    		         try {
//    		       	   String frontname=basetest.GenerateImage(fronttobase, realPath.toString());
//    		   	       String oppositename=basetest.GenerateImage(oppositetobase, realPath.toString());
//    		   	       String applyname=basetest.GenerateImage(applytobase, realPath.toString());
//    		   	       String authorizename=basetest.GenerateImage(authorizetobase, realPath.toString());
//    		   	       String hzname=basetest.GenerateImage(hztobase, realPath.toString());
    		       	   UUID randomUUID1 = UUID.randomUUID();
    		       	   UUID randomUUID2 = UUID.randomUUID();
    		       	   UUID randomUUID3 = UUID.randomUUID();
    		       	   UUID randomUUID4 = UUID.randomUUID();
    		       	   UUID randomUUID5 = UUID.randomUUID();
//    		       	   fronttobase=basetest.GetImageStr(fronttobase);
//    		       	   oppositetobase=basetest.GetImageStr(oppositetobase);
//    		       	   applytobase=basetest.GetImageStr(applytobase);
//    		       	   authorizetobase=basetest.GetImageStr(authorizetobase);
//    		           hztobase=basetest.GetImageStr(hztobase);	  	 
    		       	   String  frontname =randomUUID1.toString().replace("-","")+creditutil.timefile()+".jpg";
    		       	   String  oppositename =randomUUID2.toString().replace("-","")+creditutil.timefile()+".jpg";
    		       	   String  applyname =randomUUID3.toString().replace("-","")+creditutil.timefile()+".jpg";
    		       	   String  authorizename =randomUUID4.toString().replace("-","")+creditutil.timefile()+".jpg";
    		       	   String  hzname =randomUUID5.toString().replace("-","")+creditutil.timefile()+".jpg";    		      
    		   	       Base64Test.decodeBase64ToImage(fronttobase.toString(),path1.toString(),frontname);
    		   	       Base64Test.decodeBase64ToImage(oppositetobase.toString(),path1.toString(),oppositename);
    		   	       Base64Test.decodeBase64ToImage(applytobase.toString(),path1.toString(),applyname);
    		   	       Base64Test.decodeBase64ToImage(authorizetobase.toString(),path1.toString(),authorizename);
    		   	       Base64Test.decodeBase64ToImage(hztobase.toString(),path1.toString(),hzname);    		   	    
    		   	       imgmodel.setAddtime(creditutil.time().toString());
    		   	       imgmodel.setFrontimg(frontname);
    		   	       imgmodel.setOppositeimg(oppositename);
    		   	       imgmodel.setApplyimg(applyname);
    		   	       imgmodel.setAuthorizeimg(authorizename);
    		   	       imgmodel.setHzimg(hzname);
    		   	       imgmodel.setUptime(creditutil.time());
    		         } catch (Exception e) {
    		       	//�����������
    		       	result.put("errcode","014");
    		       	result.put("errmsg","��������");    		       
    		   		return jsonutil.toJSONString(result).replace("[","").replace("]","");    		   		    		   		
    		   	} 	      	
    		                       creditservice.save(credit);
    		                       gskh gskh=new gskh();
    		                       kd=kd-100;
    		                       gskh.setKd(kd);
    		                       gskh.setId(id);
    		                       gskhservice.upgskhkd(gskh); 		                       
    		                       imgmodel.setHttppath("http://apitest.kcway.net/image/upload/img/"+creditutil.timefile().toString());
    		                       //imgmodel.setImgpath(realPath);   	   
    		   	                   h.setZt(sum_bit);      
    		   	                   if(!ly.equals("")&&ly!=null){
    		   	                	   h.setLy(ly);
    		   	                	   bb.setBb(ly);
    		   	                   }
    		     	    		   h.setUid(String.valueOf(credit.getId()));
    		     	        	   p.setUid(String.valueOf(credit.getId()));
    		     	        	   p.setPdfuptime(creditutil.time());
    		     	        	   imgmodel.setUid(credit.getId());
    		     	        	   bb.setKhxm(name);
    		     	        	   bb.setSfzbh(IDcard_num);
    		     	        	   bb.setSqsbh(authorize_num);
    		     	        	   bb.setMd(mdxx.get("khgsname").toString());
    		     	        	   bb.setOrderid(String.valueOf(credit.getId()));
    		     	        	   bb.setCxr(mdxx.get("khrname").toString());    		     	        	      		     	        	 
    		     	        	   bb.setOnecxtime(creditutil.time().toString());    		     	        	     		     	        	   
    		     	    		   imgservice.addimg(imgmodel);   		     	    		   
    		     		    	   historyservice.hsava(h);    		     		    	   
    		     		    	   pdfoverservice.addpdf(p);
    		     		    	   bbservice.addbb(bb);   		    	   
    		           		       //ywservice.saveyw(yw);    		           		       		           		          		           		  
    		           		       result.put("errcode", "01");
    		     			       result.put("errmsg", "�ύ�ɹ�");
    		     			       result.put("orderNo",credit.getId());
    		     			       return jsonutil.toJSONString(result).replace("[","").replace("]","");
    		   	           	 	        	   	        	   
    		           	   }else {
    							
    	    		    	   result.put("errcode", "03");
    	     			       result.put("errmsg", "�ύ���ֶβ�����");
    	     			       return jsonutil.toJSONString(result).replace("[","").replace("]","");   
    	    		    	   
    					}
    		       }else {
					
    		    	   result.put("errcode", "04");
     			       result.put("errmsg", "����");     			      
     			       return jsonutil.toJSONString(result).replace("[","").replace("]","");   
    		    	   
				}
    	   
    	   
    		       }
   
    }else {		
 	       result.put("errcode", "02");
	       result.put("errmsg", "�û���֤ʧ��");
	       return jsonutil.toJSONString(result).replace("[","").replace("]","");    	   
	}
	return jsonutil.toJSONString(result).replace("[","").replace("]","");

       }
   
  
    //�����Ϣʧ�ܽ���첽���ͽӿ�
    @RequestMapping(value="/errcome.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String errcome(HttpServletRequest request,
    		String orderNo,
    		String errcode,
    		String errmsg,
    		String ckey,
    		String sign,
    		String time
    		){
    	  Map result=new HashMap();
    	  if(!orderNo.equals("")&&orderNo!=null&&!ckey.equals("")&&ckey!=null
    			  &&!errcode.equals("")&&errcode!=null
    			  &&!errmsg.equals("")&&errmsg!=null
    			  ){    
    	  Map c=gsrykhservice.fgsrykh(ckey);  
    	  if(c!=null&&!c.equals("")&&c.size()>0){    	   		  
            result.put("errcode", "01");
            result.put("errmsg", "�ɹ�");
    	  }else{
    		result.put("errcode","02");
            result.put("errmsg","�û���֤ʧ��");    		  
    	  }
    	  }else{
    		result.put("errcode","03");
            result.put("errmsg","�ύ���ֶβ�����");    		  
    	  }          	
		return jsonutil.toJSONString(result).replace("[","").replace("]","");    	    	    	    	
    }
    //�����Ϣ�ɹ�����첽���ͽӿ� (���-��������)
    @RequestMapping(value="/findingofaudit.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findingofaudit(HttpServletRequest request,
    		String name,
    		String idCard,
    		String phoneNo,
    		String orderNo,
    		String errcode,
    		String errmsg
    		){
    	 Map result=new HashMap();
    	 System.out.println(name+"--"+idCard+"--"+phoneNo+"--"+orderNo);
    	 if(!name.equals("")&&name!=null
    			 &&!idCard.equals("")&&idCard!=null
    			 &&!phoneNo.equals("")&&phoneNo!=null
    			 &&!orderNo.equals("")&&orderNo!=null    			 
    			 ){
    		 result.put("errcode", "1");
             result.put("errmsg", "�ɹ�");
             result.put("orderNo",orderNo);
    	 }else{
    		 result.put("errcode","2");
             result.put("errmsg","ʧ��(�����ֶ�Ϊ��)");	 
             result.put("orderNo",orderNo);
    	 }
    	   		    	
		return jsonutil.toJSONString(result).replace("[","").replace("]","");    	    	    	    	
    }
	
}
