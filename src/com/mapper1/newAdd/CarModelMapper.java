package com.mapper1.newAdd;
import java.util.ArrayList;

import com.model1.newAdd.CarModell;

public interface CarModelMapper {
	// ��ѯ����������ϸƷ�� 
	public ArrayList selectCarModellById(int id);
	// ��ѯ����
	public CarModell selectCarNameById(int id);
	
}
