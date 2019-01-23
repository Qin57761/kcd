package com.controller.duoying;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
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
import com.service1.duoying.bankService;
import com.service1.duoying.mgcarService;
import com.service1.duoying.mgcertService;
import com.service1.duoying.yldsService;
import com.service1.duoying.ylqyService;
import com.util.creditutil;
import com.util.duoying.syncutil;

@Controller
public class hk4_7Controller {
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
 /*
 * 4.7	����ӿ�
 *�ӿ�˵��:֪ͨ��ӯһ�������¼
 */
	@RequestMapping(value="hk.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String hk(){
		 JSONObject data=new JSONObject();
	     JSONObject obj=new JSONObject();
	     String s = null;
		String res="";
		List<mgcert> ml=mgcertservice.Apijkxxmgcert();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<net.sf.json.JSONObject> repaymentInfo=new ArrayList<net.sf.json.JSONObject>();
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
			  net.sf.json.JSONObject  RepaymentRecord=new net.sf.json.JSONObject();	
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
				  bank b=bankservice.bankmap1(d.getBank_id());
				  JSONObject bank=new JSONObject();
				  if(b!=null){
				  bank.put("bankName",b.getName());
				  bank.put("bankBranchName",b.getName());
				  bank.put("accountNo",y.getACCOUNT_NO());
				  bank.put("remark","������");								  
			     }
				  RepaymentRecord.put("repaymentBank",bank);//��������
				  RepaymentRecord.put("loanBaseId",m.getGems_code()+"|mgcert");//�����Ϣ�ⲿΨһID  , gems_code
				  RepaymentRecord.put("remark","�����¼");//��ע
				  repaymentInfo.add(RepaymentRecord);				 
		       }		  
         	  }
		   }				  
		 }		
        if(null!=repaymentInfo&&repaymentInfo.size()>0){
	    	int pointsDataLimit =200;//��������
	    	Integer size = repaymentInfo.size();
	    	//�ж��Ƿ��б�Ҫ����
	    	if(pointsDataLimit<size){
	    	int part = size/pointsDataLimit;//������
	    	System.out.println("���� �� "+size+"������"+" ��Ϊ ��"+part+"��");
	    	//
	    	for (int i = 0; i < part; i++) {	    		
	    	//100��
	        List<net.sf.json.JSONObject> listPage = repaymentInfo.subList(0,pointsDataLimit);
			 data.put("repaymentInfo",listPage);
	    	 obj=syncutil.createHead(data);
	    	 System.out.println("data��"+data.toString());
	    	 obj.put("data",data);
	    	 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/doRepayment", obj.toString());
			 res=res+s.toString();
			 repaymentInfo.subList(0,pointsDataLimit).clear();  
	        // return data.toString();		
	      }
	    if(!repaymentInfo.isEmpty()){
			 data.put("repaymentInfo",repaymentInfo);
	    	 obj=syncutil.createHead(data);
	    	 System.out.println("data��"+data.toString());
	    	 obj.put("data",data);
	    	 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/doRepayment", obj.toString());
			 res=res+s.toString();
	    }
	   }else{
			 data.put("repaymentInfo",repaymentInfo);
	    	 obj=syncutil.createHead(data);
	    	 System.out.println("data��"+data.toString());
	    	 obj.put("data",data);
	    	 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/doRepayment", obj.toString());
			 res=s.toString();
	   }
	   }else{
	     System.out.println("û������!!!");
	   }
		
     return res;

	}
	
	public static void main(String[] args) {
		
		
		System.out.println(40000*0.01*6);
	}
}
