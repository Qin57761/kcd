package com.controller.PFmodel;
public  class Customer {
	private String sex="0"; // �Ա�
	private String age="0"; // ����
	private String  h_address;//�������ڵ�
	private String is_marital_status="0";//��� 0�� 1��
	private String education;//�����̶�
	private String nature_of_Business;//��˾����
	private String bank_usage;//����ʹ�����

	private String is_surety;//�Ƿ��е�����
	private String is_credit="0";//�Ƿ�ʧ���� 0��(Ĭ��) 1��
	private String court_execution="0";//��Ժ��ִ����� Ĭ����û�б�ִ�й�1�ѽ᰸ 2δ�᰸
	
	private String seven_days="0";
	private String one_month="0";
	private String three_month="0";
	private String six_month="0";
	private String twelve_month="0";//ʮ��
	
	private String uncleared_number="0";//δ����������  ���ά����� Ŀǰ׼ȷ
	private String uncleared_monty="0";//δ��������� ��ά�����
	private String  highest_overdue="0";//����������ڽ�� ��ά�����
	private String max_credit="0";//���ÿ���߶��
	private String count_credit="0";//	���ÿ�����
	private String high_profile;//�߶���ʿ(ְҵ)(B)
	private String work_day; //��������(B)
	private String is_two_house;//�Ƿ��еڶ��׷���(B)
	private String is_two_car;//�Ƿ��еڶ�������(B)
	private String is_ife_insurance;//�Ƿ������ٱ���(B)
	private String is_w_blacklist;//�Ƿ�������������
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getH_address() {
		return h_address;
	}
	public void setH_address(String h_address) {
		this.h_address = h_address;
	}
	public String getIs_marital_status() {
		return is_marital_status;
	}
	public void setIs_marital_status(String is_marital_status) {
		this.is_marital_status = is_marital_status;
	}
	
	public String getUncleared_number() {
		return uncleared_number;
	}
	public void setUncleared_number(String uncleared_number) {
		this.uncleared_number = uncleared_number;
	}
	public String getUncleared_monty() {
		return uncleared_monty;
	}
	public void setUncleared_monty(String uncleared_monty) {
		this.uncleared_monty = uncleared_monty;
	}
	public String getHighest_overdue() {
		return highest_overdue;
	}
	public void setHighest_overdue(String highest_overdue) {
		this.highest_overdue = highest_overdue;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getNature_of_Business() {
		return nature_of_Business;
	}
	public void setNature_of_Business(String nature_of_Business) {
		this.nature_of_Business = nature_of_Business;
	}
	public String getBank_usage() {
		return bank_usage;
	}
	public void setBank_usage(String bank_usage) {
		this.bank_usage = bank_usage;
	}
	public String getMax_credit() {
		return max_credit;
	}
	public void setMax_credit(String max_credit) {
		this.max_credit = max_credit;
	}
	public String getIs_surety() {
		return is_surety;
	}
	public void setIs_surety(String is_surety) {
		this.is_surety = is_surety;
	}
	public String getIs_credit() {
		return is_credit;
	}
	public void setIs_credit(String is_credit) {
		this.is_credit = is_credit;
	}
	public String getCourt_execution() {
		return court_execution;
	}
	public void setCourt_execution(String court_execution) {
		this.court_execution = court_execution;
	}
	public String getSeven_days() {
		return seven_days;
	}
	public void setSeven_days(String seven_days) {
		this.seven_days = seven_days;
	}
	public String getOne_month() {
		return one_month;
	}
	public void setOne_month(String one_month) {
		this.one_month = one_month;
	}
	public String getThree_month() {
		return three_month;
	}
	public void setThree_month(String three_month) {
		this.three_month = three_month;
	}
	public String getSix_month() {
		return six_month;
	}
	public void setSix_month(String six_month) {
		this.six_month = six_month;
	}
	public String getTwelve_month() {
		return twelve_month;
	}
	public void setTwelve_month(String twelve_month) {
		this.twelve_month = twelve_month;
	}
	public String getCount_credit() {
		return count_credit;
	}
	public void setCount_credit(String count_credit) {
		this.count_credit = count_credit;
	}
	public String getHigh_profile() {
		return high_profile;
	}
	public void setHigh_profile(String high_profile) {
		this.high_profile = high_profile;
	}
	public String getWork_day() {
		return work_day;
	}
	public void setWork_day(String work_day) {
		this.work_day = work_day;
	}
	public String getIs_two_house() {
		return is_two_house;
	}
	public void setIs_two_house(String is_two_house) {
		this.is_two_house = is_two_house;
	}
	public String getIs_two_car() {
		return is_two_car;
	}
	public void setIs_two_car(String is_two_car) {
		this.is_two_car = is_two_car;
	}
	public String getIs_ife_insurance() {
		return is_ife_insurance;
	}
	public void setIs_ife_insurance(String is_ife_insurance) {
		this.is_ife_insurance = is_ife_insurance;
	}
	public String getIs_w_blacklist() {
		return is_w_blacklist;
	}
	public void setIs_w_blacklist(String is_w_blacklist) {
		this.is_w_blacklist = is_w_blacklist;
	}
/*	
	public String toString() {
		Customer c=new Customer();
		return "�Ա�"+c.getSex()+",����:"+c.getAge()+",�������ڵأ�"+c.getH_address()+",���"+c.getIs_marital_status()+",�����̶ȣ�"+c.getEducation()+
				",��˾���ʣ�"+c.nature_of_Business+",����ʹ�������"+c.bank_usage+",�Ƿ��е����ˣ�"+c.is_surety+",�Ƿ�ʧ���ˣ�"+c.is_credit+",��Ժ��ִ�������"+c.court_execution+",δ������������"+c.uncleared_number+",δ���������"
				+c.uncleared_monty+",����������ڽ�"+c.highest_overdue+",���ÿ�������"+c.count_credit+",ְҵ��"+c.high_profile+",�������ޣ�"+c.work_day
				+",�Ƿ��еڶ��׷���"+c.is_two_house+",�Ƿ��еڶ�������"+c.is_two_car+",�Ƿ������ٱ��գ�"+c.is_ife_insurance+",�Ƿ�������������"+c.is_w_blacklist;
	}*/
}
