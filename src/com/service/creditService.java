package com.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;

import com.model.credit;

public interface creditService {
	public List<credit> zxbysum_bit(String sum_bit);
	//���˻�������
	public int htcount();
	//����
	public List<credit> httable(int st,int ps);
	//���
    public int ecount();
    //����
    public List<credit> etable(int st,int ps);
	//
    public List<credit> findmdid(String mdid,String sum_bit);
	  //��ѯ������������
  //��ѯ������������
    public List<credit> search();
	 // public  List<credit> findcredit();
	  //�������
	  public void save(credit credit);
	  //��������
	  public void upcredit(credit credit);
	  //��ҳ��ѯ
	  public List<credit> findcredit(int startPos,int pageSize);
	  //�ȴ���˲�ѯ
	  public List<credit> dshtable(int startPos,int pageSize);
	  public List<credit> ztlist(String sum_bit,int startPos,int pageSize);
	  //�ȴ��������
	  public int dshcount();
	  //�������״̬��ѯ����
	  public int countnum(String sum_bit);
	  //��ҳ��ѯ
	  public List<credit> findcredit1(int mdid,int startPos,int pageSize);
	  //���¶���״̬
	  public void upsubmit(credit credit);
	  //��ȡ����
	  public int findcount();
	  //��ȡ����
	  public int findcount1(int mdid);
	  //���ݱ�Ų�ѯ������Ϣ
	  public Map findcreditbyid(@Param("id")int id);
	  //��ҳ
	  public void page(HttpServletRequest request,Model model);
	  //ɾ��
	  public void delcredit(int id);
	  // �������֤�� ��ѯ����
	  public Map findbysfz(String idcard);
	  //���ݲ�ѯ��Ϣ��ѯ�û�
	  public credit findcreditbyname(String name,String IDcard_num,String phone_num);
	  //�������������֤�Ų�ѯ���
	  public List<credit> findover(List<?> list);
	  //��˸���״̬
	  public void editzx(credit credit);
	//��ԭͼƬ
	  public void hyimg(credit credit);
}
