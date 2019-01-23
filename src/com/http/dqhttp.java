//package com.http;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
// 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
// 
//import com.alibaba.fastjson.JSON;
// 
//public class dqhttp {
// 
//    private static final Log log = LogFactory.getLog(dqhttp.class);
//    private static final String apiUrl = "https://api.tongdun.cn/postloan/monitor/v2";
// 
//    private HttpURLConnection conn;
// 
//    public RiskPostloanResponse invoke(Map<String, Object> params) {
//        try {
//            URL url = new URL(apiUrl);
//            // ��֯�������
//            StringBuilder postBody = new StringBuilder();
//            for (Map.Entry<String, Object> entry : params.entrySet()) {
//                if (entry.getValue() == null) continue;
//                postBody.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),
//                        "utf-8")).append("&");
//            }
// 
//            if (!params.isEmpty()) {
//                postBody.deleteCharAt(postBody.length() - 1);
//            }
// 
//            conn = (HttpURLConnection) url.openConnection();
//            // ���ó�����
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            // �������ӳ�ʱ
//            conn.setConnectTimeout(1000);
//            // ���ö�ȡ��ʱ
//            conn.setReadTimeout(3000);
//            // �ύ����
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//            conn.getOutputStream().write(postBody.toString().getBytes());
//            conn.getOutputStream().flush();
//            int responseCode = conn.getResponseCode();
//            if (responseCode != 200) {
//                log.warn("RiskServicePostloan] invoke failed, response status:" + responseCode);
//                return null;
//            }
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//            StringBuilder result = new StringBuilder();
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                result.append(line).append("\n");
//            }
//            return JSON.parseObject(result.toString().trim(), RiskPostloanResponse.class);
//        } catch (Exception e) {
//            log.error("[RiskServicePostLoan] invoke throw exception, details: " + e);
//        }
//        return null;
//    }
// 
//    public static void main(String[] args) {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("partner_code", "salesdemo");                        // �˴�ֵ��д���ĺ�������ʶ
//        params.put("partner_key", "c71d5ca75bec48dbbd00174404a09581");  // ��������Կ
//        params.put("app_name", "show");                                 // Ӧ������
//        params.put("report_id", "ER2016041111501310606186");            // ������
//        params.put("sequence_id", "");
//        params.put("loan_term", 6);                                     // �ſ�����
//        params.put("loan_term_unit", "DAY");                            // �������޵�λ
//        params.put("loan_amount", 1000);                                // �ſ���
//        params.put("loan_date", 1440056827154L);                        // �ſ�����
//        params.put("begin_scan_time", System.currentTimeMillis() + 86400000L + 1000L);  // ɨ�迪ʼʱ��
//        params.put("end_scan_time", 1521532027000L);                    // ɨ�����ʱ��
//        params.put("scan_period", "7");                                 // ɨ������
//        params.put("operator","");                                      // ����Ա
//        RiskPostloanResponse riskResp = new dqhttp().invoke(params);
//        System.out.println(riskResp.toString());
//    }
// 
//}
//            
