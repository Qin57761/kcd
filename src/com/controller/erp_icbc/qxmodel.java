package com.controller.erp_icbc;

import java.util.HashMap;
import java.util.Map;

import com.util.duoying.mapbeanutil;

public class qxmodel {

	 //Ȩ�� Ӣ-��
	public Map<Object,Object> qx(){
		Map<Object, Object> map=new HashMap<>();
		map.put("glzx","��������");
		
		map.put("zhgl","�˻�����");
		map.put("zhgl/gsgl","��˾����");
		map.put("zhgl/tjgs","��ӹ�˾");
		map.put("zhgl/rygl","��Ա����");
		map.put("zhgl/tjry","�����Ա");
		map.put("zhgl/zhqx","�˺�Ȩ��");
		
		map.put("wlghd","���ֹ��д�");
		map.put("wlghd/zx","����");
		map.put("wlghd/qcpg","��������");
		map.put("wlghd/kk","����");
		map.put("wlghd/ssmq","��Ƶ��ǩ");
		map.put("wlghd/qcdk","��������");
		return map;
	}
	 //Ȩ�޶�Ӧ
	 public static final String[] QX = 
		    {"��������-glzx",
			 "�˻�����-zhgl",
			 "��˾����-zhgl/gsgl",
			 "��ӹ�˾-zhgl/tjgs",
			 "��Ա����-zhgl/rygl",
			 "�����Ա-zhgl/tjry",
			 "�˺�Ȩ��-zhgl/zhqx",
			 "���ֹ��д�-wlghd",
			 "����-wlghd/zx",
			 "��������-wlghd/qcpg",
			 "����-wlghd/kk",
			 "��Ƶ��ǩ-wlghd/ssmq",
			 "��������-wlghd/qcdk"
			 };
     //Ȩ�� ��-Ӣ
	 public Map<String,String> qx_d(){
		 String[] strings=qxmodel.QX;
		 Map<String, String> map=new HashMap<>();
		 for(int i=0;i<strings.length;i++){
			 String[] s1=strings[i].split("-");
			 map.put(s1[0], s1[1]);
			 //System.out.println(s1[0]+"-"+s1[1]); 
			 
		 }
		return map;
	 }
	 
	 public static void main(String[] args) {
		
		 String[] strings=qxmodel.QX;
		 Map<String, String> map=new HashMap<>();
		 for(int i=0;i<strings.length;i++){
			 String[] s1=strings[i].split("-");
			 map.put(s1[0], s1[1]);
			 System.out.println(s1[0]+"-"+s1[1]); 
			 
		 }
		 System.out.println(map);
	 }
	 
	
}
