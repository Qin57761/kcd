package com.mapper1;

import java.util.List;
import com.model1.bank;

public interface bankMapper {
	//���ݱ�Ų�ѯ����
	public List<bank> findbankbycode(String code);	
	//��ѯȫ������
	public List<bank> findbank();
	//��ȡ��������
	public bank bankmap(String code);
	//��ȡ��������
	public bank bankmap1(int id);
}
