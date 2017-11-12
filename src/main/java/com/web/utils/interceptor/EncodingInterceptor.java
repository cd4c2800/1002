package com.web.utils.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.web.foundation.domain.User;

/**
 * 登录 拦截 以及 编码拦截
 * 
 * */
public class EncodingInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		User user = (User)request.getSession().getAttribute("user");
		String str1 = request.getParameter("item");
		String requestURI = request.getRequestURI();
		String trueURI="";
		if (str1!=null&&!"".equals(str1)) trueURI=str1;
		else trueURI=requestURI;
		if(trueURI.indexOf("login")==-1&&user!=null) return true;
		response.sendRedirect(request.getContextPath()+"/user/menu.htm?item=user_login");		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
