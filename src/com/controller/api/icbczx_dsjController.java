package com.controller.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.controller.data.TtMap;

public class icbczx_dsjController {
	public final static String merchantNo = "QDS00859";
	public final static String loginId = "jinyuanbaotest";
	public final static String accessKey = "30378115019385C7CA422171156073D1";
	public final static String shopNumber = "QDS00908";
	public final static String url = "http://47.101.136.207:8080/hbservice/risk/bodyguard";

	/*
	 * 'merchantNo' => 'QDS00859', 'loginId' => 'jinyuanbaotest', 'accessKey' =>
	 * '30378115019385C7CA422171156073D1', 'shopNumber' => 'QDS00908',
	 * 'merchantNo' => 'QDS04477', 'loginId' => 'kuaichedao', 'accessKey' =>
	 * '5D883646019F232F9E528D21C0E4C353', 'shopNumber' => 'QDS04477',
	 */
	// $url = "http://47.101.136.207:8080/hbservice/risk/bodyguard";
	// $url = "https://www.qhrtcb.com/hbservice/risk/bodyguard";
	// �������ݣ�
	// Ƥ����
	// 13333333333
	// 330105196602220623
	public static String getdsjzxhttp() {
		Map<String, Object> map = new HashMap<>();
		TtMap headers = new TtMap();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		map.put("merchantNo", merchantNo);
		map.put("loginId", loginId);
		map.put("accessKey", accessKey);
		map.put("shopNumber", shopNumber);
		map.put("requestId",
				getnewstr(UUID.randomUUID().toString().replaceAll("-", "")));
		map.put("accountName", "Ƥ����");
		map.put("accountMobile", "13333333333");
		map.put("idNumber", "330105196602220623");
		String mpstr = HttpTools.httpClientPost(url, map, "UTF-8", headers);
		System.out.println(mpstr);
		return mpstr;
	}

	/**
	 * �ַ���ȥ��
	 * 
	 * @param args
	 */

	public static String getnewstr(String str) {
		String s = ""; // �½�һ�����鱣���ַ�
		// String str = UUID.randomUUID().toString().replaceAll("-", "");
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i); // ȡ���ַ����е��ַ���
			if (s.indexOf(c) == -1) { // �ж��½��������Ƿ���ĳ���ַ�
				s += c;
			}
		}
		System.out.println(s);
		return s;
	}

	public static void main(String[] args) {
		String s = getdsjzxhttp();
		System.out.println("���ǡ�����"
				+ UUID.randomUUID().toString().replaceAll("-", ""));
		System.out.println(getnewstr(UUID.randomUUID().toString()
				.replaceAll("-", "")));
	}
}
