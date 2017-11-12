package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;

import com.web.foundation.domain.Ada_access_day_ipsourc;

public interface IAda_access_day_ipsourcService {
	/**
	 * 保存一个Ada_access_day_ipsourc，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Ada_access_day_ipsourc instance);
	
	/**
	 * 根据一个ID得到Ada_access_day_ipsourc
	 * 
	 * @param id
	 * @return
	 */
	Ada_access_day_ipsourc getObjById(Long id);
	
	/**
	 * 删除一个Ada_access_day_ipsourc
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Ada_access_day_ipsourc
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Ada_access_day_ipsourc
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Ada_access_day_ipsourc
	 * 
	 * @param id
	 *            需要更新的Ada_access_day_ipsourc的id
	 * @param dir
	 *            需要更新的Ada_access_day_ipsourc
	 */
	boolean update(Ada_access_day_ipsourc instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Ada_access_day_ipsourc> query(String query, Map params, int begin, int max);
}
