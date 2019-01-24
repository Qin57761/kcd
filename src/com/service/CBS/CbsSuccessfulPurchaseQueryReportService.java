package com.service.CBS;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.model.CBS.CbsSuccessfulPurchaseQueryReport;
import com.model.CBS.CbsSuccessfulPurchaseQueryReportExample;
import com.model.jbapi.jbzxapiuser;
import com.util.Page;
public interface CbsSuccessfulPurchaseQueryReportService {
	 	int countByExample(CbsSuccessfulPurchaseQueryReportExample example);
	 	//һ�Զ��ѯ
	    List<jbzxapiuser> OneToArr(Page page);
	    //�޸Ķ���״̬
	    int updateByOrderIdSelective(CbsSuccessfulPurchaseQueryReport record);
	    //��ҳ������������ѯ������
	    int OneToArrCount();
	    
	    int OneToArrCountSelective(Page page);
	    int deleteByExample(CbsSuccessfulPurchaseQueryReportExample example);

	    int deleteByPrimaryKey(Integer id);

	    int insert(CbsSuccessfulPurchaseQueryReport record);

	    int insertSelective(CbsSuccessfulPurchaseQueryReport record);

	    List<CbsSuccessfulPurchaseQueryReport> selectByExampleWithBLOBs(CbsSuccessfulPurchaseQueryReportExample example);

	    String selectByExample(CbsSuccessfulPurchaseQueryReportExample example);

	    CbsSuccessfulPurchaseQueryReport selectByPrimaryKey(Integer id);

	    int updateByExampleSelective(@Param("record") CbsSuccessfulPurchaseQueryReport record, @Param("example") CbsSuccessfulPurchaseQueryReportExample example);

	    int updateByExampleWithBLOBs(@Param("record") CbsSuccessfulPurchaseQueryReport record, @Param("example") CbsSuccessfulPurchaseQueryReportExample example);

	    int updateByExample(@Param("record") CbsSuccessfulPurchaseQueryReport record, @Param("example") CbsSuccessfulPurchaseQueryReportExample example);

	    int updateByPrimaryKeySelective(CbsSuccessfulPurchaseQueryReport record);

	    int updateByPrimaryKeyWithBLOBs(CbsSuccessfulPurchaseQueryReport record);

	    int updateByPrimaryKey(CbsSuccessfulPurchaseQueryReport record);
}
