package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;

import com.web.foundation.domain.Region_eip;

public interface IRegion_eipService {
	/**
	 * 保存一个Region_eip，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Region_eip instance);
	
	/**
	 * 根据一个ID得到Region_eip
	 * 
	 * @param id
	 * @return
	 */
	Region_eip getObjById(Long id);
	
	/**
	 * 删除一个Region_eip
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Region_eip
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Region_eip
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Region_eip
	 * 
	 * @param id
	 *            需要更新的Region_eip的id
	 * @param dir
	 *            需要更新的Region_eip
	 */
	boolean update(Region_eip instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Region_eip> query(String query, Map params, int begin, int max);
}
