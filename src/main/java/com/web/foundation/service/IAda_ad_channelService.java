package com.web.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;

import com.web.foundation.domain.Ada_ad_channel;

public interface IAda_ad_channelService {
	/**
	 * 保存一个Ada_ad_channel，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Ada_ad_channel instance);
	
	/**
	 * 根据一个ID得到Ada_ad_channel
	 * 
	 * @param id
	 * @return
	 */
	Ada_ad_channel getObjById(Long id);
	
	/**
	 * 删除一个Ada_ad_channel
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Integer id);
	
	/**
	 * 批量删除Ada_ad_channel
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Ada_ad_channel
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Ada_ad_channel
	 * 
	 * @param id
	 *            需要更新的Ada_ad_channel的id
	 * @param dir
	 *            需要更新的Ada_ad_channel
	 */
	boolean update(Ada_ad_channel instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Ada_ad_channel> query(String query, Map params, int begin, int max);
}
