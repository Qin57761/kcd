package com.service.zx;

import com.model.jbapi.jbzxuser;

public interface jbzxuserService {
    //��Ӳ�ѯ��������û���Ϣ
	public void addjbzxuser(jbzxuser jbzxuser);
	//��ѯ
	public jbzxuser findjbzxuser(String loginname);
}
