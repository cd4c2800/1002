package com.web.manage.user.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.core.beans.BeanUtils;
import com.web.core.beans.BeanWrapper;
import com.web.core.mv.JModelAndView;
import com.web.core.query.support.IPageList;
import com.web.core.tools.CommUtil;
import com.web.core.tools.WebForm;
import com.web.foundation.service.ISysConfigService;
import com.web.foundation.service.ISys_regionService;
import com.web.foundation.domain.Sys_region;
import com.web.foundation.domain.query.Sys_regionQueryObject;

@Controller
public class Sys_regionManageAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private ISys_regionService sys_regionService;	
	/**
	 * Sys_region列表页
	 * 
	 * @param currentPage
	 * @param orderBy
	 * @param orderType
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/sys_region_list.htm")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,String currentPage, String orderBy,
			String orderType) {
		ModelAndView mv = new JModelAndView("/user/html/sys_region_list.html",0, request,response);
		
		Sys_regionQueryObject qo = new Sys_regionQueryObject(currentPage, mv, orderBy,orderType);
		// WebForm wf = new WebForm();
		// wf.toQueryPo(request, qo,Sys_region.class,mv);
		IPageList pList = this.sys_regionService.list(qo);
		CommUtil.saveIPageList2ModelAndView("","","",pList, mv);
		return mv;
	}
	/**
	 * sys_region添加管理
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/user/sys_region_add.htm")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response,String currentPage) {
		ModelAndView mv = new JModelAndView("/user/html/sys_region_add.html",0, request,response);
		mv.addObject("currentPage", currentPage);
		return mv;
	}
	/**
	 * sys_region编辑管理
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/user/sys_region_edit.htm")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response,String id,String currentPage)
        {
		ModelAndView mv = new JModelAndView("/user/html/sys_region_add.html",0, request,response);
		if (id != null&&!id.equals("")) {
			Sys_region sys_region = this.sys_regionService.getObjById(Long.parseLong(id));
				mv.addObject("obj", sys_region);
		    mv.addObject("currentPage", currentPage);
		    mv.addObject("edit", true);
		}
		return mv;
	}
     /**
		 * sys_region保存管理
		 * 
		 * @param id
		 * @return
		 */
	@RequestMapping("/user/sys_region_save.htm")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response, String id,String currentPage, String cmd, String list_url, String add_url) {
		WebForm wf = new WebForm();
		Sys_region sys_region =null;
		if (id.equals("")) {
			 sys_region = wf.toPo(request, Sys_region.class);
		}else{
			Sys_region obj=this.sys_regionService.getObjById(Long.parseLong(id));
			sys_region = (Sys_region)wf.toPo(request,obj);
		}
		
		if (id.equals("")) {
			this.sys_regionService.save(sys_region);
		} else
			this.sys_regionService.update(sys_region);
	   ModelAndView mv = new JModelAndView("/user/html/success.html",0, request,response);
			mv.addObject("list_url", list_url);
			mv.addObject("op_title", "保存sys_region成功");
			if (add_url != null) {
				mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
			}
		return mv;
	}
	@RequestMapping("/user/sys_region_del.htm")
	public String delete(HttpServletRequest request,HttpServletResponse response,String mulitId,String currentPage) {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
			  Sys_region sys_region = this.sys_regionService.getObjById(Long.parseLong(id));
			  this.sys_regionService.delete(Long.parseLong(id));
			}
		}
		return "redirect:sys_region_list.htm?currentPage="+currentPage;
	}
	@RequestMapping("/user/sys_region_ajax.htm")
	public void ajax(HttpServletRequest request, HttpServletResponse response,
			String id, String fieldName, String value)
			throws ClassNotFoundException {
		Sys_region obj = this.sys_regionService.getObjById(Long.parseLong(id));
		Field[] fields = Sys_region.class.getDeclaredFields();
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
		this.sys_regionService.update(obj);
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