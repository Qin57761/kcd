package com.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model.bb;

public interface bbMapper {
    //��ӱ������
	public void addbb(bb bb);
	//���ݶ�����Ų�ѯ����
	public Map findbb(@Param("orderid")String orderid);
	//���±���
	public void upbbxx(bb bb);
}
