package com.web.foundation.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.web.core.domain.IdEntity;
@Entity
@Table(name ="ada_access_stat_day_area")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
/**
 * 地域分析
 * @author ASUS
 *
 */
public class Region_day extends IdEntity{
	
	private Long siteId;//站点ID
	private Long countryId;//国家id
	private Long provinceId;//省份id
	private Long cityId;//城市id
	private Long domainId;//域名id
	
	private Long pv;//访问量
	private Long uv;//独立访客
	private Long ip;//独立ip
	private Long epv;//异常访问量
	private Long euv;//异常独立访客
	private Long eip;//异常独立ip
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	
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
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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
	public Long getDomainId() {
		return domainId;
	}
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}
	public Long getPv() {
		return pv;
	}
	public void setPv(Long pv) {
		this.pv = pv;
	}
	public Long getUv() {
		return uv;
	}
	public void setUv(Long uv) {
		this.uv = uv;
	}
	public Long getIp() {
		return ip;
	}
	public void setIp(Long ip) {
		this.ip = ip;
	}
	public Long getEpv() {
		return epv;
	}
	public void setEpv(Long epv) {
		this.epv = epv;
	}
	public Long getEuv() {
		return euv;
	}
	public void setEuv(Long euv) {
		this.euv = euv;
	}
	public Long getEip() {
		return eip;
	}
	public void setEip(Long eip) {
		this.eip = eip;
	}
	
	
	
}
