package com.controller.erp_icbc.modal;

public class zhqx_modal {
	//��������
	private String   dn;
	private String   qn;
	private String   type;
	private String   cn;
	private Integer  id;
	private String  name;
	private Integer modal_tag;//ģ���� 1 ��  0��
	private Integer qx_type;//Ȩ������ Ĭ��0 �� 1 �����̣� 2���Ա/����Ա 
	//Ȩ��
	//1.��������
	private String   glzx;
	
	//2.��������
	private String   gzrw;
	private String   qbrw;
	private String   wdrw;
	private String   wdcy;
	private String   wdqd;
	
	//3.�˻�����
	private String   zhgl;
	private String   gsgl;
	private String   gsgladd;
	private String   gsgldelete;
	private String   gsglupdate;
	private String   rygl;
	private String   zhqxadd;
	private String   zhqxdelete;
	private String   zhqxupdate;
	private String   zhqx;
	private String   rygladd;
	private String   rygldelete;
	private String   ryglupdate;
	
	//4.���ֹ��д�
	private String   wlghd;
	private String   zx;
	private String   qcpg;
	private String   kk;
	private String   ssmq;
	private String   qcdk;
	
	//5.�������
	private String   dhgl;
	private String   yhhkxq;
	private String   yhhklr;
	private String   dclcjyq;
	private String   dclzjyq;
	private String   dclgjyq;
	private String   dcltc;
	private String   dclgs;
	private String   dhx;
	private String   yhx;
	
	//6.�������
	private String   cwgl;
	private String   khdkmx;
	
	//7.������Ƶ
	private String   zxsp;
	//8.�������
	private String   clgc;
	
	//9.���Ų�ѯ
	private String   zxcx;
	private String   cxjg_3;
	//10.����ͨ��
	private String   zxtr;
	private String   zxyhyj_6;
	private String   trsh_7;

	//11.��������
	private String clpg;
	private String pgjsh_11;
	//12.���е���
	private String yhds;
	private String dsjg_15;
	//13.��������
	private String kksq;
	private String sfhcjg_19;
	private String fkkkjg_20;
	//14.��Ƶ��ǩ
	private String spmq;
	private String jgfk_24;
	//15.������ҵ������
	private String kqyywsp;
	private String zgsh_27;
	private String zjlsh_29;
	//16.��������
	private String qcdksh;
	private String zysh_33;
	private String zgsh_35;
	private String jlsh_37;
	private String zjsh_39;
	//17.����ͨ��
	private String nstr;
	private String jgzjl_42;
	private String trshyj_43;
	private String trzg_44;
	private String trjl_45;
	//18.�ʽ����
	private String zjfp;
	private String qrsqdz_48;
	private String zjfp_49;
	private String cz_50;
	private String sslr_51;
	private String cwqrdz_52;
	//19.���д�������
	private String yhdksq;
	private String jgjscl_57;
	private String jtsjqr_58;
	private String yhsjqr_59;
	private String yhspjg_60;
	private String yhfkjg_61;
	private String skqr_62;
	private String bcclqr_63;
	private String bccl_64;
	//20.��˾�鵵
	private String gsgd;
	private String jtzzgd_67;
	private String zzgd_68;
	private String shybcl_69;
	private String xzrk_70;
	//21.��Ѻ�鵵
	private String dygd;
	private String gzjl_73;
	private String dycljsjg_74;
	private String jgsjqr_75;
	private String dyqkjl_76;
	private String dycljh_77;
	private String shsjqr_78;
	private String dyclzyh_79;
	private String yhsjqr_80;
	private String lryhcyqk_81;
	//22.ҵ����Ϣ�޸�
	private String ywxxxg;
	private String ywglb_84;
	private String xtyw_85;
	private String ywxxxgsq_96;
	//23.�˵��˷�
	private String tdtf;
	private String shytdsh_88;
	private String tdsjxz_89;
	private String shjltdsh_90;
	private String jghkjf_91;
	private String gsqrdz_92;
	private String cljh_93;
	private String jgsjqr_94;
	//24.�ͻ�����
	private String khgl;
	private String zdrxx;
	private String ghrxx;
	private String qtlxr;
	private String srxx;
	private String jtxx;
	private String fcxx;
	//25.�������
	private String dkgl;
	//26.������Ϣ
	private String clxx;
	//27.�������
	private String zzsc	;
	private String sfrz	;
	private String sjhm	;
	private String mzmd;
	private String grfxxx;
	private String bjgcxxx;
	private String fqzbg;
	private String zxbg;
	//28.Ӱ������
	private String yycl;
	private String yycl11;
	private String yycl2;
	private String yycl3;
	private String yycl4;
	private String yycl5;
	private String yycl6;
	private String yycl7;
	private String yycl8;
	private String yycl9;
	private String yycl10;
	//29.�շ���ϸ
	private String sfmx;
	//30.�շ���ϸ
	private String clhsqk;
	//31.����ģ��
	private String scmb;
	//32.������
	private String rwcl;
	//33.2018-08-30 ��������
	private String financing;
	private String financing_101;
	private String financing_102;
	private String financing_103;
	//Ȩ�޼���
	private String gpurview_map;
	//Ȩ�����ַ���
	public String getGpurview_map() {
		gpurview_map=glzx+","
                +gzrw+","+wdrw+","+wdcy+","+wdqd+","+qbrw+","
                +zhgl+","+gsgl+","+rygl+","+zhqx+","+gsgladd+","+gsgldelete+","+gsglupdate+","+zhqxadd+","+zhqxdelete+","+zhqxupdate+","+rygladd+","+rygldelete+","+ryglupdate+","
                +wlghd+","+zx+","+qcpg+","+kk+","+ssmq+","+qcdk+","
                +dhgl+","+yhhkxq+","+yhhklr+","+dclcjyq+","+dclzjyq+","+dclgjyq+","+dcltc+","+dclgs+","+dhx+","+yhx+","
                +cwgl+","+khdkmx+","
                +zxsp+","
                +clgc+","
                +zxcx+","+cxjg_3+","
                +zxtr+","+zxyhyj_6+","+trsh_7+","
                +clpg+","+pgjsh_11+","
                +yhds+","+dsjg_15+","
                +kksq+","+sfhcjg_19+","+fkkkjg_20+","
                +spmq+","+jgfk_24+","
                +kqyywsp+","+zgsh_27+","+zjlsh_29+","
                +qcdksh+","+zysh_33+","+zgsh_35+","+jlsh_37+","+zjsh_39+","
                +nstr+","+jgzjl_42+","+trshyj_43+","+trzg_44+","+trjl_45+","
                +zjfp+","+qrsqdz_48+","+zjfp_49+","+cz_50+","+sslr_51+","+cwqrdz_52+","
                +yhdksq+","+jgjscl_57+","+jtsjqr_58+","+yhsjqr_59+","+yhspjg_60+","+yhfkjg_61+","+skqr_62+","+bcclqr_63+","+bccl_64+","
                +gsgd+","+jtzzgd_67+","+zzgd_68+","+shybcl_69+","+xzrk_70+","
                +dygd+","+gzjl_73+","+dycljsjg_74+","+jgsjqr_75+","+dyqkjl_76+","+dycljh_77+","+shsjqr_78+","+dyclzyh_79+","+yhsjqr_80+","+lryhcyqk_81+","
                +ywxxxg+","+ywglb_84+","+xtyw_85+","+ywxxxgsq_96+","
                +tdtf+","+shytdsh_88+","+tdsjxz_89+","+shjltdsh_90+","+jghkjf_91+","+gsqrdz_92+","+cljh_93+","+jgsjqr_94+","
                +khgl+","+zdrxx+","+ghrxx+","+qtlxr+","+srxx+","+jtxx+","+fcxx+","
                +dkgl+","
                +clxx+","
                +zzsc+","+sfrz+","+sjhm+","+mzmd+","+grfxxx+","+bjgcxxx+","+fqzbg+","+zxbg+","
                +yycl+","+yycl11+","+yycl2+","+yycl3+","+yycl4+","+yycl5+","+yycl6+","+yycl7+","+yycl8+","+yycl9+","+yycl10+","
                +sfmx+","
                +clhsqk+","
                +rwcl+","
                +financing+","+financing_101+","+financing_102+","+financing_103+","
                ;
		return gpurview_map.replace("null,","");
	}

	public void setGpurview_map(String gpurview_map) {
		this.gpurview_map = gpurview_map;
	}
	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getQn() {
		return qn;
	}

	public void setQn(String qn) {
		this.qn = qn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGlzx() {
		return glzx;
	}

	public void setGlzx(String glzx) {
		this.glzx = glzx;
	}

	public String getGzrw() {
		return gzrw;
	}

	public void setGzrw(String gzrw) {
		this.gzrw = gzrw;
	}

	public String getWdrw() {
		return wdrw;
	}

	public void setWdrw(String wdrw) {
		this.wdrw = wdrw;
	}

	public String getWdcy() {
		return wdcy;
	}

	public void setWdcy(String wdcy) {
		this.wdcy = wdcy;
	}

	public String getWdqd() {
		return wdqd;
	}

	public void setWdqd(String wdqd) {
		this.wdqd = wdqd;
	}

	public String getZhgl() {
		return zhgl;
	}

	public void setZhgl(String zhgl) {
		this.zhgl = zhgl;
	}

	public String getGsgl() {
		return gsgl;
	}

	public void setGsgl(String gsgl) {
		this.gsgl = gsgl;
	}

	public String getRygl() {
		return rygl;
	}

	public void setRygl(String rygl) {
		this.rygl = rygl;
	}

	public String getZhqx() {
		return zhqx;
	}

	public void setZhqx(String zhqx) {
		this.zhqx = zhqx;
	}

	public String getWlghd() {
		return wlghd;
	}

	public void setWlghd(String wlghd) {
		this.wlghd = wlghd;
	}

	public String getZx() {
		return zx;
	}

	public void setZx(String zx) {
		this.zx = zx;
	}

	public String getQcpg() {
		return qcpg;
	}

	public void setQcpg(String qcpg) {
		this.qcpg = qcpg;
	}

	public String getKk() {
		return kk;
	}

	public void setKk(String kk) {
		this.kk = kk;
	}

	public String getSsmq() {
		return ssmq;
	}

	public void setSsmq(String ssmq) {
		this.ssmq = ssmq;
	}

	public String getQcdk() {
		return qcdk;
	}

	public void setQcdk(String qcdk) {
		this.qcdk = qcdk;
	}

	public String getDhgl() {
		return dhgl;
	}

	public void setDhgl(String dhgl) {
		this.dhgl = dhgl;
	}

	public String getYhhkxq() {
		return yhhkxq;
	}

	public void setYhhkxq(String yhhkxq) {
		this.yhhkxq = yhhkxq;
	}

	public String getYhhklr() {
		return yhhklr;
	}

	public void setYhhklr(String yhhklr) {
		this.yhhklr = yhhklr;
	}

	public String getCwgl() {
		return cwgl;
	}

	public void setCwgl(String cwgl) {
		this.cwgl = cwgl;
	}

	public String getKhdkmx() {
		return khdkmx;
	}

	public void setKhdkmx(String khdkmx) {
		this.khdkmx = khdkmx;
	}

	public String get() {
		return zxsp;
	}

	public void setZxsp(String zxsp) {
		this.zxsp = zxsp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClgc() {
		return clgc;
	}

	public void setClgc(String clgc) {
		this.clgc = clgc;
	}

	public String getZxcx() {
		return zxcx;
	}

	public void setZxcx(String zxcx) {
		this.zxcx = zxcx;
	}

	public String getCxjg_3() {
		return cxjg_3;
	}

	public void setCxjg_3(String cxjg_3) {
		this.cxjg_3 = cxjg_3;
	}

	public String getZxtr() {
		return zxtr;
	}

	public void setZxtr(String zxtr) {
		this.zxtr = zxtr;
	}

	public String getZxyhyj_6() {
		return zxyhyj_6;
	}

	public void setZxyhyj_6(String zxyhyj_6) {
		this.zxyhyj_6 = zxyhyj_6;
	}

	public String getTrsh_7() {
		return trsh_7;
	}

	public void setTrsh_7(String trsh_7) {
		this.trsh_7 = trsh_7;
	}

	public String getClpg() {
		return clpg;
	}

	public void setClpg(String clpg) {
		this.clpg = clpg;
	}

	public String getPgjsh_11() {
		return pgjsh_11;
	}

	public void setPgjsh_11(String pgjsh_11) {
		this.pgjsh_11 = pgjsh_11;
	}

	public String getYhds() {
		return yhds;
	}

	public void setYhds(String yhds) {
		this.yhds = yhds;
	}

	public String getDsjg_15() {
		return dsjg_15;
	}

	public void setDsjg_15(String dsjg_15) {
		this.dsjg_15 = dsjg_15;
	}

	public String getKksq() {
		return kksq;
	}

	public void setKksq(String kksq) {
		this.kksq = kksq;
	}

	public String getSfhcjg_19() {
		return sfhcjg_19;
	}

	public void setSfhcjg_19(String sfhcjg_19) {
		this.sfhcjg_19 = sfhcjg_19;
	}

	public String getFkkkjg_20() {
		return fkkkjg_20;
	}

	public void setFkkkjg_20(String fkkkjg_20) {
		this.fkkkjg_20 = fkkkjg_20;
	}

	public String getSpmq() {
		return spmq;
	}

	public void setSpmq(String spmq) {
		this.spmq = spmq;
	}

	public String getJgfk_24() {
		return jgfk_24;
	}

	public void setJgfk_24(String jgfk_24) {
		this.jgfk_24 = jgfk_24;
	}

	public String getKqyywsp() {
		return kqyywsp;
	}

	public void setKqyywsp(String kqyywsp) {
		this.kqyywsp = kqyywsp;
	}

	public String getZgsh_27() {
		return zgsh_27;
	}

	public void setZgsh_27(String zgsh_27) {
		this.zgsh_27 = zgsh_27;
	}

	public String getZjlsh_29() {
		return zjlsh_29;
	}

	public void setZjlsh_29(String zjlsh_29) {
		this.zjlsh_29 = zjlsh_29;
	}

	public String getQcdksh() {
		return qcdksh;
	}

	public void setQcdksh(String qcdksh) {
		this.qcdksh = qcdksh;
	}

	public String getZysh_33() {
		return zysh_33;
	}

	public void setZysh_33(String zysh_33) {
		this.zysh_33 = zysh_33;
	}

	public String getZgsh_35() {
		return zgsh_35;
	}

	public void setZgsh_35(String zgsh_35) {
		this.zgsh_35 = zgsh_35;
	}

	public String getJlsh_37() {
		return jlsh_37;
	}

	public void setJlsh_37(String jlsh_37) {
		this.jlsh_37 = jlsh_37;
	}

	public String getZjsh_39() {
		return zjsh_39;
	}

	public void setZjsh_39(String zjsh_39) {
		this.zjsh_39 = zjsh_39;
	}

	public String getNstr() {
		return nstr;
	}

	public void setNstr(String nstr) {
		this.nstr = nstr;
	}

	public String getJgzjl_42() {
		return jgzjl_42;
	}

	public void setJgzjl_42(String jgzjl_42) {
		this.jgzjl_42 = jgzjl_42;
	}

	public String getTrshyj_43() {
		return trshyj_43;
	}

	public void setTrshyj_43(String trshyj_43) {
		this.trshyj_43 = trshyj_43;
	}

	public String getTrzg_44() {
		return trzg_44;
	}

	public void setTrzg_44(String trzg_44) {
		this.trzg_44 = trzg_44;
	}

	public String getTrjl_45() {
		return trjl_45;
	}

	public void setTrjl_45(String trjl_45) {
		this.trjl_45 = trjl_45;
	}

	public String getZjfp() {
		return zjfp;
	}

	public void setZjfp(String zjfp) {
		this.zjfp = zjfp;
	}

	public String getQrsqdz_48() {
		return qrsqdz_48;
	}

	public void setQrsqdz_48(String qrsqdz_48) {
		this.qrsqdz_48 = qrsqdz_48;
	}

	public String getZjfp_49() {
		return zjfp_49;
	}

	public void setZjfp_49(String zjfp_49) {
		this.zjfp_49 = zjfp_49;
	}

	public String getCz_50() {
		return cz_50;
	}

	public void setCz_50(String cz_50) {
		this.cz_50 = cz_50;
	}

	public String getSslr_51() {
		return sslr_51;
	}

	public void setSslr_51(String sslr_51) {
		this.sslr_51 = sslr_51;
	}

	public String getCwqrdz_52() {
		return cwqrdz_52;
	}

	public void setCwqrdz_52(String cwqrdz_52) {
		this.cwqrdz_52 = cwqrdz_52;
	}

	public String getYhdksq() {
		return yhdksq;
	}

	public void setYhdksq(String yhdksq) {
		this.yhdksq = yhdksq;
	}

	public String getJgjscl_57() {
		return jgjscl_57;
	}

	public void setJgjscl_57(String jgjscl_57) {
		this.jgjscl_57 = jgjscl_57;
	}

	public String getJtsjqr_58() {
		return jtsjqr_58;
	}

	public void setJtsjqr_58(String jtsjqr_58) {
		this.jtsjqr_58 = jtsjqr_58;
	}

	public String getYhsjqr_59() {
		return yhsjqr_59;
	}

	public void setYhsjqr_59(String yhsjqr_59) {
		this.yhsjqr_59 = yhsjqr_59;
	}

	public String getYhspjg_60() {
		return yhspjg_60;
	}

	public void setYhspjg_60(String yhspjg_60) {
		this.yhspjg_60 = yhspjg_60;
	}

	public String getYhfkjg_61() {
		return yhfkjg_61;
	}

	public void setYhfkjg_61(String yhfkjg_61) {
		this.yhfkjg_61 = yhfkjg_61;
	}

	public String getSkqr_62() {
		return skqr_62;
	}

	public void setSkqr_62(String skqr_62) {
		this.skqr_62 = skqr_62;
	}

	public String getBcclqr_63() {
		return bcclqr_63;
	}

	public void setBcclqr_63(String bcclqr_63) {
		this.bcclqr_63 = bcclqr_63;
	}

	public String getBccl_64() {
		return bccl_64;
	}

	public void setBccl_64(String bccl_64) {
		this.bccl_64 = bccl_64;
	}

	public String getGsgd() {
		return gsgd;
	}

	public void setGsgd(String gsgd) {
		this.gsgd = gsgd;
	}

	public String getJtzzgd_67() {
		return jtzzgd_67;
	}

	public void setJtzzgd_67(String jtzzgd_67) {
		this.jtzzgd_67 = jtzzgd_67;
	}

	public String getZzgd_68() {
		return zzgd_68;
	}

	public void setZzgd_68(String zzgd_68) {
		this.zzgd_68 = zzgd_68;
	}

	public String getShybcl_69() {
		return shybcl_69;
	}

	public void setShybcl_69(String shybcl_69) {
		this.shybcl_69 = shybcl_69;
	}

	public String getXzrk_70() {
		return xzrk_70;
	}

	public void setXzrk_70(String xzrk_70) {
		this.xzrk_70 = xzrk_70;
	}

	public String getDygd() {
		return dygd;
	}

	public void setDygd(String dygd) {
		this.dygd = dygd;
	}

	public String getGzjl_73() {
		return gzjl_73;
	}

	public void setGzjl_73(String gzjl_73) {
		this.gzjl_73 = gzjl_73;
	}

	public String getDycljsjg_74() {
		return dycljsjg_74;
	}

	public void setDycljsjg_74(String dycljsjg_74) {
		this.dycljsjg_74 = dycljsjg_74;
	}

	public String getJgsjqr_75() {
		return jgsjqr_75;
	}

	public void setJgsjqr_75(String jgsjqr_75) {
		this.jgsjqr_75 = jgsjqr_75;
	}

	public String getDyqkjl_76() {
		return dyqkjl_76;
	}

	public void setDyqkjl_76(String dyqkjl_76) {
		this.dyqkjl_76 = dyqkjl_76;
	}

	public String getDycljh_77() {
		return dycljh_77;
	}

	public void setDycljh_77(String dycljh_77) {
		this.dycljh_77 = dycljh_77;
	}

	public String getShsjqr_78() {
		return shsjqr_78;
	}

	public void setShsjqr_78(String shsjqr_78) {
		this.shsjqr_78 = shsjqr_78;
	}

	public String getDyclzyh_79() {
		return dyclzyh_79;
	}

	public void setDyclzyh_79(String dyclzyh_79) {
		this.dyclzyh_79 = dyclzyh_79;
	}

	public String getYhsjqr_80() {
		return yhsjqr_80;
	}

	public void setYhsjqr_80(String yhsjqr_80) {
		this.yhsjqr_80 = yhsjqr_80;
	}

	public String getLryhcyqk_81() {
		return lryhcyqk_81;
	}

	public void setLryhcyqk_81(String lryhcyqk_81) {
		this.lryhcyqk_81 = lryhcyqk_81;
	}

	public String getYwxxxg() {
		return ywxxxg;
	}

	public void setYwxxxg(String ywxxxg) {
		this.ywxxxg = ywxxxg;
	}

	public String getYwglb_84() {
		return ywglb_84;
	}

	public void setYwglb_84(String ywglb_84) {
		this.ywglb_84 = ywglb_84;
	}

	public String getXtyw_85() {
		return xtyw_85;
	}

	public void setXtyw_85(String xtyw_85) {
		this.xtyw_85 = xtyw_85;
	}

	public String getYwxxxgsq_96() {
		return ywxxxgsq_96;
	}

	public void setYwxxxgsq_96(String ywxxxgsq_96) {
		this.ywxxxgsq_96 = ywxxxgsq_96;
	}

	public String getTdtf() {
		return tdtf;
	}

	public void setTdtf(String tdtf) {
		this.tdtf = tdtf;
	}

	public String getShytdsh_88() {
		return shytdsh_88;
	}

	public void setShytdsh_88(String shytdsh_88) {
		this.shytdsh_88 = shytdsh_88;
	}

	public String getTdsjxz_89() {
		return tdsjxz_89;
	}

	public void setTdsjxz_89(String tdsjxz_89) {
		this.tdsjxz_89 = tdsjxz_89;
	}

	public String getShjltdsh_90() {
		return shjltdsh_90;
	}

	public void setShjltdsh_90(String shjltdsh_90) {
		this.shjltdsh_90 = shjltdsh_90;
	}

	public String getJghkjf_91() {
		return jghkjf_91;
	}

	public void setJghkjf_91(String jghkjf_91) {
		this.jghkjf_91 = jghkjf_91;
	}

	public String getGsqrdz_92() {
		return gsqrdz_92;
	}

	public void setGsqrdz_92(String gsqrdz_92) {
		this.gsqrdz_92 = gsqrdz_92;
	}

	public String getCljh_93() {
		return cljh_93;
	}

	public void setCljh_93(String cljh_93) {
		this.cljh_93 = cljh_93;
	}

	public String getJgsjqr_94() {
		return jgsjqr_94;
	}

	public void setJgsjqr_94(String jgsjqr_94) {
		this.jgsjqr_94 = jgsjqr_94;
	}

	public String getKhgl() {
		return khgl;
	}

	public void setKhgl(String khgl) {
		this.khgl = khgl;
	}

	public String getZdrxx() {
		return zdrxx;
	}

	public void setZdrxx(String zdrxx) {
		this.zdrxx = zdrxx;
	}

	public String getGhrxx() {
		return ghrxx;
	}

	public void setGhrxx(String ghrxx) {
		this.ghrxx = ghrxx;
	}

	public String getQtlxr() {
		return qtlxr;
	}

	public void setQtlxr(String qtlxr) {
		this.qtlxr = qtlxr;
	}

	public String getSrxx() {
		return srxx;
	}

	public void setSrxx(String srxx) {
		this.srxx = srxx;
	}

	public String getJtxx() {
		return jtxx;
	}

	public void setJtxx(String jtxx) {
		this.jtxx = jtxx;
	}

	public String getFcxx() {
		return fcxx;
	}

	public void setFcxx(String fcxx) {
		this.fcxx = fcxx;
	}

	public String getDkgl() {
		return dkgl;
	}

	public void setDkgl(String dkgl) {
		this.dkgl = dkgl;
	}

	public String getClxx() {
		return clxx;
	}

	public void setClxx(String clxx) {
		this.clxx = clxx;
	}

	public String getZzsc() {
		return zzsc;
	}

	public void setZzsc(String zzsc) {
		this.zzsc = zzsc;
	}

	public String getSfrz() {
		return sfrz;
	}

	public void setSfrz(String sfrz) {
		this.sfrz = sfrz;
	}

	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	public String getMzmd() {
		return mzmd;
	}

	public void setMzmd(String mzmd) {
		this.mzmd = mzmd;
	}

	public String getGrfxxx() {
		return grfxxx;
	}

	public void setGrfxxx(String grfxxx) {
		this.grfxxx = grfxxx;
	}

	public String getBjgcxxx() {
		return bjgcxxx;
	}

	public void setBjgcxxx(String bjgcxxx) {
		this.bjgcxxx = bjgcxxx;
	}

	public String getFqzbg() {
		return fqzbg;
	}

	public void setFqzbg(String fqzbg) {
		this.fqzbg = fqzbg;
	}

	public String getZxbg() {
		return zxbg;
	}

	public void setZxbg(String zxbg) {
		this.zxbg = zxbg;
	}

	public String getYycl() {
		return yycl;
	}

	public void setYycl(String yycl) {
		this.yycl = yycl;
	}

	public String getSfmx() {
		return sfmx;
	}

	public void setSfmx(String sfmx) {
		this.sfmx = sfmx;
	}

	public String getZxsp() {
		return zxsp;
	}

	public String getClhsqk() {
		return clhsqk;
	}

	public void setClhsqk(String clhsqk) {
		this.clhsqk = clhsqk;
	}

	public String getScmb() {
		return scmb;
	}

	public void setScmb(String scmb) {
		this.scmb = scmb;
	}

	public String getRwcl() {
		return rwcl;
	}

	public void setRwcl(String rwcl) {
		this.rwcl = rwcl;
	}

	public String getFinancing() {
		return financing;
	}

	public void setFinancing(String financing) {
		this.financing = financing;
	}

	public String getFinancing_101() {
		return financing_101;
	}

	public void setFinancing_101(String financing_101) {
		this.financing_101 = financing_101;
	}

	public String getFinancing_102() {
		return financing_102;
	}

	public void setFinancing_102(String financing_102) {
		this.financing_102 = financing_102;
	}

	public String getFinancing_103() {
		return financing_103;
	}

	public void setFinancing_103(String financing_103) {
		this.financing_103 = financing_103;
	}

	public String getQbrw() {
		return qbrw;
	}

	public void setQbrw(String qbrw) {
		this.qbrw = qbrw;
	}

	public String getYycl11() {
		return yycl11;
	}

	public void setYycl11(String yycl11) {
		this.yycl11 = yycl11;
	}

	public String getYycl2() {
		return yycl2;
	}

	public void setYycl2(String yycl2) {
		this.yycl2 = yycl2;
	}

	public String getYycl3() {
		return yycl3;
	}

	public void setYycl3(String yycl3) {
		this.yycl3 = yycl3;
	}

	public String getYycl4() {
		return yycl4;
	}

	public void setYycl4(String yycl4) {
		this.yycl4 = yycl4;
	}

	public String getYycl5() {
		return yycl5;
	}

	public void setYycl5(String yycl5) {
		this.yycl5 = yycl5;
	}

	public String getYycl6() {
		return yycl6;
	}

	public void setYycl6(String yycl6) {
		this.yycl6 = yycl6;
	}

	public String getYycl7() {
		return yycl7;
	}

	public void setYycl7(String yycl7) {
		this.yycl7 = yycl7;
	}

	public String getYycl8() {
		return yycl8;
	}

	public void setYycl8(String yycl8) {
		this.yycl8 = yycl8;
	}

	public String getYycl9() {
		return yycl9;
	}

	public void setYycl9(String yycl9) {
		this.yycl9 = yycl9;
	}

	public String getYycl10() {
		return yycl10;
	}

	public void setYycl10(String yycl10) {
		this.yycl10 = yycl10;
	}

	public Integer getModal_tag() {
		return modal_tag;
	}

	public void setModal_tag(Integer modal_tag) {
		this.modal_tag = modal_tag;
	}

	public Integer getQx_type() {
		return qx_type;
	}

	public void setQx_type(Integer qx_type) {
		this.qx_type = qx_type;
	}

	public String getGsgladd() {
		return gsgladd;
	}

	public void setGsgladd(String gsgladd) {
		this.gsgladd = gsgladd;
	}

	public String getGsgldelete() {
		return gsgldelete;
	}

	public void setGsgldelete(String gsgldelete) {
		this.gsgldelete = gsgldelete;
	}

	public String getGsglupdate() {
		return gsglupdate;
	}

	public void setGsglupdate(String gsglupdate) {
		this.gsglupdate = gsglupdate;
	}

	public String getZhqxadd() {
		return zhqxadd;
	}

	public void setZhqxadd(String zhqxadd) {
		this.zhqxadd = zhqxadd;
	}

	public String getZhqxdelete() {
		return zhqxdelete;
	}

	public void setZhqxdelete(String zhqxdelete) {
		this.zhqxdelete = zhqxdelete;
	}

	public String getZhqxupdate() {
		return zhqxupdate;
	}

	public void setZhqxupdate(String zhqxupdate) {
		this.zhqxupdate = zhqxupdate;
	}

	public String getRygladd() {
		return rygladd;
	}

	public void setRygladd(String rygladd) {
		this.rygladd = rygladd;
	}

	public String getRygldelete() {
		return rygldelete;
	}

	public void setRygldelete(String rygldelete) {
		this.rygldelete = rygldelete;
	}

	public String getRyglupdate() {
		return ryglupdate;
	}

	public void setRyglupdate(String ryglupdate) {
		this.ryglupdate = ryglupdate;
	}

	public String getDclcjyq() {
		return dclcjyq;
	}

	public void setDclcjyq(String dclcjyq) {
		this.dclcjyq = dclcjyq;
	}

	public String getDclzjyq() {
		return dclzjyq;
	}

	public void setDclzjyq(String dclzjyq) {
		this.dclzjyq = dclzjyq;
	}

	public String getDclgjyq() {
		return dclgjyq;
	}

	public void setDclgjyq(String dclgjyq) {
		this.dclgjyq = dclgjyq;
	}

	public String getDcltc() {
		return dcltc;
	}

	public void setDcltc(String dcltc) {
		this.dcltc = dcltc;
	}

	public String getDclgs() {
		return dclgs;
	}

	public void setDclgs(String dclgs) {
		this.dclgs = dclgs;
	}

	public String getDhx() {
		return dhx;
	}

	public void setDhx(String dhx) {
		this.dhx = dhx;
	}

	public String getYhx() {
		return yhx;
	}

	public void setYhx(String yhx) {
		this.yhx = yhx;
	}


	
	

}
