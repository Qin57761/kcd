package com.mapper.icbc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.icbc.icbc;

public interface icbcMapper {

	//��ӹ��н�����Ϣ
	public void addicbc(icbc icbc);
	//���¹��н�����Ϣ
	public void upicbc(icbc icbc);
    //���ݶ������ ��ѯ
	public icbc findicbcbyorderid(String orderid);
	//findicbc ����
	public List<icbc> findicbc(@Param("querytype")int querytype,@Param("bc_status")int bc_status);
	//����id��ѯ
	public icbc findicbcbyid(@Param("id")int id);

}
