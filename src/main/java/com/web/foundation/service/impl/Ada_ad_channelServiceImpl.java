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
import com.web.foundation.domain.Ada_ad_channel;
import com.web.foundation.service.IAda_ad_channelService;

@Service
@Transactional
public class Ada_ad_channelServiceImpl implements IAda_ad_channelService{
	@Resource(name = "ada_ad_channelDAO")
	private IGenericDAO<Ada_ad_channel> ada_ad_channelDao;
	
	public boolean save(Ada_ad_channel ada_ad_channel) {
		/**
		 * init other field here
		 */
		try {
			this.ada_ad_channelDao.save(ada_ad_channel);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Ada_ad_channel getObjById(Long id) {
		Ada_ad_channel ada_ad_channel = this.ada_ad_channelDao.get(id);
		if (ada_ad_channel != null) {
			return ada_ad_channel;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.ada_ad_channelDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> ada_ad_channelIds) {
		// TODO Auto-generated method stub
		for (Serializable id : ada_ad_channelIds) {
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
		GenericPageList pList = new GenericPageList(Ada_ad_channel.class, construct,query,
				params, this.ada_ad_channelDao);
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
	
	public boolean update(Ada_ad_channel ada_ad_channel) {
		try {
			this.ada_ad_channelDao.update( ada_ad_channel);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Ada_ad_channel> query(String query, Map params, int begin, int max){
		return this.ada_ad_channelDao.query(query, params, begin, max);
		
	}
}
