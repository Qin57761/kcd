package com.service1.duoying;

import java.util.List;


import com.model1.bx;

public interface bxService {

	//���ݿͻ�������ѯ������Ϣ
		public List<bx> findbxbyc_name(String c_carno);
		
		//��ѯ��������
		public List<bx> findbx();
}
