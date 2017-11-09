package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;

import com.web.foundation.domain.Ada_collect_access_log;

public interface IAda_collect_access_logService {
	/**
	 * 保存一个Ada_collect_access_log，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Ada_collect_access_log instance);
	
	/**
	 * 根据一个ID得到Ada_collect_access_log
	 * 
	 * @param id
	 * @return
	 */
	Ada_collect_access_log getObjById(Long id);
	
	/**
	 * 删除一个Ada_collect_access_log
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Ada_collect_access_log
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Ada_collect_access_log
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Ada_collect_access_log
	 * 
	 * @param id
	 *            需要更新的Ada_collect_access_log的id
	 * @param dir
	 *            需要更新的Ada_collect_access_log
	 */
	boolean update(Ada_collect_access_log instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Ada_collect_access_log> query(String query, Map params, int begin, int max);
	/**
	 * 获得 当前在线 pv 数
	 * 
	 * */
	
	Long getSum(String query, Map params);
	
}
