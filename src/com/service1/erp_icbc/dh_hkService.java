package com.service1.erp_icbc;
import java.util.List;
import java.util.Map;
import com.model1.icbc.erp.HK;
import com.model1.icbc.erp.PageData;
public interface dh_hkService {
	//������ѯ
	 List<PageData> findtolist(PageData pd);
	 int addhk(HK hk);
	 //����id��ѯĳ���û��Ļ���ͼ
	 List<Map<String,String>> shkb(String id);
	//���
	void save(PageData pd);
	//����ɾ��
	void deletebyid(PageData pd);
}
