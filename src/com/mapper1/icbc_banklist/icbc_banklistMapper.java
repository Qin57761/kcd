package com.mapper1.icbc_banklist;

import java.util.List;

import com.model1.icbc.erp.PageData;

public interface icbc_banklistMapper {

	// ��ȡicbc_banklist ������Ϣ
	List<PageData> geticbc_banklist();

	// ���
	void saveicbc_banklist(PageData pd);

	// �༭
	void upicbc_banklist(PageData pd);

	// ����fsid��ѯ
	List<PageData> geticbc_banklistbyID(PageData pd);

	PageData geticbc_bank(int id);
}
