package com.service;

import java.util.List;
import java.util.Map;

import com.model.history;

public interface historyService {

	//��Ӽ�¼
	public void hsava(history ht);
	  //��ѯ��ʷ����
  	public Map findhistory(String uid);
  //��ѯ��ʷ����
  	public List<history> hlist(String uid);
}
