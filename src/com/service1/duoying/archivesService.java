package com.service1.duoying;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model1.archives;

public interface archivesService {
	
	//���ݶ�����Ų�ѯ������Ϣ
	public List<archives> findarchivesbyc_name(String c_carno);

	//��ѯȫ������ 
	public List<archives> findarchives();
	
	public archives archivesmap(String c_carno);
	
	//api �鳵��
	public archives Apiarchives(String c_carno,
			String r_item6,
			String query_type
			);
}
