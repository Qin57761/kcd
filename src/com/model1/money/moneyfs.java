package com.model1.money;

public class moneyfs {
	private int id; 	//int(11) 	��  	  	 
	private int mid; 	//int(11) 	��  	  	fsid_�̻���˾ID 
	private int fsid;	//int(11) 	��  	  	ͬ�� 
	private int gemsid; 	//int(11) 	��  	  	�̻�ʦid 
	private float amount; 	//float(11,2) 	��  	  	��� 
	private int type; 	//tinyint(4) 	��  	0  	���ͣ�1��ֵ,2���������������� 
	private String dt_add; 	//datetime 	��  	  	 
	private String dt_edit; 	//datetime 	��  	  	 
	private int status; 	//tinyint(4) 	��  	0  	״̬ 
	private String remark; 	    //varchar(32) 	��  	  	��̱�ע 
	private int otherid; 	//int(11) 	��  	  	����id�ֶ�1 
	private int orderid ;	//int(11) 	��  	  	����ID 
	private int bintype ;	//tinyint(4) 	��  	0  	�����࣬0Ϊ�ֽ��ֵ��1Ϊ�ײ������� 
	private int mid_add ;	//int(11) 	��  	  	 
	private int mid_edit ;	//int(11) 	��  	  	 
	private int fctype ;	//tinyint(4) 	��  	0  	��������ã�0Ϊ������ֵ��1Ϊ�ֽ���ۣ�2Ϊ��ֵ�ۿ� 
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getOtherid() {
		return otherid;
	}
	public void setOtherid(int otherid) {
		this.otherid = otherid;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getBintype() {
		return bintype;
	}
	public void setBintype(int bintype) {
		this.bintype = bintype;
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
	public int getFctype() {
		return fctype;
	}
	public void setFctype(int fctype) {
		this.fctype = fctype;
	}


}
