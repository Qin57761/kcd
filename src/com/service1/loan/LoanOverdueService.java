package com.service1.loan;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import com.model1.icbc.erp.PageData;

public interface LoanOverdueService {
	//��ѯ��������
	List<PageData> selectOverdueList(PageData pd);
}
