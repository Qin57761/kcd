package com.service1.duoying;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model1.carmodel;

public interface carmodelService {

	//�������ݱ�Ų�ѯ����
			public List<carmodel> findcarmodelbyid(int id);
			
			//��ѯ��������
			public List<carmodel> findcarmodel();
			public carmodel modelmap(int id);
			//
			carmodel findcarbyid(int id);
}
