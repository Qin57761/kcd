package com.service1.duoying;

import java.util.List;


import com.model1.thjl;

public interface thjlService {

	//����������ѯ��������
		public List<thjl> findthjlbyc_name(String c_name);
		
		//��ѯ��������
		public List<thjl> findthjl();
		
		public thjl thjlmap(String c_name);
}
