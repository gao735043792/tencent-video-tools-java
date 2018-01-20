package com.huas.bean;

import java.io.Serializable;

import com.huas.enumeration.Constants;

/**
 *@author GaoFeng
 *@date 2016年6月20日上午10:05:49
 *@todo 视频信息实体
 */
public class Video implements Serializable {
	
  private static final long serialVersionUID = -6220973207083491817L;
  
  private String title;					//视频标题
  private String thumbnail;				//视频缩略图
  private String summary;				//视频概要描述
  private String time;					//视频时长
  private Constants source;				//视频来源
  private String pageUrl;				//视频链接地址
  private String address;				//视频地址
  private String commonCode;			//通用代码
  
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Constants getSource() {
		return source;
	}
	public void setSource(Constants source) {
		this.source = source;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCommonCode() {
		return commonCode;
	}
	public void setCommonCode(String commonCode) {
		this.commonCode = commonCode;
	}
	
	  
}