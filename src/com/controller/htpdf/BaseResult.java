package com.controller.htpdf;
//�����������
public class BaseResult {
	private int code;// codeΪ0ʧ�� 1Ϊ�ɹ�
	private String message;// ��ʾ��Ϣ ѹ���������ɣ�
	private String loadf;
	private Object map;//ʧ����Ϣ ����ʧ�ܵĺ�ͬ �ֶ�ʧ��ԭ��
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLoadf() {
		return loadf;
	}
	public void setLoadf(String loadf) {
		this.loadf = loadf;
	}
	public Object getMap() {
		return map;
	}
	public void setMap(Object map) {
		this.map = map;
	}
}
