package com.web.foundation.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.web.core.domain.IdEntity;
@Entity
@Table(name ="ada_collect_access_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ada_collect_access_log extends IdEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4488123971136050705L;
		
		private Integer siteId;	//站点

	    private Integer logType;//日志类型{1:脚本日志,2:客户日志}

	    private String requestId;//请求ID

	    private String clientId;	//客户端ID

	    private String userAgent;	//用户代理

	    private String requestUrl;	//请求地址

	    private String referer;		//引用地址

	    private String realReferer;	//真实引用

	    private String remoteIp;	//远程IP

	    private String realIp;		//真实IP

	    private String os;			//操作系统

	    private String domain;		//域名

	    private Integer countryId;	//国家

	    private Integer provinceId;	//省

	    private Integer cityId;		//城市

	    private Date accessTime;	//访问时间

		public Integer getSiteId() {
			return siteId;
		}

		public void setSiteId(Integer siteId) {
			this.siteId = siteId;
		}

		public Integer getLogType() {
			return logType;
		}

		public void setLogType(Integer logType) {
			this.logType = logType;
		}

		public String getRequestId() {
			return requestId;
		}

		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}

		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getUserAgent() {
			return userAgent;
		}

		public void setUserAgent(String userAgent) {
			this.userAgent = userAgent;
		}

		public String getRequestUrl() {
			return requestUrl;
		}

		public void setRequestUrl(String requestUrl) {
			this.requestUrl = requestUrl;
		}

		public String getReferer() {
			return referer;
		}

		public void setReferer(String referer) {
			this.referer = referer;
		}

		public String getRealReferer() {
			return realReferer;
		}

		public void setRealReferer(String realReferer) {
			this.realReferer = realReferer;
		}

		public String getRemoteIp() {
			return remoteIp;
		}

		public void setRemoteIp(String remoteIp) {
			this.remoteIp = remoteIp;
		}

		public String getRealIp() {
			return realIp;
		}

		public void setRealIp(String realIp) {
			this.realIp = realIp;
		}

		public String getOs() {
			return os;
		}

		public void setOs(String os) {
			this.os = os;
		}

		public String getDomain() {
			return domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}

		public Integer getCountryId() {
			return countryId;
		}

		public void setCountryId(Integer countryId) {
			this.countryId = countryId;
		}

		public Integer getProvinceId() {
			return provinceId;
		}

		public void setProvinceId(Integer provinceId) {
			this.provinceId = provinceId;
		}

		public Integer getCityId() {
			return cityId;
		}

		public void setCityId(Integer cityId) {
			this.cityId = cityId;
		}

		public Date getAccessTime() {
			return accessTime;
		}

		public void setAccessTime(Date accessTime) {
			this.accessTime = accessTime;
		}

		
	    



}
