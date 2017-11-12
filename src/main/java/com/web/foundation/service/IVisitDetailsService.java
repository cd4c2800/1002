package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;

import com.web.foundation.domain.VisitDetails;

public interface IVisitDetailsService {
	/**
	 * 保存一个VisitDetails，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(VisitDetails instance);
	
	/**
	 * 根据一个ID得到VisitDetails
	 * 
	 * @param id
	 * @return
	 */
	VisitDetails getObjById(Long id);
	
	/**
	 * 删除一个VisitDetails
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除VisitDetails
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到VisitDetails
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个VisitDetails
	 * 
	 * @param id
	 *            需要更新的VisitDetails的id
	 * @param dir
	 *            需要更新的VisitDetails
	 */
	boolean update(VisitDetails instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<VisitDetails> query(String query, Map params, int begin, int max);
}
