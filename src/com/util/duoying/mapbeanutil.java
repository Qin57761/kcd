package com.util.duoying;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringEscapeUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.http.duoying.syncjkrxxHttp;
import com.mashape.unirest.http.JsonNode;
import com.util.jsonutil;
import com.util.jsy.WriteStringToTxt;

public class mapbeanutil {
	private static String privatekey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJTjUZ/CBrE3hvkeMvkFxtM9uQFOOGpqWjjnJdJRrPWMjb1aDWS8btISwEm80Zs8msPk7/Qb2dh11qpSjvaNwXKIK0zJwi9Z0V6zrZNz+6V7NgPpbKjmWxU+QaY2D9CK3BWlysoVqb8YSISt25p72nB8HzOHJ+sIDeKJI9UIY/vLAgMBAAECgYEAim5IyCdYnZEpN5qyfgK2+FVdHC+kGJ1Fwb541fIGxE+owbNm3JCu4Td5/ZVHtfRFWXoU+HyksbPuoXIdZnQqtWuInNhdPVpiir6/yXSvP5LLfQN6lqkCzapgtuhuz3Cayp58qb0k4ujZ2l5pegNN7a8plqHUSZNoE3VFHMNNTZECQQDYyRm7U+gliPlnO8bpnnU6ciFbiAeXbWS4z+HY2hLHWqFO7U2grBKueJ1yMYDNL8PCGbbyO0bUxDIu07t5KYg1AkEAr9IEzgIYwbCBujRgJ3rj7r5bXsggzTiHLypj+Uvsq0niI2TvHmiYczP0m9lSHmuvZwhcdhd0bufA81Zigi/z/wJBAIcVAGC3Dw/cgzQtjmviXj/WAC0t3TUhaEK03pEmic8JDTzGJ7n3nwhyhgEzEYRJwByBs3rLLv7DZlXBf68nDwUCQHe4mND2mIj7ebqjg34eriqZsHn/6GYVweeaA+1zh7qzWqsjRbf9HSIFFOEywDo6tXuBNAStv/jtEnQgNH/Vy10CQBzWF7XlU9oXiLwoVoe+7JAe7cnfAfG+2nwiuzc0x9oHB1p7rET3u0AMIR6LfC0K2FWheQRYcqsAWyQviIjWa8I=";
	   
	
	
	  public static void main(String[] args) {
		String s1=syncutil.getJson("C:/Users/Administrator/Desktop/json�ַ���/����÷.txt");
		JSONObject js=JSONObject.parseObject(s1);
		System.out.println("ԭ�ı���"+js);
		JSONObject data= js.getJSONObject("data");
		//net.sf.json.JSONObject js=jsonutil.toJSONObject(s1.toString());
		 JSONObject obj = new JSONObject();
		 System.out.println(js.get("curTime")+"-----"+js.get("nonce"));
	        obj.put("ver", "1.0");// �汾
	        obj.put("curTime",js.get("curTime"));
	        obj.put("nonce",js.get("nonce"));// һ�������       
	        //System.out.println("data----"+JSONObject.toJSONString(data).toString());
	        obj.put("sign",MD5.sign(privatekey 
	        		+obj.getString("nonce")
	                +obj.getString("curTime")
	                +com.alibaba.fastjson.JSONObject.toJSONString(data).toString(),"UTF-8"));// ǩ��
	        obj.put("signType","MD5");// ǩ������
	        obj.put("appKey", "KCD");// ��ӯ�ṩ��APPKEY
	        obj.put("data", data);
     	//new WriteStringToTxt().WriteStringToFile5("C:/Users/Administrator/Desktop/json�ַ���/jsonerror1.txt",s1);
     	//System.out.println("�����"+obj);
	        String s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/addLoanInfo",s1);				    		         	      
     	System.out.println(s+"------");
		
//       	net.sf.json.JSONObject js=jsonutil.toJSONObject(s1.toString());    
//       	net.sf.json.JSONObject data1=(net.sf.json.JSONObject) js.get("data");
//       	System.out.println("sign:---"+js.get("sign"));
//           //��������
//       	List<JSONObject> loanInfoList=new ArrayList<JSONObject>();
//       	loanInfoList=(List<JSONObject>) data1.get("loanInfoList");
//       	    	if(null!=loanInfoList&&loanInfoList.size()>0){
//       	    	int pointsDataLimit=1;//��������
//       	    	Integer size = loanInfoList.size();
//       	    	//�ж��Ƿ��б�Ҫ����
//       	    	if(pointsDataLimit<size){
//       	    	int part = size/pointsDataLimit;//������
//       	    	System.out.println("���� �� "+size+"������"+" ��Ϊ ��"+part+"��");
//       	    	//
//       	    	for (int i = 0; i < part; i++) {
//       	    	//1000��
//       	         List<JSONObject> listPage = loanInfoList.subList(0, pointsDataLimit);
//       	    	 JSONObject data =new JSONObject();
//       	    	 data.put("loanInfoList",listPage); 
//       	    	// System.out.println("data��"+data);
//       		     //System.out.println("С��100������:"+loanInfoList.size());
//       		 	 JSONObject obj=syncutil.createHead(data); 
//       	       	 obj.put("data",data); // ���ݵĲ���  
//                 JsonNode s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/addLoanInfo",obj.toString());				    		         	      
//                 net.sf.json.JSONObject js1=jsonutil.toJSONObject(s.toString());
//    		     if(obj!=null&&!obj.equals("")){
//    		     if(js1.get("code").toString().equals("500")){
//    	            // System.out.println("code:======"+js.get("code"));
//    		     new WriteStringToTxt().WriteStringToFile5("C:/Users/Administrator/Desktop/json�ַ���/json233"+i+".txt",data.toString());		       			      
//    		     } 	
//    		     }
//                 
//                 System.out.println(s+"------"); 
//       	    	 //�޳�
//       	         loanInfoList.subList(0, pointsDataLimit).clear();
//       	         }
//       	    if(!loanInfoList.isEmpty()){
//       	    	 JSONObject data =new JSONObject();
//       	    	 data.put("loanInfoList", loanInfoList); 
//       	    	// System.out.println("data��"+data);
//       		     //System.out.println("С��100������:"+loanInfoList.size());
//       		 	 JSONObject obj=syncutil.createHead(data); 
//       	       	 obj.put("data",data); // ���ݵĲ���  
//                   JsonNode s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/addLoanInfo",obj.toString());				    		         	      
//       	       System.out.println(s); 
//       	    }
//       	   }else{
//       	    	 JSONObject data =new JSONObject();
//       	    	 data.put("loanInfoList", loanInfoList); 
//       	    	// System.out.println("data��"+data);
//       		     //System.out.println("С��100������:"+loanInfoList.size());
//       		 	 JSONObject obj=syncutil.createHead(data); 
//       	       	 obj.put("data",data); // ���ݵĲ���  
//              JsonNode s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/addLoanInfo",obj.toString());				    		         	      
//       	       System.out.println(s); 
//       	    }
//       	    	}else{
//       	  System.out.println("û������!!!");
//       	   }
		  
	}
	
	
 
}
