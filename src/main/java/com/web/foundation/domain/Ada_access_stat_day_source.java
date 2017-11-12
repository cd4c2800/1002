package com.web.foundation.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.web.core.domain.IdEntity;
@Entity
@Table(name ="ada_access_stat_day_source")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ada_access_stat_day_source extends IdEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9065450726249433735L;

	private Integer siteId;		//站点ID
	
    private Integer domainId;	//域名ID

    private Integer referersId;	//来源ID

    private Integer ip;			//独立IP数

    private Integer uv;			//独立访客

    private Integer pv;			//浏览次数

    private Integer eip;		//异常独立IP数

    private Integer euv;		//异常客户端

    private Integer epv;		//异常浏览次数

    private Date startTime;		//

    private Date endTime;		//

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

	public Integer getReferersId() {
		return referersId;
	}

	public void setReferersId(Integer referersId) {
		this.referersId = referersId;
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



}