package com.web.manage.user.action;
import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.Field;



import org.springframework.stereotype.Controller;


import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.core.beans.BeanUtils;
import com.web.core.beans.BeanWrapper;
import com.web.core.mv.JModelAndView;
import com.web.core.tools.CommUtil;
import com.web.core.tools.WebForm;
import com.web.foundation.service.IAdService;
import com.web.foundation.service.ISysConfigService;
import com.web.foundation.service.ITotal_dayService;
import com.web.foundation.domain.Ad;
import com.web.foundation.domain.Site;
import com.web.foundation.domain.Total_day;
import com.web.foundation.domain.User;


@Controller
public class AdvertisementManageAction {
	@Autowired
	private IAdService iadService;
	
	/*
	 * 
	 * 跳转统计代码页面
	 */
	@RequestMapping("/user/code.htm")
	public ModelAndView code(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv=new JModelAndView("/user/html/code.html", 0, request, response);
		return mv;
		
	}
	/*
	 * 
	 * 广告设置
	 */
	
	@RequestMapping("/guanggao.htm")
	public ModelAndView guanggao(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("/user/html/index_AdSet.html",0, request,response);
		List<Ad>list = iadService.query("select obj from  Ad obj", null, -1, -1);
		mv.addObject("list", list);
	
		return mv; 
	}
	   /*
	    *保存广告
	    */
	@RequestMapping("/user/save.htm")
	public void save(HttpServletRequest request,HttpServletResponse response,String name, String prefix) {
		int code = -100;
		Map json_map = new HashMap();
		
		
			Ad ad1 = new Ad();
			ad1.setCreateTime(new Date());
			ad1.setName(name);
			ad1.setPrefix(prefix);
			boolean b=  this.iadService.save(ad1);
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