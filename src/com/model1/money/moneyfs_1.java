package com.model1.money;

public class moneyfs_1 {
	private int id; 	//int(11) 	��  	  	 
	private int mid; 	//int(11) 	��  	  	fsid_�̻���˾ID 
	private int fsid; 	//int(11) 	��  	  	ͬ�� 
	private int gemsid ;	//int(11) 	��  	  	�̻�ʦid 
	private float amount; //	float(11,2) 	��  	  	��� 
	private String dt_add ;//	datetime 	��  	  	 
	private String dt_edit; //	datetime 	��  	  	 
	private String remark ;//	varchar(32) 	��  	  	��̱�ע 
	private int moneyid ;	//int(11) 	��  	  	Ǯ����¼ID 
	private int status ;	//tinyint(4) 	��  	  	 
	private int bintype ;	//tinyint(4) 	��  	0  	�����࣬0Ϊ�ֽ��ֵ��1Ϊ�ײ������� 
	private int fctype ;	//tinyint(4) 	��  	0  	��������ã�0Ϊ������ֵ��1Ϊ�ֽ���ۣ�2Ϊ��ֵ�ۿ� 
	private int bc_type ;
	private int orderid;
	private int type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public int getFsid() {
		return fsid;
	}
	public void setFsid(int fsid) {
		this.fsid = fsid;
	}
	public int getGemsid() {
		return gemsid;
	}
	public void setGemsid(int gemsid) {
		this.gemsid = gemsid;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getMoneyid() {
		return moneyid;
	}
	public void setMoneyid(int moneyid) {
		this.moneyid = moneyid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getBintype() {
		return bintype;
	}
	public void setBintype(int bintype) {
		this.bintype = bintype;
	}
	public int getFctype() {
		return fctype;
	}
	public void setFctype(int fctype) {
		this.fctype = fctype;
	}
	public int getBc_type() {
		return bc_type;
	}
	public void setBc_type(int bc_type) {
		this.bc_type = bc_type;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}


}
