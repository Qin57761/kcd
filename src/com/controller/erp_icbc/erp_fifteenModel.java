package com.controller.erp_icbc;

import java.util.HashMap;
import java.util.Map;

public class erp_fifteenModel {
	public static Map<Object, Object> fifteenModel(){
		 Map<Object, Object> map=new HashMap<>();
		 map.put(1,"���Ų�ѯ");
		 map.put(2,"����ͨ��");
		 map.put(3,"��������");
		 map.put(4,"���е���");
		 map.put(5,"��������");
		 map.put(6,"��Ƶ��ǩ");
		 map.put(7,"������ҵ������");
		 map.put(8,"��������");
		 map.put(9,"����ͨ��");
		 map.put(10,"�ʽ����");
		 map.put(11,"���д�������");
		 map.put(12,"��˾�鵵");
		 map.put(13,"��Ѻ�鵵");
		 map.put(14,"ҵ����Ϣ�޸�");
		 map.put(15,"�˵��˷�");
		 return map;	
	}
	
	public static void main(String[] args) {
		Map map=fifteenModel();
		System.out.println(map.get(15));
	}
}
