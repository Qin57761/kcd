package com.service1.duoying;

import java.util.List;


import com.model1.yhkls;

public interface yhklsService {
	
	
	//���ݿͻ�������ѯ������Ϣ
		public List<yhkls> findyhklsbyc_name(String c_name);
		
		//��ѯ��������
		public List<yhkls> findyhkls();

}
