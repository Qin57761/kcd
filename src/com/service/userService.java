package com.service;

import java.util.List;
import java.util.Map;

import com.model.user;

public interface userService {
	    //����û�
		public void adduser(user user);
		//��½��֤
		public Map finduser(String username,String password);
		//��½��֤
		public Map finduser1(String username);
		//�û�����
		public List<user> userfind();
		//���ݱ�Ų�ѯ�û���Ϣ
		public Map finduserbyid(int uid);
}
