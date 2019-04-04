package com.controller.erp_icbc;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.controller.icbc.jsp.icbcmodel;
import com.controller.icbc.util.Jdpush;
import com.model.icbc.icbc;
import com.model.icbc.icbc_kk;
import com.model.icbc.icbc_result;
import com.model1.admin;
import com.model1.fs;
import com.model1.icbc.icbc_dk;
import com.model1.icbc.erp.PageData;
import com.model1.send.admin_msg;
import com.service1.fsService;
import com.service1.erp_icbc.erp_fiveModelService;
import com.service1.erp_icbc.erp_wdrwService;
import com.service1.kjs_icbc.icbc_result1Service;
import com.service1.kjs_icbc.newicbcService;
import com.service1.kjs_icbc.newicbc_kkService;
import com.service1.send.admin_msgService;
import com.util.creditutil;
import com.util.jsonutil;
import com.util.limitutil;

@Controller
public class erp_wdrwController {

	private static final String appKey = "7e21faf06524b22f0ee1414c";
	private static final String masterSecret = "c87361ae4d7d91067b3ea01a";

	@Autowired
	private erp_wdrwService erp_wdrwService;
	@Autowired
	private newicbcService newicbcService;
	@Autowired
	private newicbc_kkService newicbc_kkService;
	@Autowired
	private icbc_result1Service icbc_result1Service;
	@Autowired
	private com.service1.kjs_icbc.icbc_dkService icbc_dkService;
	@Autowired
	private fsService fService;
	@Autowired
	private com.service1.adminService adminService;
	@Autowired
	private admin_msgService admin_msgService;
	@Autowired
	private erp_fiveModelService erp_fiveModelService;

	/**
	 * erp ��Ѻ�鵵 ¼�����в������ 81
	 * 
	 * @param cphm
	 * @param dywcrq
	 * @param djzsh
	 * @param dyblry
	 * @param spfs
	 * @param cllb
	 * @param cyqk
	 * @param result_1_msg
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "erp/erp_dygd_81.do")
	@ResponseBody
	public void erp_dygd_81(String cphm, String dywcrq, String djzsh,
			String dyblry, String spfs, String cllb, String cyqk,
			String result_1_msg,

			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, Integer gems_id, Integer fs_id,
			HttpServletRequest request) throws IOException {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		admin_id = (Integer) pdsession.get("id");
		json_result.put("nowstatus", 81);
		json_result.put("icbc_id", icbc_id);
		json_result.put("qryid", yw_id);
		json_result.put("81_cphm", cphm);
		json_result.put("81_dywcrq", dywcrq);
		json_result.put("81_djzsh", djzsh);
		json_result.put("81_dyblry", dyblry);
		json_result.put("81_spfs", spfs);
		json_result.put("81_cllb", cllb);
		json_result.put("81_cyqk", cyqk);
		json_result.put("result_1_msg", result_1_msg);

		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "¼�����в������");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", result_1_msg);
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
		if (cyqk.equals("ͨ��")) {
			System.out.println("��ɲ�������һ�����ݲ�����״̬");
			PageData p1 = new PageData();
			PageData p1_result = new PageData();
			p1.put("dn", "icbc_erp_kj_icbc");
			p1.put("mid_edit", admin_id);
			p1.put("dt_edit", creditutil.time());
			p1.put("status", 82);
			p1.put("id", yw_id);
			p1_result.put("qryid", yw_id);
			p1_result.put("mid_add", admin_id);
			p1_result.put("mid_edit", admin_id);
			p1_result.put("dt_add", creditutil.time());
			p1_result.put("dt_edit", creditutil.time());
			p1_result.put("status", 82);
			p1_result.put("status_oldht", 0);
			p1_result.put("remark", "���");
			p1_result.put("result_1_code", 0);
			p1_result.put("result_1_msg", "");
			p1_result.put("result_1_value", "");
			p1_result.put("dt_sub", creditutil.time());
			p1_result.put("type_id", type_id);
			p1_result.put("icbc_id", icbc_id);
			p1_result.put("jsonAll", "");
			p1_result.put("dn", "icbc_erp_kj_icbc_result");
			erp_wdrwService.update(p1);
			erp_wdrwService.save(p1_result);
		}
	}

	/**
	 * erp ��Ѻ�鵵 �����ռ�ȷ��80
	 * 
	 * @param sjqr
	 * @param ksrq
	 * @param clfh
	 * @param cphm
	 * @param result_1_msg
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "erp/erp_dygd_80.do")
	@ResponseBody
	public void erp_dygd_80(String sjqr, String ksrq, String clfh, String cphm,
			String result_1_msg,

			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, Integer gems_id, Integer fs_id,
			HttpServletRequest request) throws IOException {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		admin_id = (Integer) pdsession.get("id");
		json_result.put("nowstatus", 80);
		json_result.put("icbc_id", icbc_id);
		json_result.put("qryid", yw_id);
		json_result.put("80_sjqr", sjqr);
		json_result.put("80_ksrq", ksrq);
		json_result.put("80_clfh", clfh);
		json_result.put("80_cphm", cphm);
		json_result.put("result_1_msg", result_1_msg);

		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "�����ռ�ȷ��");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", result_1_msg);
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
	}

	/**
	 * erp ��Ѻ�鵵 ���͵�Ѻ����������79
	 * 
	 * @param sjqr
	 * @param ksrq
	 * @param clfh
	 * @param cphm
	 * @param result_1_msg
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "erp/erp_dygd_79.do")
	@ResponseBody
	public void erp_dygd_79(String kdgs, String kddh, String jcrq,
			String result_1_msg,

			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, Integer gems_id, Integer fs_id,
			HttpServletRequest request) throws IOException {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���

		json_result.put("nowstatus", 79);
		json_result.put("icbc_id", icbc_id);
		json_result.put("qryid", yw_id);
		json_result.put("79_kdgs", kdgs);
		json_result.put("79_kddh", kddh);
		json_result.put("79_jcrq", jcrq);
		json_result.put("result_1_msg", result_1_msg);

		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "���͵�Ѻ����������");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", result_1_msg);
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
	}

	/**
	 * 
	 * erp ��Ѻ�鵵 ����ռ�ȷ�� 78
	 * 
	 * @param kdgs
	 * @param kddh
	 * @param jcrq
	 * @param result_1_msg
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "erp/erp_dygd_78.do")
	@ResponseBody
	public void erp_dygd_78(String sjqr, String ksrq, String clfh, String cphm,
			String result_1_msg,

			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, Integer gems_id, Integer fs_id,
			HttpServletRequest request) throws IOException {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���

		json_result.put("nowstatus", 78);
		json_result.put("icbc_id", icbc_id);
		json_result.put("qryid", yw_id);
		json_result.put("78_sjqr", sjqr);
		json_result.put("78_ksrq", ksrq);
		json_result.put("78_clfh", clfh);
		json_result.put("78_cphm", cphm);
		json_result.put("result_1_msg", result_1_msg);

		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "����ռ�ȷ��");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", result_1_msg);
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
	}

	/**
	 * erp ��Ѻ�鵵 ��Ѻ���ϼĻ� 77
	 * 
	 * @param kdgs
	 * @param kddh
	 * @param jcrq
	 * @param result_1_msg
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "erp/erp_dygd_77.do")
	@ResponseBody
	public void erp_dygd_77(String kdgs, String kddh, String jcrq,
			String result_1_msg,

			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, Integer gems_id, Integer fs_id,
			HttpServletRequest request) throws IOException {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���

		json_result.put("nowstatus", 77);
		json_result.put("icbc_id", icbc_id);
		json_result.put("qryid", yw_id);
		json_result.put("77_kdgs", kdgs);
		json_result.put("77_kddh", kddh);
		json_result.put("77_jcrq", jcrq);
		json_result.put("result_1_msg", result_1_msg);

		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "��Ѻ���ϼĻ�");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", result_1_msg);
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
	}

	/**
	 * erp ��Ѻ�鵵 ��Ѻ�����¼ 76
	 * 
	 * @param files
	 * @param cphm
	 * @param dywcrq
	 * @param djzsh
	 * @param dyblry
	 * @param spfs
	 * @param cplb
	 * @param cyqk
	 * @param result_1_msg
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 * @throws IOException
	 */
	@Transactional
	@RequestMapping(value = "erp/erp_dygd_76.do")
	@ResponseBody
	public void erp_dygd_76(String cphm, String dywcrq, String djzsh,
			String dyblry, String spfs, String cplb, String cyqk,
			String result_1_msg,

			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, Integer gems_id, Integer fs_id,
			HttpServletRequest request) throws IOException {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���
		icbc icbc = new icbc();// ��������

		json_result.put("nowstatus", 76);
		json_result.put("icbc_id", icbc_id);
		json_result.put("qryid", yw_id);
		json_result.put("76_cphm", cphm);
		json_result.put("76_dywcrq", dywcrq);
		json_result.put("76_djzsh", djzsh);
		json_result.put("76_dyblry", dyblry);
		json_result.put("76_spfs", spfs);
		json_result.put("76_cplb", cplb);
		json_result.put("76_cyqk", cyqk);
		json_result.put("result_1_msg", result_1_msg);

		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "��Ѻ�����¼");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", result_1_msg);
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toString());
		// ����icbc��
		icbc.setDygd_wcdate(dywcrq);
		icbc.setId(icbc_id);
		icbc.setDygd_djzsh(djzsh);
		icbc.setDygd_dyblry(dyblry);

		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
		newicbcService.upicbc(icbc);

	}

	/**
	 * erp ��Ѻ�鵵 75
	 * 
	 * @param files
	 * @param ksrq
	 * @param sjqr
	 * @param clfh
	 * @param result_1_msg
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "erp/erp_dygd_75.do")
	@ResponseBody
	public void erp_dygd_75(@RequestParam MultipartFile[] files, String ksrq,
			String sjqr, String clfh, String result_1_msg, Integer admin_id,
			Integer icbc_id, Integer type_id, Integer status_id, Integer yw_id,
			Integer gems_id, Integer fs_id, HttpServletRequest request)
			throws IOException {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���

		json_result.put("nowstatus", 75);
		json_result.put("icbc_id", icbc_id);
		json_result.put("qryid", yw_id);
		json_result.put("75_sjqr", sjqr);
		json_result.put("75_clfh", clfh);
		json_result.put("75_ksrq", ksrq);
		json_result.put("result_1_msg", result_1_msg);

		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "�����ռ�ȷ��");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", result_1_msg);
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		for (int i = 0; i < files.length; i++) {
			int bn = i + 1;
			MultipartFile file = files[i];
			if (file.getSize() != 0) {
				Date date = new Date();
				String filePath = "/KCDIMG/assess/upload/"
						+ new SimpleDateFormat("yyyy/MM/dd/").format(date);
				String imgpath = "upload/"
						+ new SimpleDateFormat("yyyy/MM/dd/").format(date);
				String filename = file.getOriginalFilename();
				String prefix = filename
						.substring(filename.lastIndexOf(".") + 1);
				UUID uuid = UUID.randomUUID();
				String uuidname = uuid.toString().replaceAll("-", "") + "."
						+ prefix;
				byte[] file36Byte = file.getBytes();
				FileUtils.writeByteArrayToFile(new File(filePath + uuidname),
						file36Byte);
				System.out.println("ͼƬ·����" + filePath + uuidname);
				json_result.put("bcimg" + bn, imgpath + uuidname);
			}

		}
		erp_result.put("result_1_value", json_result.toString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
	}

	/**
	 * erp ��Ѻ�鵵 74 ��Ѻ���ϼ���������
	 * 
	 * @param kdgs
	 * @param htbm
	 * @param kddh
	 * @param jcrq
	 * @param bz
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 */
	@RequestMapping(value = "erp/erp_dygd_74.do")
	@ResponseBody
	public void erp_dygd_74(

	String kdgs, String htbm, String kddh, String jcrq, String bz,

	Integer admin_id, Integer icbc_id, Integer type_id, Integer status_id,
			Integer yw_id, Integer gems_id, Integer fs_id,
			HttpServletRequest request) {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���

		json_result.put("74_kdgs", kdgs);
		json_result.put("74_htbm", htbm);
		json_result.put("74_kddh", kddh);
		json_result.put("74_jcrq", jcrq);

		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "��Ѻ���ϼ���������");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", bz);
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);

	}

	/**
	 * erp 73 ��Ѻ�鵵
	 * 
	 * @param bz
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 */
	@RequestMapping(value = "erp/erp_dygd_73.do")
	@ResponseBody
	public void erp_dygd_73(String bz, String ksrq, Integer admin_id,
			Integer icbc_id, Integer type_id, Integer status_id, Integer yw_id,
			Integer gems_id, Integer fs_id, HttpServletRequest request) {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���

		json_result.put("73_ksrq", ksrq);

		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "��֤��¼");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", bz);
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);

	}

	/**
	 * erp �ʽ���� 51 ʵ��¼��-����
	 * 
	 * @param dz_je
	 * @param dz_date
	 * @param sk_type
	 * @param qtsm
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 */
	@RequestMapping(value = "erp/erp_zjfp_51.do")
	@ResponseBody
	public void erp_zjfp_51(Integer dz_je, String dz_date, Integer sk_type,
			String qtsm,

			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, Integer gems_id, Integer fs_id,
			HttpServletRequest request) {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���

		json_result.put("dz_je_" + status_id, dz_je);
		json_result.put("dz_date_" + status_id, dz_date);
		json_result.put("sk_type_" + status_id, sk_type);
		json_result.put("qtsm_" + status_id, qtsm);

		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "ʵ��¼��-����");
		erp_result.put("result_1_code", sk_type);
		erp_result.put("result_1_msg", "");
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toJSONString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
		System.out.println("�ж�״̬;" + sk_type + "-"
				+ sk_type.toString().equals("1"));
		if (sk_type.toString().equals("1")) {
			System.out.println("�ʽ������ɲ�������һ�����ݲ�����״̬");
			PageData p1 = new PageData();
			PageData p1_result = new PageData();
			p1.put("dn", "icbc_erp_kj_icbc");
			p1.put("mid_edit", admin_id);
			p1.put("dt_edit", creditutil.time());
			p1.put("status", 55);
			p1.put("id", yw_id);
			p1_result.put("qryid", yw_id);
			p1_result.put("mid_add", admin_id);
			p1_result.put("mid_edit", admin_id);
			p1_result.put("dt_add", creditutil.time());
			p1_result.put("dt_edit", creditutil.time());
			p1_result.put("status", 55);
			p1_result.put("status_oldht", 0);
			p1_result.put("remark", "���");
			p1_result.put("result_1_code", 0);
			p1_result.put("result_1_msg", "");
			p1_result.put("result_1_value", "");
			p1_result.put("dt_sub", creditutil.time());
			p1_result.put("type_id", type_id);
			p1_result.put("icbc_id", icbc_id);
			p1_result.put("jsonAll", "");
			p1_result.put("dn", "icbc_erp_kj_icbc_result");
			erp_wdrwService.update(p1);
			erp_wdrwService.save(p1_result);
		}
	}

	/**
	 * erp �ʽ���� 50 ����
	 * 
	 * @param gems_fs_id
	 * @param kh_bank
	 * @param kh_name
	 * @param zh
	 * @param yzfje
	 * @param fwf
	 * @param dk_date
	 * @param sjdkje
	 * @param bz
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 */
	@RequestMapping(value = "erp/erp_zjfp_50.do")
	@ResponseBody
	public void erp_zjfp_50(Integer gems_fs_id, String kh_bank, String kh_name,
			String zh, Integer yzfje, Integer fwf, String dk_date,
			Integer sjdkje, String bz,

			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, Integer gems_id, Integer fs_id,
			HttpServletRequest request) {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���

		json_result.put("gems_fs_id_" + status_id, gems_fs_id);
		json_result.put("kh_bank_" + status_id, kh_bank);
		json_result.put("kh_name_" + status_id, kh_name);
		json_result.put("zh_" + status_id, zh);
		json_result.put("yzfje_" + status_id, yzfje);
		json_result.put("fwf_" + status_id, fwf);
		json_result.put("dk_date_" + status_id, dk_date);
		json_result.put("sjdkje_" + status_id, sjdkje);
		json_result.put("bz_" + status_id, bz);

		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "����");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", "");
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toJSONString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
	}

	/**
	 * erp �ʽ���� 49
	 * 
	 * @param dzhb
	 * @param dzje
	 * @param dz_date
	 * @param dz_type
	 * @param hf_date
	 * @param hfje
	 * @param dsje
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 */
	@RequestMapping(value = "erp/erp_zjfp_49.do")
	@ResponseBody
	public void erp_zjfp_49(Integer dzhb, Integer dzje, String dz_date,
			String dz_type, String hf_date, Integer hfje, Integer dsje,
			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, Integer gems_id, Integer fs_id,
			HttpServletRequest request) {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���
		if (dz_type.equals("2")) {
			json_result.put("dzhb_" + status_id, dzhb);
			json_result.put("dzje_" + status_id, dzje);
			json_result.put("dz_date_" + status_id, dz_date);
		} else {
			json_result.put("hf_date_" + status_id, hf_date);
			json_result.put("hfje_" + status_id, hfje);
			json_result.put("dsje_" + status_id, dsje);
		}
		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "�ʽ����");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", "");
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toJSONString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
		if (dz_type.equals("2")) {
			// ������ʰ��
			PageData result_date = new PageData();
			result_date.put("dn", "yhds_tocode");
			result_date.put("type_id", 16);
			result_date.put("status", 100);
			result_date.put("icbc_id", icbc_id);
			PageData rData = erp_wdrwService.icbc_form(result_date);
			if (rData != null && !rData.equals("")) {
				PageData result_date1 = new PageData();
				result_date1.put("dn", "yhds_tocode");
				result_date1.put("type_id", 16);
				result_date1.put("status", 101);
				result_date1.put("icbc_id", icbc_id);
				PageData rData1 = erp_wdrwService.icbc_form(result_date1);
				if (rData1 != null && !rData1.equals("")) {
					System.out.println("״̬���٣�" + rData1.get("result_1_code"));
					if (rData1.get("result_1_code").toString().equals("2")) {
						PageData erp_result3 = new PageData();// erp����
						PageData erp_type3 = new PageData();// erp���
						// ��������
						erp_type3.put("dn", "icbc_erp_kj_icbc");
						erp_type3.put("mid_edit", admin_id);
						erp_type3.put("dt_edit", creditutil.time());
						erp_type3.put("status", 100);
						erp_type3.put("id", rData.get("id"));
						// ��ӽ���
						erp_result3.put("qryid", rData.get("id"));
						erp_result3.put("mid_add", admin_id);
						erp_result3.put("mid_edit", admin_id);
						erp_result3.put("dt_add", creditutil.time());
						erp_result3.put("dt_edit", creditutil.time());
						erp_result3.put("status_oldht", 0);
						erp_result3.put("status", 100);
						erp_result3.put("remark", "��ʼ");
						erp_result3.put("result_1_code", 0);
						erp_result3.put("result_1_msg", "");
						erp_result3.put("dt_sub", creditutil.time());
						erp_result3.put("type_id", 16);
						erp_result3.put("icbc_id", icbc_id);
						erp_result3.put("jsonAll", "");
						erp_result3.put("dn", "icbc_erp_kj_icbc_result");
						erp_result3.put("result_1_value", "");
						erp_wdrwService.update(erp_type3);
						erp_wdrwService.save(erp_result3);
					}
				}
			} else {
				JSONObject json_result1 = new JSONObject();// json����
				json_result1.put("dzhb", dzhb);
				json_result1.put("dzje", dzje);
				json_result1.put("dz_date", dz_date);

				PageData zjfp = new PageData();// �������
				zjfp.put("dn", "icbc_erp_kj_icbc");
				zjfp.put("mid_add", admin_id);
				zjfp.put("mid_edit", admin_id);
				zjfp.put("dt_add", creditutil.time());
				zjfp.put("dt_edit", creditutil.time());
				zjfp.put("dt_sub", creditutil.time());
				zjfp.put("dt_fin", creditutil.time());
				zjfp.put("status", 100);
				zjfp.put("icbc_id", icbc_id);
				zjfp.put("gems_id", pdsession.get("gems_id"));
				zjfp.put("gems_fs_id", pdsession.get("fs_id"));
				zjfp.put("type_id", 16);
				zjfp.put("c_name", "");
				zjfp.put("c_carno", "");
				zjfp.put("c_carvin", "");
				zjfp.put("c_cardno", "");
				erp_wdrwService.save(zjfp);
				PageData zjfp_1 = new PageData();// ����ʽ����
				zjfp_1.put("remark", "��ʼ");
				zjfp_1.put("status", 100);
				zjfp_1.put("result_1_code", 0);
				zjfp_1.put("result_1_msg", "");
				zjfp_1.put("result_1_value", json_result1.toString());
				zjfp_1.put("dt_sub", creditutil.time());
				zjfp_1.put("type_id", 16);
				zjfp_1.put("icbc_id", icbc_id);
				zjfp_1.put("jsonAll", "");
				zjfp_1.put("dn", "icbc_erp_kj_icbc_result");
				zjfp_1.put("qryid", zjfp.get("id"));
				zjfp_1.put("mid_add", admin_id);
				zjfp_1.put("mid_edit", admin_id);
				zjfp_1.put("dt_add", creditutil.time());
				zjfp_1.put("dt_edit", creditutil.time());
				zjfp_1.put("status_oldht", 0);
				erp_wdrwService.save(zjfp_1);
			}
		}
	}

	/**
	 * �ʽ����
	 * 
	 * @param xdzje
	 * @param rzfwf
	 * @param skqk
	 * @param qtyj
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 */
	@RequestMapping(value = "erp/erp_zjfp_52.do")
	@ResponseBody
	public void erp_zjfp_52(Integer xdzje, Integer rzfwf, Integer skqk,
			String qtyj,

			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, Integer gems_id, Integer fs_id,
			HttpServletRequest request) {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���
		json_result.put("xdzje_" + status_id, xdzje);
		json_result.put("rzfwf_" + status_id, rzfwf);
		json_result.put("skqk_" + status_id, skqk);
		json_result.put("qtyj_" + status_id, qtyj);
		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "���Ų���ȷ�ϵ���");
		erp_result.put("result_1_code", skqk);
		erp_result.put("result_1_msg", "");
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		erp_result.put("result_1_value", json_result.toJSONString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
	}

	/**
	 * ȷ��������ʣ�
	 * 
	 * @param kdzje
	 * @param xdzje
	 * @param rzfwf
	 * @param xdz_date
	 * @param qtyj
	 * @param files
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param dk_bj
	 * @param dz_type
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "erp/qrsqdz.do")
	@ResponseBody
	public void qrsqdz(Integer kdzje, Integer xdzje, Integer rzfwf,
			String xdz_date, String qtyj, @RequestParam MultipartFile[] files,
			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, Integer dk_bj, Integer dz_type,
			Integer gems_id, Integer fs_id, String icbc_name,
			Integer icbc_adminid, String dt_date, HttpServletRequest request)
			throws IOException {
		JSONObject json_result = new JSONObject();// json����
		PageData erp_result = new PageData();// erp����
		PageData erp_type = new PageData();// erp���
		json_result.put("dz_type_48", dz_type);
		json_result.put("kdzje_48", kdzje);
		json_result.put("xdzje_48", xdzje);
		json_result.put("rzfwf_48", rzfwf);
		json_result.put("xdz_date_48", xdz_date);
		json_result.put("qtyj_48", qtyj);
		// ��������
		erp_type.put("dn", "icbc_erp_kj_icbc");
		erp_type.put("mid_edit", admin_id);
		erp_type.put("dt_edit", creditutil.time());
		erp_type.put("status", status_id);
		erp_type.put("id", yw_id);
		// ���½���
		erp_result.put("qryid", yw_id);
		erp_result.put("mid_add", admin_id);
		erp_result.put("mid_edit", admin_id);
		erp_result.put("dt_add", creditutil.time());
		erp_result.put("dt_edit", creditutil.time());
		erp_result.put("status_oldht", 0);
		erp_result.put("status", status_id);
		erp_result.put("remark", "ȷ���������");
		erp_result.put("result_1_code", 0);
		erp_result.put("result_1_msg", "");
		erp_result.put("dt_sub", creditutil.time());
		erp_result.put("type_id", type_id);
		erp_result.put("icbc_id", icbc_id);
		erp_result.put("jsonAll", "");
		erp_result.put("dn", "icbc_erp_kj_icbc_result");
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			if (file.getSize() != 0) {
				Date date = new Date();
				String filePath = "/KCDIMG/assess/upload/"
						+ new SimpleDateFormat("yyyy/MM/dd/").format(date);
				String imgpath = "upload/"
						+ new SimpleDateFormat("yyyy/MM/dd/").format(date);
				String filename = file.getOriginalFilename();
				String prefix = filename
						.substring(filename.lastIndexOf(".") + 1);
				UUID uuid = UUID.randomUUID();
				String uuidname = uuid.toString().replaceAll("-", "") + "."
						+ prefix;
				byte[] file36Byte = file.getBytes();
				FileUtils.writeByteArrayToFile(new File(filePath + uuidname),
						file36Byte);
				System.out.println("ͼƬ·����" + filePath + uuidname);
				json_result.put("img_48_" + i, imgpath + uuidname);
			}

		}
		erp_result.put("result_1_value", json_result.toJSONString());
		erp_wdrwService.update(erp_type);
		erp_wdrwService.save(erp_result);
	}

	/**
	 * erp ����������˰��
	 * 
	 * @param ds_status
	 * @param dk_status
	 * @param yjsm
	 * @param dk_id
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 * @param dk_bj
	 * @param dz_type
	 * @param gems_id
	 * @param fs_id
	 * @param request
	 */
	@RequestMapping(value = "erp/erp_dksh.do", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void erp_dksh(String ds_status, String dk_status, String yjsm,
			Integer dk_id, Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, String dk_bj, String dz_type,
			Integer gems_id, Integer fs_id, String icbc_name,
			Integer icbc_adminid, String dt_date, HttpServletRequest request) {
		int dkbj = 0;
		if (dk_bj != null && !dk_bj.equals("")) {
			dkbj = Integer.parseInt(dk_bj);
		}
		int dztype = 0;
		if (dz_type != null && !dz_type.equals("")) {
			dztype = Integer.parseInt(dz_type);
		}
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		icbc icbc = new icbc();
		PageData pData = new PageData();// json����
		PageData pd = new PageData();// ����icbc_erp_kj_icbc
		PageData kir = new PageData();// ���icbc_erp_kj_icbc_result
		icbc_kk kk = new icbc_kk();// ������
		icbc_result icbc_result = new icbc_result();// ��¼
		icbc_dk icbc_dk = new icbc_dk();
		icbc_dk icbc_dk1 = new icbc_dk();
		PageData kir1 = new PageData();// �����ϼ�������

		int status = 0;
		switch (dk_status) {
		case "1":
			status = 3;
			break;
		case "2":
			status = 8;
			break;
		case "3":
			status = 4;
			break;
		default:
			status = 0;
			break;
		}
		String content = "";
		// json����
		JSONObject json = new JSONObject();
		json.put("ds_status", ds_status);
		json.put("yjsm", yjsm);

		kir.put("qryid", yw_id);
		kir.put("mid_add", admin_id);
		kir.put("mid_edit", admin_id);
		kir.put("dt_add", creditutil.time());
		kir.put("dt_edit", creditutil.time());
		kir.put("status", status_id);
		kir.put("status_oldht", status);
		if (status_id == 33) {
			content = "רԱ��˽��";
			kir.put("remark", "רԱ��˽��");
			icbc_dk.setSh_status(1);
		} else if (status_id == 35) {
			content = "������˽��";
			kir.put("remark", "������˽��");
			icbc_dk.setSh_status(2);
		} else if (status_id == 37) {
			content = "������˽��";
			kir.put("remark", "������˽��");
			icbc_dk.setSh_status(3);
		} else if (status_id == 39) {
			content = "�ܼ���˽��";
			kir.put("remark", "�ܼ���˽��");
			icbc_dk.setSh_status(4);
		}
		System.out.println("����:" + dk_status);
		kir.put("result_1_code", dk_status);
		kir.put("result_1_msg", yjsm);
		kir.put("result_1_value", json.toString());
		kir.put("dt_sub", creditutil.time());
		kir.put("type_id", type_id);
		kir.put("icbc_id", icbc_id);
		kir.put("jsonAll", "");
		kir.put("dn", "icbc_erp_kj_icbc_result");

		pd.put("dn", "icbc_erp_kj_icbc");
		pd.put("mid_edit", admin_id);
		pd.put("dt_edit", creditutil.time());
		pd.put("status", (status_id+1)); //add
		pd.put("id", yw_id);
		// ���´����
		icbc_dk.setBc_status(status);
		icbc_dk.setDt_edit(creditutil.time());
		icbc_dk.setMid_edit(admin_id);
		icbc_dk.setId(dk_id);

		// ������˼�¼
		icbc_result.setDt_add(creditutil.time());
		icbc_result.setDt_edit(creditutil.time());
		icbc_result.setQryid(dk_id);
		icbc_result.setStatus(status);
		icbc_result.setMid_add(admin_id);
		icbc_result.setMid_edit(admin_id);
		icbc_result.setRemark(yjsm);
		// �����ϼ�����
		kir1.put("result_1_code", 0);
		kir1.put("result_1_msg", "");
		kir1.put("result_1_value", "");
		kir1.put("dt_sub", creditutil.time());
		kir1.put("type_id", type_id);
		kir1.put("icbc_id", icbc_id);
		kir1.put("jsonAll", "");
		kir1.put("dn", "icbc_erp_kj_icbc_result");
		kir1.put("qryid", yw_id);
		kir1.put("mid_add", admin_id);
		kir1.put("mid_edit", admin_id);
		kir1.put("dt_add", creditutil.time());
		kir1.put("dt_edit", creditutil.time());
		kir1.put("status_oldht", status);
		icbc_dkService.updk(icbc_dk);
		erp_wdrwService.update(pd);
		erp_wdrwService.save(kir);
		icbc_result1Service.addcardk_result(icbc_result);

		// ������Ϣ����
		admin admin = adminService.adminbyid(icbc_adminid);
		if (admin != null && !admin.equals("")) {
			String REGISTRATION_ID = admin.getJgid();
			String mString = admin.getName() + "����!" + "�ͻ�����Ϊ" + icbc_name
					+ "������Ѹ���" + ";��������:" + content + ";��ע:" + yjsm + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(icbc_adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}

		// �ȼ���˲���
		icbc_dk1.setDt_edit(creditutil.time());
		icbc_dk1.setMid_edit(admin_id);
		icbc_dk1.setId(dk_id);

		if (status_id == 33) {
			if (dk_status.equals("1") || dk_status.equals("2")) {
				if (dkbj >= 150000) {
					kir1.put("status", 34);
					kir1.put("remark", "���������");
					// ���´����
					icbc_dk1.setBc_status(2);
					icbc_dkService.updk(icbc_dk1);
					erp_wdrwService.save(kir1);
				} else if (dkbj < 150000) {
					if ((dk_status.equals("1") || dk_status.equals("2"))
							&& dztype == 2) {
						PageData result_date = new PageData();
						result_date.put("dn", "yhds_tocode");
						result_date.put("type_id", 10);
						result_date.put("status", 47);
						result_date.put("icbc_id", icbc_id);
						PageData rData = erp_wdrwService.icbc_form(result_date);
						if (rData == null || rData.equals("")) {
							PageData zjfp = new PageData();// ����ʽ����
							zjfp.put("dn", "icbc_erp_kj_icbc");
							zjfp.put("mid_add", admin_id);
							zjfp.put("mid_edit", admin_id);
							zjfp.put("dt_add", creditutil.time());
							zjfp.put("dt_edit", creditutil.time());
							zjfp.put("dt_sub", creditutil.time());
							zjfp.put("dt_fin", creditutil.time());
							zjfp.put("status", 47);
							zjfp.put("icbc_id", icbc_id);
							zjfp.put("gems_id", pdsession.get("gems_id"));
							zjfp.put("gems_fs_id", pdsession.get("fs_id"));
							zjfp.put("type_id", 10);
							zjfp.put("c_name", "");
							zjfp.put("c_carno", "");
							zjfp.put("c_carvin", "");
							zjfp.put("c_cardno", "");
							erp_wdrwService.save(zjfp);
							PageData zjfp_1 = new PageData();// ����ʽ����
							zjfp_1.put("result_1_code", 0);
							zjfp_1.put("result_1_msg", "");
							zjfp_1.put("result_1_value", "");
							zjfp_1.put("dt_sub", creditutil.time());
							zjfp_1.put("type_id", 10);
							zjfp_1.put("icbc_id", icbc_id);
							zjfp_1.put("jsonAll", "");
							zjfp_1.put("dn", "icbc_erp_kj_icbc_result");
							zjfp_1.put("qryid", zjfp.get("id"));
							zjfp_1.put("mid_add", admin_id);
							zjfp_1.put("mid_edit", admin_id);
							zjfp_1.put("dt_add", creditutil.time());
							zjfp_1.put("dt_edit", creditutil.time());
							zjfp_1.put("status_oldht", 0);
							zjfp_1.put("status", 47);
							erp_wdrwService.save(zjfp_1);
						}
					}
					if (dk_status.equals("1")) {
						PageData result_date = new PageData();
						result_date.put("dn", "yhds_tocode");
						result_date.put("type_id", 11);
						result_date.put("status", 56);
						result_date.put("icbc_id", icbc_id);
						PageData rData = erp_wdrwService.icbc_form(result_date);
						if (rData == null || rData.equals("")) {
							PageData zjfp = new PageData();// ���д�������
							zjfp.put("dn", "icbc_erp_kj_icbc");
							zjfp.put("mid_add", admin_id);
							zjfp.put("mid_edit", admin_id);
							zjfp.put("dt_add", creditutil.time());
							zjfp.put("dt_edit", creditutil.time());
							zjfp.put("dt_sub", creditutil.time());
							zjfp.put("dt_fin", creditutil.time());
							zjfp.put("status", 56);
							zjfp.put("icbc_id", icbc_id);
							zjfp.put("gems_id", gems_id);
							zjfp.put("gems_fs_id", fs_id);
							zjfp.put("type_id", 11);
							zjfp.put("c_name", "");
							zjfp.put("c_carno", "");
							zjfp.put("c_carvin", "");
							zjfp.put("c_cardno", "");
							erp_wdrwService.save(zjfp);
							PageData zjfp_1 = new PageData();// ������д�������
							zjfp_1.put("result_1_code", 0);
							zjfp_1.put("result_1_msg", "");
							zjfp_1.put("result_1_value", "");
							zjfp_1.put("dt_sub", creditutil.time());
							zjfp_1.put("type_id", 11);
							zjfp_1.put("icbc_id", icbc_id);
							zjfp_1.put("jsonAll", "");
							zjfp_1.put("dn", "icbc_erp_kj_icbc_result");
							zjfp_1.put("qryid", zjfp.get("id"));
							zjfp_1.put("mid_add", admin_id);
							zjfp_1.put("mid_edit", admin_id);
							zjfp_1.put("dt_add", creditutil.time());
							zjfp_1.put("dt_edit", creditutil.time());
							zjfp_1.put("status_oldht", 0);
							zjfp_1.put("remark", "���д������뿪ʼ");
							zjfp_1.put("status", 56);
							erp_wdrwService.save(zjfp_1);
						}
					}
				}
			}

		} else if (status_id == 35) {
			if (dk_status.equals("1") || dk_status.equals("2")) {
				if (dkbj >= 300000) {
					kir1.put("status", 36);
					kir1.put("remark", "���������");
					// ���´����
					icbc_dk1.setBc_status(2);
					icbc_dkService.updk(icbc_dk1);
					erp_wdrwService.save(kir1);
				} else if (dkbj < 300000) {
					if ((dk_status.equals("1") || dk_status.equals("2"))
							&& dztype == 2) {
						PageData result_date = new PageData();
						result_date.put("dn", "yhds_tocode");
						result_date.put("type_id", 10);
						result_date.put("status", 47);
						result_date.put("icbc_id", icbc_id);
						PageData rData = erp_wdrwService.icbc_form(result_date);
						if (rData == null || rData.equals("")) {
							PageData zjfp = new PageData();// ����ʽ����
							zjfp.put("dn", "icbc_erp_kj_icbc");
							zjfp.put("mid_add", admin_id);
							zjfp.put("mid_edit", admin_id);
							zjfp.put("dt_add", creditutil.time());
							zjfp.put("dt_edit", creditutil.time());
							zjfp.put("dt_sub", creditutil.time());
							zjfp.put("dt_fin", creditutil.time());
							zjfp.put("status", 47);
							zjfp.put("icbc_id", icbc_id);
							zjfp.put("gems_id", pdsession.get("gems_id"));
							zjfp.put("gems_fs_id", pdsession.get("fs_id"));
							zjfp.put("type_id", 10);
							zjfp.put("c_name", "");
							zjfp.put("c_carno", "");
							zjfp.put("c_carvin", "");
							zjfp.put("c_cardno", "");
							erp_wdrwService.save(zjfp);
							PageData zjfp_1 = new PageData();// ����ʽ����
							zjfp_1.put("result_1_code", 0);
							zjfp_1.put("result_1_msg", "");
							zjfp_1.put("result_1_value", "");
							zjfp_1.put("dt_sub", creditutil.time());
							zjfp_1.put("type_id", 10);
							zjfp_1.put("icbc_id", icbc_id);
							zjfp_1.put("jsonAll", "");
							zjfp_1.put("dn", "icbc_erp_kj_icbc_result");
							zjfp_1.put("qryid", zjfp.get("id"));
							zjfp_1.put("mid_add", admin_id);
							zjfp_1.put("mid_edit", admin_id);
							zjfp_1.put("dt_add", creditutil.time());
							zjfp_1.put("dt_edit", creditutil.time());
							zjfp_1.put("status_oldht", 0);
							zjfp_1.put("remark", "��ʼ");
							zjfp_1.put("status", 47);
							erp_wdrwService.save(zjfp_1);
						}
					}
					if (dk_status.equals("1")) {
						PageData result_date = new PageData();
						result_date.put("dn", "yhds_tocode");
						result_date.put("type_id", 11);
						result_date.put("status", 56);
						result_date.put("icbc_id", icbc_id);
						PageData rData = erp_wdrwService.icbc_form(result_date);
						if (rData == null || rData.equals("")) {
							PageData zjfp = new PageData();// ���д�������
							zjfp.put("dn", "icbc_erp_kj_icbc");
							zjfp.put("mid_add", admin_id);
							zjfp.put("mid_edit", admin_id);
							zjfp.put("dt_add", creditutil.time());
							zjfp.put("dt_edit", creditutil.time());
							zjfp.put("dt_sub", creditutil.time());
							zjfp.put("dt_fin", creditutil.time());
							zjfp.put("status", 56);
							zjfp.put("icbc_id", icbc_id);
							zjfp.put("gems_id", gems_id);
							zjfp.put("gems_fs_id", fs_id);
							zjfp.put("type_id", 11);
							zjfp.put("c_name", "");
							zjfp.put("c_carno", "");
							zjfp.put("c_carvin", "");
							zjfp.put("c_cardno", "");
							erp_wdrwService.save(zjfp);
							PageData zjfp_1 = new PageData();// ������д�������
							zjfp_1.put("result_1_code", 0);
							zjfp_1.put("result_1_msg", "");
							zjfp_1.put("result_1_value", "");
							zjfp_1.put("dt_sub", creditutil.time());
							zjfp_1.put("type_id", 11);
							zjfp_1.put("icbc_id", icbc_id);
							zjfp_1.put("jsonAll", "");
							zjfp_1.put("dn", "icbc_erp_kj_icbc_result");
							zjfp_1.put("qryid", zjfp.get("id"));
							zjfp_1.put("mid_add", admin_id);
							zjfp_1.put("mid_edit", admin_id);
							zjfp_1.put("dt_add", creditutil.time());
							zjfp_1.put("dt_edit", creditutil.time());
							zjfp_1.put("status_oldht", 0);
							zjfp_1.put("remark", "���д������뿪ʼ");
							zjfp_1.put("status", 56);
							erp_wdrwService.save(zjfp_1);
						}
					}
				}
			}
		} else if (status_id == 37) {
			if (dk_status.equals("1") || dk_status.equals("2")) {
				if (dkbj >= 600000) {
					kir1.put("status", 38);
					kir1.put("remark", "�ܼ������");
					// ���´����
					icbc_dk1.setBc_status(2);
					icbc_dkService.updk(icbc_dk1);
					erp_wdrwService.save(kir1);
				} else if (dkbj < 600000) {
					if ((dk_status.equals("1") || dk_status.equals("2"))
							&& dztype == 2) {
						PageData result_date = new PageData();
						result_date.put("dn", "yhds_tocode");
						result_date.put("type_id", 10);
						result_date.put("status", 47);
						result_date.put("icbc_id", icbc_id);
						PageData rData = erp_wdrwService.icbc_form(result_date);
						if (rData == null || rData.equals("")) {
							PageData zjfp = new PageData();// ����ʽ����
							zjfp.put("dn", "icbc_erp_kj_icbc");
							zjfp.put("mid_add", admin_id);
							zjfp.put("mid_edit", admin_id);
							zjfp.put("dt_add", creditutil.time());
							zjfp.put("dt_edit", creditutil.time());
							zjfp.put("dt_sub", creditutil.time());
							zjfp.put("dt_fin", creditutil.time());
							zjfp.put("status", 47);
							zjfp.put("icbc_id", icbc_id);
							zjfp.put("gems_id", pdsession.get("gems_id"));
							zjfp.put("gems_fs_id", pdsession.get("fs_id"));
							zjfp.put("type_id", 10);
							zjfp.put("c_name", "");
							zjfp.put("c_carno", "");
							zjfp.put("c_carvin", "");
							zjfp.put("c_cardno", "");
							erp_wdrwService.save(zjfp);
							PageData zjfp_1 = new PageData();// ����ʽ����
							zjfp_1.put("result_1_code", 0);
							zjfp_1.put("result_1_msg", "");
							zjfp_1.put("result_1_value", "");
							zjfp_1.put("dt_sub", creditutil.time());
							zjfp_1.put("type_id", 10);
							zjfp_1.put("icbc_id", icbc_id);
							zjfp_1.put("jsonAll", "");
							zjfp_1.put("dn", "icbc_erp_kj_icbc_result");
							zjfp_1.put("qryid", zjfp.get("id"));
							zjfp_1.put("mid_add", admin_id);
							zjfp_1.put("mid_edit", admin_id);
							zjfp_1.put("dt_add", creditutil.time());
							zjfp_1.put("dt_edit", creditutil.time());
							zjfp_1.put("status_oldht", 0);
							zjfp_1.put("remark", "��ʼ");
							zjfp_1.put("status", 47);
							erp_wdrwService.save(zjfp_1);
						}
					}
					if (dk_status.equals("1")) {
						PageData result_date = new PageData();
						result_date.put("dn", "yhds_tocode");
						result_date.put("type_id", 11);
						result_date.put("status", 56);
						result_date.put("icbc_id", icbc_id);
						PageData rData = erp_wdrwService.icbc_form(result_date);
						if (rData == null || rData.equals("")) {
							PageData zjfp = new PageData();// ���д�������
							zjfp.put("dn", "icbc_erp_kj_icbc");
							zjfp.put("mid_add", admin_id);
							zjfp.put("mid_edit", admin_id);
							zjfp.put("dt_add", creditutil.time());
							zjfp.put("dt_edit", creditutil.time());
							zjfp.put("dt_sub", creditutil.time());
							zjfp.put("dt_fin", creditutil.time());
							zjfp.put("status", 56);
							zjfp.put("icbc_id", icbc_id);
							zjfp.put("gems_id", gems_id);
							zjfp.put("gems_fs_id", fs_id);
							zjfp.put("type_id", 11);
							zjfp.put("c_name", "");
							zjfp.put("c_carno", "");
							zjfp.put("c_carvin", "");
							zjfp.put("c_cardno", "");
							erp_wdrwService.save(zjfp);
							PageData zjfp_1 = new PageData();// ������д�������
							zjfp_1.put("result_1_code", 0);
							zjfp_1.put("result_1_msg", "");
							zjfp_1.put("result_1_value", "");
							zjfp_1.put("dt_sub", creditutil.time());
							zjfp_1.put("type_id", 11);
							zjfp_1.put("icbc_id", icbc_id);
							zjfp_1.put("jsonAll", "");
							zjfp_1.put("dn", "icbc_erp_kj_icbc_result");
							zjfp_1.put("qryid", zjfp.get("id"));
							zjfp_1.put("mid_add", admin_id);
							zjfp_1.put("mid_edit", admin_id);
							zjfp_1.put("dt_add", creditutil.time());
							zjfp_1.put("dt_edit", creditutil.time());
							zjfp_1.put("status_oldht", 0);
							zjfp_1.put("remark", "���д������뿪ʼ");
							zjfp_1.put("status", 56);
							erp_wdrwService.save(zjfp_1);
						}
					}
				}

			}
		} else if (status_id == 39) {
			if ((dk_status.equals("1") || dk_status.equals("2"))) {
				if (dztype == 2) {
					PageData result_date = new PageData();
					result_date.put("dn", "yhds_tocode");
					result_date.put("type_id", 10);
					result_date.put("status", 47);
					result_date.put("icbc_id", icbc_id);
					PageData rData = erp_wdrwService.icbc_form(result_date);
					if (rData == null || rData.equals("")) {
						PageData zjfp = new PageData();// ����ʽ����
						zjfp.put("dn", "icbc_erp_kj_icbc");
						zjfp.put("mid_add", admin_id);
						zjfp.put("mid_edit", admin_id);
						zjfp.put("dt_add", creditutil.time());
						zjfp.put("dt_edit", creditutil.time());
						zjfp.put("dt_sub", creditutil.time());
						zjfp.put("dt_fin", creditutil.time());
						zjfp.put("status", 47);
						zjfp.put("icbc_id", icbc_id);
						zjfp.put("gems_id", pdsession.get("gems_id"));
						zjfp.put("gems_fs_id", pdsession.get("fs_id"));
						zjfp.put("type_id", 10);
						zjfp.put("c_name", "");
						zjfp.put("c_carno", "");
						zjfp.put("c_carvin", "");
						zjfp.put("c_cardno", "");
						erp_wdrwService.save(zjfp);
						PageData zjfp_1 = new PageData();// ����ʽ����
						zjfp_1.put("result_1_code", 0);
						zjfp_1.put("result_1_msg", "");
						zjfp_1.put("result_1_value", "");
						zjfp_1.put("dt_sub", creditutil.time());
						zjfp_1.put("type_id", 10);
						zjfp_1.put("icbc_id", icbc_id);
						zjfp_1.put("jsonAll", "");
						zjfp_1.put("dn", "icbc_erp_kj_icbc_result");
						zjfp_1.put("qryid", zjfp.get("id"));
						zjfp_1.put("mid_add", admin_id);
						zjfp_1.put("mid_edit", admin_id);
						zjfp_1.put("dt_add", creditutil.time());
						zjfp_1.put("dt_edit", creditutil.time());
						zjfp_1.put("status_oldht", 0);
						zjfp_1.put("remark", "��ʼ");
						zjfp_1.put("status", 47);
						erp_wdrwService.save(zjfp_1);
					}
				}
			}
			if (dk_status.equals("1")) {
				PageData result_date = new PageData();
				result_date.put("dn", "yhds_tocode");
				result_date.put("type_id", 11);
				result_date.put("status", 56);
				result_date.put("icbc_id", icbc_id);
				PageData rData = erp_wdrwService.icbc_form(result_date);
				if (rData == null || rData.equals("")) {
					PageData zjfp = new PageData();// ���д�������
					zjfp.put("dn", "icbc_erp_kj_icbc");
					zjfp.put("mid_add", admin_id);
					zjfp.put("mid_edit", admin_id);
					zjfp.put("dt_add", creditutil.time());
					zjfp.put("dt_edit", creditutil.time());
					zjfp.put("dt_sub", creditutil.time());
					zjfp.put("dt_fin", creditutil.time());
					zjfp.put("status", 56);
					zjfp.put("icbc_id", icbc_id);
					zjfp.put("gems_id", gems_id);
					zjfp.put("gems_fs_id", fs_id);
					zjfp.put("type_id", 11);
					zjfp.put("c_name", "");
					zjfp.put("c_carno", "");
					zjfp.put("c_carvin", "");
					zjfp.put("c_cardno", "");
					erp_wdrwService.save(zjfp);
					PageData zjfp_1 = new PageData();// ������д�������
					zjfp_1.put("result_1_code", 0);
					zjfp_1.put("result_1_msg", "");
					zjfp_1.put("result_1_value", "");
					zjfp_1.put("dt_sub", creditutil.time());
					zjfp_1.put("type_id", 11);
					zjfp_1.put("icbc_id", icbc_id);
					zjfp_1.put("jsonAll", "");
					zjfp_1.put("dn", "icbc_erp_kj_icbc_result");
					zjfp_1.put("qryid", zjfp.get("id"));
					zjfp_1.put("mid_add", admin_id);
					zjfp_1.put("mid_edit", admin_id);
					zjfp_1.put("dt_add", creditutil.time());
					zjfp_1.put("dt_edit", creditutil.time());
					zjfp_1.put("status_oldht", 0);
					zjfp_1.put("remark", "���д������뿪ʼ");
					zjfp_1.put("status", 56);
					erp_wdrwService.save(zjfp_1);
				}
			}
		}

	}

	/**
	 * erp �������
	 * 
	 * @param kk_status
	 * @param yjsm
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 */
	@RequestMapping(value = "erp/erp_kksh.do", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void erp_kksh(String kk_status, String kk_date, String kk_kh,
			String kk_bz, Integer kk_id, Integer admin_id, Integer icbc_id,
			Integer type_id, Integer status_id, Integer yw_id,
			String icbc_name, Integer icbc_adminid, String dt_date) {
		icbc icbc = new icbc();
		PageData pData = new PageData();// json����
		PageData pd = new PageData();// ����icbc_erp_kj_icbc
		PageData kir = new PageData();// ���icbc_erp_kj_icbc_result
		icbc_kk kk = new icbc_kk();// ������
		icbc_result icbc_result = new icbc_result();// ��¼
		int status = 0;
		switch (kk_status) {
		case "1":
			status = 7;
			break;
		case "2":
			status = 5;
			break;
		case "3":
			status = 4;
			break;
		default:
			status = 0;
			break;
		}
		kir.put("qryid", yw_id);
		kir.put("mid_add", admin_id);
		kir.put("mid_edit", admin_id);
		kir.put("dt_add", dt_date);
		kir.put("dt_edit", creditutil.time());
		kir.put("status", status_id);
		kir.put("status_oldht", status);
		kir.put("remark", "�����������");
		kir.put("result_1_code", kk_status);
		kir.put("result_1_msg", kk_bz);
		System.out.println("json���ݣ�" + jsonutil.toJSONString(pData));
		kir.put("result_1_value", "");
		kir.put("dt_sub", creditutil.time());
		kir.put("type_id", type_id);
		kir.put("icbc_id", icbc_id);
		kir.put("jsonAll", "");
		kir.put("dn", "icbc_erp_kj_icbc_result");

		pd.put("dn", "icbc_erp_kj_icbc");
		pd.put("mid_edit", admin_id);
		pd.put("dt_edit", creditutil.time());
		pd.put("status", status_id);
		pd.put("id", yw_id);
		// ���¿�����
		kk.setMid_edit(admin_id);
		kk.setDt_edit(creditutil.time());
		kk.setDt_sub(creditutil.time());
		kk.setBc_status(status);
		kk.setKk_kh(kk_kh);
		kk.setKk_date(kk_date);
		kk.setId(kk_id);
		// ������˼�¼
		icbc_result.setDt_add(creditutil.time());
		icbc_result.setDt_edit(creditutil.time());
		icbc_result.setQryid(kk_id);
		icbc_result.setStatus(status);
		icbc_result.setMid_add(admin_id);
		icbc_result.setMid_edit(admin_id);
		icbc_result.setRemark(kk_bz);
		newicbc_kkService.upkk(kk);
		erp_wdrwService.update(pd);
		erp_wdrwService.save(kir);
		icbc_result1Service.addkk_result(icbc_result);
		if (kk_status.equals("1") || kk_status.equals("2")) {
			PageData p1 = new PageData();
			PageData p1_result = new PageData();
			p1.put("dn", "icbc_erp_kj_icbc");
			p1.put("mid_edit", admin_id);
			p1.put("dt_edit", creditutil.time());
			p1.put("status", 21);
			p1.put("id", yw_id);
			p1_result.put("qryid", yw_id);
			p1_result.put("mid_add", admin_id);
			p1_result.put("mid_edit", admin_id);
			p1_result.put("dt_add", creditutil.time());
			p1_result.put("dt_edit", creditutil.time());
			p1_result.put("status", 21);
			p1_result.put("status_oldht", status);
			p1_result.put("remark", "���");
			p1_result.put("result_1_code", 0);
			p1_result.put("result_1_msg", "");
			p1_result.put("result_1_value", "");
			p1_result.put("dt_sub", creditutil.time());
			p1_result.put("type_id", type_id);
			p1_result.put("icbc_id", icbc_id);
			p1_result.put("jsonAll", "");
			p1_result.put("dn", "icbc_erp_kj_icbc_result");
			erp_wdrwService.update(p1);
			erp_wdrwService.save(p1_result);
		}
		// ������Ϣ����
		admin admin = adminService.adminbyid(icbc_adminid);
		if (admin != null && !admin.equals("")) {
			String REGISTRATION_ID = admin.getJgid();
			String mString = admin.getName() + "����!" + "�ͻ�����Ϊ" + icbc_name
					+ "������Ѹ���" + ";����:�����������" + ";��ע:" + kk_bz + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(icbc_adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}

	}

	/**
	 * erp������ݺ˲�
	 * 
	 * @param kk_status
	 * @param yjsm
	 * @param kk_id
	 * @param admin_id
	 * @param icbc_id
	 * @param type_id
	 * @param status_id
	 * @param yw_id
	 */
	@RequestMapping(value = "erp/erp_kksfsh.do", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void erp_kksfsh(String kk_status, String yjsm, Integer kk_id,
			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, String icbc_name,
			Integer icbc_adminid, String dt_date) {
		icbc icbc = new icbc();
		PageData pData = new PageData();// json����
		PageData pd = new PageData();// ����icbc_erp_kj_icbc
		PageData kir = new PageData();// ���icbc_erp_kj_icbc_result
		icbc_kk kk = new icbc_kk();// ������
		icbc_result icbc_result = new icbc_result();// ��¼
		int status = 0;
		switch (kk_status) {
		case "1":
			status = 3;
			break;
		case "3":
			status = 4;
			break;
		default:
			status = 0;
			break;
		}
		kir.put("qryid", yw_id);
		kir.put("mid_add", admin_id);
		kir.put("mid_edit", admin_id);
		kir.put("dt_add", dt_date);
		kir.put("dt_edit", creditutil.time());
		kir.put("status", status_id);
		kir.put("status_oldht", status);
		kir.put("remark", "��ݺ˲���");
		kir.put("result_1_code", kk_status);
		kir.put("result_1_msg", yjsm);
		System.out.println("json���ݣ�" + jsonutil.toJSONString(pData));
		kir.put("result_1_value", "");
		kir.put("dt_sub", creditutil.time());
		kir.put("type_id", type_id);
		kir.put("icbc_id", icbc_id);
		kir.put("jsonAll", "");
		kir.put("dn", "icbc_erp_kj_icbc_result");

		pd.put("dn", "icbc_erp_kj_icbc");
		pd.put("mid_edit", admin_id);
		pd.put("dt_edit", creditutil.time());
		pd.put("status", status_id);
		pd.put("id", yw_id);
		// ���¿�����
		kk.setMid_edit(admin_id);
		kk.setDt_edit(creditutil.time());
		kk.setDt_sub(creditutil.time());
		kk.setBc_status(status);
		kk.setId(kk_id);
		// ������˼�¼
		icbc_result.setDt_add(creditutil.time());
		icbc_result.setDt_edit(creditutil.time());
		icbc_result.setQryid(kk_id);
		icbc_result.setStatus(status);
		icbc_result.setMid_add(admin_id);
		icbc_result.setMid_edit(admin_id);
		icbc_result.setRemark(yjsm);
		newicbc_kkService.upkk(kk);
		erp_wdrwService.update(pd);
		erp_wdrwService.save(kir);
		icbc_result1Service.addkk_result(icbc_result);

		// ������Ϣ����
		admin admin = adminService.adminbyid(icbc_adminid);
		System.out.println("��ȡ�û���Ϣid��" + admin.getName() + ":" + admin.getId()
				+ ":" + admin.getJgid());
		if (admin != null && !admin.equals("")) {
			String REGISTRATION_ID = admin.getJgid();
			String mString = admin.getName() + "����!" + "�ͻ�����Ϊ" + icbc_name
					+ "������Ѹ���" + ";����:��ݺ˲���" + ";��ע:" + yjsm + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(icbc_adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
			System.out.println("���ͳɹ�");
		}
	}

	/*
	 * erp�������
	 */
	@RequestMapping(value = "erp/erp_zxsh.do", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void erp_zxsh(String zx_status, String zx_remark, Integer zx_type,
			String zx_result1, String zx_result2, String zx_result3,
			String zx_result4, String dsj_report_id, String gjr_dsj_report_id1,
			String gjr_dsj_report_id2, String po_dsj_report_id,
			Integer admin_id, Integer icbc_id, Integer type_id,
			Integer status_id, Integer yw_id, String icbc_name,
			Integer icbc_adminid, String dt_date) {
		icbc icbc = new icbc();
		JSONObject json = new JSONObject();// json����
		PageData pd = new PageData();// ����icbc_erp_kj_icbc
		PageData kir = new PageData();// ���icbc_erp_kj_icbc_result
		icbc_result icbc_result = new icbc_result();
		int zx_status1 = 0;
		if (zx_status.equals("1")) {
			zx_status1 = 3;
			icbc.setDt_fin(creditutil.time());
			pd.put("dt_fin", creditutil.time());
		} else if (zx_status.equals("2")) {
			zx_status1 = 5;
		} else if (zx_status.equals("3")) {
			zx_status1 = 4;
		}
		json.put("zx_result", zx_result1);
		json.put("gjr_zx_result1", zx_result2);
		json.put("gjr_zx_result2", zx_result3);
		json.put("po_zx_result", zx_result4);

		kir.put("qryid", yw_id);
		kir.put("mid_add", admin_id);
		kir.put("mid_edit", admin_id);
		kir.put("dt_add", dt_date);
		kir.put("dt_edit", creditutil.time());
		kir.put("status", status_id);
		kir.put("status_oldht", zx_status1);
		kir.put("remark", "��ѯ���");
		kir.put("result_1_code", zx_status);
		kir.put("result_1_msg", zx_remark);
		kir.put("result_1_value", json.toString());
		kir.put("dt_sub", creditutil.time());
		kir.put("type_id", type_id);
		kir.put("icbc_id", icbc_id);
		kir.put("jsonAll", "");
		kir.put("dn", "icbc_erp_kj_icbc_result");

		pd.put("dn", "icbc_erp_kj_icbc");
		pd.put("mid_edit", admin_id);
		pd.put("dt_edit", creditutil.time());
		pd.put("status", status_id);
		pd.put("id", yw_id);
		// ��������
		icbc.setBc_status(zx_status1);
		icbc.setDt_edit(creditutil.time());
		icbc.setZx_result(zx_result1);
		icbc.setGjr_zx_result1(zx_result2);
		icbc.setGjr_zx_result2(zx_result3);
		icbc.setPo_zx_result(zx_result4);
		icbc.setDsj_report_id(dsj_report_id);
		icbc.setPo_dsj_report_id(po_dsj_report_id);
		icbc.setGjr_dsj_report_id1(gjr_dsj_report_id1);
		icbc.setGjr_dsj_report_id2(gjr_dsj_report_id2);
		icbc.setMid_edit(admin_id);
		icbc.setDt_zxresult(creditutil.time());
		icbc.setId(icbc_id);
		if (zx_status != null && !zx_status.endsWith("")) {
			if (zx_status.equals("1")) {
				icbc.setZxok_tag(1);
			} else {
				icbc.setZxok_tag(0);
			}
		}
		// ������˼�¼
		icbc_result.setDt_add(creditutil.time());
		icbc_result.setDt_edit(creditutil.time());
		icbc_result.setQryid(icbc_id);
		icbc_result.setStatus(zx_status1);
		icbc_result.setMid_add(admin_id);
		icbc_result.setMid_edit(admin_id);
		icbc_result.setRemark(zx_remark);
		newicbcService.upicbc(icbc);
		erp_wdrwService.update(pd);
		erp_wdrwService.save(kir);
		icbc_result1Service.addicbc_result(icbc_result);
		// �������һ����¼
		Map map = icbcmodel.zx_status();// ����״̬
		if (zx_status.equals("1") || zx_status.equals("2")) {
			PageData p1 = new PageData();
			PageData p1_result = new PageData();
			p1.put("dn", "icbc_erp_kj_icbc");
			p1.put("mid_edit", admin_id);
			p1.put("dt_edit", creditutil.time());
			p1.put("status", 4);
			p1.put("id", yw_id);
			p1_result.put("qryid", yw_id);
			p1_result.put("mid_add", admin_id);
			p1_result.put("mid_edit", admin_id);
			p1_result.put("dt_add", creditutil.time());
			p1_result.put("dt_edit", creditutil.time());
			p1_result.put("status", 4);
			p1_result.put("status_oldht", zx_status1);
			p1_result.put("remark", "���");
			p1_result.put("result_1_code", 0);
			p1_result.put("result_1_msg", "");
			p1_result.put("result_1_value", "");
			p1_result.put("dt_sub", creditutil.time());
			p1_result.put("type_id", type_id);
			p1_result.put("icbc_id", icbc_id);
			p1_result.put("jsonAll", "");
			p1_result.put("dn", "icbc_erp_kj_icbc_result");
			erp_wdrwService.update(p1);
			erp_wdrwService.save(p1_result);
		}
		admin admin = adminService.adminbyid(icbc_adminid);
		if (admin != null && !admin.equals("")) {
			String REGISTRATION_ID = admin.getJgid();
			String mString = admin.getName() + "����!" + "�ͻ�����Ϊ" + icbc_name
					+ "������Ѹ���" + ";����:��ѯ���;��ע:" + zx_remark + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(icbc_adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erp����ͨ���������
	 */
	@RequestMapping(value = "erp/erp_zxtryhsh.do", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void erp_zxtryhsh(String result_value, Integer admin_id,
			Integer icbc_id, Integer type_id, Integer status_id, Integer yw_id,
			String icbc_name, Integer icbc_adminid, String dt_date) {
		icbc icbc = new icbc();
		PageData pData = new PageData();// json����
		PageData pd = new PageData();// ����icbc_erp_kj_icbc
		PageData kir = new PageData();// ���icbc_erp_kj_icbc_result
		kir.put("qryid", yw_id);
		kir.put("mid_add", admin_id);
		kir.put("mid_edit", admin_id);
		kir.put("dt_add", dt_date);
		kir.put("dt_edit", creditutil.time());
		kir.put("status", status_id);
		kir.put("status_oldht", 0);
		kir.put("remark", "����ͨ���������");
		kir.put("result_1_code", 0);
		kir.put("result_1_msg", result_value);
		System.out.println("json���ݣ�" + jsonutil.toJSONString(pData));
		kir.put("result_1_value", "");
		kir.put("dt_sub", creditutil.time());
		kir.put("type_id", type_id);
		kir.put("icbc_id", icbc_id);
		kir.put("jsonAll", "");
		kir.put("dn", "icbc_erp_kj_icbc_result");
		pd.put("dn", "icbc_erp_kj_icbc");
		pd.put("mid_edit", admin_id);
		pd.put("dt_edit", creditutil.time());
		pd.put("status", status_id);
		pd.put("id", yw_id);
		erp_wdrwService.update(pd);
		erp_wdrwService.save(kir);
		// ����
		Map map = icbcmodel.zx_status();
		admin admin = adminService.adminbyid(icbc_adminid);
		if (admin != null && !admin.equals("")) {
			String REGISTRATION_ID = admin.getJgid();
			String mString = admin.getName() + "����!" + "�ͻ�����Ϊ" + icbc_name
					+ "������Ѹ���" + ";����:����ͨ���������;��ע:" + result_value + "ʱ��:"
					+ creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(icbc_adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * erp����ͨ�����
	 */
	@RequestMapping(value = "erp/erp_zxtrsh.do", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void erp_zxtrsh(String tr_status, String remark, Integer admin_id,
			Integer icbc_id, Integer type_id, Integer status_id, Integer yw_id,
			String icbc_name, Integer icbc_adminid, String dt_date) {
		icbc icbc = new icbc();
		PageData pData = new PageData();// json����
		PageData pd = new PageData();// ����icbc_erp_kj_icbc
		PageData kir = new PageData();// ���icbc_erp_kj_icbc_result
		icbc_result icbc_result = new icbc_result();
		int status = 0;
		if (tr_status.equals("1")) {
			status = 2;
			icbc.setDt_fin(creditutil.time());
			pd.put("dt_fin", creditutil.time());
		} else if (tr_status.equals("2")) {
			status = 3;
		} else if (tr_status.equals("3")) {
			status = 4;
		}

		kir.put("qryid", yw_id);
		kir.put("mid_add", admin_id);
		kir.put("mid_edit", admin_id);
		kir.put("dt_add", dt_date);
		kir.put("dt_edit", creditutil.time());
		kir.put("status", status_id);
		kir.put("status_oldht", status);
		kir.put("remark", "ͨ�ڽ��");
		kir.put("result_1_code", tr_status);
		kir.put("result_1_msg", remark);
		System.out.println("json���ݣ�" + jsonutil.toJSONString(pData));
		kir.put("result_1_value", "");
		kir.put("dt_sub", creditutil.time());
		kir.put("type_id", type_id);
		kir.put("icbc_id", icbc_id);
		kir.put("jsonAll", "");
		kir.put("dn", "icbc_erp_kj_icbc_result");

		pd.put("dn", "icbc_erp_kj_icbc");
		pd.put("mid_edit", admin_id);
		pd.put("dt_edit", creditutil.time());
		pd.put("status", status_id);
		pd.put("id", yw_id);

		icbc.setDt_edit(creditutil.time());
		icbc.setMid_edit(admin_id);
		icbc.setDt_zxresult(creditutil.time());
		icbc.setId(icbc_id);
		icbc.setTr_status(status);
		icbc.setBc_status(5);
		// ������˼�¼
		icbc_result.setDt_add(creditutil.time());
		icbc_result.setDt_edit(creditutil.time());
		icbc_result.setQryid(icbc_id);
		icbc_result.setStatus(5);
		icbc_result.setMid_add(admin_id);
		icbc_result.setMid_edit(admin_id);
		icbc_result.setRemark(remark);
		newicbcService.upicbc(icbc);
		erp_wdrwService.update(pd);
		erp_wdrwService.save(kir);
		icbc_result1Service.addicbc_result(icbc_result);
		if (tr_status.equals("1") || tr_status.equals("2")) {
			PageData p1 = new PageData();
			PageData p1_result = new PageData();
			p1.put("dn", "icbc_erp_kj_icbc");
			p1.put("mid_edit", admin_id);
			p1.put("dt_edit", creditutil.time());
			p1.put("status", 8);
			p1.put("id", yw_id);
			p1_result.put("qryid", yw_id);
			p1_result.put("mid_add", admin_id);
			p1_result.put("mid_edit", admin_id);
			p1_result.put("dt_add", creditutil.time());
			p1_result.put("dt_edit", creditutil.time());
			p1_result.put("status", 8);
			p1_result.put("status_oldht", status);
			p1_result.put("remark", "���");
			p1_result.put("result_1_code", 0);
			p1_result.put("result_1_msg", remark);
			p1_result.put("result_1_value", "");
			p1_result.put("dt_sub", creditutil.time());
			p1_result.put("type_id", type_id);
			p1_result.put("icbc_id", icbc_id);
			p1_result.put("jsonAll", "");
			p1_result.put("dn", "icbc_erp_kj_icbc_result");
			erp_wdrwService.update(p1);
			erp_wdrwService.save(p1_result);
		}
		Map map = icbcmodel.zx_status();
		admin admin = adminService.adminbyid(icbc_adminid);
		if (admin != null && !admin.equals("")) {
			String REGISTRATION_ID = admin.getJgid();
			String mString = admin.getName() + "����!" + "�ͻ�����Ϊ" + icbc_name
					+ "����������" + ";����ͨ��״̬:" + map.get(status) + ";��ע:" + remark
					+ "ʱ��:" + creditutil.time() + ";";
			if (REGISTRATION_ID != null && !REGISTRATION_ID.equals("")) {
				Jdpush.testSendPush(appKey, masterSecret, REGISTRATION_ID,
						mString);
			}
			admin_msg admin_msg = new admin_msg();
			admin_msg.setDt_add(creditutil.time());
			admin_msg.setDt_edit(creditutil.time());
			admin_msg.setMid_add(icbc_adminid);
			admin_msg.setMsg(mString);
			admin_msg.setReceiveid(admin.getId());
			admin_msg.setSendid(0);
			admin_msg.setStatus(0);
			admin_msgService.addadmin_msg(admin_msg);
		}
	}

	/*
	 * ��ѯҵ������
	 */
	@RequestMapping(value = "erp/ywlxname.do", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String ywlxname() {
		PageData pd1 = new PageData();
		pd1.put("dn", "commontype");
		List<PageData> pdList = erp_wdrwService.icbc_list(pd1);
		return jsonutil.toJSONArray(pdList).toString();
	}

	/*
	 * ��ѯҵ������״̬
	 */
	@RequestMapping(value = "erp/ywlxstatus.do", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String ywlxstatus(int status) {
		PageData pd1 = new PageData();
		pd1.put("status", status);
		pd1.put("dn", "commtasknametoname");
		PageData pdList = erp_wdrwService.icbc_form(pd1);
		System.out.println(jsonutil.toJSONObject(pdList).toString());
		return jsonutil.toJSONObject(pdList).toString();
	}

	/*
	 * 
	 * ���ϻ���
	 */
	@RequestMapping(value = "erp/clhs_list.do", produces = "text/html;charset=UTF-8")
	public String clhs_list(Integer pagesize, Integer pagenow, String dn,
			String qn, String cn, String type, String text,
			HttpServletRequest request) throws UnsupportedEncodingException {

		PageData pd1 = new PageData();
		List<PageData> newpdList = new ArrayList<>();
		pd1.put("dn", "kj_icbc");
		if (text != null && !text.equals("")) {
			text = new String(text.getBytes("ISO-8859-1"), "UTF-8");
			pd1.put("text", text);
		}
		List<PageData> pdList = erp_wdrwService.icbc_list(pd1);
		int ps = 0;
		int pn = 0;
		if (pagesize != null && !pagesize.equals("")) {
			ps = pagesize;
		} else {
			ps = 10;
		}
		if (pagenow != null && !pagenow.equals("")) {
			pn = pagenow;
		} else {
			pn = 1;
		}
		System.out.println(pdList.size());
		newpdList = limitutil.fy(pdList, ps, pn);
		int q = pdList.size() % ps;
		int totalpage = 0;// ��ҳ��
		if (q == 0) {
			totalpage = pdList.size() / ps;
		} else {
			totalpage = pdList.size() / ps + 1;
		}
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("totalsize", pdList.size());
		request.setAttribute("newpdList", newpdList);
		request.setAttribute("pagesize", ps);
		request.setAttribute("pagenow", pn);
		request.setAttribute("dn", dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		request.setAttribute("text", text);
		return "kjs_icbc/index";
	}

	/**
	 * �ҵ������б�
	 * 
	 * @param pagesize
	 * @param pagenow
	 * @param dn
	 * @param qn
	 * @param cn
	 * @param type
	 * @param fsid
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	/*
	 * @RequestMapping(value = "erp/wdrw_list.do", produces =
	 * "text/html;charset=UTF-8") public String wdrw_list( Integer pagesize,
	 * Integer pagenow, String dn, String qn, String cn, String type, String
	 * fsid, String ywlx_id, Integer yw_id, String c_name, HttpServletRequest
	 * request) throws UnsupportedEncodingException { List<PageData> newpdList =
	 * new ArrayList<>(); PageData pd1 = new PageData(); PageData pdsession=
	 * (PageData)request.getSession().getAttribute("pd");//��ȡsession��Ϣ
	 * System.out.println("erpҵ��ڵ�ID:"+yw_id); //���ȡ����˱�� PageData pdsh=new
	 * PageData(); pdsh.put("operate_id",0); pdsh.put("id",yw_id);
	 * pdsh.put("dn","icbc_erp_kj_icbc"); erp_wdrwService.update(pdsh); int ps =
	 * 0; int pn = 0; if (pagesize != null && !pagesize.equals("")) { ps =
	 * pagesize; } else { ps = 10; } if (pagenow != null && !pagenow.equals(""))
	 * { pn = pagenow; } else { pn = 1; } List<PageData> pdList=new
	 * ArrayList<>(); List<PageData> pdList1=new ArrayList<>(); List<PageData>
	 * pdList2=new ArrayList<>(); List<Integer> status_ids=new ArrayList<>();
	 * pd1.put("cn", cn); // ����ҵ�����Ͳ�ѯ if (ywlx_id != null &&
	 * !ywlx_id.equals("")) { pd1.put("ywlx_id", ywlx_id);
	 * request.setAttribute("type_id",ywlx_id); } // ���ݿͻ����ֲ�ѯ if (c_name != null
	 * && !c_name.equals("")) { pd1.put("c_name",new
	 * String(c_name.getBytes("ISO-8859-1"),"UTF-8"));
	 * System.out.println("�ͻ�����:"+new
	 * String(c_name.getBytes("ISO-8859-1"),"UTF-8"));
	 * request.setAttribute("c_name",new
	 * String(c_name.getBytes("ISO-8859-1"),"UTF-8")); } int icbc_erp_tag=0;
	 * if(pdsession.get("erp_tag")!=null&&!pdsession.get("erp_tag").equals("")){
	 * icbc_erp_tag=Integer.parseInt(pdsession.get("erp_tag").toString()); }
	 * if(cn.equals("w2")){ pd1.put("dn", "wdcy"); pd1.put("admin_id",
	 * pdsession.get("id")); System.out.println("�û�ID��"+pdsession.get("id"));
	 * pdList = erp_wdrwService.icbc_list(pd1); }else if(cn.equals("w0")){
	 * List<PageData> pageDatas=new ArrayList<>(); List<Integer> pIntegers=new
	 * ArrayList<>();
	 * if(pdsession.get("qx_type")!=null&&!pdsession.get("qx_type").equals("")){
	 * int qx_type=Integer.parseInt(pdsession.get("qx_type").toString());
	 * if(qx_type==1){ PageData dls_id=new PageData(); dls_id.put("dn",
	 * "dls_cp1"); dls_id.put("id",pdsession.get("gemsid"));
	 * pageDatas=erp_wdrwService.icbc_list(dls_id); for(PageData pdData :
	 * pageDatas){ if(pdData.get("id")!=null&&!pdData.get("id").equals("")){
	 * pIntegers.add(Integer.parseInt(pdData.get("id").toString())); } }
	 * pd1.put("status_id", pIntegers); } if(icbc_erp_tag!=1){ if(qx_type==2){
	 * PageData dls_id=new PageData(); dls_id.put("dn", "dls_cp2");
	 * dls_id.put("fsid",pdsession.get("fs_id"));
	 * pageDatas=erp_wdrwService.icbc_list(dls_id); for(PageData pdData :
	 * pageDatas){ if(pdData.get("id")!=null&&!pdData.get("id").equals("")){
	 * pIntegers.add(Integer.parseInt(pdData.get("id").toString())); } }
	 * pd1.put("status_id", pIntegers); } } } pd1.put("dn", dn); pdList =
	 * erp_wdrwService.icbc_list(pd1); }else if(cn.equals("w3")){
	 * pd1.put("admin_id", pdsession.get("id")); dn="wdcy";
	 * if(pdsession.get("purview_map"
	 * )!=null&&!pdsession.get("purview_map").equals("")){ String
	 * qxs=pdsession.get("purview_map").toString(); String[] sl=qxs.split(",");
	 * for(int i=0;i<sl.length;i++){ //System.out.println("���飺"+sl[i]);
	 * if(sl[i].indexOf("_")>0){ int
	 * status=Integer.parseInt(sl[i].substring(sl[i].lastIndexOf("_")+1,
	 * sl[i].length())); status_ids.add(status);
	 * 
	 * } } pd1.put("status_id",status_ids); }
	 * System.out.println("Ȩ�����飺"+pd1.get("status_id"));
	 * if(status_ids.size()==0){ pd1.put("status_id",null); } pdList =
	 * erp_wdrwService.icbc_list(pd1); if(status_ids.size()==0){
	 * System.out.println("��ֵ��"+pdList.size()); pdList= new ArrayList<>(); }
	 * System.out.println("����������"+pdList.size()); }else{ List<PageData>
	 * pageDatas=new ArrayList<>(); List<Integer> pIntegers=new ArrayList<>();
	 * if(pdsession.get("qx_type")!=null&&!pdsession.get("qx_type").equals("")){
	 * int qx_type=Integer.parseInt(pdsession.get("qx_type").toString());
	 * if(qx_type==1){ PageData dls_id=new PageData(); dls_id.put("dn",
	 * "dls_cp1"); dls_id.put("id",pdsession.get("gemsid"));
	 * pageDatas=erp_wdrwService.icbc_list(dls_id); for(PageData pdData :
	 * pageDatas){ if(pdData.get("id")!=null&&!pdData.get("id").equals("")){
	 * pIntegers.add(Integer.parseInt(pdData.get("id").toString())); } }
	 * pd1.put("status_id", pIntegers); } if(icbc_erp_tag!=1){ if(qx_type==2){
	 * PageData dls_id=new PageData(); dls_id.put("dn", "dls_cp2");
	 * dls_id.put("fsid",pdsession.get("fs_id"));
	 * pageDatas=erp_wdrwService.icbc_list(dls_id); for(PageData pdData :
	 * pageDatas){ if(pdData.get("id")!=null&&!pdData.get("id").equals("")){
	 * pIntegers.add(Integer.parseInt(pdData.get("id").toString())); } }
	 * pd1.put("status_id", pIntegers); } } } pd1.put("dn", dn); pdList2 =
	 * erp_wdrwService.icbc_list(pd1);
	 * System.out.println("δ����Ȩ��ǰ������"+pdList2.size()); //����� int dkbj=0; //��������
	 * int dz_type=0; String knname=""; for(int i=0;i<pdList2.size();i++){ int
	 * erp_status=0; PageData pData=pdList2.get(i);
	 * if(pData.get("kk_dk_price")!=null&&!pData.get("kk_dk_price").equals("")){
	 * dkbj=Integer.parseInt(pData.get("kk_dk_price").toString()); }
	 * if(pData.get("kk_dz_type")!=null&&!pData.get("kk_dz_type").equals("")){
	 * dz_type=Integer.parseInt(pData.get("kk_dz_type").toString()); }
	 * if(pData.get("status")!=null&&!pData.get("status").equals("")){ switch
	 * (pData.get("status").toString()) { case "1": pData.put("erp_status", 2);
	 * break; case "2": pData.put("erp_status", 3); break; case "3": if
	 * (pData.get("kir_code").toString().equals("1") ||
	 * pData.get("kir_code").toString().equals("2")) { pData.put("erp_status",
	 * 4); } if (pData.get("kir_code").toString().equals("3")) {
	 * pData.put("erp_status", 2);
	 * 
	 * } break; case "4": pData.put("erp_status", 4); break; case "5":
	 * 
	 * pData.put("erp_status", 6); break; case "6":
	 * 
	 * pData.put("erp_status", 7); break; case "7": if
	 * (pData.get("kir_code").toString().equals("1") ||
	 * pData.get("kir_code").toString().equals("2")) {
	 * 
	 * pData.put("erp_status", 8); } if
	 * (pData.get("kir_code").toString().equals("3")) {
	 * 
	 * pData.put("erp_status", 5); } break; case "8": pData.put("erp_status",
	 * 8); break; case "9": pData.put("erp_status",10); break; case "10":
	 * pData.put("erp_status",11); break; case "11": if
	 * (pData.get("kir_code").toString().equals("1")
	 * ||pData.get("kir_code").toString().equals("2")) {
	 * pData.put("erp_status",12); } if
	 * (pData.get("kir_code").toString().equals("3")) {
	 * pData.put("erp_status",10); }
	 * 
	 * break; case "12": pData.put("erp_status",12); break; case "13":
	 * pData.put("erp_status",14); break; case "14": pData.put("erp_status",15);
	 * break; case "15": if (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",16); } else if
	 * (pData.get("kir_code").toString().equals("3")) {
	 * pData.put("erp_status",14); }
	 * 
	 * break; case "16": pData.put("erp_status",16); break; case "17":
	 * pData.put("erp_status",18); break; case "18": pData.put("erp_status",19);
	 * break; case "19": if (pData.get("kir_code").toString().equals("1") ||
	 * pData.get("kir_code").toString().equals("2")) {
	 * 
	 * pData.put("erp_status",20); } else if
	 * (pData.get("kir_code").toString().equals("3")) {
	 * 
	 * pData.put("erp_status",18); } break; case "20": if
	 * (pData.get("kir_code").toString().equals("1") ||
	 * pData.get("kir_code").toString().equals("2")) {
	 * pData.put("erp_status",21); } else if
	 * (pData.get("kir_code").toString().equals("3")) {
	 * pData.put("erp_status",18); }
	 * 
	 * break; case "21": pData.put("erp_status",21); break; case "22":
	 * pData.put("erp_status",23); break; case "23": pData.put("erp_status",24);
	 * break; case "24": if (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",25); } else if
	 * (pData.get("kir_code").toString().equals("3")) {
	 * pData.put("erp_status",23); } break; case "25":
	 * pData.put("erp_status",25); break; case "26": pData.put("erp_status",98);
	 * break; case "98": pData.put("erp_status",27); break; case "27":
	 * 
	 * //result_1_code �ֶ� 1-ͨ�� 2-��ͨ�� 3-���� if
	 * (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",29); }else
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",30); }else
	 * if(pData.get("kir_code").toString().equals("3")){
	 * pData.put("erp_status",98); } break; case "28":
	 * pData.put("erp_status",27); break; case "29": if
	 * (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",30); }else
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",30); }else
	 * if(pData.get("kir_code").toString().equals("3")){
	 * pData.put("erp_status",98); } break; case "30":
	 * pData.put("erp_status",30); break; case "31": pData.put("erp_status",32);
	 * break; case "32": pData.put("erp_status",33); break; case "33":
	 * if(dkbj<150000){ if (pData.get("kir_code").toString().equals("1") ) {
	 * pData.put("erp_status",40); } else if
	 * (pData.get("kir_code").toString().equals("3")||
	 * pData.get("kir_code").toString().equals("2")) {
	 * pData.put("erp_status",31); } }else{ if
	 * (pData.get("kir_code").toString().equals("1")
	 * ||pData.get("kir_code").toString().equals("2")) {
	 * pData.put("erp_status",34); } else if
	 * (pData.get("kir_code").toString().equals("3")) {
	 * pData.put("erp_status",31); } } break; case "34":
	 * pData.put("erp_status",35); break; case "35": if(dkbj<300000){ if
	 * (pData.get("kir_code").toString().equals("1") ) {
	 * pData.put("erp_status",35); } else if
	 * (pData.get("kir_code").toString().equals("3")||
	 * pData.get("kir_code").toString().equals("2")) {
	 * pData.put("erp_status",35); } }else{ if
	 * (pData.get("kir_code").toString().equals("1") ||
	 * pData.get("kir_code").toString().equals("2")) {
	 * pData.put("erp_status",35);
	 * 
	 * } else if (pData.get("kir_code").toString().equals("3")) {
	 * pData.put("erp_status",35); } } break; case "36":
	 * pData.put("erp_status",37); break; case "37": if(dkbj<600000){ if
	 * (pData.get("kir_code").toString().equals("1") ) {
	 * pData.put("erp_status",40); } else if
	 * (pData.get("kir_code").toString().equals
	 * ("3")||pData.get("kir_code").toString().equals("2")) {
	 * pData.put("erp_status",31); } }else{ if
	 * (pData.get("kir_code").toString().equals("1") ||
	 * pData.get("kir_code").toString().equals("2")) {
	 * pData.put("erp_status",38);
	 * 
	 * } else if (pData.get("kir_code").toString().equals("3")) {
	 * pData.put("erp_status",31); } } break; case "38":
	 * pData.put("erp_status",39); break; case "39": if
	 * (pData.get("kir_code").toString().equals("1")) {
	 * 
	 * pData.put("erp_status",40); } else if
	 * (pData.get("kir_code").toString().equals("3") ||
	 * pData.get("kir_code").toString().equals("2")) {
	 * 
	 * pData.put("erp_status",31); } break; case "40":
	 * pData.put("erp_status",40); break; case "41": pData.put("erp_status",42);
	 * break; case "42": pData.put("erp_status",43); break; case "43":
	 * pData.put("erp_status",44); break; case "44": pData.put("erp_status",45);
	 * break; case "45": pData.put("erp_status",46); break; case "46":
	 * pData.put("erp_status",46); break; case "47": if(dz_type==2){
	 * pData.put("erp_status",48); }else{
	 * 
	 * pData.put("erp_status",49); } break; case "48":
	 * pData.put("erp_status",52); break; case "49": pData.put("erp_status",99);
	 * break; case "99": if(pData.get("kir_code").toString().equals("1")){
	 * 
	 * pData.put("erp_status",50); }else{
	 * 
	 * pData.put("erp_status",49); } break; case "50":
	 * 
	 * pData.put("erp_status",51);
	 * 
	 * break; case "51": if (pData.get("kir_code").toString().equals("1")) {
	 * 
	 * pData.put("erp_status",55); } else if
	 * (pData.get("kir_code").toString().equals("2") ){ PageData pData3=new
	 * PageData(); pData3.put("dn","yhds_tocode2");
	 * pData3.put("type_id",pData.get("type_id")); pData3.put("status",51);
	 * pData3.put("icbc_id",pData.get("icbc_id")); PageData
	 * pData4=erp_wdrwService.icbc_form(pData3);
	 * System.out.println("id�Աȣ�"+pData4.get("id")+"--"+pData.get("id"));
	 * if(pData4.get("id").equals(pData.get("id"))){ pData.put("erp_status",54);
	 * }else{ pData.put("erp_status",50); } }else
	 * if(pData.get("kir_code").toString().equals("3")){
	 * pData.put("erp_status",49); } break; case "52": if
	 * (pData.get("kir_code").toString().equals("2")) {
	 * pData.put("erp_status",49); } else if
	 * (pData.get("kir_code").toString().equals
	 * ("1")||pData.get("kir_code").toString().equals("3") ){
	 * pData.put("erp_status",48); }
	 * 
	 * break; case "53": erp_status =54; break; case "54":
	 * pData.put("erp_status",50); break; case "55": pData.put("erp_status",55);
	 * break; case "56": pData.put("erp_status",57); break; case "57":
	 * pData.put("erp_status",58); break; case "58": JSONObject json =
	 * (JSONObject) JSON.parse(pData.get("kir_value").toString()); if
	 * (pData.get("kir_code").toString().equals("1")&&
	 * json.get("58_msg").toString().equals("���յ�")) {
	 * pData.put("erp_status",59); }else{ pData.put("erp_status",57); } break;
	 * case "59": JSONObject json2 = (JSONObject)
	 * JSON.parse(pData.get("kir_value").toString()); if
	 * (pData.get("kir_code").toString().equals("1") &&
	 * json2.get("58_msg").toString().equals("���յ�")) {
	 * pData.put("erp_status",60); }else{ pData.put("erp_status",58); } break;
	 * case "60": //1��ͨ���������зſ������ſ����е����ʧ�ܡ�������һ���� //2����ͨ��������
	 * //3�����������������������ȷ�ϡ�-�����ϲ���������Ҫ�������䡱-��������ϡ��ύ-���������ȷ�ϡ�-��������-���ص�һ�����Ͳ���
	 * //4���ȵ�Ѻ��Ŵ���ѡ�����������ʱ�����¼��ߣ��ȡ���Ѻ�鵵��ģ����ɿ��������зſ����� if
	 * (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",61); }else
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",65); }else
	 * if(pData.get("kir_code").toString().equals("3")){
	 * pData.put("erp_status",63); }else
	 * if(pData.get("kir_code").toString().equals("4")){
	 * //�ȵ�Ѻ��Ŵ���ѡ�����������ʱ�����¼��ߣ��ȡ���Ѻ�鵵��ģ����ɿ��������зſ����� //�õ� ��Ѻ�鵵���-С״̬ PageData
	 * get_status=new PageData();
	 * get_status.put("dn","selectListStatus_icbc_erp_kj_icbc_result");
	 * get_status.put("icbc_id",pData.get("icbc_id"));
	 * get_status.put("type_id",13); //��Ѻ�鵵-��� get_status.put("status",82); //
	 * ��Ѻ�鵵���-С״̬ List<PageData> pErpIcbc_82 = new ArrayList<>(); pErpIcbc_82 =
	 * erp_fiveModelService.findtolist(get_status); if(pErpIcbc_82.size()>0){ //
	 * ����Ѻ�鵵��ģ����� , ���������зſ����� pData.put("erp_status",61); }else{ // ����Ѻ�鵵��ģ��
	 * δ��� ,��ʱ�����¼��� pData.put("erp_status",60); } } break; case "61": if
	 * (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",62); }else
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",60); } break; case "62": if
	 * (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",65); }else{ pData.put("erp_status",61); } break;
	 * case "63": if (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",57); }else
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",63); } break; case "64":
	 * pData.put("erp_status",64); break; case "65": pData.put("erp_status",65);
	 * break; case "66": pData.put("erp_status",67); break; case "67": if
	 * (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",68); }else
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",69); } break; case "68":
	 * pData.put("erp_status",70); break; case "69": pData.put("erp_status",67);
	 * break; case "70": if (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",71); }else
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",67); } break; case "71":
	 * pData.put("erp_status",71); break; case "72": pData.put("erp_status",73);
	 * break; case "73": pData.put("erp_status",74); break; case "74":
	 * pData.put("erp_status",75); break; case "75": JSONObject
	 * jsonObject=JSONObject.parseObject(pData.get("kir_value").toString());
	 * if(jsonObject
	 * .get("75_clfh").equals("��ͨ��")||jsonObject.get("75_sjqr").equals("δ�յ�")){
	 * pData.put("erp_status",74); }else{ pData.put("erp_status",76); } break;
	 * case "76": JSONObject
	 * jsonObject1=JSONObject.parseObject(pData.get("kir_value").toString());
	 * if(jsonObject1.get("76_cyqk").equals("��ͨ��")){ pData.put("erp_status",75);
	 * }else{ pData.put("erp_status",77); } break; case "77":
	 * pData.put("erp_status",78); break; case "78": JSONObject
	 * jsonObject2=JSONObject.parseObject(pData.get("kir_value").toString());
	 * if(
	 * jsonObject2.get("78_clfh").equals("��ͨ��")||jsonObject2.get("78_sjqr").equals
	 * ("δ�յ�")){ pData.put("erp_status",77); }else{ pData.put("erp_status",79);
	 * } break; case "79": pData.put("erp_status",80); break; case "80":
	 * JSONObject
	 * jsonObject3=JSONObject.parseObject(pData.get("kir_value").toString());
	 * if(
	 * jsonObject3.get("80_clfh").equals("��ͨ��")||jsonObject3.get("80_sjqr").equals
	 * ("δ�յ�")){ pData.put("erp_status",79); }else{ pData.put("erp_status",81);
	 * }
	 * 
	 * break; case "81": JSONObject
	 * jsonObject4=JSONObject.parseObject(pData.get("kir_value").toString());
	 * if(jsonObject4.get("81_cyqk").equals("��ͨ��")){ pData.put("erp_status",80);
	 * }else{ pData.put("erp_status",82); } break; case "82":
	 * pData.put("erp_status",82); break; case "83": pData.put("erp_status",96);
	 * break; case "96": pData.put("erp_status",84); break; case "84": if
	 * (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",85); }else
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",86); } break; case "85":
	 * pData.put("erp_status",86); break; case "86": pData.put("erp_status",86);
	 * break; case "87": pData.put("erp_status",97); break; case "97":
	 * pData.put("erp_status",88); break; case "88": if
	 * (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",90); }else
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",89); } break; case "89":
	 * pData.put("erp_status",88); break; case "90": //�õ��Ƿ� ���� PageData
	 * pdd_status=new PageData();
	 * pdd_status.put("dn","selectListStatus_icbc_erp_kj_icbc_result");
	 * pdd_status.put("icbc_id",pData.get("icbc_id"));
	 * pdd_status.put("type_id",10); //�ʽ����-��� pdd_status.put("status",50); //
	 * ����-С״̬ List<PageData> pErpIcbc_50 = new ArrayList<>(); pErpIcbc_50 =
	 * erp_fiveModelService.findtolist(pdd_status); //�õ� �����ռ�ȷ�� �Ľ�� PageData
	 * pdd_s=new PageData();
	 * pdd_s.put("dn","selectListStatus_icbc_erp_kj_icbc_result");
	 * pdd_s.put("icbc_id",pData.get("icbc_id")); pdd_s.put("type_id",11);
	 * //��������-��� pdd_s.put("status",58); // �����ռ�ȷ��-С״̬ List<PageData>
	 * pErpIcbc_58 = new ArrayList<>(); pErpIcbc_58 =
	 * erp_fiveModelService.findtolist(pdd_s); //�õ����һ������ PageData get_max58 =
	 * pErpIcbc_58.get(pErpIcbc_58.size() - 1); JSONObject json3 = (JSONObject)
	 * JSON.parse(pData.get("kir_value").toString()); if
	 * (pData.get("kir_code").toString().equals("1")) { //ͨ��
	 * if(pErpIcbc_50.size()>0){ //��Ǯ�� // ͨ��-���� ����91 pData.put("erp_status",91);
	 * }else{ if(json3 != null){
	 * if(json3.get("58_msg").toString().equals("���յ�")){ // ͨ��-δ����-���ռ� ����93
	 * pData.put("erp_status",93); }else
	 * if(json3.get("58_msg").toString().equals("δ�յ�")){ // ͨ��-δ����-δ�ռ� ����95���
	 * pData.put("erp_status",95); } } } }else
	 * if(pData.get("kir_code").toString().equals("2")){ // ��ͨ��
	 * pData.put("erp_status",89); } break; case "91":
	 * pData.put("erp_status",92); break; case "92": // 1���ˡ�ȷ�������ѵ������ռ��� ����93 //
	 * 2�ѵ���δ�ռ� ����95 // 3����/δ���� ����91 if
	 * (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",93); }else
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",95); }else
	 * if(pData.get("kir_code").toString().equals("3")){
	 * pData.put("erp_status",91); } break; case "93":
	 * pData.put("erp_status",94); break; case "94": if
	 * (pData.get("kir_code").toString().equals("1")) {
	 * pData.put("erp_status",95); }else
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",93); } break; case "95":
	 * pData.put("erp_status",95); break; case "100":
	 * pData.put("erp_status",101); break; case "101":
	 * if(pData.get("kir_code").toString().equals("1")){
	 * pData.put("erp_status",102); }
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",104); } break; case "102":
	 * pData.put("erp_status",103); break; case "103":
	 * if(pData.get("kir_code").toString().equals("1")){
	 * pData.put("erp_status",104); }
	 * if(pData.get("kir_code").toString().equals("2")){
	 * pData.put("erp_status",102); } break; default: break; } PageData
	 * pData2=new PageData(); pData2.put("dn", "commtasknametoname");
	 * pData2.put("status",pData.get("erp_status")); PageData
	 * pd_names=erp_wdrwService.icbc_form(pData2);
	 * if(pd_names!=null&&!pd_names.equals("")){ pData.put("knname",
	 * pd_names.get("name")); } pdList1.add(pData); } }
	 * System.out.println("�ж�״̬��������"+pdList1.size());
	 * //System.out.println("�ж�״̬��json��"+jsonutil.toJSONArray(pdList1)); //��ȡȨ��
	 * ����Ķ�Ӧ���״̬
	 * if(pdsession.get("purview_map")!=null&&!pdsession.get("purview_map"
	 * ).equals("")){ String qxs=pdsession.get("purview_map").toString();
	 * String[] sl=qxs.split(","); for(int i=0;i<sl.length;i++){
	 * //System.out.println("���飺"+sl[i]); if(sl[i].indexOf("_")>0){
	 * //System.out.println("Ȩ�޶�Ӧ���֣�"+sl[i].substring(sl[i].lastIndexOf("_")+1,
	 * sl[i].length())); String status=sl[i].substring(sl[i].lastIndexOf("_")+1,
	 * sl[i].length()); for(int j=0;j<pdList1.size();j++){ PageData
	 * pageData=pdList1.get(j);
	 * //System.out.println("Ȩ�޶�Ӧ״̬��"+pageData.get("erp_status")+"--"+status);
	 * if
	 * (pageData.get("erp_status")!=null&&!pageData.get("erp_status").equals(""
	 * )){ if(status.equals(pageData.get("erp_status").toString())){
	 * //System.out.println("��ȵ�״̬Ȩ�ޣ�"+pageData.get("erp_status")+"--"+status);
	 * pdList.add(pageData); } } } } } }
	 * System.out.println("����Ȩ�޺�������"+pdList.size()); } //����
	 * Collections.sort(pdList,new Comparator<PageData>() {
	 * 
	 * int compare(PageData o1, PageData o2) ����һ���������͵����ͣ� ���ظ�����ʾ��o1 С��o2�� ����0
	 * ��ʾ��o1��o2��ȣ� ����������ʾ��o1����o2
	 * 
	 * @Override public int compare(PageData o1, PageData o2) { SimpleDateFormat
	 * df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date sd1 = null; Date
	 * sd2 = null; try { sd1=df.parse(o1.get("dt_edit").toString());
	 * sd2=df.parse(o2.get("dt_edit").toString()); } catch (ParseException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } Integer
	 * i=sd1.compareTo(sd2); //����ʱ����н��������� if(i<0){ return 1; } if(i==0){ return
	 * 0; } return -1; } }); newpdList = limitutil.fy(pdList, ps, pn); int q =
	 * pdList.size() % ps; int totalpage = 0;// ��ҳ�� if (q == 0) { totalpage =
	 * pdList.size() / ps; } else { totalpage = pdList.size() / ps + 1; }
	 * request.setAttribute("totalpage", totalpage);
	 * request.setAttribute("totalsize", pdList.size());
	 * request.setAttribute("newpdList", newpdList);
	 * request.setAttribute("pagesize", ps); request.setAttribute("pagenow",
	 * pn); request.setAttribute("dn",dn); request.setAttribute("cn", cn);
	 * request.setAttribute("qn", qn); request.setAttribute("type", type);
	 * request.setAttribute("yw_id", yw_id); return "kjs_icbc/index"; }
	 */
	/*
	 * public static void main(String[] args) {
	 * 
	 * SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date
	 * sd1 = null; Date sd2 = null; try { sd1=df.parse("2018-09-03 10:51:21");
	 * sd2=df.parse("2018-09-03 10:51:22"); } catch (ParseException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } Integer
	 * i=sd1.compareTo(sd2); System.out.println("*:"+i); }
	 */
	/**
	 * �ҵ�����+���Ȱ��
	 * 
	 * @param dn
	 * @param qn
	 * @param cn
	 * @param type
	 * @param icbc_id
	 * @param type_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "erp/wdrw_from.do", produces = "text/html;charset=UTF-8")
	public String wdrw_from(String dn, String qn, String cn, String type,
			String icbc_id, String type_id, String cn1, // ����״̬// 1��2ɾ3��4��
			Integer yw_id, HttpServletRequest request) {
		PageData pdsession = (PageData) request.getSession().getAttribute("pd");// ��ȡsession��Ϣ
		/*
		 * if(cn1.equals("3")){ //�����˱�� PageData pdsh=new PageData();
		 * pdsh.put("operate_id"
		 * ,Integer.parseInt(pdsession.get("id").toString()));
		 * pdsh.put("id",yw_id); pdsh.put("dn","icbc_erp_kj_icbc");
		 * erp_wdrwService.update(pdsh); request.setAttribute("yw_id", yw_id); }
		 */
		request.setAttribute("yw_id", yw_id);
		PageData pData = new PageData();
		pData.put("dn", "icbc_erp_kj_icbc");
		pData.put("icbc_id", icbc_id);
		// pData.put("qryid", yw_id);
		System.out.println("������id:" + icbc_id);

		List<fs> fs = fService.findbypy();
		request.setAttribute("fs", fs);// �̻�������
		if (icbc_id != null && !icbc_id.equals("")) {
			PageData pageData = erp_wdrwService.icbc_form(pData);
			if (pageData != null && !pageData.equals("")) {
				// �����˼���
				List<PageData> ghrlist = new ArrayList<>();
				if (pageData.get("c_name_mt") != null
						&& !pageData.get("c_name_mt").equals("")) {
					PageData po = new PageData();
					po.put("name", pageData.get("c_name_mt"));
					po.put("cardno", pageData.get("c_cardno_mt"));
					po.put("tel", pageData.get("c_tel_mt"));
					po.put("ysr", pageData.get("po_ysr"));
					po.put("xl", pageData.get("po_xl"));
					po.put("xzdz", pageData.get("po_xzdz"));
					po.put("yzbm", pageData.get("po_yzbm"));
					po.put("gzdw", pageData.get("po_gzdw"));
					po.put("dwdz", pageData.get("po_dwdz"));
					po.put("zw", pageData.get("po_zw"));
					po.put("wsdz", pageData.get("po_wsdz"));
					po.put("yzdrgx", "��ż");
					ghrlist.add(po);
				}
				if (pageData.get("c_name_gj1") != null
						&& !pageData.get("c_name_gj1").equals("")) {
					PageData gjr1 = new PageData();
					gjr1.put("name", pageData.get("c_name_gj1"));
					gjr1.put("cardno", pageData.get("c_cardno_gj1"));
					gjr1.put("tel", pageData.get("c_tel_gj1"));
					gjr1.put("ysr", pageData.get(""));
					gjr1.put("xl", pageData.get(""));
					gjr1.put("xzdz", pageData.get("gjr1_xzdz"));
					gjr1.put("yzbm", pageData.get("gjr1_yzbm"));
					gjr1.put("gzdw", pageData.get("gjr1_gzdw"));
					gjr1.put("dwdz", pageData.get("gjr1_dwdz"));
					gjr1.put("zw", pageData.get(""));
					gjr1.put("wsdz", pageData.get("gjr1_wsdz"));
					gjr1.put("yzdrgx", pageData.get("gjr1_yzdrgx"));
					ghrlist.add(gjr1);
				}
				if (pageData.get("c_name_gj2") != null
						&& !pageData.get("c_name_gj2").equals("")) {
					PageData gjr2 = new PageData();
					gjr2.put("name", pageData.get("c_name_gj2"));
					gjr2.put("cardno", pageData.get("c_cardno_gj2"));
					gjr2.put("tel", pageData.get("c_tel_gj2"));
					gjr2.put("ysr", pageData.get(""));
					gjr2.put("xl", pageData.get(""));
					gjr2.put("xzdz", pageData.get("gjr1_xzdz"));
					gjr2.put("yzbm", pageData.get("gjr1_yzbm"));
					gjr2.put("gzdw", pageData.get("gjr1_gzdw"));
					gjr2.put("dwdz", pageData.get("gjr1_dwdz"));
					gjr2.put("zw", pageData.get(""));
					gjr2.put("wsdz", pageData.get("gjr1_wsdz"));
					gjr2.put("yzdrgx", pageData.get("gjr1_yzdrgx"));
					ghrlist.add(gjr2);
				}
				request.setAttribute("ghrlist", ghrlist);
				request.setAttribute("pd", pageData);
				PageData pd2 = new PageData();
				pd2.put("dn", "icbc_erp_kj_icbc");
				pd2.put("icbc_id", icbc_id);

				// ���� ��Ԫ �� Ԫ start
				/*
				 * if(pageData.get("icbc_pricecs")!=null){ String
				 * get_icbc_pricecs = pageData.get("icbc_pricecs").toString();
				 * double d_icbc_pricecs = new Double(get_icbc_pricecs); double
				 * icbc_pricecs = d_icbc_pricecs*10000;
				 * pageData.put("icbc_pricecs",icbc_pricecs); }
				 */
				// ���� ��Ԫ �� Ԫ end

				// ҵ�����Ͱ��
				if (type_id != null && !type_id.equals("")) {
					pd2.put("ywlx_id", type_id);
				} else {
					type_id = "1";
					pd2.put("ywlx_id", type_id);
				}
				// ��һ������״̬
				int erp_status = 0;
				// erp �������ݼ���
				List<PageData> erp15 = new ArrayList<>();
				// 15���鴦��
				if (type_id.equals("1") || type_id.equals("2")) {
					// 1.���Ű�鴦�����
					PageData pd001 = new PageData();
					pd001.put("dn", "001");
					pd001.put("icbc_id", icbc_id);
					pd001.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd001);
					// ��ȡ������һ������
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						// ��ȡ���ű�id
						request.setAttribute("icbc_id", pData2.get("icbc_id"));

						System.out.println("���һ�β���״̬:" + pData2.get("status")
								+ "==" + pData2.get("status").equals("1")
								+ "||" + pData2.get("status").equals("2")
								+ "||"
								+ pData2.get("status").toString().equals("2"));
						if (pData2.get("status").toString().equals("1")) {
							erp_status = 2;
						}
						if (pData2.get("status").toString().equals("2")) {
							erp_status = 3;
						}
						if (pData2.get("status").toString().equals("3")) {
							if (pData2.get("result_1_code").toString()
									.equals("1")
									|| pData2.get("result_1_code").toString()
											.equals("2")) {
								erp_status = 4;
							}
							if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 2;
							}
						}
						if (pData2.get("status").toString().equals("4")) {
							erp_status = 4;
						}
						if (pData2.get("status").toString().equals("5")) {
							erp_status = 7;
						}
						// if (pData2.get("status").toString().equals("6")) {
						// erp_status = 7;
						// }
						if (pData2.get("status").toString().equals("7")) {
							if (pData2.get("result_1_code").toString()
									.equals("1")
									|| pData2.get("result_1_code").toString()
											.equals("2")) {
								erp_status = 8;
							}
							if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 5;
							}

						}
						if (pData2.get("status").toString().equals("8")) {
							erp_status = 8;
						}
					}
				}
				// 3.�����������
				if (type_id.equals("3")) {
					PageData pd001 = new PageData();
					pd001.put("dn", "003");
					pd001.put("icbc_id", icbc_id);
					pd001.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd001);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						// ��ȡ������id
						request.setAttribute("cars_id", pData2.get("cars_id"));
						if (pData2.get("status").toString().equals("9")) {
							erp_status = 10;
						}
						if (pData2.get("status").toString().equals("10")) {
							erp_status = 11;
						}
						if (pData2.get("status").toString().equals("11")) {
							if (pData2.get("result_1_code").toString()
									.equals("1")
									|| pData2.get("result_1_code").toString()
											.equals("2")) {
								erp_status = 12;
							}
							if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 10;
							}
						}
						if (pData2.get("status").toString().equals("12")) {
							erp_status = 12;
						}
					}
				}

				// 4.���е���
				if (type_id.equals("4")) {
					PageData pd004 = new PageData();
					pd004.put("dn", "004");
					pd004.put("icbc_id", icbc_id);
					pd004.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd004);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "13":
							erp_status = 14;
							break;
						case "14":
							erp_status = 15;
							break;
						case "15":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 16;
							} else if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 14;
							}
							break;
						case "16":
							erp_status = 16;
							break;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}
				}
				// ����������
				if (type_id.equals("5")) {
					PageData pd005 = new PageData();
					pd005.put("dn", "005");
					pd005.put("icbc_id", icbc_id);
					pd005.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd005);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						// ��ȡ������id
						request.setAttribute("kk_id", pData2.get("kk_id"));
						switch (pData2.get("status").toString()) {
						case "17":
							erp_status = 18;
							break;
						case "18":
							erp_status = 19;
							break;
						case "19":
							if (pData2.get("result_1_code").toString()
									.equals("1")
									|| pData2.get("result_1_code").toString()
											.equals("2")) {
								erp_status = 20;
							} else if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 18;
							}
							break;
						case "20":
							if (pData2.get("result_1_code").toString()
									.equals("1")
									|| pData2.get("result_1_code").toString()
											.equals("2")) {
								erp_status = 21;
							} else if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 18;
							}
							break;
						case "21":
							erp_status = 21;
							PageData result_date = new PageData();
							result_date.put("dn", "yhds_tocode");
							result_date.put("type_id", type_id);
							result_date.put("status", erp_status);
							result_date.put("icbc_id", icbc_id);
							PageData rData = erp_wdrwService
									.icbc_form(result_date);
							if (rData == null || rData.equals("")) {
								PageData p1 = new PageData();
								PageData p1_result = new PageData();
								p1.put("dn", "icbc_erp_kj_icbc");
								p1.put("mid_edit", pData2.get("mid_edit"));
								p1.put("dt_edit", creditutil.time());
								p1.put("status", erp_status);
								p1.put("id", pData2.get("qryid"));
								p1_result.put("qryid", pData2.get("qryid"));
								p1_result
										.put("mid_add", pData2.get("mid_edit"));
								p1_result.put("mid_edit",
										pData2.get("mid_edit"));
								p1_result.put("dt_add", creditutil.time());
								p1_result.put("dt_edit", creditutil.time());
								p1_result.put("status", 21);
								p1_result.put("status_oldht", 0);
								p1_result.put("remark", "���");
								p1_result.put("result_1_code", 0);
								p1_result.put("result_1_msg", "");
								p1_result.put("result_1_value", "");
								p1_result.put("dt_sub", creditutil.time());
								p1_result.put("type_id", type_id);
								p1_result.put("icbc_id", icbc_id);
								p1_result.put("jsonAll", "");
								p1_result.put("dn", "icbc_erp_kj_icbc_result");
								erp_wdrwService.update(p1);
								erp_wdrwService.save(p1_result);
							}
							break;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}

				}
				// ��Ƶ��ǩ���
				if (type_id.equals("6")) {
					PageData pd006 = new PageData();
					pd006.put("dn", "006");
					pd006.put("icbc_id", icbc_id);
					pd006.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd006);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "22":
							erp_status = 23;
							break;
						case "23":
							erp_status = 24;
							break;
						case "24":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 25;
							} else if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 23;
							}
							break;
						case "25":
							erp_status = 25;
							break;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}

				}

				// ����������
				if (type_id.equals("8")) {
					System.out.println("������:" + pageData.get("dk_price"));
					int dkbj = 0;
					if (pageData.get("dk_price") != null) {
						dkbj = Integer.parseInt(pageData.get("dk_price")
								.toString());
					}
					PageData pd008 = new PageData();
					pd008.put("dn", "008");
					pd008.put("icbc_id", icbc_id);
					pd008.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd008);
					PageData pdyhds = new PageData();
					pdyhds.put("dn", "yhds_tocode");
					pdyhds.put("icbc_id", icbc_id);
					pdyhds.put("type_id", 4);
					pdyhds.put("status", 15);
					PageData yhdspd = erp_wdrwService.icbc_form(pdyhds);
					if (yhdspd != null && !yhdspd.equals("")) {
						System.out.println("���е���"
								+ yhdspd.get("result_1_code"));
						request.setAttribute("yhds_code",
								yhdspd.get("result_1_code"));
					}
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "31":
							erp_status = 32;
							break;
						case "32":
							erp_status = 33;
							break;
						case "33":
							if (dkbj < 150000) {
								if (pData2.get("result_1_code").toString()
										.equals("1")) {
									erp_status = 40;
								} else if (pData2.get("result_1_code")
										.toString().equals("3")
										|| pData2.get("result_1_code")
												.toString().equals("2")) {
									erp_status = 31;
								}
							} else {
								if (pData2.get("result_1_code").toString()
										.equals("1")
										|| pData2.get("result_1_code")
												.toString().equals("2")) {
									erp_status = 34;

								} else if (pData2.get("result_1_code")
										.toString().equals("3")) {
									erp_status = 31;
								}
							}

							break;
						case "34":
							erp_status = 35;
							break;
						case "35":
							if (dkbj < 300000) {
								if (pData2.get("result_1_code").toString()
										.equals("1")) {
									erp_status = 40;
								} else if (pData2.get("result_1_code")
										.toString().equals("3")
										|| pData2.get("result_1_code")
												.toString().equals("2")) {
									erp_status = 31;
								}
							} else {
								if (pData2.get("result_1_code").toString()
										.equals("1")
										|| pData2.get("result_1_code")
												.toString().equals("2")) {
									erp_status = 36;

								} else if (pData2.get("result_1_code")
										.toString().equals("3")) {
									erp_status = 31;
								}
							}
							break;
						case "36":
							erp_status = 37;
							break;
						case "37":
							if (dkbj < 600000) {
								if (pData2.get("result_1_code").toString()
										.equals("1")) {
									erp_status = 40;
								} else if (pData2.get("result_1_code")
										.toString().equals("3")
										|| pData2.get("result_1_code")
												.toString().equals("2")) {
									erp_status = 31;
								}
							} else {
								if (pData2.get("result_1_code").toString()
										.equals("1")
										|| pData2.get("result_1_code")
												.toString().equals("2")) {
									erp_status = 38;

								} else if (pData2.get("result_1_code")
										.toString().equals("3")) {
									erp_status = 31;
								}
							}
							break;
						case "38":
							erp_status = 39;
							break;
						case "39":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 40;
							} else if (pData2.get("result_1_code").toString()
									.equals("3")
									|| pData2.get("result_1_code").toString()
											.equals("2")) {
								erp_status = 31;
							}
							break;
						case "40":
							erp_status = 40;
							PageData result_date = new PageData();
							result_date.put("dn", "yhds_tocode");
							result_date.put("type_id", type_id);
							result_date.put("status", erp_status);
							result_date.put("icbc_id", icbc_id);
							PageData rData = erp_wdrwService
									.icbc_form(result_date);
							if (rData == null || rData.equals("")) {
								PageData p1 = new PageData();
								PageData p1_result = new PageData();
								p1.put("dn", "icbc_erp_kj_icbc");
								p1.put("mid_edit", pData2.get("mid_edit"));
								p1.put("dt_edit", creditutil.time());
								p1.put("status", 21);
								p1.put("id", pData2.get("qryid"));
								p1_result.put("qryid", pData2.get("qryid"));
								p1_result
										.put("mid_add", pData2.get("mid_edit"));
								p1_result.put("mid_edit",
										pData2.get("mid_edit"));
								p1_result.put("dt_add", creditutil.time());
								p1_result.put("dt_edit", creditutil.time());
								p1_result.put("status", 21);
								p1_result.put("status_oldht", 0);
								p1_result.put("remark", "���");
								p1_result.put("result_1_code", 0);
								p1_result.put("result_1_msg", "");
								p1_result.put("result_1_value", "");
								p1_result.put("dt_sub", creditutil.time());
								p1_result.put("type_id", type_id);
								p1_result.put("icbc_id", icbc_id);
								p1_result.put("jsonAll", "");
								p1_result.put("dn", "icbc_erp_kj_icbc_result");
								erp_wdrwService.update(p1);
								erp_wdrwService.save(p1_result);
							}
							break;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}
				}
				// �ʽ������
				if (type_id.equals("10")) {
					int dz_type = 0;
					System.out.println("��������:" + pageData.get("dz_type"));
					if (pageData.get("dz_type") != null
							&& !pageData.get("dz_type").equals("")) {
						dz_type = Integer.parseInt(pageData.get("dz_type")
								.toString());
					}
					PageData pd006 = new PageData();
					pd006.put("dn", "010");
					pd006.put("icbc_id", icbc_id);
					pd006.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd006);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "47":
							if (dz_type == 2) {
								erp_status = 48;
							} else {
								erp_status = 49;
							}
							break;
						case "48":
							JSONObject jsonObject_48 = JSONObject
									.parseObject(pData2.get("result_1_value")
											.toString());
							request.setAttribute("xdzje",
									jsonObject_48.get("xdzje_48"));
							request.setAttribute("rzfwf",
									jsonObject_48.get("rzfwf_48"));
							erp_status = 52;
							break;
						case "49":
							erp_status = 99;
							break;
						case "99":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 50;
							} else {
								erp_status = 49;
							}
							break;
						case "50":
							erp_status = 51;
							break;
						case "51":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 55;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								PageData pData3 = new PageData();
								pData3.put("dn", "yhds_tocode2");
								pData3.put("type_id", type_id);
								pData3.put("status", 51);
								pData3.put("icbc_id", icbc_id);
								PageData pData4 = erp_wdrwService
										.icbc_form(pData3);
								System.out.println("id�Աȣ�" + pData4.get("id")
										+ "--" + pData2.get("id"));
								if (pData4.get("id").equals(pData2.get("id"))) {
									erp_status = 54;
								} else {
									erp_status = 50;
								}
							} else if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 49;
							}
							break;
						case "52":
							if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 49;
							} else if (pData2.get("result_1_code").toString()
									.equals("1")
									|| pData2.get("result_1_code").toString()
											.equals("3")) {
								erp_status = 48;
							}

							break;
						/*
						 * case "53": erp_status =54; break;
						 */
						case "54":
							erp_status = 50;
							break;
						case "55":
							erp_status = 55;
							PageData result_date = new PageData();
							result_date.put("dn", "yhds_tocode");
							result_date.put("type_id", type_id);
							result_date.put("status", erp_status);
							result_date.put("icbc_id", icbc_id);
							PageData rData = erp_wdrwService
									.icbc_form(result_date);
							if (rData == null || rData.equals("")) {
								PageData p1 = new PageData();
								PageData p1_result = new PageData();
								p1.put("dn", "icbc_erp_kj_icbc");
								p1.put("mid_edit", pData2.get("mid_edit"));
								p1.put("dt_edit", creditutil.time());
								p1.put("status", erp_status);
								p1.put("id", pData2.get("qryid"));
								p1_result.put("qryid", pData2.get("qryid"));
								p1_result
										.put("mid_add", pData2.get("mid_edit"));
								p1_result.put("mid_edit",
										pData2.get("mid_edit"));
								p1_result.put("dt_add", creditutil.time());
								p1_result.put("dt_edit", creditutil.time());
								p1_result.put("status", 21);
								p1_result.put("status_oldht", 0);
								p1_result.put("remark", "���");
								p1_result.put("result_1_code", 0);
								p1_result.put("result_1_msg", "");
								p1_result.put("result_1_value", "");
								p1_result.put("dt_sub", creditutil.time());
								p1_result.put("type_id", type_id);
								p1_result.put("icbc_id", icbc_id);
								p1_result.put("jsonAll", "");
								p1_result.put("dn", "icbc_erp_kj_icbc_result");
								erp_wdrwService.update(p1);
								erp_wdrwService.save(p1_result);
							}
							break;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}
				}

				// 16.���ʰ��
				if (type_id.equals("16")) {
					PageData pd006 = new PageData();
					pd006.put("dn", "004");
					pd006.put("icbc_id", icbc_id);
					pd006.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd006);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "100":
							erp_status = 101;
							break;
						case "101":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 102;
							}
							if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 104;
							}
							break;
						case "102":
							erp_status = 103;
							break;
						case "103":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 104;
							}
							if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 102;
							}
							break;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}
				}
				// 11.���д���������
				if (type_id.equals("11")) {
					PageData pd0011 = new PageData();
					pd0011.put("dn", "004");
					pd0011.put("icbc_id", icbc_id);
					pd0011.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd0011);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "56":
							erp_status = 57;
							break;
						case "57":
							erp_status = 58;
							break;
						case "58":
							JSONObject json = (JSONObject) JSON.parse(pData2
									.get("result_1_value").toString());
							if (pData2.get("result_1_code").toString()
									.equals("1")
									&& json.get("58_msg").toString()
											.equals("���յ�")) {
								erp_status = 59;
							} else {
								erp_status = 57;
							}
							break;
						case "59":
							JSONObject json2 = (JSONObject) JSON.parse(pData2
									.get("result_1_value").toString());
							if (pData2.get("result_1_code").toString()
									.equals("1")
									&& json2.get("58_msg").toString()
											.equals("���յ�")) {
								erp_status = 60;
							} else {
								erp_status = 58;
							}
							break;
						case "60":
							// 1��ͨ���������зſ������ſ����е����ʧ�ܡ�������һ����
							// 2����ͨ��������
							// 3�����������������������ȷ�ϡ�-�����ϲ���������Ҫ�������䡱-��������ϡ��ύ-���������ȷ�ϡ�-��������-���ص�һ�����Ͳ���
							// 4���ȵ�Ѻ��Ŵ���ѡ�����������ʱ�����¼��ߣ��ȡ���Ѻ�鵵��ģ����ɿ��������зſ�����
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 61;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 65;
							} else if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 63;
							} else if (pData2.get("result_1_code").toString()
									.equals("4")) {
								// �ȵ�Ѻ��Ŵ���ѡ�����������ʱ�����¼��ߣ��ȡ���Ѻ�鵵��ģ����ɿ��������зſ�����
								// �õ� ��Ѻ�鵵���-С״̬
								PageData get_status = new PageData();
								get_status
										.put("dn",
												"selectListStatus_icbc_erp_kj_icbc_result");
								get_status.put("icbc_id", icbc_id);
								get_status.put("type_id", 13); // ��Ѻ�鵵-���
								get_status.put("status", 82); // ��Ѻ�鵵���-С״̬
								List<PageData> pErpIcbc_82 = new ArrayList<>();
								pErpIcbc_82 = erp_fiveModelService
										.findtolist(get_status);
								if (pErpIcbc_82.size() > 0) {
									// ����Ѻ�鵵��ģ����� , ���������зſ�����
									erp_status = 61;
								} else {
									// ����Ѻ�鵵��ģ�� δ��� ,��ʱ�����¼���
									erp_status = 60;
								}
							}
							break;
						case "61":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 62;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 60;
							}
							break;
						case "62":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 65;
							} else {
								erp_status = 61;
							}
							break;
						case "63":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 57;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 64;
							}
							break;
						case "64":
							erp_status = 63;
							break;
						case "65":
							erp_status = 65;
							break;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}
				}

				// 7.������ҵ������
				if (type_id.equals("7")) {
					PageData pd006 = new PageData();
					pd006.put("dn", "004");
					pd006.put("icbc_id", icbc_id);
					pd006.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd006);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "26":
							erp_status = 98;
							break;
						case "98":
							erp_status = 27;
							break;
						case "27":
							// result_1_code �ֶ� 1-ͨ�� 2-��ͨ�� 3-����
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 29;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 30;
							} else if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 98;
							}
							break;
						case "29":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 30;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 30;
							} else if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 98;
							}
							break;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}
				}
				// 9.����ͨ�ڰ��
				if (type_id.equals("9")) {
					PageData pd006 = new PageData();
					pd006.put("dn", "004");
					pd006.put("icbc_id", icbc_id);
					pd006.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd006);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "41":
							erp_status = 42;
							break;
						case "42":
							erp_status = 43;
							break;
						case "43":
							erp_status = 44;
							/*
							 * if
							 * (pData2.get("result_1_code").toString().equals(
							 * "1")) { erp_status=25; }else
							 * if(pData2.get("result_1_code"
							 * ).toString().equals("3")){ erp_status=23; }
							 */
							break;
						case "44":
							erp_status = 45;
							break;
						case "45":
							erp_status = 46;
							break;
						case "46":
							erp_status = 46;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}
				}
				// 12.��˾�鵵���
				if (type_id.equals("12")) {
					PageData pd0011 = new PageData();
					pd0011.put("dn", "004");
					pd0011.put("icbc_id", icbc_id);
					pd0011.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd0011);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "66":
							erp_status = 67;
							break;
						case "67":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 68;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 69;
							}
							break;
						case "68":
							erp_status = 70;
							break;
						case "69":
							erp_status = 67;
							break;
						case "70":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 71;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 67;
							}
							break;
						case "71":
							erp_status = 71;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}
				}
				// 13.��Ѻ�鵵
				if (type_id.equals("13")) {
					PageData pd006 = new PageData();
					pd006.put("dn", "004");
					pd006.put("icbc_id", icbc_id);
					pd006.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd006);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "72":
							erp_status = 73;
							break;
						case "73":
							erp_status = 74;
							break;
						case "74":
							erp_status = 75;
							break;
						case "75":
							JSONObject jsonObject = JSONObject
									.parseObject(pData2.get("result_1_value")
											.toString());
							if (jsonObject.get("75_clfh").equals("��ͨ��")
									|| jsonObject.get("75_sjqr").equals("δ�յ�")) {
								erp_status = 74;
							} else {
								erp_status = 76;
							}
							break;
						case "76":
							JSONObject jsonObject1 = JSONObject
									.parseObject(pData2.get("result_1_value")
											.toString());
							if (jsonObject1 != null) {
								if (jsonObject1.get("76_cyqk") != null
										&& !jsonObject1.get("76_cyqk").equals(
												"")) {
									if (jsonObject1.get("76_cyqk")
											.equals("��ͨ��")) {
										erp_status = 75;
									} else {
										erp_status = 77;
									}

								}

							}

							break;
						case "77":
							erp_status = 78;
							break;
						case "78":
							JSONObject jsonObject2 = JSONObject
									.parseObject(pData2.get("result_1_value")
											.toString());
							if (jsonObject2.get("78_clfh").equals("��ͨ��")
									|| jsonObject2.get("78_sjqr").equals("δ�յ�")) {
								erp_status = 77;
							} else {
								erp_status = 79;
							}
							break;
						case "79":
							erp_status = 80;
							break;
						case "80":
							JSONObject jsonObject3 = JSONObject
									.parseObject(pData2.get("result_1_value")
											.toString());
							if (jsonObject3.get("80_clfh").equals("��ͨ��")
									|| jsonObject3.get("80_sjqr").equals("δ�յ�")) {
								erp_status = 79;
							} else {
								erp_status = 81;
							}

							break;
						case "81":
							JSONObject jsonObject4 = JSONObject
									.parseObject(pData2.get("result_1_value")
											.toString());
							if (jsonObject4.get("81_cyqk").equals("��ͨ��")) {
								erp_status = 80;
							} else {
								erp_status = 82;
							}
							break;
						case "82":
							erp_status = 82;
							PageData result_date2 = new PageData();
							result_date2.put("dn", "yhds_tocode");
							result_date2.put("type_id", 10);
							result_date2.put("status", 54);
							result_date2.put("icbc_id", icbc_id);
							PageData result_date3 = new PageData();
							result_date3.put("dn", "icbc_tocode");
							result_date3.put("type_id", 10);
							result_date3.put("icbc_id", icbc_id);
							PageData rData2 = erp_wdrwService
									.icbc_form(result_date2);
							PageData rData3 = erp_wdrwService
									.icbc_form(result_date3);
							if (rData3 != null) {
								if (rData2 == null || rData2.equals("")) {
									PageData p1 = new PageData();
									PageData p1_result = new PageData();
									p1.put("dn", "icbc_erp_kj_icbc");
									p1.put("mid_edit", pData2.get("mid_edit"));
									p1.put("dt_edit", creditutil.time());
									p1.put("status", 54);
									p1.put("id", rData3.get("id"));
									p1_result.put("qryid", rData3.get("id"));
									p1_result.put("mid_add",
											pData2.get("mid_edit"));
									p1_result.put("mid_edit",
											pData2.get("mid_edit"));
									p1_result.put("dt_add", creditutil.time());
									p1_result.put("dt_edit", creditutil.time());
									p1_result.put("status", 54);
									p1_result.put("status_oldht", 0);
									p1_result.put("remark", "���");
									p1_result.put("result_1_code", 0);
									p1_result.put("result_1_msg", "");
									p1_result.put("result_1_value", "");
									p1_result.put("dt_sub", creditutil.time());
									p1_result.put("type_id", 10);
									p1_result.put("icbc_id", icbc_id);
									p1_result.put("jsonAll", "");
									p1_result.put("dn",
											"icbc_erp_kj_icbc_result");
									erp_wdrwService.update(p1);
									erp_wdrwService.save(p1_result);
								}
							}
							PageData result_date = new PageData();
							result_date.put("dn", "yhds_tocode");
							result_date.put("type_id", type_id);
							result_date.put("status", erp_status);
							result_date.put("icbc_id", icbc_id);
							PageData rData = erp_wdrwService
									.icbc_form(result_date);
							if (rData == null || rData.equals("")) {
								PageData p1 = new PageData();
								PageData p1_result = new PageData();
								p1.put("dn", "icbc_erp_kj_icbc");
								p1.put("mid_edit", pData2.get("mid_edit"));
								p1.put("dt_edit", creditutil.time());
								p1.put("status", erp_status);
								p1.put("id", pData2.get("qryid"));
								p1_result.put("qryid", pData2.get("qryid"));
								p1_result
										.put("mid_add", pData2.get("mid_edit"));
								p1_result.put("mid_edit",
										pData2.get("mid_edit"));
								p1_result.put("dt_add", creditutil.time());
								p1_result.put("dt_edit", creditutil.time());
								p1_result.put("status", 21);
								p1_result.put("status_oldht", 0);
								p1_result.put("remark", "���");
								p1_result.put("result_1_code", 0);
								p1_result.put("result_1_msg", "");
								p1_result.put("result_1_value", "");
								p1_result.put("dt_sub", creditutil.time());
								p1_result.put("type_id", type_id);
								p1_result.put("icbc_id", icbc_id);
								p1_result.put("jsonAll", "");
								p1_result.put("dn", "icbc_erp_kj_icbc_result");
								erp_wdrwService.update(p1);
								erp_wdrwService.save(p1_result);
							}
							break;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}
				}
				// 14.ҵ����Ϣ�޸�
				if (type_id.equals("14")) {
					PageData pd006 = new PageData();
					pd006.put("dn", "004");
					pd006.put("icbc_id", icbc_id);
					pd006.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd006);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "83":
							erp_status = 96;
							break;
						case "96":
							erp_status = 84;
							break;
						case "84":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 85;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 86;
							}
							break;
						case "85":
							erp_status = 86;
							break;
						case "86":
							erp_status = 86;
							break;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}
				}
				// 15.�˵��˷�
				// 15.�˵��˷��޸�
				if (type_id.equals("15")) {
					PageData pd006 = new PageData();
					pd006.put("dn", "004");
					pd006.put("icbc_id", icbc_id);
					pd006.put("type_id", type_id);
					erp15 = erp_wdrwService.icbc_list(pd006);
					if (erp15.size() > 0) {
						PageData pData2 = erp15.get(erp15.size() - 1);
						request.setAttribute("pData2", pData2);
						switch (pData2.get("status").toString()) {
						case "87":
							erp_status = 97;
							break;
						case "97":
							erp_status = 88;
							break;
						case "88":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 90;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 89;
							}
							break;
						case "89":
							erp_status = 88;
							break;
						case "90":
							// �õ��Ƿ� ����
							PageData pdd_status = new PageData();
							pdd_status.put("dn",
									"selectListStatus_icbc_erp_kj_icbc_result");
							pdd_status.put("icbc_id", icbc_id);
							pdd_status.put("type_id", 10); // �ʽ����-���
							pdd_status.put("status", 50); // ����-С״̬
							List<PageData> pErpIcbc_50 = new ArrayList<>();
							pErpIcbc_50 = erp_fiveModelService
									.findtolist(pdd_status);
							// �õ� �����ռ�ȷ�� �Ľ��
							PageData pdd_s = new PageData();
							pdd_s.put("dn",
									"selectListStatus_icbc_erp_kj_icbc_result");
							pdd_s.put("icbc_id", icbc_id);
							pdd_s.put("type_id", 11); // ��������-���
							pdd_s.put("status", 58); // �����ռ�ȷ��-С״̬
							List<PageData> pErpIcbc_58 = new ArrayList<>();
							pErpIcbc_58 = erp_fiveModelService
									.findtolist(pdd_s);
							// �õ����һ������
							PageData get_max58 = pErpIcbc_58.get(pErpIcbc_58
									.size() - 1);
							JSONObject json = (JSONObject) JSON.parse(get_max58
									.get("result_1_value").toString());
							if (pData2.get("result_1_code").toString()
									.equals("1")) { // ͨ��
								if (pErpIcbc_50.size() > 0) { // ��Ǯ��
									// ͨ��-���� ����91
									erp_status = 91;
								} else {
									if (json != null) {
										if (json.get("58_msg").toString()
												.equals("���յ�")) {
											// ͨ��-δ����-���ռ� ����93
											erp_status = 93;
										} else if (json.get("58_msg")
												.toString().equals("δ�յ�")) {
											// ͨ��-δ����-δ�ռ� ����95���
											erp_status = 95;
										}
									}
								}
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								// ��ͨ��
								erp_status = 89;
							}
							break;
						case "91":
							erp_status = 92;
							break;
						case "92":
							// 1���ˡ�ȷ�������ѵ������ռ��� ����93
							// 2�ѵ���δ�ռ� ����95
							// 3����/δ���� ����91
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 93;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 95;
							} else if (pData2.get("result_1_code").toString()
									.equals("3")) {
								erp_status = 91;
							}
							break;
						case "93":
							erp_status = 94;
							break;
						case "94":
							if (pData2.get("result_1_code").toString()
									.equals("1")) {
								erp_status = 95;
							} else if (pData2.get("result_1_code").toString()
									.equals("2")) {
								erp_status = 93;
							}
							break;
						default:
							erp_status = Integer.parseInt(pData2.get("status")
									.toString());
							break;
						}
					}
				}
				// erp�������ͨ�ð��--��ʼ
				System.out.println("-----״̬:" + erp_status);
				PageData pd1 = new PageData();
				pd1.put("status", erp_status);
				pd1.put("dn", "commtasknametoname");
				PageData sh_names = erp_wdrwService.icbc_form(pd1);
				if (sh_names != null && !sh_names.equals("")) {
					System.out.println("-----�������:" + sh_names);
					request.setAttribute("sh_name", sh_names.get("name"));
				}
				request.setAttribute("erp_status", erp_status);
				request.setAttribute("erp15", erp15);

				// erp�������ͨ�ð��--����

				List<PageData> pList = erp_wdrwService.icbc_list(pd2);
				System.out.println("��������:" + pList.size());
				request.setAttribute("pList", pList);
				request.setAttribute("type_id", type_id);
				// ���Ų���
				// ///////////////////////add
				// start//////////////////////////////////
				/*
				 * List<Object> zxbc_imgs = new ArrayList<>();
				 * if(pageData.get("imgstep2_1"
				 * )!=null&&!pageData.get("imgstep2_1").equals("")){
				 * zxbc_imgs.add(pageData.get("imgstep2_1")); }
				 * if(pageData.get("imgstep2_2"
				 * )!=null&&!pageData.get("imgstep2_2").equals("")){
				 * zxbc_imgs.add(pageData.get("imgstep2_2")); }
				 * if(pageData.get("imgstep2_3"
				 * )!=null&&!pageData.get("imgstep2_3").equals("")){
				 * zxbc_imgs.add(pageData.get("imgstep2_3")); }
				 * if(pageData.get("imgstep2_4"
				 * )!=null&&!pageData.get("imgstep2_4").equals("")){
				 * zxbc_imgs.add(pageData.get("imgstep2_4")); }
				 * if(pageData.get("imgstep2_5"
				 * )!=null&&!pageData.get("imgstep2_5").equals("")){
				 * zxbc_imgs.add(pageData.get("imgstep2_5")); } String[] imgs =
				 * null; if (pageData.get("imgstep2_5s") != null &&
				 * !pageData.get("imgstep2_5s").equals("")) { imgs = ((String)
				 * pageData.get("imgstep2_5s")).split(""); if (imgs.length > 0)
				 * { for (int i = 0; i < imgs.length; i++) { if (imgs[i] != null
				 * && !imgs[i].equals("")) { zxbc_imgs.add(imgs[i]); } } } }
				 * request.setAttribute("zxbc_imgs",zxbc_imgs);
				 */
				// ��ѯ����ͼƬ
				/*
				 * PageData pData2 = new PageData(); pData2.put("dn",
				 * "assess_cars_item"); pData2.put("cars_id",
				 * pageData.get("carsid")); List<PageData> img_pList =
				 * erp_wdrwService.icbc_list(pData2); List<PageData>
				 * img_pList1=new ArrayList<PageData>();
				 * if(img_pList!=null&&!img_pList.equals("")){ for(int
				 * i=0;i<img_pList.size();i++){ PageData
				 * pg_img=img_pList.get(i); PageData pg_img1=new PageData();
				 * if(!
				 * pg_img.get("bcimg").equals("")&&pg_img.get("bcimg")!=null){
				 * pg_img1.put("bcimg",pg_img.get("bcimg"));
				 * img_pList1.add(pg_img1); } }
				 * System.out.println("��������ͼƬ:"+img_pList1);
				 * request.setAttribute("img_pList", img_pList1); }
				 */
				PageData cars_pd = new PageData();
				cars_pd.put("dn", "erp_icbc_lsjl");
				cars_pd.put("icbc_id", icbc_id);
				cars_pd.put("status", 10);
				cars_pd.put("type_id", 3);
				List<PageData> cars_pdList = erp_wdrwService.icbc_list(cars_pd);
				if (cars_pdList != null && !cars_pdList.equals("")
						&& cars_pdList.size() > 0) {
					PageData pageData1 = cars_pdList.get(0);
					request.setAttribute("qcpg_dt_edit",
							pageData1.get("dt_edit"));
					if (pageData1.get("result_1_value") != null
							&& !pageData1.get("result_1_value").equals("")) {
						JSONObject jsonObject1 = JSONObject
								.parseObject(pageData1.get("result_1_value")
										.toString());
						List<Object> cars_imgs = new ArrayList<>();
						List<Object> cars_imgs1 = new ArrayList<>();
						List<Object> cars_imgs2 = new ArrayList<>();
						if (jsonObject1.get("imgstep9_1ss") != null
								&& !jsonObject1.get("imgstep9_1ss").equals("")) {
							cars_imgs1 = (List<Object>) jsonObject1
									.get("imgstep9_1ss");
							cars_imgs.addAll(cars_imgs1);
						}
						if (jsonObject1.get("imgstep9_2ss") != null
								&& !jsonObject1.get("imgstep9_2ss").equals("")) {
							cars_imgs2 = (List<Object>) jsonObject1
									.get("imgstep9_2ss");
							cars_imgs.addAll(cars_imgs2);
						}

						request.setAttribute("cars_imgs", cars_imgs);
					}
				}

				PageData pd = new PageData();
				pd.put("dn", "erp_icbc_lsjl");
				pd.put("icbc_id", icbc_id);
				pd.put("status", 2);
				pd.put("type_id", 1);
				List<PageData> pdList = erp_wdrwService.icbc_list(pd);
				if (pdList != null && !pdList.equals("") && pdList.size() > 0) {
					PageData pageData1 = pdList.get(0);
					request.setAttribute("zx_dt_edit", pageData1.get("dt_edit"));
					if (pageData1.get("result_1_value") != null
							&& !pageData1.get("result_1_value").equals("")) {
						System.out.println("result_1_value;"
								+ pageData1.get("result_1_value").toString()
										.replaceAll("\\\\", ""));
						JSONObject jsonObject1 = new JSONObject()
								.parseObject(replaceBlank(pageData1.get(
										"result_1_value").toString()));
						/*
						 * pageData2.put("img1",
						 * jsonObject1.get("imgstep2_1ss"));
						 * pageData2.put("img2",
						 * jsonObject1.get("imgstep2_2ss"));
						 * pageData2.put("img3",
						 * jsonObject1.get("imgstep2_3ss"));
						 * pageData2.put("img4",
						 * jsonObject1.get("imgstep2_4ss"));
						 */
						// �����˲���

						List<Object> zdr_imgss = new ArrayList<>();
						String[] zdr_imgs = null;
						if (jsonObject1.get("imgstep2_1ss") != null
								&& !jsonObject1.get("imgstep2_1ss").equals("")) {
							System.out.println("json:"
									+ jsonObject1.getString("imgstep2_1ss"));
							zdr_imgss = (List<Object>) jsonObject1
									.get("imgstep2_1ss");
							/*
							 * zdr_imgs =
							 * jsonObject1.getString("imgstep2_1ss").split(",");
							 * if (zdr_imgs.length > 0) { for (int i = 0; i <
							 * zdr_imgs.length; i++) { if (zdr_imgs[i] != null
							 * && !zdr_imgs[i].equals("")) {
							 * zdr_imgss.add(zdr_imgs[i]); } } }
							 */
						}
						request.setAttribute("zdr_imgss", zdr_imgss);
						// ��������ż����
						List<Object> zdrpo_imgss = new ArrayList<>();
						String[] zdrpo_imgs = null;
						if (jsonObject1.get("imgstep2_2ss") != null
								&& !jsonObject1.get("imgstep2_2ss").equals("")) {
							zdrpo_imgss = (List<Object>) jsonObject1
									.get("imgstep2_2ss");
							/*
							 * zdrpo_imgs
							 * =jsonObject1.getString("imgstep2_2ss").
							 * split(","); if (zdrpo_imgs.length > 0) { for (int
							 * i = 0; i < zdrpo_imgs.length; i++) { if
							 * (zdrpo_imgs[i] != null &&
							 * !zdrpo_imgs[i].equals("")) {
							 * zdrpo_imgss.add(zdrpo_imgs[i]); } } }
							 */
						}
						request.setAttribute("zdrpo_imgss", zdrpo_imgss);
						// ������1����
						List<Object> ghr1_imgss = new ArrayList<>();
						String[] ghr1_imgs = null;
						if (jsonObject1.get("imgstep2_3ss") != null
								&& !jsonObject1.get("imgstep2_3ss").equals("")) {
							/*
							 * ghr1_imgs =
							 * jsonObject1.getString("imgstep2_3ss").split(",");
							 * if (ghr1_imgs.length > 0) { for (int i = 0; i <
							 * ghr1_imgs.length; i++) { if (ghr1_imgs[i] != null
							 * && !ghr1_imgs[i].equals("")) {
							 * ghr1_imgss.add(ghr1_imgs[i]); } } }
							 */
							ghr1_imgss = (List<Object>) jsonObject1
									.get("imgstep2_3ss");
						}
						request.setAttribute("ghr1_imgss", ghr1_imgss);
						// ������2����
						List<Object> ghr2_imgss = new ArrayList<>();
						String[] ghr2_imgs = null;
						if (jsonObject1.get("imgstep2_4ss") != null
								&& !jsonObject1.get("imgstep2_4ss").equals("")) {
							/*
							 * ghr2_imgs
							 * =jsonObject1.getString("imgstep2_4ss").split
							 * (","); if (ghr2_imgs.length > 0) { for (int i =
							 * 0; i < ghr2_imgs.length; i++) { if (ghr2_imgs[i]
							 * != null && !ghr2_imgs[i].equals("")) {
							 * ghr2_imgss.add(ghr2_imgs[i]); } } }
							 */
							ghr2_imgss = (List<Object>) jsonObject1
									.get("imgstep2_3ss");
						}
						request.setAttribute("ghr2_imgss", ghr2_imgss);
					}
				}

				// ͨ�ڲ���
				/*
				 * String[] imgss = null; if (pageData.get("imgstep8_1ss") !=
				 * null && !pageData.get("imgstep8_1ss").equals("")) { imgss =
				 * ((String) pageData.get("imgstep8_1ss")).split(""); if
				 * (imgss.length > 0) { request.setAttribute("tr_imgs", imgss);
				 * } }
				 */
				PageData zxtr_pd = new PageData();
				zxtr_pd.put("dn", "erp_icbc_lsjl");
				zxtr_pd.put("icbc_id", icbc_id);
				zxtr_pd.put("status", 5);
				zxtr_pd.put("type_id", 2);
				List<PageData> zxtr_pdList = erp_wdrwService.icbc_list(zxtr_pd);
				System.out.println("����ͨ�ڲ���:" + zxtr_pdList.size());
				if (zxtr_pdList != null && !zxtr_pdList.equals("")
						&& zxtr_pdList.size() > 0) {
					PageData pageData1 = zxtr_pdList.get(0);
					request.setAttribute("trzx_dt_edit",
							pageData1.get("dt_edit"));
					if (pageData1.get("result_1_value") != null
							&& !pageData1.get("result_1_value").equals("")) {
						String result_1_value = pageData1.get("result_1_value")
								.toString();
						JSONObject jsonObject1 = JSONObject
								.parseObject(replaceBlank(result_1_value));
						List<Object> tr_imgs = new ArrayList<>();
						String[] ghr2_imgs = null;
						if (jsonObject1.get("imgstep8_1ss") != null
								&& !jsonObject1.get("imgstep8_1ss").equals("")) {
							tr_imgs = (List<Object>) jsonObject1
									.get("imgstep8_1ss");
						}
						request.setAttribute("tr_imgs", tr_imgs);

					}
				}

				// ��������PDF����
				List<Object> KK_PDFs = new ArrayList<>();
				if (pageData.get("pdf") != null
						&& !pageData.get("pdf").equals("")) {
					KK_PDFs.add(pageData.get("pdf"));
				}
				if (pageData.get("pdfstep4_1") != null
						&& !pageData.get("pdfstep4_1").equals("")) {
					KK_PDFs.add(pageData.get("pdfstep4_1"));
				}
				request.setAttribute("KK_PDFs", KK_PDFs);
				// ��Ƶ����
				List<Object> mq_videos = new ArrayList<>();
				if (pageData.get("imgstep8_1v") != null
						&& !pageData.get("imgstep8_1v").equals("")) {
					mq_videos.add(pageData.get("imgstep8_1v"));
				}
				if (pageData.get("imgstep8_2v") != null
						&& !pageData.get("imgstep8_2v").equals("")) {
					mq_videos.add(pageData.get("imgstep8_2v"));
				}
				if (pageData.get("imgstep8_3v") != null
						&& !pageData.get("imgstep8_3v").equals("")) {
					mq_videos.add(pageData.get("imgstep8_3v"));
				}
				if (pageData.get("imgstep8_4v") != null
						&& !pageData.get("imgstep8_4v").equals("")) {
					mq_videos.add(pageData.get("imgstep8_4v"));
				}
				request.setAttribute("mq_videos", mq_videos);
				// ///////////////////////add
				// end//////////////////////////////////

				// ��������
				PageData kk_pd = new PageData();
				kk_pd.put("dn", "erp_icbc_lsjl");
				kk_pd.put("icbc_id", icbc_id);
				kk_pd.put("status", 18);
				kk_pd.put("type_id", 5);
				List<PageData> kk_pdList = erp_wdrwService.icbc_list(kk_pd);
				if (kk_pdList != null && !kk_pdList.equals("")
						&& kk_pdList.size() > 0) {
					PageData pageData1 = kk_pdList.get(0);
					request.setAttribute("kk_dt_edit", pageData1.get("dt_edit"));
					if (pageData1.get("result_1_value") != null
							&& !pageData1.get("result_1_value").equals("")) {
						JSONObject jsonObject1 = JSONObject
								.parseObject(pageData1.get("result_1_value")
										.toString());
						List<Object> kk_imgs = new ArrayList<>();
						if (jsonObject1.get("imgstep3_7s") != null
								&& !jsonObject1.get("imgstep3_7s").equals("")) {
							kk_imgs = JSONArray.parseArray(jsonObject1.get(
									"imgstep3_7s").toString());
						}
						if (jsonObject1.get("imgstep3_1") != null
								&& !jsonObject1.get("imgstep3_1").equals("")) {
							kk_imgs.add(jsonObject1.get("imgstep3_1"));
						}
						if (jsonObject1.get("imgstep3_2") != null
								&& !jsonObject1.get("imgstep3_2").equals("")) {
							kk_imgs.add(jsonObject1.get("imgstep3_2"));
						}
						if (jsonObject1.get("imgstep3_3") != null
								&& !jsonObject1.get("imgstep3_3").equals("")) {
							kk_imgs.add(jsonObject1.get("imgstep3_3"));
						}
						if (jsonObject1.get("imgstep3_4") != null
								&& !jsonObject1.get("imgstep3_4").equals("")) {
							kk_imgs.add(jsonObject1.get("imgstep3_4"));
						}
						if (jsonObject1.get("imgstep3_5") != null
								&& !jsonObject1.get("imgstep3_5").equals("")) {
							kk_imgs.add(jsonObject1.get("imgstep3_5"));
						}
						if (jsonObject1.get("imgstep3_6") != null
								&& !jsonObject1.get("imgstep3_6").equals("")) {
							kk_imgs.add(jsonObject1.get("imgstep3_6"));
						}
						if (jsonObject1.get("imgstep3_7") != null
								&& !jsonObject1.get("imgstep3_7").equals("")) {
							kk_imgs.add(jsonObject1.get("imgstep3_7"));
						}
						if (jsonObject1.get("imgstep3_71") != null
								&& !jsonObject1.get("imgstep3_71").equals("")) {
							kk_imgs.add(jsonObject1.get("imgstep3_71"));
						}
						if (jsonObject1.get("imgstep3_72") != null
								&& !jsonObject1.get("imgstep3_72").equals("")) {
							kk_imgs.add(jsonObject1.get("imgstep3_72"));
						}
						request.setAttribute("kk_imgs", kk_imgs);
					}

				}
				/*
				 * List<Object> kk_imgs = new ArrayList<>();
				 * if(pageData.get("imgstep3_1"
				 * )!=null&&!pageData.get("imgstep3_1").equals("")){
				 * kk_imgs.add(pageData.get("imgstep3_1")); }
				 * if(pageData.get("imgstep3_2"
				 * )!=null&&!pageData.get("imgstep3_2").equals("")){
				 * kk_imgs.add(pageData.get("imgstep3_2")); }
				 * if(pageData.get("imgstep3_3"
				 * )!=null&&!pageData.get("imgstep3_3").equals("")){
				 * kk_imgs.add(pageData.get("imgstep3_3")); }
				 * if(pageData.get("imgstep3_4"
				 * )!=null&&!pageData.get("imgstep3_4").equals("")){
				 * kk_imgs.add(pageData.get("imgstep3_4")); }
				 * if(pageData.get("imgstep3_5"
				 * )!=null&&!pageData.get("imgstep3_5").equals("")){
				 * kk_imgs.add(pageData.get("imgstep3_5")); }
				 * if(pageData.get("imgstep3_6"
				 * )!=null&&!pageData.get("imgstep3_6").equals("")){
				 * kk_imgs.add(pageData.get("imgstep3_6")); }
				 * if(pageData.get("imgstep3_7"
				 * )!=null&&!pageData.get("imgstep3_7").equals("")){
				 * kk_imgs.add(pageData.get("imgstep3_7")); }
				 * if(pageData.get("imgstep3_71"
				 * )!=null&&!pageData.get("imgstep3_71").equals("")){
				 * kk_imgs.add(pageData.get("imgstep3_71")); }
				 * if(pageData.get("imgstep3_72"
				 * )!=null&&!pageData.get("imgstep3_72").equals("")){
				 * kk_imgs.add(pageData.get("imgstep3_72")); } String[]
				 * kk_imgss; if (pageData.get("imgstep3_7s") != null &&
				 * !pageData.get("imgstep3_72").equals("")) { kk_imgss =
				 * ((String) pageData.get("imgstep3_7s")).split(""); if
				 * (kk_imgss.length > 0) { for (int i = 0; i < kk_imgss.length;
				 * i++) { if (kk_imgss[i] != null && !kk_imgss[i].equals("")) {
				 * kk_imgs.add(kk_imgss[i]); } } } }
				 */

				// �����������
				PageData dk_pd = new PageData();
				dk_pd.put("dn", "erp_icbc_lsjl");
				dk_pd.put("icbc_id", icbc_id);
				dk_pd.put("status", 32);
				dk_pd.put("type_id", 8);
				List<PageData> dk_pdList = erp_wdrwService.icbc_list(dk_pd);
				if (dk_pdList != null && !dk_pdList.equals("")
						&& dk_pdList.size() > 0) {
					PageData pageData1 = dk_pdList.get(0);
					request.setAttribute("qcdk_dt_edit",
							pageData1.get("dt_edit"));
					if (pageData1.get("result_1_value") != null
							&& !pageData1.get("result_1_value").equals("")) {
						JSONObject jsonObject1 = JSONObject
								.parseObject(pageData1.get("result_1_value")
										.toString());
						// ֤������
						if (jsonObject1.get("imgstep5_1ss") != null
								&& !jsonObject1.get("imgstep5_1ss").equals("")) {
							List<Object> zm_imgs2 = JSONArray
									.parseArray(jsonObject1.get("imgstep5_1ss")
											.toString());
							request.setAttribute("zm_imgs2", zm_imgs2);
						}
						// ��ͬ����
						List<Object> qy_imgs = new ArrayList<>();
						if (jsonObject1.get("imgstep4_1ss") != null
								&& !jsonObject1.get("imgstep4_1ss").equals("")) {
							List<Object> list1 = JSONArray
									.parseArray(jsonObject1.get("imgstep4_1ss")
											.toString());
							qy_imgs.addAll(list1);
						}
						if (jsonObject1.get("imgstep4_2ss") != null
								&& !jsonObject1.get("imgstep4_2ss").equals("")) {
							List<Object> list2 = JSONArray
									.parseArray(jsonObject1.get("imgstep4_2ss")
											.toString());
							qy_imgs.addAll(list2);
						}
						if (jsonObject1.get("imgstep4_3ss") != null
								&& !jsonObject1.get("imgstep4_3ss").equals("")) {
							List<Object> list3 = JSONArray
									.parseArray(jsonObject1.get("imgstep4_3ss")
											.toString());
							qy_imgs.addAll(list3);
						}
						if (jsonObject1.get("imgstep4_4ss") != null
								&& !jsonObject1.get("imgstep4_4ss").equals("")) {
							List<Object> list4 = JSONArray
									.parseArray(jsonObject1.get("imgstep4_4ss")
											.toString());
							qy_imgs.addAll(list4);
						}
						if (jsonObject1.get("imgstep4_5ss") != null
								&& !jsonObject1.get("imgstep4_5ss").equals("")) {
							List<Object> list5 = JSONArray
									.parseArray(jsonObject1.get("imgstep4_5ss")
											.toString());
							qy_imgs.addAll(list5);
						}
						request.setAttribute("qy_imgs", qy_imgs);
						// ��������
						if (jsonObject1.get("imgstep6_1ss") != null
								&& !jsonObject1.get("imgstep6_1ss").equals("")) {
							List<Object> qt_imgs3 = JSONArray
									.parseArray(jsonObject1.get("imgstep6_1ss")
											.toString());
							request.setAttribute("qt_imgs3", qt_imgs3);
						}
						// �������
						if (jsonObject1.get("imgstep7_1ss") != null
								&& !jsonObject1.get("imgstep7_1ss").equals("")) {
							List<Object> bc_imgs4 = JSONArray
									.parseArray(jsonObject1.get("imgstep7_1ss")
											.toString());
							request.setAttribute("bc_imgs4", bc_imgs4);
						}
					}
				}

				/*
				 * Object imgss1 = (String) pageData.get("imgstep4_1ss") +
				 * pageData.get("imgstep4_2ss") + pageData.get("imgstep4_3ss") +
				 * pageData.get("imgstep4_4ss") + pageData.get("imgstep4_5ss");
				 * String[] qy_imgs = null; System.out.println("ǩԼ������Ϣ1:" +
				 * imgss1); imgss1 = imgss1.toString().replace("null", "");
				 */
				// ǩԼ����
				/*
				 * if (imgss1 != null && !imgss1.equals("")) { qy_imgs =
				 * ((String) imgss1).split(""); System.out.println("ǩԼ������Ϣ2:" +
				 * qy_imgs); if (qy_imgs.length > 0) {
				 * request.setAttribute("qy_imgs", qy_imgs); } }
				 */
				// ֤������
				/*
				 * String[] zm_imgs2 = null; if (pageData.get("imgstep5_1ss") !=
				 * null && !pageData.get("imgstep5_1ss").equals("")) { zm_imgs2
				 * = ((String) pageData.get("imgstep5_1ss")).split(""); if
				 * (zm_imgs2.length > 0) { request.setAttribute("zm_imgs2",
				 * zm_imgs2); } }
				 */
				// ��������
				/*
				 * String[] qt_imgs3 = null; if (pageData.get("imgstep6_1ss") !=
				 * null && !pageData.get("imgstep6_1ss").equals("")) { qt_imgs3
				 * = ((String) pageData.get("imgstep6_1ss")).split(""); if
				 * (qt_imgs3.length > 0) { request.setAttribute("qt_imgs3",
				 * qt_imgs3); } }
				 */
				// �������
				/*
				 * String[] bc_imgs4 = null; if (pageData.get("imgstep7_1ss") !=
				 * null && !pageData.get("imgstep7_1ss").equals("")) { bc_imgs4
				 * = ((String) pageData.get("imgstep7_1ss")).split(""); if
				 * (bc_imgs4.length > 0) { request.setAttribute("bc_imgs4",
				 * bc_imgs4); } }
				 */
			}
		}
		// ��ȡ���еİ��
		PageData erp_type = new PageData();
		erp_type.put("dn", "erp_type_id");
		erp_type.put("icbc_id", icbc_id);
		List<PageData> erplist = erp_wdrwService.icbc_list(erp_type);
		int erp1 = 0, erp2 = 0, erp3 = 0, erp4 = 0, erp5 = 0, erp6 = 0, erp7 = 0, erp8 = 0, erp9 = 0, erp10 = 0, erp11 = 0, erp12 = 0, erp13 = 0, erp14 = 0, erp15s = 0, erp16 = 0;
		for (int i = 0; i < erplist.size(); i++) {
			PageData pageData = erplist.get(i);
			switch (pageData.get("type_id").toString()) {
			case "1":
				erp1 = 1;
				break;
			case "2":
				erp2 = 1;
				break;
			case "3":
				erp3 = 1;
				break;
			case "4":
				erp4 = 1;
				break;
			case "5":
				erp5 = 1;
				break;
			case "6":
				erp6 = 1;
				break;
			case "7":
				erp7 = 1;
				break;
			case "8":
				erp8 = 1;
				break;
			case "9":
				erp9 = 1;
				break;
			case "10":
				erp10 = 1;
				break;
			case "11":
				erp11 = 1;
				break;
			case "12":
				erp12 = 1;
				break;
			case "13":
				erp13 = 1;
				break;
			case "14":
				erp14 = 1;
				break;
			case "15":
				erp15s = 1;
				break;
			case "16":
				erp16 = 1;
				break;
			default:
				break;
			}

		}
		request.setAttribute("erp1", erp1);
		request.setAttribute("erp2", erp2);
		request.setAttribute("erp3", erp3);
		request.setAttribute("erp4", erp4);
		request.setAttribute("erp5", erp5);
		request.setAttribute("erp6", erp6);
		request.setAttribute("erp7", erp7);
		request.setAttribute("erp8", erp8);
		request.setAttribute("erp9", erp9);
		request.setAttribute("erp10", erp10);
		request.setAttribute("erp11", erp11);
		request.setAttribute("erp12", erp12);
		request.setAttribute("erp13", erp13);
		request.setAttribute("erp14", erp14);
		request.setAttribute("erp15s", erp15s);
		request.setAttribute("erp16", erp16);
		request.setAttribute("icbc_id", icbc_id);
		request.setAttribute("dn", dn);
		request.setAttribute("cn", cn);
		request.setAttribute("cn1", cn1);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		return "kjs_icbc/index";
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static List removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	/*
	 * 
	 * GPS
	 */
	@RequestMapping(value = "erp/gps_list.do", produces = "text/html;charset=UTF-8")
	public String gps_list(String dn, String qn, String type,
			HttpServletRequest request) throws UnsupportedEncodingException {
		// http://www.tianyigps.cn/loginController.do?checkuser
		String str = "http://www.tianyigps.cn";
		request.setAttribute("str", str);
		request.setAttribute("dn", dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		return "kjs_icbc/index";
	}
}
