package com.model.jbapi;


public class jbzx {
	private int id; 	//int(11) 	��  	  	 
	private int mid_add; 	//int(11) 	��  	  	 
	private int mid_edit; 	//int(11) 	��  	  	 
	private String dt_add; 	//datetime 	��  	  	 
	private String dt_edit; 	//datetime 	��  	  	 
	private String loginname; 	//varchar(32) 	��  	  	��¼�û��� 
	private String api_resultcode; 	//int(4) 	��  	  	�ӿڷ��ش��� 
	private String api_resultmsg; 	//varchar(255) 	��  	  	�ӿڷ�����Ϣ 
	private String bc_status; 	//tinyint(4) 	��  	  	����״̬ 
	private int gems_id;	//int(11) 	��  	  	 
	private int gems_fs_id; 	//int(11) 	��  	  	 
	private String gems_code; 	//varchar(20) 	��  	  	�������� 
	private String smscode; 	//varchar(10) 	��  	  	������֤�� 
	private int query_type;//	tinyint(4) 	��  	0  	���ͣ���ʱһ�֣�Ϊ0 
	private String c_name; 	//varchar(20) 	��  	  	���� 
	private String c_tel; 	//varchar(15) 	��  	  	�ֻ����� 
	private String c_cardno; 	//varchar(32) 	��  	  	���֤ 
	private String api_result; 	//longtext 	��  	  	API�����������ݰ� 
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
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getApi_resultcode() {
		return api_resultcode;
	}
	public void setApi_resultcode(String api_resultcode) {
		this.api_resultcode = api_resultcode;
	}
	public String getApi_resultmsg() {
		return api_resultmsg;
	}
	public void setApi_resultmsg(String api_resultmsg) {
		this.api_resultmsg = api_resultmsg;
	}
	public String getBc_status() {
		return bc_status;
	}
	public void setBc_status(String bc_status) {
		this.bc_status = bc_status;
	}
	public int getGems_id() {
		return gems_id;
	}
	public void setGems_id(int gems_id) {
		this.gems_id = gems_id;
	}
	public int getGems_fs_id() {
		return gems_fs_id;
	}
	public void setGems_fs_id(int gems_fs_id) {
		this.gems_fs_id = gems_fs_id;
	}
	public String getGems_code() {
		return gems_code;
	}
	public void setGems_code(String gems_code) {
		this.gems_code = gems_code;
	}
	public String getSmscode() {
		return smscode;
	}
	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}
	public int getQuery_type() {
		return query_type;
	}
	public void setQuery_type(int query_type) {
		this.query_type = query_type;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getC_tel() {
		return c_tel;
	}
	public void setC_tel(String c_tel) {
		this.c_tel = c_tel;
	}
	public String getC_cardno() {
		return c_cardno;
	}
	public void setC_cardno(String c_cardno) {
		this.c_cardno = c_cardno;
	}
	public String getApi_result() {
		return api_result;
	}
	public void setApi_result(String api_result) {
		this.api_result = api_result;
	}



  
  
}
