package com.mapper1;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model1.carmodel;

public interface carmodelMapper {

	   //�������ݱ�Ų�ѯ����
		public List<carmodel> findcarmodelbyid(int id);
		
		//��ѯ��������
		public List<carmodel> findcarmodel();
		
		
		public carmodel modelmap(int id);
		
		//
		carmodel findcarbyid(@Param("id")int id);
}
