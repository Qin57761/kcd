package com.mapper1.Excel;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface uploadExcelMapper {

	/**
	 * �����ݿ�����ӱ�������
	 * @param list
	 * @return
	 */
	int addExcel(Map map); 
	/**
	 * �������֤�Ų�ѯ��������
	 * @param id_card
	 * @return
	 */
	int count(@Param("id_card") String id_card);
	/**
	 * ͨ�����֤�Ų�ѯ�����ڿͻ�
	 * @param id_card
	 * @return
	 */
	List<Map> selectOverdue(@Param("id_card") String id_card );
	/**
	 * �����ڿͻ�������ӵ�����
	 * @param map
	 * @return
	 */
	int addOverdue(Map map);
	
}
