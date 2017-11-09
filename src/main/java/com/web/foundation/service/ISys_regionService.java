package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;
import com.web.foundation.domain.Sys_region;

public interface ISys_regionService {
	/**
	 * 保存一个Sys_region，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Sys_region instance);
	
	/**
	 * 根据一个ID得到Sys_region
	 * 
	 * @param id
	 * @return
	 */
	Sys_region getObjById(Long id);
	
	/**
	 * 删除一个Sys_region
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Sys_region
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Sys_region
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Sys_region
	 * 
	 * @param id
	 *            需要更新的Sys_region的id
	 * @param dir
	 *            需要更新的Sys_region
	 */
	boolean update(Sys_region instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Sys_region> query(String query, Map params, int begin, int max);
	/**
	 * 获得 国家 省份的 名称
	 * 
	 * */
	String getName(String string, Map params);
}
