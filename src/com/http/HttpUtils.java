package com.http;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.model.index.zxmodel;
import com.util.Base64Test;
import com.util.jsonutil;
import com.util.md5util;
import com.util.duoying.MD5;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-06-24 13:11
 */
public final class HttpUtils {
	 public static String appadd(String url1,String js) {
		 StringBuffer sb = null;
	        try {
	            //��������
	            URL url = new URL(url1);
	            HttpURLConnection connection = (HttpURLConnection) url
	                    .openConnection();
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            connection.setRequestMethod("POST");
	            connection.setUseCaches(false);
	            connection.setInstanceFollowRedirects(true);
	            connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");

	            connection.connect();

	            //POST����
	            DataOutputStream out = new DataOutputStream(
	                    connection.getOutputStream());
//	            JSONObject obj = new JSONObject();
//	            obj.element("app_name", "asdf");
//	            obj.element("app_ip", "10.21.243.234");
//	            obj.element("app_port", 8080);
//	            obj.element("app_type", "001");
//	            obj.element("app_area", "asd");

	            out.writeBytes(js);
	            out.flush();
	            out.close();

	            //��ȡ��Ӧ
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String lines;
	            sb = new StringBuffer("");
	            while ((lines = reader.readLine()) != null) {
	                lines = new String(lines.getBytes(), "utf-8");
	                sb.append(lines);
	            }
	            //System.out.println(sb);
	            reader.close();
	            // �Ͽ�����
	            connection.disconnect();
	        } catch (MalformedURLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
			return sb.toString();

	    }
	
    public static String zxcebody(String url,String data){
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
    	//http://localhost:8080/kcd/to_query_zx.do
    	//http://apitest.kcway.net/to_query_zx.do
		String url="http://apitest.kcway.net/to_query_zx.do";
    	String fronttobase=Base64Test.GetImageStr("E:/�����Ŀ�ļ��ĵ�����/1/2016-3-24 14-09-30.png");//���֤������ͼƬתbase64����
    	String oppositetobase=Base64Test.GetImageStr("E:/�����Ŀ�ļ��ĵ�����/1/2016-3-24 14-09-30.png");//���֤������ͼƬתbase64����
    	String applytobase=Base64Test.GetImageStr("E:/�����Ŀ�ļ��ĵ�����/1/2016-3-24 14-09-30.png");//������������ͼƬתbase64����
    	String authorizetobase=Base64Test.GetImageStr("E:/�����Ŀ�ļ��ĵ�����/1/2016-3-24 14-09-30.png");//��Ȩ��������ͼƬתbase64����
    	String hztobase=Base64Test.GetImageStr("E:/�����Ŀ�ļ��ĵ�����/1/2016-3-24 14-09-30.png");//����������ͼƬתbase64����
    	String ckey="1";//ckey��֤��
    	String name="����";//�ͻ�����
    	String IDcard_num="411403199512198419";//�ͻ����֤��
    	String phone_num="15544345534";//�ͻ��ֻ���
    	String authorize_num="12345678910";//��Ȩ�����
    	String sum_bit="4";//�ύ״̬ 
    	String ly="�����ύ";//��ע
        Map m=new HashMap<>();
        //System.out.println(md5util.getMD5(fronttobase,"UTF-8"));
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
        m.put("ly", ly);
        System.out.println(m);
        zxmodel zx=new zxmodel();
//        zx.setFronttobase(fronttobase);
//        zx.setOppositetobase(oppositetobase);
//        zx.setApplytobase(applytobase);
//        zx.setAuthorizetobase(authorizetobase);
//        zx.setHztobase(hztobase);
//        zx.setCkey(ckey);
//        zx.setName(name);
   //     zx.setIdcard_num(IDcard_num);
//        zx.setPhone_num(phone_num);
//        zx.setAuthorize_num(authorize_num);
//        zx.setSum_bit(sum_bit);
//        zx.setLy(ly);
        JSONObject js= jsonutil.toJSONObject(m);
        System.out.println("json��С��"+js.toString().length());
    	String s=zxcebody(url, js.toString());
        // String s=appadd(url, js.toString());
    	System.out.println("�������ݣ�"+s);
	}
}