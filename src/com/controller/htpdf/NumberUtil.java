package com.controller.htpdf;
/**�����ת��Ϊ��д����
 * ������ NumberUtil
 * ������:
 * ������: LiWang
 */
public class NumberUtil {
	  private static final String UNIT[] = { "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��",  
	            "ʰ", "��", "Ǫ", "��", "ʰ", "", "��", "��" };  
	    private static final String NUM[] = { "��", "Ҽ", "��", "��", "��", "��", "½",  
	            "��", "��", "��" };  
	    /** 
	     * �����С��ת�������Ĵ�д��� 
	     * @param money 
	     * @return result 
	     */  
	    public static String Test2(double money) {  
	        long money1 = Math.round(money * 100);// �������뵽��  
	        if (money1 == 0) return "��";  
	        String strMoney = String.valueOf(money1);//��long���͵�ת��Ϊ�ַ��� 
	        int numIndex = 0; // numIndex����ѡ������ֵ  
	        int unitIndex = UNIT.length - strMoney.length();//unitIndex����ѡ���λ   ��λ�Ӵ�С
	        boolean isZero = false; // �����жϵ�ǰΪ�Ƿ�Ϊ��  
	        String result = "";  
	        for (; numIndex < strMoney.length(); numIndex++, unitIndex++){
	            char num = strMoney.charAt(numIndex);//���ش�λ���ϵĽ������
	            if (num == '0') {  
	                isZero = true;//����"2033".charAt("0")  
	                if (UNIT[unitIndex] == "��" || UNIT[unitIndex] == "��" || UNIT[unitIndex] == "") { // �����ǰλ���ڡ���Ԫ������ֵΪ��   ����20000
	                    result = result + UNIT[unitIndex]; //����λ�ڡ���Ԫ  
	                    isZero = false;  
	                }   
	            }else {  
	                if (isZero) {  
	                    result = result + "��";  
	                    isZero = false;  
	                }  
	                result = result + NUM[Integer.parseInt(String.valueOf(num))] + UNIT[unitIndex];  
	            }  
	        }  
	        //���ǽǷֽ�β�ͼ�"��"��  
	       /* if (!result.endsWith("��")&&!result.endsWith("��")) {  
	            result = result + "��";  
	        } */ 
	        //����û�����д��룬��ֵ"400000001101.2"���������"��ǧ����ҼǧҼ����ҼԪ����"  
	        result = result.replaceAll("����", "��");  
	        return result;  
	    }
}
