package com.mapper.zx;

import java.util.List;

import com.model.jbapi.jbzxapiuser;

public interface jbzxapiuserMapper {
    //���API���뷽 ����
	public void addapiuser(jbzxapiuser jbzxapiuser);
	//����appkey ��ѯ��Ϣ
	public jbzxapiuser findapiuserbyappkey(String appkey);
	//���½��
	public void  upmoney(jbzxapiuser jbzxapiuser);
	//ͨ��id��ѯ
	public jbzxapiuser findapiuserbyid(int id);
	//
	public List<jbzxapiuser> apiuserlist();
	//���²���
	public void upjbzxapiuser(jbzxapiuser jbzxapiuser);
}
