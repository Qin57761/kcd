package com.mapper.newAdd;
import java.util.ArrayList;

import com.model.newAdd.AssessBclientCheck;

public interface AssessBclientCheckMapper {
	//�������
	public int addAssessBclientCheck(AssessBclientCheck assessBclientCheck);
	//��ѯȫ������
	public ArrayList selectAllAssessBclientCheck(int assessid);
}
