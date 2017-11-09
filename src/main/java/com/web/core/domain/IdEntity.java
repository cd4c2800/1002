package com.web.core.domain;

import java.io.Serializable;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;





/**
 * 
 * <p>
 * Title: IdEntity.java
 * </p>
 * 
 * <p>
 * Description:
 * 系统域模型基类，该类包含3个常用字段，其中id为自增长类型，该类实现序列化，只有序列化后才可以实现tomcat集群配置session共享
 * </p>
 * 
 * @date 2014-4-24
 */
@MappedSuperclass
public class IdEntity implements Serializable {
	/**
	 * 序列化接口，自动生成序列号
	 */
	private static final long serialVersionUID = -7741168269971132706L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int id;// 域模型id，这里为自增类型
	private Date createTime;// 添加时间，这里为长时间格式


	public IdEntity() {
		super();
	}

	public IdEntity(int id, Date createTime) {
		super();
		this.id = id;
		this.createTime = createTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getCreateTime() {
		
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		
		this.createTime = createTime;
	}

	


}
