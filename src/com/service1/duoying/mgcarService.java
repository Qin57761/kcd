package com.service1.duoying;

import java.util.List;
import com.model1.mgcar;

public interface mgcarService {
	
	//���ݶ����Ų�ѯ������Ϣ
	public List<mgcar> findmgcarbygems_code(String gems_code); 
	
	//��ѯȫ������
	public List<mgcar> findmgcar();

}
