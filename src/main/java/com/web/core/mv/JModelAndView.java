package com.web.core.mv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.web.core.constant.Globals;
import com.web.core.tools.SecurityUserHolder;
import com.web.core.tools.CommUtil;
import com.web.core.tools.HttpInclude;
import com.web.foundation.domain.SysConfig;

/**
 * 
 * <p>
 * Title: JModelAndView.java
 * </p>
 * 
 * <p>
 * Description: 顶级视图管理类，封装ModelAndView并进行系统扩展
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 * <p>
 * Company: 吉祥网购www.xp1588.com
 * </p>
 * 
 * @author erikzhang
 * 
 * @date 2014-4-24
 * 
 * @version xpshop v2.0 2015版 
 */
public class JModelAndView extends ModelAndView {
	/**
	 * 普通视图，根据velocity配置文件的路径直接加载视图
	 * 
	 * @param viewName
	 *            视图名称
	 */
	public JModelAndView(String viewName) {
		super.setViewName(viewName);
	}

	/**
	 * 
	 * @param viewName
	 *            用户自定义的视图，可以添加任意路径
	 * @param request
	 */
	public JModelAndView(String viewName, SysConfig config,
			HttpServletRequest request, HttpServletResponse response) {
		String contextPath = request.getContextPath().equals("/") ? ""
				: request.getContextPath();
		String webPath = CommUtil.getURL(request);
		super.addObject("current_webPath", webPath);
		String port = request.getServerPort() == 80 ? "" : ":"
				+ CommUtil.null2Int(request.getServerPort());
		if (Globals.SSO_SIGN && config.isSecond_domain_open()
				&& !CommUtil.generic_domain(request).equals("localhost")
				&& !CommUtil.isIp(request.getServerName())) {
			webPath = "http://www." + CommUtil.generic_domain(request) + port
					+ contextPath;
		}
		super.setViewName(viewName);
		super.addObject("domainPath", CommUtil.generic_domain(request));
		if (config.getImageWebServer() != null
				&& !config.getImageWebServer().equals("")) {
			super.addObject("imageWebServer", config.getImageWebServer());
		} else {
			super.addObject("imageWebServer", webPath);
		}
		super.addObject("webPath", webPath);
		super.addObject("config", config);
		super.addObject("user", SecurityUserHolder.getCurrentUser());
		super.addObject("httpInclude", new HttpInclude(request, response));
		String query_url = "";
		if (request.getQueryString() != null
				&& !request.getQueryString().equals("")) {
			query_url = "?" + request.getQueryString();
		}
		super.addObject("current_url", request.getRequestURI() + query_url);
		boolean second_domain_view = false;
		String serverName = request.getServerName().toLowerCase();
		if (serverName.indexOf("www.") < 0 && serverName.indexOf(".") >= 0
				&& serverName.indexOf(".") != serverName.lastIndexOf(".")
				&& config.isSecond_domain_open()) {
			String secondDomain = serverName.substring(0,
					serverName.indexOf("."));
			second_domain_view = true;// 使用二级域名访问，相关js url需要处理，避免跨域
			super.addObject("secondDomain", secondDomain);
		}
		super.addObject("second_domain_view", second_domain_view);
	}

	/**
	 * 按指定路径加载视图，如不指定则系统默认路径加载
	 * 
	 * @param viewName
	 *            视图名称
	 * @param config
	 *            商城配置
	 * @param userPath
	 *            自定义路径，和type配合使用
	 * @param type
	 *            视图类型 0为后台，1为前台 大于1为自定义路径
	 */
	public JModelAndView(String viewName,int type, HttpServletRequest request, HttpServletResponse response) {
		
				if (type == 0) {
					super.setViewName(Globals.DEFAULT_SYSTEM_PAGE_ROOT + "system/" + viewName);
				}
	
	
		super.addObject("CommUtil", new CommUtil());
		String contextPath = request.getContextPath().equals("/") ? ""
				: request.getContextPath();
		String webPath = CommUtil.getURL(request);
		String port = request.getServerPort() == 80 ? "" : ":"
				+ CommUtil.null2Int(request.getServerPort());
		super.addObject("current_webPath", webPath);
	
		super.addObject("domainPath", CommUtil.generic_domain(request));
		super.addObject("webPath", webPath);
		
		super.addObject("imageWebServer", webPath);
		super.addObject("user", SecurityUserHolder.getCurrentUser());
		super.addObject("httpInclude", new HttpInclude(request, response));
		String query_url = "";
		if (request.getQueryString() != null
				&& !request.getQueryString().equals("")) {
			query_url = "?" + request.getQueryString();
		}
		super.addObject("current_url", request.getRequestURI() + query_url);
		boolean second_domain_view = false;
		String serverName = request.getServerName().toLowerCase();
		
		super.addObject("second_domain_view", second_domain_view);
	}
}
