package com.mapper1.erp_icbc;
import java.util.List;
import java.util.Map;
import com.model1.icbc.erp.HK;
import com.model1.icbc.erp.PageData;
public interface dh_hkMapper {
	 List<PageData> findtolist(PageData pd);
	 //��ӻ�����Ϣ
	 int addhk(HK hk);
	 //�������� ����
	 List<Map<String,String>> shkb(String id);
}
