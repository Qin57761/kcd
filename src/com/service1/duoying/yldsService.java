package com.service1.duoying;

import java.util.List;


import com.model1.ylds;

public interface yldsService {

	//�������ݱ�Ų�ѯ����
		public List<ylds> findyldsbyid(String ACCOUNT_NO);
		
		//��ѯ��������
		public List<ylds> findylds();
	
}
