package com.controller.htpdf;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//���ݹ��˸���
public class DataConversionParent {
	public static String getcolor(String s){
		 String ss = null;
		 switch (s){
		 	case "1":
				ss="��";
				break;
			case "2":
				ss="��";
				break;
			case "3":
				 ss="��";
				 break;
			case "4":
				 ss="��";
				 break;
			case "5":
				ss="��";
				break;
			case "6":
				 ss="��";
				 break;
			case "7":
				ss="��";
				break;
			case "8":
				 ss="��";
				 break;
			case "9":
				 ss="��";
				 break;
			case "10":
				ss="��";
				break;
			case "11":
				 ss="��";
				 break;
			case "12":
				ss="��";
				break;
			case "13":
				 ss="��";
				 break;
			case "14":
				 ss="��";
				 break;
			case "15":
				ss="��";
				break;
			case "16":
				 ss="����";
				 break;
			case "17":
				ss="��";
				break;
			case "18":
				 ss="����";
				 break;
			case "19":
				 ss="����";
				 break;
			case "20":
				ss="��ɽ";
				break;
			case "0":
		 		ss="����ɫ";
		 		break;
		 }
		 return ss;
	}
	
	/*
	 * @Description: ְ��
	 */
	public static String getduty(String s){
		 String ss;
		 switch (s){
			case "0":
				 ss="��ҵ������";
				 break;
			case "1":
				ss="�ܾ���";
				break;
			case "2":
				 ss="���ž���";
				 break;
			default :
				ss="ְԱ";
				break; 
		}
		 return ss;
	}
	/**
	 * @Description: ѧ�����
	 */
	public static String geteducation(String s){
		String ss="����";
		 switch (s){
			case "1":
				 ss="Сѧ";
				 break;
			case "3":
				ss="����";
				break;
			case "4":
				 ss="��ר";
				 break;
			case "5":
				 ss="����";
				 break;
			case "6":
				ss="˶ʿ";
				break;
			case "7":
				 ss="��ʿ������";
				 break;
		}
		 return ss;
	}
	 /**
	 * @Description: �������
	 */
	public static String getmarriage(String s){
		 String ss="δ��";
		 switch (s){
			case "1":
				 ss="�ѻ�";
				 break;
			case "2":
				ss="����";
				break;
			case "3":
				 ss="ɥż";
				 break;
		}
		 return ss;
	 }
	 
	/**
	 * @Description: �����˹�ϵ
	 */
	public static String getMutualborrowingR(String s){
		String ss="����";
		switch (s){
			case "0":
				 ss="����";
				 break;
			case "1":
				 ss="����";
				 break;
			case "2":
				ss="ĸ��";
				break;
			case "3":
				ss="�ֵ�";
				break;
			case "4":
				ss="ͬ��";
				break;
			case "5":
				ss="����";
				break;
			case "6":
				 ss="����";
				 break;
			case "7":
				 ss="��Ů";
				 break;
			case "8":
				 ss="ĸŮ";
				 break;
			case "9":
				ss="����";
				break;
			case "10":
				ss="���";
				break;
		}
		return ss;
	}
    /**��������
     */
	public static Map getCarInfo(String CardCode){ 
   	 	String year;//������
   	 	String sex;//�Ա�
   	 	int age = 0;//����
   	 	
   		year = CardCode.substring(6,10);// �õ����
   		if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// �ж��Ա�
            sex = "Ů";
        } else {
            sex = "��";
        }  	
       //�õ���ǰ��ϵͳʱ��  
       SimpleDateFormat format = new SimpleDateFormat("yyyy"); 
       String fyear = format.format(new Date());
       age=Integer.parseInt(fyear) - Integer.parseInt(year);
       
       Map<String, Object> map = new HashMap<String, Object>();
       map.put("sex", sex);
       map.put("age", age);
       map.put("birth",new StringBuilder().append(year).append("��").append(CardCode.substring(10,12)).append("��").append(CardCode.substring(12,14)).append("��").toString());
       return map;  
   } 
	/** 
     * ʹ��java������ʽȥ�������.��0 
     */  
    public static String subZeroAndDot(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "").replaceAll("[.]$", "");  
        }  
        return s;  
    }
    /**
	 * @Description: ��ס���
	 */
    public static String getreside(String s){
		 String ss="����ס��";
		 switch (s){
			case "1":
				 ss="�ⷿ";
				 break;
			case "2":
				ss="���ڸ����";
				break;
			case "3":
				 ss="��������";
				 break;
			case "4":
				 ss="��λ����";
				 break;
			case "5":
				ss="����";
				break;
		}
		 return ss;
	}
	
	/**
	 * @Description: ��λ����
	 */
    public static String getworkunit(String s){
		 String ss="����";
		 switch (s){
			case "0":
				 ss="����";
				 break;
			case "1":
				ss="���徭��";
				break;
			case "2":
				 ss="˽Ӫ";
				 break;
			case "3":
				 ss="��Ӫ";
				 break;
			case "4":
				ss="�ɷݺ���";
				break;
			case "5":
				 ss="�����ɷ���";
				 break;
			case "6":
				 ss="����";
				 break;
			case "7":
				 ss="����";
				 break;
		}
	   return ss;
	}
	
	/**
	 * @return
	 * @Description: ������ҵ
	 * @param name
	 * @return 
	 */
    public static String getindustry(String s){
		 String ss="����";
		 switch (s){
			case "0":
				 ss="ũ������";
				 break;
			case "1":
				ss="�ʵ�ͨѶ";
				break;
			case "2":
				 ss="���ز�";
				 break;
			case "3":
				 ss="�ƽ�����";
				 break;
			case "4":
				ss="��ҵ";
				break;
			case "5":
				 ss="����";
				 break;
			case "6":
				ss="֤ȯ";
				break;
			case "7":
				 ss="����";
				 break;
			case "8":
				 ss="��ҵ";
				 break;
			case "9":
				ss="��������";
				break;
		}
		 return ss;
	}
	/**
	 * @Description: ְҵ
	 */
    public static String getProfessional(String s){
		  String ss="����";
		 switch (s){
			case "0":
				 ss="����Ա";
				 break;
			case "1":
				ss="��ҵ��λԱ��";
				break;
			case "2":
				 ss="����";
				 break;
			case "3":
				 ss="ũ��";
				 break;
			case "4":
				ss="����";
				break;
			case "5":
				 ss="ְԱ";
				 break;
			case "6":
				ss="˽��ҵ��";
				break;
			case "7":
				ss="ѧ��";
				break;
			case "8":
				 ss="����ְҵ";
				 break;
		}
		 return ss;
	}
}
