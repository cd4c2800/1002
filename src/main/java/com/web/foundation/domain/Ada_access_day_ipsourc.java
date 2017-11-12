package com.web.foundation.domain;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.web.core.domain.IdEntity;
@Entity
@Table(name ="ada_access_day_ipsourc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ada_access_day_ipsourc extends IdEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3458200688944673732L;

    private Integer siteId;

    private Integer domainId;

    private Integer adId;

    private Integer referersId;

    private String remoteIp;

    private Integer epv;

    private String exceType;

    private Date startTime;

    private Date endTime;

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

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public Integer getReferersId() {
		return referersId;
	}

	public void setReferersId(Integer referersId) {
		this.referersId = referersId;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public Integer getEpv() {
		return epv;
	}

	public void setEpv(Integer epv) {
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