package com.service;

import java.util.Map;

import com.model.company;

public interface companyService {

	    // ��ӹ�˾Ա��
		public void addcompany(company company);
		//���ݹ�˾���Ʋ�ѯ��˾��Ϣ
		public Map findcompany(String company);
		//����beans
		public void upcompany(company company);
		//��֤keyֵ
		 public int ckynum(String ckey);
		 
		 public Map ckymap(String ckey);
}
