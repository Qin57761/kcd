package com.mapper1.loan;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;

public interface LoanOverdueMapper {
	/**
	 * ���
	 */
	//��ѯ���form�б�  �ͻ���Ϣ+������Ϣ+�������Ϣ
	PageData selectPhoneClientCarLoanInfo(PageData pd);
	//��ѯ�������
	List<PageData> selectPhoneList(PageData pd);
	
	
	/**
	 * ���� 
	 */
	//��ѯһ����������
	PageData selectOverdueOne(PageData pd);
	//�޸Ŀͻ�����״̬
	Integer updateOverdueStatus(PageData pd);
	//��ѯ��������
	List<PageData> selectOverdueList(PageData pd);
	
	
	//��Ӳ�����¼
	Integer addOperationResult(PageData pd);
}
