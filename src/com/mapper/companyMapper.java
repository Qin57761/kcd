package com.mapper;

import java.util.Map;

import com.model.company;

public interface companyMapper {

	 // ��ӹ�˾Ա��
	 public void addcompany(company company);

	 //��½��֤
	 public Map login(String username,String password);
	
	 //���ݹ�˾���Ʋ�ѯ��˾��Ϣ
	 public Map findcompany(String company);
	
	 //��ֵ
	 public void upcompany(company company);
	 
	 //��֤keyֵ
	 public int ckynum(String ckey);
	 
	 public Map ckymap(String ckey);
}
