package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;

import com.web.foundation.domain.Region_day;

public interface IRegion_dayService {
	/**
	 * 保存一个Region_day，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Region_day instance);
	
	/**
	 * 根据一个ID得到Region_day
	 * 
	 * @param id
	 * @return
	 */
	Region_day getObjById(Long id);
	
	/**
	 * 删除一个Region_day
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Region_day
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Region_day
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Region_day
	 * 
	 * @param id
	 *            需要更新的Region_day的id
	 * @param dir
	 *            需要更新的Region_day
	 */
	boolean update(Region_day instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Region_day> query(String query, Map params, int begin, int max);
}
