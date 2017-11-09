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
import com.web.foundation.domain.Region_eip;
import com.web.foundation.service.IRegion_eipService;

@Service
@Transactional
public class Region_eipServiceImpl implements IRegion_eipService{
	@Resource(name = "region_eipDAO")
	private IGenericDAO<Region_eip> region_eipDao;
	
	public boolean save(Region_eip region_eip) {
		/**
		 * init other field here
		 */
		try {
			this.region_eipDao.save(region_eip);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Region_eip getObjById(Long id) {
		Region_eip region_eip = this.region_eipDao.get(id);
		if (region_eip != null) {
			return region_eip;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.region_eipDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> region_eipIds) {
		// TODO Auto-generated method stub
		for (Serializable id : region_eipIds) {
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
		GenericPageList pList = new GenericPageList(Region_eip.class, construct,query,
				params, this.region_eipDao);
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
	
	public boolean update(Region_eip region_eip) {
		try {
			this.region_eipDao.update( region_eip);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Region_eip> query(String query, Map params, int begin, int max){
		return this.region_eipDao.query(query, params, begin, max);
		
	}
}
