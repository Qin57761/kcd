package com.service1.Repayment;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import com.model1.icbc.erp.PageData;

public interface OverdueService {
	
	/**
	 * ��ѯ��������
	 * @param param
	 * @param pd
	 * @return
	 */
	List<PageData> selectoverdue(String param,PageData pd);
}
