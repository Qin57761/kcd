package com.mapper;

import java.util.List;

import com.model.txl;

public interface txlMapper {
    //ͨѶ¼��ѯ��¼
	public void addtxl(txl txl);
	//
	public void uptime(txl txl);
	//
	public List<txl> txlcx();
	//
	public int txlnum();
}
