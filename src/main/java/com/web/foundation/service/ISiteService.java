package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;
import com.web.foundation.domain.Site;


public interface ISiteService {
	/**
	 * 保存一个user，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Site instance);
	
	/**
	 * 根据一个ID得到user
	 * 
	 * @param id
	 * @return
	 */
	Site getObjById(Integer id);
	
	/**
	 * 删除一个user
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Integer id);
	
	/**
	 * 批量删除user
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到user
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个user
	 * 
	 * @param id
	 *            需要更新的user的id
	 * @param dir
	 *            需要更新的user
	 */
	boolean update(Site instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Site> query(String query, Map params, int begin, int max);
}
