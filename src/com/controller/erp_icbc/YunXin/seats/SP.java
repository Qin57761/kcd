package com.controller.erp_icbc.YunXin.seats;
/** 
 * ��дequals���� ��������mark���»���ɾ��ScanPool1(��Ƶͨ�������)
 */
public class SP {
	String mark;//��ʶ
	@Override
	public boolean equals(Object obj) { 
      if(obj!=null && ((SP)obj).getMark().equals(this.mark)){
    	  return true;
      }
      return false;
    }
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	@Override
	public String toString() {
		return "SP [mark=" + mark + "]";
	}
}
