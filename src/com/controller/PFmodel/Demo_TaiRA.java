package com.controller.PFmodel;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;

/**
 * ̩�ڵ�demo 
 *  ����������Ե�ַ��
	http://120.26.74.189:5090/dmp/frame
	kjrz/kjrz123456
 */
public class Demo_TaiRA {
	private static String prepareParam(Customer customer) {
		JSONArray data = new JSONArray();
		List<Param> customParams = new ArrayList<Param>();
		List<Param> clazzParams = new ArrayList<Param>();
		Clazz custom = new Clazz();
		Clazz clazz = new Clazz();
		
		data.add(custom);
		data.add(clazz);
		
		custom.setName("��������");
		custom.setClazz("��������");
		custom.setVariables(customParams);
		
		clazz.setName("����");
		clazz.setClazz("java.util.HashMap");
		clazz.setVariables(clazzParams); //��Ӳ�����

		Param rs = new Param(); //��ӽ���ֶ�
		clazzParams.add(rs);
		rs.setName("pingfen");
		rs.setLabel("�ܷ�");
		rs.setType("String");
		
		Param age = new Param();
		customParams.add(age);
		age.setName("age");
		age.setLabel("����");
		age.setType("String");
		age.setDefaultValue(customer.getAge());
		
		Param is_marital_status  = new Param();
		customParams.add(is_marital_status );
		is_marital_status.setName("is_marital_status");
		is_marital_status.setLabel("���");
		is_marital_status.setType("String");
		is_marital_status.setDefaultValue(customer.getIs_marital_status());
		
		/*Param work_day = new Param();
		customParams.add(work_day);
		work_day.setName("work_day");
		work_day.setLabel("��������");
		work_day.setType("String");
		work_day.setDefaultValue(customer.getWork_day());*/
		
		Param education= new Param();
		customParams.add(education);
		education .setName("education");
		education .setLabel("�����̶�");
		education .setType("String");
		education .setDefaultValue(customer.getEducation());
		
		Param h_address= new Param();
		customParams.add(h_address);
		h_address .setName("h_address");
		h_address .setLabel("�������ڵ�");
		h_address .setType("String");
		h_address .setDefaultValue(customer.getH_address());
		
		Param is_credit= new Param();
		customParams.add(is_credit);
		is_credit.setName("is_credit");
		is_credit.setLabel("�Ƿ�ʧ����");
		is_credit.setType("String");
		is_credit.setDefaultValue(customer.getIs_credit());
		
		Param nature_of_Business= new Param();
		customParams.add(nature_of_Business);
		nature_of_Business.setName("nature_of_Business");
		nature_of_Business.setLabel("��˾����(����/���У���Ӫ��ҵ������)");
		nature_of_Business.setType("String");
		nature_of_Business.setDefaultValue(customer.getNature_of_Business());

		

		Param is_surety= new Param();
		customParams.add(is_surety);
		is_surety .setName("is_surety");
		is_surety .setLabel("�Ƿ��е�����");
		is_surety .setType("String");
		is_surety .setDefaultValue(customer.getIs_surety());
		
		Param high_profile= new Param();
		customParams.add(high_profile);
		high_profile .setName("high-profile");
		high_profile .setLabel("�߶���ʿ(ְҵ)");
		high_profile .setType("String");
		high_profile .setDefaultValue(customer.getHigh_profile());
		
		
		Param max_credit = new Param();
		customParams.add(max_credit);
		max_credit.setName("max_credit");
		max_credit.setLabel("���ÿ���߶��");
		max_credit.setType("String");
		max_credit.setDefaultValue(customer.getMax_credit());
		
		Param court_execution = new Param();
		customParams.add(court_execution);
		court_execution .setName("court_execution");
		court_execution .setLabel("��Ժ��ִ�����");
		court_execution .setType("String");
		court_execution .setDefaultValue(customer.getCourt_execution());
		
		Param count_credit  = new Param();
		customParams.add(count_credit);
		count_credit.setName("count_credit");
		count_credit.setLabel("���ÿ�����");
		count_credit.setType("String");
		count_credit.setDefaultValue(customer.getCount_credit());
		
	
		
		Param is_two_house= new Param();
		customParams.add(is_two_house);
		is_two_house .setName("is_two_house");
		is_two_house .setLabel("�Ƿ��еڶ��׷���");
		is_two_house .setType("String");
		is_two_house .setDefaultValue(customer.getIs_two_house());
		
		Param is_two_car= new Param();
		customParams.add(is_two_car);
		is_two_car .setName("is_two_car");
		is_two_car .setLabel("�Ƿ�ڶ���������");
		is_two_car .setType("String");
		is_two_car .setDefaultValue(customer.getIs_two_car());
		
		Param is_ife_insurance= new Param();
		customParams.add(is_ife_insurance);
		is_ife_insurance.setName("is_ife_insurance");
		is_ife_insurance .setLabel("�Ƿ������ٱ���");
		is_ife_insurance .setType("String");
		is_ife_insurance .setDefaultValue(customer.getIs_ife_insurance());

		Param seven_days= new Param();
		customParams.add(seven_days);
		seven_days .setName("seven_days");
		seven_days .setLabel("����");
		seven_days .setType("String");
		seven_days .setDefaultValue(customer.getSeven_days());
		
		Param one_month= new Param();
		customParams.add(one_month);
		one_month .setName("one_month");
		one_month .setLabel("һ����");
		one_month .setType("String");
		one_month .setDefaultValue(customer.getOne_month());
		
		Param six_month= new Param();
		customParams.add(six_month);
		six_month .setName("six_month");
		six_month .setLabel("������");
		six_month .setType("String");
		six_month .setDefaultValue(customer.getSix_month());
		
		Param three_month= new Param();
		customParams.add(three_month);
		three_month .setName("three_month");
		three_month .setLabel("������");
		three_month .setType("String");
		three_month .setDefaultValue(customer.getThree_month());
		
		Param twelve_month= new Param();
		customParams.add(twelve_month);
		twelve_month .setName("twelve_month");
		twelve_month .setLabel("12����");
		twelve_month .setType("String");
		twelve_month .setDefaultValue(customer.getTwelve_month());
		
		Param uncleared_number= new Param();
		customParams.add(uncleared_number);
		uncleared_number.setName("uncleared_number");
		uncleared_number.setLabel("δ����������");
		uncleared_number.setType("String");
		uncleared_number.setDefaultValue(customer.getUncleared_number());
		
		Param uncleared_monty= new Param();
		customParams.add(uncleared_monty);
		uncleared_monty.setName("uncleared_monty");
		uncleared_monty.setLabel("δ���������");
		uncleared_monty.setType("String");
		uncleared_monty.setDefaultValue(customer.getUncleared_monty());
		
		Param highest_overdue= new Param();
		customParams.add(highest_overdue);
		highest_overdue.setName("highest_overdue");
		highest_overdue.setLabel("����������ڽ��");
		highest_overdue.setType("String");
		highest_overdue.setDefaultValue(customer.getHighest_overdue());
		return data.toJSONString();
	}
	public static String fun(Customer customer){
		HttpClient client = HttpClients.createDefault();
		RequestBuilder rb = RequestBuilder.post();// ��������
		// �����ڲ���
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					org.apache.http.HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity, Charset.forName("utf-8")) : null;
				} else {
					//
					org.apache.http.HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity, Charset.forName("utf-8")) : null;
				}
			}
		};
		// ���� org.apache.httpcomponents ? httpclient
		// ָ��SaaS�����URL
		rb.setUri("http://120.26.74.189:5090/dmp/packageeditor/doExceute");
		// �趨����
		rb.addHeader("apikey", "e92e23bb-dc35-da10-0cc0-658758ac70dc");// Ӧ�ó������Կ
		rb.addParameter("files", "jcr:/���/�������.sc,LATEST");// ��������Ĺ��򼯱�ʶ	    jcr:/���/��ӽ�������.rs.xml,LATEST
		rb.addParameter("assistParam", "{\"caseID\": \"ID123456\"}"); // ������Ϣ���� Map����
		// ��Http���ؽ����Ϊ�ַ�
		rb.addParameter("rulesetParam", Demo_TaiRA.prepareParam(customer));// ָ��ֵ����
		String responseStr=null;
		try {
			responseStr = client.execute(rb.build(), responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseStr;
	}
}





