package com.service;

import java.util.Map;

import com.model.bb;

public interface bbService {
	    //��ӱ������
		public void addbb(bb bb);
		//���ݶ�����Ų�ѯ����
		public Map findbb(String orderid);
		//���±���
		public void upbbxx(bb bb);
}
