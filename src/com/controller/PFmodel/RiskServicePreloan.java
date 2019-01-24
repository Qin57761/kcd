package com.controller.PFmodel;
import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Currency;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * ����������demo
 * 2018��4��25��
 */
public class RiskServicePreloan {
    private static final Log  log= LogFactory.getLog(RiskServicePreloan.class);
    private static final String submitUrl    = "https://api.tongdun.cn/preloan/apply/v5";
    private static final String queryUrl     = "https://api.tongdun.cn/preloan/report/v8";
    private static final long   WAIT_TIME    = 5 * 1000;
    private static final String PARTNER_CODE = "hdts";// ��������ʶ
    private static final String PARTNER_KEY  = "fc6ea2c81e144074955a888631725dae";//��������Կ
    private static final String PARTNER_APP  = "hdts_web";//Ӧ����  api hdts_web  test rongzizulin_web
    private HttpsURLConnection   conn;
    private SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
    public  JSONObject query(String reportId) {
    	 StringBuilder result = null;
        try {
            String urlString = new StringBuilder().append(queryUrl).append("?partner_code=").append(PARTNER_CODE).append("&partner_key=").append(PARTNER_KEY).append("&report_id=").append(reportId).toString();
            URL url = new URL(urlString);
            conn = (HttpsURLConnection) url.openConnection();
            //����https
            conn.setSSLSocketFactory(ssf);
            // ���ó�����
            conn.setRequestProperty("Connection", "Keep-Alive");
            // �������ӳ�ʱ          
            // ���ö�ȡ��ʱ
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            // �ύ����
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"));
                result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            }
        } catch (Exception e) {
            log.error("[RiskServicePreloan] query throw exception, details: " + e);
        }
        return JSONObject.parseObject(result.toString().trim());
    }
    public void Customer(Customer customer){ 
    	  RiskServicePreloan service = new RiskServicePreloan();
    	  JSONObject jsonObject=service.query("ER2017040814362018354777");
    	  System.out.println(jsonObject.toJSONString());
    	  JSONArray  ja1=(JSONArray) jsonObject.get("risk_items");//������еķ�����
    	  if(ja1!=null){////ɨ������ķ����Ϊ��
    		  for(int i=0;i<ja1.size();i++){
    			  JSONObject jo=(JSONObject) ja1.get(i);//������������
    			  JSONObject jo2= (JSONObject)jo.get("item_detail");//�������
    			  Object ss1=jo2.get("platform_count");
    			  String s=jo.getString("item_id");
    			  if(ss1!=null){
    				  if(s.equals("3393158")){ //7�����������ڶ��ƽ̨������
        				  customer.setSeven_days(ss1.toString());
         			  }else if(s.equals("3393160")){ //һ�������������ڶ��ƽ̨������
        				   customer.setOne_month(ss1.toString());//��ͷ���
         			  }else if(s.equals("3393162")){ //3�������������ڶ��ƽ̨������
           				   customer.setThree_month(ss1.toString());
         			  }else if(s.equals("3393164")){ //6�������������ڶ��ƽ̨������
    	   				   customer.setSix_month(ss1.toString());
        			  }else if(s.equals("3393166")){ //12�������������ڶ��ƽ̨������
    	   				   customer.setTwelve_month(ss1.toString());
    	    		 }
    				  continue;
    			  }
	   			 if(s.equals("10")){ // ��Ժִ�� ʧ����
	   				   JSONArray ja2=(JSONArray) jo2.get("namelist_hit_details");//�������������б�
	   				   for(int j=0;j<ja2.size();j++){
	   					JSONArray ja3=ja2.getJSONObject(j).getJSONArray("court_details");//��Ժ�����б�
	   					for(int z=0;z<ja3.size();z++){
	   						JSONObject jo3=(JSONObject)ja3.get(z);//��Ժ����
	   						String s0=jo3.get("fraud_type").toString();//��թ����  "��Ժʧ��"��"��Ժִ��"��"��Ժ�᰸"�е�һ��
	   						String s1=jo3.getString("situation").toString();//���
	   						if(s0.equals("��Ժʧ��")){//����ʧ�Ź�
	   							customer.setIs_credit("1");
	   							continue;
	   						}
	   						if(s0.equals("��Ժִ��")){
	   							if(s1.equals("�ѽ᰸") && !customer.getCourt_execution().equals("2")){//��ִ���˵�����������ѽ᰸ ����û��δ�᰸��
	   								customer.setCourt_execution("1");
	   							}else{
	   								customer.setCourt_execution("2");
	   							}
	   							continue;
	   						}
	   						if(s0.equals("��Ժ�᰸")  && !customer.getCourt_execution().equals("2")){//���ڷ�Ժ�᰸ ����û����ִ���е�
	   							customer.setCourt_execution("1");
	   							continue;
	   						}
	   					  }
	   				    }
	    			 }	
	    		  } 	
	    		 Demo_TaiRA.fun(customer); 
	    	  }  	
    	}
}