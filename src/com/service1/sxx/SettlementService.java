package com.service1.sxx;

import java.util.List;
import java.util.Map;

import com.model1.icbc.erp.PageData;

public interface SettlementService {

	/**
	 * ��ѯ������ѺרԱ�������ԭʼ���� --������
	 * @return
	 */
	public List<Map<String, Object>> ToBeProcessed(Integer status);
	
	/**
	 * ����ԭʼ���ݴ����õ���idbcid��ѯרԱ���ͨ������������� 
	 * @return
	 */
	public List<PageData> FindDataByIcbcid(List<Integer> list, String param);
	
}
