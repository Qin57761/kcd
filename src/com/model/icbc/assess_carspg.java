package com.model.icbc;

import java.sql.Date;

public class assess_carspg {
	private int id ;	//int(11) 	��  	  	 
	private int carpg_asid 	;//int(11) 	��  	  	��Ӧassess_cars��ID 
	private String carpg_owner; //	varchar(255) 	��  	  	������������Ϣ 
	private String carpg_rzcode; //	varchar(64) 	��  	  	��֤��� 
	private int carpg_owntp ;//	tinyint(2) 	��  	  	˽��/��˾/���� 
	private int carpg_usetp ;//	tinyint(2) 	��  	0  	Ӫ��/��Ӫ�� 
	private int carpg_km ;	//int(8) 	��  	  	��ʻ���� 
	private String carpg_vincode ;//	varchar(64) 	��  	  	vin 
	private Date carpg_cardays; //	date 	��  	  	��ʼ�Ǽ����� 
	private String carpg_gzxh ;	//varchar(128) 	��  	  	�����ͺ� 
	private int carpg_cartp; //	tinyint(2) 	��  	  	�������ͣ���ͨ�ͳ�/����С�� 
	private String carpg_pl 	;//varchar(8) 	��  	  	��������2.4L 
	private String carpg_fdjcode ;//	varchar(64) 	��  	  	�������� 
	private Date carpg_ccrq ;	//date 	��  	  	�������� 
	private int carpg_clcl1 ;	//tinyint(2) 	��  	  	��������_ԭʼ��Ʊ 
	private int carpg_clcl2 ;	//tinyint(2) 	��  	  	��������_�����Ǽ�֤�� 
	private int carpg_clcl3 ;	///tinyint(2) 	��  	  	��������_��������˰֤ 
	private int carpg_clcl4 ;	//tinyint(2) 	��  	  	��������_������ʻ֤ 
	private int carpg_clcl5 ;	//tinyint(2) 	��  	  	��������_���˴���(���֤) 
	private int carpg_clcl6 ;	//tinyint(2) 	��  	  	��������_����֤�� 
	private int carpg_mudi1 ;	//tinyint(2) 	��  	  	���� 
	private int carpg_mudi2 ;	//tinyint(2) 	��  	  	ת�� 
	private int carpg_mudi3 ;	//tinyint(2) 	��  	  	���� 
	private int carpg_mudi4 ;	//tinyint(2) 	��  	  	�û� 
	private int carpg_mudi5 ;	//tinyint(2) 	��  	  	��Ѻ 
	private int carpg_mudi6 ;	//tinyint(2) 	��  	  	���� 
	private int carpg_mudi7 ;	//tinyint(2) 	��  	  	��ѯ 
	private int carpg_mudi8 ;	//tinyint(2) 	��  	  	˾���ٲ� 
	private Date carpg_pgdt; //	date 	��  	  	������׼�� 
	private float carpg_price ;	//float(11,2) 	��  	  	������ 
	private String dt_add ;	//timestamp 	��  	CURRENT_TIMESTAMP  	 
	private String dt_edit ;	//timestamp 	��  	CURRENT_TIMESTAMP  	 
	private String carpg_carpn ;	//varchar(32) 	��  	  	���� 
	private String carpg_color ;	//varchar(8) 	��  	  	��ɫ�ַ� 
	private int carpg_jsff ;	//tinyint(2) 	��  	0  	���㷽�� 
	private float carpg_newprice ;//	float(11,2) 	��  	  	�³��ۼ� 
	private float carpg_score ;	//float(5,2) 	��  	  	���� 
	private float carpg_cxl ;	//float(5,2) 	��  	  	������ 
	private String address ;	//varchar(255) 	��  	NULL  	��ַ 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCarpg_asid() {
		return carpg_asid;
	}
	public void setCarpg_asid(int carpg_asid) {
		this.carpg_asid = carpg_asid;
	}
	public String getCarpg_owner() {
		return carpg_owner;
	}
	public void setCarpg_owner(String carpg_owner) {
		this.carpg_owner = carpg_owner;
	}
	public String getCarpg_rzcode() {
		return carpg_rzcode;
	}
	public void setCarpg_rzcode(String carpg_rzcode) {
		this.carpg_rzcode = carpg_rzcode;
	}
	public int getCarpg_owntp() {
		return carpg_owntp;
	}
	public void setCarpg_owntp(int carpg_owntp) {
		this.carpg_owntp = carpg_owntp;
	}
	public int getCarpg_usetp() {
		return carpg_usetp;
	}
	public void setCarpg_usetp(int carpg_usetp) {
		this.carpg_usetp = carpg_usetp;
	}
	public int getCarpg_km() {
		return carpg_km;
	}
	public void setCarpg_km(int carpg_km) {
		this.carpg_km = carpg_km;
	}
	public String getCarpg_vincode() {
		return carpg_vincode;
	}
	public void setCarpg_vincode(String carpg_vincode) {
		this.carpg_vincode = carpg_vincode;
	}
	public Date getCarpg_cardays() {
		return carpg_cardays;
	}
	public void setCarpg_cardays(Date carpg_cardays) {
		this.carpg_cardays = carpg_cardays;
	}
	public String getCarpg_gzxh() {
		return carpg_gzxh;
	}
	public void setCarpg_gzxh(String carpg_gzxh) {
		this.carpg_gzxh = carpg_gzxh;
	}
	public int getCarpg_cartp() {
		return carpg_cartp;
	}
	public void setCarpg_cartp(int carpg_cartp) {
		this.carpg_cartp = carpg_cartp;
	}
	public String getCarpg_pl() {
		return carpg_pl;
	}
	public void setCarpg_pl(String carpg_pl) {
		this.carpg_pl = carpg_pl;
	}
	public String getCarpg_fdjcode() {
		return carpg_fdjcode;
	}
	public void setCarpg_fdjcode(String carpg_fdjcode) {
		this.carpg_fdjcode = carpg_fdjcode;
	}
	public Date getCarpg_ccrq() {
		return carpg_ccrq;
	}
	public void setCarpg_ccrq(Date carpg_ccrq) {
		this.carpg_ccrq = carpg_ccrq;
	}
	public int getCarpg_clcl1() {
		return carpg_clcl1;
	}
	public void setCarpg_clcl1(int carpg_clcl1) {
		this.carpg_clcl1 = carpg_clcl1;
	}
	public int getCarpg_clcl2() {
		return carpg_clcl2;
	}
	public void setCarpg_clcl2(int carpg_clcl2) {
		this.carpg_clcl2 = carpg_clcl2;
	}
	public int getCarpg_clcl3() {
		return carpg_clcl3;
	}
	public void setCarpg_clcl3(int carpg_clcl3) {
		this.carpg_clcl3 = carpg_clcl3;
	}
	public int getCarpg_clcl4() {
		return carpg_clcl4;
	}
	public void setCarpg_clcl4(int carpg_clcl4) {
		this.carpg_clcl4 = carpg_clcl4;
	}
	public int getCarpg_clcl5() {
		return carpg_clcl5;
	}
	public void setCarpg_clcl5(int carpg_clcl5) {
		this.carpg_clcl5 = carpg_clcl5;
	}
	public int getCarpg_clcl6() {
		return carpg_clcl6;
	}
	public void setCarpg_clcl6(int carpg_clcl6) {
		this.carpg_clcl6 = carpg_clcl6;
	}
	public int getCarpg_mudi1() {
		return carpg_mudi1;
	}
	public void setCarpg_mudi1(int carpg_mudi1) {
		this.carpg_mudi1 = carpg_mudi1;
	}
	public int getCarpg_mudi2() {
		return carpg_mudi2;
	}
	public void setCarpg_mudi2(int carpg_mudi2) {
		this.carpg_mudi2 = carpg_mudi2;
	}
	public int getCarpg_mudi3() {
		return carpg_mudi3;
	}
	public void setCarpg_mudi3(int carpg_mudi3) {
		this.carpg_mudi3 = carpg_mudi3;
	}
	public int getCarpg_mudi4() {
		return carpg_mudi4;
	}
	public void setCarpg_mudi4(int carpg_mudi4) {
		this.carpg_mudi4 = carpg_mudi4;
	}
	public int getCarpg_mudi5() {
		return carpg_mudi5;
	}
	public void setCarpg_mudi5(int carpg_mudi5) {
		this.carpg_mudi5 = carpg_mudi5;
	}
	public int getCarpg_mudi6() {
		return carpg_mudi6;
	}
	public void setCarpg_mudi6(int carpg_mudi6) {
		this.carpg_mudi6 = carpg_mudi6;
	}
	public int getCarpg_mudi7() {
		return carpg_mudi7;
	}
	public void setCarpg_mudi7(int carpg_mudi7) {
		this.carpg_mudi7 = carpg_mudi7;
	}
	public int getCarpg_mudi8() {
		return carpg_mudi8;
	}
	public void setCarpg_mudi8(int carpg_mudi8) {
		this.carpg_mudi8 = carpg_mudi8;
	}
	public Date getCarpg_pgdt() {
		return carpg_pgdt;
	}
	public void setCarpg_pgdt(Date carpg_pgdt) {
		this.carpg_pgdt = carpg_pgdt;
	}
	public float getCarpg_price() {
		return carpg_price;
	}
	public void setCarpg_price(float carpg_price) {
		this.carpg_price = carpg_price;
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
	public String getCarpg_carpn() {
		return carpg_carpn;
	}
	public void setCarpg_carpn(String carpg_carpn) {
		this.carpg_carpn = carpg_carpn;
	}
	public String getCarpg_color() {
		return carpg_color;
	}
	public void setCarpg_color(String carpg_color) {
		this.carpg_color = carpg_color;
	}
	public int getCarpg_jsff() {
		return carpg_jsff;
	}
	public void setCarpg_jsff(int carpg_jsff) {
		this.carpg_jsff = carpg_jsff;
	}
	public float getCarpg_newprice() {
		return carpg_newprice;
	}
	public void setCarpg_newprice(float carpg_newprice) {
		this.carpg_newprice = carpg_newprice;
	}
	public float getCarpg_score() {
		return carpg_score;
	}
	public void setCarpg_score(float carpg_score) {
		this.carpg_score = carpg_score;
	}
	public float getCarpg_cxl() {
		return carpg_cxl;
	}
	public void setCarpg_cxl(float carpg_cxl) {
		this.carpg_cxl = carpg_cxl;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}


}
