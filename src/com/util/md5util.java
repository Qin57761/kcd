package com.util;

import java.security.MessageDigest;

public class md5util {
  
    
    /** 
     * message-digest algorithm 5����Ϣ-ժҪ�㷨�� 
     *  
     * md5�ĳ��ȣ�Ĭ��Ϊ128bit��Ҳ����128�� 0��1�� �����ƴ� �� 
     *  
     * 128/4 = 32 ���� 16���� ��ʾ��Ϊ32λ�ˡ� 
     */  

      
        // ���Է���  
//        public static void main(String[] args) {  
//        String pwd = "������־����";  
//        System.out.println("����ǰ�� " + pwd);  
//        System.err.println("���ܺ� " + md5util.getMD5(pwd));  
//        }  
      
        /** 
         * ����md5 
         *  
         * @param message 
   
         * @return 
         */  
        public static String getMD5(String message,String charset) {  
        String md5str = "";  
        try {  
            // 1 ����һ���ṩ��ϢժҪ�㷨�Ķ��󣬳�ʼ��Ϊmd5�㷨����  
            MessageDigest md = MessageDigest.getInstance("MD5");  
      
            // 2 ����Ϣ���byte����  
            byte[] input = message.getBytes(charset);  
      
            // 3 ��������ֽ�����,�������128λ��  
            byte[] buff = md.digest(input);  
      
            // 4 ������ÿһ�ֽڣ�һ���ֽ�ռ��λ������16��������md5�ַ���  
            md5str = bytesToHex(buff);  
      
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return md5str;  
        }  
        // ���ܺ����  
        public static String JM(String inStr) {  
         char[] a = inStr.toCharArray();  
         for (int i = 0; i < a.length; i++) {  
          a[i] = (char) (a[i] ^ 't');  
         }  
         String k = new String(a);  
         return k;  
        }  
        /** 
         * ������תʮ������ 
         *  
         * @param bytes 
         * @return 
         */  
        public static String bytesToHex(byte[] bytes) {  
        StringBuffer md5str = new StringBuffer();  
        // ������ÿһ�ֽڻ���16��������md5�ַ���  
        int digital;  
        for (int i = 0; i < bytes.length; i++) {  
            digital = bytes[i];  
      
            if (digital < 0) {  
            digital += 256;  
            }  
            if (digital < 16) {  
            md5str.append("0");  
            }  
            md5str.append(Integer.toHexString(digital));  
        }  
        return md5str.toString().toUpperCase();  
        }  
          
        	 // MD5���롣32λ     
        	 public static String MD5(String inStr) {     
        	  MessageDigest md5 = null;     
        	  try {     
        	   md5 = MessageDigest.getInstance("MD5");     
        	  } catch (Exception e) {     
        	   System.out.println(e.toString());     
        	   e.printStackTrace();     
        	   return "";     
        	  }     
        	  char[] charArray = inStr.toCharArray();     
        	  byte[] byteArray = new byte[charArray.length];     
        	    
        	  for (int i = 0; i < charArray.length; i++)     
        	   byteArray[i] = (byte) charArray[i];     
        	    
        	  byte[] md5Bytes = md5.digest(byteArray);     
        	    
        	  StringBuffer hexValue = new StringBuffer();     
        	    
        	  for (int i = 0; i < md5Bytes.length; i++) {     
        	   int val = ((int) md5Bytes[i]) & 0xff;     
        	   if (val < 16)     
        	    hexValue.append("0");     
        	   hexValue.append(Integer.toHexString(val));     
        	  }     
        	    
        	  return hexValue.toString();     
        	 }     
        	    
        	 // ����ļ����㷨     
        	 public static String KL(String inStr) {     
        	  // String s = new String(inStr);     
        	  char[] a = inStr.toCharArray();     
        	  for (int i = 0; i < a.length; i++) {     
        	   a[i] = (char) (a[i] ^ 't');     
        	  }     
        	  String s = new String(a);     
        	  return s;     
        	 }     
        	       

        	 // ����������     
        	 public static void main(String args[]) {     
        	  String s = new String("a");     
        	  System.out.println("ԭʼ��" + s);     
        	  System.out.println("MD5��" + getMD5(s,"UTF-8"));     
        	  //System.out.println("MD5���ټ��ܣ�" + KL(MD5(s)));     
        	  System.out.println("���ܵģ�" + JM(JM(getMD5(s,"UTF-8"))));     
        	 }     

}
