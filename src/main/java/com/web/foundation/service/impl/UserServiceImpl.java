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
import com.web.foundation.domain.User;
import com.web.foundation.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService{
	@Resource(name = "userDAO")
	private IGenericDAO<User> userDao;
	
	public boolean save(User user) {
		/**
		 * init other field here
		 */
		try {
			this.userDao.save(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User getObjById(Long id) {
		User user = this.userDao.get(id);
		if (user != null) {
			return user;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.userDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> userIds) {
		// TODO Auto-generated method stub
		for (Serializable id : userIds) {
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
		GenericPageList pList = new GenericPageList(User.class, construct,query,
				params, this.userDao);
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
	
	public boolean update(User user) {
		try {
			this.userDao.update( user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<User> query(String query, Map params, int begin, int max){
		return this.userDao.query(query, params, begin, max);
		
	}
}
