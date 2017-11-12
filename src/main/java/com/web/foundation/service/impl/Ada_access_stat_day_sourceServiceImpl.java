package com.web.foundation.service.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import com.web.foundation.domain.Ada_access_stat_day_source;

import com.web.foundation.service.IAda_access_stat_day_sourceService;


@Service
@Transactional
public class Ada_access_stat_day_sourceServiceImpl implements IAda_access_stat_day_sourceService{
	@Resource(name = "ada_access_stat_day_sourceDAO")
	private IGenericDAO<Ada_access_stat_day_source> ada_access_stat_day_sourceDao;
	public boolean save(Ada_access_stat_day_source ada_access_stat_day_source) {
		/**
		 * init other field here
		 */
		try {
			this.ada_access_stat_day_sourceDao.save(ada_access_stat_day_source);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Ada_access_stat_day_source getObjById(Long id) {
		Ada_access_stat_day_source ada_access_stat_day_source = this.ada_access_stat_day_sourceDao.get(id);
		if (ada_access_stat_day_source != null) {
			return ada_access_stat_day_source;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.ada_access_stat_day_sourceDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> ada_access_stat_day_sourceIds) {
		// TODO Auto-generated method stub
		for (Serializable id : ada_access_stat_day_sourceIds) {
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
		GenericPageList pList = new GenericPageList(Ada_access_stat_day_source.class, construct,query,
				params, this.ada_access_stat_day_sourceDao);
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
	
	public boolean update(Ada_access_stat_day_source ada_access_stat_day_source) {
		try {
			this.ada_access_stat_day_sourceDao.update( ada_access_stat_day_source);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Ada_access_stat_day_source> query(String query, Map params, int begin, int max){
		return this.ada_access_stat_day_sourceDao.query(query, params, begin, max);
		
	}
	/**
	 * 按 来源 名 分组
	 * 
	 * */
	public String[][] source(String query, Map params, int begin, int max,String path,Map<Integer, String> idName){
		List<Ada_access_stat_day_source> source =query(query, params, begin, max);
		if (source==null||source.isEmpty()) return null;
		Date starttime = (Date) params.get("startime");
		Date endtime = (Date) params.get("endtime");		
		Map<Integer, List<Ada_access_stat_day_source>> classf = classf(source);
		if (classf==null||classf.isEmpty()) return null;
		String[][] result=new String[classf.size()][7];//来源 分类的数据
		int index=0;
		for (Integer key : classf.keySet()) {
			String [] item=new String[7];//列数据 
			Map<String,Integer> num = sourceNum(classf.get(key));
			item[0]=idName.get(key);
			item[1]=num.get("pv").toString();
			item[2]=num.get("uv").toString();
			item[3]=num.get("ip").toString();
			item[4]=num.get("epv").toString();
			item[5]="<a href='"+path+"/user/data/source/euveip.htm?way=1&referersId="+key+"&starttime="+starttime.getTime()+"&endtime="+endtime.getTime()+"' >"+num.get("euv")+"</a>";
			item[6]="<a href='"+path+"/user/data/source/euveip.htm?way=2&referersId="+key+"&starttime="+starttime.getTime()+"&endtime="+endtime.getTime()+"' >"+num.get("eip")+"</a>";
			result[index]=item;
			index++;			
		}			
		return result;
	}
	
	/**
	 * 按 来源的 ID 进行分组  
	 * 
	 * */
	private Map<Integer,List<Ada_access_stat_day_source>> classf(List<Ada_access_stat_day_source> source ){
		Map<Integer,List<Ada_access_stat_day_source>> classfy=new HashMap<Integer, List<Ada_access_stat_day_source>>();
		if (source!=null&&!source.isEmpty()) {
			for (Ada_access_stat_day_source item : source) {
				List<Ada_access_stat_day_source> templ=classfy.get(item.getReferersId());
				if (templ==null) {
					templ=new ArrayList<Ada_access_stat_day_source>();
					templ.add(item);
					classfy.put(item.getReferersId(),templ);
				} else {
					templ.add(item);	
				} 				
			}		
		}	
		
		return classfy;
	}
	/**
	 * 计算 浏览数据 
	 * 
	 * */
	private  Map<String,Integer> sourceNum(List<Ada_access_stat_day_source> query) {
		Map<String,Integer> result=new HashMap<String,Integer>();
		Integer ip=0;//独立ip数
		Integer uv=0;//独立客户
		Integer pv=0;//总访问数量
		Integer eip=0;//异常ip数
		Integer euv=0;//异常客户数
		Integer epv=0;//异常访问数
		if (query!=null&&!query.isEmpty()) {
			for (Ada_access_stat_day_source item : query) {
				pv+=item.getPv();
				uv+=item.getUv();
				ip+=item.getIp();
				epv+=item.getEpv();
				euv+=item.getEuv();
				eip+=item.getEip();			
			}			
		}				
		//保存数据
		result.put("pv",pv);
		result.put("uv",uv);
		result.put("ip",ip);
		result.put("epv",epv);
		result.put("euv",euv);
		result.put("eip",eip);		
		return result;		
	}
	
	
	
}
