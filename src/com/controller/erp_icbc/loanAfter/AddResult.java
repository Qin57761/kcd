package com.controller.erp_icbc.loanAfter;

import com.model1.icbc.erp.PageData;

/**
 * ������ڿͻ�������¼
 * @author ��ʮ����
 * 2019-3-21
 */
public class AddResult {
	//�������ֶ��������������
	public static PageData addResult(PageData pdsession,PageData pdOne){
		PageData addResult = new PageData();
		addResult.put("qryid",pdOne.get("id"));
		addResult.put("mid_add",pdsession.get("id"));
		addResult.put("mid_edit",pdsession.get("id"));
		addResult.put("type_id",pdOne.get("type_id"));
		addResult.put("type_status",pdOne.get("type_status"));
		addResult.put("remark", "�ֶ����,���ڿͻ�������");
		addResult.put("result_msg", "�ֶ����,���ڿͻ�������");
		addResult.put("icbc_id", pdOne.get("icbc_id"));
		addResult.put("result_value",addResult.toString());
		return addResult;
	}
}
