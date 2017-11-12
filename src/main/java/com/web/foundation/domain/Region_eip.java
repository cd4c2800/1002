package com.web.foundation.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.web.core.domain.IdEntity;
@Entity
@Table(name ="ada_access_day_iparea")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
/**
 * 地域分析-eip
 * @author ASUS
 *
 */
public class Region_eip extends IdEntity{
	
	private Long siteId;//站点ID
	private Long domainId;//域名id
	private Long adId;//广告id
	private Long countryId;//国家id
	private Long provinceId;//省份id
	private Long cityId;//城市id
	private String remoteIp;//远程ip（请求ip）
	private Long epv;//异常访问量
	private String exceType;//异常类型1:天天访问,2:高频访问,3:频繁切换ip,4:停留时间超短,5:疑似作弊软件,6:直接访问,7:频繁切换客户端,8:只访问广告
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Long getDomainId() {
		return domainId;
	}
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}
	public Long getAdId() {
		return adId;
	}
	public void setAdId(Long adId) {
		this.adId = adId;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public Long getEpv() {
		return epv;
	}
	public void setEpv(Long epv) {
		this.epv = epv;
	}
	public String getExceType() {
		return exceType;
	}
	public void setExceType(String exceType) {
		this.exceType = exceType;
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
