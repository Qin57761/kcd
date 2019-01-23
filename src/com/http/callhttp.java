package com.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * ��Ӫ�̽�������ʾ��
 * Created by chris on 2016/11/14.
 */
public class callhttp
{
    private final static String APIX_KEY = "apix-key";

    private static String apixKey = "74b97d95b0864246709347458bd5306e";
    
    

   
    
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
    public static String geth5(String reurl,
			String successurl,
			String errurl,
			String name,
			String cardnum,
			String contlist,
			String showbar,
			String phoneno) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://e.apix.cn/apixanalysis/mobile/yys/phone/carrier/page";

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(reqUrl)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("callback_url", reurl)//�ص�url
                .addParameter("success_url" ,successurl)//ҳ����Ȩ�ɹ�url
                .addParameter("failed_url",errurl)//ҳ����Ȩʧ��url
                .addParameter("name" ,name)//�Ǽ�������
                .addParameter("cert_no",cardnum)//�Ǽ������֤��
                .addParameter("contact_list" ,contlist)//�û���ϵ��
                .addParameter("show_nav_bar",showbar)//�Ƿ���ʾh5ҳ�浼������true��ʾ��false ����ʾ��Ĭ�ϲ���Ϊ true����ʾ�������� 
                .addParameter("phone_no" ,phoneno)//�޶����ֻ��ţ��Ǳ��������д�˲� �����û��Լ������ֻ��ţ���д�˲��� ��ֻ����Ȩ���ֻ��ţ��û����ɱ༭�� 
                .build();

        CloseableHttpResponse response = client.execute(request);
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * ��ȡͼƬ��֤��
     * @param phoneNo �ֻ�����
     * @return ��Ӧ����
     * @throws IOException
     */
    public static String getCapcha(String phoneno,String ckey) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://apitest.kcway.net/getcapcha.do";

        HttpUriRequest request = RequestBuilder.post()//get����
                .setUri(reqUrl)
                //.addHeader(APIX_KEY, apixKey)
                .addParameter("phoneno", phoneno)//�ֻ���
                .addParameter("ckey" , "ckey")//�û�����
//                .addParameter("cert_no", "")//�û����֤����
//                .addParameter("contact_list" , "")//��ϵ��
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }

    /**
     * ��¼
     * @param phoneNo �ֻ�����
     * @param passwd ����
     * @param capcha ͼƬ��֤��
     * @param callback �ص���ַ
     * @return ��Ӧ����
     * @throws IOException
     */
    public static String login(String phoneNo, String passwd, String capcha, String callback) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://apitest.kcway.net/authorizelogin.do";

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(reqUrl)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("phone_no", phoneNo)//�ֻ���
                .addParameter("passwd" , passwd)//��������
                .addParameter("capcha", capcha)//��֤��
                .addParameter("callback" , "")//�ص���ַ
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }

    /**
     * ��ȡ������֤��
     * @param phoneNo �ֻ�����
     * @return ��Ӧ����
     * @throws IOException
     */
    public static String getSmsCode(String phoneNo) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://apitest.kcway.net/getsmscode.do";

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(reqUrl)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("phone_no", phoneNo)//�ֻ���
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }


    /**
     * ������֤��У��
     * @param phoneNo �ֻ�����
     * @param smsCode ������֤��
     * @param name ���������������������Ҫ��
     * @param certNo ���֤���루���������������Ҫ��
     * @param capcha ͼƬ��֤�루�����ƶ���Ҫ��
     * @return
     * @throws IOException
     */
    public static String verifySmsCode(String phoneNo, String smsCode, String name, String certNo, String capcha) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        //��ȡ��֤��ӿڵ�ַ
        String reqUrl = "http://apitest.kcway.net/verifysmscode.do";

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(reqUrl)
                .addHeader(APIX_KEY, apixKey)
                .addParameter("phone_no", phoneNo)//�ֻ���
                .addParameter("sms_code", smsCode)//������֤��
                .addParameter("name", name)//�û�������������������ű����ύ�˲���
                .addParameter("cert_no", certNo)//���֤���룬������������ű����ύ�˲���
                .addParameter("capcha", capcha)//ͼƬ��֤�� �����ƶ���Ҫ�˲���
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());
    }

    /**
     * ��ȡ����
     * @param url �ӿڵ�ַ
     * @param token ��ѯ��
     * @return
     * @throws IOException
     */
	public static String getData(String url , String token) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpUriRequest request = RequestBuilder.get()//get����
                .setUri(url)
                //.addHeader(APIX_KEY, apixKey)
                .addParameter("query_code", token)//ͼƬ��֤�� �����ƶ���Ҫ�˲���
                .build();

        CloseableHttpResponse response = client.execute(request);

        return EntityUtils.toString(response.getEntity());

    }

    public static void main(String[] args) throws IOException, InterruptedException, JSONException
    {
        //��ʼ�������
        String phoneNo = "17612161642";//�ֻ�����
        String passwd = "110990";//����

        //�Ǳ������
        String callback = "";//�ص���ַ

        //������������
        String name = "";
        String certNo = "";
        String capcha = "";
        String smsCode = "";
        String ckey = "04D5C8B3257FE2A268326A4B5F0BC2D2";
        //��һ�� ��ȡ��¼��֤��
        String resp = getCapcha(phoneNo,ckey);

        JSONObject json = new JSONObject(resp);
        String retCode = json.getString("errorCode");
        System.out.println(retCode);
//        if(StringUtils.equals(retCode, "0"))
//        {
//            String codeType = json.getString("imgUrl");
//            if(StringUtils.isBlank(codeType))
//            {
//                System.out.println("��¼����Ҫ��֤��");
//            }
//            else if(StringUtils.equals("smsCode", codeType))
//            {
//                System.out.println("�������յ��Ķ�����֤�룺");
//                capcha = new Scanner(System.in).next();
//            }
//            else
//            {
//                String imgUrl = json.getString("imgUrl");
//                System.out.println(String.format("��ʶ�𷵻ص�ͼƬ��֤�룬��֤���ַΪ[%s]��", imgUrl));
//                capcha = new Scanner(System.in).next();
//            }
//        }
//        else
//        {
//            System.out.println(String.format("����ʧ�ܣ� | [%s]", json.getString("errorMsg")));
//        }
//
//        //�ڶ��� ��¼
//        resp = login(phoneNo, passwd, capcha, callback);
//
//        json = new JSONObject(resp);
//        retCode = json.getString("errorCode");
//        if(StringUtils.equals(retCode, "0"))
//        {
//            Boolean isFinish = json.getBoolean("result");
//            if(isFinish)
//            {
//                String token = json.getString("token");
//                System.out.println(String.format("��ϲ����Ȩ��ɣ� | ��ȨtokenΪ[%s]", token));
//                System.exit(0);
//            }
//            else
//            {
//                System.out.println("��Ȩ��δ��������Ҫִ�е����Ĳ����Եȣ�");
//            }
//        }
//        else
//        {
//            System.out.println(String.format("����ʧ�ܣ� | [%s]", json.getString("errorMsg")));
//        }
//
//        String operator = json.getString("operator");
//
//        if(StringUtils.contains(operator, "�ƶ�"))
//        {
//            //��Ҫ�ȴ�60�룬�ſɻ�ȡ������֤��
//            Thread.sleep(50000);
//        }
//
//        //������ ��ȡ������֤��
//        resp = getSmsCode(phoneNo);
//
//        json = new JSONObject(resp);
//        retCode = json.getString("errorCode");
//
//        if(StringUtils.equals(retCode, "0"))
//        {
//            if(StringUtils.contains(operator, "����"))
//            {
//                if(StringUtils.contains(operator, "����")  || StringUtils.contains(operator, "����") )
//                {
//                    System.out.println("�������û�����:");
//                    name = new Scanner(System.in).next();
//                    System.out.println("�������û����֤����:");
//                    certNo = new Scanner(System.in).next();
//
//                }
//            }
//            else if(StringUtils.contains(operator, "�ƶ�"))
//            {
//                if(StringUtils.contains(operator, "����"))
//                {
//                    String imgUrl = json.getString("imgUrl");
//                    System.out.println(String.format("��ʶ�𷵻ص�ͼƬ��֤�룬��֤���ַΪ[%s]��", imgUrl));
//                    capcha = new Scanner(System.in).next();
//                } 
//            }
//
//            System.out.println("�������ȡ���Ķ�����֤��:");
//            smsCode = new Scanner(System.in).next();
//        }
//        else
//        {
//            System.out.println(String.format("����ʧ�ܣ� | [%s]", json.getString("errorMsg")));
//        }
//
//        //���Ĳ� ������֤��У��
//        resp = verifySmsCode(phoneNo, smsCode, name, certNo, capcha);
//        json = new JSONObject(resp);
//        retCode = json.getString("errorCode");
//
//        if(StringUtils.equals(retCode, "0"))
//        {
//            String token = json.getString("token");
//            System.out.println(String.format("��ϲ����Ȩ��ɣ� | ��ȨtokenΪ[%s]", token));
//            System.exit(0);
//        }
//        else
//        {
//            System.out.println(String.format("����ʧ�ܣ� | [%s]", json.getString("errorMsg")));
//        }

        //���岽 ��ȡ����
        //String token = "";//��ȡ����token
        //String reqUrl = "http://e.apix.cn/apixanalysis/mobile/retrieve/phone/data/; //��ȡ��������url
        //String reqUrl = "http://e.apix.cn/apixanalysis/mobile/retrieve/phone/data/query";//��ȡԭʼ����url
        //String retData = getData(reqUrl, token);//��ȡ��������
    }




}

