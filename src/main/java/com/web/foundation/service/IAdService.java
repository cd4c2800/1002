package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;

import com.web.foundation.domain.Ad;

public interface IAdService {
	/**
	 * 保存一个Ad，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Ad instance);
	
	/**
	 * 根据一个ID得到Ad
	 * 
	 * @param id
	 * @return
	 */
	Ad getObjById(Long id);
	
	/**
	 * 删除一个Ad
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Ad
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Ad
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Ad
	 * 
	 * @param id
	 *            需要更新的Ad的id
	 * @param dir
	 *            需要更新的Ad
	 */
	boolean update(Ad instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Ad> query(String query, Map params, int begin, int max);
}
