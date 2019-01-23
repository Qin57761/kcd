package com.http.zxceshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.util.Base64Test;
import com.util.jsonutil;
import com.util.md5util;

import net.sf.json.JSONObject;

public class HttpRequest {
    /**
     * ��ָ��URL����GET����������
     * 
     * @param url
     *            ���������URL
     * @param param
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return URL ������Զ����Դ����Ӧ���
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * ��ָ�� URL ����POST����������
     * 
     * @param url
     *            ��������� URL
     * @param param
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return ������Զ����Դ����Ӧ���
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*;charset=UTF-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
            out.print(param);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("���� POST ��������쳣��"+e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    public static String zxup(String url,String data){
//    	, String cKey
        String SENTIMENT_URL =url;//"http://cesi3.0.zhiyujinrong.com/index.php/Api/Index/index/token/9164152B7EB0AF6EA589B1BE06073304.html";        
        //http://apitest.kcway.net
        //String body ="orderNo="+orderNo+"&errmsg="+errmsg;  //new JSONArray(new String[]{text}).toString();
        //System.out.println(body);
        HttpResponse<String> jsonResponse = null;
        try {
            jsonResponse = Unirest.post(SENTIMENT_URL)
                    .header("Content-Type","application/json;charset=UTF-8")
                    //.header("ckey",cKey)
                    .body(data)
                    .asString();
            //Unirest.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(jsonResponse.getBody());
        return jsonResponse.getBody();
    }
    public static void main(String[] args) {
    	String filebase=Base64Test.getImageStr("C:/Users/Administrator/Desktop/���Խ��.jpg");
		String strURL = "http://apitest.kcway.net/to_query_zx.do";
		String fronttobase = filebase;
		String oppositetobase = filebase;
		String applytobase = filebase;
		String authorizetobase = filebase;
		String hztobase = filebase;
		String name = "Ըdan����";
		String IDcard_num = "429001199405030283";
		String phone_num = "15010219309";
		String authorize_num = "012345";
		String sum_bit = "4";
		String ckey = "04D5C8B3257FE2A268326A4B5F0BC2D2";
		//String ly = "����";
		String orderid="143";
        Map m=new HashMap<>();
        System.out.println(md5util.getMD5(fronttobase,"UTF-8"));
        //System.out.println(md5util.JM(md5util.getMD5(fronttobase,"UTF-8")));
        m.put("fronttobase", fronttobase);
        m.put("oppositetobase", oppositetobase);
        m.put("applytobase", applytobase);
        m.put("authorizetobase", authorizetobase);
        m.put("hztobase", hztobase);
        m.put("ckey", ckey);
        m.put("name", name);
        m.put("idcard_num", IDcard_num);
        m.put("phone_num", phone_num);
        m.put("authorize_num", authorize_num);
        m.put("sum_bit", sum_bit);
        //m.put("ly", ly);
        m.put("orderid",orderid);
        JSONObject js= jsonutil.toJSONObject(m);
        System.out.println("json��С��"+js.toString().length());
    	String s=zxup(strURL, js.toString());
        // String s=appadd(url, js.toString());
    	System.out.println("�������ݣ�"+s);
	}
}