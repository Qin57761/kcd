package com.mapper1.erp_icbc;

import java.util.List;

import com.model1.icbc.erp.PageData;



public interface erp_fiveModelMapper {
    
	//������ѯ
	 List<PageData> findtolist(PageData pd);
	//���
	void save(PageData pd);
	//����id��ѯ
	PageData findbyid(PageData pd);
	//����ɾ��
	void deletebyid(PageData pd);
	//�����޸�
	void updatebyid(PageData pd);
	
}
