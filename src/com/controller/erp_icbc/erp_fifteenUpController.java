package com.controller.erp_icbc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.controller.icbc.jsp.icbcmodel;
import com.controller.icbc.util.Jdpush;
import com.model.icbc.assess_cars;
import com.model.icbc.bclient_check;
import com.model.icbc.icbc;
import com.model1.admin;
import com.model1.icbc.icbc_mq;
import com.model1.icbc.icbc_mq_result;
import com.model1.icbc.erp.PageData;
import com.model1.send.admin_msg;
import com.service1.adminService;
import com.service1.Repayment.RepaymentService;
import com.service1.car.icbc_carsService;
import com.service1.car.icbc_cars_resultService;
import com.service1.erp_icbc.erp_fiveModelService;
import com.service1.erp_icbc.erp_wdrwService;
import com.service1.kjs_icbc.icbc_mqService;
import com.service1.kjs_icbc.icbc_mq_resultService;
import com.service1.kjs_icbc.newicbcService;
import com.service1.send.admin_msgService;
import com.util.creditutil;

@Controller
public class erp_fifteenUpController {
	private static final String appKey = "7e21faf06524b22f0ee1414c";
	private static final String masterSecret = "c87361ae4d7d91067b3ea01a";
	@Autowired
	private icbc_carsService icbc_carsService;
	@Autowired
	private adminService adminService;
	@Autowired
	private erp_fiveModelService erp_fiveModelService;
	@Autowired
	private newicbcService newicbcService;
	@Autowired
	private icbc_mqService icbc_mqService;
	@Autowired
	private icbc_mq_resultService icbc_mq_resultService;
	@Autowired
	private icbc_cars_resultService icbc_cars_resultService;
	@Autowired
	private admin_msgService admin_msgService;
	@Autowired
	private erp_wdrwService erp_wdrwService;
	@Autowired
	private RepaymentService repaymentservice;

	/*
	 * erpʮ��ģ��-������ҵ������-�ܾ������(29) ���
	 */
	@RequestMapping(value = "erp/erp_kqyspsh_29.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_kqyspsh_29(int adminid, String result_1_msg,
			int result_1_code, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		int status = 29;
		String remark = "�ܾ������";
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id")); // aCars.getGems_id()
															// pdsession.get("gems_id")
			picbc.put("gems_fs_id", pdsession.get("fs_id")); // aCars.getGems_fs_id()
																// pdsession.get("fs_id")
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "��ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (result_1_code == 1 || result_1_code == 2) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", 7);
			upd.put("status", 30);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put(
					"dt_add",
					getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id).get(
							"dt_edit"));
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 30);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", 7);
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		} else if (result_1_code == 3) {
			result_1_code_String = "����";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-������ҵ������-�������(27) ���
	 */
	@RequestMapping(value = "erp/erp_kqyspsh_27.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_kqyspsh_27(int adminid, String result_1_msg,
			int result_1_code, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		int status = 27;
		String remark = "�������";
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ����������� ��Ӧ 11
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", status);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id")); // aCars.getGems_id()
															// pdsession.get("gems_id")
			picbc.put("gems_fs_id", pdsession.get("fs_id")); // aCars.getGems_fs_id()
																// pdsession.get("fs_id")
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "��ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (result_1_code == 2) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", 7);
			upd.put("status", 30);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 30);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", 7);
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		} else if (result_1_code == 3) {
			result_1_code_String = "����";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-������ҵ������-������ҵ������(98) ���
	 */
	@RequestMapping(value = "erp/erp_kqyspsh_98.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_kqyspsh_98(int adminid, String kqyspsh_98_sqspd,
			String result_1_msg, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// ��ӿ�ʼ
		erp_sh_add(adminid, type_id, icbc_id, request, response);
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		int status = 98;
		String remark = "������ҵ������";
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		JSONObject json = new JSONObject();
		json.put("98_sqspd", kqyspsh_98_sqspd);
		pResult.put("result_1_value", json.toString());
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", 0);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ����������� ��Ӧ 11
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", status);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id")); // aCars.getGems_id()
															// pdsession.get("gems_id")
			picbc.put("gems_fs_id", pdsession.get("fs_id")); // aCars.getGems_fs_id()
																// pdsession.get("fs_id")
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";���ڽ���" + remark + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�����������-�����ռ�ȷ��(94) ���
	 */
	@RequestMapping(value = "erp/erp_tdtfsh_94.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_tdtfsh_94(int adminid, String tdtfsh_94_msg,
			String tdtfsh_94_date, int result_1_code, String result_1_msg,
			int type_id, int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		int status = 94;
		String remark = "�����ռ�ȷ��";
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		JSONObject json = new JSONObject();
		json.put("94_msg", tdtfsh_94_msg);
		json.put("94_date", tdtfsh_94_date);
		pResult.put("result_1_value", json.toString());
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ����������� ��Ӧ 11
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", status);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id")); // aCars.getGems_id()
															// pdsession.get("gems_id")
			picbc.put("gems_fs_id", pdsession.get("fs_id")); // aCars.getGems_fs_id()
																// pdsession.get("fs_id")
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (result_1_code == 1) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", 15);
			upd.put("status", 95);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 95);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", 15);
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:" + tdtfsh_94_msg + "���ϸ��˽��״̬:"
					+ result_1_code_String + ";����:" + result_1_msg + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�����������-���ϼĻ�(93) ���
	 */
	@RequestMapping(value = "erp/erp_tdtfsh_93.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_tdtfsh_93(int adminid, String tdtfsh_93_kdgs,
			String tdtfsh_93_kddh, String tdtfsh_93_jcrq,
			String tdtfsh_93_bcimg1, String result_1_msg, int type_id,
			int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		int status = 93;
		String remark = "���ϼĻ�";
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id); // ����������� ��Ӧ 11
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		JSONObject json = new JSONObject();
		json.put("kdgs", tdtfsh_93_kdgs);
		json.put("kddh", tdtfsh_93_kddh);
		json.put("jcrq", tdtfsh_93_jcrq);
		json.put("bcimg1", tdtfsh_93_bcimg1);
		pResult.put("result_1_value", json.toString());
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ����������� ��Ӧ 11
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", status);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:" + "��ݹ�˾:" + tdtfsh_93_kdgs
					+ ",��ݵ���:" + tdtfsh_93_kddh + ",�ĳ�����:" + tdtfsh_93_jcrq
					+ ";����:" + result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�˵��˷�-��˾ȷ�ϵ���(92) ���
	 */
	@RequestMapping(value = "erp/erp_tdtfsh_92.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_tdtfsh_92(int adminid, String result_1_msg,
			int result_1_code, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		int status = 92;
		String remark = "��˾ȷ�ϵ���";
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (result_1_code == 2) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", 15);
			upd.put("status", 95);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 95);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", 15);
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "���ˡ�ȷ�������ѵ������ռ���";
		} else if (result_1_code == 2) {
			result_1_code_String = "�ѵ���δ�ռ�";
		} else if (result_1_code == 3) {
			result_1_code_String = "����/δ����";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�˵��˷�-�����ؿ�ɷ����(91) ���
	 */
	@RequestMapping(value = "erp/erp_tdtfsh_91.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_tdtfsh_91(int adminid, String result_1_msg,
			String tdtfsh_91_dze, String tdtfsh_91_zjzyf, int type_id,
			int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		int status = 91;
		String remark = "�����ؿ�ɷ�";
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", 0);
		JSONObject json = new JSONObject();
		json.put("91_dze", tdtfsh_91_dze);
		json.put("91_zjzyf", tdtfsh_91_zjzyf);
		pResult.put("result_1_value", json.toString());
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:���ʶ�:" + tdtfsh_91_dze + "�ʽ�ռ�з�:"
					+ tdtfsh_91_zjzyf + ";����:" + result_1_msg + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�˵��˷�-��˾����˵����(90) ���
	 */
	@RequestMapping(value = "erp/erp_tdtfsh_90.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_tdtfsh_90(int adminid, int result_1_code,
			String result_1_msg, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int status = 90;
		String remark = "��˾����˵����";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "ͨ��"+"δ����δ�ռ�"ʱ��
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		// �õ��Ƿ� ����
		PageData pdd_status = new PageData();
		pdd_status.put("dn", "selectListStatus_icbc_erp_kj_icbc_result");
		pdd_status.put("icbc_id", icbc_id);
		pdd_status.put("type_id", 10); // �ʽ����-���
		pdd_status.put("status", 50); // ����-С״̬
		List<PageData> pErpIcbc_50 = new ArrayList<>();
		pErpIcbc_50 = erp_fiveModelService.findtolist(pdd_status);
		// �õ� �����ռ�ȷ�� �Ľ��
		PageData pdd_s = new PageData();
		pdd_s.put("dn", "selectListStatus_icbc_erp_kj_icbc_result");
		pdd_s.put("icbc_id", icbc_id);
		pdd_s.put("type_id", 11); // ��������-���
		pdd_s.put("status", 58); // �����ռ�ȷ��-С״̬
		List<PageData> pErpIcbc_58 = new ArrayList<>();
		pErpIcbc_58 = erp_fiveModelService.findtolist(pdd_s);
		// �õ����һ������
		PageData get_max58 = pErpIcbc_58.get(pErpIcbc_58.size() - 1);
		JSONObject json = (JSONObject) JSON.parse(get_max58.get(
				"result_1_value").toString());
		if (pErpIcbc_50.size() > 0) { // ��Ǯ�� -����

		} else {
			// δ����
			if (json != null) {
				// δ�յ�
				if (json.get("58_msg").toString().equals("δ�յ�")) {
					// ��ȷ�� Ϊ "ͨ��"ʱ
					// icbc����¼�¼ �� result ���һ����ɼ�¼
					if (result_1_code == 1) {
						// ����icbc_erp_kj_icbc���У�status������װ��
						PageData upd = new PageData();
						upd.put("dn", "update_icbc_erp_kj_icbc");
						upd.put("icbc_id", icbc_id);
						upd.put("type_id", 15);
						upd.put("status", 95);
						upd.put("mid_edit", adminid); // �޸���id
						upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
						erp_fiveModelService.updatebyid(upd);
						PageData pResult_gsgd = new PageData();
						pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
						pResult_gsgd.put("qryid", pErpIcbc.get("id"));
						pResult_gsgd.put("mid_add", adminid);
						pResult_gsgd.put("mid_edit", adminid);
						pResult_gsgd.put("dt_add", creditutil.time());
						pResult_gsgd.put("dt_edit", creditutil.time());
						pResult_gsgd.put("status", 95);
						pResult_gsgd.put("status_oldht", 0);
						pResult_gsgd.put("remark", "���");
						pResult_gsgd.put("result_1_code", 0);
						pResult_gsgd.put("dt_sub", creditutil.time());
						pResult_gsgd.put("type_id", 15);
						pResult_gsgd.put("icbc_id", icbc_id);
						erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵��
																	// icbc
																	// result��
					}
				}
			}
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�˵��˷�-�˵���������(89) ���
	 */
	@RequestMapping(value = "erp/erp_tdtfsh_89.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_tdtfsh_89(int adminid, String result_1_msg, int type_id,
			int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int status = 89;
		String remark = "�˵���������";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", 0);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:" + "�˵�����������" + ";����:" + result_1_msg
					+ "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�˵��˷�-���Ա�˵����(88) ���
	 */
	@RequestMapping(value = "erp/erp_tdtfsh_88.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_tdtfsh_88(int adminid, int result_1_code,
			String result_1_msg, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int status = 88;
		String remark = "���Ա�˵����";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�˵��˷�-�˵��˷�����(97) ���
	 */
	// @RequestMapping(value="erp/erp_tdtfsh_97.do",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@RequestMapping(value = "erp/erp_tdtfsh_97.do")
	public void erp_tdtfsh_97(@RequestParam MultipartFile[] files, int adminid,
			String result_1_msg, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// ��ӿ�ʼ
		erp_sh_add(adminid, type_id, icbc_id, request, response);
		int status = 97;
		String remark = "�˵��˷�����";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", 0);
		System.err.println("111111111111166666666666666");
		System.err.println("�ϴ�ͼƬ����:" + files.length);
		JSONObject json_result = new JSONObject();// json����
		for (int i = 0; i < files.length; i++) {
			int bn = i + 1;
			MultipartFile file = files[i];
			int mFile = (int) files[i].getSize();
			if (file.getSize() != 0) {
				Date date = new Date();
				String filePath = "/KCDIMG/assess/upload/"
						+ new SimpleDateFormat("yyyy/MM/dd/").format(date);
				String imgpath = "upload/"
						+ new SimpleDateFormat("yyyy/MM/dd/").format(date);
				String filename = files[i].getOriginalFilename();
				String prefix = filename
						.substring(filename.lastIndexOf(".") + 1);
				UUID uuid = UUID.randomUUID();
				String uuidname = uuid.toString().replaceAll("-", "") + "."
						+ prefix;
				byte[] file36Byte = files[i].getBytes();
				FileUtils.writeByteArrayToFile(new File(filePath + uuidname),
						file36Byte);
				System.out.println("ͼƬ·����" + filePath + uuidname);
				json_result.put("bcimg" + bn, imgpath + uuidname);
			}
		}

		pResult.put("result_1_value", json_result.toString());
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}

		// ����
		/*
		 * Map map=erp_fifteenModel.fifteenModel(); admin
		 * admin1=adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		 * if(admin1!=null&&!admin1.equals("")){ String
		 * REGISTRATION_ID=admin1.getJgid(); String mString=admin1.getName()
		 * +"����!" +"�ͻ�����Ϊ" +icbc.getC_name()+"������Ѹ���" +";���ڽ���"+remark
		 * +";����:"+result_1_msg +"ʱ��:"+creditutil.time()+";";
		 * if(REGISTRATION_ID!=null&&!REGISTRATION_ID.equals("")){
		 * Jdpush.testSendPush(appKey,masterSecret,REGISTRATION_ID,mString); }
		 * admin_msg admin_msg=new admin_msg();
		 * admin_msg.setDt_add(creditutil.time());
		 * admin_msg.setDt_edit(creditutil.time());
		 * admin_msg.setMid_add(adminid); admin_msg.setMsg(mString);
		 * admin_msg.setReceiveid(admin1.getId()); admin_msg.setSendid(0);
		 * admin_msg.setStatus(0); admin_msgService.addadmin_msg(admin_msg); }
		 */
	}

	/*
	 * erpʮ��ģ��-ҵ����Ϣ�޸�-ϵͳ��ά��רԱ��(85) ���
	 */
	@RequestMapping(value = "erp/erp_ywxxxgsh_85.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_ywxxxgsh_85(int adminid, String result_1_msg,
			int result_1_code, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int status = 85;
		String remark = "ϵͳ��ά��רԱ��";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (1 == 1) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", 86);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 86);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", type_id);
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-ҵ����Ϣ�޸�-ҵ�����(84) ���
	 */
	@RequestMapping(value = "erp/erp_ywxxxgsh_84.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_ywxxxgsh_84(int adminid, String result_1_msg,
			int result_1_code, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int status = 84;
		String remark = "ҵ�����";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (result_1_code == 2) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", 86);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 86);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", type_id);
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-ҵ����Ϣ�޸�-ҵ����Ϣ�޸�����(96) ���
	 */
	@RequestMapping(value = "erp/erp_ywxxxgsh_96.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_ywxxxgsh_96(int adminid, String result_1_msg,
			String ywxxxgsh_96_ywlx, String ywxxxgsh_96_xgbz, int type_id,
			int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// ��ӿ�ʼ
		erp_sh_add(adminid, type_id, icbc_id, request, response);
		int status = 96;
		String remark = "ҵ����Ϣ�޸�����";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", 0);
		JSONObject json = new JSONObject();
		json.put("96_ywlx", ywxxxgsh_96_ywlx);
		json.put("96_xgbz", ywxxxgsh_96_xgbz);
		pResult.put("result_1_value", json.toString());
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		/*
		 * Map map=erp_fifteenModel.fifteenModel(); admin
		 * admin1=adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		 * if(admin1!=null&&!admin1.equals("")){ String
		 * REGISTRATION_ID=admin1.getJgid(); String mString=admin1.getName()
		 * +"����!" +"�ͻ�����Ϊ" +icbc.getC_name()+"������Ѹ���" +";���ڽ���"+remark
		 * +";����:"+result_1_msg +"ʱ��:"+creditutil.time()+";";
		 * if(REGISTRATION_ID!=null&&!REGISTRATION_ID.equals("")){
		 * Jdpush.testSendPush(appKey,masterSecret,REGISTRATION_ID,mString); }
		 * admin_msg admin_msg=new admin_msg();
		 * admin_msg.setDt_add(creditutil.time());
		 * admin_msg.setDt_edit(creditutil.time());
		 * admin_msg.setMid_add(adminid); admin_msg.setMsg(mString);
		 * admin_msg.setReceiveid(admin1.getId()); admin_msg.setSendid(0);
		 * admin_msg.setStatus(0); admin_msgService.addadmin_msg(admin_msg); }
		 */
	}

	/*
	 * erpʮ��ģ��-��˾�鵵-�������(70) ���
	 */
	@RequestMapping(value = "erp/erp_gsgdsh_70.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_gsgdsh_70(int adminid, String result_1_msg,
			int result_1_code, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int status = 70;
		String remark = "�������";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (result_1_code == 1) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", 71);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 71);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", type_id);
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-��˾�鵵-���Ա������(69) ���
	 */
	@RequestMapping(value = "erp/erp_gsgdsh_69.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_gsgdsh_69(int adminid, String result_1_msg, int type_id,
			int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int status = 69;
		String remark = "���Ա������";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", 0);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";���ڽ���" + remark + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-��˾�鵵-ֽ�ʹ鵵(68) ���
	 */
	@RequestMapping(value = "erp/erp_gsgdsh_68.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_gsgdsh_68(int adminid, String result_1_msg, int type_id,
			int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int status = 68;
		String remark = "ֽ�ʹ鵵";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", 0);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";���ڽ���" + remark + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-��˾�鵵-����ֽ�ʹ鵵(67) ���
	 */
	@RequestMapping(value = "erp/erp_gsgdsh_67.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_gsgdsh_67(int adminid, String result_1_msg,
			int result_1_code, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int status = 67;
		String remark = "����ֽ�ʹ鵵";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + "�������״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-����ͨ��-ͨ�ھ���(45) ���
	 */
	@RequestMapping(value = "erp/erp_nstrsh_45.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_nstrsh_45(int adminid, String result_1_msg, int type_id,
			int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int status = 45;
		String remark = "ͨ�ھ���";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", 0);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (1 == 1) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", 46);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 46);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", type_id);
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";���ڽ���" + remark + "���"
					+ ";����:" + result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-����ͨ��-ͨ������(44) ���
	 */
	@RequestMapping(value = "erp/erp_nstrsh_44.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_nstrsh_44(int adminid, String result_1_msg, int type_id,
			int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int status = 44;
		String remark = "ͨ������";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", 0);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";���ڽ���" + remark + "���"
					+ ";����:" + result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-����ͨ��-���Աͨ������������(43) ���
	 */
	@RequestMapping(value = "erp/erp_nstrsh_43.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_nstrsh_43(int adminid, String result_1_msg, int type_id,
			int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int status = 43;
		String remark = "���Աͨ������������";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", 0);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + remark + ":" + result_1_msg + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-����ͨ��-�����ܾ�������ͨ��(42) ���
	 */
	@RequestMapping(value = "erp/erp_nstrsh_42.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_nstrsh_42(int adminid, String result_1_msg, int type_id,
			int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// ��ӿ�ʼ
		erp_sh_add(adminid, type_id, icbc_id, request, response);
		int status = 42;
		String remark = "�����ܾ�������ͨ��";
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", remark);
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", 0);
		pResult.put("result_1_value", "");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", status);
			upd.put("mid_edit", adminid);
			upd.put("dt_edit", creditutil.time());
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id);
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		/*
		 * Map map=erp_fifteenModel.fifteenModel(); admin
		 * admin1=adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		 * if(admin1!=null&&!admin1.equals("")){ String
		 * REGISTRATION_ID=admin1.getJgid(); String mString=admin1.getName()
		 * +"����!" +"�ͻ�����Ϊ" +icbc.getC_name()+"������Ѹ���"
		 * +";���ڽ���"+map.get(type_id)+"_"+remark +";����:"+result_1_msg
		 * +"ʱ��:"+creditutil.time()+";";
		 * if(REGISTRATION_ID!=null&&!REGISTRATION_ID.equals("")){
		 * Jdpush.testSendPush(appKey,masterSecret,REGISTRATION_ID,mString); }
		 * admin_msg admin_msg=new admin_msg();
		 * admin_msg.setDt_add(creditutil.time());
		 * admin_msg.setDt_edit(creditutil.time());
		 * admin_msg.setMid_add(adminid); admin_msg.setMsg(mString);
		 * admin_msg.setReceiveid(admin1.getId()); admin_msg.setSendid(0);
		 * admin_msg.setStatus(0); admin_msgService.addadmin_msg(admin_msg); }
		 */
	}

	/*
	 * erpʮ��ģ��-add �˵��˷�15��ҵ����Ϣ�޸�14������ͨ��9��������ҵ������7
	 */
	@RequestMapping(value = "erp/erp_sh_add.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_sh_add(int adminid, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response) {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		int status = 0;
		switch (type_id) {
		case 7:
			status = 26;
			break;
		case 9:
			status = 41;
			break;
		case 14:
			status = 83;
			break;
		case 15:
			status = 87;
			break;
		default:
			break;
		}
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id);
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);

		// �ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd_status = new PageData();
		pdd_status.put("dn", "selectOneStatus_icbc_erp_kj_icbc_result");
		pdd_status.put("icbc_id", icbc_id);
		pdd_status.put("type_id", type_id);
		pdd_status.put("status", status); // ����ͨ�ڿ�ʼ
		PageData pErpIcbc_status = erp_fiveModelService.findbyid(pdd_status);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", creditutil.time());
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", status);
		pResult.put("status_oldht", 0);
		pResult.put("remark", "��ʼ");
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id);
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			if (pErpIcbc_status == null) {
				pResult.put("qryid", pErpIcbc.get("id"));
				erp_fiveModelService.save(pResult);
			}
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", status);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
	}

	/*
	 * erpʮ��ģ��-�����������-�������ȷ��(63) ���
	 */
	@RequestMapping(value = "erp/erp_yhdksh_63.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_yhdksh_63(int adminid, int result_1_code,
			String result_1_msg, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id); // ����������� ��Ӧ 11
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", 63);
		pResult.put("status_oldht", 0);
		pResult.put("remark", "�������ȷ��");
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ����������� ��Ӧ 11
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", 63);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", 63);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ���Ϊ�����Ļ�, ���һ�� 56״̬������
		if (result_1_code == 1) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", 56);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 56);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "��Ѻ�鵵��ʼ");
			pResult_gsgd.put("result_1_code", result_1_code);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", 11); // ����������� ��Ӧ 11
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd);
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "��������";
		} else if (result_1_code == 2) {
			result_1_code_String = "���ϲ���������Ҫ��������";
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_�������ȷ��״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}

	}

	/*
	 * erpʮ��ģ��-�����������-�տ�ȷ��(62) ���
	 */
	@RequestMapping(value = "erp/erp_yhdksh_62.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_yhdksh_62(int adminid, int result_1_code2,
			String yhdksh_62_fkrq, String yhdksh_62_fkje,
			String yhdksh_62_sqrq, String result_1_msg, String gems_id,
			String gems_fs_id, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id); // ����������� ��Ӧ 11
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", 62);
		pResult.put("status_oldht", 0);
		pResult.put("remark", "�տ�ȷ��");
		// �����ſ����� ����ֶ�
		pResult.put("fk_date", yhdksh_62_fkrq);
		pResult.put("fk_money", yhdksh_62_fkje);
		JSONObject json = new JSONObject();
		json.put("62_fkrq", yhdksh_62_fkrq);
		json.put("62_fkje", yhdksh_62_fkje);
		json.put("62_sqrq", yhdksh_62_sqrq);
		pResult.put("result_1_value", json.toString());
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code2);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ����������� ��Ӧ 11
		pResult.put("icbc_id", icbc_id);

		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", 62);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", 62);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// �������ռ�ȷ�� Ϊ ȷ���յ���ʱ,�ʽ���� ����
		// ��Ѻ�鵵 icbc�� �� result �����һ����ʼ��¼
		// �õ��Ƿ� �ʽ����
		PageData pdd_status = new PageData();
		pdd_status.put("dn", "selectListStatus_icbc_erp_kj_icbc_result");
		pdd_status.put("icbc_id", icbc_id);
		pdd_status.put("type_id", 10); // �ʽ����-���
		pdd_status.put("status", 47); // �ʽ���俪ʼ -С״̬
		List<PageData> pErpIcbc_47 = new ArrayList<>();
		pErpIcbc_47 = erp_fiveModelService.findtolist(pdd_status);
		if (result_1_code2 == 1) {
			if (pErpIcbc_47.size() > 0) {
				// pErpIcbc_66.size()>0 ˵���� ��˾�鵵��ʼ -С״̬ С״̬��
				// �򲻱������
			} else {
				// ���� ���
				PageData picbc_gsgd = new PageData();
				picbc_gsgd.put("dn", "icbc_erp_kj_icbc");
				picbc_gsgd.put("mid_add", adminid);
				picbc_gsgd.put("mid_edit", adminid);
				picbc_gsgd.put("dt_add", creditutil.time());
				picbc_gsgd.put("dt_edit", creditutil.time());
				picbc_gsgd.put("dt_sub", creditutil.time());
				picbc_gsgd.put("status", 47);
				picbc_gsgd.put("icbc_id", icbc_id);
				picbc_gsgd.put("gems_id", gems_id);
				picbc_gsgd.put("gems_fs_id", gems_fs_id);
				picbc_gsgd.put("type_id", 10); // �ʽ���� ��Ӧ 10
				if (aCars != null) {
					picbc_gsgd.put("c_carno", aCars.getC_carno());
					picbc_gsgd.put("c_carvin", aCars.getVincode());
				}
				if (icbc != null) {
					picbc_gsgd.put("c_name", icbc.getC_name());
					picbc_gsgd.put("c_cardno", icbc.getC_cardno());
				}
				erp_fiveModelService.save(picbc_gsgd); // ���� ��˾�鵵�� icbc��
				PageData pResult_gsgd = new PageData();
				pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
				pResult_gsgd.put("qryid", picbc_gsgd.get("id"));
				pResult_gsgd.put("mid_add", adminid);
				pResult_gsgd.put("mid_edit", adminid);
				pResult_gsgd.put("dt_add", creditutil.time());
				pResult_gsgd.put("dt_edit", creditutil.time());
				pResult_gsgd.put("status", 47);
				pResult_gsgd.put("status_oldht", 0);
				pResult_gsgd.put("remark", "�ʽ���俪ʼ");
				pResult_gsgd.put("result_1_code", result_1_code2);
				pResult_gsgd.put("dt_sub", creditutil.time());
				pResult_gsgd.put("type_id", 10); // �ʽ���� ��Ӧ 10
				pResult_gsgd.put("icbc_id", icbc_id);
				erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc
															// result��
			}
		}
		// ��ȷ�� Ϊ "ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (result_1_code2 == 1) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", 65);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 65);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", type_id);
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code2 == 1) {
			result_1_code_String = "����ȷ�ϣ�����������";
		} else if (result_1_code2 == 2) {
			result_1_code_String = "δ�յ�����";
		} else if (result_1_code2 == 3) {
			result_1_code_String = "�տ����";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_" + "�տ�ȷ��״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�����������-���зſ���(61) ���
	 */
	@RequestMapping(value = "erp/erp_yhdksh_61.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_yhdksh_61(int adminid, int result_1_code1,
			String yhdksh_61_date, String yhdksh_61_kh, String yhdksh_61_zh,
			String yhdksh_61_je, String yhdksh_61_sqhkr, String yhdksh_61_yh,
			String yhdksh_61_syhk, String yhdksh_61_fq, String result_1_msg,
			int type_id, int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id); // ����������� ��Ӧ 11
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", 61);
		pResult.put("status_oldht", 0);
		pResult.put("remark", "���зſ���");
		JSONObject json = new JSONObject();
		json.put("61_date", yhdksh_61_date);
		json.put("61_kh", yhdksh_61_kh);
		json.put("61_zh", yhdksh_61_zh);
		json.put("61_je", yhdksh_61_je);
		json.put("61_sqhkr", yhdksh_61_sqhkr);
		json.put("61_yh", yhdksh_61_yh);
		json.put("61_syhk", yhdksh_61_syhk);
		json.put("61_fq", yhdksh_61_fq);
		pResult.put("result_1_value", json.toString());
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code1);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ����������� ��Ӧ 11
		pResult.put("icbc_id", icbc_id);
		int icbc_count = repaymentservice.selectrepay(icbc_id);
		repaymentservice.addrepay(icbc_id, yhdksh_61_je, icbc_count);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", 61);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", 61);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code1 == 1) {
			result_1_code_String = "�ɹ�";
		} else if (result_1_code1 == 2) {
			result_1_code_String = "ʧ��";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_���зſ���״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�����������-�����������(60) ���
	 */
	@RequestMapping(value = "erp/erp_yhdksh_60.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_yhdksh_60(int adminid, int result_1_code,
			String result_1_msg, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id); // ����������� ��Ӧ 11
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", 60);
		pResult.put("status_oldht", 0);
		pResult.put("remark", "�����������");
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ����������� ��Ӧ 11
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", 60);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", 60);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (result_1_code == 2) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id);
			upd.put("status", 65);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 65);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", type_id);
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		} else if (result_1_code == 3) {
			result_1_code_String = "������";
		} else if (result_1_code == 4) {
			result_1_code_String = "�ȵ�Ѻ��Ŵ�";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_�����������״̬:" + result_1_code_String + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�����������-�����ռ�ȷ��(59) ���
	 */
	@RequestMapping(value = "erp/erp_yhdksh_59.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_yhdksh_59(int adminid, String yhdksh_59_msg,
			String yhdksh_59_date, String yhdksh_59_jyhrq, int result_1_code,
			String result_1_msg, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id); // ����������� ��Ӧ 11
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", 59);
		pResult.put("status_oldht", 0);
		pResult.put("remark", "�����ռ�ȷ��");
		JSONObject json = new JSONObject();
		json.put("58_msg", yhdksh_59_msg);
		json.put("58_date", yhdksh_59_date);
		json.put("58_jyhrq", yhdksh_59_jyhrq);
		pResult.put("result_1_value", json.toString());
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ����������� ��Ӧ 11
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", 59);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", 59);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// �������ռ�ȷ�� Ϊ "���յ�"ʱ,��Ѻ�鵵 ����
		// ��Ѻ�鵵 icbc�� �� result �����һ����ʼ��¼
		// �õ��Ƿ� ��Ѻ�鵵
		PageData pdd_status = new PageData();
		pdd_status.put("dn", "selectListStatus_icbc_erp_kj_icbc_result");
		pdd_status.put("icbc_id", icbc_id);
		pdd_status.put("type_id", 13); // ��Ѻ�鵵-���
		pdd_status.put("status", 72); // ��Ѻ�鵵��ʼ -С״̬
		List<PageData> pErpIcbc_72 = new ArrayList<>();
		pErpIcbc_72 = erp_fiveModelService.findtolist(pdd_status);
		if (yhdksh_59_msg.equals("���յ�")) {
			if (pErpIcbc_72.size() > 0) {
				// pErpIcbc_72.size()>0 ˵���� ��Ѻ�鵵��ʼ -С״̬ С״̬��
				// �򲻱������
			} else {
				// ���� ���
				PageData picbc_gsgd = new PageData();
				picbc_gsgd.put("dn", "icbc_erp_kj_icbc");
				picbc_gsgd.put("mid_add", adminid);
				picbc_gsgd.put("mid_edit", adminid);
				picbc_gsgd.put("dt_add", creditutil.time());
				picbc_gsgd.put("dt_edit", creditutil.time());
				picbc_gsgd.put("dt_sub", creditutil.time());
				picbc_gsgd.put("status", 72);
				picbc_gsgd.put("icbc_id", icbc_id);
				picbc_gsgd.put("gems_id", aCars.getGems_id());
				picbc_gsgd.put("gems_fs_id", aCars.getGems_fs_id());
				picbc_gsgd.put("type_id", 13); // ��Ѻ�鵵 ��Ӧ 13
				if (aCars != null) {
					picbc_gsgd.put("c_carno", aCars.getC_carno());
					picbc_gsgd.put("c_carvin", aCars.getVincode());
				}
				if (icbc != null) {
					picbc_gsgd.put("c_name", icbc.getC_name());
					picbc_gsgd.put("c_cardno", icbc.getC_cardno());
				}
				erp_fiveModelService.save(picbc_gsgd); // ���� ��˾�鵵�� icbc��
				PageData pResult_gsgd = new PageData();
				pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
				pResult_gsgd.put("qryid", picbc_gsgd.get("id"));
				pResult_gsgd.put("mid_add", adminid);
				pResult_gsgd.put("mid_edit", adminid);
				pResult_gsgd.put("dt_add", creditutil.time());
				pResult_gsgd.put("dt_edit", creditutil.time());
				pResult_gsgd.put("status", 72);
				pResult_gsgd.put("status_oldht", 0);
				pResult_gsgd.put("remark", "��Ѻ�鵵��ʼ");
				pResult_gsgd.put("result_1_code", result_1_code);
				pResult_gsgd.put("dt_sub", creditutil.time());
				pResult_gsgd.put("type_id", 13); // ��Ѻ�鵵 ��Ӧ 13
				pResult_gsgd.put("icbc_id", icbc_id);
				erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc
															// result��
			}
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_�����ռ�ȷ��״̬:" + yhdksh_59_msg + "���ϸ��˽��״̬:"
					+ result_1_code_String + ";����:" + result_1_msg + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�����������-�����ռ�ȷ��(58) ���
	 */
	@RequestMapping(value = "erp/erp_yhdksh_58.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_yhdksh_58(int adminid, String yhdksh_58_msg,
			String yhdksh_58_date, int result_1_code, String result_1_msg,
			int type_id, int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id); // ����������� ��Ӧ 11
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", 58);
		pResult.put("status_oldht", 0);
		pResult.put("remark", "�����ռ�ȷ��");
		JSONObject json = new JSONObject();
		json.put("58_msg", yhdksh_58_msg);
		json.put("58_date", yhdksh_58_date);
		pResult.put("result_1_value", json.toString());
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("result_1_code", result_1_code);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ����������� ��Ӧ 11
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", 58);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", 58);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// �������ռ�ȷ�� Ϊ "���յ�"ʱ,��˾�鵵 ����
		// ��˾�鵵 icbc�� �� result �����һ����ʼ��¼
		// �õ��Ƿ� ��˾�鵵
		PageData pdd_status = new PageData();
		pdd_status.put("dn", "selectListStatus_icbc_erp_kj_icbc_result");
		pdd_status.put("icbc_id", icbc_id);
		pdd_status.put("type_id", 12); // ��˾�鵵-���
		pdd_status.put("status", 66); // ��˾�鵵��ʼ -С״̬
		List<PageData> pErpIcbc_66 = new ArrayList<>();
		pErpIcbc_66 = erp_fiveModelService.findtolist(pdd_status);
		if (yhdksh_58_msg.equals("���յ�")) {
			if (pErpIcbc_66.size() > 0) {
				// pErpIcbc_66.size()>0 ˵���� ��˾�鵵��ʼ -С״̬ С״̬��
				// �򲻱������
			} else {
				// ���� ���
				PageData picbc_gsgd = new PageData();
				picbc_gsgd.put("dn", "icbc_erp_kj_icbc");
				picbc_gsgd.put("mid_add", adminid);
				picbc_gsgd.put("mid_edit", adminid);
				picbc_gsgd.put("dt_add", creditutil.time());
				picbc_gsgd.put("dt_edit", creditutil.time());
				picbc_gsgd.put("dt_sub", creditutil.time());
				picbc_gsgd.put("status", 66);
				picbc_gsgd.put("icbc_id", icbc_id);
				picbc_gsgd.put("gems_id", aCars.getGems_id());
				picbc_gsgd.put("gems_fs_id", aCars.getGems_fs_id());
				picbc_gsgd.put("type_id", 12); // ��˾�鵵 ��Ӧ 12
				if (aCars != null) {
					picbc_gsgd.put("c_carno", aCars.getC_carno());
					picbc_gsgd.put("c_carvin", aCars.getVincode());
				}
				if (icbc != null) {
					picbc_gsgd.put("c_name", icbc.getC_name());
					picbc_gsgd.put("c_cardno", icbc.getC_cardno());
				}
				erp_fiveModelService.save(picbc_gsgd); // ���� ��˾�鵵�� icbc��
				PageData pResult_gsgd = new PageData();
				pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
				pResult_gsgd.put("qryid", picbc_gsgd.get("id"));
				pResult_gsgd.put("mid_add", adminid);
				pResult_gsgd.put("mid_edit", adminid);
				pResult_gsgd.put("dt_add", creditutil.time());
				pResult_gsgd.put("dt_edit", creditutil.time());
				pResult_gsgd.put("status", 66);
				pResult_gsgd.put("status_oldht", 0);
				pResult_gsgd.put("remark", "��˾�鵵��ʼ");
				pResult_gsgd.put("result_1_code", result_1_code);
				pResult_gsgd.put("dt_sub", creditutil.time());
				pResult_gsgd.put("type_id", 12); // ��˾�鵵 ��Ӧ 12
				pResult_gsgd.put("icbc_id", icbc_id);
				erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc
															// result��
			}
		}
		// ����
		String result_1_code_String = "״̬";
		if (result_1_code == 1) {
			result_1_code_String = "ͨ��";
		} else if (result_1_code == 2) {
			result_1_code_String = "��ͨ��";
		}
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_�����ռ�ȷ��״̬:" + yhdksh_58_msg + "���ϸ��˽��״̬:"
					+ result_1_code_String + ";����:" + result_1_msg + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�����������-�������Ͳ���(57) ���
	 */
	@RequestMapping(value = "erp/erp_yhdksh_57.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_yhdksh_57(int adminid, String yhdksh_57_kdgs,
			String yhdksh_57_kddh, String yhdksh_57_jcrq,
			String yhdksh_57_bcimg1, String result_1_msg, int type_id,
			int icbc_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id); // ����������� ��Ӧ 11
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add", getMaxPagedate_7_9_11_12_14_15(icbc_id, type_id)
				.get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", 57);
		pResult.put("status_oldht", 0);
		pResult.put("remark", "�������Ͳ���");
		JSONObject json = new JSONObject();
		json.put("kdgs", yhdksh_57_kdgs);
		json.put("kddh", yhdksh_57_kddh);
		json.put("jcrq", yhdksh_57_jcrq);
		json.put("bcimg1", yhdksh_57_bcimg1);
		pResult.put("result_1_value", json.toString());
		pResult.put("result_1_msg", result_1_msg);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ����������� ��Ӧ 11
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ����������� ��Ӧ 11
			upd.put("status", 57);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", 57);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id); // ����������� ��Ӧ 11
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ����
		Map map = erp_fifteenModel.fifteenModel();
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";" + map.get(type_id)
					+ "_�������Ͳ���״̬:" + "��ݹ�˾:" + yhdksh_57_kdgs + ",��ݵ���:"
					+ yhdksh_57_kddh + ",�ĳ�����:" + yhdksh_57_jcrq + ";����:"
					+ result_1_msg + "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-���е��� ���
	 */
	@RequestMapping(value = "erp/erp_dssh.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_dssh(int adminid, int ds_status, String ds_remark,
			String dsfqsm, int type_id, int icbc_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		// ��ȡ ���ơ�vin������˺�
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id); // ���е��� ��Ӧ 4
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add",
				getMaxPagedate_004(icbc_id, type_id).get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", 15);
		pResult.put("status_oldht", 0);
		pResult.put("remark", "�������");
		JSONObject json = new JSONObject();
		json.put("dsfqsm", dsfqsm); // ������˵��
		pResult.put("result_1_value", json.toString());
		pResult.put("result_1_code", ds_status);
		pResult.put("result_1_msg", ds_remark);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ���е��� ��Ӧ 4
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ���е��� ��Ӧ 4
			upd.put("status", 15);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", 15);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id); // ���е��� ��Ӧ 4
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);

		}
		// ��ȷ�� Ϊ "ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (ds_status == 1) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", 4);
			upd.put("status", 16);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 16);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", 4);
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		String ds_status_String = "״̬";
		if (ds_status == 1) {
			ds_status_String = "ͨ��";
		} else if (ds_status == 3) {
			ds_status_String = "���˲���";
		} else {
			ds_status_String = "����״̬";
		}
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";���е���״̬:"
					+ ds_status_String + ";����:" + ds_remark + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-��Ƶ��ǩ ���
	 */
	@RequestMapping(value = "erp/erp_mqsh.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_mqsh(int adminid, int mq_status, String mq_remark,
			int type_id, int icbc_id, int mq_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		int status_oldht = 0;
		switch (mq_status) {
		case 1: // 1ͨ��
			status_oldht = 3;
			break;
		case 3: // 3���˲���
			status_oldht = 4;
			break;
		default:
			break;
		}
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �޸� kjs_icbc_mq ��
		icbc_mq iMq = new icbc_mq();
		iMq.setBc_status(status_oldht);
		iMq.setDt_edit(creditutil.time());
		iMq.setMid_edit(adminid);
		iMq.setId(mq_id);
		icbc_mqService.upmq(iMq);
		// �޸� kjs_icbc_mq_result ��
		icbc_mq_result iMq_result = new icbc_mq_result();
		iMq_result.setDt_add(creditutil.time());
		iMq_result.setDt_edit(creditutil.time());
		iMq_result.setMid_add(adminid);
		iMq_result.setMid_edit(adminid);
		iMq_result.setQryid(mq_id);
		Map map = icbcmodel.mq_status();
		if (mq_remark != null && !mq_remark.equals("")) {
			iMq_result.setRemark(mq_remark);
		} else {
			iMq_result.setRemark(map.get(status_oldht).toString());
		}
		iMq_result.setStatus(status_oldht);
		icbc_mq_resultService.addmq_result(iMq_result);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", type_id); // ��Ƶ��ǩ ��Ӧ 6
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add",
				getMaxPagedate_006(icbc_id, type_id).get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", 24);
		pResult.put("status_oldht", status_oldht);
		pResult.put("remark", "�������");
		pResult.put("result_1_code", mq_status);
		pResult.put("result_1_msg", mq_remark);
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", type_id); // ��Ƶ��ǩ ��Ӧ 6
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", type_id); // ��Ƶ��ǩ ��Ӧ 6
			upd.put("status", 24);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", 24);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id"));
			picbc.put("gems_fs_id", pdsession.get("fs_id"));
			picbc.put("type_id", type_id); // ��Ƶ��ǩ ��Ӧ 6
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (mq_status == 1) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", 6); // ��Ƶ��ǩ ��Ӧ 6
			upd.put("status", 25);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 25);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", 6); // ��Ƶ��ǩ ��Ӧ 6
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";��Ƶ��ǩ״̬:"
					+ map.get(status_oldht) + ";����:" + mq_remark + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erpʮ��ģ��-�����������
	 */
	@RequestMapping(value = "erp/erp_pgsh.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public void erp_pgsh(int adminid, int result_1_code, String remark,
			int price_new, String icbc_pricecs, String price_result,
			int type_id, int icbc_id, int cars_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		int status_oldht = 0;
		switch (result_1_code) {
		case 1: // 1ͨ��
			status_oldht = 3;
			break;
		case 2: // 2�ܾ�
			status_oldht = 5;
			break;
		case 3: // 3���˲���
			status_oldht = 4;
			break;
		default:
			break;
		}
		assess_cars aCars = icbc_carsService.findicbc_cars1(icbc_id);
		icbc icbc = newicbcService.findicbcbyid(icbc_id);
		// �޸� assess_cars ������
		assess_cars assess_cars = new assess_cars();
		assess_cars.setId(aCars.getId());
		assess_cars.setIcbc_pricecs(Float.parseFloat(icbc_pricecs));
		assess_cars.setPrice_new(price_new);
		assess_cars.setMem_id(adminid);
		admin admin = adminService.adminbyid(adminid);
		assess_cars.setMem_name(admin.getName());
		assess_cars.setMem_tel(admin.getTel());
		assess_cars.setDt_edit(creditutil.time());
		int price = 0;
		if (price_result != null && !price_result.equals("")) {
			price = Integer.parseInt(price_result);
		}
		assess_cars.setPrice_result(price);
		assess_cars.setBc_status(status_oldht);
		icbc_carsService.upicbc_cars(assess_cars);
		// �޸� assess_bclient_check ��
		bclient_check bclient_check = new bclient_check();
		bclient_check.setDt_add(creditutil.time());
		bclient_check.setDt_edit(creditutil.time());
		bclient_check.setAssessid(aCars.getId());
		bclient_check.setStatus(status_oldht);
		bclient_check.setMid_add(adminid);
		bclient_check.setMid_edit(adminid);
		Map map = icbcmodel.pg_status();
		if (remark != null && !remark.equals("")) {
			bclient_check.setRemark(remark);
		} else {
			bclient_check.setRemark(map.get(status_oldht).toString());
		}
		icbc_cars_resultService.addbclient_check(bclient_check);
		// �������֮ǰ���ж�һ��icbc_erp_kj_icbc�����Ƿ���ĳ���û���ĳ�����
		PageData pdd = new PageData();
		pdd.put("dn", "selectOne_icbc_erp_kj_icbc");
		pdd.put("icbc_id", icbc_id);
		pdd.put("type_id", 3); // �������� ��Ӧ 3
		PageData pErpIcbc = erp_fiveModelService.findbyid(pdd);
		/*
		 * ������ϸ��¼ start/////
		 */
		// ���� �������Լ�¼
		PageData pResult = new PageData();
		pResult.put("dn", "icbc_erp_kj_icbc_result");
		pResult.put("mid_add", adminid);
		pResult.put("mid_edit", adminid);
		pResult.put("dt_add",
				getMaxPagedate_003(icbc_id, type_id).get("dt_edit"));
		pResult.put("dt_edit", creditutil.time());
		pResult.put("status", 11);
		pResult.put("status_oldht", status_oldht);
		pResult.put("remark", "�������");
		pResult.put("result_1_code", result_1_code);
		pResult.put("result_1_msg", remark);
		JSONObject json = new JSONObject();
		json.put("price_new", price_new); // �㳵�۸�
		json.put("icbc_pricecs", Float.parseFloat(icbc_pricecs)); // ����������
		json.put("price_result", price_result); // ϵͳ������
		pResult.put("result_1_value", json.toString());
		pResult.put("dt_sub", creditutil.time());
		pResult.put("type_id", 3); // �������� ��Ӧ 3
		pResult.put("icbc_id", icbc_id);
		/*
		 * ������ϸ��¼ end/////
		 */
		if (pErpIcbc != null) {
			pResult.put("qryid", pErpIcbc.get("id"));
			erp_fiveModelService.save(pResult);
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", 3); // �������� ��Ӧ 3
			upd.put("status", 11);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
		} else {
			PageData picbc = new PageData();
			picbc.put("dn", "icbc_erp_kj_icbc");
			picbc.put("mid_add", adminid);
			picbc.put("mid_edit", adminid);
			picbc.put("dt_add", creditutil.time());
			picbc.put("dt_edit", creditutil.time());
			picbc.put("dt_sub", creditutil.time());
			picbc.put("status", 11);
			picbc.put("icbc_id", icbc_id);
			picbc.put("gems_id", pdsession.get("gems_id")); // aCars.getGems_id()
															// pdsession.get("gems_id")
			picbc.put("gems_fs_id", pdsession.get("fs_id")); // aCars.getGems_fs_id()
																// pdsession.get("fs_id")
			picbc.put("type_id", 3); // �������� ��Ӧ 3
			if (aCars != null) {
				picbc.put("c_carno", aCars.getC_carno());
				picbc.put("c_carvin", aCars.getVincode());
			}
			if (icbc != null) {
				picbc.put("c_name", icbc.getC_name());
				picbc.put("c_cardno", icbc.getC_cardno());
			}
			erp_fiveModelService.save(picbc);
			// result�������
			pResult.put("qryid", picbc.get("id"));
			erp_fiveModelService.save(pResult);
		}
		// ��ȷ�� Ϊ "ͨ��"ʱ
		// icbc����¼�¼ �� result ���һ����ɼ�¼
		if (result_1_code == 1) {
			// ����icbc_erp_kj_icbc���У�status������װ��
			PageData upd = new PageData();
			upd.put("dn", "update_icbc_erp_kj_icbc");
			upd.put("icbc_id", icbc_id);
			upd.put("type_id", 3); // �������� ��Ӧ 3
			upd.put("status", 12);
			upd.put("mid_edit", adminid); // �޸���id
			upd.put("dt_edit", creditutil.time()); // �޸�ʱ��
			erp_fiveModelService.updatebyid(upd);
			PageData pResult_gsgd = new PageData();
			pResult_gsgd.put("dn", "icbc_erp_kj_icbc_result");
			pResult_gsgd.put("qryid", pErpIcbc.get("id"));
			pResult_gsgd.put("mid_add", adminid);
			pResult_gsgd.put("mid_edit", adminid);
			pResult_gsgd.put("dt_add", creditutil.time());
			pResult_gsgd.put("dt_edit", creditutil.time());
			pResult_gsgd.put("status", 12);
			pResult_gsgd.put("status_oldht", 0);
			pResult_gsgd.put("remark", "���");
			pResult_gsgd.put("result_1_code", 0);
			pResult_gsgd.put("dt_sub", creditutil.time());
			pResult_gsgd.put("type_id", 3); // ��Ѻ�鵵 ��Ӧ 13
			pResult_gsgd.put("icbc_id", icbc_id);
			erp_fiveModelService.save(pResult_gsgd); // ���� ��˾�鵵�� icbc result��
		}
		// ����
		admin admin1 = adminService.adminbyid(pErpIcbc.getInt("mid_add"));
		if (admin1 != null && !admin1.equals("")) {
			String REGISTRATION_ID = admin1.getJgid();
			String mString = admin1.getName() + "����!" + "�ͻ�����Ϊ"
					+ icbc.getC_name() + "������Ѹ���" + ";����״̬:"
					+ map.get(status_oldht) + ";����:" + remark + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin1.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
		// request.setCharacterEncoding("UTF-8");
		// response.setContentType("text/html;charset=utf-8");
		// String msString="�ύ�ɹ�!";
		// PrintWriter out = response.getWriter();
		// out.print("<script language=\"javascript\">alert('"+msString+"');window.location.href='wdrw_from.do?type=wdrw&dn=wdrw&qn=form&icbc_id="+icbc_id+"&cn=w3&cn1=3&type_id="+type_id+"'</script>");
	}

	// �����������һ������
	public PageData getMaxPagedate_003(int icbc_id, int type_id) {
		List<PageData> erp15 = new ArrayList<>();
		PageData pd001 = new PageData();
		pd001.put("dn", "003");
		pd001.put("icbc_id", icbc_id);
		pd001.put("type_id", type_id);
		erp15 = erp_wdrwService.icbc_list(pd001);
		// ��ȡ������һ������
		PageData pData2 = erp15.get(erp15.size() - 1);
		if (pData2 == null) {
			pData2.put("dt_edit", creditutil.time());
		}
		return pData2;
	}

	// ���е������һ������
	public PageData getMaxPagedate_004(int icbc_id, int type_id) {
		List<PageData> erp15 = new ArrayList<>();
		PageData pd004 = new PageData();
		pd004.put("dn", "004");
		pd004.put("icbc_id", icbc_id);
		pd004.put("type_id", type_id);
		erp15 = erp_wdrwService.icbc_list(pd004);
		// ��ȡ������һ������
		PageData pData2 = erp15.get(erp15.size() - 1);
		if (pData2 == null) {
			pData2.put("dt_edit", creditutil.time());
		}
		return pData2;
	}

	// ��Ƶ��ǩ������һ������
	public PageData getMaxPagedate_006(int icbc_id, int type_id) {
		List<PageData> erp15 = new ArrayList<>();
		PageData pd006 = new PageData();
		pd006.put("dn", "006");
		pd006.put("icbc_id", icbc_id);
		pd006.put("type_id", type_id);
		erp15 = erp_wdrwService.icbc_list(pd006);
		// ��ȡ������һ������
		PageData pData2 = erp15.get(erp15.size() - 1);
		if (pData2 == null) {
			pData2.put("dt_edit", creditutil.time());
		}
		return pData2;
	}

	// 7������ҵ��-9����ͨ�ڰ��-11���д���������-12��˾�鵵���-14ҵ����Ϣ�޸�-15�˵��˷ѡ������һ������
	public PageData getMaxPagedate_7_9_11_12_14_15(int icbc_id, int type_id) {
		List<PageData> erp15 = new ArrayList<>();
		PageData pd0011 = new PageData();
		pd0011.put("dn", "004");
		pd0011.put("icbc_id", icbc_id);
		pd0011.put("type_id", type_id);
		erp15 = erp_wdrwService.icbc_list(pd0011);
		// ��ȡ������һ������
		PageData pData2 = erp15.get(erp15.size() - 1);
		if (pData2 == null) {
			pData2.put("dt_edit", creditutil.time());
		}
		return pData2;
	}
}
