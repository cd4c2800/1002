package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;

import com.web.foundation.domain.Region_euv;

public interface IRegion_euvService {
	/**
	 * 保存一个Region_euv，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Region_euv instance);
	
	/**
	 * 根据一个ID得到Region_euv
	 * 
	 * @param id
	 * @return
	 */
	Region_euv getObjById(Long id);
	
	/**
	 * 删除一个Region_euv
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Region_euv
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Region_euv
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Region_euv
	 * 
	 * @param id
	 *            需要更新的Region_euv的id
	 * @param dir
	 *            需要更新的Region_euv
	 */
	boolean update(Region_euv instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Region_euv> query(String query, Map params, int begin, int max);
}
