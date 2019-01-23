package com.controller.ManagementCenter.Management;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service1.ManagementCenter.kj_icbcService;
import com.service1.ManagementCenter.kjs_icbc_cardkService;
import com.service1.ManagementCenter.kj_icbc_resultService;
import net.sf.json.JSONArray;


@Controller
public class Management {
	@Autowired
	private kj_icbcService kj_icbcService;
	@Autowired
	private kjs_icbc_cardkService kjs_icbc_cardkService;
	@Autowired
	private kj_icbc_resultService kj_icbc_resultService;
	
	//ǰ̨���ݺ�̨��ȡ
	public  void management(HttpServletRequest request) {
			
		List<HashMap> loanlist=kj_icbcService.SelectLoan();//�����ѷſ�����ܽ��   amount=0/money=null
			if(loanlist.get(0).get("amount").equals(0) ){
				loanlist.get(0).put("money",0);
			}
		List<HashMap> fklist=kj_icbcService.SelectFk();//�����ѷſ�δ��Ѻ�������ܽ��   amount=0/money=null
			if(fklist.get(0).get("amount").equals(0)){
				fklist.get(0).put("money",0);
			}
		
		List<HashMap> rankinglist=kj_icbcService.SelectStates();//ÿ���ܶ�������ʡ����     sell,name=null
			for(int i=0;i<rankinglist.size();i++){
				if(rankinglist.get(i).get("sell")==null && rankinglist.get(0).get("sell").equals("")){
					rankinglist.get(i).put("sell",0);
					rankinglist.get(i).put("name"," ");
				}
			}
		
		List<HashMap> gemslist=kj_icbcService.SelectGems();//ÿ���ܶ�����������������    gems,name=null
			for(int i=0;i<gemslist.size();i++){
				if(gemslist.get(i).get("gems")==null && gemslist.get(0).get("gems").equals("")){
					gemslist.get(i).put("gems",0);
					gemslist.get(i).put("name"," ");
				}
			}
		
		List<HashMap> rankingloanlist=kj_icbcService.SelectLoanStates();//ÿ�·ſ����ʡ����	 sell,name=null
			for(int i=0;i<rankingloanlist.size();i++){
				if(rankingloanlist.get(i).get("sell")==null && rankingloanlist.get(0).get("sell").equals("")){
					rankingloanlist.get(i).put("sell",0);
					rankingloanlist.get(i).put("name"," ");
				}
			}
		
		List<HashMap> gemsloanlist=kj_icbcService.SelectLoanGems();//ÿ�·ſ��������������         gems,name=null
			for(int i=0;i<gemsloanlist.size();i++){
				if(gemsloanlist.get(i).get("gems")==null && gemsloanlist.get(0).get("gems").equals("")){
					gemsloanlist.get(i).put("gems",0);
					gemsloanlist.get(i).put("name"," ");
				}
			}
		
		List<HashMap> cardpasscomm=kjs_icbc_cardkService.SelectCarPassComm();//ÿ��������������ʸ�ʡ����     rate,name=null
			for(int i=0;i<cardpasscomm.size();i++){
				if(cardpasscomm.get(i).get("rate")==null && cardpasscomm.get(0).get("rate").equals("")){
					cardpasscomm.get(i).put("rate",0);
					cardpasscomm.get(i).put("name"," ");
				}
			}
		
		List<HashMap> cardpassgems=kjs_icbc_cardkService.SelectCarPassGems();//ÿ��������������ʸ�����������    rate,name=null
			for(int i=0;i<cardpassgems.size();i++){
				if(cardpassgems.get(i).get("rate")==null && cardpassgems.get(0).get("rate").equals("")){
					cardpassgems.get(i).put("rate",0);
					cardpassgems.get(i).put("name"," ");
				}
			}
		
		request.setAttribute("billlist",kj_icbcService.SelectBill());//ÿ�±�������     0
		request.setAttribute("loanlist",loanlist);//ÿ���ѷſ�����ܽ��
		request.setAttribute("fklist",fklist);//ÿ���ѷſ�δ��Ѻ�������ܽ��
		request.setAttribute("carselect",kjs_icbc_cardkService.CountSelect());//������������     0
		request.setAttribute("carpass",kjs_icbc_cardkService.CountPass());//��������ͨ��          0
		request.setAttribute("rankinglist",rankinglist);//ÿ���ܶ�������ʡ����
		request.setAttribute("gemslist",gemslist);//ÿ���ܶ�����������������
		request.setAttribute("rankingloanlist",rankingloanlist);//ÿ���ܶ�����������ʡ����
		request.setAttribute("gemsloanlist",gemsloanlist);//ÿ���ܶ���������������������
		request.setAttribute("cardpasscomm",cardpasscomm );//ÿ��������������ʸ�ʡ����
		request.setAttribute("cardpassgems",cardpassgems );//ÿ��������������ʸ�����������
		
		//request.setAttribute("lll",lll);
	}
	//ÿ�������ܵ�������ͼajaxǰ̨��ȡ           null,null,null
	@RequestMapping("erp/Management/getPathMap.do") 
	@ResponseBody
	public String getPathMap(HttpServletRequest request, HttpServletResponse response){
		try {
			/*** ��������ȡֵ���ɶ�ά���ݣ���ת��json ***/
			List<HashMap> chart=kj_icbcService.SelectChart();//��̨��ȡ��ѯ����
			Object [][] Ochart=new Object[2][9];			
			if(chart.size()<9){
				for(int i=0;i<chart.size();i++){
					Ochart[1][i]=(chart.get(i).get("year")+"-"+chart.get(i).get("month"));//�����ڸ�ʽ��������ά����xxxx-xx
					Ochart[0][i]=(chart.get(i).get("total"));//��ÿ�����ݷ����ά����	
					
				}
				for(int i=chart.size();i<9;i++){
					Ochart[1][i] = "2018-";
					Ochart[0][i] = "0";
				}	
			}else{			
				for(int i=0;i<9;i++){
					Ochart[1][i]=(chart.get(i).get("year")+"-"+chart.get(i).get("month"));//�����ڸ�ʽ��������ά����xxxx-xx
					Ochart[0][i]=(chart.get(i).get("total"));//��ÿ�����ݷ����ά����	
				}
			}		
			JSONArray jsonArray = JSONArray.fromObject(Ochart);
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.print(jsonArray); // �켣ͼ������ȡ��������
			pw.flush();
			pw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//��������ͨ��������ͼajaxǰ̨��ȡ
	@RequestMapping("erp/Management/getCarPathMap.do") 
	@ResponseBody
	public String getCarPathMap(HttpServletRequest request, HttpServletResponse response) {
		try {
			/*** ��������ȡֵ���ɶ�ά���ݣ���ת��json ***/
			List<HashMap> chart=kjs_icbc_cardkService.SelectChart();//��̨��ȡ��ѯ����
			Object [][] Ochart=new Object[2][9];			
			if(chart.size()<9){
				for(int i=0;i<chart.size();i++){
					Ochart[1][i]=(chart.get(i).get("year")+"-"+chart.get(i).get("month"));//�����ڸ�ʽ��������ά����xxxx-xx
					Ochart[0][i]=(chart.get(i).get("total"));//��ÿ�����ݷ����ά����	
					
				}
				for(int i=chart.size();i<9;i++){
					Ochart[1][i] = "2018-";
					Ochart[0][i] = "0";
				}
			}else{
				for(int i=0;i<9;i++){
					Ochart[0][i]=(chart.get(i).get("total"));//��ÿ�����ݷ����ά����
					Ochart[1][i]=(chart.get(i).get("year")+"-"+chart.get(i).get("month"));//�����ڸ�ʽ��������ά����xxxx-xx
				}
			}	
			JSONArray jsonArray = JSONArray.fromObject(Ochart);
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.print(jsonArray); // �켣ͼ������ȡ��������
			pw.flush();
			pw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//�¾���������ֲ�����ͼajaxǰ̨��ȡ
	@RequestMapping("erp/Management/getCarFkPathMap.do") 
	@ResponseBody
	public String getCarFkPathMap(HttpServletRequest request, HttpServletResponse response) {
		try {
			/*** ��������ȡֵ���ɶ�ά���ݣ���ת��json ***/
			List<HashMap> chart=kjs_icbc_cardkService.SelectCarFk();//��̨��ȡ��ѯ����
			String[] s = new String[2];
			if(chart.size() < 2){
				if(chart.size()<1){
					for(int i=0;i<2;i++){
						s[i]="0";
					}
				}else{
					if(chart.get(0).get("cartype").toString().equals("1")){
						s[0]=chart.get(0).get("cartotal").toString();
						s[1]="0";
					}else{
						s[0]="0";
						s[1]=chart.get(0).get("cartotal").toString();
					}				
				}		
			}else{
				for(int i=0;i<2;i++){
					s[i]=chart.get(i).get("cartotal").toString();
				}
			}
			JSONArray jsonArray = JSONArray.fromObject(s);
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.print(jsonArray); // �켣ͼ������ȡ��������
			pw.flush();
			pw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//������ֲ�����ͼajaxǰ̨��ȡ     null,null,null,null
	@RequestMapping("erp/Management/getMoneyPathMap.do") 
	@ResponseBody
	public String getMoneyPathMap(HttpServletRequest request, HttpServletResponse response) {
		try {
			/*** ��������ȡֵ���ɶ�ά���ݣ���ת��json ***/
			List<HashMap> chart=kj_icbcService.SelectMoneyDistribute();//��̨��ȡ��ѯ����
			String[] s = new String[4];	
			if(chart.get(0) == null){
				for(int i=0;i<4;i++){
					s[i]="0";
				}
			}else{
				for(int i=1;i<5;i++){
					if(chart.get(0).get("singular"+i)==null){
						s[i-1]="0";
					}else{
						s[i-1]=chart.get(0).get("singular"+i).toString();
					}
				}
			}
			JSONArray jsonArray = JSONArray.fromObject(s);
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.print(jsonArray); // �켣ͼ������ȡ��������
			pw.flush();
			pw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//��Ѻ��ɷֲ�����ͼajaxǰ̨��ȡ
	@RequestMapping("erp/Management/getPawnPathMap.do") 
	@ResponseBody
	public String getPawnPathMap(HttpServletRequest request, HttpServletResponse response) {
		try {
			/*** ��������ȡֵ���ɶ�ά���ݣ���ת��json ***/
			List<HashMap> chart=kj_icbc_resultService.SelectResult();//��̨��ȡ��ѯ����
			String[] s = new String[5];		
			if(chart.get(0) == null){
				for(int i=0;i<5;i++){
					s[i]="0";
				}
			}else{
				for(int i=1;i<6;i++){
					if(chart.get(0).get("paw"+i)==null){
						s[i-1]="0";
					}else{
						s[i-1]=chart.get(0).get("paw"+i).toString();
					}
				}
			}
			JSONArray jsonArray = JSONArray.fromObject(s);
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.print(jsonArray); // �켣ͼ������ȡ��������
			pw.flush();
			pw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//���Ų�ѯ�ֲ�����ͼajaxǰ̨��ȡ   null,null       0,1
	@RequestMapping("erp/Management/getCreditPathMap.do") 
	@ResponseBody
	public String getCreditPathMap(HttpServletRequest request, HttpServletResponse response) {
		try {
			/*** ��������ȡֵ���ɶ�ά���ݣ���ת��json ***/
			List<HashMap> credit=kj_icbcService.SelectCredit();//��̨��ȡ��ѯ����
			String[] s = new String[2];
			if(credit.size() < 2){
				if(credit.size()<1){
					for(int i=0;i<2;i++){
						s[i]="0";
					}
				}else{
					if(credit.get(0).get("zxok_tag").toString().equals("0")){
						s[0]=credit.get(0).get("zxok").toString();
						s[1]="0";
					}else{
						s[0]="0";
						s[1]=credit.get(0).get("zxok").toString();
					}				
				}		
			}else{
				for(int i=0;i<2;i++){
					s[i]=credit.get(i).get("zxok").toString();
				}
			}
			JSONArray jsonArray = JSONArray.fromObject(s);
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.print(jsonArray); // �켣ͼ������ȡ��������
			pw.flush();
			pw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//�ͻ�����ֲ�����ͼajaxǰ̨��ȡ      null,null,null,null
	@RequestMapping("erp/Management/getAgePathMap.do") 
	@ResponseBody
	public String getAgePathMap(HttpServletRequest request, HttpServletResponse response) {
		try {
			/*** ��������ȡֵ���ɶ�ά���ݣ���ת��json ***/
			List<HashMap> credit=kj_icbcService.SelectClientAge();//��̨��ȡ��ѯ����
			String[] s = new String[4];
			if(credit.get(0) == null){
				for(int i=0;i<4;i++){
					s[i]="0";
				}
			}else{
				for(int i=1;i<5;i++){
					if(credit.get(0).get("age"+i)==null){
						s[i-1]="0";
					}else{
						s[i-1]=credit.get(0).get("age"+i).toString();
					}
				}
			}
			JSONArray jsonArray = JSONArray.fromObject(s);
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.print(jsonArray); // �켣ͼ������ȡ��������
			pw.flush();
			pw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//�³�����ֲ�����ͼajaxǰ̨��ȡ    null,null,null,null
	@RequestMapping("erp/Management/getCarsAgePathMap.do") 
	@ResponseBody
	public String getCarsAgePathMap(HttpServletRequest request, HttpServletResponse response) {
		try {
			/*** ��������ȡֵ���ɶ�ά���ݣ���ת��json ***/
			List<HashMap> carsage=kj_icbcService.SelectCarsAge();//��̨��ȡ��ѯ����
			String[] s = new String[4];
			if(carsage.get(0) == null){
				for(int i=0;i<4;i++){
					s[i]="0";
				}
			}else{
				for(int i=1;i<5;i++){
					if(carsage.get(0).get("age"+i)==null){
						s[i-1]="0";
					}else{
						s[i-1]=carsage.get(0).get("age"+i).toString();
					}
				}
			}
			JSONArray jsonArray = JSONArray.fromObject(s);
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.print(jsonArray); // �켣ͼ������ȡ��������
			pw.flush();
			pw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//���������ֲ�ͼajaxǰ̨��ȡ   null,null,null
	@RequestMapping("erp/Management/getAdvanceFundPathMap.do") 
	@ResponseBody
	public String getAdvanceFundPathMap(HttpServletRequest request, HttpServletResponse response) {
		try {
			/*** ��������ȡֵ���ɶ�ά���ݣ���ת��json ***/
			List<HashMap> fund=kj_icbcService.SelectAdvanceFund();//��̨��ȡ��ѯ����
			Object [][] Ofund=new Object[2][12];					
			if(fund.size()<12){
				for(int i=0;i<fund.size();i++){
					Ofund[1][i]=(fund.get(i).get("year")+"-"+fund.get(i).get("month"));//�����ڸ�ʽ��������ά����xxxx-xx
					Ofund[0][i]=(fund.get(i).get("adate"));//��ÿ�����ݷ����ά����	
					
				}
				for(int i=fund.size();i<12;i++){
					Ofund[1][i] = "2018-";
					Ofund[0][i] = "0";
				}
			}else{
				for(int i=0;i<12;i++){	
					Ofund[0][i]=(fund.get(i).get("adate"));//��ÿ�����ݷ����ά����
					Ofund[1][i]=(fund.get(i).get("year")+"-"+fund.get(i).get("month"));//�����ڸ�ʽ��������ά����xxxx-xx
				}
			}
			
			//System.out.println(Ochart);
			JSONArray jsonArray = JSONArray.fromObject(Ofund);
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.print(jsonArray); // �켣ͼ������ȡ��������
			pw.flush();
			pw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//��Ѻ���ϻ��շֲ�ͼajaxǰ̨��ȡ   null,null,null
	@RequestMapping("erp/Management/getRecyclePathMap.do") 
	@ResponseBody
	public String getRecyclePathMap(HttpServletRequest request, HttpServletResponse response) {
		try {
			/*** ��������ȡֵ���ɶ�ά���ݣ���ת��json ***/
			List<HashMap> fund=kj_icbcService.SelectRecycle();//��̨��ȡ��ѯ����
			Object [][] Ofund=new Object[2][9];						
			if(fund.size()<9){
				for(int i=0;i<fund.size();i++){
					Ofund[1][i]=(fund.get(i).get("year")+"-"+fund.get(i).get("month"));//�����ڸ�ʽ��������ά����xxxx-xx
					Ofund[0][i]=(fund.get(i).get("total"));//��ÿ�����ݷ����ά����	
					
				}
				for(int i=fund.size();i<9;i++){
					Ofund[1][i] = "2018-";
					Ofund[0][i] = "0";
				}
			}else{
				for(int i=0;i<9;i++){
					Ofund[0][i]=(fund.get(i).get("total"));//��ÿ�����ݷ����ά����
					Ofund[1][i]=(fund.get(i).get("year")+"-"+fund.get(i).get("month"));//�����ڸ�ʽ��������ά����xxxx-xx
				}
			}
			//System.out.println(Ochart);
			JSONArray jsonArray = JSONArray.fromObject(Ofund);
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.print(jsonArray); // �켣ͼ������ȡ��������
			pw.flush();
			pw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//�¾ɳ��ſ�ֲ�ͼajaxǰ̨��ȡ    null,null,null,null
	@RequestMapping("erp/Management/getNewOldCarsPathMap.do") 
	@ResponseBody
	public String getNewOldCarsPathMap(HttpServletRequest request, HttpServletResponse response) {
		try {
			/*** ��������ȡֵ���ɶ�ά���ݣ���ת��json ***/
			List<HashMap> newcars=kj_icbcService.SelectNewCars();//��̨��ȡ��ѯ����	
			Object [][] Ofund=new Object[5][12];			
			System.out.println(newcars.size()+"--------------------------------------");
			if(newcars.size()<12){
				for(int i=0;i<newcars.size();i++){
					Ofund[0][i]=(newcars.get(i).get("year1")+"-"+newcars.get(i).get("month1"));//�����ڸ�ʽ��������ά����xxxx-xx
					Ofund[1][i]=(newcars.get(i).get("newcon"));//���³�ÿ�·ſ�������ά����
					Ofund[2][i]=(newcars.get(i).get("newmoney"));//���³�ÿ�·ſ��ܽ������ά����
					Ofund[3][i]=(newcars.get(i).get("oldcon"));//�Ѷ��ֳ�ÿ�·ſ�������ά����
					Ofund[4][i]=(newcars.get(i).get("oldmoney"));//�Ѷ��ֳ�ÿ�·ſ��ܽ������ά����
					
				}
				for(int i=newcars.size();i<12;i++){
					Ofund[0][i] = "2018-";
					Ofund[1][i] = "0";
					Ofund[2][i] = "0";
					Ofund[3][i] = "0";
					Ofund[4][i] = "0";
				}
			}else{
				for(int i=0;i<12;i++){
					Ofund[0][i]=(newcars.get(i).get("year1")+"-"+newcars.get(i).get("month1"));//�����ڸ�ʽ��������ά����xxxx-xx
					Ofund[1][i]=(newcars.get(i).get("newcon"));//���³�ÿ�·ſ�������ά����
					Ofund[2][i]=(newcars.get(i).get("newmoney"));//���³�ÿ�·ſ��ܽ������ά����
					Ofund[3][i]=(newcars.get(i).get("oldcon"));//�Ѷ��ֳ�ÿ�·ſ�������ά����
					Ofund[4][i]=(newcars.get(i).get("oldmoney"));//�Ѷ��ֳ�ÿ�·ſ��ܽ������ά����
				}
			}
			//System.out.println(Ochart);
			JSONArray jsonArray = JSONArray.fromObject(Ofund);
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.print(jsonArray); // �켣ͼ������ȡ��������
			pw.flush();
			pw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
