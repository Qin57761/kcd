package com.controller.erp_icbc.YunXin.bean;
import java.util.List;
/**
 * @Description:TODO
 * @author:LiWang ����ʵ����
 * @time:2018��8��22��
 */
public class InfoCopy {
	private Long timestamp;//�ǳ��¼�������ʱ���
	private String channelId;//	ͨ����
	private String createtime;//����Ƶͨ��/�װ忪ʼ��ʱ��, ��תΪ13λʱ���
	private String duration;//��ͨͨ��/�װ��ͨ��ʱ������ȷ���룬��תΪInteger����
	private String eventType;//Ϊ5����ʾ��ʵʱ����Ƶ/�װ�ʱ�������¼�
	private List<Members> members;//��ʾͨ��/�װ�Ĳ�����
	private String status;
	private String faccid;//������
	private String accid;//�ǳ����˺� ����ͨ�����˺�
	private String live;
	/*��Ƶͨ��״̬ SUCCESS����ʾ�����Ҷϣ� 
	TIMEOUT����ʾ��ʱ��
	SINGLE_PARTICIPATE����ʾֻ��һ�������ߣ�
	UNKNOWN����ʾδ֪״̬*/
	private String ext;//����Ƶ����ʱ���Զ����ֶΣ���ѡ�����û�ָ��
	private List<Fileinfo> fileinfo;
	private Fileinfo fi;//���������;
	private String type;
	public Long getTimestamp() {
		return timestamp;
	}
	public Fileinfo getFi() {
		return fi;
	}
	public void setFi(Fileinfo fi) {
		this.fi = fi;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public List<Members> getMembers() {
		return members;
	}
	public void setMembers(List<Members> members) {
		this.members = members;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFaccid() {
		return faccid;
	}
	public void setFaccid(String faccid) {
		this.faccid = faccid;
	}
	public String getAccid() {
		return accid;
	}
	public void setAccid(String accid) {
		this.accid = accid;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}

	public List<Fileinfo> getFileinfo() {
		return fileinfo;
	}
	public void setFileinfo(List<Fileinfo> fileinfo) {
		this.fileinfo = fileinfo;
	}
	public String getLive() {
		return live;
	}
	public void setLive(String live) {
		this.live = live;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
