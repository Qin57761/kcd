/**
 * 2017-10-30
 * @author zhuyilong
 * ����ǩԼ
 */
package com.model1;

public class ylqy {

	private int id;//���ݱ��
	private int mid_add;//������ID
	private int mid_edit;//���༭��ID
	private String dt_add;//����ʱ��
	private String dt_edit;//������ʱ��
	private int gems_fs_id;//���˵�id
	private int gems_id;//�̻�ʦid
	private String CURRENCY;//��������
	private String BANK_CODE;//���д���
	private String ACCOUNT_TYPE;//�˺�����,00���п�,01����
	private String ACCOUNT_NO;//���п�����ۺ���
	private String ACCOUNT_NAME;//���п�������ϵ�����������
	private String PROVINCE;//����������ʡ,ȥ��ʡ,������
	private String CITY;//������������
	private String BANK_NAME;//����������
	private String ACCOUNT_PROP;//0˽�ˣ�1��˾������ʱ��Ĭ��Ϊ˽��0
	private String ID_TYPE;//0�����֤,1: ���ڲ���2������,3.����֤,4.ʿ��֤��5. �۰ľ��������ڵ�ͨ��֤,6. ̨��ͬ�������ڵ�ͨ��֤,7. ��ʱ���֤,8. ����˾���֤,9. ����֤, X.����֤��
	private String c_cardid;//֤����
	private String TEL;//�ֻ���
	private String remark;//��ע
	private String checkid;//ǩԼid
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMid_add() {
		return mid_add;
	}
	public void setMid_add(int mid_add) {
		this.mid_add = mid_add;
	}
	public int getMid_edit() {
		return mid_edit;
	}
	public void setMid_edit(int mid_edit) {
		this.mid_edit = mid_edit;
	}
	public String getDt_add() {
		return dt_add;
	}
	public void setDt_add(String dt_add) {
		this.dt_add = dt_add;
	}
	public String getDt_edit() {
		return dt_edit;
	}
	public void setDt_edit(String dt_edit) {
		this.dt_edit = dt_edit;
	}
	public int getGems_fs_id() {
		return gems_fs_id;
	}
	public void setGems_fs_id(int gems_fs_id) {
		this.gems_fs_id = gems_fs_id;
	}
	public int getGems_id() {
		return gems_id;
	}
	public void setGems_id(int gems_id) {
		this.gems_id = gems_id;
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}
	public String getBANK_CODE() {
		return BANK_CODE;
	}
	public void setBANK_CODE(String bANK_CODE) {
		BANK_CODE = bANK_CODE;
	}
	public String getACCOUNT_TYPE() {
		return ACCOUNT_TYPE;
	}
	public void setACCOUNT_TYPE(String aCCOUNT_TYPE) {
		ACCOUNT_TYPE = aCCOUNT_TYPE;
	}
	public String getACCOUNT_NO() {
		return ACCOUNT_NO;
	}
	public void setACCOUNT_NO(String aCCOUNT_NO) {
		ACCOUNT_NO = aCCOUNT_NO;
	}
	public String getACCOUNT_NAME() {
		return ACCOUNT_NAME;
	}
	public void setACCOUNT_NAME(String aCCOUNT_NAME) {
		ACCOUNT_NAME = aCCOUNT_NAME;
	}
	public String getPROVINCE() {
		return PROVINCE;
	}
	public void setPROVINCE(String pROVINCE) {
		PROVINCE = pROVINCE;
	}
	public String getCITY() {
		return CITY;
	}
	public void setCITY(String cITY) {
		CITY = cITY;
	}
	public String getBANK_NAME() {
		return BANK_NAME;
	}
	public void setBANK_NAME(String bANK_NAME) {
		BANK_NAME = bANK_NAME;
	}
	public String getACCOUNT_PROP() {
		return ACCOUNT_PROP;
	}
	public void setACCOUNT_PROP(String aCCOUNT_PROP) {
		ACCOUNT_PROP = aCCOUNT_PROP;
	}
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}
	public String getC_cardid() {
		return c_cardid;
	}
	public void setC_cardid(String c_cardid) {
		this.c_cardid = c_cardid;
	}
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCheckid() {
		return checkid;
	}
	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}
	
	
	
}
