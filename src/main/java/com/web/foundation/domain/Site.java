package com.web.foundation.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.web.core.constant.Globals;
import com.web.core.domain.IdEntity;
@Entity
@Table(name ="ada_site")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Site extends IdEntity{
	
	private String siteName;//站点名称
	private String siteHome;//站点首页
	private int user_id;
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteHome() {
		return siteHome;
	}
	public void setSiteHome(String siteHome) {
		this.siteHome = siteHome;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	
}
