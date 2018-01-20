package com.huas.enumeration;

/**
 * @author: GaoFeng 
 * @date: 2016-7-8下午12:00:53
 *@todo : 视频来源网站
 */	

public enum Constants {
	
	youku("youku.com","优酷网"),
	tudou("tudou.com","土豆网"),
	iqiyi("iqiyi.com","爱奇艺"),
	qq("qq.com","腾讯网"),
	ku6("ku6.com","酷6网"), 
	letv("le.com","乐视网"), 
	sohu("sohu.com","搜狐网"),
	ifeng("ifeng.com","凤凰网"),
	yinyuetai("yinyuetai.com","音悦台"),
	sina("sina.com","新浪网");
	
	private String url;
	private String msg;
	
	Constants(String url, String msg) {
		this.url = url;
		this.msg = msg;
	}
	public String getUrl() {
		return url;
	}
	public String getMsg() {
		return msg;
	}

	public static Constants getValueOf(Object ordinal) {
		int num = Integer.valueOf(ordinal.toString());
		return Constants.values()[num];
	}
}
