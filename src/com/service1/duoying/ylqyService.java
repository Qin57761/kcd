package com.service1.duoying;

import java.util.List;
import com.model1.ylqy;

public interface ylqyService {

	//���ݱ�Ų�ѯ����
	public List<ylqy> findylqybyname(String c_name);
	
	//��ѯ��������
	public List<ylqy> finfylqy();
	
	//��ѯһ������
    public ylqy ylqymap(String ACCOUNT_NAME);
}
