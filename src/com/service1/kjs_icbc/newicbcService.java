package com.service1.kjs_icbc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.icbc.icbc;
import com.model.icbc.icbc_result;

public interface newicbcService {
	//ɾ��
	public void del_icbc(int id);
	//��ӹ��н�����Ϣ
	public void addicbc(icbc icbc);
	//���¹��н�����Ϣ
	public void upicbc(icbc icbc);
    //���ݶ������ ��ѯ
	public icbc findicbcbyorderid(String orderid);
	//���ݶ������ ��ѯ
	icbc findicbcbyorderid2(String orderid);
	//findicbc ����
	public List<icbc> findicbc(int querytype,int bc_status);
	//����id��ѯ
	public icbc findicbcbyid(int id);
	//
	public void upicbc_tag(icbc icbc);
	//
	public icbc findlastid();
	//
	void up_fk(icbc icbc);
	//
	public List<icbc> searchicbc(String time1,
			String time2,
			int querytype,
			int bc_status,
			int gems_fs_id,
			int book_status,
			int card_status,
			String icbcname
			);
	//
	public int findicbccount();
	//
	List<icbc> kjs_zx(int bc_status);
	//
	int kjs_zx_count();
}
