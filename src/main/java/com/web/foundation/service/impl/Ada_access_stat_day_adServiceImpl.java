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
import com.web.foundation.domain.Ada_access_stat_day_ad;
import com.web.foundation.service.IAda_access_stat_day_adService;

@Service
@Transactional
public class Ada_access_stat_day_adServiceImpl implements IAda_access_stat_day_adService{
	@Resource(name = "ada_access_stat_day_adDAO")
	private IGenericDAO<Ada_access_stat_day_ad> ada_access_stat_day_adDao;
	
	public boolean save(Ada_access_stat_day_ad ada_access_stat_day_ad) {
		/**
		 * init other field here
		 */
		try {
			this.ada_access_stat_day_adDao.save(ada_access_stat_day_ad);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Ada_access_stat_day_ad getObjById(Long id) {
		Ada_access_stat_day_ad ada_access_stat_day_ad = this.ada_access_stat_day_adDao.get(id);
		if (ada_access_stat_day_ad != null) {
			return ada_access_stat_day_ad;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.ada_access_stat_day_adDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> ada_access_stat_day_adIds) {
		// TODO Auto-generated method stub
		for (Serializable id : ada_access_stat_day_adIds) {
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
		GenericPageList pList = new GenericPageList(Ada_access_stat_day_ad.class, construct,query,
				params, this.ada_access_stat_day_adDao);
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
	
	public boolean update(Ada_access_stat_day_ad ada_access_stat_day_ad) {
		try {
			this.ada_access_stat_day_adDao.update( ada_access_stat_day_ad);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Ada_access_stat_day_ad> query(String query, Map params, int begin, int max){
		return this.ada_access_stat_day_adDao.query(query, params, begin, max);
		
	}
}
