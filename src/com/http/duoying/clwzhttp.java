package com.http.duoying;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class clwzhttp{
	
    public static final String APPKEY = "25f54018acb1a239";// ���appkey
    public static final String URL = "http://api.jisuapi.com/illegal/query";
    public static final String carorg = "";// ���ִܾ���
    public static final String lsprefix = "��";// ����ǰ׺ utf8
    public static final String lsnum = "";// ����
    public static final String lstype = "02";// ��������
    public static final String engineno = "";// ��������
    public static final String frameno = "";// ���ܺ�
	
	 public static JsonNode query(
			 String carorg,
			 String lsprefix,
			 String lsnum,
			 String lstype,
			 String engineno,
			 String frameno 
			 ){
	        HttpResponse<JsonNode> jsonResponse = null;
	        try {
	            jsonResponse = Unirest.get(URL)
	            		.header("method", "get")
	            		//.header("Content-Type","text/html;charset=UTF-8")
	                    //.header("ckey",cKey)
	                    //.routeParam("method", "get")
	                    .queryString("appkey",APPKEY)//
	                    .queryString("carorg",carorg)// ���ִܾ���
	                    .queryString("lsprefix",lsprefix)// ����ǰ׺ utf8
	                    .queryString("lsnum",lsnum)// ����
	                    .queryString("lstype",lstype)// ��������
	                    .queryString("engineno",engineno)// ��������
	                    .queryString("frameno",frameno)// ���ܺ�
	                    .asJson();
	            //Unirest.shutdown();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        //System.out.println(jsonResponse.getBody());
	        return jsonResponse.getBody();
	    }
	 
	 public static void main(String[] args) {
		     String carorg = "";// ���ִܾ���
		     String lsprefix = "��";// ����ǰ׺ utf8  true
		     String lsnum = "F511AZ";// ����  true
		     String lstype = "02";// ��������    
		     String engineno = "*932467";// ��������   true
		     String frameno = "*013249";// ���ܺ�    true
		     
		// System.out.println("��CNW006640".substring(0,1));
		 //System.out.println("��CNW006640".substring(1,"��CNW006640".length()));
		JsonNode s= query(carorg, lsprefix, lsnum, lstype, engineno, frameno);
		  JSONObject jb=JSONObject.parseObject(s.toString());
		     
		if(jb.get("status").equals("0")){
			JSONObject result=JSONObject.parseObject(jb.get("result").toString()); 
			JSONArray ja=JSONArray.parseArray(result.getString("list"));
			
			
			
			System.out.println("�ɹ�"+ja);
		}
//		 int i=290000/100-2704;
//		System.out.println(i);
		 
	}
	 

}