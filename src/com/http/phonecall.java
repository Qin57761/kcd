package com.http;
///**  ������
// *   <dependency>
// <groupId>org.apache.httpcomponents</groupId>
// <artifactId>httpcore</artifactId>
// <version>4.4.4</version>
// </dependency>
//
// <dependency>
// <groupId>org.apache.httpcomponents</groupId>
// <artifactId>httpclient</artifactId>
// <version>4.5.2</version>
// </dependency>
//
// <dependency>
// <groupId>org.apache.httpcomponents</groupId>
// <artifactId>httpmime</artifactId>
// <version>4.5.2</version>
// </dependency>
//
// <dependency>
// <groupId>org.json</groupId>
// <artifactId>json</artifactId>
// <version>20160212</version>
// </dependency>
// *
// */
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

/**
 * ��Ӫ�̽�������ʾ��
 * Created by chris on 2016/11/14.
 */
public class phonecall
{
    private final static String APIX_KEY = "apix-key";

    private static String apixKey = "74b97d95b0864246709347458bd5306e";
    
    

    /**
     * ���ݲ�ѯ 1������ token ��ѯ��������
     * @param url �ӿڵ�ַ
     * @param token ��ѯ��
     * @return
     * @throws IOException
     */
	public static String analyzedData(String token) throws IOException
    {
		String url="http://e.apix.cn/apixanalysis/mobile/retrieve/phone/data/analyzed";
        CloseableHttpClient client = HttpClients.createDefault();

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(url)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("query_code", token)//ͼƬ��֤�� �����ƶ���Ҫ�˲���
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());

    }
    
	 /**
     * ���ݲ�ѯ 2������ token ��ѯԭʼ���� 
     * @param url �ӿڵ�ַ
     * @param token ��ѯ��
     * @return
     * @throws IOException
     */
	public static String queryData(String token) throws IOException
    {
		 String url="http://e.apix.cn/apixanalysis/mobile/retrieve/phone/data/query";
        CloseableHttpClient client = HttpClients.createDefault();
  
        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(url)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("query_code", token)//ͼƬ��֤�� �����ƶ���Ҫ�˲���
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());

    }
    /**
     * ��ȡH5ҳ����Ϣ
     * @param reurl
     * @param successurl
     * @param errurl
     * @param name
     * @param cardnum
     * @param contlist
     * @param showbar
     * @param phoneno
     * @return
     * @throws IOException
     */
    public static String geth5(String callback_url,
			String success_url,
			String failed_url,
			String name,
			String cert_no,
			String contact_list,
			String show_nav_bar,
			String phone_no) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://e.apix.cn/apixanalysis/mobile/yys/phone/carrier/page";

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(reqUrl)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("callback_url", callback_url)//�ص�url
                .addParameter("success_url" ,success_url)//ҳ����Ȩ�ɹ�url
                .addParameter("failed_url",failed_url)//ҳ����Ȩʧ��url
                .addParameter("name" ,name)//�Ǽ�������
                .addParameter("cert_no",cert_no)//�Ǽ������֤��
                .addParameter("contact_list" ,contact_list)//�û���ϵ��
                .addParameter("show_nav_bar",show_nav_bar)//�Ƿ���ʾh5ҳ�浼������true��ʾ��false ����ʾ��Ĭ�ϲ���Ϊ true����ʾ�������� 
                .addParameter("phone_no" ,phone_no)//�޶����ֻ��ţ��Ǳ��������д�˲� �����û��Լ������ֻ��ţ���д�˲��� ��ֻ����Ȩ���ֻ��ţ��û����ɱ༭�� 
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }

    /**
     * ��ȡ��Ӫ����֤�� 
     * @param phoneNo �ֻ�����
     * @return ��Ӧ����
     * @throws IOException
     */
    public static String getCapcha(String phone_no,
    		String name,
    		String cert_no,
    		String contact_list) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://e.apix.cn/apixanalysis/mobile/yys/phone/capcha";

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(reqUrl)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("phone_no", phone_no)//�ֻ���
                .addParameter("name" , name)//�û�����
                .addParameter("cert_no",cert_no)//�û����֤����
                .addParameter("contact_list" ,contact_list)//��ϵ��
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }

    /**
     * ��Ӫ��������Ȩ��½ 
     * @param phoneNo �ֻ�����
     * @param passwd ����
     * @param capcha ͼƬ��֤��
     * @param callback �ص���ַ
     * @return ��Ӧ����
     * @throws IOException
     */
    public static String login(String phone_no, String passwd, String capcha, String callback) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://e.apix.cn/apixanalysis/mobile/yys/phone/login";

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(reqUrl)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("phone_no", phone_no)//�ֻ���
                .addParameter("passwd" , passwd)//��������
                .addParameter("capcha", capcha)//��֤��
                .addParameter("callback" ,callback)//�ص���ַ
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }
    
    /**
     * ��ȡ�굥ͼƬ��֤�� 
     * @param phoneNo
     * @return
     * @throws IOException
     */
    public static String image(String phone_no) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://e.apix.cn/apixanalysis/mobile/yys/phone/third/image";

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(reqUrl)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("phone_no", phone_no)//�ֻ���
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }
    

    /**
     * ���������֤����Ȩ
     * @param phoneNo �ֻ�����
     * @return ��Ӧ����
     * @throws IOException
     */
    public static String getSmsCode(String phone_no) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://e.apix.cn/apixanalysis/mobile/yys/phone/smsCode/getSms";

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(reqUrl)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("phone_no", phone_no)//�ֻ���
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }


    /**
     * �굥������֤����֤
     * @param phoneNo �ֻ�����
     * @param smsCode ������֤��
     * @param name ���������������������Ҫ��
     * @param certNo ���֤���루���������������Ҫ��
     * @param capcha ͼƬ��֤�루�����ƶ���Ҫ��
     * @return
     * @throws IOException
     */
    public static String verifySmsCode(String phone_no,
    		String sms_code,
    		String name,
    		String cert_no, 
    		String capcha) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://e.apix.cn/apixanalysis/mobile/yys/phone/smsCode/verify";

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(reqUrl)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("phone_no", phone_no)//�ֻ���
                .addParameter("sms_code", sms_code)//������֤��
                .addParameter("name", name)//�û�������������������ű����ύ�˲���
                .addParameter("cert_no", cert_no)//���֤���룬������������ű����ύ�˲���
                .addParameter("capcha", capcha)//ͼƬ��֤�� �����ƶ���Ҫ�˲���
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }



    public static void main(String[] args) throws IOException, InterruptedException, JSONException
    {
        //��ʼ�������
        String phoneNo = "15093111875";//�ֻ�����
        String passwd = "110990";//����

        //�Ǳ������
        String callback = "";//�ص���ַ

        //������������
        String name = "";
        String certNo = "";
        String capcha = "";
        String smsCode = "";

        //��һ�� ��ȡ��¼��֤��
        String resp = getCapcha(phoneNo, name, name, name);

        JSONObject json = new JSONObject(resp);
        String retCode = json.getString("errorCode");
        System.out.println(retCode);
        if(StringUtils.equals(retCode, "0"))
        {
            String codeType = json.getString("imgUrl");
            if(StringUtils.isBlank(codeType))
            {
                System.out.println("��¼����Ҫ��֤��");
            }
            else if(StringUtils.equals("smsCode", codeType))
            {
                System.out.println("�������յ��Ķ�����֤�룺");
                capcha = new Scanner(System.in).next();
            }
            else
            {
                String imgUrl = json.getString("imgUrl");
                System.out.println(String.format("��ʶ�𷵻ص�ͼƬ��֤�룬��֤���ַΪ[%s]��", imgUrl));
                capcha = new Scanner(System.in).next();
            }
        }
        else
        {
            System.out.println(String.format("����ʧ�ܣ� | [%s]", json.getString("errorMsg")));
        }

        //�ڶ��� ��¼
        resp = login(phoneNo, passwd, capcha, callback);

        json = new JSONObject(resp);
        retCode = json.getString("errorCode");
        if(StringUtils.equals(retCode, "0"))
        {
            Boolean isFinish = json.getBoolean("result");
            if(isFinish)
            {
                String token = json.getString("token");
                System.out.println(String.format("��ϲ����Ȩ��ɣ� | ��ȨtokenΪ[%s]", token));
                System.exit(0);
            }
            else
            {
                System.out.println("��Ȩ��δ��������Ҫִ�е����Ĳ����Եȣ�");
            }
        }
        else
        {
            System.out.println(String.format("����ʧ�ܣ� | [%s]", json.getString("errorMsg")));
        }

        String operator = json.getString("operator");

        if(StringUtils.contains(operator, "�ƶ�"))
        {
            //��Ҫ�ȴ�60�룬�ſɻ�ȡ������֤��
            Thread.sleep(50000);
        }

        //������ ��ȡ������֤��
        resp = getSmsCode(phoneNo);

        json = new JSONObject(resp);
        retCode = json.getString("errorCode");

        if(StringUtils.equals(retCode, "0"))
        {
            if(StringUtils.contains(operator, "����"))
            {
                if(StringUtils.contains(operator, "����")  || StringUtils.contains(operator, "����") )
                {
                    System.out.println("�������û�����:");
                    name = new Scanner(System.in).next();
                    System.out.println("�������û����֤����:");
                    certNo = new Scanner(System.in).next();

                }
            }
            else if(StringUtils.contains(operator, "�ƶ�"))
            {
                if(StringUtils.contains(operator, "����"))
                {
                    String imgUrl = json.getString("imgUrl");
                    System.out.println(String.format("��ʶ�𷵻ص�ͼƬ��֤�룬��֤���ַΪ[%s]��", imgUrl));
                    capcha = new Scanner(System.in).next();
                } 
            }

            System.out.println("�������ȡ���Ķ�����֤��:");
            smsCode = new Scanner(System.in).next();
        }
        else
        {
            System.out.println(String.format("����ʧ�ܣ� | [%s]", json.getString("errorMsg")));
        }

        //���Ĳ� ������֤��У��
        resp = verifySmsCode(phoneNo, smsCode, name, certNo, capcha);
        json = new JSONObject(resp);
        retCode = json.getString("errorCode");

        if(StringUtils.equals(retCode, "0"))
        {
            String token = json.getString("token");
            System.out.println(String.format("��ϲ����Ȩ��ɣ� | ��ȨtokenΪ[%s]", token));
            System.exit(0);
        }
        else
        {
            System.out.println(String.format("����ʧ�ܣ� | [%s]", json.getString("errorMsg")));
        }

        //���岽 ��ȡ����
        //String token = "";//��ȡ����token
        //String reqUrl = "http://e.apix.cn/apixanalysis/mobile/retrieve/phone/data/; //��ȡ��������url
        //String reqUrl = "http://e.apix.cn/apixanalysis/mobile/retrieve/phone/data/query";//��ȡԭʼ����url
        //String retData = getData(reqUrl, token);//��ȡ��������
    }




}

