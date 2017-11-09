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
import com.web.foundation.domain.Ad;
import com.web.foundation.service.IAdService;

@Service
@Transactional
public class AdServiceImpl implements IAdService{
	@Resource(name = "adDAO")
	private IGenericDAO<Ad> adDao;
	
	public boolean save(Ad ad) {
		/**
		 * init other field here
		 */
		try {
			this.adDao.save(ad);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Ad getObjById(Long id) {
		Ad ad = this.adDao.get(id);
		if (ad != null) {
			return ad;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.adDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> adIds) {
		// TODO Auto-generated method stub
		for (Serializable id : adIds) {
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
		GenericPageList pList = new GenericPageList(Ad.class, construct,query,
				params, this.adDao);
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
	
	public boolean update(Ad ad) {
		try {
			this.adDao.update( ad);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Ad> query(String query, Map params, int begin, int max){
		return this.adDao.query(query, params, begin, max);
		
	}
}
