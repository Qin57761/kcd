package com.mapper.guazi;
import java.util.List;

import com.model.CBS.CbsSuccessfulPurchaseQueryReport;
import com.model.guazi.GuaziRecords;
import com.model.jbapi.jbzxapiuser;
import com.util.Page;
public interface GuaziRecordsMapper{
    //��ҳ���ݣ�����ѯ������
    List<jbzxapiuser> OneToArr(Page page);
    //��ҳ������������ѯ������
    int OneToArrCount();
    //����ѯ���� ��ѯ����
    int OneToArrCountSelective(Page page);
    //���
    int insert(GuaziRecords guazirecoreds);
    //��ѯ
    String selectbyid(String gzid);
}