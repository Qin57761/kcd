package com.mapper1;

import java.util.List;

import com.model1.yhkls;

public interface yhklsMapper {

	//���ݿͻ�������ѯ������Ϣ
	public List<yhkls> findyhklsbyc_name(String c_name);
	
	//��ѯ��������
	public List<yhkls> findyhkls();
}
