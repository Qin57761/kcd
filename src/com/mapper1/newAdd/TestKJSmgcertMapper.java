package com.mapper1.newAdd;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.newAdd.ssm;
import com.model1.mgcert;
import com.model1.mgcert_result;
import com.model1.ylds;
import com.model1.ylqy;
public interface TestKJSmgcertMapper {
	//��ѯ����
	public mgcert TestKJSselectCaoZuo(int amid);
	//��ѯ modelTwo
	public List<ylds> TestKJSselectModelTwo(int mgcertId);
	public List<ylqy> TestKJSselectModelTwoByMomeny(int id);
	//��ѯ modelOne
	public ylqy TestKJSselectModelOne(String c_cardid);
	//��ѯ����Ѻ֤
	public ArrayList TestKJSselectAll(@Param("status") int status,@Param("page") int page,@Param("size") int size);
	//��ѯ����
	public int TestKJSselectAllCounts();
}
