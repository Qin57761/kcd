package com.service;

import java.util.List;

import com.model.dsj;

public interface dsjService {
    //�����ݲ�ѯ��¼
	public void adddsj(dsj dsj);
	//���´����ݼ�¼
    public void editdsj(dsj dsj);
    //��ҳ��ѯ
    public List<dsj> finddsj(int startPos,int pageSize);
  //��ѯ����
  	public int finddsjcount();
}
