package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;

import com.web.foundation.domain.Ada_access_stat_day_channel;

public interface IAda_access_stat_day_channelService {
	/**
	 * 保存一个Ada_access_stat_day_channel，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Ada_access_stat_day_channel instance);
	
	/**
	 * 根据一个ID得到Ada_access_stat_day_channel
	 * 
	 * @param id
	 * @return
	 */
	Ada_access_stat_day_channel getObjById(Long id);
	
	/**
	 * 删除一个Ada_access_stat_day_channel
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Ada_access_stat_day_channel
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Ada_access_stat_day_channel
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Ada_access_stat_day_channel
	 * 
	 * @param id
	 *            需要更新的Ada_access_stat_day_channel的id
	 * @param dir
	 *            需要更新的Ada_access_stat_day_channel
	 */
	boolean update(Ada_access_stat_day_channel instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Ada_access_stat_day_channel> query(String query, Map params, int begin, int max);
}
