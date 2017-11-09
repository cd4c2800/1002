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

import com.web.core.domain.virtual.SysMap;
import com.web.core.mv.JModelAndView;
import com.web.core.query.support.IPageList;
import com.web.core.tools.CommUtil;
import com.web.core.tools.Md5Encrypt;
import com.web.core.tools.WebForm;
import com.web.foundation.service.IAdService;
import com.web.foundation.service.ISysConfigService;
import com.web.foundation.domain.Ad;
import com.web.foundation.domain.query.AdQueryObject;

@Controller
public class AdManageAction {
	@Autowired
	private ISysConfigService configService;
	
	@Autowired
	private IAdService adService;	
	/**
	 * Ad列表页
	 * 
	 * @param currentPage
	 * @param orderBy
	 * @param orderType
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/ad_list.htm")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String siteid) {
		ModelAndView mv = new JModelAndView("/user/html/index_AdSet.html",0, request,response);
		String url = this.configService.getSysConfig().getAddress();
		if (url == null || url.equals("")) {
			url = CommUtil.getURL(request);
		}
		String params = "";
		AdQueryObject qo = new AdQueryObject(currentPage, mv, "createTime","asc");
		if(siteid!=null && !"".equals(siteid)){
			qo.addQuery("obj.siteId", new SysMap("id",CommUtil.null2Int(siteid)), "=");
		}else{
			qo.addQuery("obj.siteId", new SysMap("id",-1), "=");
		}
		// WebForm wf = new WebForm();
		// wf.toQueryPo(request, qo,Ad.class,mv);
		IPageList pList = this.adService.list(qo);
		CommUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
		return mv;
	}
	/**
	 * ad添加管理
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/admin/ad_add.htm")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response,String currentPage) {
		ModelAndView mv = new JModelAndView("admin/blue/ad_add.html", 0, request,response);
		mv.addObject("currentPage", currentPage);
		return mv;
	}
	/**
	 * ad编辑管理
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/admin/ad_edit.htm")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response,String id,String currentPage)
        {
		ModelAndView mv = new JModelAndView("admin/blue/ad_add.html", 0, request,response);
		if (id != null&&!id.equals("")) {
			Ad ad = this.adService.getObjById(Long.parseLong(id));
				mv.addObject("obj", ad);
		    mv.addObject("currentPage", currentPage);
		    mv.addObject("edit", true);
		}
		return mv;
	}
     /**
		 * ad保存管理
		 * 
		 * @param id
		 * @return
		 */
	@RequestMapping("/admin/ad_save.htm")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response, String id,String currentPage, String cmd, String list_url, String add_url) {
		WebForm wf = new WebForm();
		Ad ad =null;
		if (id.equals("")) {
			 ad = wf.toPo(request, Ad.class);
			ad.setCreateTime(new Date());
		}else{
			Ad obj=this.adService.getObjById(Long.parseLong(id));
			ad = (Ad)wf.toPo(request,obj);
		}
		
		if (id.equals("")) {
			this.adService.save(ad);
		} else
			this.adService.update(ad);
	   ModelAndView mv = new JModelAndView("admin/blue/success.html",0, request,response);
			mv.addObject("list_url", list_url);
			mv.addObject("op_title", "保存ad成功");
			if (add_url != null) {
				mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
			}
		return mv;
	}
	@RequestMapping("/admin/ad_del.htm")
	public String delete(HttpServletRequest request,HttpServletResponse response,String mulitId,String currentPage) {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
			  Ad ad = this.adService.getObjById(Long.parseLong(id));
			  this.adService.delete(Long.parseLong(id));
			}
		}
		return "redirect:ad_list.htm?currentPage="+currentPage;
	}
	
}