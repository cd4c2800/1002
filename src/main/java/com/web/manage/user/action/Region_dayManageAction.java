package com.web.manage.user.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
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
import com.web.foundation.service.IRegion_eipService;
import com.web.foundation.service.IRegion_euvService;
import com.web.foundation.service.ISysConfigService;
import com.web.foundation.service.IRegion_dayService;
import com.web.foundation.domain.Region_day;
import com.web.foundation.domain.Region_euv;
import com.web.foundation.domain.query.Region_dayQueryObject;
import com.web.foundation.domain.query.Region_eipQueryObject;
import com.web.foundation.domain.query.Region_euvQueryObject;
import com.web.manage.user.tools.AreaUtil;

@Controller
public class Region_dayManageAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IRegion_dayService region_dayService;
	@Autowired
	private IRegion_eipService region_eipService;
	@Autowired
	private IRegion_euvService region_euvService;
	@Autowired
	private AreaUtil areaUtil;
	/**
	 * Region_day列表页 地域分析
	 * 
	 * @param currentPage
	 * @param orderBy
	 * @param orderType
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/region_day_list.htm")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String beginTime,String endTime) {
		
		ModelAndView mv = new JModelAndView("/user/html/index_Region.html",0, request,response);
		Region_dayQueryObject qo = new Region_dayQueryObject(currentPage, mv, "pv","desc");
		qo.setPageSize(-1);
		Region_dayQueryObject qo2 = new Region_dayQueryObject(currentPage, mv, "provinceId","desc");
		Integer siteId = (Integer) request.getSession().getAttribute("currentSiteId");
		if((beginTime==null || "".equals(beginTime))&&(endTime==null || "".equals(endTime))){//没有日期 默认查询昨天的统计数据
			qo.addQuery("obj.startTime", new SysMap("startTime",CommUtil.getYesterdayBegin()), "=");
			qo2.addQuery("obj.startTime", new SysMap("startTime",CommUtil.getYesterdayBegin()), "=");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			beginTime = sdf.format(CommUtil.getYesterdayBegin());
			endTime = sdf.format(CommUtil.getYesterDayEnd());
		}
		if(beginTime!=null && !"".equals(beginTime)){
			qo.addQuery("obj.startTime", new SysMap("beginTime",CommUtil.getDayBeginTime(beginTime)), ">=");
			qo2.addQuery("obj.startTime", new SysMap("beginTime",CommUtil.getDayBeginTime(beginTime)), ">=");
		}
		if(endTime!=null && !"".equals(endTime)){
			qo.addQuery("obj.startTime", new SysMap("endTime",CommUtil.getDayEndTime(endTime)), "<=");
			qo2.addQuery("obj.startTime", new SysMap("endTime",CommUtil.getDayEndTime(endTime)), "<=");
		}
		qo.addQuery("obj.siteId", new SysMap("siteId",CommUtil.null2Long(siteId)), "=");
		qo2.addQuery("obj.siteId", new SysMap("siteId",CommUtil.null2Long(siteId)), "=");
		
		qo2.addQuery("1=1  group by obj.provinceId", null);
		
		IPageList pList = this.region_dayService.list(qo);
		IPageList pList2 = this.region_dayService.list(qo2);
		
		int pv = 0;
		int uv = 0;
		int ip = 0;
		int epv = 0;
		int euv = 0;
		int eip = 0;
		List<Region_day> list  = pList.getResult();
		List<Region_day> list2  = pList2.getResult();
		
		List<Map> data = new ArrayList<Map>();
		for(int i=0;i<list.size();i++){//循环所有数据
			
			Map map = new HashMap();
			Region_day day = list.get(i);
			for(int j=0;j<list2.size();j++){//循环省份
				Region_day region_day = list2.get(j);
				if(day.getProvinceId()==region_day.getProvinceId()){
					
				}
				
			}
			//统计六个数据
			pv += day.getPv();
			uv += day.getUv();
			ip += day.getIp();
			epv += day.getEpv();
			euv += day.getEuv();
			eip += day.getEip();
			//获取地域信息
			String name = areaUtil.getProvinceById(CommUtil.null2String(day.getProvinceId()));
			
			if(name.indexOf("新疆")!=-1){
				name = name.substring(0, 2);
			}else if(name.indexOf("广西")!=-1){
				name = name.substring(0, 2);
			}else if(name.indexOf("西藏")!=-1){
				name = name.substring(0, 2);
			}
			else if(name.indexOf("宁夏")!=-1){
				name = name.substring(0, 2);
			}
			else if(name.indexOf("内蒙古")!=-1){
				name = name.substring(0, 3);
			}else{
				name = name.substring(0,name.length()-1);
			}
			
			map.put("name", name);
			map.put("value", day.getPv());
			data.add(map);
		}
		
		String json = Json.toJson(data, JsonFormat.compact());
		System.out.println("地域数据："+json);
		mv.addObject("data", json);
		
		mv.addObject("pv", pv);
		mv.addObject("uv", uv);
		mv.addObject("ip", ip);
		mv.addObject("epv", epv);
		mv.addObject("euv", euv);
		mv.addObject("eip", eip);
		mv.addObject("bpv", Math.round(CommUtil.div(epv, pv)*100));
		mv.addObject("buv", Math.round(CommUtil.div(euv, uv)*100));
		mv.addObject("bip", Math.round(CommUtil.div(eip, ip)*100));
		mv.addObject("beginTime", beginTime);
		mv.addObject("endTime", endTime);
		CommUtil.saveIPageList2ModelAndView("","","",pList, mv);
		mv.addObject("areaUtil", areaUtil);
		return mv;
	}
	
	/**
	 * 查看Eip信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/region_eip.htm")
	public ModelAndView region_eip(HttpServletRequest request,HttpServletResponse response,
			String currentPage,String provinceName,String provinceId,String beginTime,String endTime){
		ModelAndView mv = new JModelAndView("/user/html/region_EIP.html",0, request,response);
		Integer siteId = (Integer) request.getSession().getAttribute("currentSiteId");
		Region_eipQueryObject qo = new Region_eipQueryObject(currentPage, mv, "epv","desc");
		
		qo.addQuery("obj.siteId", new SysMap("siteId",CommUtil.null2Long(siteId)), "=");
		qo.addQuery("obj.provinceId", new SysMap("provinceId",CommUtil.null2Long(provinceId)), "=");
		qo.addQuery("obj.startTime", new SysMap("beginTime",CommUtil.getDayBeginTime(beginTime)), ">=");
		qo.addQuery("obj.startTime", new SysMap("endTime",CommUtil.getDayBeginTime(endTime)), "<=");
		
		IPageList pList = this.region_eipService.list(qo);
		CommUtil.saveIPageList2ModelAndView("","","",pList, mv);
		mv.addObject("provinceName", provinceName);
		mv.addObject("provinceId", provinceId);
		mv.addObject("beginTime", beginTime);
		mv.addObject("endTime", endTime);
		return mv;
	}
	
	/**
	 * 
	 * 查看Euv信息
	 * @return
	 */
	@RequestMapping("/user/region_euv.htm")
	public ModelAndView region_euv(HttpServletRequest request,HttpServletResponse response,
			String currentPage,String provinceName,String provinceId,String beginTime,String endTime){
		ModelAndView mv = new JModelAndView("/user/html/region_EUV.html",0, request,response);
		Integer siteId = (Integer) request.getSession().getAttribute("currentSiteId");
		Region_euvQueryObject qo = new Region_euvQueryObject(currentPage, mv, "epv","desc");
		
		qo.addQuery("obj.siteId", new SysMap("siteId",CommUtil.null2Long(siteId)), "=");
		qo.addQuery("obj.provinceId", new SysMap("provinceId",CommUtil.null2Long(provinceId)), "=");
		qo.addQuery("obj.startTime", new SysMap("beginTime",CommUtil.getDayBeginTime(beginTime)), ">=");
		qo.addQuery("obj.startTime", new SysMap("endTime",CommUtil.getDayBeginTime(endTime)), "<=");
		
		IPageList pList = this.region_euvService.list(qo);
		CommUtil.saveIPageList2ModelAndView("","","",pList, mv);
		mv.addObject("provinceName", provinceName);
		mv.addObject("provinceId", provinceId);
		mv.addObject("beginTime", beginTime);
		mv.addObject("endTime", endTime);
		return mv;
	}
	
	
	
	/**
	 * region_day添加管理
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/user/region_day_add.htm")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response,String currentPage) {
		ModelAndView mv = new JModelAndView("/user/html/region_day_add.html",0, request,response);
		mv.addObject("currentPage", currentPage);
		return mv;
	}
	/**
	 * region_day编辑管理
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/user/region_day_edit.htm")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response,String id,String currentPage)
        {
		ModelAndView mv = new JModelAndView("/user/html/region_day_add.html",0, request,response);
		if (id != null&&!id.equals("")) {
			Region_day region_day = this.region_dayService.getObjById(Long.parseLong(id));
				mv.addObject("obj", region_day);
		    mv.addObject("currentPage", currentPage);
		    mv.addObject("edit", true);
		}
		return mv;
	}
     /**
		 * region_day保存管理
		 * 
		 * @param id
		 * @return
		 */
	@RequestMapping("/user/region_day_save.htm")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response, String id,String currentPage, String cmd, String list_url, String add_url) {
		WebForm wf = new WebForm();
		Region_day region_day =null;
		if (id.equals("")) {
			 region_day = wf.toPo(request, Region_day.class);
			region_day.setCreateTime(new Date());
		}else{
			Region_day obj=this.region_dayService.getObjById(Long.parseLong(id));
			region_day = (Region_day)wf.toPo(request,obj);
		}
		
		if (id.equals("")) {
			this.region_dayService.save(region_day);
		} else
			this.region_dayService.update(region_day);
	   ModelAndView mv = new JModelAndView("/user/html/success.html",0, request,response);
			mv.addObject("list_url", list_url);
			mv.addObject("op_title", "保存region_day成功");
			if (add_url != null) {
				mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
			}
		return mv;
	}
	@RequestMapping("/user/region_day_del.htm")
	public String delete(HttpServletRequest request,HttpServletResponse response,String mulitId,String currentPage) {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
			  Region_day region_day = this.region_dayService.getObjById(Long.parseLong(id));
			  this.region_dayService.delete(Long.parseLong(id));
			}
		}
		return "redirect:region_day_list.htm?currentPage="+currentPage;
	}
	@RequestMapping("/user/region_day_ajax.htm")
	public void ajax(HttpServletRequest request, HttpServletResponse response,
			String id, String fieldName, String value)
			throws ClassNotFoundException {
		Region_day obj = this.region_dayService.getObjById(Long.parseLong(id));
		Field[] fields = Region_day.class.getDeclaredFields();
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
		this.region_dayService.update(obj);
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