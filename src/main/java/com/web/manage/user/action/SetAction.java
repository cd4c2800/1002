package com.web.manage.user.action;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.core.domain.virtual.SysMap;
import com.web.core.mv.JModelAndView;
import com.web.core.query.support.IPageList;
import com.web.core.tools.CommUtil;
import com.web.foundation.domain.Site;
import com.web.foundation.domain.User;
import com.web.foundation.domain.query.SiteQueryObject;
import com.web.foundation.service.ISiteService;
import com.web.utils.response.ResponseData;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
@Controller
@SuppressWarnings("all")
public class SetAction {
	@Autowired
	private ISiteService siteService;
	
	/**
	 * 视图 跳转
	 * */
	@RequestMapping("set/toAdd.htm")
	public ModelAndView menuSwitch(HttpServletRequest request,HttpServletResponse response,
			Integer id,String siteName,String homeName) {
		JModelAndView mv = new JModelAndView("/user/html/add_Site.html",0, request,response);
		Site site = siteService.getObjById(id);
		mv.addObject("site", site);
		return mv;
	}
	/**
	 * 站点 列表请求数据
	 * 
	 * 
	 * */
	@RequestMapping("set/siteList.htm")
	public void listSite(HttpServletRequest request,HttpServletResponse response,
			Integer user_id){
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("user_id", user_id);	
		List<Site> siteList =  siteService.query("select obj from Site obj where obj.user_id=:user_id", params, -1, -1);
		//将所有的站点 信息保存 到域对象中
		request.getSession().setAttribute("siteList",siteList);	
		Map result=new HashMap();//数据 结果集
		result.put("site",siteList);
		ResponseData.responseData(response, result);//向页面 相应 数据				
	}	
	/**
	 * 删除站点 
	 * 
	 * */
	@RequestMapping("set/siteDelete.htm")
	public void deleteSite(HttpServletRequest request,HttpServletResponse response,
			Integer siteId){
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("siteId", siteId);	
		 boolean delete = siteService.delete(siteId);
		Map result=new HashMap();//数据 结果集
		result.put("result",delete);
		ResponseData.responseData(response, result);//向页面 相应 数据				
	}
	/**
	 * 添加 站点
	 * */
	@RequestMapping("set/siteUpdate.htm")
	public void updateSite(HttpServletRequest request,HttpServletResponse response,
			String siteName,String homeName,Integer id){
		Map result=new HashMap();//数据 结果集
		User user = (User) request.getSession().getAttribute("user");	
		if(user!=null){
			Site site = new Site();
			site.setCreateTime(new Date());
			site.setSiteName(siteName);
			site.setSiteHome(homeName);
			site.setUser_id(user.getId());
			boolean save=false;
			if (id!=null) {
				site.setId(id);
				save= siteService.update(site);
			} else {
				save = siteService.save(site);
			}			
			result.put("result", save);
		}	
		ResponseData.responseData(response, result);//向页面 相应 数据				
	}

	
	
}
