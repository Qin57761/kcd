package com.mapper1.erp_icbc;

import com.model1.icbc.erp.PageData;

public interface fs_detailsMapper {
	
	//���
    void save(PageData pd);
    //����
    void update(PageData pd);
    //������ѯ
    PageData findbyid(PageData pd);
  
}
