package com.mapper1;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.admin;

public interface adminMapper {

	//�û� ��½ ͬ��
	public Map adminlogin(@Param("username")String username,@Param("userpass")String userpass);

    //����id��ȡ�û���Ϣ
	public admin adminbyid(@Param("id")int id);
	
	 //����id��ȡ�û���Ϣ
	admin adminbygems_id(@Param("gems_id")int gems_id);




}
