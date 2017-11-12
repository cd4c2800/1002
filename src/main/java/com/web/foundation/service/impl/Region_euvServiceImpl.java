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
import com.web.foundation.domain.Region_euv;
import com.web.foundation.service.IRegion_euvService;

@Service
@Transactional
public class Region_euvServiceImpl implements IRegion_euvService{
	@Resource(name = "region_euvDAO")
	private IGenericDAO<Region_euv> region_euvDao;
	
	public boolean save(Region_euv region_euv) {
		/**
		 * init other field here
		 */
		try {
			this.region_euvDao.save(region_euv);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Region_euv getObjById(Long id) {
		Region_euv region_euv = this.region_euvDao.get(id);
		if (region_euv != null) {
			return region_euv;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.region_euvDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> region_euvIds) {
		// TODO Auto-generated method stub
		for (Serializable id : region_euvIds) {
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
		GenericPageList pList = new GenericPageList(Region_euv.class, construct,query,
				params, this.region_euvDao);
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
	
	public boolean update(Region_euv region_euv) {
		try {
			this.region_euvDao.update( region_euv);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Region_euv> query(String query, Map params, int begin, int max){
		return this.region_euvDao.query(query, params, begin, max);
		
	}
}
