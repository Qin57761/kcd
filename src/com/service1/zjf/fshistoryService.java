package com.service1.zjf;

import com.model1.zjf.fshistory;

public interface fshistoryService {
	//����id��ѯ�̻���
	public fshistory fshistorybyfid(int fid);

	//ͬ���̻�����Ӽ�¼
	public void addfshistory(fshistory fshistory);
}
