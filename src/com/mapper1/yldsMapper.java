package com.mapper1;

import java.util.List;

import com.model1.ylds;

public interface yldsMapper {

	//�������ݱ�Ų�ѯ����
	public List<ylds> findyldsbyid(String ACCOUNT_NO);
	
	//��ѯ��������
	public List<ylds> findylds();
}
