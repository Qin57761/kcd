package com.mapper1.ManagementCenter;

import java.util.HashMap;
import java.util.List;

public interface kj_icbcMapper {
	//ÿ�±�������
	public List<HashMap> selectbill();
	//ÿ�·ſ��,�ܽ��
	public List<HashMap> selectloan();
	//ÿ���ѷſ�δ��Ѻ����,�ܽ��
	public List<HashMap> selectfk();
	//ÿ���ܶ�����ʡ����
	public List<HashMap> selectstates(); 
	//ÿ���ܶ���������������
	public List<HashMap> selectgems(); 
	//ÿ���ܶ�����ʡ����
	public List<HashMap> selectloanstates(); 
	//ÿ���ܶ���������������
	public List<HashMap> selectloangems(); 
	//����ͼ����
	public List<HashMap> selectchart();
	//��������������ͼ
	public List<HashMap> selectmoneydistribute();
	//����ͨ���ʲ�ѯ
	public List<HashMap> selectcredit();
	//�ͻ������ѯͳ��
	public List<HashMap> selectclientage();
	//�³������ѯͳ��
	public List<HashMap> selectcarsage();
	//���������ֲ�ͼ
	public List<HashMap> selectadvancefund();
	//��Ѻ���ϻ������
	public List<HashMap> selectrecycle();
	//���ֳ�ÿ�·ſ�����ܽ��
	public List<HashMap> selectoldcars();
	//�³�ÿ�·ſ�����ܽ��
	public List<HashMap> selectnewcars();
	
}
