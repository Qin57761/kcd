package com.mapper.zx;

import com.model.jbapi.jbzxuser;

public interface jbzxuserMapper {
    //��Ӳ�ѯ��������û���Ϣ
	public void addjbzxuser(jbzxuser jbzxuser);
	//��ѯ
	public jbzxuser findjbzxuser(String loginname);
}
