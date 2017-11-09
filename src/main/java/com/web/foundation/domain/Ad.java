package com.web.foundation.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.web.core.domain.IdEntity;
@Entity
@Table(name ="ada_ad_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ad extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4555224656047060592L;
	private String name;//广告名称
	private String prefix;//广告链接
	private int siteId;//站点ID
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	
	
}
