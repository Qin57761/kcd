package com.service.newAdd;
import java.util.ArrayList;

import com.model.newAdd.AssessCars;

public interface AssessCarsService {
	//�޸ĵ�������
	public int updateOneACarsById(AssessCars assessCars);
	//ɾ����������
	public void deleteOneACarsById(int id);
	//��ѯ��������
	public AssessCars selectOneACarsById(int id);
	//��ѯ����
	public int getSumAssessCars();
	//��ѯȫ������
	public ArrayList selectAllAssessCars(int page,int size);
	
	
}
