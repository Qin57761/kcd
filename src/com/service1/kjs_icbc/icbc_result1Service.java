package com.service1.kjs_icbc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.icbc.icbc_result;

public interface icbc_result1Service {
	//�����־
		public void addicbc_result(icbc_result icbc_result);
		    //�����־
			public void addzx_result(icbc_result icbc_result);
			//�����־
			public void addkk_result(icbc_result icbc_result);
			//�����־
			public void addcardk_result(icbc_result icbc_result);
			//�����־
			public void addpreaudit_result(icbc_result icbc_result);

		    //����id��ѯ
		    public List<icbc_result> findresultbyqryid(int qryid);
		    //����id��ѯ
			public List<icbc_result> findzxbyqryid(int qryid);
			//����id��ѯ
			public List<icbc_result> findkkbyqryid(int qryid);
			//����id��ѯ
			public List<icbc_result> findcardkbyqryid(int qryid);
			//����id��ѯ
			public List<icbc_result> findpreauditbyqryid(int qryid);
            //
			icbc_result lastfindresult(int qryid);
			//
			icbc_result kklastfindresult(int qryid);
			//
			icbc_result dklastfindresult(int qryid);
}
