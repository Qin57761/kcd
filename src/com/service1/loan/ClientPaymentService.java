package com.service1.loan;
import java.util.List;
import com.model1.icbc.erp.PageData;
public interface ClientPaymentService {
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
