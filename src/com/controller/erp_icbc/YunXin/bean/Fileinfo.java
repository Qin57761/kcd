package com.controller.erp_icbc.YunXin.bean;
/**
 * @Description:TODO
 * @author:LiWang ��Ƶ��ǩ�ص�
 * @time:2018��8��22��
 */
public class Fileinfo {
	private boolean caller;//�Ƿ��Ǵ�ͨͨ���ķ����ߣ�������Ϊtrue����������û�д��ֶΣ���תΪBooleanֵ
	private String channelid;
	private String md5;//�ļ���md5ֵ
	private String size;//�ļ��Ĵ�С
	private String type;//�ļ�������
	private String user;//�û��ʺţ������ļ�Ϊ���¼���ļ�������ֶ�Ϊ"0"
	private String filename;//�ļ���
	private String url;//�ļ������ص�ַ���벻Ҫ�������ֶ�
	private String vid;//�㲥�ļ�id
	private String timestamp;//�ļ����ɵ�ϵͳʱ��
	private String pieceindex;//¼���ļ�����Ƭ�����������ͨͨ��¼��ʱ��������Ƭʱ������¼���ļ��ᱻ�ұ��и�ɶ���ļ�
	private boolean mix=false;//�Ƿ�Ϊ���¼���ļ���true�����¼���ļ���false������¼���ļ�
	public boolean isCaller() {
		return caller;
	}
	
	public void setCaller(boolean caller) {
		this.caller = caller;
	}
	public String getChannelid() {
		return channelid;
	}
	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getPieceindex() {
		return pieceindex;
	}
	public void setPieceindex(String pieceindex) {
		this.pieceindex = pieceindex;
	}
	public boolean isMix() {
		return mix;
	}
	public void setMix(boolean mix) {
		this.mix = mix;
	}
}
