package com.mapper.CBS;

import com.model.CBS.CbsSuccessfulPurchaseQueryReport;
import com.model.CBS.CbsSuccessfulPurchaseQueryReportExample;
import com.model.jbapi.jbzxapiuser;
import com.util.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CbsSuccessfulPurchaseQueryReportMapper {
    int countByExample(CbsSuccessfulPurchaseQueryReportExample example);
    //��ҳ���ݣ�����ѯ������
    List<jbzxapiuser> OneToArr(Page page);
    //��ҳ������������ѯ������
    int OneToArrCount();
    int OneToArrCountSelective(Page page);
    //�޸Ķ���״̬
    int updateByOrderIdSelective(CbsSuccessfulPurchaseQueryReport record);
    
    int deleteByExample(CbsSuccessfulPurchaseQueryReportExample example);

    int deleteByPrimaryKey(Integer cid);

    int insert(CbsSuccessfulPurchaseQueryReport record);

    int insertSelective(CbsSuccessfulPurchaseQueryReport record);

    List<CbsSuccessfulPurchaseQueryReport> selectByExampleWithBLOBs(CbsSuccessfulPurchaseQueryReportExample example);

    String selectByExample(CbsSuccessfulPurchaseQueryReportExample example);

    CbsSuccessfulPurchaseQueryReport selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("record") CbsSuccessfulPurchaseQueryReport record, @Param("example") CbsSuccessfulPurchaseQueryReportExample example);

    int updateByExampleWithBLOBs(@Param("record") CbsSuccessfulPurchaseQueryReport record, @Param("example") CbsSuccessfulPurchaseQueryReportExample example);

    int updateByExample(@Param("record") CbsSuccessfulPurchaseQueryReport record, @Param("example") CbsSuccessfulPurchaseQueryReportExample example);

    int updateByPrimaryKeySelective(CbsSuccessfulPurchaseQueryReport record);

    int updateByPrimaryKeyWithBLOBs(CbsSuccessfulPurchaseQueryReport record);

    int updateByPrimaryKey(CbsSuccessfulPurchaseQueryReport record);
}