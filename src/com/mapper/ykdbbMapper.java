package com.mapper;

import java.util.List;
import java.util.Map;

import com.model.ykdbb;

public interface ykdbbMapper {

	
	//����ʱ�������ѯ
	public List<ykdbb> findbydate(Map<String, String> mdate);
}
