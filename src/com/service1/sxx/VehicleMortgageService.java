package com.service1.sxx;

import java.util.List;
import java.util.Map;

public interface VehicleMortgageService {

	/**
	 * ��ѯ������ѺרԱ�������ԭʼ���� --������
	 * @return
	 */
	public List<Map<String, Object>> ToBeProcessed(Integer status);
	
	/**
	 * ����ԭʼ���ݴ����õ���idbcid��ѯרԱ���ͨ������������� --������
	 * @return
	 */
	public List<Map<String, Object>> FindDataByIcbcid(List<Integer> list);
	
}
