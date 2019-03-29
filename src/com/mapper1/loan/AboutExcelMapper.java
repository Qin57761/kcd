package com.mapper1.loan;

import java.util.List;

import com.model1.icbc.erp.PageData;
import com.util.pagedate;

public interface AboutExcelMapper{
	//�޸Ļ���ƻ���¼
	Integer updatePaySchedule(PageData pd);
	//��ѯ����������Ϣ
	PageData icbcInfo(PageData pd);
	//��ѯ����¼
	List<PageData> selectRecordList(PageData pd);
	//��excel�������¼�����ݿ����
	Integer excel_to_sql(PageData pd);
	//���¼�����¼
	Integer addExcelRecord(PageData pd);
	//������ڿͻ������ݿ����
	Integer addOverdueClient(PageData pd);
	
	//��ѯ���ڱ����Ƿ��иÿͻ��������զ�������ڱ������
	Integer selectOverdueClintOnAdd(PageData pd);
}
