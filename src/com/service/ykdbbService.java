package com.service;

import java.util.List;
import java.util.Map;

import com.model.ykdbb;

public interface ykdbbService {
	//����ʱ�������ѯ
		public List<ykdbb> findbydate(Map<String, String> mdate);
}
