package com.mapper1.loan;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;

public interface LoanOverdueMapper {
	//��ѯ��������
	List<PageData> selectOverdueList(PageData pd);
}
