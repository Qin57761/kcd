package com.service;

import java.util.List;
import com.model.customer;

public interface customerService {
	       //��ֵ�춹 ����û���˾
		   public void addperson(customer customer);
		
		   //���ݹ�˾���Ʋ�ѯԱ��
		   public List<customer> findbycompany(String owencompany);	
		   //���ݵȼ���ѯԱ����Ϣ
		   public List<customer> findbylevel(String level,String owencompany);
		   //��֤key
		   public int keypeople(String ckey);
}
