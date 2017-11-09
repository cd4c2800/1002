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
import com.web.foundation.domain.Sys_region;
import com.web.foundation.service.ISys_regionService;

@Service
@Transactional
public class Sys_regionServiceImpl implements ISys_regionService{
	@Resource(name = "sys_regionDAO")
	private IGenericDAO<Sys_region> sys_regionDao;
	
	public boolean save(Sys_region sys_region) {
		/**
		 * init other field here
		 */
		try {
			this.sys_regionDao.save(sys_region);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Sys_region getObjById(Long id) {
		Sys_region sys_region = this.sys_regionDao.get(id);
		if (sys_region != null) {
			return sys_region;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.sys_regionDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> sys_regionIds) {
		// TODO Auto-generated method stub
		for (Serializable id : sys_regionIds) {
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
		GenericPageList pList = new GenericPageList(Sys_region.class, construct,query,
				params, this.sys_regionDao);
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
	
	public boolean update(Sys_region sys_region) {
		try {
			this.sys_regionDao.update( sys_region);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Sys_region> query(String query, Map params, int begin, int max){
		return this.sys_regionDao.query(query, params, begin, max);
		
	}
	/**
	 * 获得 国家 省份的 名称
	 * 
	 * */
	public String getName(String string, Map params) {
		// TODO Auto-generated method stub
		 return this.sys_regionDao.getName(string, params);
	}
}
