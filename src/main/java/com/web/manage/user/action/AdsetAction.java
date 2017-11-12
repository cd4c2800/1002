package com.web.manage.user.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.web.core.beans.BeanUtils;
import com.web.core.beans.BeanWrapper;
import com.web.core.mv.JModelAndView;
import com.web.core.query.support.IPageList;
import com.web.core.tools.CommUtil;
import com.web.core.tools.WebForm;
import com.web.foundation.service.IAdService;
import com.web.foundation.service.IAda_ad_channelService;
import com.web.foundation.service.ISysConfigService;
import com.web.foundation.service.IAda_collect_access_logService;
import com.web.foundation.service.ISys_regionService;
import com.web.foundation.domain.Ad;
import com.web.foundation.domain.Ada_access_day_ip_adchannel;
import com.web.foundation.domain.Ada_ad_channel;
import com.web.foundation.domain.Ada_collect_access_log;
import com.web.foundation.domain.Site;
import com.web.foundation.domain.Sys_region;
import com.web.foundation.domain.User;
import com.web.foundation.domain.query.Ada_collect_access_logQueryObject;

@Controller
public class AdsetAction {
	@Autowired
	private ISys_regionService iSys_regionService;
	@Autowired
	private IAda_collect_access_logService ada_collect_access_logService;	
	@Autowired
	private IAdService iAdService;	
	@Autowired
	private IAda_ad_channelService iAda_ad_channelService;
	
	
	/**
     * 广告列表
     *(zhou 11.9)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/Ad_set_list.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public void Ad_set_list(HttpServletRequest request, HttpSession session,HttpServletResponse response){
   
		Integer siteId = (Integer)request.getSession().getAttribute("currentSiteId");	   
	   JSONObject resultJson = new JSONObject();
             Map params = new HashMap();
     		 params.put("siteId",siteId); 
     	      List<Ad>  total_days = this.iAdService.query("select obj from Ad obj where siteId=:siteId", params, -1, -1);   	   		 	 	    		
     		    String[][] mm = makeTser_Ad_set_list(total_days);
            	resultJson.put("makes", mm);
            	resultJson.put("status", "success");
            	String json = Json.toJson(resultJson, JsonFormat.compact());
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
	
	
       public String[][] makeTser_Ad_set_list(List<Ad> total_days) {
		String[][]  mm = new  String[total_days.size()][];		 
		for(int i=0; i<total_days.size() ;i++){
			String[] kk = new String[4];
			kk[0] = total_days.get(i).getId()+"";
			kk[1] = total_days.get(i).getName();
			kk[2] = total_days.get(i).getPrefix();
			kk[3] = "<a href='/adaToshow/user/ad_like_channelList.htm?id="+total_days.get(i).getId()+"'>渠道查看</a>&nbsp;<a onclick='add_channle("+total_days.get(i).getId()+");'>添加渠道</a>&nbsp;<a onclick='update_add("+total_days.get(i).getId()+");'>修改</a>&nbsp;<a onclick='del("+total_days.get(i).getId()+");'>删除</a>";
			mm[i] =kk;
		}			
		return mm;
	}
       
                  
   	
   /**
   	 * 广告下渠道查看
   	 * @param request
   	 * @param response
   	 * @param currentPage
   	 * @param siteid
   	 * @return
   	 */
   	@RequestMapping("/user/ad_like_channelList.htm")
   	public ModelAndView ad_like_channelList(HttpServletRequest request,HttpServletResponse response,String currentPage,
   			String id) {
   		ModelAndView mv = new JModelAndView("/user/html/index_AdSet_channel.html",0, request,response);  	    
   		mv.addObject("id", id);	
   		return mv;
   	}
       
       
    /**
     * 莫个广告下的渠道编辑列表
     *(zhou 11.9)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/adset_channelList.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public void adset_channelList(HttpServletRequest request, HttpSession session,String id,HttpServletResponse response){
             JSONObject resultJson = new JSONObject();
             Map paramK = new HashMap();
     		 paramK.put("id", Integer.parseInt(id)); 
     		 List<Ad>  ad = this.iAdService.query("select obj from Ad obj where id=:id", paramK, -1, -1);   
     		 resultJson.put("ad_name", ad.get(0).getName());
             Map params = new HashMap();
     		 params.put("id", Integer.parseInt(id)); 
     		 Integer site = (Integer) request.getSession().getAttribute("currentSiteId");
     		 params.put("siteId", site); 
     	      List<Ada_ad_channel>  total_days = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where adId=:id and siteId=:siteId", params, -1, -1);   	   		 	 	    		
     		    String[][] mm = makeadset_channelList(total_days);
            	resultJson.put("makes", mm);
            	resultJson.put("status", "success");
            	String json = Json.toJson(resultJson, JsonFormat.compact());
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
	
	
       public String[][] makeadset_channelList(List<Ada_ad_channel> total_days) {
		String[][]  mm = new  String[total_days.size()][];		 
		for(int i=0; i<total_days.size() ;i++){
			String[] kk = new String[4];
			kk[0] = total_days.get(i).getId()+"";
			kk[1] = total_days.get(i).getName();
			kk[2] = total_days.get(i).getContent();
			kk[3] = "<a onclick='update_add("+total_days.get(i).getId()+");'>修改</a>&nbsp;<a onclick='del("+total_days.get(i).getId()+","+total_days.get(i).getAdId()+");'>删除</a>";
			mm[i] =kk;
		}			
		return mm;
	} 
   	
   	
       /**
   	 * 添加广告
   	 * @author （zhou）
   	 * @date 2017年11月9日
   	 * @return void
   	 */
   	@RequestMapping("/user/save_Ad.htm")
   	public void save_Ad(HttpServletRequest request,HttpServletResponse response,
   			String name,String prefix){
   		int code = -100;
   		Map json_map = new HashMap();
   		User user = (User) request.getSession().getAttribute("user");
   		Integer site = (Integer) request.getSession().getAttribute("currentSiteId");
   		if(user!=null){
   			Ad ad = new Ad();
   			ad.setCreateTime(new Date());
   			ad.setName(name);
   			ad.setPrefix(prefix);
   			ad.setSiteId(site);
   			boolean b=  this.iAdService.save(ad);
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
	
   	
    /**
   	 * 删除广告
   	 * @author zhou
   	 * @date 2017年11月9日
   	 * @return void
   	 */
   	@RequestMapping("/user/delect_Ad.htm")
   	public void delect_Ad(HttpServletRequest request,HttpServletResponse response,
   			String id){
   		int code = -100;
   		Map json_map = new HashMap();
   		User user = (User) request.getSession().getAttribute("user"); 	
   		if(user!=null){
   		  Map params = new HashMap();
  		  params.put("id", Integer.parseInt(id)); 
   			boolean b=  this.iAdService.delete(Integer.parseInt(id));
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
   	
   	/**
	 * 修改广告页面
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param siteid
	 * @return
	 */
	@RequestMapping("/user/update_like_ad.htm")
	public ModelAndView ad_eip_list(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String id) {
		ModelAndView mv = new JModelAndView("/user/html/update_Ad.html",0, request,response);
	      Map params = new HashMap();
		  params.put("id", Integer.parseInt(id)); 
  		  List<Ad>  ad = this.iAdService.query("select obj from Ad obj where id=:id", params, -1, -1);
		mv.addObject("id", id);	
		mv.addObject("name", ad.get(0).getName());	
		mv.addObject("prefix", ad.get(0).getPrefix());	
		return mv;
	}
   	
   	
   	
    /**
   	 * 修改广告
   	 * @author zhou
   	 * @date 2017年11月9日
   	 * @return void
   	 */
   	@RequestMapping("/user/update_Ad.htm")
   	public void updtae_Ad(HttpServletRequest request,HttpServletResponse response,
   			String id,String name,String prefix){
   		int code = -100;
   		Map json_map = new HashMap();
   		User user = (User) request.getSession().getAttribute("user"); 	
   		if(user!=null){
   		  Map params = new HashMap();
  		  params.put("id", Integer.parseInt(id)); 
  		  List<Ad>  ad = this.iAdService.query("select obj from Ad obj where id=:id", params, -1, -1);
  		    ad.get(0).setCreateTime(new Date());
  		    ad.get(0).setName(name);
  		    ad.get(0).setPrefix(prefix);
   			boolean b=  this.iAdService.update(ad.get(0));
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
   	
   	
   	/**
	 * 添加广告渠道页面
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param siteid
	 * @return
	 */
	@RequestMapping("/user/add_Ad_Channle_like.htm")
	public ModelAndView add_Ad_Channle_like(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String id) {
		ModelAndView mv = new JModelAndView("/user/html/add_Ad_Channle.html",0, request,response);	   
		mv.addObject("id", id);	
		return mv;
	}
   	
   /**
   	 * 添加渠道
   	 * @author （zhou）
   	 * @date 2017年11月9日
   	 * @return void
   	 */
   	@RequestMapping("/user/save_Ad_channle.htm")
   	public void save_Ad_channle(HttpServletRequest request,HttpServletResponse response,
   			String name,String prefix,String id){
   		int code = -100;
   		Map json_map = new HashMap();
   		User user = (User) request.getSession().getAttribute("user");
   		Integer site = (Integer) request.getSession().getAttribute("currentSiteId");
   		if(user!=null){
   			Ada_ad_channel ad = new Ada_ad_channel();
   			ad.setCreateTime(new Date());
   			ad.setName(name);
   			ad.setContent(prefix);
   			ad.setSiteId(site);
   			ad.setRule(0);
   			ad.setAdId(Integer.parseInt(id));
   			boolean b=  this.iAda_ad_channelService.save(ad);
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
   	 	
    /**
   	 * 删除渠道
   	 * @author zhou
   	 * @date 2017年11月9日
   	 * @return void
   	 */
   	@RequestMapping("/user/delect_channle.htm")
   	public void delect_channle(HttpServletRequest request,HttpServletResponse response,
   			String id){
   		int code = -100;
   		Map json_map = new HashMap();
   		User user = (User) request.getSession().getAttribute("user"); 	
   		if(user!=null){		 
   			boolean b=  this.iAda_ad_channelService.delete(Integer.parseInt(id));
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
   	
   	
   	/**
	 * 修改渠道页面
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param siteid
	 * @return
	 */
	@RequestMapping("/user/update_like_channel.htm")
	public ModelAndView update_like_channel(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String id) {
		ModelAndView mv = new JModelAndView("/user/html/update_channel.html",0, request,response);
	      Map params = new HashMap();
		  params.put("id", Integer.parseInt(id)); 
  		  List<Ada_ad_channel>  ad = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where id=:id", params, -1, -1);
		mv.addObject("id", id);	
		mv.addObject("adid", ad.get(0).getAdId());	
		mv.addObject("name", ad.get(0).getName());	
		mv.addObject("prefix", ad.get(0).getContent());	
		return mv;
	}
   	
   	
   	
    /**
   	 * 修改广告
   	 * @author (zhou)
   	 * @date 2017年11月9日
   	 * @return void
   	 */
   	@RequestMapping("/user/update_channel.htm")
   	public void update_channel(HttpServletRequest request,HttpServletResponse response,
   			String id,String name,String prefix){
   		int code = -100;
   		Map json_map = new HashMap();
   		User user = (User) request.getSession().getAttribute("user"); 	
   		if(user!=null){
   		  Map params = new HashMap();
		  params.put("id", Integer.parseInt(id)); 
  		  List<Ada_ad_channel>  ad = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where id=:id", params, -1, -1);
            ad.get(0).setCreateTime(new Date());
  		    ad.get(0).setName(name);
  		    ad.get(0).setContent(prefix);
   			boolean b=  this.iAda_ad_channelService.update(ad.get(0));
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
   	
   	
   	

   	
   	
   	
	
}