package com.web.foundation.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.web.core.domain.IdEntity;
@Entity
@Table(name ="ada_collect_access_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
/**
 * 访问明细
 * @author ASUS
 *
 */
public class VisitDetails extends IdEntity{
	
	private Long siteId;//站点ID
	private String remoteIp;//远程ip（请求ip）
	private Long countryId;//国家id
	private Long provinceId;//省份id
	private Long cityId;//城市id
	
	private String referer;//被请求页面
	
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
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
	
	
	
	
}
