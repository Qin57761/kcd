package com.util.url;

import java.net.HttpURLConnection;
import java.net.URL;

/**
* �ļ�����Ϊ��URLAvailability.java
* �ļ����ܼ����� ����һ��URL��ַ�Ƿ���Ч
* @author Jason
* @time   2010-9-14 
* 
*/
public class URLAvailability {
private static URL url;
private static HttpURLConnection con;
private static int state = -1;
 
/**
   * ���ܣ���⵱ǰURL�Ƿ�����ӻ��Ƿ���Ч,
   * ����������������� 5 ��, ��� 5 �ζ����ɹ�����Ϊ�õ�ַ������
   * @param urlStr ָ��URL�����ַ
   * @return URL
   */
public static synchronized int isConnect(String urlStr) {
   int counts = 0;
   int i=0;
   if (urlStr == null || urlStr.length() <= 0) {                       
    return i;                 
   }
   while (counts < 5) {
    try {
     url = new URL(urlStr);
     con = (HttpURLConnection) url.openConnection();
     state = con.getResponseCode();
     System.out.println(counts +"= "+state);
     if (state == 200) {
      System.out.println("URL���ã�");
      i=1;
     }
     break;
    }catch (Exception ex) {
     counts++; 
     System.out.println("URL�����ã����ӵ� "+counts+" ��");
     urlStr = null;
     continue;
    }
   }
   return i;
}
public static void main(String[] args) {
    URLAvailability u=new URLAvailability();
  int i=  u.isConnect("http://apitest.kcway.net/image/upload/apply/20180326/2018032674f1b3debf.pdf");
   System.out.println(i);
}
}
