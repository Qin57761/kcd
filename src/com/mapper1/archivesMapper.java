package com.mapper1;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model1.archives;






public interface archivesMapper {

	//���ݿͻ�������ѯ������Ϣ
	public List<archives> findarchivesbyc_name(String c_carno);
	
	//��ѯ��������
	public List<archives> findarchives();
	
	public archives archivesmap(String c_carno);
	
	//api �鳵��
	public archives Apiarchives(@Param("c_carno")String c_carno,
			@Param("r_item6")String r_item6,
			@Param("query_type")String query_type
			);
}
