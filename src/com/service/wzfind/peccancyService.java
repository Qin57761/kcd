package com.service.wzfind;

import java.util.List;

import com.model.wzfind.peccancy;

public interface peccancyService {
	//Υ�²�ѯ
    public void addpeccancy(peccancy peccancy);
    //����API���ؽ��
    public void uppeccancy(peccancy peccancy);
    //���¶���״̬
    public void upporderstate(peccancy peccancy);
    //����
    public void uporderno(peccancy peccancy);
    //����ѯ
    public List<peccancy> peccancylist();
    //����id��ѯpeccancy
    public peccancy findpeccancybyid(int id);
}
