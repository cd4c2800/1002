package com.web.foundation.domain;



import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.web.core.domain.IdEntity;
/**
 * 来源列表
 * 
 * */
@Entity
@Table(name ="ada_referers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ada_referers extends IdEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1539376082140885401L;



    private String domain;	//域名

    private String name;	//名称




    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

}