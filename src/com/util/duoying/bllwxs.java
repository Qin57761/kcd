package com.util.duoying;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class bllwxs {
	   /*  
     * �ж��Ƿ�Ϊ����   
     * @param str ������ַ���   
     * @return ����������true,���򷵻�false   
   */    
     public static boolean isInteger(String str) {      
       Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");      
       return pattern.matcher(str).matches();      
     }    
 
 
   /*   
     * �ж��Ƿ�Ϊ������������double��float   
     * @param str ������ַ���   
     * @return �Ǹ���������true,���򷵻�false   
   */      
     public static boolean isDouble(String str) {      
       Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");      
       return pattern.matcher(str).matches();      
     } 
	public static boolean isNumeric(String str){
		  for (int i = 0; i < str.length(); i++){
		   System.out.println(str.charAt(i));
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }
	
	
	public static double xshlw(Object ob){
		double dbj=0 ;   
		DecimalFormat jd = new DecimalFormat("#0.00");			
		//System.out.println(xsd);
        dbj=(double) ob;
        boolean tf= isIntegerForDouble(dbj);
        if(tf!=true){
        int xsd=ob.toString().indexOf(".");
        int ws=Integer.parseInt(ob.toString().substring(xsd+3,xsd+4));
        	System.out.println("������double:"+dbj);
        if(ws>4){
        	dbj=Double.parseDouble(jd.format(Double.parseDouble(ob.toString())));
        	System.out.println("����:"+dbj);
        }else{ 
        	int two=Integer.parseInt(ob.toString().substring(xsd+2,xsd+3));
        	two=two+1;
        	dbj=Double.parseDouble(ob.toString().substring(0,xsd+2)+two);        	
        	System.out.println("�Ĳ���:"+dbj);
        }		    	
        }else{
        	System.out.println("����double:"+dbj);
        }
		return dbj;			
	 }
	/** 
	 * �ж�double�Ƿ������� 
	 * @param obj 
	 * @return 
	 */  
	public static boolean isIntegerForDouble(double obj) {  
	    double eps = 1e-10;  // ���ȷ�Χ  
	    return obj-Math.floor(obj) < eps;  
	} 	
	public static void main(String[] args) {		
		double s=bllwxs.xshlw(40.002131);	
		//double obj=40.00000;
		//System.out.println(isIntegerForDouble(obj)); 
		System.out.println(s);		
	}
}
