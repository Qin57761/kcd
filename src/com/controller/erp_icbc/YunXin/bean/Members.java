package com.controller.erp_icbc.YunXin.bean;
/**
 * @Description:TODO
 * @author:LiWang 
 * @time:2018��8��22��
 */
public class Members{
	private String accid;//accidΪ�û��ʺ�
	private boolean caller=false;//�����ͨ���ķ����ߵĻ���caller�ֶ�Ϊtrue��������caller�ֶ�
	private int duration;//����ʱ��
	public String getAccid() {
		return accid;
	}
	public void setAccid(String accid) {
		this.accid = accid;
	}
	public boolean isCaller() {
		return caller;
	}
	public void setCaller(boolean caller) {
		this.caller = caller;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
}