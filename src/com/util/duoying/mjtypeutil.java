package com.util.duoying;

import java.util.HashMap;
import java.util.Map;

public class  mjtypeutil {
//	������״̬��
//	0:����,1:ת��,2:������,3:ͣʻ,4:ע��,
//	5:Υ��δ����,6:���ؼ��,
//	7:�¹�δ����,8:���ɳ�,
//	9:���,10:�ݿ�,11:ǿ��ע��,
//	12:�¹�����,13:����,14:�˳� 
//	public static final String[] IPOSBTITLE={"���-bk","״̬-zt","��˾����-gsname","��˹���ʱ��-shtime"};
 public static int mjmap(String s){	  
	Map m=new HashMap<>();
	m.put("����", 0);
	m.put("ת��", 1);
	m.put("������", 2);
	m.put("ͣʻ", 3);
	m.put("ע��",4);
	m.put("Υ��δ����",5);
	m.put("���ؼ��",6);
	m.put("�¹�δ����",7); 
	m.put("���ɳ�", 8);
	m.put("���", 9);
	m.put("�ݿ�", 10);
	m.put("ǿ��ע��", 11);
	m.put("�¹�����", 12); 
	m.put("����", 13);
	m.put("�˳�", 14);
	m.put("Υ��δ����", 15);
	int i;
	i=(int) m.get(s);	
	return i;
 }
 public static int UseType(String s){	  
		Map m=new HashMap<>();
		m.put("����Ӫ", 0);
		m.put("��Ӫ", 4);
		int i;		
		i=(int) m.get(s);	
		return i;	
		
	 }
 
 
 public static int sgmap(String s){	  
		Map m=new HashMap<>();
		m.put("��", 1);
		m.put("��", 0);
		int i;
		i=(int) m.get(s);	
		return i;
	 }
// 0	�ƶ�
// 1	��ͨ
// 2	����
// 3	����
 public static int yystype(String s){	  
		Map m=new HashMap<>();
		m.put("�й��ƶ�", 0);
		m.put("�й���ͨ", 1);
		m.put("�й�����", 2);
		m.put("����", 3);
		int i;
		i=(int) m.get(s);	
		return i;
	 }
 //true 1 false 0
 public static int tfint(Object s){	  
		Map m=new HashMap<>();
		m.put("True", 0);
		m.put("true", 0);
		m.put("False", 1);
		m.put("false", 1);
		m.put("unKnow", 2);
		m.put("unKnow", 2);
		int i=2;
		if(m.get(s)!=null){
		 i=(int) m.get(s);	
		}			
		return i;
	 }
// 0:���ڻ���ƻ�
// 1:β�ڻ���ƻ�
// 2:���л���ƻ�
 public static int hkjh(Object s){	  
		Map m=new HashMap<>();
		m.put("���ڻ���ƻ�", 0);
		m.put("β�ڻ���ƻ�", 1);
		m.put("���л���ƻ�", 2);
//		m.put("false", 1);
//		m.put("unKnow", 2);
//		m.put("unKnow", 2);
		int i;
		i=(int) m.get(s);	
		return i;
	 }
 public static void main(String[] args) {
	System.out.println(tfint("True"));
}
 
}