package com.mapper;

import java.util.List;

import com.model.finance;

public interface financeMapper {

	     // ��� ������ˮ��Ϣ
	     public void  savefinance(finance fn);
	     
	     //�鿴������ˮ��Ϣ
	     public List<finance>  findfinance();
	     
	     
	     //��ҳ
	     public List<finance> findfinancelimit(int st,int ps);

	     //��ѯ����
	     public int findfinancecount();
}
