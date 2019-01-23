package com.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpStatus;


public class saassfzsbhttp {
	

	
	public static void main(String[] args) throws IOException {
//		//���������õ�ַ
//		protected $apiurl = 'http://www.yunmaiocr.com/SrvXMLAPI';	
//		//�û���
//		protected $user = '8f0854fe-9fc1-4fec-a68a-91aa9e37c8a0';	
//		//����
//		protected $pwd = 'qIMwArUHEMjjGjjtoJyHctuLiThJWz';
		System.out.println(">> start demo......");
		//���ӣ�
		//�Ѳ���ͼƬ�ŵ�D�����棬Ҳ���Էŵ���ĵط�������Ҫ����filePath��Ӧ������·��
		String filePath="C:/Users/Administrator/Desktop/2/test-idcard.JPG";//����ͼƬ����·��

		File file=null;
		try{
			file=new File(filePath);//Ҫʶ����ļ�·��
		}catch(Exception e){
			System.out.print(">> �ļ�����ʧ�ܣ�");
			return;
		}
		String result = "123";
		result=Scan(file2byte(file),filePath.substring(filePath.lastIndexOf(".")+1));//��������ӿ�ʶ��
		System.out.print(result);//��ӡ��ʶ����
		System.out.println(">> end demo.");

	}


	//ʶ��API��URL
	public static final String ENGINE_URL="http://www.yunmaiocr.com/SrvXMLAPI";
	public static final String username="8f0854fe-9fc1-4fec-a68a-91aa9e37c8a0";//�滻OCR SDK������ƽ̨API�ʺ�
	public static final String password="qIMwArUHEMjjGjjtoJyHctuLiThJWz";//�滻OCR SDK������ƽ̨API����

	public static String Scan(byte[] file,String ext){
		String xml = getSendXML( "idcard.scan",ext);
		return send(xml,file);
	}

	private static String getSendXML(String action,String ext) {
		ArrayList<String[]> arr = new ArrayList<String[]>();
		arr.add(new String[] { "action", action});
		arr.add(new String[] { "client", username });
		arr.add(new String[] { "system", "web_saas"});
		arr.add(new String[] { "password", MD5(password)});
		arr.add(new String[] { "ext",ext });
		arr.add(new String[] { "er","0" });//1:����ʾ���꣬Ĭ��Ϊ1��0����ʾ����
		arr.add(new String[] { "header","0" });//����ͷ��ͼƬ����Ĭ��Ϊ0����Ҫ��1����Ҫͷ���ļ���
		arr.add(new String[] { "json", "1" });//���ؽ���Ƿ�ת��json��ʽ��������Ĭ����xml��ʽ��Ϊ1ʱ��ת����json��ʽ
		return getXML(arr,false);
	}
	public static String getXML(ArrayList<String[]> arr,boolean IsUpper) {
		if (arr == null || arr.size() == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		String tag="";
		for (int idx = 0; idx < arr.size(); idx++) {
			tag=arr.get(idx)[0];
			if(IsUpper){
				tag=tag.toUpperCase();
			}
			sb.append("<");
			sb.append(tag);
			sb.append(">");
			sb.append(arr.get(idx)[1]);
			//sb.append(XMLFunctions.code(arr.get(idx)[1]));
			sb.append("</");
			sb.append(tag);
			sb.append(">");
		}
		return sb.toString();
	}
	private static String send(String xml,byte[] file){
		byte[] dest = new byte[xml.getBytes().length+file.length+"<file></file>".getBytes().length];
		int pos = 0;
		System.arraycopy(xml.getBytes(), 0, dest, pos, xml.getBytes().length);
		pos += xml.getBytes().length;
		System.arraycopy("<file>".getBytes(), 0, dest, pos, "<file>".getBytes().length);
		pos += "<file>".getBytes().length;
		System.arraycopy(file, 0, dest, pos, file.length);
		pos += file.length;
		System.arraycopy("</file>".getBytes(), 0, dest, pos, "</file>".getBytes().length);
		try {
			return httpClient(ENGINE_URL, dest);
		} catch (IOException e) {
			return "-1";
		}
	}
	public static String httpClient(String url,byte[] content) throws IOException{
		HttpClient httpClient = new HttpClient();
		HttpClientParams httparams = new HttpClientParams();
		httpClient.setParams(httparams);

		PostMethod method = new PostMethod(url);
		RequestEntity requestEntity = new ByteArrayRequestEntity(content);
		method.setRequestEntity(requestEntity);
		String responseBody = null;
		try {
			method.getParams().setContentCharset("utf-8");
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("\r\nMethod failed: " + method.getStatusLine() + ",url:\r\n" + url + "\r\n");
			}
			StringBuffer resultBuffer = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),
					method.getResponseCharSet()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				resultBuffer.append(inputLine);
				resultBuffer.append("\r\n");
			}
			in.close();
			responseBody = resultBuffer.toString().trim();
		} catch (Exception e) {
			System.out.println(">>> http�����쳣��url=" + url);
			e.printStackTrace();
			responseBody = "-2";
		} finally {
			if (method != null) {
				method.releaseConnection();
				method = null;
			}
			return responseBody;
		}

	}
	public static byte[] file2byte(File file) throws IOException {
		byte[] bytes = null;
		if (file != null) {
			InputStream is = new FileInputStream(file);
			int length = (int) file.length();
			if (length > Integer.MAX_VALUE) // ���ļ��ĳ��ȳ�����int�����ֵ
			{
				System.out.println("this file is max ");
				return null;
			}
			bytes = new byte[length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			// ����õ����ֽڳ��Ⱥ�fileʵ�ʵĳ��Ȳ�һ�¾Ϳ��ܳ�����
			if (offset < bytes.length) {
				System.out.println("file length is error");
				return null;
			}
			is.close();
		}
		return bytes;
	}
	public final static String MD5(String pwd) {
		//���ڼ��ܵ��ַ�
		char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = pwd.getBytes();
			
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {   //  i = 0
				byte byte0 = md[i];  //95
				str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5  
				str[k++] = md5String[byte0 & 0xf];   //   F
			}
			return new String(str);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	
	
	
	
	
	
	
}
