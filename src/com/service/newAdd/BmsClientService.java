package com.service.newAdd;
import java.util.ArrayList;
import com.model.newAdd.BmsCpyclient;
public interface BmsClientService {
	/////////��½�û���(ҵ��������ҵ��Ա)����������Ϣ/////////
	//(ҵ�����ҵ��Ա)ģ����ѯ
	public ArrayList selectClientLikeManager(String ucid,String tname);
	//��ѯ��½�û�(ҵ�����ҵ��Ա)�µĿͻ�
	public ArrayList selectLoginUserClient(String ucid);
	
	
	/////////���Ȩ��(����)�¿�����ȫ����Ϣ////////////////
	//�޸�ĳ�ͻ�������Ա����
	public int updateClientToUserUp(String ucid,int tid);
	//ģ����ѯ
	public ArrayList selectClientLike(String tname);
	//�޸��û��������
	public void updateClientInfo(String ta,String tb,String tc,String td,int tid);
	//��ӿͻ� ����
	public int addBMSClient(BmsCpyclient bc);
	//�޸��û�  �Ƿ����
	public void updateClientStatus(int status,int tid);
	//ɾ���ͻ�
	public void deleteClientByTid(int tid);
	//��ѯ�����û��ĸ������
	public BmsCpyclient selectOneClientByTid(int tid);
	//��ѯ���пͻ�
	public ArrayList selectAllClient();
}
