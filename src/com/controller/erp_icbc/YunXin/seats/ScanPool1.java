package com.controller.erp_icbc.YunXin.seats;
/** 
 * @author:LiWang
 */
public class ScanPool1 extends SP{
	//��ʼռ�õ�ʱ�� �������㱻ռ�õ�ʱ�䳤��
	private Long createTime=0L;
	//�ͻ�
	private  String clientAccid;
	private  String clientToken;
	//���
	private String auditAccid;
	private String auditToken;
	public Long getCreateTime(){
		return createTime;
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

}
