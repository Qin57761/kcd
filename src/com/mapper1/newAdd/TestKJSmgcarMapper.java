package com.mapper1.newAdd;
import java.util.ArrayList;
import java.util.List;

import com.model.newAdd.ssm;
import com.model1.mgcar;
import com.model1.mgcert;
import com.model1.ylds;
import com.model1.ylqy;
public interface TestKJSmgcarMapper {
	//��ѯ����
	public mgcar TestKJSselectCaoZuo(int amid);
	//��ѯ modelTwo
	public List<ylds> TestKJSselectModelTwo(int mgcertId);
	public List<ylqy> TestKJSselectModelTwoByMomeny(int id);
	//��ѯ modelOne
	public ylqy TestKJSselectModelOne(String c_cardid);
	//��ѯ����Ѻ֤
	public ArrayList TestKJSselectAll(int status,int page,int size);
	//��ѯ����
	public int TestKJSselectAllCounts();
}
