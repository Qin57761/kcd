package com.service1;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.admin;

public interface adminService {
	
	//�û� ��½ ͬ��
	public Map adminlogin(String username,String userpass);
	//����id��ȡ�û���Ϣ
	public admin adminbyid(int id);
	//
	admin adminbygems_id(int gems_id);
}
