package com.controller.erp_icbc.YunXin.seats;
/** 
 * ɨ��� 
 * @author:LiWang
 */
public class ScanPool1 extends SP{
	private String Id;
	//��ǰʱ��-��ʼռ�õ�ʱ�� =��ռ�õ�ʱ�䳤��
	private Long createTime=0L;
	private Integer delmark;
	//����id
	private String bankId;
	//����ͨ��������ռ��ʱ�� 
	private int validtime=900000;//15����=900000
	//���ߵ���Чʱ�� �ͻ�����ѵʱ��һ��Ҫ�����С,��֤������ߴ��ڼ���״̬
	private int onlinetime=300000;//5����=300000
	//�ͻ�
	private  String clientAccid;
	private  String clientToken;
	//���
	private String auditAccid;
	private String auditToken;
	//����Ԥ����չ����Բ�ͬ���е���Ƶ��ǰʱ�����ܲ�ͬ�����
	public ScanPool1(int onlinetime,int validtime){
		this.onlinetime=onlinetime;
		this.validtime=validtime;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Integer getDelmark() {
		return delmark;
	}

	public void setDelmark(Integer delmark) {
		this.delmark = delmark;
	}

	public ScanPool1(){};
	
	public Long getCreateTime(){
		return createTime;
	}
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public void setCreateTime(Long createTime){
		this.createTime = createTime;
	}
	public String getClientAccid() {
		return clientAccid;
	}
	public void setClientAccid(String clientAccid) {
		this.clientAccid = clientAccid;
	}
	public String getClientToken() {
		return clientToken;
	}
	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}
	public String getAuditAccid() {
		return auditAccid;
	}
	public void setAuditAccid(String auditAccid) {
		this.auditAccid = auditAccid;
	}
	public String getAuditToken() {
		return auditToken;
	}
	public void setAuditToken(String auditToken) {
		this.auditToken = auditToken;
	}

	public int getValidtime() {
		return validtime;
	}
	public void setValidtime(int validtime) {
		this.validtime = validtime;
	}
	public int getOnlinetime() {
		return onlinetime;
	}
	public void setOnlinetime(int onlinetime) {
		this.onlinetime = onlinetime;
	}

	@Override
	public String toString() {
		return "ScanPool1 [mark=" + super.mark + ", createTime=" + createTime + ", delmark=" + delmark + ", bankId=" + bankId + ", validtime="
				+ validtime + ", onlinetime=" + onlinetime + ", clientAccid=" + clientAccid + ", clientToken="
				+ clientToken + ", auditAccid=" + auditAccid + ", auditToken=" + auditToken + "]";
	}
	
}
