package com.service1.loan;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import com.model1.icbc.erp.PageData;

public interface LoanOverdueService {
	//查询某一名单的操作记录
	List<PageData> selectResults(PageData pd);
	//查询电催form列表  客户信息+车辆信息+贷款方案信息
	PageData selectPhoneClientCarLoanInfo(PageData pd);
	//查询电催名单
	List<PageData> selectPhoneList(PageData pd);
	
	//查询一条逾期名单
	PageData selectOverdueOne(PageData pd);
	//修改客户逾期状态
	Integer updateOverdueStatus(PageData pd);
	//查询逾期名单
	List<PageData> selectOverdueList(PageData pd);
	//添加操作记录
	Integer addOperationResult(PageData pd);
	//定时任务-修改客户逾期天数
	Integer updateOverdueDay();
	
	//修改配置
	Integer updateConfig(PageData pd);
	//查询配置
	PageData selectConfig(PageData pd);
	//添加配置
	Integer addConfig(PageData pd);
	//在拖车完成中显示已受理页面上传的入库时间、地址、影像
	Map<String,Object> selectCool(Integer lol_id);
}
