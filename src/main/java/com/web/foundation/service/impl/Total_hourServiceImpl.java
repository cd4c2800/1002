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
import com.web.foundation.domain.Total_hour;
import com.web.foundation.service.ITotal_hourService;

@Service
@Transactional
public class Total_hourServiceImpl implements ITotal_hourService{
	@Resource(name = "total_hourDAO")
	private IGenericDAO<Total_hour> total_hourDao;
	
	public boolean save(Total_hour total_hour) {
		/**
		 * init other field here
		 */
		try {
			this.total_hourDao.save(total_hour);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Total_hour getObjById(Long id) {
		Total_hour total_hour = this.total_hourDao.get(id);
		if (total_hour != null) {
			return total_hour;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.total_hourDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> total_hourIds) {
		// TODO Auto-generated method stub
		for (Serializable id : total_hourIds) {
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
		GenericPageList pList = new GenericPageList(Total_hour.class, construct,query,
				params, this.total_hourDao);
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
	
	public boolean update(Total_hour total_hour) {
		try {
			this.total_hourDao.update( total_hour);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Total_hour> query(String query, Map params, int begin, int max){
		return this.total_hourDao.query(query, params, begin, max);
		
	}
}
