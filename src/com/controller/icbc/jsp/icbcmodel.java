package com.controller.icbc.jsp;

import java.util.HashMap;
import java.util.Map;

public class icbcmodel {
	public static void main(String[] args) {
		Map<Object, Object> map= zx_status();
		System.out.println(map.get("0"));
	}
    //�������״̬
	public static Map<Object, Object> zx_status(){
		 Map<Object, Object> map=new HashMap<>();
		 map.put(0,"�ݸ���");
		 map.put(1,"�ݸ���");
		 map.put(2,"�����");
		 map.put(3,"����ͨ��");
		 map.put(4,"���˲���");
		 map.put(5,"���Ų�ͨ��");
		 map.put(6,"����");
		 map.put(7,"���Ų�ͨ��(ͨ�������)");  //�� ���Ų�ͨ��(�û��ύͨ��) ��Ϊ  ���Ų�ͨ��(ͨ�������)
		 map.put(8,"���Ų�ͨ��(ͨ��ͨ��)");
		 map.put(9,"���Ų�ͨ��(ͨ�ڲ�ͨ��)");
		 map.put(10,"���Ų�ͨ��(������ͨ��)"); // �൱�� ����ͨ�ڵĻ��˲���
		 return map;	
	}
	 //�������״̬
		public static Map<Object, Object> zxtr_status(){
			 Map<Object, Object> map=new HashMap<>();
			 map.put(1,"�����");
			 map.put(2,"ͨ��ͨ��");
			 map.put(3,"ͨ�ڲ�ͨ��");
			 map.put(4,"������ͨ��");
			 return map;	
		}
	//�������״̬
	public static Map<Object, Object> pg_status(){
		 Map<Object, Object> map=new HashMap<>();
		 map.put(0,"�ݸ���");
		 map.put(1,"�ݸ���");
		 map.put(2,"�����");
		 map.put(3,"�������");
		 map.put(4,"���˲���");
		 map.put(5,"δ��");
		 map.put(6,"����");
		 return map;	
	}
    //�������״̬
	public static Map<Object, Object> kk_status(){
		 Map<Object, Object> map=new HashMap<>();
		 map.put(0,"�ݸ���");
		 map.put(1,"�ݸ���");
		 map.put(2,"��ݺ˲���");
		 map.put(3,"���������");
		 map.put(4,"���˲���");
		 map.put(5,"����ʧ��");
		 map.put(6,"����");
		 map.put(7,"����ɹ�");
		 map.put(8,"�������");
		 map.put(9,"�������");
		 return map;	
	}
	//��ǩ���״̬
	public static Map<Object, Object> mq_status(){
		 Map<Object, Object> map=new HashMap<>();
		 map.put(0,"�ݸ���");
		 map.put(1,"�ݸ���");
		 map.put(2,"�����");
		 map.put(3,"���ͨ��");
		 map.put(4,"���˲���");
		 map.put(5,"δ�ñ���");
		 map.put(6,"����");
		 return map;	
	}
	//�����������״̬
	public static Map<Object, Object> qcdk_status(){
		 Map<Object, Object> map=new HashMap<>();
		 map.put(0,"�ݸ���");
		 map.put(1,"�ݸ���");
		 map.put(2,"�����");
		 map.put(3,"����");
		 map.put(4,"���˲���");
//		 map.put(5,"��������(ʧ��)");
//		 map.put(6,"����");
//		 map.put(7,"��������");
		 map.put(8,"����������");
		 return map;	
	}

	public static Map<Object, Object> icbczx(){
	 Map<Object, Object> map=new HashMap<>();
	 map.put(1,"��Ȩ��");
	 map.put(2,"��ǩ��");
	 map.put(3,"���֤�����渴ӡ��");
	 map.put(4,"���֤����");
	 map.put(5,"���֤����");
	 return map;	
	}
	//7-7 �������޸�
	public static Map<Object, Object> icbckk(){
		 Map<Object, Object> map=new HashMap<>();
		 map.put(1,"���֤����");
		 map.put(2,"���֤����");
		 map.put(3,"����˻�������1");
		 map.put(4,"����˻�������2");
		 map.put(5,"���ÿ������1");
		 map.put(6,"���ÿ������2");
		 map.put(7,"����˰������");
		 map.put(8,"��ǩ��");//2018-07-17����
		 map.put(9,"�绰���������");//2018-07-07����
		 map.put(10,"�����������");
		 return map;	
	}
	public static Map<Object, Object> icbcpg(){
		 Map<Object, Object> map=new HashMap<>();
		 map.put(1,"��ʻ֤����");
		 map.put(2,"��ʻ֤����");
		 map.put(3,"��ʻ֤����");
		 map.put(4,"��ʻ֤����");
		 map.put(5,"��Ȩ֤1-2ҳ");
		 map.put(6,"��Ȩ֤3-4ҳ");
		 map.put(7,"��Ȩ֤5-6ҳ");
		 map.put(8,"��������");
		 map.put(9,"��ͷ����");
		 map.put(10,"ǰ���45��");
		 map.put(11,"����45��");
		 map.put(12,"��β����");
		 map.put(13,"��������");
		 map.put(14,"�п�̨����");
		 map.put(15,"��̱�");
		 map.put(16,"��ǰ����(��)");
		 map.put(17,"��ǰ����(��)");
		 map.put(18,"��ʻ֤����");
		 map.put(19,"��ʻ֤����");
		 map.put(20,"�ϸ�֤");
		 /* 18=>'�Һ����Ӱ��',
			19=>'������Ӱ��',
			20=>'��ǰ����45��',
			21=>'�����45��',
			22=>'��ǰ����45��',
			23=>'�Һ���45��',
			24=>'��̥������', */
		 return map;	
	}
	
}
