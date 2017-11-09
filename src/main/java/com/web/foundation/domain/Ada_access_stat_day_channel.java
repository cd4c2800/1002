package com.web.foundation.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.web.core.domain.IdEntity;
@Entity
@Table(name ="ada_access_stat_day_channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ada_access_stat_day_channel extends IdEntity{
	
	private int siteId;//站点ID
	private int domainId;//域名id
	private int channelId;//渠道ID
	private int ip;//独立ip数
	private int uv;//独立客户
	private int pv;//总访问数量
	private int eip;//异常ip数
	private int euv;//异常客户数
	private int epv;//异常访问数
	private Date startTime;//开始时间
	private Date endTime;//结束时间
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
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public int getIp() {
		return ip;
	}
	public void setIp(int ip) {
		this.ip = ip;
	}
	public int getUv() {
		return uv;
	}
	public void setUv(int uv) {
		this.uv = uv;
	}
	public int getPv() {
		return pv;
	}
	public void setPv(int pv) {
		this.pv = pv;
	}
	public int getEip() {
		return eip;
	}
	public void setEip(int eip) {
		this.eip = eip;
	}
	public int getEuv() {
		return euv;
	}
	public void setEuv(int euv) {
		this.euv = euv;
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
	
	
	
	
	
}
