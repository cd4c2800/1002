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
import com.web.foundation.domain.VisitDetails;
import com.web.foundation.service.IVisitDetailsService;

@Service
@Transactional
public class VisitDetailsServiceImpl implements IVisitDetailsService{
	@Resource(name = "visitDetailsDAO")
	private IGenericDAO<VisitDetails> visitDetailsDao;
	
	public boolean save(VisitDetails visitDetails) {
		/**
		 * init other field here
		 */
		try {
			this.visitDetailsDao.save(visitDetails);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public VisitDetails getObjById(Long id) {
		VisitDetails visitDetails = this.visitDetailsDao.get(id);
		if (visitDetails != null) {
			return visitDetails;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.visitDetailsDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> visitDetailsIds) {
		// TODO Auto-generated method stub
		for (Serializable id : visitDetailsIds) {
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
		GenericPageList pList = new GenericPageList(VisitDetails.class, construct,query,
				params, this.visitDetailsDao);
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
	
	public boolean update(VisitDetails visitDetails) {
		try {
			this.visitDetailsDao.update( visitDetails);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<VisitDetails> query(String query, Map params, int begin, int max){
		return this.visitDetailsDao.query(query, params, begin, max);
		
	}
}
