package com.mapper.newAdd;

import java.util.List;

import com.model.newAdd.queryBx;
import com.model1.bx;

public interface queryBxMapper {
	// hzj ��ѯ����
	public int BxCounts();
	// hzj ��ѯȫ��������
	public List showBxAndKey(int start,int num);
	// hzj �õ� ��������ID 
	public int getNewId(String tableName,String databaseName);
	// hzj �������  
	public void addBX(queryBx bx);
	//���ݿͻ�������ѯ������Ϣ
	public List<bx> findbxbyc_name(String c_carno);
	//��ѯ��������
	public List<bx> findbx();

}
