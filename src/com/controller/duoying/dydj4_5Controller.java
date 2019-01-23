package com.controller.duoying;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.http.duoying.syncjkrxxHttp;
import com.model1.archives;
import com.model1.mgcert;
import com.service1.duoying.archivesService;
import com.service1.duoying.mgcertService;
import com.util.creditutil;
import com.util.duoying.syncutil;

@Controller
public class dydj4_5Controller {
	@Autowired
	private mgcertService mgcertservice;
	@Autowired
	private archivesService archivesservice;
	/**
	 * 4.5	��Ѻ�Ǽ���ɽӿ�
     *�ӿ�˵��:֪ͨ��ӯ��Ѻ�Ǽ����
	 * @return
	 */
	
	@RequestMapping(value="dydj.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String dydj(){
	     List<mgcert> ml=mgcertservice.Apijkxxmgcert();
	     List cl=new ArrayList<>();
	     List<JSONObject> carList=new ArrayList<JSONObject>();
	     JSONObject data=new JSONObject();
	     JSONObject obj=new JSONObject();
	     String s = null;
	    	String res="";
	     if(ml!=null&&ml.size()>0){	      	 
	    	for(mgcert m : ml){
	    		System.out.println(ml.size());
	    	 System.out.println("sssssssssss");
	    	 JSONObject jb=new JSONObject();	
	    	 jb.put("loanBaseId",m.getGems_code()+"|mgcert");//�����Ϣ�ⲿΨһID  , gems_code
	    	 archives a=archivesservice.Apiarchives(m.getC_carno(),m.getC_vin(),"0");	    	 
	    	 if(a!=null&&!a.equals("")){	    	
	    		 System.out.println(a.getR_item1());
		    	 //jb.put("plateNumber",a.getR_item1());//��������/����
		    	 jb.put("collateralUniqueId",a.getR_item6());//����ʶ�����
		    	 jb.put("mortgageSign",a.getR2_item5());//��Ѻ���
		    	 if(a.getR2_item6()!=null&&!a.getR2_item6().equals("")){
		    	 jb.put("mortgageTime",creditutil.datatotime(a.getR2_item6()));//��Ѻʱ��	 
		    	 }		    	 
		    	 jb.put("mortgageHolder",a.getR2_item7());//��ѺȨ��
		    	 jb.put("historyMortgage",a.getR2_item8());//��ʷ��Ѻ   	    	
    	        }
	    	     carList.add(jb);	     
	         } 

	        if(null!=carList&&carList.size()>0){
		    	int pointsDataLimit =200;//��������
		    	Integer size = carList.size();
		    	//�ж��Ƿ��б�Ҫ����
		    	if(pointsDataLimit<size){
		    	int part = size/pointsDataLimit;//������
		    	System.out.println("���� �� "+size+"������"+" ��Ϊ ��"+part+"��");
		    	//
		    	for (int i = 0; i < part; i++) {
		    		
		    	//100��
		        List<JSONObject> listPage = carList.subList(0,pointsDataLimit);
		        data.put("mortgageList",listPage);
		    	// System.out.println("data:"+data);	    	    		 
		 	     obj=syncutil.createHead(data);
		 	     obj.put("data", data);	
		    	 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/doMortgage", obj.toString());  	 		
		         res=res+s.toString();
		         carList.subList(0,pointsDataLimit).clear();  
		      // return data.toString();		
		      }
		    if(!carList.isEmpty()){
		    	     data.put("mortgageList", carList);
			    	// System.out.println("data:"+data);	    	    		 
			 	     obj=syncutil.createHead(data);
			 	     obj.put("data", data);	
			    	 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/doMortgage", obj.toString());  	 		
			    	 res=res+s.toString();
		    }
		   }else{
			     data.put("mortgageList", carList);
		    	// System.out.println("data:"+data);	    	    		 
		 	     obj=syncutil.createHead(data);
		 	     obj.put("data", data);	
		    	 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/doMortgage", obj.toString());  	 		
		         res=s.toString();
		   }
		   }else{
		     System.out.println("û������!!!");
		   }
	    	  }
		 return res;
	
	}
}
