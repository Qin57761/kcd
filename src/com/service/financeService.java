package com.service;

import java.util.List;

import com.model.finance;

public interface financeService {
	 // ��� ������ˮ��Ϣ
    public void  savefinance(finance fn);
    
    //�鿴������ˮ��Ϣ
    public List<finance>  findfinance();
    
    
  //��ҳ
    public List<finance> findfinancelimit(int st,int ps);
  //��ѯ����
    public int findfinancecount();
}
