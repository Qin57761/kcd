package com.http.dyapi;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class dyapihttp {
	
    //ͬ��������Ϣ
	public static String kjsflxxhttp(
            String id,
			String url
			) throws IOException
    {
//		String url="http://localhost:8080/kcd/kjs_send.do";
        CloseableHttpClient client = HttpClients.createDefault();
      //�������1
        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(url)
                .addParameter("id",id)
                .build();
        CloseableHttpResponse response = client.execute(request);
        return EntityUtils.toString(response.getEntity());

    }
    //����˶�����Ϣ
	public static String kjsjkrxxhttp(
            String id,
			String kjs_type,
			String url
			) throws IOException
    {
//		String url="http://localhost:8080/kcd/kjs_send.do";
        CloseableHttpClient client = HttpClients.createDefault();
      //�������1
        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(url)
                .addParameter("id",id)
                .addParameter("kjs_type",kjs_type)
                .build();
        CloseableHttpResponse response = client.execute(request);
        return EntityUtils.toString(response.getEntity());

    }
	
	//�������ϸ��Ϣ
	public static String kjsdyapiGet(String id,
			String kjs_type,
			String time,
			String sign,
			String url
			) throws IOException
    {
//		String url="http://localhost:8080/kcd/kjs_send.do";
        CloseableHttpClient client = HttpClients.createDefault();
      //�������1
        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(url)
                .addParameter("id", id)
                .addParameter("kjs_type", kjs_type)
                .addParameter("time",time)
                .addParameter("sign",sign)
                .build();
        CloseableHttpResponse response = client.execute(request);
        return EntityUtils.toString(response.getEntity());
    } 
}
