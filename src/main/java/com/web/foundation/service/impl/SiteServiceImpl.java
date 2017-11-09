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
import com.web.foundation.domain.Site;
import com.web.foundation.service.ISiteService;

@Service
@Transactional
public class SiteServiceImpl implements ISiteService{
	@Resource(name = "siteDAO")
	private IGenericDAO<Site> siteDao;
	
	public boolean save(Site site) {
		/**
		 * init other field here
		 */
		try {
			this.siteDao.save(site);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Site getObjById(Long id) {
		Site site = this.siteDao.get(id);
		if (site != null) {
			return site;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.siteDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> userIds) {
		// TODO Auto-generated method stub
		for (Serializable id : userIds) {
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
		GenericPageList pList = new GenericPageList(Site.class, construct,query,
				params, this.siteDao);
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
	
	public boolean update(Site user) {
		try {
			this.siteDao.update( user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Site> query(String query, Map params, int begin, int max){
		return this.siteDao.query(query, params, begin, max);
		
	}
}
