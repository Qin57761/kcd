package com.service1.newAdd;

import java.util.ArrayList;
import java.util.List;

import com.model1.mgcert;
import com.model1.mgcert_result;
import com.model1.ylds;
import com.model1.ylqy;

public interface TestKJSmgcertService {
	//��ѯ����
	public mgcert TestKJSselectCaoZuo(int amid);
	//��ѯ modelTwo
	public List<ylds> TestKJSselectModelTwo(int mgcertId);
	public List<ylqy> TestKJSselectModelTwoByMomeny(int id);
	//��ѯ modelOne
	public ylqy TestKJSselectModelOne(String c_cardid);
	//��ѯ����Ѻ֤��Ϣ
	public ArrayList TestKJSselectAll(int status,int page,int size);
	//��ѯ����
	public int TestKJSselectAllCounts();
}
