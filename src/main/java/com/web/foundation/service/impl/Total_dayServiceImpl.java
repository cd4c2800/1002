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
import com.web.foundation.domain.Total_day;
import com.web.foundation.service.ITotal_dayService;

@Service
@Transactional
public class Total_dayServiceImpl implements ITotal_dayService{
	@Resource(name = "total_dayDAO")
	private IGenericDAO<Total_day> total_dayDao;
	
	public boolean save(Total_day total_day) {
		/**
		 * init other field here
		 */
		try {
			this.total_dayDao.save(total_day);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Total_day getObjById(Long id) {
		Total_day total_day = this.total_dayDao.get(id);
		if (total_day != null) {
			return total_day;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.total_dayDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> total_dayIds) {
		// TODO Auto-generated method stub
		for (Serializable id : total_dayIds) {
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
		GenericPageList pList = new GenericPageList(Total_day.class, construct,query,
				params, this.total_dayDao);
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
	
	public boolean update(Total_day total_day) {
		try {
			this.total_dayDao.update( total_day);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Total_day> query(String query, Map params, int begin, int max){
		return this.total_dayDao.query(query, params, begin, max);
		
	}
}
