package com.mapper1.erp_icbc;

import java.util.List;
import com.model1.icbc.erp.PageData;

public interface erp_loanBeforeMapper {
	//������ѯ
    List<PageData> findList(PageData pd);
    //����id��ѯ����
  	PageData findById(PageData pd);
}
