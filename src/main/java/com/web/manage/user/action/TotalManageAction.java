package com.web.manage.user.action;
import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.Field;



import org.springframework.stereotype.Controller;


import java.text.ParseException;

import java.util.Date;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.core.beans.BeanUtils;
import com.web.core.beans.BeanWrapper;
import com.web.core.mv.JModelAndView;
import com.web.core.tools.CommUtil;
import com.web.core.tools.WebForm;
import com.web.foundation.service.ISysConfigService;
import com.web.foundation.service.ITotal_dayService;
import com.web.foundation.domain.Total_day;


@Controller
public class TotalManageAction {
	@Autowired
	private ISysConfigService configService;

	@Autowired
	private ITotal_dayService total_dayService;	
	/**
	 * total_day添加管理
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/admin/total_day_add.htm")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response,String currentPage) {
		ModelAndView mv = new JModelAndView("admin/blue/total_day_add.html",0, request,response);
		mv.addObject("currentPage", currentPage);
		return mv;
	}
	/**
	 * total_day编辑管理
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/admin/total_day_edit.htm")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response,String id,String currentPage)
        {
		ModelAndView mv = new JModelAndView("admin/blue/total_day_add.html",0, request,response);
		if (id != null&&!id.equals("")) {
			Total_day total_day = this.total_dayService.getObjById(Long.parseLong(id));
				mv.addObject("obj", total_day);
		    mv.addObject("currentPage", currentPage);
		    mv.addObject("edit", true);
		}
		return mv;
	}
     /**
		 * total_day保存管理
		 * 
		 * @param id
		 * @return
		 */
	@RequestMapping("/admin/total_day_save.htm")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response, String id,String currentPage, String cmd, String list_url, String add_url) {
		WebForm wf = new WebForm();
		Total_day total_day =null;
		if (id.equals("")) {
			 total_day = wf.toPo(request, Total_day.class);
			total_day.setCreateTime(new Date());
		}else{
			Total_day obj=this.total_dayService.getObjById(Long.parseLong(id));
			total_day = (Total_day)wf.toPo(request,obj);
		}
		
		if (id.equals("")) {
			this.total_dayService.save(total_day);
		} else
			this.total_dayService.update(total_day);
	   ModelAndView mv = new JModelAndView("admin/blue/success.html",0, request,response);
			mv.addObject("list_url", list_url);
			mv.addObject("op_title", "保存total_day成功");
			if (add_url != null) {
				mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
			}
		return mv;
	}
	@RequestMapping("/admin/total_day_del.htm")
	public String delete(HttpServletRequest request,HttpServletResponse response,String mulitId,String currentPage) {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
			  Total_day total_day = this.total_dayService.getObjById(Long.parseLong(id));
			  this.total_dayService.delete(Long.parseLong(id));
			}
		}
		return "redirect:total_day_list.htm?currentPage="+currentPage;
	}
	@RequestMapping("/admin/total_day_ajax.htm")
	public void ajax(HttpServletRequest request, HttpServletResponse response,
			String id, String fieldName, String value)
			throws ClassNotFoundException {
		Total_day obj = this.total_dayService.getObjById(Long.parseLong(id));
		Field[] fields = Total_day.class.getDeclaredFields();
		BeanWrapper wrapper = new BeanWrapper(obj);
		Object val = null;
		for (Field field : fields) {
			// System.out.println(field.getName());
			if (field.getName().equals(fieldName)) {
				Class clz = Class.forName("java.lang.String");
				if (field.getType().getName().equals("int")) {
					clz = Class.forName("java.lang.Integer");
				}
				if (field.getType().getName().equals("boolean")) {
					clz = Class.forName("java.lang.Boolean");
				}
				if (!value.equals("")) {
					val = BeanUtils.convertType(value, clz);
				} else {
					val = !CommUtil.null2Boolean(wrapper
							.getPropertyValue(fieldName));
				}
				wrapper.setPropertyValue(fieldName, val);
			}
		}
		this.total_dayService.update(obj);
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.print(val.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}