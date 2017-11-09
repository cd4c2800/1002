package com.web.foundation.service.impl;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.web.core.query.PageObject;
import com.web.core.query.support.IPageList;
import com.web.core.query.support.IQueryObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.web.core.dao.IGenericDAO;
import com.web.core.query.GenericPageList;
import com.web.foundation.domain.Ada_access_stat_day_channel;
import com.web.foundation.service.IAda_access_stat_day_channelService;

@Service
@Transactional
public class Ada_access_stat_day_channelServiceImpl implements IAda_access_stat_day_channelService{
	@Resource(name = "ada_access_stat_day_channelDAO")
	private IGenericDAO<Ada_access_stat_day_channel> ada_access_stat_day_channelDao;
	
	public boolean save(Ada_access_stat_day_channel ada_access_stat_day_channel) {
		/**
		 * init other field here
		 */
		try {
			this.ada_access_stat_day_channelDao.save(ada_access_stat_day_channel);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Ada_access_stat_day_channel getObjById(Long id) {
		Ada_access_stat_day_channel ada_access_stat_day_channel = this.ada_access_stat_day_channelDao.get(id);
		if (ada_access_stat_day_channel != null) {
			return ada_access_stat_day_channel;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.ada_access_stat_day_channelDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> ada_access_stat_day_channelIds) {
		// TODO Auto-generated method stub
		for (Serializable id : ada_access_stat_day_channelIds) {
			delete((Long) id);
		}
		return true;
	}
	
	public IPageList list(IQueryObject properties) {
		if (properties == null) {
			return null;
		}
		String query = properties.getQuery();
		String construct = properties.getConstruct();
		Map params = properties.getParameters();
		GenericPageList pList = new GenericPageList(Ada_access_stat_day_channel.class, construct,query,
				params, this.ada_access_stat_day_channelDao);
		if (properties != null) {
			PageObject pageObj = properties.getPageObj();
			if (pageObj != null)
				pList.doList(pageObj.getCurrentPage() == null ? 0 : pageObj
						.getCurrentPage(), pageObj.getPageSize() == null ? 0
						: pageObj.getPageSize());
		} else
			pList.doList(0, -1);
		return pList;
	}
	
	public boolean update(Ada_access_stat_day_channel ada_access_stat_day_channel) {
		try {
			this.ada_access_stat_day_channelDao.update( ada_access_stat_day_channel);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Ada_access_stat_day_channel> query(String query, Map params, int begin, int max){
		return this.ada_access_stat_day_channelDao.query(query, params, begin, max);
		
	}
}
