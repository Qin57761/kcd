package com.mapper.sxx.financialExcel;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface FinancialExcelMapper {

	/**
	 * ����ID��ѯ������Ϣ
	 * @param id
	 * @return
	 */
	Map GetLoanInformationbyid(Integer id);
	/**
	 * ��ѯ������Ҫ�ĸ��ֻ�������
	 * @return
	 */
	List<Map> ExportBuyCarInstallmentExcel(@Param(value="Institutions")String Institutions, @Param(value="Bank")String Bank);
	
	/**
	 * ����status��ѯʱ����Ϣ
	 * @param status
	 * @return
	 */
	List<Map> FindBuyCarInstallmentExcelByStatus();
	
	/**
	 * ��ѯ���ڻ�����
	 * @return
	 */
	List<Map> FindFirstPaymentDate();
	
	/**
	 * ��ѯ���еĺ�����������
	 * @return
	 */
	List<String> FindInstitutionsNameList();
	
	/**
	 * ��ѯ���е���������
	 * @return
	 */
	List<String> FindBankNameList();
	
}
