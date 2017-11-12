package com.web.core.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * 
 * <p>
 * Title: GenericDAO.java
 * </p>
 * 
 * <p>
 * Description: 统所有数据库操作的基类，采用JDK5泛型接口完成POJO类型注入
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 * 
 * @author erikzhang
 * 
 * @date 2014-4-24
 * 
 * @version xpshop v2.0 2015�?
 */
public class GenericDAO<T> implements com.web.core.dao.IGenericDAO<T> {
	protected Class<T> entityClass;// DAO�?��理的Entity类型
	@Autowired
	@Qualifier("genericEntityDao")
	private GenericEntityDao geDao;

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public GenericEntityDao getGeDao() {
		return geDao;
	}

	public void setGeDao(GenericEntityDao geDao) {
		this.geDao = geDao;
	}

	public GenericDAO() {
		this.entityClass = (Class<T>) ((ParameterizedType) (this.getClass()
				.getGenericSuperclass())).getActualTypeArguments()[0];
		// System.out.println("执行GenericDAO不带参数构�?方法�?);
		// System.out.println("注入类：" + this.entityClass);
	}

	public GenericDAO(Class<T> type) {
		this.entityClass = type;
	}

	public int batchUpdate(String jpql, Object[] params) {
		// TODO Auto-generated method stub
		return this.geDao.batchUpdate(jpql, params);
	}

	public List executeNamedQuery(String queryName, Object[] params, int begin,
			int max) {
		// TODO Auto-generated method stub
		return this.geDao.executeNamedQuery(queryName, params, begin, max);
	}

	public List executeNativeNamedQuery(String nnq) {
		// TODO Auto-generated method stub
		return this.geDao.executeNativeNamedQuery(nnq);
	}

	public List executeNativeQuery(String nnq, Object[] params, int begin,
			int max) {
		// TODO Auto-generated method stub
		return this.geDao.executeNativeQuery(nnq, params, begin, max);
	}

	public int executeNativeSQL(String nnq) {
		// TODO Auto-generated method stub
		return this.geDao.executeNativeSQL(nnq);
	}

	public List find(String construct, String query, Map params, int begin,
			int max) {
		// TODO Auto-generated method stub
		//System.out.println("构�?函数�? + construct);
		//System.out.println("查询条件:" + query);
		return this.getGeDao().find(this.entityClass, construct, query, params,
				begin, max);
	}

	public void flush() {
		// TODO Auto-generated method stub
		this.geDao.flush();
	}

	public T get(Serializable id) {
		// TODO Auto-generated method stub
		return (T) this.getGeDao().get(this.entityClass, id);
	}

	public T getBy(String construct, String propertyName, Object value) {
		// TODO Auto-generated method stub
		return (T) this.getGeDao().getBy(this.entityClass, construct,
				propertyName, value);
	}

	public List query(String query, Map params, int begin, int max) {
		// TODO Auto-generated method stub
		return this.getGeDao().query(query, params, begin, max);
	}
	public void remove(Serializable id) {
		// TODO Auto-generated method stub
		/** **/
//		this.getGeDao().remove(this.entityClass, id);
	}

	public void save(Object newInstance) {
		// TODO Auto-generated method stub
		this.getGeDao().save((T) newInstance);
	}

	public void update(Object transientObject) {
		// TODO Auto-generated method stub
		this.getGeDao().update((T) transientObject);
	}

	/**
	 * 获得 访问的  总数
	 * 
	 * */
	public Long getSum(String query, Map params) {
		// TODO Auto-generated method stub
		return this.getGeDao().getSum(query, params);
	}

	@Override
	public String getName(String string, Map params) {
		// TODO Auto-generated method stub
		return this.getGeDao().getName(string, params);
	}
	
}
