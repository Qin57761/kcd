package com.mapper.zx;

import java.util.List;

import com.model.zxjb;


public interface jbzxMapper{
    //������Ų�ѯ��¼���
	public void addjbzx(zxjb zxjb);
	//������Ÿ���
	public void upzxjb(zxjb zxjb);
	//���ݶ�����ѯ
	public zxjb findzxjb(String gems_code);
	//����
	public List<zxjb> jbzxlist();
	//�������
	public zxjb findjbzxbyid(int id);
}
