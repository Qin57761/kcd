package com.mapper1.erp_icbc;

import java.util.List;

import com.model1.icbc.erp.PageData;

public interface erp_wdrwMapper {
	//��ѯ����
	List<PageData> icbc_list(PageData pd);
	//��ѯ��������
    PageData icbc_form(PageData pd);
    //���
    void save(PageData pd);
    //�޸�
    void update(PageData pd);
}
