package com.mapper1;

import java.util.List;
import com.model1.ylqy;

public interface ylqyMapper {

	//�������ݱ�Ų�ѯ����
	public List<ylqy> findylqybyname(String ACCOUNT_NAME);

	//��ѯ��������
	public List<ylqy> findylqy();
	
	//��ѯһ������
	public ylqy ylqymap(String ACCOUNT_NAME);
	
	//�������֤�Ų�ѯ����
	public ylqy findylqybycardid(String cardid);
}
