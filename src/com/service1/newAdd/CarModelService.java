package com.service1.newAdd;

import java.util.ArrayList;

import com.model1.newAdd.CarModell;

public interface CarModelService {
	//��ѯ����
	public CarModell selectCarNameById(int id);
	// ��ѯ����������ϸƷ�� 
	public ArrayList selectCarModellById(int id);
}
