package com.service1.duoying;

import java.util.List;


import com.model1.mgcert;

public interface mgcertService {
	//���ݸ��±�� zjf_type ��ѯ
	public List<mgcert> certlist(int zjf_type);
	//���ݸ��±�� zjf_type ��ѯ
	public List<mgcert> carlist(int zjf_type);
	//���ݸ��±�� zjf_type ��ѯ
	public List<mgcert> newcarlist(int zjf_type);
	//ͬ����Ϣ���±��
	public void upmgcert(mgcert mg);
	//ͬ����Ϣ���±��
	public void upmgcar(mgcert mg);
	//ͬ����Ϣ���±��
	public void upmgnewcar(mgcert mg);
	//����id��ȡ�������Ϣ
	public mgcert mgcertAPI(int id);
	//����id��ȡ�������Ϣ
	public mgcert mgcarAPI(int id);
	//����id��ȡ�������Ϣ
	public mgcert mgnewcarAPI(int id);
	//����id��ȡ�������Ϣ
	public mgcert mgxcAPI(int id);
	  //�����id
    public List<Object> jkrID();
	//��ʽ 
    public List<mgcert> Apimgcert();
    
    public List<mgcert> Apijkxxmgcert(); 
    //��ʽ  Ѻ��
	public List<mgcert> Apimgcar();
	   
	public List<mgcert> Apijkxxmgcar(); 
	//��ʽ  Ѻ�³�
	public List<mgcert> Apimgnewcar();
	   
	public List<mgcert> Apijkxxmgnewcar(); 
    
    //���ݱ�Ų�ѯ��������
	public mgcert findcert(String gems_code);		
    //���ݱ�Ų�ѯ��������
	public mgcert findcar(String gems_code);
    //���ݱ�Ų�ѯ��������
	public mgcert findnewcar(String gems_code);	
	//��ѯ��������
	public List<mgcert> findmgcertlist();
	//��������ѯ
	public List<mgcert> findzsresult(int bc_status);
	//��������ѯ
	public List<mgcert> csmgcert();
	//��������ѯ
	public List<mgcert> csmgcar();
	//��������ѯ
	public List<mgcert> csmgnewcar();
	//Ѻ��
	public List<mgcert> findmgcar();
	//ѹ�³�
	public List<mgcert> findmgnewcar();
	public  List<mgcert> findmgcertbyid(int fsid);
	public  List<mgcert> findmgcarbyid(int fsid);
	public  List<mgcert> findmgnewcarbyid(int fsid);
}
