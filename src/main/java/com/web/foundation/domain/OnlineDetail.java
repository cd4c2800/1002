package com.web.foundation.domain;

import java.io.Serializable;

/**
 * 当前在线
 * 
 * */
public class OnlineDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4043577916621415237L;
	private String accessTime;
	private String remoteIp;
	private String addr;
	private String channel;
	private String userAgent;
	private String accessUrl;
	
	
	public OnlineDetail(String accessTime, String remoteIp, String addr,
			String channel, String userAgent, String accessUrl) {
		super();
		this.accessTime = accessTime;
		this.remoteIp = remoteIp;
		this.addr = addr;
		this.channel = channel;
		this.userAgent = userAgent;
		this.accessUrl = accessUrl;
	}
	public String getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getAccessUrl() {
		return accessUrl;
	}
	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}
	
}
