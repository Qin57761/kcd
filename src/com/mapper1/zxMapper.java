package com.mapper1;

import java.util.List;

import com.model1.zx;

public interface zxMapper {

	//���ݿͻ�������ѯ������Ϣ
	public List<zx> findzxbyc_name(String c_name);
	
	//��ѯȫ������
	public List<zx> findzx();
	//����
	public zx zxmap(String c_name);
	//���Ŵ�����
	public zx zxdsjmap(String c_name);
	
	
}
