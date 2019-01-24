package com.controller.htpdf;
import java.math.BigDecimal;
/**
 * @author LiWang
 * 2018��5��21��
 */
public class DoubleUtil {
    // Ĭ�ϳ������㾫��  
    private static final int DEF_DIV_SCALE = 10;  
    // ����಻��ʵ����  
    private DoubleUtil() {  
    }  
    /** 
     * �ṩ��ȷ�ļӷ����㡣 
     *  
     * @param v1 
     *            ������ 
     * @param v2 
     *            ���� 
     * @return ���������ĺ� 
     */  
    public static String add(String v1, String v2) {  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.add(b2).toString();  
    }  
    /** 
     * �ṩ��ȷ�ļ������㡣 
     *  
     * @param v1 
     *            ������ 
     * @param v2 
     *            ���� 
     * @return ���������Ĳ� 
     */  
    public static String sub(String v1, String v2) {  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.subtract(b2).toString();  
    }  
  
    /** 
     * �ṩ��ȷ�ĳ˷����㡣 
     *  
     * @param v1 
     *            ������ 
     * @param v2 
     *            ���� 
     * @return ���������Ļ� 
     */  
    public static String mul(String v1, String v2) {  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.multiply(b2).toString();  
    }  

    /**
     * �ڶ������㲿��֮ǰʼ����������(ʼ�նԷ�����������ǰ������ּ�1)
     * @param i
     * @param i1
     */
    public static String  ru(String i0,String i,int i1){
    	return  new BigDecimal(i0).divide(new  BigDecimal(i), i1, BigDecimal.ROUND_UP).toString();
    }
  
    /** 
     * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ�� С�����Ժ�10λ���Ժ�������������롣 
     *  
     * @param v1 
     *            ������ 
     * @param v2 
     *            ���� 
     * @return ������������ 
     */  
    public static String div(String v1, String v2) {  
        return div(v1, v2, DEF_DIV_SCALE);  
    }  

    /** 
     * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ �����ȣ��Ժ�������������롣 
     *  
     * @param v1 
     *            ������ 
     * @param v2 
     *            ���� 
     * @param scale 
     *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ�� 
     * @return ������������ 
     */  
    public static String div(String v1, String v2, int scale) {  
        if (scale < 0) {  
            throw new IllegalArgumentException("The scale must be a positive integer or zero");  
        }  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();  
    }  
  
    /** 
     * �ṩ��ȷ��С��λ�������봦�� 
     *  
     * @param v 
     *            ��Ҫ������������� 
     * @param scale 
     *            С���������λ 
     * @return ���������Ľ�� 
     */  
    public static String round(String v,int scale) {  
        if (scale < 0) {  
            throw new IllegalArgumentException("The scale must be a positive integer or zero");  
        }  
        BigDecimal b = new BigDecimal(v);  
        BigDecimal one = new BigDecimal("1");  
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();  
    }  
  
    /** 
     * �ṩ����ԣ���ȷ�ĳ������㡣��������2λС���� 
     * @param v1 
     *            ������ 
     * @param v2 
     *            ���� 
     * @return ������������ 
     */  
    public static String div2Scale(String v1, String v2) {  
        return div(v1, v2, 2);  
    } 			
}
