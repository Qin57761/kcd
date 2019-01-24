package com.controller.erp_icbc.YunXin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.controller.erp_icbc.YunXin.seats.PoolCache1;
import com.controller.erp_icbc.YunXin.seats.SP;
import com.controller.erp_icbc.YunXin.seats.ScanPool1;
import com.controller.erp_icbc.YunXin.seats.Seats;
import com.controller.erp_icbc.base.BaseController;
import com.controller.erp_icbc.result.Result;
import com.controller.erp_icbc.utils.EmptyUtil;
import com.controller.erp_icbc.utils.PageInfo;
import com.model1.icbc.erp.PageData;
import com.service1.erp_icbc.YXService;
import com.util.creditutil;
import com.util.duoying.MD5;
/**
 * @Description:TODO
 * @author:LiWang
 * @time:2018��8��22��
 */
@Controller
@RequestMapping("yx/")
public class YunXinController extends BaseController{
	public YunXinController(){
		super();
	};
	@Autowired
	private YXService yx;
	/**
	 * @param icbcid ����id �����д�״̬�Ƿ���� �Ͳ�ѯ�û���������Ϣ��������Ϣ
	 * @param through ͨ�����߲�ͨ��
	 */
	@RequestMapping(value="sutitstatus.do")
	@ResponseBody
	public void auditStatus(String icbcid,String auditstatus,String channel,HttpServletRequest request){
		Map map=autioMonth(icbcid,auditstatus,channel,request);
		//System.out.println("����һ����Ƶ��Ϣ"+JSON.toJSONString(map));
		/*����һ�´�result��������ǩ��Ϣ*/
		yx.update_infocopy_viedo_vid(map);
		//�������ͨ��  25��С״̬
		if(auditstatus.equals("1")){
			//�����һ����ɵ�С״̬
			map.put("status",25);
			map.put("remark","���");
			map.put("resultmsg","���");
			//System.out.println("���һ����ɵ�״̬"+JSON.toJSONString(map));
			yx.insert_kjicbcresult(map);//���һ����ɵ�С״̬
		}
	}
	/**	��ȡ��������
	 * @param icbcid ��˵�id
	 * auditstatus   ͨ�����߲�ͨ�� 0����ͨ�� ����ͨ��
	 * @param request ������
	 */
	public Map  autioMonth(String icbcid,String auditstatus,String channel,HttpServletRequest request){
		String parentid = yx.select_kjicbc_byid("6",icbcid);//��ѯ��Ӧ����Ƶ��ǩ��״̬�Ƿ����
		Map map=new HashMap<>();
		map.put("channel", channel);
		//ͨ�õ�ֱ�Ӹ�ֵ��
		map.put("addadmin","121");//pdsession.get("id").toString();//��õ�ǰ��¼�û�������
		map.put("editadmin","121");//�޸���
		map.put("addtime",creditutil.time());//�½�ʱ��
		map.put("editime",creditutil.time());//���༭ʱ��
		map.put("sub",creditutil.time());//�ύʱ��
		map.put("icbcid",icbcid);
		map.put("typeid", 6);
		if(auditstatus.equals("1")){//������ͨ��
			yx.updata_mq_status("3",icbcid);//������ǩ��״̬ 3�������ͨ��
			map.put("status","25");//25�������
			map.put("resultmsg","��ǩͨ��");
		}else if(auditstatus.equals("3")){//����
			yx.updata_mq_status("4",icbcid);//��ǩ��ͨ��
			map.put("status","24");
			map.put("resultmsg","��ǩ��ͨ��");
		}
		if(parentid==null){//�����ڴ�״̬  �����
			//��ѯ��Ϣ
			Map icbc = yx.select_icbc_byid(icbcid);
			Map car = yx.select_car_byid(icbcid);
			map.put("c_name", icbc.get("c_name").toString());//����
			map.put("gems_id", icbc.get("gems_id").toString());//ҵ��Ա
			map.put("gems_fs_id",icbc.get("gems_fs_id").toString());//����
			map.put("c_cardno", icbc.get("c_cardno").toString());//����
			map.put("vincode",car.get("vincode").toString());//vin��
			map.put("code", car.get("code").toString());//����
			//���һ����״̬
			//System.out.println("���һ����״̬"+JSON.toJSONString(map));
			yx.insert_kjicbc(map);
			map.put("parentid",map.get("id"));//��������һ��result�ĸ�����id
		}else{
			//����һ�´�״̬ 
			//System.out.println("����һ����״̬"+JSON.toJSONString(map));
			yx.update_kjicbc(map);//����icbcId �� typeid ��Ҫ�޸�һ�����µĠ�B ��������r�g �� �����������
			map.put("parentid",parentid);//��������һ��result�ĸ�����id
		}	
		//����һ��24С״̬ �������
		//System.out.println("��״̬id"+ map.get("parentid").toString());
		map.put("status",24);
		map.put("remark","�������");
		map.put("resultcode",auditstatus);//1ͨ�� 3����
		//System.out.println("���һ��С״̬"+JSON.toJSONString(map));
		yx.insert_kjicbcresult(map);//���һ��С״̬
		return map;
	}
	/**һϵ�е���Ϣ �����Ƶ��ǩ��û�е��ͨ�����߲�ͨ�� ��ô��������߼��Ͳ���ִ�� ��˵��߼����ڲ鿴��ʷ�����е� �мǣ�����
	 * @param auditstatus ���״̬
	 * @param customvalue icbc_id address��
	 * @param chanelid ͨ��id
	 * @param request 
	 * @Description: TODO
	 * @param name
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="viedoAudit.do")
	@ResponseBody
	public void viedoAudit(String auditstatus, String customvalue,String channel,HttpServletRequest request) throws UnsupportedEncodingException{
		//System.out.println("�������:"+auditstatus+customvalue+" "+channel);
		JSONObject custom = JSONObject.parseObject(customvalue);
		String icbcid=custom.getString("id");
		Map map=autioMonth(icbcid,auditstatus,channel,request);
		//��С��Ƶ�Ĳ���
		map.put("address", custom.getString("address"));
		if(yx.select_infocopy(channel)!=null){//��ǩ��
			//����
			//System.out.println("����һ����Ƶ��Ϣ"+JSON.toJSONString(map));
			yx.update_infocopy_viedo(map);
		}else{
			//���
			//System.out.println("���һ����Ƶ��Ϣ"+JSON.toJSONString(map));
			yx.insert_infocopy_viedo(map);
		}
		
		//�������ͨ��  25��С״̬
		if(auditstatus.equals("1")){
			//�����һ����ɵ�С״̬
			map.put("status",25);
			map.put("remark","���");
			map.put("resultmsg","���");
			//System.out.println("���һ����ɵ�״̬"+JSON.toJSONString(map));
			yx.insert_kjicbcresult(map);//���һ����ɵ�С״̬
		}
	}
		//��ҳ��ѯ��ʷ
		@RequestMapping(value="selectOperatingFalse.do")
		@ResponseBody
		public PageInfo selectOperatingFalse(Integer pagesize,
				Integer pageIndex,
				String name,
				String idNumber,
				String organization, //����������
				String viedostartsvalue,//���״̬ 1����� 2δ���
				String viedotype,//��Ƶ���� 1��Ƶ��ǩ 2��Ƶ¼��
				String contract,//ǩԼ״̬ 1�ɹ� 3����
				HttpServletRequest request) throws UnsupportedEncodingException{ //ǩԼ״̬
			request.setCharacterEncoding("utf-8");
			String  s = new String(request.getParameter("name").getBytes("ISO-8859-1"),"utf-8");
			//System.out.println("����"+s);
			//System.out.println(pagesize+"��ҳ�룺"+pageIndex);
			//System.out.println("����:"+name+",��ݣ�"+idNumber+",������"+organization+",��Ƶ�����ͣ�"+viedotype+",״̬��"+contract+",���״̬��"+viedostartsvalue);
			Map map=new HashMap();
			//��ѯ����
			if(!EmptyUtil.isEmpty(idNumber) ){//���֤
				map.put("idNumber", "%"+idNumber+"%");
			}
			map.put("viedostartsvalue",viedostartsvalue);
			map.put("organization",organization);//����
			map.put("contract", contract);
			if(!EmptyUtil.isEmpty(name)){//����
				map.put("name", "%"+s+"%");
			}
			map.put("viedotype",viedotype);
			
			PageInfo pageinfo=new PageInfo(pageIndex,pagesize);
			if(map.size()>0){
				pageinfo.setCondition(map);
			}		
			pageinfo.setRows(yx.select_operating(pageinfo));//����
			pageinfo.setTotal(yx.select_operating_count(pageinfo));//������
			//System.out.println(JSON.toJSONString(pageinfo));
			return pageinfo;
		}

	/*�����û�����Ϣ*/
	@RequestMapping(value="viedoinfo.do")
	@ResponseBody
	public Map selectViedobyid(String id,String domvalue){
		System.out.println(id+"  "+domvalue);
		Map map=null;
		List select_mq_info=null;
		String icbcid="-1";
		if(domvalue.equals("A")){//��Ƶ�Ի� idΪicbc_id
			 map= yx.select_viedo_byid(id);
			 icbcid=id;
		}else if(domvalue.equals("B")){
			 map=(Map) yx.select_viedo_byid2(id).get(0);
			 icbcid=map.get("icbcid").toString();
		}
		select_mq_info = yx.select_mq_info(icbcid);//��  �����ǩֻȡ��һ��
		if(select_mq_info.size()>0){
			 map.putAll((Map)select_mq_info.get(0));//��ӵ�������
		 }
		System.out.println("���:"+JSON.toJSONString(map));
		return map;
	}
	/**�ϴ��ɹ��Ļص���ַ
	 * @param callback
	 * @Description: TODO
	 * @param name
	 * ����ʾ��:
	 * $.ajax({
	        type: "POST",
	        url: "${pageContext.request.contextPath}/yx/callback.do",
	        dataType: "json",
	        data:{name:'snow.mp4',origAddr:'http://vodk32ywxdf.vod.126.net/vodk32ywxdf/b3d259f4-a7bc-4119-ae24-60e0eb09216e.mp4',type:'upload',vid:1022,user_defined:'userId=123456'},
	        success: function(date){	
	        }	
	      });
	 */
	@RequestMapping(value="callback.do")
	@ResponseBody
	public void callBack(HttpServletRequest request){
			String body="";
			try {
				body = readBody(request);
				StringBuilder redundant=new StringBuilder(body.replaceAll("(\\})|(\\{)|(\\[)|(\\])|(\")", ""));//���͵�������Ϣ
	    		//�ַ�������
	    		body=body.replaceAll("\\\\", "").replaceAll("\"\\{","{").replaceAll("\\}\"","}").replaceAll("\"\\[\\{", "[{").replaceAll("\\}\\]\"", "}]");
	    		JSONObject map = JSONObject.parseObject(body);
				//�Զ���ͨ��id ������ǰ�˵Ĳ�������ʹ��ͨ��id Ҳ����ʹ��������id
	    		map.put("channelid",MD5.sign(UUID.randomUUID().toString().replace("-", "").toLowerCase(),"utf-8"));
	    		map.put("viedotype", 0);
	    		map.put("te", redundant.toString());
				yx.addcallback(map);
			} catch (Exception e) {
				//yx.insert_M(body);//�������ֱ�ӱ���
			}
	}
	@RequestMapping(value="selectvideo.do")
	@ResponseBody
	public Result selectvideo(){
		return renderSuccess(yx.select_YX_video());
	}

	//�����ϴ��ɹ���Ļص���ַ {"ret":{},"requestId":"vodc59a1e37-9654-4314-89fb-406b5661086f","code":200}
	public static String setCallBack(){
		Map map=new HashMap(2);
		map.put("callbackUrl","http://apitest.kcway.net/kcd/yx/callback.do");
		map.put("signKey","kjs9999");
		return HttpYX.doPost(YXConstant.SetCallBack,map);
	}
	/**�����ļ��ϴ��ĳ�ʼ������ȡxNosToken���ϴ�ƾ֤����bucket���洢�����Ͱ������object�����ɵ�Ψһ����������
	 * @param originFileName �ϴ��ļ���ԭʼ���ƣ�������׺����
	 * @param userFileName �û��������ϴ��ļ�����
	 * @param typeId ��Ƶ���������Id������дΪĬ�Ϸ��ࣩ
	 * @param presetId ��Ƶ����ת��ģ��Id������дΪĬ��ģ�壬Ĭ��ģ�岻����ת�룩
	 * @param uploadCallbackUrl �ϴ��ɹ���ص��ͻ��˵�URL��ַ�����׼http��ʽ��
	 * @param callbackUrl ת��ɹ���ص��ͻ��˵�URL��ַ�����׼http��ʽ��
	 * @param description �ϴ���Ƶ��������Ϣ
	 * @param watermarkId ��ƵˮӡId������дΪ�����ˮӡ�����ѡ���������ˮӡ��������ǰ���ˮӡͼƬ���ϴ��Ͳ��������ã��ұ�������prestId�ֶΣ���presetId�ֶβ�ΪĬ��ģ�壩
	 * @param userDefInfo �û��Զ�����Ϣ���ص��᷵�ش���Ϣ�����Ȳ��ܳ���256�ַ���
	 * @param transOffset ��Ƶת�봦��ü���Ƶ����ʼλ�ã���λ���룩
	 * @param transDuration ��Ƶת�봦��ü���Ƶ����Ƶʱ������λ���룩
	 	ʾ����
	  	sbucket": "jdvod6ep5thqk",
        "xNosToken": "UPLOAD ab1856bb39044591939d7b94e1b8e5ee:Lvz8pD5w0VFDnr6DfS4DdUEh/Pjr6QlUbB5fp7SgOV8=:eyJCdWNrZXQiOiJqZHZvZDZlcDV0aHFrIiwiT2JqZWN0IjoiNjM0MzA2ZjctYWE4Mi00ZmE2LWFjM2QtOTZlMDcyNWUyZjBhLm1wNCIsIkV4cGlyZXMiOjE1NjU0OTM5MjgsIkNhbGxiYWNrVXJsIjoiaHR0cDovL3ZjbG91ZC4xNjMuY29tL3hoci92b2Qvbm9zL2NhbGxiYWNrIiwiQ2FsbGJhY2tCb2R5IjoiZmlsZU5hbWU9dGVzdC5tcDQmb2JqZWN0TmFtZT02MzQzMDZmNy1hYTgyLTRmYTYtYWMzZC05NmUwNzI1ZTJmMGEubXA0JiQoT2JqZWN0U2l6ZSkmdWlkPTEwMjMxMDAwOCZ0eXBlSWQ9MTA0MjY3NDY0JnByZXNldElkPTEwNDI4MjQyNCZ3YXRlcm1hcmtJZHM9MTAyMzY2NDQxJmRlc2NyaXB0aW9uPW51bGwmdHJhbnNjb2RlQ2FsbGJhY2s9bnVsbCYkKEFWaW5mby5WaWRlby5EdXJhdGlvbikmJChBVmluZm8uVmlkZW8uSGVpZ2h0KSYkKEFWaW5mby5WaWRlby5XaWR0aCkmMCYwJjAmbnVsbCZ1cGxvYWRTdGFydD0xNTMzOTU3OTI4MzQzJnVwbG9hZENhbGxiYWNrPW51bGwmbnVsbCZtcDQmMCYwJnRyYW5zT2Zmc2V0PW51bGwmdHJhbnNEdXJhdGlvbj1udWxsJmFwcGtleT05MDM5MmNkNDEzMGIzNmJlNTIzMjk5Y2M5YmJhYmVlOCIsIlJlZ2lvbiI6IkpEIn0=",
        "object": "634306f7-aa82-4fa6-ac3d-96e0725e2f0a.mp4"
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	@RequestMapping(value="UploadInit.do")
	@ResponseBody
	public Result getUploadInit(
			String originFileName,
			String userFileName,
			Integer typeId,
			Integer presetId,
			String uploadCallbackUrl,
			String callbackUrl,
			String description,
			Integer watermarkId,
			String userDefInfo,
			Integer transOffset,
			Integer transDuration){
			if(!EmptyUtil.isEmpty(originFileName)){
				JSONObject jsonobject=new JSONObject();
				jsonobject.put("originFileName", originFileName);
				jsonobject.put("userFileName", userFileName);
				jsonobject.put("typeId", typeId);
				jsonobject.put("presetId", presetId);
				jsonobject.put("uploadCallbackUrl", uploadCallbackUrl);
				jsonobject.put("callbackUrl", callbackUrl);
				jsonobject.put("description", description);
				jsonobject.put("watermarkId", watermarkId);
				jsonobject.put("userDefInfo", userDefInfo);
				jsonobject.put("transOffset", transOffset);
				jsonobject.put("transDuration", transDuration);
				return tokenDispose(HttpYX.doPost(YXConstant.InitUpload,jsonobject.toJSONString()),"1");
			}else{
				return renderError("�������ϴ��ļ���ԭʼ����(������׺��)!");
			}
	}
	/**���ݴ����accid����������ͨ��ID ���û�д���accid����ϵͳ����һ��32λ��accid
	 * @param accid �Զ���accid
	 * @param employcode ʹ������ 0 ������� 1����ͻ� 2�����ϴ�
	 * @param cfcode �������� 
	 */
	@RequestMapping(value="ct.do")
	@ResponseBody
	public Object createAccount(String accid,String employcode){
		if(EmptyUtil.isEmpty(accid)){
			accid=MD5.sign(UUID.randomUUID().toString().replace("-", "").toLowerCase(),"utf-8");
		}
		if(EmptyUtil.isEmpty(employcode) || (!employcode.equals("0") && !employcode.equals("1") && !employcode.equals("2"))){
			return renderError("��������ȷ������!");
		}else{
			Result result=null;
			if(employcode.equals("2")){//�ƶ��ϴ�sdk
				//System.out.println("��ȡ�ƶ��ϴ�");
				result=tokenDispose(HttpYX.geMobileUpload(accid),"1");
			}else{//������Ƶtoken
				//System.out.println("��ȡ����token");
				result=tokenDispose(HttpYX.getToken(accid),"0");
			}
			if(result.isSuccess()){//����ɹ���
				Map map=((Map) result.getData());
				map.put("employcode",employcode);//��������
				int i=yx.add_YX_account(map);//���� 
				if(i>0){//����ɹ�
					/*
					//��Ӻ��Ѵ��� ��Ƶ��ǰ����Ҫ��Ӻ���
					List list=null;
					if(employcode.equals("0")){//��ӵ�Ϊ���
						list=yx.query_token("1",-1);//��ô��ѯ���еĿͻ�
					}else if(employcode.equals("1")){//��ӵ�Ϊ�ͻ�
						list=yx.query_token("0", -1);//��ô��ѯ���е����
					}else{
						return result;
					}
					StringBuffer sb=new StringBuffer();
					//��ʼֱ����Ӻ���
					String s=null;
					String s1=map.get("accid").toString();;
					String s2=null;
					//ѭ����Ӻ��� 
					for(int j=0;j<list.size();j++){
						s=((Map)list.get(j)).get("accid").toString();//������
						s2=HttpYX.addBuddy(s,s1);
						if(s2.indexOf("200")==-1){
							sb.append(s1).append("��ӣ�").append(s).append("ʧ�ܣ�");
						}
					}
					result.setMessage(sb.toString());*/
					return result;
				}else{
					return renderError("����ʧ��:"+JSON.toJSONString(result.getData()));//����ʧ�ܷ���accid��token
				}
			}
			return result;//����ʧ�� ���߱���ʧ��
		}
	}
	/** ���� ����������ͨ��ID ���ؽ�� 
	 * @param s �ӿ��з��ص��ַ���
	 */
	public  Result tokenDispose(String s,String type){
		if(s!=null){//���������
			////System.out.println("�ӿڷ��ؽ����"+s);
			JSONObject jo = JSONObject.parseObject(s);//�ȴ���
			if(jo.getInteger("code")==200){//����ɹ�
				Map map =null;
				if(type.equals("0")){
					map=jo.getJSONObject("info");
				}else if(type.equals("1")){
					map=jo.getJSONObject("ret");
				}
				return renderSuccess(map);
			}else{//����ʧ�� 
				return renderError(jo.getString("code")+"!");//����ʧ�ܵ�״̬����� ����ȷ������ http://dev.netease.im/docs/product/%E9%80%9A%E7%94%A8/%E7%8A%B6%E6%80%81%E7%A0%81
			}		
		}
		return renderError("����ӿ�ʧ�ܣ�");
	}
	
	/**ֱ����Ӻ���
	 * @param accid  �Ӻ��ѷ�����accid
	 * @param faccid �Ӻ��ѽ�����accid
	 * http://localhost/kcd/yx/Buddy.do?accid=75dd5d18ee7c102b4aa3ff5c12a5936a&faccid=4a21effaf6827d9a312e628a929f6525
	 */
/*	@RequestMapping(value="Buddy.do")
	@ResponseBody
	public Object addBuddy(String accid,String faccid){
		Result result=new Result();
		if(EmptyUtil.isEmpty(accid) || EmptyUtil.isEmpty(faccid)){
			return renderError("��������ȷ�ķ����ߺͽ����ߣ�");
		}else{
			String s=HttpYX.addBuddy(accid,faccid);
			if(s!=null){
				if(s.indexOf("200")!=-1){//��ʾ�ɹ�
					return renderSuccess();
				}else{
					return renderError("���ʧ�ܣ�");
				}
			}else{
				return renderError("����ӿ�ʧ�ܣ�");
			}
		}
	}*/
	/**
	 *ռλ 
	 *http://localhost/kcd/yx/gcr.do
	 */
	@RequestMapping(value="occupy.do")
	@ResponseBody
	public Object occupy(){
		ScanPool1 scanpool = ((Seats)PoolCache1.Seats).aReduceBusy();
		if(scanpool!=null){
			return renderSuccess(scanpool);
		}else{
			return renderError("����û�����õ���Ƶͨ����ϯ,���Ժ����ԣ�");
		}
	}
	/**
	 *�ͷ�
	 */
	@RequestMapping(value="free.do")
	@ResponseBody
	public Object freeToken(String mark){
		if(!EmptyUtil.isEmpty(mark)){
			ScanPool1 aAddActive = ((Seats)PoolCache1.Seats).aAddActive(mark);
			if(aAddActive!=null){
				return renderSuccess(aAddActive);
			}
		}
		 return renderError("�ͷ�ʧ��!�����ڴ�mark��");
	}
	@RequestMapping(value="refreshtime.do")
	@ResponseBody
	public void  refreshTime(String mark){
		((Seats)PoolCache1.Seats).active.refreshTime(mark);
	}
	/**
		��½ �ɹ�����������ID
	 */
	@RequestMapping(value="login.do")
	@ResponseBody
	public ScanPool1 toLogin(HttpServletRequest request){
		//System.out.println("��½");
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");//��ȡsession��Ϣ
		String id=pdsession.get("id").toString();
		if(!EmptyUtil.isEmpty(id)){//����
			List list = yx.query_tokenbyid(id);
			if(list.size()>0){	
				ScanPool1 scanpool=(ScanPool1)list.get(0);
				ScanPool1 offer = ((Seats)PoolCache1.Seats).active.offer(scanpool);
				return offer;
			}
		}
		return null;
	}
	/*�˳�����*/
	@RequestMapping(value="outlogin.do")
	@ResponseBody
	public  void outLogin(String mark){
		//System.out.println("�˳�"+mark);
		SP sp=new SP();
		sp.setMark(mark);
		((Seats)PoolCache1.Seats).busy.remove(mark);
		((Seats)PoolCache1.Seats).active.delete(sp);//ֱ��ɾ��
	}	

	/**
	 *��ȡ���һ���ϴ�accid
	 */
	@RequestMapping(value="sur.do")
	@ResponseBody
	public Object setUploadReduce(){
		List list = yx.query_token("2",-1);
		Random random = new Random();
		int n = random.nextInt(list.size());
		return renderSuccess(list.get(n));
	}
	/**
	 * @param infocopy  ��Ϣ���� �Զ�����Ϣ
	 */
	@RequestMapping(value="infocopy.do")
	@ResponseBody
	public JSONObject infoCopy(HttpServletRequest request)
            throws Exception {
        JSONObject result = new JSONObject();
        try {
            // ��ȡ������
            String body = readBody(request);
            body=body.replaceAll("\\\\", "");
            if (EmptyUtil.isEmpty(body)) {//���Ϊnull ���߿��ַ�
                result.put("code", 414);
                return result;
            }else{
            	try {
            		StringBuilder redundant=new StringBuilder(body.replaceAll("(\\})|(\\{)|(\\[)|(\\])", ""));//���͵�������Ϣ
            		//�ַ�������
            		body=body.replaceAll("\"\\{","{").replaceAll("\\}\"","}").replaceAll("\"\\[\\{", "[{").replaceAll("\\}\\]\"", "}]");
            		//System.out.println(body);
            		JSONObject map = JSONObject.parseObject(body);
            		map.put("viedotype", "1");//������Ƶ������
            		String eventType = map.get("eventType").toString();
            			String channeid =null;
            			if(eventType.equals("5")){//����Ƶ/�װ�ʱ����Ϣ����
            				 channeid = yx.select_infocopy(map.get("channelId").toString());//��ȡͨ��id
            				 //JSONArray members  =JSONArray.parseArray(map.getString("members").toString().replaceAll("(^\"*)|(\"*$)","")); //����ַ��� ȥ����β��"�� ��ת��Ϊjsonarray
            				 JSONArray members=map.getJSONArray("members");
            				map.put("duration_time",redundant.toString());//������ͨ��ʱ������
            				 for(int i=0;i<members.size();i++){//ȷ���������ߺͽ�����
            					JSONObject members2=members.getJSONObject(i);
            					//�����ͨ���ķ����ߵĻ���caller�ֶ�Ϊtrue,������caller�ֶ�;         
            					if(members2.toString().indexOf("caller")==-1){//������ 
            						map.put("faccid", members2.getString("accid"));//������
            						if(channeid!=null){//���ڸ���channeid���� 
                    					//System.out.println("����");
                    					 yx.update_infocopy_durationM(map);
                    				 }else{//�����������
                    					 //System.out.println("����");
                    					 System.out.println(JSON.toJSONString(map));
                    					 yx.insert_infocopy_durationM(map);
                    				 }
            					}
            					//�������ǽ����� �򶪵����� 
            				 }	
            			}else if(eventType.equals("6")){//����Ƶ/�װ��ļ�������Ϣ���ͣ�
            					JSONArray fileinfo = map.getJSONArray("fileinfo");
            					 String url="";
            					map.put("download_info", redundant.toString());
            					for(int j=0;j<fileinfo.size();j++){
            						JSONObject fileinfo2 = fileinfo.getJSONObject(j);
            						//System.out.println("���ݣ�"+JSON.toJSONString(fileinfo2));
            						boolean b=fileinfo2.getBooleanValue("mix");
            						url=fileinfo2.getString("url");
            						if(b && url.indexOf("mp4")!=-1){//mix���Ƿ�Ϊ���¼���ļ���true�����¼���ļ���false������¼���ļ� ����Ϊmp4��ʽ
            							channeid = yx.select_infocopy(fileinfo2.getString("channelid"));//�ж��Ƿ����ͨ��id
            							map.put("fi", fileinfo2);//������ͬһ�γ��ͣ����ܻ᳭���㲻ͬchannel ID ����Ϣ��	
            							 if(channeid!=null){//���� �޸�
            								 //System.out.println("����");
            								 yx.update_infocopy_downloadM(map);
            							 }else{//�����������
            								 //System.out.println("����");
            								 //System.out.println("����:"+JSON.toJSONString(map));
            								 yx.insert_infocopy_downloadM(map);
            							 }
            						}
            					}
            				}
            		
				} catch (Exception e) {
					yx.insert_M(body);//�������ֱ�ӱ���
				}
            }
            // TODO: �Ƚ�md5��checkSum�Ƿ�һ�£��Լ�����ҵ����
            result.put("code", 200);
            return result;
        } catch (Exception ex) {
            result.put("code",414);
            return result;
        }
    }
    private String readBody(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
        if (request.getContentLength() > 0) {
        	BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			//ѭ����ȡ
			while ((temp = br.readLine()) != null) { 
			  sb.append(temp);
			}
			br.close();
            return  sb.toString();
        } else
            return null;
    }
	public static void main(String[] args){
		//��Ϣʱ�����Ͳ���
/*		InfoCopy infocopy=new InfoCopy();
		infocopy.setChannelId("62654898432013131");
		infocopy.setCreatetime(System.currentTimeMillis()+"");
		infocopy.setDuration(25+"");
		infocopy.setEventType(5+"");
		infocopy.setStatus("SUCCESS");
		infocopy.setExt("123");
		Members members=new Members();
		members.setAccid("2");
		members.setCaller(true);
		Members members1=new Members();
		members1.setAccid("3");
		List<Members> list=new ArrayList<>();
		list.add(members1);
		list.add(members);	
		infocopy.setMembers(list);
		
		//System.out.println(JSON.toJSONString(infocopy));

		
		//System.out.println(HttpYX.doPost("http://localhost:80/yx/infocopy.do",JSON.toJSONString(infocopy)));*/
		
		
		//ɾ������ ��ʼ
	/*	List list = yx.query_token("1",-1);
		String s;
		String s1 = "";
		String s2;//���շ��ؽ��
		StringBuilder sb=new StringBuilder();*/
		//ѭ��ɾ������ 
	/*	int i=0;
		for(int j=0;j<list.size();j++){
			s=((Map)list.get(j)).get("accid").toString();//������
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid",s));
			nvps.add(new BasicNameValuePair("faccid",s1));
			s2=HttpYX.doPost("https://api.netease.im/nimserver/friend/delete.action",nvps);
			if(s2.indexOf("200")==-1){
				sb.append(s1).append("ɾ����").append(s).append("ʧ�ܣ�");
				i++;
			}
		}
		//System.out.println("�ܵĺ�����:"+list.size()+",ʧ�ܸ�����"+i+" "+sb.toString());*/
		//ɾ�����ѽ���
		
		//��Ӻ���
		//System.out.println(HttpYX.addBuddy("789", "4a21effaf6827d9a312e628a929f6525"));
	
		//ѭ��ɾ������ ��ʼ
		//��ȡ���е����
		/*List list0 = yx.query_token("0",-1);
		//��ȡ���еĿͻ�
		List list = yx.query_token("1",-1);
		String s;
		String s1 = "";
		String s2;//���շ��ؽ��
		
		//ѭ��ɾ������ 
		for(int i=0;i<list0.size();i++){
			s1=((Map)list0.get(i)).get("accid").toString();
			int sum=0;
			StringBuilder sb=new StringBuilder();
			for(int j=0;j<list.size();j++){
				s=((Map)list.get(j)).get("accid").toString();//������
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("accid",s));
				nvps.add(new BasicNameValuePair("faccid",s1));
				s2=HttpYX.doPost("https://api.netease.im/nimserver/friend/delete.action",nvps);
				if(s2.indexOf("200")==-1){
					sb.append(s1).append("ɾ����").append(s).append("ʧ�ܣ�");
					sum++;
				}
			}
			//System.out.println(s1+"�ܵĺ�����:"+list.size()+",ʧ�ܸ�����"+sum+" "+sb.toString());
		}*/
		//ѭ��ɾ�����ѽ���
		String s="dfd'{dfd}''{";
		//System.out.println(s.replaceAll("\\'\\{","{"));
	}
}
