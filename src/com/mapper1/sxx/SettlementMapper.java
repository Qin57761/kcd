package com.mapper1.sxx;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;

public interface SettlementMapper {

	/**
	 * ��ѯ������ѺרԱ�������ԭʼ���� 
	 * @return
	 */
	public List<Map<String, Object>> ToBeProcessed(@Param("status") Integer status);
	
	/**
	 * ����ԭʼ���ݴ����õ���idbcid��ѯרԱ���ͨ������������� --������
	 * @return
	 */
	public List<PageData> FindDataByIcbcid(@Param("list") List<Integer> list, @Param("param") String param);
	
}
