package com.service1.Excel;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;
import com.util.Excel.RecordUtil;

public interface recordService {

	/**
	 * ��ѯ���ݿ��������   ��ģ����ѯ
	 * @return
	 */
	List<PageData> selectRecord(@Param(value="param") String param,@Param("pd")PageData pd);
	


}
