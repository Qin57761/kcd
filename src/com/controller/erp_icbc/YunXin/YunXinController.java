package com.controller.erp_icbc.YunXin;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.controller.erp_icbc.YunXin.seats.PoolCache1;
import com.controller.erp_icbc.YunXin.seats.ScanPool1;
import com.controller.erp_icbc.base.BaseController;
import com.controller.erp_icbc.base.RootStatic;
import com.controller.erp_icbc.result.Result;
import com.controller.erp_icbc.utils.EmptyUtil;
import com.controller.erp_icbc.utils.PageInfo;
import com.controller.htpdf.DataConversionParent;
import com.controller.htpdf.DoubleUtil;
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
	private static Logger log = LogManager.getLogger(YunXinController.class.getName());
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
		/*����һ�´�result��������ǩ��Ϣ*/
		int i0=yx.update_infocopy_viedo_vid(map);
		log.info("����С��¼����ǩ��Ϣ->update:"+i0);
		//�������ͨ��  25��С״̬
		if(auditstatus.equals("1")){
			//�����һ����ɵ�С״̬
			map.put("status",25);
			map.put("remark","���");
			map.put("resultmsg","���");
			int i=yx.insert_kjicbcresult(map);//���һ����ɵ�С״̬
			log.info("���һ����ɵ�С״̬->insert:"+i);
		}
	}
	//��ѯ���еĻ���
	@RequestMapping(value="cl.do")
	@ResponseBody
	public List getOrganization(){
		return yx.getOrganization();
	}
	//��ѯ���е�����
	@RequestMapping(value="getBank.do")
	@ResponseBody
	public List getBank(){
		return yx.getBank();
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
			int i=yx.updata_mq_status("3",icbcid);//������ǩ��״̬ 3�������ͨ��
			log.info("������ǩ��״̬(��ǩͨ��)->update"+i);
			map.put("status","25");//25�������
			map.put("resultmsg","��ǩͨ��");
		}else if(auditstatus.equals("3")){//����
			int i=yx.updata_mq_status("4",icbcid);//��ǩ��ͨ��
			log.info("������ǩ��״̬(��ǩ��ͨ��)->update"+i);
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
			int i=yx.insert_kjicbc(map);
			log.info("���һ��da״̬->"+i);
			map.put("parentid",map.get("id"));//��������һ��result�ĸ�����id
		}else{
			//����һ�´�״̬ 
			int i=yx.update_kjicbc(map);//����icbcId �� typeid ��Ҫ�޸�һ�����µĠ�B ��������r�g �� �����������
			log.info("����һ����״̬->"+i);
			map.put("parentid",parentid);//��������һ��result�ĸ�����id
		}	
		//����һ��24С״̬ �������
		map.put("status",24);
		map.put("remark","�������");
		map.put("resultcode",auditstatus);//1ͨ�� 3����
		int i=yx.insert_kjicbcresult(map);//���һ��С״̬
		log.info("���һ��С״̬(�������)->"+i);
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
		log.info("�������:"+auditstatus+" "+customvalue+" "+channel);
		JSONObject custom = JSONObject.parseObject(customvalue);
		String icbcid=custom.getString("id");
		Map map=autioMonth(icbcid,auditstatus,channel,request);
		//��С��Ƶ�Ĳ���
		map.put("address", custom.getString("address"));
		if(yx.select_infocopy(channel)!=null){//��ǩ��
			//����
			int i=yx.update_infocopy_viedo(map);
			log.info("����icbc_erp_video_info��->update:"+i);
		}else{
			//���
			int i=yx.insert_infocopy_viedo(map);
			log.info("���icbc_erp_video_info��->insert:"+i);
		}
		
		//�������ͨ��  25��С״̬
		if(auditstatus.equals("1")){
			//�����һ����ɵ�С״̬
			map.put("status",25);
			map.put("remark","���");
			map.put("resultmsg","���");
			int i=yx.insert_kjicbcresult(map);//���һ����ɵ�С״̬
			log.info("���С״̬icbc_erp_kj_icbc_result��->insert"+i);
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
				String bank,//����id
				HttpServletRequest request) throws UnsupportedEncodingException{ //ǩԼ״̬
			request.setCharacterEncoding("utf-8");
			String  s = new String(request.getParameter("name").getBytes("ISO-8859-1"),"utf-8");
			Map map=new HashMap();
			//��ѯ����
			if(!EmptyUtil.isEmpty(name)){//����
				map.put("name", "%"+s+"%");
			}
			if(!EmptyUtil.isEmpty(idNumber) ){//���֤
				map.put("idNumber", "%"+idNumber+"%");
			}
			if(!EmptyUtil.isEmpty(viedostartsvalue) ){
				map.put("viedostartsvalue",viedostartsvalue);
			}
			if(!EmptyUtil.isEmpty(organization) ){
				map.put("organization",organization);//����
			}
			if(!EmptyUtil.isEmpty(contract) ){
				map.put("contract", contract);
			}
			if(!EmptyUtil.isEmpty(viedotype) ){
				map.put("viedotype",viedotype);
			}
			if(!EmptyUtil.isEmpty(bank) ){
				map.put("bank",bank);
			}
			
			PageInfo pageinfo=new PageInfo(pageIndex,pagesize);
			if(map.size()>0){
				pageinfo.setCondition(map);
			}	
			log.info("��ҳ����->"+pageinfo);
			pageinfo.setRows(yx.select_operating(pageinfo));//����
			pageinfo.setTotal(yx.select_operating_count(pageinfo));//������
			return pageinfo;
		}

	/*�����û�����Ϣ*/
	@RequestMapping(value="viedoinfo.do")
	@ResponseBody
	public Object selectViedobyid(String id,String domvalue){
		Map map=null;
		List select_mq_info=null;
		String icbcid="-1";
		if(domvalue.equals("A")){//��Ƶ�Ի� idΪicbc_id
			 map= yx.select_viedo_byid(id);
			 log.info("ʵʱ��Ƶ����Ԫ����->{}"+JSON.toJSONString(map));
		}else if(domvalue.equals("B")){
			 map=(Map) yx.select_viedo_byid2(id).get(0);
			 log.info("�鿴��ʷ��Ƶ����Ԫ����->{}"+JSON.toJSONString(map));
		}
		 if(map.get("kk_car_stateid")!=null){
			map.put("kk_car_stateid",yx.getCommStates(Integer.parseInt(map.get("kk_car_stateid").toString()))); 
		 }
		 if(map.get("kk_car_cityid")!=null){
			map.put("kk_car_cityid",yx.getCommCitys(Integer.parseInt(map.get("kk_car_cityid").toString()))); 
		 }
		 if(map.get("kk_loan_stateid")!=null){
			map.put("kk_loan_stateid",yx.getCommStates(Integer.parseInt(map.get("kk_loan_stateid").toString()))); 
		 }
		 if(map.get("kk_loan_cityid")!=null){
			map.put("kk_loan_cityid",yx.getCommCitys(Integer.parseInt(map.get("kk_loan_cityid").toString()))); 
		 }
		 if(map.get("mem_states")!=null){
			map.put("mem_states",yx.getCommStates(Integer.parseInt(map.get("mem_states").toString()))); 
		 }
		 if(map.get("mem_citys")!=null){
			map.put("mem_citys",yx.getCommCitys(Integer.parseInt(map.get("mem_citys").toString()))); 
		 }
		 if(map.get("kk_kpj")!=null && Integer.parseInt(DataConversionParent.subZeroAndDot(map.get("kk_kpj").toString()))>0 &&  map.get("sfje")!=null){
			 map.put("sfbl",DataConversionParent.subZeroAndDot(DoubleUtil.mul(DoubleUtil.div( map.get("sfje").toString(),map.get("kk_kpj").toString(),4),"100")));
		 }	
		//������ͼƬ
		Object imgstep2_1ss=map.get("imgstep2_1ss");
		if(imgstep2_1ss!=null && !imgstep2_1ss.toString().equals("")){
			String ss[]=imgstep2_1ss.toString().split("");
			for(String s:ss){
				if(!s.equals("")){
					map.put("positive","http://a.kcway.net/assess/"+s);
					map.remove("img");
					break;
				}
			}
		}
		select_mq_info = yx.select_mq_info(map.get("id").toString());//��  �����ǩֻȡ��һ��
		if(select_mq_info.size()>0){
			 map.putAll((Map)select_mq_info.get(0));//��ӵ�������
		 }
		//�������صĵ�ַ �ȴӱ��������ٿ��Ǵ���������
		//����
		Object serverPath= map.get("serverPath");
		if(serverPath!=null && StringUtils.isNotBlank(serverPath.toString())){
			log.info("��������·���Ѿ�����");
			//http://a.kcway.net/assess/upload/0-50873460261080-0-mix.mp4
			String serverPath1=serverPath.toString();
			int i=serverPath1.indexOf("upload");
			if(i!=-1){
				map.put("downUrl",RootStatic.url+RootStatic.root_Directory+serverPath1.substring(i));
				//C:/Users/Administrator/Desktop/word/haha1/upload/0-50873460261080-0-mix.mp4
			}
		}else{
			log.info("ֻ�������ŵ�����·��");
			//����
			Object url=map.get("url");
			if(url!=null && StringUtils.isNotBlank(url.toString())){
				map.put("downUrl",RootStatic.url+url.toString());
				//http://localhost/kcd/yx/downloadClient.do?url=http://jdvod6ep5thqk.vod.126.net/jdvod6ep5thqk/0-50873460261080-0-mix.mp4
			}
		}
		log.info("�鿴��ʷ��Ƶ����ʵʱ��ǩ���ش�����û���Ϣ->{}"+JSON.toJSONString(map));
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
				log.info("�ϴ��ɹ��Ļص��ַ���->"+body);
				StringBuilder redundant=new StringBuilder(body.replaceAll("(\\})|(\\{)|(\\[)|(\\])|(\")", ""));//���͵�������Ϣ
	    		//�ַ�������
	    		body=body.replaceAll("\\\\", "").replaceAll("\"\\{","{").replaceAll("\\}\"","}").replaceAll("\"\\[\\{", "[{").replaceAll("\\}\\]\"", "}]");
	    		JSONObject map = JSONObject.parseObject(body);
				//�Զ���ͨ��id ������ǰ�˵Ĳ�������ʹ��ͨ��id Ҳ����ʹ��������id
	    		map.put("channelid",MD5.sign(UUID.randomUUID().toString().replace("-", "").toLowerCase(),"utf-8"));
	    		map.put("viedotype", 0);
	    		map.put("te", redundant.toString());
				int i=yx.addcallback(map);
				log.info("�ϴ��ɹ����->"+i);
			} catch (Exception e) {
				yx.insert_M(body+"----error:"+getErrorInfoFromException(e));//�������ֱ�ӱ���
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
			Integer transDuration,
			String id){
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
				
		
				String obj=HttpYX.doPost(YXConstant.InitUpload,jsonobject.toJSONString());
				Map map=new HashMap<>();
				map.put("icbcId", id);
				map.put("dataTime",creditutil.time());
				map.put("result", obj);
				map.put("describe","�ļ��ϴ���ʼ��");
				int i=yx.addOccupyTest(map);
				log.info("��ӵ��ü�¼->addCount:+"+i);
				return tokenDispose(obj,"1");
			}else{
				return renderError("�������ϴ��ļ���ԭʼ����(������׺��)!");
			}
	}
	/**���ݴ����accid�����������ϴ��˺� ���û�д���accid����ϵͳ����һ��32λ��accid
	 * @param accid �Զ���accid
	 * @param cfcode �������� 
	 */
	@RequestMapping(value="ct.do")
	@ResponseBody
	public Object createAccount(String accid,HttpServletRequest request){
		if(EmptyUtil.isEmpty(accid)){
			accid=MD5.sign(UUID.randomUUID().toString().replace("-", "").toLowerCase(),"utf-8");
		}
		Result result=tokenDispose(HttpYX.geMobileUpload(accid),"1");
		log.info("�����ƶ��ϴ��˻��ӿڷ���->"+result);
		if(result.isSuccess()){//����ɹ���
			Map map=((Map) result.getData());
			map.put("employcode","1");//��������
			map.put("delmark", 0);
			map.put("dt_add",super.getTime());
			map.put("dt_edit",super.getTime());
			map.put("mid_add", super.getUserId(request));
			map.put("mid_edit", super.getUserId(request));
			int i=yx.add_YX_account(map);//���� 
			if(i>0){//����ɹ�
				return result;
			}else{
				return renderError("����ʧ��:"+JSON.toJSONString(result.getData()));//����ʧ�ܷ���accid��token
			}
		}
		return result;//����ʧ�� ���߱���ʧ��
	}
	//������Ƶ�û�
	@RequestMapping(value="addRealTimeVideoBinding.do")
	@ResponseBody
	public Object addRealTimeVideoBinding(String Id,String bankId,HttpServletRequest request){
		//���������߼�
		if(StringUtils.isBlank(bankId)){
			return renderError("bankId����Ϊ�գ�");
		}
		int i=yx.selectBankCount(bankId);
		if(i==0){
			return renderError("�����ڴ����У�");
		}
		if(StringUtils.isBlank(Id)){
			return renderError("Id����Ϊ�գ�");
		}
		//�鿴�û�id�Ƿ����
		int count=yx.selectCountAdminById(Id);
		if(count==1){//��Id��ע��
			//�鿴�Ƿ��Ѿ�����
			String delmark=yx.selectCountTokenByUid(Id);
			if(StringUtils.isNotBlank(delmark)){//�����ӳ�䲻������-ֵӳ���ϵ���򷵻� true�� 
				if(delmark.equals("0")){//����			
						return renderSuccess("���˺��Ѵ��ڴ�Ȩ��!");				
				}
				//�������û������� �󶨵�����Ĭ�ϲ����޸�
				return yx.updateVideoTokenBinding("0",null, Id,super.getTime(),super.getUserId(request));	 
			}
			//�����˺�
			Result result1=tokenDispose(HttpYX.getToken(MD5.sign(UUID.randomUUID().toString().replace("-", "").toLowerCase(),"utf-8")),"0");
			log.info("������Ե���Ƶ�˻��ӿڷ���->"+result1);
			if(result1.isSuccess()){
				Map map1=((Map) result1.getData());
				map1.put("delmark", 0);
				map1.put("uid", Id);
				map1.put("bankId", bankId);
				map1.put("employcode","0");//��������
				map1.put("dt_add",super.getTime());
				map1.put("dt_edit",super.getTime());
				map1.put("mid_add", super.getUserId(request));
				map1.put("mid_edit", super.getUserId(request));
				int i1=yx.add_YX_account(map1);//���� 
				if(i1==1){
					Result result2=tokenDispose(HttpYX.getToken(MD5.sign(UUID.randomUUID().toString().replace("-", "").toLowerCase(),"utf-8")),"0");
					log.info("������Ե���Ƶ�˻��ӿڷ���->"+result2);
					if(result2.isSuccess()){
						Map map2=((Map) result2.getData());
						map2.put("parentid",map1.get("id").toString());
						map2.put("delmark", 0);
						map2.put("employcode","0");
						map2.put("bankId", bankId);
						map2.put("dt_add",super.getTime());
						map2.put("dt_edit",super.getTime());
						map2.put("mid_add", super.getUserId(request));
						map2.put("mid_edit", super.getUserId(request));
						int i2=yx.add_YX_account(map2);//���� 
						if(i2==1){
							//��Ӻ���
							String addBuddy=HttpYX.addBuddy(map1.get("accid").toString(), map2.get("accid").toString());
							log.info("��Ӻ��ѷ���->"+addBuddy);
							return renderSuccess("�����ɹ�!");
						}else{
							return renderError("�����˻�2ʧ��!");
						}
					}else{
						return renderError("�����˻�2ʧ��!");
					}
				}else{
					return renderError("�����˻�1ʧ��!");
				}
			}
			return result1;
		}
		return renderError("���û�������!");
	}
	//������˺�
	@RequestMapping(value="VideoUnbind.do")
	@ResponseBody
	public Object removeRealTimeVideoUnbind(String Id,HttpServletRequest request){
		return yx.updateVideoTokenBinding("1",null,Id,super.getTime(),super.getUserId(request));
	}
	/** ���� ����������ͨ��ID ���ؽ�� 
	 * @param s �ӿ��з��ص��ַ���
	 */
	public  Result tokenDispose(String s,String type){
		if(s!=null){//���������
			log.info("����ӿڷ���->"+s+",����->"+type);
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
	 *�ƶ���ռλ 
	 *http://localhost/kcd/yx/occupy.do
	 */
	@RequestMapping(value="occupy.do")
	@ResponseBody
	public Object occupy(Integer id){
		log.info("ռλicbcId->"+id);
		String bankId=yx.selectBankId(String.valueOf(id));
		ScanPool1 scanPool1=null;;
		if(id==null || StringUtils.isBlank(bankId)){
			scanPool1=PoolCache1.defaultSeat();
			log.info("�����ȡһ����->"+scanPool1);
			//return renderError("icbcId�����ڻ���bankId�����ڣ�");
		}else{
			log.info("ռλbankId->"+bankId);
			scanPool1 =PoolCache1.aReduceBusy(bankId);
			log.info("ռλ���->"+scanPool1);
		}
		Map map=new HashMap<>();
		map.put("icbcId", id);
		map.put("dataTime",creditutil.time());
		map.put("result", JSON.toJSONString(scanPool1));
		map.put("describe","��ȡ��Ƶͨ���˺�");
		int i=yx.addOccupyTest(map);
		String Id=map.get("id").toString();
		log.info("��ӵ��ü�¼->addCount:+"+i+",����:"+Id);
		
		if(scanPool1!=null){
			scanPool1.setId(Id);
			return renderSuccess(scanPool1);
		}else{
			return renderError("����û�����õ���Ƶͨ����ϯ,���Ժ����ԣ�");
		}
	}
	/**
	 *�ƶ����ͷ�
	 */
	@RequestMapping(value="free.do")
	@ResponseBody
	public Object freeToken(String mark){
		return null;
	}
	
	//web�˹Ҷ��ͷ�
	@RequestMapping(value="free1.do")
	@ResponseBody
	public Object freeToken1(ScanPool1 data){
		log.info("web�˹Ҷ��ͷ�->param:"+data.toString());
		ScanPool1 scanPool1=PoolCache1.deleteBusy(data.getMark());
		
		if(scanPool1!=null){
			log.info("web���ͷųɹ�");
			return renderSuccess(scanPool1);
		}else{
			log.info("web���ͷ�ʧ��");
			return renderError("�ͷ�ʧ��");
		}
		 
	}
	//web��ˢ�����ߣ���������������
	@RequestMapping(value="refreshtime.do")
	@ResponseBody
	public Object refreshTime(ScanPool1 data){
		log.info("web��ˢ������->param:"+data.toString());
		return PoolCache1.add(data);
	}
	/**
		��½ �ɹ�����������ID
	 * @throws Exception 
	 */
	@RequestMapping(value="login.do")
	@ResponseBody
	public Object toLogin(HttpServletRequest request) throws Exception{
		String id=null;
		try {
			id=getUserId(request);
		} catch (Exception e) {
			id=request.getParameter("id");
			if(StringUtils.isBlank(id)){
				return null;
			}
		}  
		if(!EmptyUtil.isEmpty(id)){//����
			List list = yx.query_tokenbyid(id);
			if(list.size()>0){
				ScanPool1 scanpool=(ScanPool1)list.get(0);
				if(scanpool.getDelmark()==0){
					ScanPool1 offer=null;
					ScanPool1 poolCache1= PoolCache1.isMarkToBusy(scanpool.getMark());
					if(poolCache1!=null){
						return renderError("���˺�������Ƶ��");
					}else{
						offer =PoolCache1.add(scanpool);
					}
					return renderSuccess(offer);
				}else{
					log.info("���˺ű�����");
					return renderError("���˺ű�����");
				}
			}else{
				log.info("���˺�û�а���Ƶtoken");
				return renderError("���˺�û�а���Ƶtoken");
			}
		}
		return renderError("���˺�û�а���Ƶtoken");                                                                                                                       
	}
	
	//ɾ����Ծ�е�
	@RequestMapping(value="deleteActive.do")
	@ResponseBody
	public  Object deleteActive(ScanPool1 data){
		log.info("Web���յ�Becall->�ػ��û���Ϣ:"+data.toString());
		return renderSuccess(PoolCache1.deleteActive(data));
	}	
	
	/*�˳�����*/
	@RequestMapping(value="outlogin.do")
	@ResponseBody
	public  Object outLogin(ScanPool1 data){
		log.info("web�˳�����->param:"+data.toString());
		return renderSuccess(PoolCache1.outLogin(data));
	}	
	/**
	 *��ȡ���һ���ϴ�accid
	 */
	@RequestMapping(value="sur.do")
	@ResponseBody
	public Object setUploadReduce(String id){
		List list = yx.query_token("2",-1);
		Random random = new Random();
		int n = random.nextInt(list.size());
		
		Map map=new HashMap<>();
		Object object=list.get(n);
		map.put("icbcId", id);
		map.put("dataTime",creditutil.time());
		map.put("result", JSON.toJSONString(object));
		map.put("describe", "��ȡ����ϴ��˺�");
		int i=yx.addOccupyTest(map);
		log.info("��ӵ��ü�¼->addCount:+"+i);
		
		return renderSuccess(object);
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
            log.info("��Ϣ����ԭ->"+body);
            body=body.replaceAll("\\\\", "");
            if (EmptyUtil.isEmpty(body)) {//���Ϊnull ���߿��ַ�
                result.put("code", 414);
                return result;
            }else{
            	JSONObject map=null;
            	try {
            		StringBuilder redundant=new StringBuilder(body.replaceAll("(\\})|(\\{)|(\\[)|(\\])", ""));//���͵�������Ϣ
            		//�ַ�������
            		body=body.replaceAll("\"\\{","{").replaceAll("\\}\"","}").replaceAll("\"\\[\\{", "[{").replaceAll("\\}\\]\"", "}]");
            		 map = JSONObject.parseObject(body);
            		log.info("��Ϣ���ʹ���->"+map.toJSONString());
            		map.put("viedotype", "1");//������Ƶ������
            		String eventType = map.get("eventType").toString();
            		String id_=null;
            			//{"channelId":"6265490045067594274","createtime":"1458798080073","duration":"22","eventType":"5","live":"1","members":"[{\"accid\":\"789\",\"duration\":11},{\"accid\":\"123456\",\"caller\":true,\"duration\":11}]","status":"SUCCESS","type":"VEDIO"}
            			if(eventType.equals("5")){//��ʾAUDIO/VEDIO/DataTunnel��Ϣ�����㱨ʵʱ����Ƶͨ��ʱ�����װ��¼�ʱ������Ϣ
            				log.info("ʱ����Ϣstart");
            				//JSONArray members  =JSONArray.parseArray(map.getString("members").toString().replaceAll("(^\"*)|(\"*$)","")); //����ַ��� ȥ����β��"�� ��ת��Ϊjsonarray
            				id_ = yx.select_infocopy(map.get("channelId").toString());//��ȡͨ��id
            				JSONArray members=map.getJSONArray("members");
            				map.put("duration_time",redundant.toString());//������ͨ��ʱ������
            				 for(int i=0;i<members.size();i++){//ȷ���������ߺͽ�����
            					JSONObject members2=members.getJSONObject(i);
            					//�����ͨ���ķ����ߵĻ���caller�ֶ�Ϊtrue,������caller�ֶ�;         
            					if(members2.toString().indexOf("caller")==-1){//�����ͨ���ķ����ߵĻ���caller�ֶ�Ϊtrue��������caller�ֶΣ�duration��ʾ��Ӧaccid�û��ĵ���ʱ��
            						String uid=yx.selectUidByAccid(members2.getString("accid"));
            						log.info("����accid��ѯuid->accid:"+members2.getString("accid")+",uid:"+uid);
            						map.put("createtime", DataUtil.millisecondTodate(Long.parseLong(map.getString("createtime").toString())));
            						map.put("faccid",uid);//�ĳɽ���
            						if(id_!=null){//���ڸ���channeid���� 
                    					 int count=yx.update_infocopy_durationM(map);
                    					 log.info("����ͨ��ʱ����Ϣ->"+count);
                    				 }else{//�����������
                    					 int count=yx.insert_infocopy_durationM(map);
                    					 log.info("���ͨ��ʱ����Ϣ->"+count);
                    				 }
            					}
            					//�������ǽ����� �򶪵����� 
            				 }	
            			//{"eventType":"6","fileinfo":"[{\"caller\":true,\"channelid\":\"6290737000999815988\",\"filename\":\"xxxxxx.type\",\"md5\":\"a9b248e29669d588dd0b10259dedea1a\",\"mix\":true,\"size\":\"2167\",\"type\":\"gz\",\"vid\":\"1062591\",\"url\":\"http://xxxxxxxxxxxxxxxxxxxx.mp4\",\"user\":\"zhangsan\"}]"}
            			}else if(eventType.equals("6")){//��ʾ����Ƶ/�װ��ļ��洢��Ϣ�����㱨����Ƶ/�װ��ļ��Ĵ�С�����ص�ַ����Ϣ
            					log.info("������Ϣstart");
            					JSONArray fileinfo = map.getJSONArray("fileinfo");
            					 String url="";
            					map.put("download_info", redundant.toString());
            					for(int j=0;j<fileinfo.size();j++){
            						JSONObject fileinfo2 = fileinfo.getJSONObject(j);
            						boolean b=fileinfo2.getBooleanValue("mix");//mix���Ƿ�Ϊ���¼���ļ���true�����¼���ļ���false������¼���ļ�
            						url=fileinfo2.getString("url");
            						if(b && url.indexOf("mp4")!=-1){//mix���Ƿ�Ϊ���¼���ļ���true�����¼���ļ���false������¼���ļ� ����Ϊmp4��ʽ
            							id_ = yx.select_infocopy(fileinfo2.getString("channelid"));//�ж��Ƿ����ͨ��id
            							map.put("fi", fileinfo2);//������ͬһ�γ��ͣ����ܻ᳭���㲻ͬchannel ID ����Ϣ��
            							map.put("url", fileinfo2.get("url"));
            							map.put("vid", fileinfo2.get("vid"));
            							 if(id_!=null){//���� �޸�
            								 map.put("id11", id_);
            								 int i=yx.update_infocopy_downloadM(map);
            								 log.info("�������ص�ַ��Ϣ->"+i);
            							 }else{//�����������
            								map.put("channelid", fileinfo2.get("channelid"));
            								 int i=yx.insert_infocopy_downloadM(map);
            								 log.info("������ص�ַ��Ϣ->"+i);
            							 }
            						}
            					}
            				}
            	
				} catch (Exception e) {
					String map1="";
					if(map!=null){
						map1=JSON.toJSONString(map);
					}
					yx.insert_M(body+",map:"+map1+"----error:"+getErrorInfoFromException(e));//�������ֱ�ӱ���
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
    @RequestMapping(value="downloadClient.do")
	@ResponseBody
    public void downloadClient(String url,HttpServletResponse response) throws Exception{
    	log.info("�ͻ���������Ƶ�ļ�->"+url);
        String[] ss=url.split("/");
        String fileName= ss[ss.length-1];
        if(url.indexOf("www")!=-1 || url.indexOf("http")!=-1){//ͨ��url����
        	log.info("ͨ��url����");
        	super.urlToWeb(url, response, fileName);
        }else{
        	log.info("��������");
        	super.	download(url,response);
        }
    	
    }
    private static HashMap map__1=new HashMap<>();
	@RequestMapping(value="dsdb.do")
	@ResponseBody
    public Object downloadServiceDatabase(String id){
    	if(StringUtils.isBlank(id)){
    		return renderError("��������ȷ");
    	}
    	Map map1=yx.selectUrlAndVidById(id);
    	String url=map1.get("url").toString();
    	log.info("id:"+id+"�Ƿ����->"+map__1.get("id"));
    	if(map__1.get(id)==null){
    		//ɾ���ƶ˵���Ƶ
        	String[] s=url.split("/");
        	try {
        		map__1.put(id,creditutil.time());
        		String last=s[s.length-1];
        		log.info("ԭ��Ƶ�ļ���->"+last);
        		String scen="upload/mp4/"+DataUtil.splicingPath;
        		String download_path=RootStatic.root_Directory+scen;
        		String play_path=RootStatic.download_prefix+scen+last;
        		log.info("��Ƶ���ŵ�ַ->"+play_path);
        		
        		File file=new File(download_path);
    
    	     
    	       if(!file.exists()){
    	     	   file.mkdirs();//����Ŀ¼
    	       }  
    	       log.info("����·��->"+file.getAbsolutePath());
    			downloadFile(url,download_path+last);
    			//�������ݿⲥ�ŵ�ַ
    			int updateCount=yx.updateServerPath(play_path,id);
    			if(updateCount>0){//���³ɹ�
    				//�h��ԭҕ�l
    				 String result=HttpYX.delteViedo(map1.get("vid").toString());
    				 log.info("�h����ƵԴ�ļ�����->"+result);
    			}
    			log.info("���²��ŵ�ַ->id:"+id+",count"+updateCount);
    			Map map=new HashMap<>();
    			map.put("play_path", play_path);
    			map.put("AbsolutePath", file.getAbsolutePath());
    			map.put("downUrl", RootStatic.url+RootStatic.root_Directory+scen+last);
    			map.put("id", id);
    			return renderSuccess(map);
    		} catch (Exception e) {
    			log.info("������Ƶ�����ط������쳣");
    			e.printStackTrace();
    			return renderError("����ʧ�ܣ�ʧ��ԭ��"+e.getMessage());
    		}finally {
    			map__1.remove(id);
			}
    	}else{
    		return renderError(map__1.get(id).toString()+",���������У���ȴ�...");
    	}
    }
	public static void main(String[] args) throws Exception{
		//����Ŀ¼����
		/*String scen="upload/mp4/"+DataUtil.splicingPath;
		String download_path=RootStatic.root_Directory+scen;
		File file =new File(download_path);
		if(file.exists()){
			System.out.println("Ŀ¼����");
		}else{
			System.out.println("����");
			file.mkdirs();
		}*/
		
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
		//System.out.println(HttpYX.addBuddy("c6fa296f9c17c8032be6593a5d02269b", "507da3a2ddd113ec9166fb8e58005fb5"));
		//System.out.print(DataConversionParent.subZeroAndDot("0.0"));
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
//		String s="dfd'{dfd}''{";
//		//System.out.println(s.replaceAll("\\'\\{","{"));
//		//char5�ָ�
//		String s1="upload/2018/12/05/b96488e4cb8801432fd5c5a1944751a0.jpgupload/2018/12/05/0480eb24d3448ceade8ad0f84fd57458.jpgupload/2018/12/05/19c43d2433d75441c2dd7be45a604ff8.jpgupload/2018/12/05/250126c780fd93b29e175db5ec00db8d.jpg";
//		String ss[]=s1.split("");
//		for(String s2:ss){
//			if(!s2.equals("")){
//				System.out.println("���"+s2);
//				break;
//			}
//		}
		//��Ƶ����
	/*	Long l=System.currentTimeMillis();
		downloadFile("http://jdvod6ep5thqk.vod.126.net/jdvod6ep5thqk/0-50870502883509-0-mix.mp4","C:\\Users\\Administrator\\Desktop\\word\\haha1\\upload\\0-6324213347287310-0-mix.mp4");
		Long l1=System.currentTimeMillis();
		System.out.println(l1-l);*/
		
		//��ȡ�ַ�����
		/*String s="http://a.kcway.net/assess/upload/0-50873460261080-0-mix.mp4";
		int i=s.indexOf("upload");
		if(i!=-1){
			System.out.println(s.substring(i));
		}*/
	}
}
