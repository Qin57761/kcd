package com.http.jbzx;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class jbzxaqwthttp {
	
	
	 //������Ű�ȫ����1
	public static String jbzxaq1(String loginName,
    		String passwd,
    		String appKey) throws IOException
    {
		String url="http://taifinance.cn/TaiAPI/api/creditReference/getQuestions";
        CloseableHttpClient client = HttpClients.createDefault();
      //�������1
          //System.out.println(loginName +"---"+passwd+"----"+appKey);
        HttpUriRequest request = RequestBuilder.post()//get����
                .setUri(url)
                //.addHeader(APIX_KEY, apixKey)
                .addParameter("loginName", loginName)// ��¼����
                .addParameter("passwd", passwd)//��¼����
                .addParameter("appKey", appKey)//�û�����
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());

    }	
	    //������Ű�ȫ����2
		public static String jbzxaq2(
				String loginName,
	    		String password,
	    		String qFirst,
	    		String qSecond,
	    		String qThird,
	    		String qFouth,
	    		String qFivth,
	    		String appKey) throws IOException
		
	    {
			//�������2
			//System.out.println(loginName+"---"+password +"---"+qFirst+"----"+qSecond+"---"+qThird+"---"+qFouth+"---"+qFivth+"---"+appKey);
	        CloseableHttpClient client = HttpClients.createDefault();
	      //��ȡ��֤��ӿڵ�ַ
	        String url="http://taifinance.cn/TaiAPI/api/creditReference/submitAnswer";
	        HttpUriRequest request = RequestBuilder.post()//get����
	                .setUri(url)
	                //.addHeader(APIX_KEY, apixKey)
	                .addParameter("loginName",loginName)// ��¼����
	                .addParameter("password",password)//��¼����
	                .addParameter("qFirst",qFirst)//����1
	                .addParameter("qSecond",qSecond)//����2
	                .addParameter("qThird",qThird)//����3
	                .addParameter("qFouth",qFouth)//����4
	                .addParameter("qFivth",qFivth)//����5
	                .addParameter("appKey",appKey)//�û�����
	                .build();

	        CloseableHttpResponse response = client.execute(request);

	        return EntityUtils.toString(response.getEntity());

	    }	
	 
	    //������Ű�ȫ����3
		public static String jbzxaq3(String loginName,
	    		String password,
	    		String code,
	    		String appKey) throws IOException
	    {
			String url="http://taifinance.cn/TaiAPI/api/creditReference/getPersonalCredit";
	        CloseableHttpClient client = HttpClients.createDefault();
	      //�������3
	         System.out.println(loginName +"---"+password+"----"+code+"---"+appKey);
	        HttpUriRequest request = RequestBuilder.post()//get����
	                .setUri(url)
	                //.addHeader(APIX_KEY, apixKey)
	                .addParameter("loginName", loginName)// ��¼����
	                .addParameter("password", password)//��¼����
	                .addParameter("code", code)//������֤��
	                .addParameter("appKey", appKey)//�û�����
	                .build();

	        CloseableHttpResponse response = client.execute(request);

	        return EntityUtils.toString(response.getEntity());
	    }	
		
  public static void main(String[] args) {
	//��ȫ������Բ���
		String loginName="lhr13162809518";
		String passwd="lhr13162809518";
	  	String appKey="6602e77a-c286-a0b9-13a6-287f438b3336";
		String qFirst="���϶�����";
		String qSecond="���϶�����";
		String qThird ="���϶�����";
		String qFouth ="δ��";
		String qFivth="����ʡ������";
		String code="58790";
	//��ȫ�������1
	String c1;
	try {		
//		c1 = jbzxaq1(loginName,passwd,appKey);
//		  System.out.println(c1);
	
/*		for(int i=0;i<10;i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("�ȴ�ʱ�䣺"+(i+1)+"s");
		}*/		
				
	//��ȫ�������2
//	   String c2=jbzxaq2(loginName,passwd,qFirst,qSecond,qThird,qFouth,qFivth,appKey);
//	   System.out.println(c2);
			
		//c2 = jbzxaq2(loginName, password, qFirst, qSecond,qThird,qFouth,qFivth,appKey);
				
	 //��ȫ�������3
	   String c3=jbzxaq3(loginName,passwd,code,appKey);
	   		System.out.println(c3);
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	}
  }





