package com.web.foundation.domain;



import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.web.core.domain.IdEntity;
/**
 * 广告渠道
 * 
 * */
@Entity
@Table(name ="ada_ad_channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ada_ad_channel extends IdEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -258728828896049697L;
    	
    private Integer siteId;	//站点

    private Integer adId;	//广告ID

    private String name;	//渠道名称

    private Integer rule;	//匹配规则{1:URL参数}

    private String content;	//规则内容{p=a}




  

    public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRule() {
        return rule;
    }

    public void setRule(Integer rule) {
        this.rule = rule;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }


}