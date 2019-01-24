package com.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.hp.hpl.sparta.ParseException;
import com.util.Base64Test;

public class niwodaihttp {

//	http://apitest.kcway.net
	//http://localhost:8080/kcd/
	 public static final String POST_URL="http://localhost:8080/kcd/tofindzx.do";//���ű����ύ��ѯ�ӿڵ�ַ
	 public static final String POST_URL1="http://apitest.kcway.net/findorder.do";//���ű����ѯ�ӿڵ�ַ
	 public static final String POST_URL2="http://apitest.kcway.net/hqzxbg.do";//���ű�����ʵʱ���ͽӿ�
	 public static final String POST_URL3="http://apitest.kcway.net/findingofaudit.do";//���ű�����ʵʱ���ͽӿ�
    /** 
     * ���ű����ύ��ѯ�ӿ�
     */  
    public static String zxtj(String fronttobase,
    		String oppositetobase,
    		String applytobase,
    		String authorizetobase,
    		String hztobase,
    		String ckey,
    		String name,
    		String IDcard_num,
    		String phone_num,
    		String authorize_num,
    		String sum_bit,
    		String ly) { 
    	  StringBuilder sb = new StringBuilder();
        try {  
            URL url = new URL(POST_URL);  
              
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
            connection.setRequestProperty("Content-Type", "application/xml;charset=utf-8");     
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); 
            //connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); 
            // �������� (����δ��ʼ,ֱ��connection.getInputStream()��������ʱ�ŷ���,���ϸ��������������ڴ˷���֮ǰ����)             
            connection.connect();  
              
            // �������������,�����������������Я���Ĳ���,(�������Ϊ?���������)  
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
              
            fronttobase = "fronttobase="+ URLEncoder.encode(fronttobase, "utf-8");        
            oppositetobase = "&oppositetobase="+ URLEncoder.encode(oppositetobase, "utf-8");           
            applytobase = "&applytobase="+ URLEncoder.encode(applytobase, "utf-8");            
            authorizetobase = "&authorizetobase="+ URLEncoder.encode(authorizetobase, "utf-8");        
            hztobase = "&hztobase="+ URLEncoder.encode(hztobase, "utf-8");  
            ckey = "&ckey="+ URLEncoder.encode(ckey, "utf-8");  
            name = "&name="+ URLEncoder.encode(name, "utf-8");  
            IDcard_num = "&IDcard_num="+ URLEncoder.encode(IDcard_num, "utf-8");  
            phone_num = "&phone_num="+ URLEncoder.encode(phone_num, "utf-8");  
            authorize_num = "&authorize_num="+ URLEncoder.encode(authorize_num, "utf-8");  
            sum_bit = "&sum_bit="+ URLEncoder.encode(sum_bit, "utf-8");  
            ly = "&ly="+ URLEncoder.encode(ly, "utf-8"); 
            // ��ʽ parm = aaa=111&bbb=222&ccc=333&ddd=444  
            String parm =fronttobase+
            		oppositetobase+
            		applytobase+
            		authorizetobase+
            		hztobase+
            		ckey+
            		name+
            		IDcard_num+
            		phone_num+
            		authorize_num+
            		sum_bit+
            		ly;
            		
            // ���������������  
            //System.out.println(parm);
            dataout.writeBytes(parm);  
              System.out.println("�������С��"+parm.length()/1024);
            // �����ɺ�ˢ�²��ر���  
            dataout.flush();  
            dataout.close(); // ��Ҫ���׺��Բ��� (�ر���,�м�!)   
              
//            System.out.println(connection.getResponseCode());                
            // ���ӷ�������,�����������Ӧ  (�����ӻ�ȡ������������װΪbufferedReader)  
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));   
            String line;  
           // �����洢��Ӧ����  
              
            // ѭ����ȡ��,��������β��  
            while ((line = bf.readLine()) != null) {  
//                sb.append(bf.readLine());  
                sb.append(line).append(System.getProperty("line.separator"));  
            }  
            bf.close();    // ��Ҫ���׺��Բ��� (�ر���,�м�!)   
            connection.disconnect(); // ��������  
            //System.out.println(sb.toString());  
      
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return sb.toString();  
    }         			
    
    
    /** 
     * 1.2 ���ű����ѯ�ӿ�
     */  
    public static String zxcx(String orderNo,String ckey) {  
    	 StringBuilder sb = new StringBuilder(); // �����洢��Ӧ����  
        try {  
            URL url = new URL(POST_URL1);  
              
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
              
            // �������� (����δ��ʼ,ֱ��connection.getInputStream()��������ʱ�ŷ���,���ϸ��������������ڴ˷���֮ǰ����)  
            connection.connect();  
              
            // �������������,�����������������Я���Ĳ���,(�������Ϊ?���������)  
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
              
            orderNo = "orderNo="+ URLEncoder.encode(orderNo, "utf-8");        
            ckey = "&ckey="+ URLEncoder.encode(ckey, "utf-8");           
            // ��ʽ parm = aaa=111&bbb=222&ccc=333&ddd=444  
            String parm =orderNo+ckey;
            		
            // ���������������  
            dataout.writeBytes(parm);  
              
            // �����ɺ�ˢ�²��ر���  
            dataout.flush();  
            dataout.close(); // ��Ҫ���׺��Բ��� (�ر���,�м�!)   
              
//            System.out.println(connection.getResponseCode());  
              
            // ���ӷ�������,�����������Ӧ  (�����ӻ�ȡ������������װΪbufferedReader)  
            //System.out.println(connection.getInputStream()+"----http");
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
    
    
    
    
    /** 
     * 1.2 ���ű�����ʵʱ���ͽӿ�
     */  
    public static String hqzxbg(String orderNo,String pdfurl,String addtime) {  
    	 StringBuilder sb = new StringBuilder(); // �����洢��Ӧ����  
        try {  
            URL url = new URL(POST_URL2);  
              
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
              
            // �������� (����δ��ʼ,ֱ��connection.getInputStream()��������ʱ�ŷ���,���ϸ��������������ڴ˷���֮ǰ����)  
            connection.connect();  
              
            // �������������,�����������������Я���Ĳ���,(�������Ϊ?���������)  
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
              
            orderNo = "orderNo="+ URLEncoder.encode(orderNo, "utf-8");        
            pdfurl = "&pdfurl="+ URLEncoder.encode(pdfurl, "utf-8"); 
           // pdfname = "&pdfname="+ URLEncoder.encode(pdfname, "utf-8"); 
            addtime = "&addtime="+ URLEncoder.encode(addtime, "utf-8"); 
            // ��ʽ parm = aaa=111&bbb=222&ccc=333&ddd=444  
            String parm =orderNo+pdfurl+addtime;
            		
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
    /** 
     * 1.2 �����Ϣ�ɹ�����첽���ͽӿ� (���-��������)
     */  
    public static String shxxresult(String name,
    		String idCard,
    		String phoneNo,
    		String orderNo,
    		String errcode,
    		String errmsg) {  
    	 StringBuilder sb = new StringBuilder(); // �����洢��Ӧ����  
        try {  
            URL url = new URL(POST_URL3);  
              
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
              
            // �������� (����δ��ʼ,ֱ��connection.getInputStream()��������ʱ�ŷ���,���ϸ��������������ڴ˷���֮ǰ����)  
            connection.connect();  
              
            // �������������,�����������������Я���Ĳ���,(�������Ϊ?���������)  
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
//            String name,
//    		String idCard,
//    		String phoneNo,
//    		String orderNo,
//    		String errcode,
//    		String errmsg
            name = "name="+ URLEncoder.encode(name, "utf-8");        
            idCard = "&idCard="+ URLEncoder.encode(idCard, "utf-8"); 
            phoneNo = "&phoneNo="+ URLEncoder.encode(phoneNo, "utf-8"); 
            orderNo = "&orderNo="+ URLEncoder.encode(orderNo, "utf-8"); 
            errcode = "&errcode="+ URLEncoder.encode(errcode, "utf-8"); 
            errmsg = "&errmsg="+ URLEncoder.encode(errmsg, "utf-8"); 
            // ��ʽ parm = aaa=111&bbb=222&ccc=333&ddd=444  
            String parm =name+idCard+phoneNo+orderNo+errcode+errmsg;
            		
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
    
    
    /** 
     * ģ������ 
     *  
     * @param url       ��Դ��ַ 
     * @param map   �����б� 
     * @param encoding  ���� 
     * @return 
     * @throws ParseException 
     * @throws IOException 
     */  
    public static String send(String url, Map<String,String> map,String encoding) throws ParseException, IOException{  
        String body = "";  
  
        //����httpclient����  
        CloseableHttpClient client = HttpClients.createDefault();  
        //����post��ʽ�������  
        HttpPost httpPost = new HttpPost(url);  
          
        //װ�����  
        List nvps = new ArrayList();  
        if(map!=null){  
            for (Entry<String, String> entry : map.entrySet()) {  
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
            }  
        }  
        //���ò��������������  
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));  
  
        System.out.println("�����ַ��"+url);  
        System.out.println("���������"+nvps.toString());  
          
        //����header��Ϣ  
        //ָ������ͷ��Content-type������User-Agent��  
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
        //httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
          
        //ִ��������������õ������ͬ��������  
        CloseableHttpResponse response = client.execute(httpPost);  
        //��ȡ���ʵ��  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            //��ָ������ת�����ʵ��ΪString����  
            body = EntityUtils.toString(entity, encoding);  
        }  
        EntityUtils.consume(entity);  
        //�ͷ�����  
        response.close();  
        return body;  
    }  

public static void main(String[] args) {
	niwodaihttp nwd=new niwodaihttp();
	String fronttobase=Base64Test.GetImageStr("E:/�����Ŀ�ļ��ĵ�����/1/2016-3-24 14-08-41.png");//���֤������ͼƬתbase64����
	String oppositetobase=Base64Test.GetImageStr("E:/�����Ŀ�ļ��ĵ�����/1/2016-3-24 14-08-41.png");//���֤������ͼƬתbase64����
	String applytobase=Base64Test.GetImageStr("E:/�����Ŀ�ļ��ĵ�����/1/2016-3-24 14-08-41.png");//������������ͼƬתbase64����
	String authorizetobase=Base64Test.GetImageStr("E:/�����Ŀ�ļ��ĵ�����/1/2016-3-24 14-08-41.png");//��Ȩ��������ͼƬתbase64����
	String hztobase=Base64Test.GetImageStr("E:/�����Ŀ�ļ��ĵ�����/1/2016-3-24 14-08-41.png");//����������ͼƬתbase64����
	String ckey="1";//ckey��֤��
	String name="����";//�ͻ�����
	String IDcard_num="411403199512198419";//�ͻ����֤��
	String phone_num="15544345534";//�ͻ��ֻ���
	String authorize_num="12345678910";//��Ȩ�����
	String sum_bit="4";//�ύ״̬ 
	String ly="�ύ��ѯ��Ϣ";//��ע
//	 String url="https://api.kcway.net/cshttp.do";  
//     Map<String, String> map = new HashMap<String, String>();  
//     map.put("csstr", "js");  
//     map.put("id","1");   
//     String body;
//	try {
//		body = send(url, map,"utf-8");
//	    System.out.println("������Ӧ�����");  
//	     System.out.println(body);  
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}  
 
     //������Ϣ�ύ
	//System.out.println();
	String s= zxtj(fronttobase,oppositetobase, applytobase, authorizetobase, hztobase, 
				ckey, name, IDcard_num, phone_num, authorize_num, sum_bit, ly);
	System.out.println(s);
	 String orderno="";
	 //������ѯ
//	 System.out.println("������Ҫ��ѯ�Ķ���:");
//	 String orderNo = new Scanner(System.in).next();
//	 zxcx(orderNo,ckey);
	 
	 
}
	
	
	
	
	
	
	
	
	
}
