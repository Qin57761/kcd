package com.controller.erp_icbc.YunXin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import com.alibaba.fastjson.JSON;
/**
 * @Description:TODO
 * @author:LiWang
 * @time:2018��8��22��
 */
public class HttpYX {
	/**
		��ȡ��������ƵͨѶID
	 */
	public static String getToken(String accid){
		//���ò���
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid",accid));
		return HttpYX.doPost(YXConstant.CreateToken,nvps);
	}
	//��ȡ�ƶ����ϴ��ն��û�
	public static String geMobileUpload(String accid){
		//���ò���
		Map map=new HashMap(2);
		map.put("accid",accid);
		map.put("type",1);
		return HttpYX.doPost(YXConstant.CreateMobileUpload,map);
	}
	/**��Ӻ���
	 * @param accid������ͨ��ID����󳤶�32�ַ������뱣֤һ�� APP��Ψһ��ֻ������ĸ�����֡�����»���_��@����ǵ��Լ����-��ɣ������ִ�Сд����ͳһСд������ע���Դ˽ӿڷ��ؽ���е�accidΪ׼����
	 * @param faccid �����accid
	 */
	public static String addBuddy(String accid,String faccid){
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid",accid));
		nvps.add(new BasicNameValuePair("faccid",faccid));
		nvps.add(new BasicNameValuePair("type","1"));//1ֱ�ӼӺ��ѣ�2����Ӻ��ѣ�3ͬ��Ӻ��ѣ�4�ܾ��Ӻ���
		return HttpYX.doPost(YXConstant.AddFriends,nvps);
	}
	/**
	 * post����
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url,Object nvps){
		CloseableHttpResponse response=null;
		BufferedReader in=null;
        try {  
        	 //����Ĭ�ϵ�httpclient
            CloseableHttpClient client = HttpClients.createDefault();
            //����post�������
            HttpPost httpPost = new HttpPost(url);
            String appKey = YXConstant.appKey;
            String appSecret = YXConstant.appSecret;
            String nonce = "19970419";
            String curTime = String.valueOf((new Date()).getTime() / 1000L);
            String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//�ο� ����CheckSum��java����
            // ���������header
            httpPost.addHeader("AppKey", appKey);
            httpPost.addHeader("Nonce", nonce);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.setURI(new URI(url));
        	if(nvps instanceof Map) {//���Ϊ map���͵�����
        		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        	    httpPost.setEntity(new StringEntity(JSON.toJSONString(nvps),Charset.forName("UTF-8")));
    		}else if(nvps instanceof java.util.List){
    			 httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
    			//�����������
                httpPost.setEntity(new UrlEncodedFormEntity((List<? extends NameValuePair>) nvps,HTTP.UTF_8)); 
    		}else if(nvps instanceof String){
    			httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
    			httpPost.setEntity(new StringEntity(nvps.toString(),Charset.forName("UTF-8")));
    		}
        	 response = client.execute(httpPost);  
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){	//����ɹ�
            	in = new BufferedReader(new InputStreamReader(response.getEntity()  
                        .getContent(),"utf-8"));
                StringBuffer sb = new StringBuffer("");  
                String line = "";  
                String NL =System.getProperty("line.separator");  
                while ((line = in.readLine()) != null) {  
                    sb.append(line + NL);  
                }           
                return sb.toString();
            }
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
        	if(response!=null){
        		try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}   
		}
        return null;
	}
	 /**
     * httpclient get����
     * 
     * @throws Exception
     */
    public static String doGet(String url) {
    	//System.out.println(url);
        //����һ��httpclient����
        CloseableHttpClient client = HttpClients.createDefault();
        //����httpGet����
        HttpGet hg = new HttpGet(url);
        CloseableHttpResponse response=null;
        try {
            //�������
             response = client.execute(hg);
            //��ȡ��Ӧ��
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                //��ȡ����ʵ��entity
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream content = entity.getContent();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
                    String line = "";
                    StringBuffer sb = new StringBuffer("");  
                    while ((line = bufferedReader.readLine()) != null){
                    	sb.append(line);
                    }
                    bufferedReader.close();
          
                    return sb.toString();
                }
            } 
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			//�ر�response��client
            try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return null;
    }
}
