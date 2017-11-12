package com.web.foundation.service.impl;
import java.io.Serializable;
import java.util.HashMap;
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
import com.web.foundation.domain.Ada_referers;
import com.web.foundation.service.IAda_referersService;

@Service
@Transactional
public class Ada_referersServiceImpl implements IAda_referersService{
	@Resource(name = "ada_referersDAO")
	private IGenericDAO<Ada_referers> ada_referersDao;
	
	public boolean save(Ada_referers ada_referers) {
		/**
		 * init other field here
		 */
		try {
			this.ada_referersDao.save(ada_referers);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Ada_referers getObjById(Long id) {
		Ada_referers ada_referers = this.ada_referersDao.get(id);
		if (ada_referers != null) {
			return ada_referers;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.ada_referersDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> ada_referersIds) {
		// TODO Auto-generated method stub
		for (Serializable id : ada_referersIds) {
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
		GenericPageList pList = new GenericPageList(Ada_referers.class, construct,query,
				params, this.ada_referersDao);
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
	
	public boolean update(Ada_referers ada_referers) {
		try {
			this.ada_referersDao.update( ada_referers);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Ada_referers> query(String query, Map params, int begin, int max){
		return this.ada_referersDao.query(query, params, begin, max);
		
	}
	
	public Map<String, Map<Integer, String>> map(String query, Map params, int begin, int max){
		List<Ada_referers> qresult = query(query, params, begin, max);
		if (qresult==null||qresult.isEmpty()) return null;
		Map<String, Map<Integer, String>> map=new HashMap<String, Map<Integer,String>>();
		Map<Integer,String> idDomain=new HashMap<Integer, String>();
		Map<Integer,String> idName=new HashMap<Integer, String>();
			for (Ada_referers item : qresult) {
				idDomain.put(item.getId(), item.getDomain());	
				idName.put(item.getId(), item.getName());								
			}			
		map.put("idDomain", idDomain);
		map.put("idName", idName);				
		return map;
	}
	
	
}
