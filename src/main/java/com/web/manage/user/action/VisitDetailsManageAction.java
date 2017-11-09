package com.web.manage.user.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.web.core.domain.virtual.SysMap;
import com.web.core.mv.JModelAndView;
import com.web.core.query.support.IPageList;
import com.web.core.tools.CommUtil;
import com.web.core.tools.WebForm;
import com.web.foundation.service.ISysConfigService;
import com.web.foundation.service.IVisitDetailsService;
import com.web.foundation.domain.VisitDetails;
import com.web.foundation.domain.query.VisitDetailsQueryObject;
import com.web.manage.user.tools.AreaUtil;

@Controller
public class VisitDetailsManageAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IVisitDetailsService visitdetailsService;
	@Autowired
	private AreaUtil areaUtil;
	/**
	 * VisitDetails列表页
	 * 
	 * @param currentPage
	 * @param orderBy
	 * @param orderType
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/visitdetails_list.htm")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String ip,String beginTime,String endTime) {
		ModelAndView mv = new JModelAndView("/user/html/index_VisitDetails.html",0, request,response);
		Integer siteId = (Integer) request.getSession().getAttribute("currentSiteId");
		VisitDetailsQueryObject qo = new VisitDetailsQueryObject(currentPage, mv, "createTime","desc");
		
		
		qo.addQuery("obj.siteId", new SysMap("siteId",CommUtil.null2Long(siteId)), "=");
		if((beginTime==null || "".equals(beginTime))&&(endTime==null || "".equals(endTime))){//没有日期 默认查询昨天的统计数据
			qo.addQuery("obj.createTime", new SysMap("startTime",CommUtil.getDayBegin()), "=");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			beginTime = sdf.format(CommUtil.getDayBegin());
			endTime = sdf.format(CommUtil.getDayEnd());
		}
		if(ip!=null && !"".equals(ip)){
			qo.addQuery("obj.remoteIp", new SysMap("remoteIp","%"+ip+"%"), "like");
		}
		if(beginTime!=null && !"".equals(beginTime)){
			qo.addQuery("obj.createTime", new SysMap("beginTime",CommUtil.getDayBeginTime(beginTime)), ">=");
		}
		if(endTime!=null && !"".equals(endTime)){
			qo.addQuery("obj.createTime", new SysMap("endTime",CommUtil.getDayBeginTime(endTime)), "<=");
			System.out.println("--------->"+CommUtil.getDayBeginTime(endTime));
		}
		
		
		
		IPageList pList = this.visitdetailsService.list(qo);
		CommUtil.saveIPageList2ModelAndView("","","",pList, mv);
		mv.addObject("areaUtil", areaUtil);
		mv.addObject("ip", ip);
		mv.addObject("beginTime", beginTime);
		mv.addObject("endTime", endTime);
		return mv;
	}
	/**
	 * visitdetails添加管理
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/user/visitdetails_add.htm")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response,String currentPage) {
		ModelAndView mv = new JModelAndView("/user/html/visitdetails_add.html",0, request,response);
		mv.addObject("currentPage", currentPage);
		return mv;
	}
	/**
	 * visitdetails编辑管理
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/user/visitdetails_edit.htm")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response,String id,String currentPage)
        {
		ModelAndView mv = new JModelAndView("/user/html/visitdetails_add.html",0, request,response);
		if (id != null&&!id.equals("")) {
			VisitDetails visitdetails = this.visitdetailsService.getObjById(Long.parseLong(id));
				mv.addObject("obj", visitdetails);
		    mv.addObject("currentPage", currentPage);
		    mv.addObject("edit", true);
		}
		return mv;
	}
     /**
		 * visitdetails保存管理
		 * 
		 * @param id
		 * @return
		 */
	@RequestMapping("/user/visitdetails_save.htm")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response, String id,String currentPage, String cmd, String list_url, String add_url) {
		WebForm wf = new WebForm();
		VisitDetails visitdetails =null;
		if (id.equals("")) {
			 visitdetails = wf.toPo(request, VisitDetails.class);
			visitdetails.setCreateTime(new Date());
		}else{
			VisitDetails obj=this.visitdetailsService.getObjById(Long.parseLong(id));
			visitdetails = (VisitDetails)wf.toPo(request,obj);
		}
		
		if (id.equals("")) {
			this.visitdetailsService.save(visitdetails);
		} else
			this.visitdetailsService.update(visitdetails);
	   ModelAndView mv = new JModelAndView("/user/html/success.html",0, request,response);
			mv.addObject("list_url", list_url);
			mv.addObject("op_title", "保存visitdetails成功");
			if (add_url != null) {
				mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
			}
		return mv;
	}
	@RequestMapping("/user/visitdetails_del.htm")
	public String delete(HttpServletRequest request,HttpServletResponse response,String mulitId,String currentPage) {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
			  VisitDetails visitdetails = this.visitdetailsService.getObjById(Long.parseLong(id));
			  this.visitdetailsService.delete(Long.parseLong(id));
			}
		}
		return "redirect:visitdetails_list.htm?currentPage="+currentPage;
	}
	@RequestMapping("/user/visitdetails_ajax.htm")
	public void ajax(HttpServletRequest request, HttpServletResponse response,
			String id, String fieldName, String value)
			throws ClassNotFoundException {
		VisitDetails obj = this.visitdetailsService.getObjById(Long.parseLong(id));
		Field[] fields = VisitDetails.class.getDeclaredFields();
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
		this.visitdetailsService.update(obj);
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