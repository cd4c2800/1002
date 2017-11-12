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
import com.web.foundation.domain.Ada_access_day_clientsourc;
import com.web.foundation.service.IAda_access_day_clientsourcService;

@Service
@Transactional
public class Ada_access_day_clientsourcServiceImpl implements IAda_access_day_clientsourcService{
	@Resource(name = "ada_access_day_clientsourcDAO")
	private IGenericDAO<Ada_access_day_clientsourc> ada_access_day_clientsourcDao;
	
	public boolean save(Ada_access_day_clientsourc ada_access_day_clientsourc) {
		/**
		 * init other field here
		 */
		try {
			this.ada_access_day_clientsourcDao.save(ada_access_day_clientsourc);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Ada_access_day_clientsourc getObjById(Long id) {
		Ada_access_day_clientsourc ada_access_day_clientsourc = this.ada_access_day_clientsourcDao.get(id);
		if (ada_access_day_clientsourc != null) {
			return ada_access_day_clientsourc;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.ada_access_day_clientsourcDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> ada_access_day_clientsourcIds) {
		// TODO Auto-generated method stub
		for (Serializable id : ada_access_day_clientsourcIds) {
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
		GenericPageList pList = new GenericPageList(Ada_access_day_clientsourc.class, construct,query,
				params, this.ada_access_day_clientsourcDao);
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
	
	public boolean update(Ada_access_day_clientsourc ada_access_day_clientsourc) {
		try {
			this.ada_access_day_clientsourcDao.update( ada_access_day_clientsourc);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Ada_access_day_clientsourc> query(String query, Map params, int begin, int max){
		return this.ada_access_day_clientsourcDao.query(query, params, begin, max);
		
	}
}
