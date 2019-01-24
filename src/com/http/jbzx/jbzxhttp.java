package com.http.jbzx;
/*
 * ��ѩ��   ������� API ע��ӿ�
 */
import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class jbzxhttp {
	//public $appkey = "6602e77a-c286-a0b9-13a6-287f438b3336";
   /*
    * �������ע��1
    * 
    * 
    * 
    */
	//private String appKey="6602e77a-c286-a0b9-13a6-287f438b3336";
	public static String jbzxzc1(String cardno,
    		String name,
    		String appKey) throws IOException
    {
		String url="http://taifinance.cn/TaiAPI/api/creditReference/doUserCheck";
        CloseableHttpClient client = HttpClients.createDefault();

        HttpUriRequest request = RequestBuilder.post()//get����
                .setUri(url)
                //.addHeader(APIX_KEY, apixKey)
                .addParameter("cardno", cardno)//���֤��
                .addParameter("name", name)//����
                .addParameter("appKey", appKey)//��֤key ֵ
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());

    }	
    /*
     * ע��2  ��ȡ������֤��
     * 
     */
    public static String jbzxzc2(
    		String username,//ע����
    		String phoneNo,//�ֻ���
    		String cardno,//���֤���ֶ�
    		String appKey//�û�����
    		) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl2 = "http://taifinance.cn/TaiAPI/api/creditReference/getRegSmsCode";

        HttpUriRequest request = RequestBuilder.post()//get����
                .setUri(reqUrl2)
                //.addHeader(APIX_KEY, apixKey)
                .addParameter("username", username)//�û�����
                .addParameter("phoneNo" , phoneNo)//�ֻ���
                .addParameter("cardno",cardno)//�û����֤����
                .addParameter("appKey" ,appKey)//�û�����
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }   	
    /*
     *ע��3   ��¼��  ��������	
     * 
     */
    public static String jbzxzc3(String username,
    		String cardno,//���֤���ֶ�
    		String phoneNo,//�ֻ���
    		String loginName,//��¼��
    		String passwd,//����
    		String smsCode,//������֤��
    		String appKey//�û�����
    		) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://taifinance.cn/TaiAPI/api/creditReference/regUserInfo";

        HttpUriRequest request = RequestBuilder.post()//get����
                .setUri(reqUrl)
                //.addHeader(APIX_KEY, apixKey)
                .addParameter("cardno",cardno)//���֤���ֶ�
                .addParameter("phoneNo", phoneNo)//�ֻ���
                .addParameter("loginName" , loginName)//��¼��
                .addParameter("passwd" , passwd)//����
                .addParameter("smsCode" ,smsCode)//������֤��
                .addParameter("appKey" ,appKey)//�û�����
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }
    
    //�����Ƿ�ɹ�
	public static void main(String[] args) throws InterruptedException {
    	String cardno="411403199512108410";
		String username="����ǿ";
		String appKey="6602e77a-c286-a0b9-13a6-287f438b3336";
		String phoneNo="13162809518";
		String loginName="lhr13162809518";
		String passwd="lhr13162809518";
		String smsCode="7a93fr";
		
	   //ע��1����
		String s1;
		
		try {
			//8906
			s1 = jbzxzc1(cardno,username,appKey);
			System.out.println(s1);
		
		
//		for(int i=0;i<10;i++){
//			Thread.sleep(1000);
//		System.out.println("�ȴ�ʱ�䣺"+(i+1)+"s");
//		}		
		//ע��2����  9005
//		String s2=	jbzxzc2(username, phoneNo, cardno, appKey);
//		System.out.println(s2);
		
//		
////�ָ���---------------------------------------------------------------------------------------------------------		
		//ע��3����(����ִ��)
//		String s3=	jbzxzc3(username, cardno, phoneNo, loginName, passwd, smsCode, appKey);
//		System.out.println(s3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}