package com.model1;

public class carbrand {
    private int	id; 	//int(11) 	��  	  	 
    private String name; 	//varchar(255) 	��  	NULL  	Ʒ������ 
	private String alias;	//varchar(255) 	��  	NULL  	���� 
	private String first; 	//char(8) 	��  	NULL  	ƴ������ĸ 
	private String logo;	//varchar(255) 	��  	NULL  	LOGO 
	private String imgurl; 	//varchar(255) 	��  	NULL  	ͼƬ 
	private int	status; 	//tinyint(4) 	��  	0  	 
	private int	create_time;	//int(10) 	��  	NULL  	 
	private int	update_time;	//int(10) 	��  	NULL  	 
	private int	id1; 	//int(11) 	��  	NULL  	��һ��ID 
	private String namepy; 	//varchar(255) 	��  	NULL  	Ʒ��ƴ�� 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCreate_time() {
		return create_time;
	}
	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}
	public int getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(int update_time) {
		this.update_time = update_time;
	}
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getNamepy() {
		return namepy;
	}
	public void setNamepy(String namepy) {
		this.namepy = namepy;
	}
	
	
}
