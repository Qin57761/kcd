package com.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.util.creditutil;
import com.util.jsonutil;
import com.util.md5util;

import net.sf.json.JSONObject;

public class zxhttp {

	
	
	
	 /** 
     * 1.2 �����Ϣ�ɹ�����첽���ͽӿ� (���-��������)
     */  
    public static String zxxxhd(String orderNo,
    		String errmsg,
    		String path,
    		String pdfurl,
    		String pdfname,
    		String pdftime
    		) {  
    	 StringBuilder sb = new StringBuilder(); // �����洢��Ӧ����  
        try {  
        	//"http://www.zhixiangcf.com/wx/receive"
            URL url = new URL(path);   
            // ��url �� open�������ص�urlConnection  ����ǿתΪHttpURLConnection����  (��ʶһ��url�����õ�Զ�̶�������)  
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// ��ʱcnnectionֻ��Ϊһ�����Ӷ���,��������                
            // �������������Ϊtrue,Ĭ��false (post �����������ķ�ʽ��ʽ�Ĵ��ݲ���)  
            connection.setDoOutput(true);                
            // ��������������Ϊtrue  
            connection.setDoInput(true);                
            // ��������ʽΪpost  
            connection.setRequestMethod("POST");                
            // post���󻺴���Ϊfalse  
            connection.setUseCaches(false);                
            // ���ø�HttpURLConnectionʵ���Ƿ��Զ�ִ���ض���  
            connection.setInstanceFollowRedirects(true);                
            // ��������ͷ����ĸ������� (����Ϊ�������ݵ�����,����Ϊ����urlEncoded�������from����)  
            // application/x-javascript text/xml->xml���� application/x-javascript->json���� application/x-www-form-urlencoded->������  
            // ;charset=utf-8 ����Ҫ����Ȼ��Ǳ߻�������롾�����  
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
           // connection.setRequestProperty("token", "9164152B7EB0AF6EA589B1BE06073304");
            // �������� (����δ��ʼ,ֱ��connection.getInputStream()��������ʱ�ŷ���,���ϸ��������������ڴ˷���֮ǰ����)  
            connection.connect();                
            // �������������,�����������������Я���Ĳ���,(�������Ϊ?���������)  
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
//          String name,
//    		String idCard,
//    		String phoneNo,
//    		String orderNo,
//    		String errcode,
//    		String errmsg
//          String orderNo,
//    		String errmsg
            orderNo ="orderNo="+ URLEncoder.encode(orderNo, "utf-8");        
            errmsg ="&errmsg="+ URLEncoder.encode(errmsg, "utf-8");
            if(pdfurl!=null&&pdfname!=null&&pdftime!=null
            		&&!pdfurl.equals("")
            		&&!pdfname.equals("")
            		&&!pdftime.equals("")
            		){
            	  pdfurl ="&pdfurl="+ URLEncoder.encode(pdfurl, "utf-8");        
                  pdfname ="&pdfname="+ URLEncoder.encode(pdfname, "utf-8"); 
                  pdftime ="&pdftime="+ URLEncoder.encode(pdftime, "utf-8");  	
            }
                           
            String parm;
            // ��ʽ parm = aaa=111&bbb=222&ccc=333&ddd=444 
            if(pdfurl!=null&&pdfname!=null&&pdftime!=null
            		&&!pdfurl.equals("")
            		&&!pdfname.equals("")
            		&&!pdftime.equals("")
            		){
            	parm =orderNo+errmsg+pdfurl+pdfname+pdftime;
            }else{
            	parm =orderNo+errmsg;
            }	
            System.out.println("������"+parm);
            // ���������������  
            dataout.writeBytes(parm);  
              
            // �����ɺ�ˢ�²��ر���  
            dataout.flush();  
            dataout.close(); // ��Ҫ���׺��Բ��� (�ر���,�м�!)   
              
//            System.out.println(connection.getResponseCode());  
              
            // ���ӷ�������,�����������Ӧ  (�����ӻ�ȡ������������װΪbufferedReader)  
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));   
            String line;  
           
              
            // ѭ����ȡ��,��������β��  
            while ((line = bf.readLine()) != null) {  
//                sb.append(bf.readLine());  
                sb.append(line).append(System.getProperty("line.separator"));  
            }  
            bf.close();    // ��Ҫ���׺��Բ��� (�ر���,�м�!)   
            connection.disconnect(); // ��������  
           // System.out.println(sb.toString());  
      
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return sb.toString();  
    } 
    
    public static String zxcf(String orderNo,String errmsg,String SENTIMENT_URL,String md){
//    	, String cKey
        //String SENTIMENT_URL ="http://www.zhixiangcf.com/wx/receive";
        //http://apitest.kcway.net
        String body ="orderNo="+orderNo+"&errmsg="+errmsg;  //new JSONArray(new String[]{text}).toString();
        System.out.println(body);
        HttpResponse<String> jsonResponse = null;
        try {
            jsonResponse = Unirest.post(SENTIMENT_URL)
                    .header("Content-Type","text/html;charset=UTF-8")
                    .header("token",md)
                    .header("Method","post")
                    .body(body)
                    .asString();
            //Unirest.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(jsonResponse.getBody());
        return jsonResponse.getBody();
    }
    public static String bgcf(String orderNo,String errmsg){
//    	, String cKey
        String SENTIMENT_URL ="http://cesi3.0.zhiyujinrong.com/index.php/Api/Index/index/token/9164152B7EB0AF6EA589B1BE06073304.html";        
        //http://apitest.kcway.net
        String body ="orderNo="+orderNo+"&errmsg="+errmsg;  //new JSONArray(new String[]{text}).toString();
        System.out.println(body);
        HttpResponse<String> jsonResponse = null;
        try {
            jsonResponse = Unirest.post(SENTIMENT_URL)
                    .header("Content-Type","text/html;charset=UTF-8")
                    //.header("ckey",cKey)
                    .body(body)
                    .asString();
            //Unirest.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(jsonResponse.getBody());
        return jsonResponse.getBody();
    }
    
    
    public static void main(String[] args) {
    	//String errmsg = null ;
    	String errmsg1="�������";
    	String errmsg2="���ڲ�ѯ";
    	String errmsg3="��ѯ�ɹ�";
    	String errmsg4="����";    	
    	String md5=md5util.getMD5("18019035363"+"DFADF50683890586428B480902A6FB76","UTF-8");
    	System.out.println("md5:"+md5);
    	//http://cesi3.0.zhiyujinrong.com/index.php/Api/Index/index.html
    	String url="http://cesi3.0.zhiyujinrong.com/index.php/Api/Index/index/token/"+md5+".html";
    	System.out.println(url);
    	String res=zxxxhd("228",errmsg3,url,"http://apitest.kcway.net/image/upload/20171201/dc0e3d42446742f497f2eb59d2368506.pdf","dc0e3d42446742f497f2eb59d2368506.pdf",creditutil.time().toString());
    	JSONObject jobj=jsonutil.toJSONObject(res);
    	String needStr=jobj.getString("status");
        System.out.println(needStr+"-----"+res.toString());

	}
}
