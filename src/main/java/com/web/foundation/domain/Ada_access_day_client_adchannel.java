package com.web.foundation.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.web.core.domain.IdEntity;
@Entity
@Table(name ="ada_access_day_client_adchannel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ada_access_day_client_adchannel extends IdEntity{
	
	private int siteId;//站点ID
	private int domainId;//域名id
	private int adId;//广告id
	private int epv;//异常访问数
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private String clientId;//ip
	private String exceType;//异常类型
	private int channelId;//渠道id
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public int getDomainId() {
		return domainId;
	}
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	public int getAdId() {
		return adId;
	}
	public void setAdId(int adId) {
		this.adId = adId;
	}
	public int getEpv() {
		return epv;
	}
	public void setEpv(int epv) {
		this.epv = epv;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getExceType() {
		return exceType;
	}
	public void setExceType(String exceType) {
		this.exceType = exceType;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	
	
}
