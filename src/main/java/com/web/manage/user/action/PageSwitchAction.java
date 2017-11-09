package com.web.manage.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.core.mv.JModelAndView;
/**
 * 
 * 页面跳转中转 控制类
 * 
 * 
 * */
@Controller
public class PageSwitchAction {
	/**
	 * 页面 跳转
	 * 
	 * */
	@RequestMapping("/user/menu.htm")
	public ModelAndView menuSwitch(HttpServletRequest request,HttpServletResponse response,String currentPage, String orderBy,
			String orderType,String date,String item,Integer siteId) {
		//设置当前 站点 （后续页面使用）
		if (siteId!=null)
			request.getSession().setAttribute("currentSiteId", siteId);
		//用于 验证用户是否 登录（未登录 直接跳转到主页）
		if (!"user_login".equals( item )&&request.getSession().getAttribute("user")!=null  )

			return new JModelAndView("/user/html/"+item+".html",0, request,response);
			
		else 
			return new JModelAndView("/user/html/user_login.html", 0, request, response);
	}
	/**
	 * 站点设置 → 查看报表
	 * 
	 * */
	@RequestMapping("/user/view.htm")
	public ModelAndView viewReportSwitch(HttpServletRequest request,HttpServletResponse response,String currentPage, String orderBy,
			String orderType,String date,Integer siteId) {
		//设置当前 站点 （后续页面使用）
			request.getSession().setAttribute("currentSiteId", siteId);
		//用于 验证用户是否 登录（未登录 直接跳转到主页）
		if (request.getSession().getAttribute("user")!=null  ) 
			return new JModelAndView("/user/html/index.html",0, request,response);
		else 
			return new JModelAndView("/user/html/user_login.html", 0, request, response);
	}
	
	
	
	
	
	
	
	

}
