package com.service1.loan;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import com.model1.icbc.erp.PageData;

public interface LoanOverdueService {
	//��ѯһ����������
	PageData selectOverdueOne(PageData pd);
	//�޸Ŀͻ�����״̬
	Integer updateOverdueStatus(PageData pd);
	//��ѯ��������
	List<PageData> selectOverdueList(PageData pd);
	//��Ӳ�����¼
	Integer addOperationResult(PageData pd);
}
