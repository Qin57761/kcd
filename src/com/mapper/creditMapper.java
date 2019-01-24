package com.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model.credit;

public interface creditMapper {
	
	
	public List<credit> zxbysum_bit(@Param("sum_bit")String sum_bit);
	//���˻�������
	public int htcount();
	//����
	public List<credit> httable(@Param("st")int st,@Param("ps")int ps);
	//���
    public int ecount();
    //����
    public List<credit> etable(@Param("st")int st,@Param("ps")int ps);
		//
    public List<credit> findmdid(@Param("mdid")String mdid,@Param("sum_bit")String sum_bit);
	
  //��ѯ������������
   public List<credit> search();
 // public  List<credit> findcredit();
  //�������
  public void save(credit credit);
  
  //��������
  public void upcredit(credit credit);
  //���¶���״̬
  public void upsubmit(credit credit);
  //��ҳ��ѯ
  public List<credit> findcredit(@Param("st")int st,@Param("ps")int ps);
  
  //�������״̬��ѯ����
  public int countnum(String sum_bit);
  //�ȴ���˲�ѯ
  public List<credit> dshtable(@Param("st")int st,@Param("ps")int ps);
  //�ȴ��������
  public int dshcount();
  public List<credit> ztlist(@Param("sum_bit")String sum_bit,@Param("st")int startPos,@Param("ps")int pageSize);
  //��ȡ����
  public int findcount();
  //��ҳ��ѯ
  public List<credit> findcredit1(@Param("mdid")int mdid,@Param("st")int st,@Param("ps")int ps);
  
  //��ȡ����
  public int findcount1(@Param("mdid")int mdid);
  //���ݱ�Ų�ѯ������Ϣ
  public Map findcreditbyid(int id);
  //ɾ������ ����id
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