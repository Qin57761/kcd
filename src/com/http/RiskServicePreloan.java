package com.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
//��ǰ  
public class RiskServicePreloan {
//	$testurl = "https://apitest.tongdun.cn/preloan/report/v7?partner_code=$p_code&partner_key=$p_key&app_name=$p_app_name";
//	$okurl = "https://api.tongdun.cn/preloan/report/v8?partner_code=$p_code&partner_key=$p_key&app_name=$p_app_name";
//?partner_code=$p_code&partner_key=$p_key&app_name=$p_app_name
    private static final Log    log          = LogFactory.getLog(RiskServicePreloan.class);
    private static final String submitUrl    = "https://api.tongdun.cn/preloan/apply/v5";
    private static final String queryUrl     = "https://api.tongdun.cn/preloan/report/v8";
    private static final long   WAIT_TIME    = 5 * 1000;

    private static final String PARTNER_CODE = "hdts";// ��������ʶ
    private static final String PARTNER_KEY  = "fc6ea2c81e144074955a888631725dae";//��������Կ
    // api fc6ea2c81e144074955a888631725dae 
    // test 39629fcf80034fa09ed3c63b4168e88b
    private static final String PARTNER_APP  = "hdts_web";//Ӧ����  api hdts_web  test rongzizulin_web

    private HttpsURLConnection   conn;
    private SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

    /**
     * submit�ӿ�ʾ��
     * @param <PreloanSubmitResponse>
     *
     * @param params
     * @return 
     */
    public JSONObject apply(Map<String, Object> params) {

       // PreloanSubmitResponse submitResponse = new PreloanSubmitResponse();
        StringBuilder result = null;
        try {
            String urlString = new StringBuilder().append(submitUrl).append("?partner_code=").append(PARTNER_CODE).append("&partner_key=").append(PARTNER_KEY).append("&app_name=").append(PARTNER_APP).toString();
            URL url = new URL(urlString);
            System.out.println(urlString);
            System.out.println(params);
            // ��֯�������
            StringBuilder postBody = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null) continue;
                postBody.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),"utf-8")).append("&");
            }
            if (!params.isEmpty()) {
                postBody.deleteCharAt(postBody.length() - 1);
            }
            conn = (HttpsURLConnection) url.openConnection();
            //����https
            conn.setSSLSocketFactory(ssf);
            // ���ó�����
            conn.setRequestProperty("Connection","Keep-Alive");
            // �������ӳ�ʱ          
            // ���ö�ȡ��ʱ
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            // �ύ����
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.getOutputStream().write(postBody.toString().getBytes());
            conn.getOutputStream().flush();
            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);
      
            if (responseCode==200) {
            	System.out.println("11111111");
                BufferedReader bufferedReader = new BufferedReader(
                                                                   new InputStreamReader(conn.getInputStream(), "utf-8"));
                result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                System.out.println("2"+result.toString().trim());
                //return jsonutil.toJSONString(result.toString().trim());
            }
        } catch (Exception e) {
            log.error("[RiskServicePreloan] apply throw exception, details: " + e);
        }
        return JSONObject.parseObject(result.toString().trim());
    }

    public  JSONObject query(String reportId) {
       // PreloanQueryResponse queryResponse = new PreloanQueryResponse();
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
                //return JSON.parseObject(result.toString().trim());
            }
        } catch (Exception e) {
            log.error("[RiskServicePreloan] query throw exception, details: " + e);
        }
        return JSONObject.parseObject(result.toString().trim());
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("loan_amount", ""); // ��������
        params.put("loan_term", ""); // ����������
        params.put("loan_term_unit", ""); // ���޵�λ
        params.put("loan_date", ""); // ����������
        params.put("purpose", ""); // �����;
        params.put("apply_province", ""); // ����ʡ��
        params.put("apply_city", ""); // ��������
        params.put("apply_channel", ""); // ��������
        params.put("name", "����"); // ���� ���� 352123197501281314
        //Ƥ���� 320304198404070412  ER2017122917192888A1963A

        params.put("id_number", "352123197501281314"); // ���֤��
        params.put("mobile", "13100000001"); // �ֻ���
        params.put("card_number", ""); // ���п�
        params.put("work_phone", ""); // ��λ����
        params.put("home_phone", ""); // ��ͥ����
        params.put("qq", ""); // qq
        params.put("email", ""); // ��������
        params.put("diploma", ""); // ѧ��
        params.put("marriage", ""); // ����
        params.put("house_property", ""); // �������
        params.put("house_type", ""); // ��������
        params.put("registered_address", ""); // ������ַ
        params.put("home_address", ""); // ��ͥ��ַ
        params.put("contact_address", ""); // ͨѶ��ַ
        params.put("career", ""); // ְҵ
        params.put("applyer_type", ""); // ���������
        params.put("work_time", ""); // ����ʱ��
        params.put("company_name", ""); // ������λ
        params.put("company_address", ""); // ��λ��ַ
        params.put("company_industry", ""); // ��˾��ҵ
        params.put("company_type", ""); // ��˾����
        params.put("occupation", ""); // ְλ
        params.put("annual_income", ""); // ������
        params.put("is_cross_loan", ""); // ���������Ƿ��ƽ̨������
        params.put("cross_loan_count", ""); // �������ڿ�ƽ̨������ƽ̨����
        params.put("is_liability_loan", ""); // ���������Ƿ��ƽ̨��ծ
        params.put("liability_loan_count", ""); // �������ڿ�ƽ̨��ծƽ̨����
        params.put("is_id_checked", ""); // �Ƿ�ͨ��ʵ����֤
        params.put("contact1_relation", ""); // ��һ��ϵ������ϵ
        params.put("concatc1_name", ""); // ��һ��ϵ������
        params.put("contact1_id_number", ""); // ��һ��ϵ�����֤
        params.put("contact1_mobile", ""); // ��һ��ϵ���ֻ���
        params.put("contact1_addr", ""); // ��һ��ϵ�˼�ͥ��ַ
        params.put("contact1_com_name", ""); // ��һ��ϵ�˹�����λ
        params.put("contact1_com_addr", ""); // ��һ��ϵ�˹�����ַ
        params.put("contact2_relation", "");
        params.put("contact2_name", "");
        params.put("contact2_id_number", "");
        params.put("contact2_mobile", "");
        params.put("contact2_addr", "");
        params.put("contact2_com_name", "");
        params.put("contact2_com_addr", "");
        params.put("contact3_relation", "");
        params.put("contact3_name", "");
        params.put("contact3_id_number", "");
        params.put("contact3_mobile", "");
        params.put("contact3_addr", "");
        params.put("contact3_com_name", "");
        params.put("contact3_com_addr", "");
        params.put("contact4_relation", "");
        params.put("contact4_name", "");
        params.put("contact4_id_number", "");
        params.put("contact4_mobile", "");
        params.put("contact4_addr", "");
        params.put("contact4_com_name", "");
        params.put("contact4_com_addr", "");
        params.put("contact5_relation", "");
        params.put("contact5_name", "");
        params.put("contact5_id_number", "");
        params.put("contact5_mobile", "");
        params.put("contact5_addr", "");
        params.put("contact5_com_name", "");
        params.put("contact5_com_addr", "");
        params.put("ip_address", ""); // IP��ַ
        params.put("token_id", ""); // token_id
        params.put("black_box", ""); // black_box
        RiskServicePreloan service = new RiskServicePreloan();
        
        JSONObject riskPreloanResponse = service.apply(params);
        System.out.println(riskPreloanResponse.toString());        
        String[] s= analyzeJsonToArray(riskPreloanResponse,"value");
        System.out.println(s[0]);
        if (!riskPreloanResponse.equals("")) {
            // �ȴ�һ��ʱ��󣬿ɵ���query�ӿڲ�ѯ�����
            // ʱ�佨�飺5s��ɵ���
            try {
                Thread.sleep(WAIT_TIME);
            } catch (Exception e) {
                //
            }
            // query�ӿڻ�ȡ���
            JSONObject response = service.query(s[0]);
            System.out.println(response);
            // �������� ������
        }
    }
    
    
    /**
     * ��json��ֵ�Էֱ������������
     * 
     * @param jsonject
     *            ��Ҫ������json����
     * @param type
     *            ��������ֵ�����ݣ�����ֵ
     * @return type="key"������json������"��"���ַ����� type="key""value":����json������"ֵ"���ַ���
     */
    public static String[] analyzeJsonToArray(JSONObject jsonject, String type) {

    String string = jsonject.toString();
    
    string = string.replace("}", "");
    string = string.replace("{", "");
    string = string.replace("\"", "");
    System.out.println(string);
    String[] strings = string.split(",");

    if (type.equals("key")) {
    String[] stringsNum = new String[strings.length];
    for (int i = 0; i < strings.length; i++) {
    stringsNum[i] = strings[i].split(":")[0];
    }
    return stringsNum;
    } else if (type.equals("value")) {
    String[] stringsName = new String[strings.length];
    for (int i = 0; i < strings.length; i++) {
    stringsName[i] = strings[i].split(":")[1];
    }
    return stringsName;
    } else {
    return null;
    }
    } 
}

            

