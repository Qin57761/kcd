package com.controller.icbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.util.HttpClientUtil;
import com.util.httputil;


/**
 * �쳵���ʽ𷽴�������
 * 
 * @author zhaozhaoxin
 *
 */
public class KCDUtil {

	private static final String SUCCESS = "200";//���سɹ����
	private static final String ckey = "8918da17a51cca06cbed868dcaeb669a";
	private static final String orderState_url="http://apitest.kcway.net/orderState.do";
	private static final String icbcIntopieces_url="http://apitest.kcway.net/icbcIntopieces.do";
	private static final String icbcCredit_url = "http://apitest.kcway.net/icbcCredit.do";
	private static final String icbcKaik_url="http://apitest.kcway.net/icbcKaik.do";
	
	private KCDUtil() {
	}

	private static class KCDUtilHandle {
		private static KCDUtil instance = new KCDUtil();
	}

	public static KCDUtil getInstance() {
		return KCDUtilHandle.instance;
	}

	/**
	 * ��������״̬��ѯ�ӿ�
	 * @param orderId
	 */
//	public void orderState(String orderId){
//		Map<String,Object> params = new HashMap<String, Object>();
//		params.put("ckey", ckey);
//		params.put("orderid", orderId);
//		String result = HttpUtil.doGet(orderState_url, params);
//		System.err.println(result);
//	}
//	
	/**
	 * �����ͻ���Ϣ�ύ�ӿ�
	 * @param params
	 */
//	public void icbcIntopieces(String name,String cardid,String phonenumber){
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("ckey", ckey);
//		params.put("bankid", "1");
//		params.put("name", name);
//		params.put("cardid", cardid);
//		params.put("sex","1");
//		params.put("phonenumber", phonenumber);
//		 
//		String result = HttpClientUtil.doPost(icbcIntopieces_url, params,"utf-8");
//		//{"result":"ICBC0000075","code":"200","msg":"�ύ�ɹ�"}
//		System.err.println(result);
//	}
	public void icbcIntopieces() {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httppost = new HttpPost("http://localhost:8080/kcd/icbcIntopieces.do");
			MultipartEntity reqEntity = new MultipartEntity();
			StringBody bankid = new StringBody("1");
			StringBody cardid = new StringBody("213412341");
			StringBody sex = new StringBody("1");
			StringBody phonenumber = new StringBody("123123123");
			StringBody ckey = new StringBody("61325e7a3bebbf076f048a8f7a36905a");
			StringBody name = new StringBody("����");
			reqEntity.addPart("bankid", bankid);
			reqEntity.addPart("cardid", cardid);
			reqEntity.addPart("sex", sex);
			reqEntity.addPart("phonenumber", phonenumber);
			reqEntity.addPart("ckey", ckey);
			reqEntity.addPart("name", name);
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				System.out.println("������������Ӧ.....");
				HttpEntity resEntity = response.getEntity();
				System.out.println(EntityUtils.toString(resEntity));// httpclient�Դ��Ĺ������ȡ��������
				// System.out.println(resEntity.getContent());//�����Ƿ���˵ķ���ֵ
				EntityUtils.consume(resEntity);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
	}
	public void icbcCredit() {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httppost = new HttpPost(icbcCredit_url);
			FileBody cardfront_file = new FileBody(new File("D:/data/kcd_images/cardfront.jpg"));
			FileBody cardopposite_file = new FileBody(new File("D:/data/kcd_images/cardopposite.jpg"));
			FileBody cardbook_file = new FileBody(new File("D:/data/kcd_images/cardbook.jpg"));
			FileBody authorizebook_file = new FileBody(new File("D:/data/kcd_images/authorizebook.jpg"));
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("cardfront", cardfront_file);// ���֤����
			reqEntity.addPart("cardopposite", cardopposite_file);//���֤����
			reqEntity.addPart("authorizebook", authorizebook_file);//��Ȩ��
			reqEntity.addPart("cardbook", cardbook_file);//
			reqEntity.addPart("facebook", cardbook_file);//
			StringBody orderid = new StringBody("ICBC0000075");
			StringBody ck = new StringBody(ckey);
			StringBody businesslevel = new StringBody("1");
			StringBody product = new StringBody("1");
			reqEntity.addPart("orderid", orderid);
			reqEntity.addPart("ckey", ck);
			reqEntity.addPart("businesslevel", businesslevel);
			reqEntity.addPart("product", product);
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				System.out.println("������������Ӧ.....");
				HttpEntity resEntity = response.getEntity();
				System.out.println(EntityUtils.toString(resEntity));// httpclient�Դ��Ĺ������ȡ��������
				// System.out.println(resEntity.getContent());//�����Ƿ���˵ķ���ֵ
				EntityUtils.consume(resEntity);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
	}

	
	public void icbcKaik() {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			FileBody cardfront_file = new FileBody(new File("C:/Users/Administrator/Desktop/test-idcard.jpg"));
			FileBody cardopposite_file = new FileBody(new File("C:/Users/Administrator/Desktop/test-idcard.jpg"));
			FileBody cardbook_file = new FileBody(new File("C:/Users/Administrator/Desktop/test-idcard.jpg"));
			FileBody authorizebook_file = new FileBody(new File("C:/Users/Administrator/Desktop/test-idcard.jpg"));
			StringBody orderid = new StringBody("ICBC0000069");
			StringBody ck = new StringBody("61325e7a3bebbf076f048a8f7a36905a");
			StringBody billingprice = new StringBody("7.00");
			StringBody loanprice = new StringBody("5.00");
			StringBody coverprice = new StringBody("2000");
			StringBody loanallprice = new StringBody("5.20");
			StringBody mortgageterm = new StringBody("1");
			StringBody mortgagemodel = new StringBody("1");
			StringBody mortgagebank = new StringBody("1");			
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("orderid", orderid);
			reqEntity.addPart("ckey", ck);
			reqEntity.addPart("billingprice", billingprice);
			reqEntity.addPart("loanprice", loanprice);
			reqEntity.addPart("coverprice", coverprice);
			reqEntity.addPart("loanallprice", loanallprice);
			reqEntity.addPart("mortgageterm", mortgageterm);
			reqEntity.addPart("mortgagemodel", mortgagemodel);
			reqEntity.addPart("mortgagebank", mortgagebank);			
			reqEntity.addPart("kkcardfront", cardfront_file);// ���֤����
			reqEntity.addPart("kkcardopposite", cardopposite_file);//���֤����
			reqEntity.addPart("creditcard2", authorizebook_file);//
			reqEntity.addPart("creditcard1", cardbook_file);//
			reqEntity.addPart("visainterview", cardbook_file);//
			reqEntity.addPart("sibit", cardbook_file);//
			reqEntity.addPart("doitric", cardbook_file);//			
			HttpPost httppost = new HttpPost("http://localhost:8080/kcd/icbcKaik.do");
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			System.err.println("icbcKaik_url��" + statusCode);
			if (statusCode == HttpStatus.SC_OK) {
				System.out.println("������������Ӧ.....");
				HttpEntity resEntity = response.getEntity();
				System.out.println(EntityUtils.toString(resEntity));// httpclient�Դ��Ĺ������ȡ��������
				// System.out.println(resEntity.getContent());//�����Ƿ���˵ķ���ֵ
				EntityUtils.consume(resEntity);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
	}
	
	public void carpg() {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			FileBody imgs1 = new FileBody(new File("C:/Users/Administrator/Desktop/test-idcard.jpg"));
			FileBody imgs2 = new FileBody(new File("C:/Users/Administrator/Desktop/test-idcard.jpg"));
			FileBody imgs3 = new FileBody(new File("C:/Users/Administrator/Desktop/test-idcard.jpg"));
			FileBody imgs4 = new FileBody(new File("C:/Users/Administrator/Desktop/465.mp4"));
			StringBody orderid = new StringBody("ICBC0000069");
			StringBody ck = new StringBody("61325e7a3bebbf076f048a8f7a36905a");
			StringBody mileage = new StringBody("2");
			StringBody carcolor = new StringBody("��",Charset.forName("UTF-8"));
			StringBody querytype = new StringBody("1");
			StringBody models = new StringBody("2017�µ�",Charset.forName("UTF-8"));
			StringBody oncarddate = new StringBody("2013-12-20");
			StringBody province = new StringBody("����ʡ",Charset.forName("UTF-8"));
			StringBody city = new StringBody("������",Charset.forName("UTF-8"));	
			StringBody vin = new StringBody("fasfasad");	
			
			MultipartEntity reqEntity = new MultipartEntity();			
			reqEntity.addPart("orderid", orderid);
			reqEntity.addPart("ckey", ck);
			reqEntity.addPart("mileage", mileage);
			reqEntity.addPart("carcolor", carcolor);
			reqEntity.addPart("querytype", querytype);
			reqEntity.addPart("models", models);
			reqEntity.addPart("oncarddate", oncarddate);
			reqEntity.addPart("province", province);
			reqEntity.addPart("city", city);			
			reqEntity.addPart("vin", vin);	
			reqEntity.addPart("imgs", imgs1);
			reqEntity.addPart("imgs", imgs2);
			reqEntity.addPart("imgs", imgs3);			
			reqEntity.addPart("imgs", imgs4);
			//System.out.println(carcolor.getContentType());
			//http://localhost:8080/kcd/
			//http://apitest.kcway.net/
			HttpPost httppost = new HttpPost("http://localhost:8080/kcd/carsAssess.do");
			System.out.println(reqEntity.getContentType());
			httppost.setEntity(reqEntity);		
			//httppost.setHeader("Content-Type","charset=UTF-8");
			//httppost.setHeader("Content-Type", "text/plain; charset=UTF-8");
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			System.err.println("icbcKaik_url��" + statusCode);
			if (statusCode == HttpStatus.SC_OK) {
				System.out.println("������������Ӧ.....");
				HttpEntity resEntity = response.getEntity();
				System.out.println(EntityUtils.toString(resEntity));// httpclient�Դ��Ĺ������ȡ��������
				System.out.println(reqEntity.getContentEncoding());//�����Ƿ���˵ķ���ֵ
				EntityUtils.consume(resEntity);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
	}
	public String getDocumentAt(String urlString) {
		//�˷�������HTTP��FTPЭ��
		    StringBuffer document = new StringBuffer();
		    try {
		      URL url = new URL(urlString);
		      URLConnection conn = url.openConnection();
		      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.
		          getInputStream()));
		      String line = null;
		      while ( (line = reader.readLine()) != null) {
		        document.append(line + "\n");
		      }
		      reader.close();
		    }
		    catch (MalformedURLException e) {
		      System.out.println("Unable to connect to URL: " + urlString);
		    }
		    catch (IOException e) {
		      System.out.println("IOException when connecting to URL: " + urlString);
		    }
		    return document.toString();
		  }
	
	public static void main(String[] args) {
		
		// ICBC0000072  ICBC0000075
//		getInstance().orderState("ICBC0000075");
		
		String name = "����Ȼ";
		String cardid = "421002197612211495";
		String phonenumber = "13795363735";
//		getInstance().icbcIntopieces(name, cardid, phonenumber);
		//getInstance().icbcIntopieces(name, cardid, phonenumber);
		//getInstance().icbcIntopieces();
		getInstance().getDocumentAt("http://apitest.kcway.net/image/upload/img");
		//getInstance().carpg();
	}
}
