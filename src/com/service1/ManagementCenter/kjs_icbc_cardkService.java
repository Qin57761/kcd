package com.service1.ManagementCenter;

import java.util.HashMap;
import java.util.List;

public interface kjs_icbc_cardkService {
	//ÿ�����������ܶ�����
	public int CountSelect();
	//ÿ�����������������
	public int CountPass();
	//ÿ�����������ʡ��ȫ��������������
	public List<HashMap> SelectCarPassComm();
	//ÿ�����������������ȫ��������������
	public List<HashMap> SelectCarPassGems();
	//������������ͼ
	public List<HashMap> SelectChart();
	//�����ſ�ֲ�����ͼ
	public List<HashMap> SelectCarFk();
}
