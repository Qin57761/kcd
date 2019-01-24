package com.controller.duoying;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.model1.thjl;

import com.service1.duoying.thjlService;


import net.sf.json.JSONArray;

import net.sf.json.JSONObject;

@Controller
public class thjlController {
	
	@Autowired
	private thjlService thjlService;

	@RequestMapping(value="findthjl.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findquerythjl(){
		List<thjl> thjl = new ArrayList<thjl>();
		List<JSONObject> list = new ArrayList<>();
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String,Object>> maplist=new ArrayList<Map<String,Object>>();
		thjl = thjlService.findthjl();
		thjl th = new thjl();
		for(int i= 0;i<thjl.size();i++){
			th = thjl.get(i);
		Map<String,Object> thmap=new HashMap<String,Object>();
		thmap.put("id", th.getId());
		thmap.put("mid_add", th.getMid_add());
		thmap.put("mid_edit", th.getMid_edit());
		thmap.put("dt_add", th.getDt_add());
		thmap.put("dt_edit", th.getDt_edit());
		thmap.put("bc_status", th.getBc_status());
		thmap.put("quert_type", th.getQuery_type());
		thmap.put("gems_id", th.getGems_id());
		thmap.put("gems_fs_id", th.getGems_fs_id());
		thmap.put("c_name", th.getC_name());
		thmap.put("c_tel", th.getC_tel());
		thmap.put("c_cardno", th.getC_cardno());
		thmap.put("gems_code", th.getGems_code());
		thmap.put("api_status", th.getApi_status());
		thmap.put("api_token", th.getApi_token());
		//thmap.put("api_note", th.getApi_note());
		maplist.add(thmap);
        System.out.println(th.getApi_note());
		
		}
		
		
	

		String jsonStr = "";
		jsonStr = th.getApi_note();
		System.out.println("jsonStr"+jsonStr);
		Map<Object, Object> result = jsonToMap(jsonStr);
		//��һ������
		System.out.println("���"+result.get("_id"));
		System.out.println(result.get("basicInfo"));//
		System.out.println(result.get("callRecordsInfo"));//
		System.out.println(result.get("consumeInfo"));
		System.out.println(result.get("contactAreaInfo"));
		System.out.println(result.get("deceitRisk"));
		System.out.println(result.get("messageRecordsInfo"));
		System.out.println(result.get("phoneInfo"));
		System.out.println(result.get("phoneOffInfos"));
		System.out.println("�ֻ����룺"+result.get("phone_no"));
		System.out.println(result.get("specialCallInfo"));
		System.out.println("token"+result.get("token"));
		
		//�ڶ�������toArrayList
		
        Map<Object, Object> id = jsonToMap(result.get("_id"));//
		System.out.println(id.get("$oid"));
		
/*		Map<Object, Object> phoneno = jsonToMap(result.get("phone_no"));//�ֻ���
		System.out.println(phoneno.get("phone_no"));
		
		Map<Object, Object> token = jsonToMap(result.get("token"));
		System.out.println(token.get("token"));*/
		
		
		
		
		Map<Object,Object> basicInfo = jsonToMap(result.get("basicInfo"));//������Ϣ
		System.out.println(basicInfo.get("age"));//����
		System.out.println(basicInfo.get("birthArea"));//��������
		System.out.println(basicInfo.get("birthday"));//������
		System.out.println(basicInfo.get("certNo"));//���֤����
		System.out.println(basicInfo.get("phoneBelongArea"));//�ֻ��Ź�����
		System.out.println("�绰���룺"+basicInfo.get("phoneNo"));//�Ǽ��ֻ���
		System.out.println(basicInfo.get("reportID"));//������
		System.out.println(basicInfo.get("reportTime"));//����ʱ��
		System.out.println(basicInfo.get("sex"));//����
		
		Map<Object,Object> phoneInfo = jsonToMap(result.get("phoneInfo"));//��Ӫ�̻�����Ϣ
		System.out.println(phoneInfo.get("addr"));//�Ǽǵ�ַ
		System.out.println(phoneInfo.get("balance"));//��ǰ���
		System.out.println(phoneInfo.get("certNo"));//��֤ʡ��֤��
		System.out.println(phoneInfo.get("email"));//�Ǽ�����
		System.out.println(phoneInfo.get("firstCallDate"));//����ͨ��ʱ��
		System.out.println(phoneInfo.get("inNetDate"));//����ʱ��
		System.out.println(phoneInfo.get("lastCallDate"));//���ͨ��ʱ��
		System.out.println(phoneInfo.get("netAge"));//����
		System.out.println(phoneInfo.get("operator"));//��Ӫ������
		System.out.println(phoneInfo.get("phoneNo"));//�ֻ���
		System.out.println(phoneInfo.get("pointValue"));//����ֵ
		System.out.println(phoneInfo.get("realName"));//��֤ʵ��
		System.out.println(phoneInfo.get("vipLevel"));//��Ա�ȼ�
		JSONObject one6 = new JSONObject();
	        one6.put("operator", phoneInfo.get("operator"));// ö��< PhoneServiceProviders > �� ��Ӫ������
	        // 0 �ƶ�
	        // 1 ��ͨ
	        // 2 ����
	        // 3 ����
	        one6.put("netInTime", phoneInfo.get("inNetDate"));// DateTime �� ����ʱ��
	        one6.put("autonym", phoneInfo.get("realName"));// String �� ʵ����֤
	        one6.put("phone", phoneInfo.get("phoneNo"));// String �� �ֻ�����
	        one6.put("email", phoneInfo.get("email"));// String �� �Ǽ�����
	        one6.put("balance", phoneInfo.get("balance"));// Decimal �� ��ǰ���
	        one6.put("grade", phoneInfo.get("vipLevel"));// Int �� ��Ա�ȼ�
	        one6.put("integral", phoneInfo.get("pointValue"));// Int �� ����ֵ
	        one6.put("netAge", phoneInfo.get("netAge"));// Int �� ����
	        one6.put("beginCallTime", phoneInfo.get("firstCallDate"));// DateTime �� ����ͨ��ʱ��
	        one6.put("latelyCallTime", phoneInfo.get("lastCallDate"));// DateTime �� ���ͨ��ʱ��
	        one6.put("regAddress", phoneInfo.get("addr"));// String �� �Ǽǵ�ַ
	        one6.put("remark", "remark");
		   list.add(one6);
	        
	        
		Map<Object, Object> deceitRisk = jsonToMap(result.get("deceitRisk"));
		System.out.println(deceitRisk.get("calledByCourtNo"));//�Ƿ���ַ�Ժ��غ������
		System.out.println(deceitRisk.get("certNoIsValid"));//���֤������Ч��
		System.out.println(deceitRisk.get("emergency_contacted"));//�Ƿ���ϵ��������ϵ��
		System.out.println(deceitRisk.get("inBlacklist"));//��������Ϣ�Ƿ���������������
		System.out.println(deceitRisk.get("longTimePowerOff"));//�Ƿ���ֹ���ʱ��ػ�
		System.out.println(deceitRisk.get("phoneIsAuth"));//��Ӫ���Ƿ�ʵ��
		System.out.println(deceitRisk.get("samePeople"));//��Ӫ��ʵ���Ƿ���Ǽ���һ��
		JSONObject one3 = new JSONObject();
	        one3.put("court", deceitRisk.get("calledByCourtNo"));// ö��<YN> �� �Ƿ���ַ�Ժ��غ������
	        one3.put("valid", deceitRisk.get("certNoIsValid"));// ö��<YN> �� ���֤������Ч��
	        one3.put("urgency", deceitRisk.get("emergency_contacted"));// ö��<YN> �� �Ƿ���ϵ��������ϵ��
	        one3.put("blackList", deceitRisk.get("inBlacklist"));// ö��<YN> �� ��������Ϣ�Ƿ���������������
	        one3.put("shutdown", deceitRisk.get("longTimePowerOff"));// ö��<YN> �� �Ƿ���ֳ�ʱ��ػ�
	        one3.put("reality", deceitRisk.get("phoneIsAuth"));// ö��<YN> �� ��Ӫ���Ƿ�ʵ��
	        one3.put("accordance", deceitRisk.get("samePeople"));// ö��<YN> �� ��Ӫ��ʵ���Ƿ���Ǽ���һ��
	        one3.put("remark", "remark");
		    list.add(one3);
		
		List<Map<Object, Object>> callRecordsInfo = toList(result.get("callRecordsInfo"));//ͨ����¼����
		for(int a=0;a<callRecordsInfo.size();a++){
	    Map<Object, Object> call=new HashMap<Object,Object>();
	    call= callRecordsInfo.get(a);
	    System.out.println(call.get("belongArea"));//���������
		System.out.println(call.get("callTimes"));//���д���
		System.out.println(call.get("calledTimes"));//���д���
		System.out.println(call.get("connTime"));//ͨ��ʱ��
		System.out.println(call.get("connTimes"));//ͨ������
		System.out.println(call.get("identifyInfo"));//�����ʶ
		System.out.println("���룺"+call.get("phoneNo"));//����
		JSONObject one5 = new JSONObject();
        one5.put("callPhone", call.get("phoneNo"));// String �� ͨ������
        one5.put("callTime", call.get("connTime"));// int �� ͨ��ʱ��
        one5.put("callNumber", call.get("connTimes"));// int �� ͨ������
        one5.put("calling", call.get("callTimes"));// int �� ����
        one5.put("called", call.get("calledTimes"));// int �� ����
        one5.put("phoneAddress", call.get("belongArea"));// String �� ������޵�
        one5.put("remark", "remark");
        list.add(one5);
		}
		

		List<Map<Object, Object>> consumeInfo = toList(result.get("consumeInfo"));//��Ӫ�����ѷ���
		for(int b=0;b<consumeInfo.size();b++){
	    Map<Object, Object> consume=new HashMap<Object,Object>();
	    consume= consumeInfo.get(b);
	    System.out.println(consume.get("callTime"));//����ʱ��
		System.out.println(consume.get("calledTime"));//����ʱ��
		System.out.println(consume.get("month"));//�·�
		System.out.println(consume.get("payMoney"));//���ѳ�ֵ��
		System.out.println(consume.get("totalSmsNumber"));//������
		JSONObject one2 = new JSONObject();
        one2.put("month", consume.get("month"));// Int �� �·�
        one2.put("callingTime", consume.get("callTime"));// Int �� ����ʱ��
        one2.put("calledTime", consume.get("calledTime"));// Int �� ����ʱ��
        one2.put("noteNumber", consume.get("totalSmsNumber"));// Int �� ������
        one2.put("balance", consume.get("payMoney"));// Decimal �� ���ѳ�ֵ��
        one2.put("remark", "remark");
        list.add(one2);
		
		}
		
		
		List<Map<Object, Object>> contactAreaInfo = toList(result.get("contactAreaInfo"));//��ϵ��λ�÷���
		for(int c=0;c<contactAreaInfo.size();c++){
	    Map<Object, Object> contact=new HashMap<Object,Object>();
	    contact= contactAreaInfo.get(c);
	    System.out.println(contact.get("area"));//����
		System.out.println(contact.get("callTime"));//����ʱ��
		System.out.println(contact.get("callTimes"));//���д���
		System.out.println(contact.get("calledTime"));//����ʱ��
		System.out.println(contact.get("calledTimes"));//���д���
		System.out.println(contact.get("percent"));//ռ��
		System.out.println(contact.get("totalNumber"));//��������
		
		
        JSONObject one = new JSONObject();
        one.put("region", contact.get("area"));// String �� ����
        one.put("phoneNumber", contact.get("totalNumber"));// int �� ��������
        one.put("callingNumber", contact.get("callTimes"));// int �� ���д���
        one.put("callingTime", contact.get("callTime"));// int �� ����ʱ��
        one.put("calledNumber", contact.get("calledTimes"));// int �� ���д���
        one.put("calledTime", contact.get("calledTime"));// int �� ����ʱ��
        one.put("percentage", contact.get("percent"));// int �� ռ��
        one.put("remark", "remark");
        list.add(one);
		}
		
		List<Map<Object, Object>> messageRecordsInfo = toList(result.get("messageRecordsInfo"));//���ż�¼����
		for(int d=0;d<messageRecordsInfo.size();d++){
	    Map<Object, Object> message=new HashMap<Object,Object>();
	    message= messageRecordsInfo.get(d);
	    System.out.println(message.get("belongArea"));//���������
		System.out.println(message.get("identifyInfo"));//�����ʶ
		System.out.println(message.get("phoneNo"));//����
		System.out.println(message.get("totalSmsNumber"));//����
		
		
	        JSONObject one1 = new JSONObject();
	        one1.put("notePhone", message.get("phoneNo"));// string �� ����
	        one1.put("noteNumber", message.get("totalSmsNumber"));// int �� ��������
	        one1.put("noteAddress", message.get("belongArea"));// string �� ���������
	        one1.put("remark", "remark");
	        list.add(one1);
		
		}
		
		List<Map<Object, Object>> phoneOffInfos = toList(result.get("phoneOffInfos"));//�ػ�����
		for(int e=0;e<phoneOffInfos.size();e++){
	    Map<Object, Object> phoneOff=new HashMap<Object,Object>();
	    phoneOff= phoneOffInfos.get(e);
	    System.out.println(phoneOff.get("beginDate"));//�ػ���ʼ����
		System.out.println(phoneOff.get("days"));//�ػ�����
		System.out.println(phoneOff.get("endDate"));//�ػ���������
		JSONObject one4 = new JSONObject();
        one4.put("beginTime", phoneOff.get("beginDate"));// DateTime �� ��ʼ����
        one4.put("overTime",phoneOff.get("endDate") );// DateTime �� ��������
        one4.put("countDay", phoneOff.get("days"));// int �� ����
        one4.put("remark", "remark");
        list.add(one4);
		
		
		}
		
		
		List<Map<Object, Object>> specialCallInfo = toList(result.get("specialCallInfo"));//ͨ�������������
		for(int f=0;f<specialCallInfo.size();f++){
	    Map<Object, Object> special=new HashMap<Object,Object>();
	    special= specialCallInfo.get(f);
	    System.out.println(special.get("connTimes"));//ͨ������
		System.out.println(special.get("identityInfo"));//�Է���ʶ
		System.out.println(special.get("month"));//�·�
		System.out.println(special.get("phoneNo"));//�Է�����
		System.out.println(special.get("smsTimes"));//��������
		
		}
		map.put("list",list);
		maplist.add(map);
		return maplist.toString();
		}



   

	public static Map<Object, Object> jsonToMap(Object jsonObj) {
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        Map<Object, Object> map = jsonObject;
        return map;
    }
	
	public static List<Map<Object, Object>> toList(Object object)
    {
        List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
        JSONArray jsonArray = JSONArray.fromObject(object);
        for (Object obj : jsonArray)
        {
            JSONObject jsonObject = (JSONObject) obj;
            Map<Object, Object> map = new HashMap<Object, Object>();
            Iterator it = jsonObject.keys();
            while (it.hasNext())
            {
                String key = (String) it.next();
                Object value = jsonObject.get(key);
                map.put(key, value);
            }
            list.add(map);
        }
        return list;
    }
	
	
}
