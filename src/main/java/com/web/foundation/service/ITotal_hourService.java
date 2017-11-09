package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;

import com.web.foundation.domain.Total_hour;

public interface ITotal_hourService {
	/**
	 * 保存一个Total_hour，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Total_hour instance);
	
	/**
	 * 根据一个ID得到Total_hour
	 * 
	 * @param id
	 * @return
	 */
	Total_hour getObjById(Long id);
	
	/**
	 * 删除一个Total_hour
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Total_hour
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Total_hour
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Total_hour
	 * 
	 * @param id
	 *            需要更新的Total_hour的id
	 * @param dir
	 *            需要更新的Total_hour
	 */
	boolean update(Total_hour instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Total_hour> query(String query, Map params, int begin, int max);
}
