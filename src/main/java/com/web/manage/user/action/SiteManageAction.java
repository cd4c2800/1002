package com.web.manage.user.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
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
import com.web.foundation.service.ISysConfigService;
import com.web.foundation.service.IUserService;

@Controller
public class SiteManageAction {
	
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ISiteService siteService;

	/**
	 * 站点列表
	 * @author yueqin
	 * @date 2017年10月9日
	 * @return ModelAndView
	 */
	@RequestMapping("/user/site_list.htm")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,
			String currentPage){	
		ModelAndView mv = new JModelAndView("/user/html/index_SiteSet.html", 0, request, response);
		User user = (User) request.getSession().getAttribute("user");
		//获取当前 用户的 站点信息
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("user_id", user.getId());	
		List<Site> siteList =  siteService.query("select obj from Site obj where obj.user_id=:user_id", params, -1, -1);
		//将所有的站点 信息保存 到域对象中
		request.getSession().setAttribute("siteList",siteList);		
		SiteQueryObject qo = new SiteQueryObject(currentPage, mv, "createTime","asc");
		qo.setPageSize(10);
		if(user!=null){
			qo.addQuery("obj.user_id", new SysMap("id",user.getId()), "=");
		}
		
		IPageList pList = this.siteService.list(qo);
		CommUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
		
		return mv;
	}
	
	/**
	 * 去新增站点页面
	 * @author yueqin
	 * @date 2017年10月9日
	 * @return ModelAndView
	 */
	@RequestMapping("/user/go_addSite")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new JModelAndView("/user/html/add_Site.html", 0, request, response);
		return mv;
	}
	
	/**
	 * 保存站点信息
	 * @author yueqin
	 * @date 2017年10月9日
	 * @return void
	 */
	@RequestMapping("/user/save_site.htm")
	public void save(HttpServletRequest request,HttpServletResponse response,
			String siteName,String siteHome){
		int code = -100;
		Map json_map = new HashMap();
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null){
			Site site = new Site();
			site.setCreateTime(new Date());
			site.setSiteName(siteName);
			site.setSiteHome(siteHome);
			site.setUser_id(user.getId());
			boolean b=  this.siteService.save(site);
			if(b){
				code = 100;
				json_map.put("code", code);
				String json = Json.toJson(json_map, JsonFormat.compact());
				response.setContentType("text/plain");
				response.setHeader("Cache-Control", "no-cache");
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer;
				try {
					writer = response.getWriter();
					writer.print(json);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
