package com.service.CBS;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.mapper.CBS.CbsSuccessfulPurchaseQueryReportMapper;
import com.model.CBS.CbsSuccessfulPurchaseQueryReport;
import com.model.CBS.CbsSuccessfulPurchaseQueryReportExample;
import com.model.jbapi.jbzxapiuser;
import com.util.Page;
@Service
public class CbsSuccessfulPurchaseQueryReportServiceImpl implements CbsSuccessfulPurchaseQueryReportService{
	@Resource
	private CbsSuccessfulPurchaseQueryReportMapper cspqr;
	
	@Override
	public int countByExample(CbsSuccessfulPurchaseQueryReportExample example) {
		// TODO Auto-generated method stub
		return 0;
	}
	//һ�Զ��ѯ
    public List<jbzxapiuser> OneToArr(Page page){
    	return cspqr.OneToArr(page);
    }
    
    //�޸Ķ���״̬
    public int updateByOrderIdSelective(CbsSuccessfulPurchaseQueryReport record){
    	return cspqr.updateByOrderIdSelective(record);
    }
    //��ҳ������������ѯ������
    public int OneToArrCount(){
    	return cspqr.OneToArrCount();
    }
    
    
    public int OneToArrCountSelective(Page page){
    	return cspqr.OneToArrCountSelective(page);
    }
	@Override
	public int deleteByExample(CbsSuccessfulPurchaseQueryReportExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(CbsSuccessfulPurchaseQueryReport record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(CbsSuccessfulPurchaseQueryReport record) {
		// TODO Auto-generated method stub
		return cspqr.insertSelective(record);
	}

	@Override
	public List<CbsSuccessfulPurchaseQueryReport> selectByExampleWithBLOBs(
			CbsSuccessfulPurchaseQueryReportExample example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectByExample(CbsSuccessfulPurchaseQueryReportExample example) {
		// TODO Auto-generated method stub
		return cspqr.selectByExample(example);
	}

	@Override
	public CbsSuccessfulPurchaseQueryReport selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByExampleSelective(CbsSuccessfulPurchaseQueryReport record,
			CbsSuccessfulPurchaseQueryReportExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExampleWithBLOBs(CbsSuccessfulPurchaseQueryReport record,
			CbsSuccessfulPurchaseQueryReportExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(CbsSuccessfulPurchaseQueryReport record,
			CbsSuccessfulPurchaseQueryReportExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(CbsSuccessfulPurchaseQueryReport record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(CbsSuccessfulPurchaseQueryReport record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(CbsSuccessfulPurchaseQueryReport record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
