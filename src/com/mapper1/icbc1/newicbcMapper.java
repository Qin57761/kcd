package com.mapper1.icbc1;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.icbc.icbc;

public interface newicbcMapper {
	//ɾ��
	public void del_icbc(@Param("id") int id);
	//��ӹ��н�����Ϣ
	public void addicbc(icbc icbc);
	//���¹��н�����Ϣ
	public void upicbc(icbc icbc);
    //���ݶ������ ��ѯ
	public icbc findicbcbyorderid(String orderid);
	//���ݶ������ ��ѯ
	icbc findicbcbyorderid2(String orderid);
	//findicbc ����
	public List<icbc> findicbc(@Param("querytype")int querytype,@Param("bc_status")int bc_status);
	//����id��ѯ
	public icbc findicbcbyid(@Param("id")int id);
	//
	public void upicbc_tag(icbc icbc);
	//
	public icbc findlastid();
	//
	void up_fk(icbc icbc);
	//
	public List<icbc> searchicbc(
			@Param("time1")String time1,
			@Param("time2")String time2,
			@Param("querytype")int querytype,
			@Param("bc_status")int bc_status,
			@Param("gems_fs_id")int gems_fs_id,
			@Param("book_status")int book_status,
			@Param("card_status")int card_status,
			@Param("icbcname")String icbcname
			);
	//
	public int findicbccount();
	//
	List<icbc> kjs_zx(@Param("bc_status")int bc_status);
	//
	int kjs_zx_count();
}
