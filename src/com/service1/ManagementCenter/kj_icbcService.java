package com.service1.ManagementCenter;

import java.util.HashMap;
import java.util.List;

public interface kj_icbcService {
	//ÿ�±�������
	public List<HashMap> SelectBill();
	//ÿ�·ſ��,�ܽ��
	public List<HashMap> SelectLoan();
	//ÿ���ѷſ�δ��Ѻ����,�ܽ��
	public List<HashMap> SelectFk();
	//ÿ���ܶ�����ʡ����
	public List<HashMap> SelectStates();
	//ÿ���ܶ�����ʡ����
	public List<HashMap> SelectGems();
	//ÿ���ܶ�����ʡ����
	public List<HashMap> SelectLoanStates();
	//ÿ���ܶ�����ʡ����
	public List<HashMap> SelectLoanGems();
	//����ͼ����
	public List<HashMap> SelectChart();
	//��������������ͼ
	public List<HashMap> SelectMoneyDistribute();
	//����ͨ���ʲ�ѯ
	public List<HashMap> SelectCredit();
	//�ͻ������ѯͳ��
	public List<HashMap> SelectClientAge();
	//�³������ѯͳ��
	public List<HashMap> SelectCarsAge();
	//���������ֲ�ͼ
	public List<HashMap> SelectAdvanceFund();
	//��Ѻ���ϻ������
	public List<HashMap> SelectRecycle();
	//���ֳ�ÿ�·ſ�����ܽ��
	public List<HashMap> SelectOldCars();
	//�³�ÿ�·ſ�����ܽ��
	public List<HashMap> SelectNewCars();
}
