package com.mapper1.loan;

import java.util.List;

import com.model1.icbc.erp.PageData;
import com.util.pagedate;

public interface ClientPaymentMapper{
	//��ѯ�ͻ��������form ��ѯ������
	PageData selectZdr(String icbc_id);
	//��ѯ�ͻ��������form ��ѯ������Ϣ
	PageData selectLoanAfter(String icbc_id);
	//��ѯ�ͻ��������form ��ѯ�ͻ�����ƻ�
	List<PageData> selectPaySchedule(String icbc_id);
	//��ѯ�ͻ��������form ҵ��״̬��Ϣ
	PageData selectPayform(String icbc_id);
	//�û��ſ�ɹ������ɻ���ƻ�
	Integer addPaySchedule(PageData pd);
	//��ѯ��������б�
	List<PageData> selectPayList(PageData pd);
}
