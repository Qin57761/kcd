package com.service.zx;

import java.util.List;

import com.model.jbapi.apikchistory;

public interface apikchistoryService {
	 //api�û���ֵ��¼
	 public void addapikchistory(apikchistory apikchistory);
    //��ѯ��¼ 
	 public List<apikchistory> findapikchistory(int jauid);
}
