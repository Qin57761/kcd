package com.service1.erp_icbc;

import java.util.List;

import com.model1.icbc.erp.PageData;

public interface erp_loanBeforeService{
	//������ѯ
    List<PageData> findList(PageData pd);
    //��ѯ���� ����id��ѯ
  	PageData findById(PageData pd);
}	
