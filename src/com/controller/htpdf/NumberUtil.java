package com.controller.htpdf;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/**������ִ�Сдת��
 * ������ NumberUtil
 * ������:
 * ������: LiWang
 */
public class NumberUtil {
	  private static Logger log = LogManager.getLogger(NumberUtil.class.getName());
	  private static final String UNIT[] = { "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��",  
	            "ʰ", "��", "Ǫ", "��", "ʰ", "", "��", "��" };  
	    private static final String NUM[] = { "��", "Ҽ", "��", "��", "��", "��", "½",  
	            "��", "��", "��" }; 
	    //									�� 		ʮ 		��			 ǧ				 ��				ʮ��				����
	    private static String suffix[]={"_ones","_tens","_hundreds","_thousands","_tenThousand","_oneHundredThousand","_million"};
	    //��һ���������ĸ�λʮλ ��λǧλ �����������ĵ���ʽ��ʾ������map��
		 public static void numberSubchinese(String money,Map map,String prefix){
			Integer integer=Integer.parseInt(money);//ת��Ϊ��������
			money=String.valueOf(integer);//������������
			Integer length=money.length();
			for(int j=1;j<=suffix.length;j++){
				int site=length-j;
				if(site>=0){//money��index��Χ��ȡֵ �����±�Խ��
					Integer value=Integer.parseInt(String.valueOf(money.charAt(site)));//��õ�ǰ������ֵ �Ӹ�λ��ʼ
					map.put(prefix+suffix[j-1],NUM[value]);
				}else{
					//Ĭ��Ϊ0
					map.put(prefix+suffix[j-1],"��");
				}
			}
	    }
	    /** 
	     * �����С��ת�������Ĵ�д��� 
	     * @param money 
	     * @return result 
	     */  
	    public static String Test2(double money) {  
	        long money1 = Math.round(money * 100);// �������뵽��  
	        if (money1 == 0) return "��";  
	        String strMoney = String.valueOf(money1);
	        int numIndex = 0; // numIndex����ѡ������ֵ
	        int unitIndex = UNIT.length - strMoney.length();//unitIndex��ʾ ��λ
	        boolean isZero = false; // �����жϵ�ǰΪ�Ƿ�Ϊ��  
	        String result = "";  
	        for (; numIndex < strMoney.length(); numIndex++, unitIndex++){
	            char num = strMoney.charAt(numIndex);//���ش�λ���ϵĽ������ ������λ����ʼ
	            if (num == '0') { //�����λ�õ�����Ϊ 0
	                isZero = true;
	                
	                if (UNIT[unitIndex] == "��" || UNIT[unitIndex] == "��" || UNIT[unitIndex] == "") { // �����ǰλ���ڡ���Ԫ������ֵΪ��   ����20000
	                    result = result + UNIT[unitIndex]; //����λ�ڡ���Ԫ  
	                    isZero = false; //ʲô����²���Ҫ���� ����20 **** ��ʰ��.... �� ��ʰ��ȴû�з�ʰǪ �����������
	                }
	            }else {  
	                if (isZero){ //ʲô�������Ҫ���� ���� 20** ��Ǫ��....
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
	    public static void main(String[] args){
//	    	String s=NumberUtil.Test2(Double.valueOf("200303"));//200303.22
//	    	System.out.println(s);
//	    	System.out.println(chineseNumber2Int(s));
	    	
	    	
//	    	String s="45";
//	    	Map map=new LinkedHashMap<>();
//	    	numberSubchinese(s,map,"A");
//	    	System.out.println(s+"   "+JSON.toJSONString(map));
	    }
	    //��ȷ����λ
	    @SuppressWarnings("unused")
	    private static double chineseNumber2Int(String chineseNumber){
	        double result = 0;
	        double temp = 1;//���һ����λ�������磺ʮ��
	        int count = 0;//�ж��Ƿ���chArr
	        char[] cnArr = new char[]{'Ҽ', '��', '��', '��', '��', '½', '��', '��', '��'};
	        char[] chArr = new char[]{'ʰ', '��', 'Ǫ', '��', '��'};//, '��','��','��'
	        for (int i = 0; i < chineseNumber.length(); i++) {
	            boolean b = true;//�ж��Ƿ���chArr
	            char c = chineseNumber.charAt(i);
	            for (int j = 0; j < cnArr.length; j++) {//�ǵ�λ��������
	                if (c == cnArr[j]) {//�����ʱ c����1 j=0����� c����2 j=1�����
	                    if(0 != count){//�����һ����λ֮ǰ���Ȱ���һ����λֵ��ӵ������
	                        result += temp;
	                        temp = 1;
	                        count = 0;
	                    }
	                    // �±�+1�����Ƕ�Ӧ��ֵ
	                    temp = j + 1;
	                    b = false;
	                    break;
	                }
	            }
	            if(b){//��λ{'ʮ','��','ǧ','��','��'}
	                for (int j = 0; j < chArr.length; j++) {
	                    if (c == chArr[j]) {
	                        switch (j) {
	                        case 0:
	                            temp *= 10;
	                            break;
	                        case 1:
	                            temp *= 100;
	                            break;
	                        case 2:
	                            temp *= 1000;
	                            break;
	                        case 3:
	                            temp *= 10000;
	                            break;
	                        case 4:
	                            temp *= 100000000;
	                            break;
	                        case 5:
	                            temp *= 0.1;
	                            break;
	                        case 6:
	                            temp *= 0.01;
	                            break;
	                        case 7:
	                            temp *= 0.001;
	                            break;
	                        default:
	                            break;
	                        }
	                        count++;
	                    }
	                }
	            }
	            if (i == chineseNumber.length() - 1) {//���������һ���ַ�
	                result += temp;
	            }
	        }
	        return result;
	    }
	   
}
