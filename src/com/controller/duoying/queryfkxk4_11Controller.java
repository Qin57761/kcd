package com.controller.duoying;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.http.duoying.syncjkrxxHttp;
import com.mashape.unirest.http.JsonNode;
import com.model1.mgcert;
import com.service1.duoying.mgcertService;
import com.util.duoying.syncutil;

@Controller
public class queryfkxk4_11Controller {

	  @Autowired
	  private mgcertService mgcertservice;
	 
	  /**
	   * 4.11	��ѯ�ſ����
       * �ӿ�˵��:��ѯ�����Ƿ���Էſ�
	   * @return
	   */
	 @RequestMapping(value="queryfkxk.do",produces = "text/html;charset=UTF-8")
     @ResponseBody
	 public String queryfkxk(){
		
		    List<mgcert> ml=mgcertservice.Apijkxxmgcert();
			JSONObject data=new JSONObject();
			List idList =new ArrayList<>();			
			String s = null;
			String res="";
			if(ml!=null&&ml.size()>0){
				for(mgcert m : ml){				  
					idList.add(m.getGems_code()+"|mgcert");
				}	
		        if(null!=idList&&idList.size()>0){
			    	int pointsDataLimit =200;//��������
			    	Integer size = idList.size();
			    	//�ж��Ƿ��б�Ҫ����
			    	if(pointsDataLimit<size){
			    	int part = size/pointsDataLimit;//������
			    	System.out.println("���� �� "+size+"������"+" ��Ϊ ��"+part+"��");
			    	//
			    	for (int i = 0; i < part; i++) {	    		
			    	//100��
			        List<net.sf.json.JSONObject> listPage = idList.subList(0,pointsDataLimit);
					 data.put("idList", listPage);
					 JSONObject obj=syncutil.createHead(data);
					 obj.put("data", data);
					 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/queryDisbursement", obj.toString());  
					 res=res+s.toString();
					 idList.subList(0,pointsDataLimit).clear();  
			        // return data.toString();		
			      }
			    if(!idList.isEmpty()){
					 data.put("idList", idList);
					 JSONObject obj=syncutil.createHead(data);
					 obj.put("data", data);
					 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/queryDisbursement", obj.toString());  
					 res=res+s.toString();
			    }
			   }else{
					 data.put("idList", idList);
					 JSONObject obj=syncutil.createHead(data);
					 obj.put("data", data);
					 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/queryDisbursement", obj.toString());  
					 res=s.toString();
			   }
			   }else{
			     System.out.println("û������!!!");
			   }	
								
}			
		 return res.toString();
		 
		 
		 
	 }
}
