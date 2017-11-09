package com.web.manage.user.action;

import java.io.IOException;
import java.io.PrintWriter;
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






import com.web.core.mv.JModelAndView;
import com.web.foundation.domain.User;
import com.web.foundation.service.ISysConfigService;
import com.web.foundation.service.IUserService;

/**
 * 平台管理基础控制
 * @author ASUS
 *
 */
@Controller
@SuppressWarnings("all")
public class BaseManageAction {
	@Autowired
	private ISysConfigService configService;
	
	@Autowired
	private IUserService userService;	


	/**
	 * 用户进入登录页面
	 * @author yueqin
	 * @date 2017年9月27日
	 * @return ModelAndView
	 */
	@RequestMapping("/user/login.htm")
	public ModelAndView  login(HttpServletRequest request,HttpServletResponse response) {
		
		ModelAndView mv = new JModelAndView("/user/html/user_login.html", 0, request, response);
		return mv;
	}
	
	
/*	@RequestMapping("/user/adalogin.htm")
	public void adaLogin (HttpServletRequest request,HttpServletResponse response,
			String userName,String password){
		
		int code = -100;
		Map json_map = new HashMap();
		
		Map params = new HashMap();
		params.put("name", userName);
		params.put("password", password);
		
		List<User> list =  userService.query("select obj from User obj where obj.userName=:name and obj.password=:password", params, -1, -1);
		
		if(list!=null &&list.size()>0){
			code = 100;
			
			request.getSession().setAttribute("user", list.get(0));
		}
		
		json_map.put("code", code);
		json_map.put("url", "/user/site_list.htm");
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
	}*/
	
	@RequestMapping("/user/loginSucess.htm")
	public ModelAndView loginSuccess(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new JModelAndView("/user/html/index_SiteSet.html", 0, request, response);
		
		return mv;
	}
}
