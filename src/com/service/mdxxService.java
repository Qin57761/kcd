package com.service;

import java.util.List;
import java.util.Map;

import com.model.mdxx;

public interface mdxxService {
	    //����ŵ꼰��Ա��Ϣ
		public void addmdxx(List<Map<String, String>> mdxxs);
		//�û���֤
		public int mdxxsize(String ckey);
		//�û���֤
		public Map mdxxmap(String ckey);
		//�û���֤
		public int mdxxsize1(String sname,String pname,String pIDcard);
		//�춹�۷�
		public void upmdxx(mdxx mdxx);
		//mdxxlist
		public List<mdxx> mdxxlist(int st,int ps);
		//��������
		public int mdxxnum();
		//
		public List<mdxx> mdxxckey();
		//
		public List<mdxx> mdxxbyname();
}
