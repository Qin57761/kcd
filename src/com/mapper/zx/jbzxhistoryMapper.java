package com.mapper.zx;

import java.util.List;

import com.model.jbapi.jbzxapihistory;

public interface jbzxhistoryMapper {

	//������ŵ��ü�¼
	public void addjbzxhistory(jbzxapihistory jbzxapihistory);
	//
	public List<jbzxapihistory> jbzxapihistorylist(int jbzx_id);
}
