package com.mapper;

import java.util.List;
import java.util.Map;

import com.model.customer;

public interface customerMapper {

	//��ֵ�춹 ����û���˾
	public void addperson(customer customer);
	
	//��½��֤
    public Map login(String username,String password);
    
   //���ݹ�˾���Ʋ�ѯԱ��
   public List<customer> findbycompany(String owencompany);
   //���ݵȼ���ѯԱ����Ϣ
   public List<customer> findbylevel(String level,String owencompany);
   //��֤key
   public int keypeople(String ckey);
   
   public Map ckymap1(String ckey);
}
