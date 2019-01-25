package com.mapper1.sxx.financialExcel;

import java.util.List;
import java.util.Map;

public interface VehicleMortgageMapper {

	/**
	 * ��ѯ������ѺרԱ�������ԭʼ���� 
	 * @return
	 */
	public List<Map<String, Object>> ToBeProcessed(Integer status);
	
	/**
	 * ����ԭʼ���ݴ����õ���idbcid��ѯרԱ���ͨ������������� --������
	 * @return
	 */
	public List<Map<String, Object>> FindDataByIcbcid(List<Integer> list);
	
	/**
	 * ��ѯ������ѺרԱ�������ԭʼ���� --�Ѵ���
	 * @return
	 */
	public List<Map<String, Object>> AlreadyToDealWith();
}
