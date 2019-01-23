package com.service.newAdd;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.model.newAdd.BmsUser;
import com.model.newAdd.pdfdownload;

public interface BmsUserService {
////////////////////�Ŷӹ���////////////////////	
	// ��ѯ��½�û����Ŷ���Ա
	public ArrayList selectLoginUserTeam(int upid);
	
	
////////////////////�û�����////////////////////		
	// �޸��û�����Ȩ�ޣ�
	public void updateUserDeptno(int deptno,int uid);
	//ģ����ѯ
	public ArrayList selectUserLike(String uname);
	//ɾ���û�
	public void deleteUserByUid(int uid);
	//�޸��û�  �Ƿ����
	public int updateUserStatus(int status,int uid);
	//BMS ��½����֤Ȩ��
	public Map checkUser(String username,String password);
	//�����ֱ��ѯ
	public ArrayList selectOtherUser(int deptno);
	//��ѯ��ϵͳ���е��û�
	public ArrayList selectAllUser();
	//����û�ʱ��ѯ�Ƿ��ظ�
	public BmsUser addUserBySelect(String username);
	//���ϵͳ�û�
	public int addBMSUser(BmsUser bu);
	
}
