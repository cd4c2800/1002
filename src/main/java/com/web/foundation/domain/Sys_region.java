package com.web.foundation.domain;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.web.core.domain.IdEntity;

/**
 * 
 * 地区 实体
 * */
@Entity
@Table(name ="sys_region")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sys_region  extends IdEntity{

	private static final long serialVersionUID = -7597810101189737207L;


	private String name;  			//简称

	private String fullname; 	 	//全称

	private Integer level;   		//行政级别{1:省,2:市,3:区,4:街道}

	private String jianpin;			//简拼

	private String pinyin;   		//全拼

	private String zipCode; 		//邮政编码

	private Integer sort; 			//排序

	private Integer ishot;  		//是否热点{1:是,0:否}

	private String lng;   			//经度

	private String lat;   			//纬度

	private Integer parentId;  		//上级ID
	
	private Integer countryId;		//国家ID
	
	private String countryName;		//国家名称
	
	private Integer provinceId; 	//所属省ID

	private String provinceName; 	//所属省名称

	private Integer cityId;  		//所属市ID

	private String cityName;		//所属市名称

	private Integer districtId; 	//所属区县ID

	private String districtName; 	//所属区名称
	

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getJianpin() {
		return jianpin;
	}

	public void setJianpin(String jianpin) {
		this.jianpin = jianpin;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIshot() {
		return ishot;
	}

	public void setIshot(Integer ishot) {
		this.ishot = ishot;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}



}