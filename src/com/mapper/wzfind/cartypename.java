package com.mapper.wzfind;

import java.util.HashMap;
import java.util.Map;

public class cartypename {

public static String cartype(String cartype){
	Map<Object,Object> m=new HashMap<Object,Object>();
	m.put("01", "������������");
	m.put("02", "С����������");
	m.put("03", "ʹ����������");
	m.put("04", "�����������");
	m.put("05", "������������");
	m.put("06", "�⼮��������");
	m.put("07", "��������Ħ�г�����");
    m.put("08", "���Ħ�г�����");
    m.put("09", "ʹ��Ħ�г�����");
	m.put("10", "���Ħ�г�����");
	m.put("11", "����Ħ�г�����");
	m.put("12", "�⼮Ħ�г�����");
	m.put("13", "ũ�����䳵����");
    
	String str= "";
	if(m.get(cartype)!=null&&!m.get(cartype).equals("")){
		str=(String) m.get(cartype);
	}else{
		str=(String) m.get("02");
	}
	
	return str;	
}
	
 public static void main(String[] args) {
	 
	 System.out.println(cartype("03"));
  }
	
}
