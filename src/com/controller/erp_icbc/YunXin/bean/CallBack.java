package com.controller.erp_icbc.YunXin.bean;
/**
 * @Description:TODO
 * @author:LiWang �ϴ��ص�ʵ����
 * @time:2018��8��22��
 */
public class CallBack {
	private String address;//¼�Ƶĵ�ַ
	private String viedotype;
	private String channelId;//�Զ���ͨ��id
	private String id;
	private String type;//�ص����ͣ��ϴ��ص��̶�Ϊ��upload��
	private Long vid;//��Ƶ�ļ���ʶ
	private String name;//��Ƶ�ļ����ƣ��ϴ�ʱָ����Ƶ���ƣ�
	private String origAddr;//��Ƶ�Ĳ��ŵ�ַ
	private String warnning;//��Ƶ���ͼ������������Ƶ���ͺ��ϴ�ָ����һ�£�mp4��flv���͵���Ƶ���ʺ���ý�岥���϶���
	private userDefined user_defined;//�û��Զ����ֶ�ֵ���ϴ�ʱ���õ�ֵ��
	public class userDefined{
		private String address;//��ַ
		private String latitude;//����
		private String longitude;
		private String id;//icbcid
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public String getLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
	}
	public String getViedotype() {
		return viedotype;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setViedotype(String viedotype) {
		this.viedotype = viedotype;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getVid() {
		return vid;
	}
	public void setVid(Long vid) {
		this.vid = vid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrigAddr() {
		return origAddr;
	}
	public void setOrigAddr(String origAddr) {
		this.origAddr = origAddr;
	}
	public String getWarnning() {
		return warnning;
	}
	public void setWarnning(String warnning) {
		this.warnning = warnning;
	}
	public userDefined getUser_defined() {
		return user_defined;
	}
	public void setUser_defined(userDefined user_defined) {
		this.user_defined = user_defined;
	}
}
