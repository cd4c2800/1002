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
import com.web.foundation.domain.Ada_access_day_ip_adchannel;
import com.web.foundation.service.IAda_access_day_ip_adchannelService;

@Service
@Transactional
public class Ada_access_day_ip_adchannelServiceImpl implements IAda_access_day_ip_adchannelService{
	@Resource(name = "ada_access_day_ip_adchannelDAO")
	private IGenericDAO<Ada_access_day_ip_adchannel> ada_access_day_ip_adchannelDao;
	
	public boolean save(Ada_access_day_ip_adchannel ada_access_day_ip_adchannel) {
		/**
		 * init other field here
		 */
		try {
			this.ada_access_day_ip_adchannelDao.save(ada_access_day_ip_adchannel);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Ada_access_day_ip_adchannel getObjById(Long id) {
		Ada_access_day_ip_adchannel ada_access_day_ip_adchannel = this.ada_access_day_ip_adchannelDao.get(id);
		if (ada_access_day_ip_adchannel != null) {
			return ada_access_day_ip_adchannel;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.ada_access_day_ip_adchannelDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> ada_access_day_ip_adchannelIds) {
		// TODO Auto-generated method stub
		for (Serializable id : ada_access_day_ip_adchannelIds) {
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
		GenericPageList pList = new GenericPageList(Ada_access_day_ip_adchannel.class, construct,query,
				params, this.ada_access_day_ip_adchannelDao);
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
	
	public boolean update(Ada_access_day_ip_adchannel ada_access_day_ip_adchannel) {
		try {
			this.ada_access_day_ip_adchannelDao.update( ada_access_day_ip_adchannel);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Ada_access_day_ip_adchannel> query(String query, Map params, int begin, int max){
		return this.ada_access_day_ip_adchannelDao.query(query, params, begin, max);
		
	}
}
