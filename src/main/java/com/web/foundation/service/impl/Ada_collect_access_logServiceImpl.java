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
import com.web.foundation.domain.Ada_collect_access_log;
import com.web.foundation.service.IAda_collect_access_logService;

@Service
@Transactional
public class Ada_collect_access_logServiceImpl implements IAda_collect_access_logService{
	@Resource(name = "ada_collect_access_logDAO")
	private IGenericDAO<Ada_collect_access_log> ada_collect_access_logDao;
	
	public boolean save(Ada_collect_access_log ada_collect_access_log) {
		/**
		 * init other field here
		 */
		try {
			this.ada_collect_access_logDao.save(ada_collect_access_log);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Ada_collect_access_log getObjById(Long id) {
		Ada_collect_access_log ada_collect_access_log = this.ada_collect_access_logDao.get(id);
		if (ada_collect_access_log != null) {
			return ada_collect_access_log;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.ada_collect_access_logDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> ada_collect_access_logIds) {
		// TODO Auto-generated method stub
		for (Serializable id : ada_collect_access_logIds) {
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
		GenericPageList pList = new GenericPageList(Ada_collect_access_log.class, construct,query,
				params, this.ada_collect_access_logDao);
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
	
	public boolean update(Ada_collect_access_log ada_collect_access_log) {
		try {
			this.ada_collect_access_logDao.update( ada_collect_access_log);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Ada_collect_access_log> query(String query, Map params, int begin, int max){
		return this.ada_collect_access_logDao.query(query, params, begin, max);
		
	}

	/**
	 * 获得 访问记录的  总数
	 * */
	public Long getSum(String query, Map params) {
		
		return this.ada_collect_access_logDao.getSum(query, params);
	}
}
