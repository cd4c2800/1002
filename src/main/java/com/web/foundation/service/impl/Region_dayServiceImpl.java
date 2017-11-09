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
import com.web.foundation.domain.Region_day;
import com.web.foundation.service.IRegion_dayService;

@Service
@Transactional
public class Region_dayServiceImpl implements IRegion_dayService{
	@Resource(name = "region_dayDAO")
	private IGenericDAO<Region_day> region_dayDao;
	
	public boolean save(Region_day region_day) {
		/**
		 * init other field here
		 */
		try {
			this.region_dayDao.save(region_day);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Region_day getObjById(Long id) {
		Region_day region_day = this.region_dayDao.get(id);
		if (region_day != null) {
			return region_day;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.region_dayDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> region_dayIds) {
		// TODO Auto-generated method stub
		for (Serializable id : region_dayIds) {
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
		GenericPageList pList = new GenericPageList(Region_day.class, construct,query,
				params, this.region_dayDao);
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
	
	public boolean update(Region_day region_day) {
		try {
			this.region_dayDao.update( region_day);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Region_day> query(String query, Map params, int begin, int max){
		return this.region_dayDao.query(query, params, begin, max);
		
	}
}
