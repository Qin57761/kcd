package com.mapper;

import java.util.List;

import com.model.dsj;


public interface dsjMapper {

	//�����ݲ�ѯ��¼
	public void adddsj(dsj dsj);
	//���´����ݼ�¼
	public void editdsj(dsj dsj);
	//�����ݷ�ҳ��ѯ
	public List<dsj> finddsj(int startPos, int pageSize);
	//��ѯ����
	public int finddsjcount();
}
