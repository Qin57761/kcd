package com.service1.newAdd;

import java.util.ArrayList;

import com.model1.newAdd.CarModell;

public interface CarModelService {
	// ��ѯ����
	public CarModell selectCarNameById(int id);

	// ��ѯ����������ϸƷ��
	public ArrayList selectCarModellById(int id);

	// ��ѯ����
	public CarModell selectCarNameById_v2(int id);

	// ��ѯ����������ϸƷ��
	public ArrayList selectCarModellById_v2(int id);
}
