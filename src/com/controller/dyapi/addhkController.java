package com.controller.dyapi;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.http.duoying.syncjkrxxHttp;
import com.model1.bank;
import com.model1.mgcert;
import com.model1.ylds;
import com.model1.ylqy;
import com.model1.zjf.zjfhistory;
import com.service1.duoying.bankService;
import com.service1.duoying.mgcarService;
import com.service1.duoying.mgcertService;
import com.service1.duoying.yldsService;
import com.service1.duoying.ylqyService;
import com.service1.zjf.zjfhistoryService;
import com.util.creditutil;
import com.util.duoying.syncutil;

@Controller
public class addhkController {

	
	@Autowired
	private mgcertService mgcertservice;
	
	@Autowired
	private mgcarService mgcarservice;
	
	@Autowired
	private ylqyService ylqyservice;
	
	@Autowired
	private bankService bankservice;
	
	@Autowired
	private yldsService yldsservice;
	
	@Autowired
	private zjfhistoryService zjfhistoryservice;
	/**
	 * 3.7����ӿ�
     *�ӿ�˵��:֪ͨ��ӯһ�������¼
	 */
	@RequestMapping(value="kjs_hk.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	private String kjs_hk(
			String kjs_type,
			String id
			){
		JSONObject data=new JSONObject();
	    JSONObject obj=new JSONObject();
	    String s = null;
		String res="";
		List<mgcert> ml=new ArrayList<mgcert>();
		    if(kjs_type.equals("8")){
			   ml=mgcertservice.carlist(7);
			}
	        if(kjs_type.equals("9")){
	    	   ml=mgcertservice.certlist(7);	
			}
	        if(kjs_type.equals("10")){
	    	   ml=mgcertservice.newcarlist(7);
	        }	
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<JSONObject> repaymentInfo=new ArrayList<JSONObject>();
		for(mgcert m : ml){
			 String s1=null;
			 String s2=null;
			 java.util.Calendar c1=java.util.Calendar.getInstance();
			 java.util.Calendar c2=java.util.Calendar.getInstance();			
		  //System.out.println("ΨһID:"+m.getGems_code());
		  //net.sf.json.JSONObject  RepaymentRecord=new net.sf.json.JSONObject();		
		  ylqy y= ylqyservice.ylqymap(m.getC_name());
		  //String  ACCOUNT_NO = null;
		  if(y!=null){
		  List<ylds>  dl= yldsservice.findyldsbyid(y.getACCOUNT_NO());
		  if(dl!=null&&dl.size()>0){
		  for(int i=0;i<dl.size();i++){
			  ylds d=dl.get(i);
			  JSONObject  RepaymentRecord=new JSONObject();	
			  if(d.getYy_dt()!=null){
					RepaymentRecord.put("repaymentDate",creditutil.datatotime1(d.getYy_dt()));//ʵ�ʻ���ʱ��	 	 
			    	  
			  }
                 RepaymentRecord.put("earlyRepay","0");//�Ƿ���ǰ����
			     DecimalFormat bl2= new DecimalFormat("######0.00");  
			     if(m.getC_mgprice_result()!=null&&!m.getC_mgprice_result().equals("")){
					    int qs=m.getC_mgdays()/30;
					    //0 ��Ϣ�� 1 �ȶϢ
				        int a = m.getC_mgtype();
				        double bj=Double.parseDouble(m.getC_mgprice_result())*10000;
				        double lx=0;
				        double qdlx=0;
				        double xj=(double)d.getAMOUNT()/100;
			    	
			    if(a==1){			    	
			    	lx=bj*0.0068; 
			    	qdlx=xj-lx-bj/qs;
			    	RepaymentRecord.put("repaymentPrincipal",bl2.format(bj/qs));//ʵ�ʻ����
			       // xj=lx+qdlx+bj/qs;
			    }
			    if(a==0){			    	
			    	lx=bj*0.01;	
			    	if(i==0){
			    		qdlx=xj-bj-lx;
			    	}else{
			    		qdlx=xj-lx;
			    	}
			    RepaymentRecord.put("repaymentPrincipal","0");//ʵ�ʻ����
			    }
			      RepaymentRecord.put("repaymentInterest",bl2.format(lx));//ʵ�ʻ�����Ϣ			      
			      RepaymentRecord.put("repaymentCost",bl2.format(qdlx));//ʵ�ʻ������\
			      RepaymentRecord.put("repaymentTotal",bl2.format(xj));//ʵ�ʻ���С��  			      
			     }
				  bank b=bankservice.bankmap(y.getBANK_CODE());
				  JSONObject bank=new JSONObject();
				  if(b!=null){
				  bank.put("bankName",b.getName());
				  bank.put("bankBranchName",b.getName());
				  bank.put("accountNo",y.getACCOUNT_NO());
				  bank.put("remark","������");								  
			     }
				  RepaymentRecord.put("repaymentBank",bank);//��������
				  if(kjs_type.equals("8")){
					  if(m.getSpcount()!=null&&!m.getSpcount().equals("")){
						  RepaymentRecord.put("loanBaseId",m.getGems_code()+"|mgcar|"+m.getSpcount());//�����Ϣ�ⲿΨһID  , gems_code				  					    						    
					  }else{
						  RepaymentRecord.put("loanBaseId",m.getGems_code()+"|mgcar|0");//�����Ϣ�ⲿΨһID  , gems_code				  					    						    
							   
					  }

				  }
                  if(kjs_type.equals("9")){
					  if(m.getSpcount()!=null&&!m.getSpcount().equals("")){
						  RepaymentRecord.put("loanBaseId",m.getGems_code()+"|mgcert|"+m.getSpcount());//�����Ϣ�ⲿΨһID  , gems_code				  					    						    
					  }else{
						  RepaymentRecord.put("loanBaseId",m.getGems_code()+"|mgcert|0");//�����Ϣ�ⲿΨһID  , gems_code				  					    						    
							   
					  }
                  }
                  if(kjs_type.equals("10")){
					  if(m.getSpcount()!=null&&!m.getSpcount().equals("")){
						  RepaymentRecord.put("loanBaseId",m.getGems_code()+"|mgnewcar|"+m.getSpcount());//�����Ϣ�ⲿΨһID  , gems_code				  					    						    
					  }else{
						  RepaymentRecord.put("loanBaseId",m.getGems_code()+"|mgnewcar|0");//�����Ϣ�ⲿΨһID  , gems_code				  					    						    
							   
					  }
                  }

				  RepaymentRecord.put("remark","�����¼");//��ע
				  repaymentInfo.add(RepaymentRecord);				 
		       }		  
         	  }
		   }				  
		 }		
			 data.put("repaymentInfo",repaymentInfo);
	    	 obj=syncutil.createHead(data);
	    	 System.out.println("data��"+data.toString());
	    	 obj.put("data",data);
		        zjfhistory zh=new zjfhistory();
		        zh.setAddtime(creditutil.time());
		        zh.setUptime(creditutil.time());
		        zh.setSynpath("http://abs.51duoying.com:8082/ws/services/rest/loan/doApproval");
		        zh.setSynrecording(obj.toString());
		        zh.setCountnum(repaymentInfo.size());
		        zh.setZjfname("��ӯ����");
		        zh.setPath_type(9);
	    	 try {
		    	 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/doRepayment", obj.toString());
		         zh.setReturnmsg(s.toString());
		         zh.setSuccessnum(1);  
				 zjfhistoryservice.addsynhistory(zh);
				 JSONObject result=JSONObject.parseObject(s);
			    	if(result.getString("code").equals("200")){			    		
			    		JSONObject ret=JSONObject.parseObject(result.getString("ret"));
			            String[] successIds=ret.getString("successIds").replace(" ","").replace("[","").replace("]","").split(",");
			    	  if(successIds[0]!=null&&!successIds[0].equals("")){
			    		 // System.out.println("��ӯ�����ѯ(�ɹ�����)");
			    		 for(int i=0;i<successIds.length;i++){
			    			 mgcert c=new mgcert();
			    			 if(kjs_type.equals("8")){
				    			 mgcert mc=mgcertservice.findcar(successIds[i].substring(1,successIds[i].indexOf("|")));		    			 				    				
				    				 c.setZjf_type("8");
				    				 //c.setSpcount(0);
				    				 c.setId(mc.getId());
				    				 mgcertservice.upmgcar(c);
				    			//System.out.println("��˳ɹ�����id:"+successIds[i].substring(1,successIds[i].indexOf("|")));
			    			 }
			    			 if(kjs_type.equals("9")){
				    			 mgcert mc=mgcertservice.findcert(successIds[i].substring(1,successIds[i].indexOf("|")));		    			 				    			 
				    				 c.setZjf_type("8");
				    				 //c.setSpcount(0);
				    				 c.setId(mc.getId());
				    				 mgcertservice.upmgcert(c);
			    			 }
			    			 if(kjs_type.equals("10")){
				    			 mgcert mc=mgcertservice.findnewcar(successIds[i].substring(1,successIds[i].indexOf("|")));		    			 				    							    				 
				    				 c.setZjf_type("8");
				    				 //c.setSpcount(0);
				    				 c.setId(mc.getId());
				    				 mgcertservice.upmgnewcar(c);
			    			 }	
			    			 
			    		 }
			    	  }
			    		 }
				 return s;
			} catch (Exception e) {
				JSONObject result=new JSONObject();
				 result.put("code","201");
				 result.put("ret", "");
				 result.put("msg", "ͬ��ʧ��-"+e);
			     zh.setReturnmsg(result.toString());
			     zjfhistoryservice.addsynhistory(zh); 
			     return result.toString();
			}


		
   

	}
	
}
