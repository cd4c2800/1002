package com.web.foundation.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.web.core.constant.Globals;
import com.web.core.domain.IdEntity;
@Entity
@Table(name ="ada_access_total_day")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Total_day extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3563434804634955322L;
	private Integer siteId;//站点ID
	private Integer domainId;//域名id
	private Integer ip;//独立ip数
	private Integer uv;//独立客户
	private Integer pv;//总访问数量
	private Integer eip;//异常ip数
	private Integer euv;//异常客户数
	private Integer epv;//异常访问数
	private Date startTime;//开始时间
	private Date endTime;//结束时间

	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Integer getDomainId() {
		return domainId;
	}
	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}
	public Integer getIp() {
		return ip;
	}
	public void setIp(Integer ip) {
		this.ip = ip;
	}
	public Integer getUv() {
		return uv;
	}
	public void setUv(Integer uv) {
		this.uv = uv;
	}
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	public Integer getEip() {
		return eip;
	}
	public void setEip(Integer eip) {
		this.eip = eip;
	}
	public Integer getEuv() {
		return euv;
	}
	public void setEuv(Integer euv) {
		this.euv = euv;
	}
	public Integer getEpv() {
		return epv;
	}
	public void setEpv(Integer epv) {
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
