package com.service.icbc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.icbc.icbc;

public interface icbcService {
	//��ӹ��н�����Ϣ
	public void addicbc(icbc icbc);
	//���¹��н�����Ϣ
	public void upicbc(icbc icbc);
	//���ݶ������ ��ѯ
    public icbc findicbcbyorderid(String orderid);
	//findicbc ����
	public List<icbc> findicbc(int querytype,int bc_status);
	//����id��ѯ
	public icbc findicbcbyid(int id);

}
