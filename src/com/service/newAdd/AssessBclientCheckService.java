package com.service.newAdd;
import java.util.ArrayList;

import com.model.newAdd.AssessBclientCheck;
import com.model.newAdd.AssessCars;

public interface AssessBclientCheckService {
	//�������
	public int addAssessBclientCheck(AssessBclientCheck assessBclientCheck);
	//��ѯȫ������
	public ArrayList selectAllAssessBclientCheck(int assessid);
	
}
