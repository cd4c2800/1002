package com.web.manage.user.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.web.core.mv.JModelAndView;
import com.web.foundation.domain.Ad;
import com.web.foundation.domain.Ada_access_day_client_adchannel;
import com.web.foundation.domain.Ada_access_day_ip_adchannel;
import com.web.foundation.domain.Ada_access_except_detail;
import com.web.foundation.domain.Ada_access_stat_day_ad;
import com.web.foundation.domain.Ada_access_stat_day_channel;
import com.web.foundation.domain.Ada_ad_channel;
import com.web.foundation.service.IAdService;
import com.web.foundation.service.IAda_access_day_client_adchannelService;
import com.web.foundation.service.IAda_access_day_ip_adchannelService;
import com.web.foundation.service.IAda_access_except_detailService;
import com.web.foundation.service.IAda_access_stat_day_adService;
import com.web.foundation.service.IAda_access_stat_day_channelService;
import com.web.foundation.service.IAda_ad_channelService;
import com.web.foundation.service.ISysConfigService;
import com.web.foundation.service.ITotal_dayService;


@Controller
public class AdaAdItemAction {
	@Autowired
	private IAdService iAdService;
	@Autowired
	private ITotal_dayService iTotal_dayService;
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IAda_access_stat_day_adService iAda_access_stat_day_adService;
	@Autowired
	private IAda_access_stat_day_channelService iAda_access_stat_day_channelService;
	@Autowired
	private IAda_ad_channelService iAda_ad_channelService;
	@Autowired
	private IAda_access_day_client_adchannelService iAda_access_day_client_adchannelService;
	@Autowired
	private IAda_access_day_ip_adchannelService iAda_access_day_ip_adchannelService;
	@Autowired
	private IAda_access_except_detailService iAda_access_except_detailService;
	/**
     * 广告分析列表
     *(zhou 11.1)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/ad_analysis_list.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public void registerJson(HttpServletRequest request, HttpSession session,Integer userId,Integer siteId,Integer dayway,Date begin,Date end,HttpServletResponse response){
             JSONObject resultJson = new JSONObject();
             response.setContentType("text/json;charset=UTF-8");
           	Integer site = (Integer) request.getSession().getAttribute("currentSiteId");
             if(site!=null){
            	 siteId= site;
             }
         	Map params = new HashMap();
    		params.put("siteid", siteId);
    		params.put("startime", begin);
    		params.put("endtime",end);
     		List<Ada_access_stat_day_ad>  total_days = this.iAda_access_stat_day_adService.query("select obj from Ada_access_stat_day_ad obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);
            	 
            	String[][] mm = makeTser(total_days,begin,end);
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
	
	public String[][] makeTser(List<Ada_access_stat_day_ad> total_days,Date begin,Date end) {
		String[][]  mm = new  String[total_days.size()][];
		for(int i=0; i<total_days.size() ;i++){
			String[] kk = new String[8];
			  Map paramsF = new HashMap();
    		  paramsF.put("id", total_days.get(i).getAdId());
       		List<Ad>  ad = this.iAdService.query("select obj from Ad obj where obj.id=:id", paramsF, -1, -1); 
       		String BBY = "无";
       		if(ad.size()==0){
       			
       		}else{
       			BBY	 = ad.get(0).getName();
       		}
			kk[0] = BBY;
			kk[1] =total_days.get(i).getPv()+"";
			kk[2] =total_days.get(i).getUv()+"";
			kk[3] =total_days.get(i).getIp()+"";
			kk[4] =total_days.get(i).getEpv()+"";
			kk[5] ="<a href='/adaToshow/user/ad_euv_list.htm?siteId="+total_days.get(i).getSiteId()+"&adId="+total_days.get(i).getAdId()+"&name="+BBY+"&begin="+begin+"&end="+end+"'>"+total_days.get(i).getEuv()+"</a>";
			kk[6] ="<a href='/adaToshow/user/ad_eip_list.htm?siteId="+total_days.get(i).getSiteId()+"&adId="+total_days.get(i).getAdId()+"&name="+BBY+"&begin="+begin+"&end="+end+"'>"+total_days.get(i).getEip()+"</a>";
			kk[7] ="<a href='/adaToshow/user/ad_details_list.htm?siteId="+total_days.get(i).getSiteId()+"&adId="+total_days.get(i).getAdId()+"&name="+BBY+"'>查看详情</a>";
			mm[i] =kk;
		}			
		return mm;
	}
	
	
	/**
	 * 广告分析详情eip
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param siteid
	 * @return
	 */
	@RequestMapping("/user/ad_eip_list.htm")
	public ModelAndView ad_eip_list(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String siteId,String adId,String begin,String end,String name) {
		ModelAndView mv = new JModelAndView("/user/html/Ad_EIP.html",0, request,response);
		mv.addObject("siteid", siteId);
		mv.addObject("adId", adId);	
		mv.addObject("begin", begin);
		mv.addObject("end", end);
		   SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
	       try
	       {
	       	   Date date=sdf1.parse(begin);
	       	   Date date1=sdf1.parse(end);
	           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	           String begins=sdf.format(date);
	           String ends=sdf.format(date1);
	           mv.addObject("begins", begins); 
	           mv.addObject("ends", ends); 
	       }
	       catch (ParseException e)
	       {
	           e.printStackTrace();
	       }
		mv.addObject("name", name);
		return mv;
	}
	
	/**
     * 广告eip
     *(zhou 11.3)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/ad_EIP_list.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public void ad_EIP_list(HttpServletRequest request, HttpSession session,String siteId,String adId,Integer userId,Date begin,Date end,HttpServletResponse response){
             JSONObject resultJson = new JSONObject();
             Map paramS = new HashMap();
             paramS.put("adId",  Integer.parseInt(adId));	
             List<Ad> ad = this.iAdService.query("select obj from Ad obj where id=:adId", paramS, -1, -1);
             resultJson.put("adid_like", ad.get(0).getName());
             
             Map params = new HashMap();
     		 params.put("siteId", Integer.parseInt(siteId));
     		 params.put("adId",  Integer.parseInt(adId));	 
     		 params.put("startime", begin);
    		 params.put("endtime",end);
     		List<Ada_access_day_ip_adchannel>  total_days = this.iAda_access_day_ip_adchannelService.query("select obj from Ada_access_day_ip_adchannel obj where siteId=:siteId and adId=:adId and obj.endTime between :startime and :endtime", params, -1, -1);   	   		 
            	String[][] mm = makeTser_ad_eip(total_days);
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
	
	public String[][] makeTser_ad_eip(List<Ada_access_day_ip_adchannel> total_days) {
		String[][]  mm = new  String[total_days.size()][];
		  Map map =new HashMap();
		  map.put("1", "天天访问");
		  map.put("2", "高频访问");
		  map.put("3", "频繁切换ip");
		  map.put("4", "停留时间超短");
		  map.put("5", "疑似作弊软件");
		  map.put("6", "直接访问");
		  map.put("7", "频繁切换客户端");
		  map.put("8", "只访问广告");
		for(int i=0; i<total_days.size() ;i++){
			String[] kk = new String[4];
			if(total_days.get(i).getRemoteIp() ==null){
				kk[0] ="空";
			}else{
				kk[0] =total_days.get(i).getRemoteIp();
			}
            kk[1] =total_days.get(i).getEpv()+"";
            String[] sourceStrArray = total_days.get(i).getExceType().split(",");
            String G="";
            for (int k = 0; k < sourceStrArray.length; k++) {
                System.out.println(sourceStrArray[k]);
                if(k==sourceStrArray.length-1){
                	 G+=map.get(sourceStrArray[k]);
                }else{
                	 G+=map.get(sourceStrArray[k])+",";
                }  
            }  
            kk[2] =G; 
            kk[3] ="<a href='/adaToshow/user/ad_eipMake.htm?siteId="+total_days.get(i).getSiteId()+"&adId="+total_days.get(i).getAdId()+"&remoteIp="+total_days.get(i).getRemoteIp()+"'>查看IP详情</a>";
			mm[i] =kk;
		}			
		return mm;
	}
	
	
	/**
	 * 广告分析详情euv
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param siteid
	 * @return
	 */
	@RequestMapping("/user/ad_euv_list.htm")
	public ModelAndView ad_euv_list(HttpServletRequest request,HttpServletResponse response,String currentPage,String siteId,String adId,String name,
		String begin,String end) {
		ModelAndView mv = new JModelAndView("/user/html/Ad_EUV.html",0, request,response);
		mv.addObject("siteid", siteId);
		mv.addObject("adId", adId);
		mv.addObject("begin", begin);
		mv.addObject("end", end);
		   SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
	       try
	       {
	       	   Date date=sdf1.parse(begin);
	       	   Date date1=sdf1.parse(end);
	           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	           String begins=sdf.format(date);
	           String ends=sdf.format(date1);
	           mv.addObject("begins", begins); 
	           mv.addObject("ends", ends); 
	       }
	       catch (ParseException e)
	       {
	           e.printStackTrace();
	       }
		mv.addObject("name", name);
		return mv;
	}
	
	
	
	/**
     * 广告euv
     *(zhou 11.1)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/ad_EUV_list.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public void ad_EUV_list(HttpServletRequest request, HttpSession session,String siteId,String adId,Date begin,Date end,HttpServletResponse response){
             JSONObject resultJson = new JSONObject();
             
             Map paramS = new HashMap();
             paramS.put("adId",  Integer.parseInt(adId));	
             List<Ad> ad = this.iAdService.query("select obj from Ad obj where id=:adId", paramS, -1, -1);
             resultJson.put("adid_like", ad.get(0).getName());
             
             Map params = new HashMap();
     		 params.put("siteId", Integer.parseInt(siteId));
     		 params.put("adId",  Integer.parseInt(adId));	 
     		 params.put("startime", begin);
    		 params.put("endtime",end);
     		List<Ada_access_day_client_adchannel>  total_days = this.iAda_access_day_client_adchannelService.query("select obj from Ada_access_day_client_adchannel obj where siteId=:siteId and adId=:adId and obj.endTime between :startime and :endtime", params, -1, -1);   	   		 
            	String[][] mm = makeTser_ad_euv(total_days);
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
	
	public String[][] makeTser_ad_euv(List<Ada_access_day_client_adchannel> total_days) {
		String[][]  mm = new  String[total_days.size()][];
		  Map map =new HashMap();
		  map.put("1", "天天访问");
		  map.put("2", "高频访问");
		  map.put("3", "频繁切换ip");
		  map.put("4", "停留时间超短");
		  map.put("5", "疑似作弊软件");
		  map.put("6", "直接访问");
		  map.put("7", "频繁切换客户端");
		  map.put("8", "只访问广告");
		for(int i=0; i<total_days.size() ;i++){
			String[] kk = new String[3];
			if(total_days.get(i).getClientId() ==null){
				kk[0] ="空";
			}else{
				kk[0] =total_days.get(i).getClientId();
			}
            kk[1] =total_days.get(i).getEpv()+"";
            String[] sourceStrArray = total_days.get(i).getExceType().split(",");
            String G="";
            for (int k = 0; k < sourceStrArray.length; k++) {
                System.out.println(sourceStrArray[k]);
                if(k==sourceStrArray.length-1){
                	 G+=map.get(sourceStrArray[k]);
                }else{
                	 G+=map.get(sourceStrArray[k])+",";
                }  
            }  
            kk[2] =G;            		
			mm[i] =kk;
		}			
		return mm;
	}
	
	/**
	 * 广告分析详情查看
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param siteid
	 * @return
	 */
	@RequestMapping("/user/ad_details_list.htm")
	public ModelAndView ad_details_list(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String siteId,String adId,String name,String begin,String end) {
		ModelAndView mv = new JModelAndView("/user/html/ad_Channel.html",0, request,response);
		mv.addObject("siteid", siteId);
	/*	mv.addObject("begin", begin);
		mv.addObject("end", end);
		   SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
	       try
	       {
	       	   Date date=sdf1.parse(begin);
	       	   Date date1=sdf1.parse(begin);
	           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	           String begins=sdf.format(date);
	           String ends=sdf.format(date1);
	           mv.addObject("begins", begins); 
	           mv.addObject("ends", ends); 
	       }
	       catch (ParseException e)
	       {
	           e.printStackTrace();
	       }*/
		mv.addObject("adId", adId);
		mv.addObject("name", name);
		return mv;
	}
	
	/**
     * 单个广告渠道分析列表
     *(zhou 11.1)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/ad_channel_list.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public void ad_channel_list(HttpServletRequest request, HttpSession session,String siteId,String adId,Integer dayway,Date begin,Date end,HttpServletResponse response){
             JSONObject resultJson = new JSONObject();
             
             Map paramS = new HashMap();
             paramS.put("adId",  Integer.parseInt(adId));	
             List<Ad> ad = this.iAdService.query("select obj from Ad obj where id=:adId", paramS, -1, -1);
             resultJson.put("adid_like", ad.get(0).getName());
             
             
             Map params = new HashMap();
     		 params.put("siteId", Integer.parseInt(siteId));
     		 params.put("adId",  Integer.parseInt(adId));	  	
     		List<Ada_ad_channel>  total_days = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where siteId=:siteId and adId=:adId", params, -1, -1);   
     		List<Ada_access_stat_day_channel> Bao = new ArrayList<Ada_access_stat_day_channel>();
	          for(Ada_ad_channel total_daysM:total_days){
	        	  Map paramK = new HashMap();
	      		 paramK.put("channelId", total_daysM.getId());
	      		 paramK.put("startime", begin);
	      		 paramK.put("endtime",end);
	      		List<Ada_access_stat_day_channel>  ada_access_stat_day_channel = this.iAda_access_stat_day_channelService.query("select obj from Ada_access_stat_day_channel obj where channelId=:channelId and obj.endTime between :startime and :endtime ", paramK, -1, -1);  
	      		for(Ada_access_stat_day_channel ada_access_stat_day_channelR:ada_access_stat_day_channel){
	      			Bao.add(ada_access_stat_day_channelR);
	      		}
	          }     		 
            	String[][] mm = makeTser_channel(Bao,end,begin);
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
	
	public String[][] makeTser_channel(List<Ada_access_stat_day_channel> total_days,Date end,Date begin) {
		String[][]  mm = new  String[total_days.size()][];
		for(int i=0; i<total_days.size() ;i++){
			Map params = new HashMap();
	        params.put("channelId", total_days.get(i).getChannelId());
	     	List<Ada_ad_channel>  Ad = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where id=:channelId", params, -1, -1);   
			String[] kk = new String[7];
			kk[0] = Ad.get(0).getName();
			kk[1] =total_days.get(i).getPv()+"";
			kk[2] =total_days.get(i).getUv()+"";
			kk[3] =total_days.get(i).getIp()+"";
			kk[4] =total_days.get(i).getEpv()+"";
			kk[5] ="<a href='/adaToshow/user/adchannel_euv_list.htm?siteId="+total_days.get(i).getSiteId()+"&name="+Ad.get(0).getName()+"&channelId="+total_days.get(i).getChannelId()+"&begin="+begin+"&end="+end+"'>"+total_days.get(i).getEuv()+"</a>";			
			kk[6] ="<a href='/adaToshow/user/adchannel_eip_list.htm?siteId="+total_days.get(i).getSiteId()+"&name="+Ad.get(0).getName()+"&channelId="+total_days.get(i).getChannelId()+"&begin="+begin+"&end="+end+"'>"+total_days.get(i).getEip()+"</a>";
			mm[i] =kk;
		}			
		return mm;
	}
	
	
	
	/**
     * 渠道分析列表
     *(zhou 11.2)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/channelList.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public void channelList(HttpServletRequest request, HttpSession session,Integer userId,Integer siteId,Integer dayway,Date begin,Date end,HttpServletResponse response){
             JSONObject resultJson = new JSONObject();
           	Integer site = (Integer) request.getSession().getAttribute("currentSiteId");
            if(site!=null){
           	 siteId= site;
            }
             Map params = new HashMap();
     		params.put("siteid", siteId);
     		params.put("startime", begin);
     		params.put("endtime",end);
     		List<Ada_access_stat_day_channel>  total_days = this.iAda_access_stat_day_channelService.query("select obj from Ada_access_stat_day_channel obj where  obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);   	
            	String[][] mm = makeTser_channelList(total_days,begin,end);
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
	
	public String[][] makeTser_channelList(List<Ada_access_stat_day_channel> total_days,Date begin,Date end) {
		String[][]  mm = new  String[total_days.size()][];
		for(int i=0; i<total_days.size() ;i++){
			Map params = new HashMap();
	        params.put("channelId", total_days.get(i).getChannelId());
	     	List<Ada_ad_channel>  Ad = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where id=:channelId", params, -1, -1);   
			String[] kk = new String[8];
			kk[0] = Ad.get(0).getName();
			kk[1] =total_days.get(i).getPv()+"";
			kk[2] =total_days.get(i).getUv()+"";
			kk[3] =total_days.get(i).getIp()+"";
			kk[4] =total_days.get(i).getEpv()+"";
			kk[5] ="<a href='/adaToshow/user/adchannel_euv_list.htm?siteId="+total_days.get(i).getSiteId()+"&name="+Ad.get(0).getName()+"&channelId="+total_days.get(i).getChannelId()+"&begin="+begin+"&end="+end+"'>"+total_days.get(i).getEuv()+"</a>";
			kk[6] ="<a href='/adaToshow/user/adchannel_eip_list.htm?siteId="+total_days.get(i).getSiteId()+"&name="+Ad.get(0).getName()+"&channelId="+total_days.get(i).getChannelId()+"&begin="+begin+"&end="+end+"'>"+total_days.get(i).getEip()+"</a>";
			kk[7] ="<a href='/adaToshow/user/adchannel_list.htm?siteId="+total_days.get(i).getSiteId()+"&name="+Ad.get(0).getName()+"&channelId="+total_days.get(i).getChannelId()+"'>查看详情</a>";
			mm[i] =kk;
		}			
		return mm;
	}
	
	/**
	 * 渠道分析详情eip
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param siteid
	 * @return
	 */
	@RequestMapping("/user/adchannel_eip_list.htm")
	public ModelAndView adchannel_eip_list(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String siteId,String channelId,String name,String begin,String end) {
		ModelAndView mv = new JModelAndView("/user/html/channel_EIP.html",0, request,response);
		mv.addObject("siteId", siteId);
		mv.addObject("begin", begin);
		mv.addObject("end", end);
		   SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
	       try
	       {
	       	   Date date=sdf1.parse(begin);
	       	   Date date1=sdf1.parse(end);
	           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	           String begins=sdf.format(date);
	           String ends=sdf.format(date1);
	           mv.addObject("begins", begins); 
	           mv.addObject("ends", ends); 
	       }
	       catch (ParseException e)
	       {
	           e.printStackTrace();
	       }
		mv.addObject("channelId", channelId);
		mv.addObject("name", name);
		return mv;
	}
	
	/**
     * 渠道eip
     *(zhou 11.3)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/channel_EIP_list.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public void channel_EIP_list(HttpServletRequest request, HttpSession session,String siteId,String channelId,Integer dayway,Date begin,Date end,HttpServletResponse response){
             JSONObject resultJson = new JSONObject();
             
             Map paramM = new HashMap();    		
     		 paramM.put("channelId",  Integer.parseInt(channelId));	
             List<Ada_ad_channel> ada_ad_channels = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where id=:channelId", paramM, -1, -1);
         	 resultJson.put("channelname", ada_ad_channels.get(0).getName());
     		 
             Map params = new HashMap();
     		 params.put("siteId", Integer.parseInt(siteId));
     		 params.put("channelId",  Integer.parseInt(channelId));	
     		 params.put("startime", begin);
     		 params.put("endtime",end);
     		List<Ada_access_day_ip_adchannel>  total_days = this.iAda_access_day_ip_adchannelService.query("select obj from Ada_access_day_ip_adchannel obj where siteId=:siteId and channelId=:channelId and obj.endTime between :startime and :endtime", params, -1, -1);   	   		 
            	String[][] mm = makeTser_channel_eip(total_days);
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
	
	public String[][] makeTser_channel_eip(List<Ada_access_day_ip_adchannel> total_days) {
		String[][]  mm = new  String[total_days.size()][];
		  Map map =new HashMap();
		  map.put("1", "天天访问");
		  map.put("2", "高频访问");
		  map.put("3", "频繁切换ip");
		  map.put("4", "停留时间超短");
		  map.put("5", "疑似作弊软件");
		  map.put("6", "直接访问");
		  map.put("7", "频繁切换客户端");
		  map.put("8", "只访问广告");
		for(int i=0; i<total_days.size() ;i++){
			String[] kk = new String[4];
			if(total_days.get(i).getRemoteIp() ==null){
				kk[0] ="空";
			}else{
				kk[0] =total_days.get(i).getRemoteIp();
			}
            kk[1] =total_days.get(i).getEpv()+"";
            String[] sourceStrArray = total_days.get(i).getExceType().split(",");
            String G="";
            for (int k = 0; k < sourceStrArray.length; k++) {
                System.out.println(sourceStrArray[k]);
                if(k==sourceStrArray.length-1){
                	 G+=map.get(sourceStrArray[k]);
                }else{
                	 G+=map.get(sourceStrArray[k])+",";
                }  
            }  
            kk[2] =G; 
            kk[3] ="<a href='/adaToshow/user/ad_eipMake.htm?siteId="+total_days.get(i).getSiteId()+"&channelId="+total_days.get(i).getChannelId()+"&remoteIp="+total_days.get(i).getRemoteIp()+"'>查看IP详情</a>";
			mm[i] =kk;
		}			
		return mm;
	}
	
	
	/**
	 * 渠道分析详情euv
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param siteid
	 * @return
	 */
	@RequestMapping("/user/adchannel_euv_list.htm")
	public ModelAndView adchannel_euv_list(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String siteId,String channelId,String name,String begin,String end) {
		ModelAndView mv = new JModelAndView("/user/html/channel_EUV.html",0, request,response);
		mv.addObject("siteId", siteId);
		mv.addObject("begin", begin);
		mv.addObject("end", end);
		   SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
	       try
	       {
	       	   Date date=sdf1.parse(begin);
	       	   Date date1=sdf1.parse(end);
	           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	           String begins=sdf.format(date);
	           String ends=sdf.format(date1);
	           mv.addObject("begins", begins); 
	           mv.addObject("ends", ends); 
	       }
	       catch (ParseException e)
	       {
	           e.printStackTrace();
	       }
		mv.addObject("channelId", channelId);
		mv.addObject("name", name);
		return mv;
	}
	
	/**
     * 渠道euv
     *(zhou 11.3)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/channel_EUV_list.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public void channel_EUV_list(HttpServletRequest request, HttpSession session,String siteId,String channelId,Integer dayway,Date begin,Date end,HttpServletResponse response){
             JSONObject resultJson = new JSONObject();
             
             Map paramM = new HashMap();    		
     		 paramM.put("channelId",  Integer.parseInt(channelId));	
             List<Ada_ad_channel> ada_ad_channels = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where id=:channelId", paramM, -1, -1);
         	 resultJson.put("channelname", ada_ad_channels.get(0).getName());

             
             Map params = new HashMap();
     		 params.put("siteId", Integer.parseInt(siteId));
     		 params.put("channelId",  Integer.parseInt(channelId));	 
     		 params.put("startime", begin);
     		 params.put("endtime",end);
     		List<Ada_access_day_client_adchannel>  total_days = this.iAda_access_day_client_adchannelService.query("select obj from Ada_access_day_client_adchannel obj where siteId=:siteId and channelId=:channelId and obj.endTime between :startime and :endtime", params, -1, -1);   	   		 
            	String[][] mm = makeTser_channel_euv(total_days);
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
	
	public String[][] makeTser_channel_euv(List<Ada_access_day_client_adchannel> total_days) {
		String[][]  mm = new  String[total_days.size()][];
		  Map map =new HashMap();
		  map.put("1", "天天访问");
		  map.put("2", "高频访问");
		  map.put("3", "频繁切换ip");
		  map.put("4", "停留时间超短");
		  map.put("5", "疑似作弊软件");
		  map.put("6", "直接访问");
		  map.put("7", "频繁切换客户端");
		  map.put("8", "只访问广告");
		for(int i=0; i<total_days.size() ;i++){
			String[] kk = new String[3];
			if(total_days.get(i).getClientId() ==null){
				kk[0] ="空";
			}else{
				kk[0] =total_days.get(i).getClientId();
			}
            kk[1] =total_days.get(i).getEpv()+"";
            String[] sourceStrArray = total_days.get(i).getExceType().split(",");
            String G="";
            for (int k = 0; k < sourceStrArray.length; k++) {
                System.out.println(sourceStrArray[k]);
                if(k==sourceStrArray.length-1){
                	 G+=map.get(sourceStrArray[k]);
                }else{
                	 G+=map.get(sourceStrArray[k])+",";
                }  
            }  
            kk[2] =G;            		
			mm[i] =kk;
		}			
		return mm;
	}
	/**
	 * 渠道分析详情查看
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param siteid
	 * @return
	 */
	@RequestMapping("/user/adchannel_list.htm")
	public ModelAndView adchannel_list(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String siteId,String channelId,String name,String begin,String end) {
		ModelAndView mv = new JModelAndView("/user/html/channel_Ad.html",0, request,response);
		mv.addObject("siteId", siteId);
		/*mv.addObject("begin", begin);
		mv.addObject("end", end);
		   SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
	       try
	       {
	       	   Date date=sdf1.parse(begin);
	       	   Date date1=sdf1.parse(begin);
	           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	           String begins=sdf.format(date);
	           String ends=sdf.format(date1);
	           mv.addObject("begins", begins); 
	           mv.addObject("ends", ends); 
	       }
	       catch (ParseException e)
	       {
	           e.printStackTrace();
	       }*/
		mv.addObject("channelId", channelId);
		mv.addObject("name", name);
		return mv;
	}
	
	/**
     * 单个渠道广告分析列表
     *(zhou 11.2)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/channel_ad_list.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public void channel_ad_list(HttpServletRequest request, HttpSession session,String siteId,String channelId,Integer dayway,Date begin,Date end,HttpServletResponse response){
             JSONObject resultJson = new JSONObject();
             
             Map paramM = new HashMap();    		
     		 paramM.put("channelId",  Integer.parseInt(channelId));	
             List<Ada_ad_channel> ada_ad_channels = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where id=:channelId", paramM, -1, -1);
         	 resultJson.put("channelname", ada_ad_channels.get(0).getName());

             
             Map params = new HashMap();
     		 params.put("siteId", Integer.parseInt(siteId));
     		 params.put("channelId",  Integer.parseInt(channelId));	
     		List<Ada_ad_channel>  total_days = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where siteId=:siteId and id=:channelId ", params, -1, -1);   
     		List<Ada_access_stat_day_ad> Bao = new ArrayList<Ada_access_stat_day_ad>();
	          for(Ada_ad_channel total_daysM:total_days){
	        	  Map paramK = new HashMap();
	      		 paramK.put("adid", total_daysM.getAdId());
	      		 paramK.put("startime", begin);
	      		 paramK.put("endtime",end);
	      		List<Ada_access_stat_day_ad>  ada_access_stat_day_ad = this.iAda_access_stat_day_adService.query("select obj from Ada_access_stat_day_ad obj where adId=:adid and obj.endTime between :startime and :endtime", paramK, -1, -1);  
	      		for(Ada_access_stat_day_ad ada_access_stat_day_channelR:ada_access_stat_day_ad){
	      		 Bao.add(ada_access_stat_day_channelR);
	      		}
	          }     		 
            	String[][] mm = makeTser_adchannel(Bao,begin,end);
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
	
	public String[][] makeTser_adchannel(List<Ada_access_stat_day_ad> total_days,Date begin,Date end) {
		String[][]  mm = new  String[total_days.size()][];
		for(int i=0; i<total_days.size() ;i++){
			Map params = new HashMap();
	        params.put("adid", total_days.get(i).getAdId());
	        List<Ad>  ad = this.iAdService.query("select obj from Ad obj where obj.id=:adid", params, -1, -1);      			
	        String[] kk = new String[7];
			kk[0] = ad.get(0).getName();
			kk[1] =total_days.get(i).getPv()+"";
			kk[2] =total_days.get(i).getUv()+"";
			kk[3] =total_days.get(i).getIp()+"";
			kk[4] =total_days.get(i).getEpv()+"";
			kk[5] ="<a href='/adaToshow/user/ad_euv_list.htm?siteId="+total_days.get(i).getSiteId()+"&adId="+total_days.get(i).getAdId()+"&name="+ad.get(0).getName()+"&begin="+begin+"&end="+end+"'>"+total_days.get(i).getEuv()+"</a>";
			kk[6] ="<a href='/adaToshow/user/ad_eip_list.htm?siteId="+total_days.get(i).getSiteId()+"&adId="+total_days.get(i).getAdId()+"&name="+ad.get(0).getName()+"&begin="+begin+"&end="+end+"'>"+total_days.get(i).getEip()+"</a>";
			mm[i] =kk;
		}			
		return mm;
	}
	
	
	
	/**
	 * ip详情
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param siteid
	 * @return
	 */
	@RequestMapping("/user/ad_eipMake.htm")
	public ModelAndView ad_eipMake(HttpServletRequest request,HttpServletResponse response,String currentPage,
			String siteId,String adId,String remoteIp,String channelId) {
		ModelAndView mv = new JModelAndView("/user/html/Eip_details.html",0, request,response);
		mv.addObject("siteid", siteId);
		mv.addObject("adId", adId);
		mv.addObject("remoteIp", remoteIp);
		mv.addObject("channelId", channelId);
		return mv;
	}
	
	
	
	/**
     * ip详情eip
     *(zhou 11.2)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/Eip_details_list.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public Object Eip_details_list(HttpServletRequest request, HttpSession session,String siteId,String channelId,String adId,String remoteIp){
	        JSONObject resultJson = new JSONObject();
	        List<Ada_access_except_detail> ada_access_except_detail = new ArrayList<Ada_access_except_detail>();
	       if(channelId.equals("")){
	    	     Map param = new HashMap();
	    	     param.put("adId",  Integer.parseInt(adId));
	    	     List<Ad> ad =this.iAdService.query("select obj from Ad obj where id=:adId", param, -1, -1);
	    	     Map params = new HashMap();
	     		 params.put("siteId", Integer.parseInt(siteId));
	     		 params.put("referer",  ad.get(0).getPrefix());	 
	     		 params.put("remoteIp",  remoteIp);	
	     		 ada_access_except_detail = this.iAda_access_except_detailService.query("select obj from Ada_access_except_detail obj where siteId=:siteId and referer=:referer and remoteIp=:remoteIp", params, -1, -1);
	      }else if(adId.equals("")){
	    	     Map param = new HashMap();
	    	     param.put("channelId",  Integer.parseInt(channelId));
	    	     List<Ada_ad_channel> ad =this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where id=:channelId", param, -1, -1);
	    	     Map params = new HashMap();
	     		 params.put("siteId", Integer.parseInt(siteId));
	     		 params.put("referer",  "%"+ad.get(0).getContent()+"%");	 
	     		 params.put("remoteIp",  remoteIp);	
	     		 ada_access_except_detail = this.iAda_access_except_detailService.query("select obj from Ada_access_except_detail obj where siteId=:siteId and realReferer like (:referer) and remoteIp=:remoteIp", params, -1, -1);  
	      }	            
            	String[][] mm = make_Eip_details(ada_access_except_detail);
            	resultJson.put("makes", mm);
            	resultJson.put("status", "success");
        return resultJson.toString();
    }
	
	public String[][] make_Eip_details(List<Ada_access_except_detail> total_days) {
		String[][]  mm = new  String[total_days.size()][];
		for(int i=0; i<total_days.size() ;i++){   			
	        String[] kk = new String[5];
			kk[0] = total_days.get(0).getClientId();
			kk[1] =total_days.get(i).getUserAgent();
			kk[2] =total_days.get(i).getReferer();
			kk[3] =total_days.get(i).getRealReferer();
			kk[4] =total_days.get(i).getAccessTime()+"";
			mm[i] =kk;
		}			
		return mm;
	}
	

	/**
	 * 广告 柱状图请求
	 * 
	 * */
	@RequestMapping("/user/ad_zZtu.htm")
	public void sourceData(HttpServletRequest request,HttpServletResponse response,
			Integer userId,Integer siteId,Integer dayway,Date begin,Date end){
		//dayway 的 值 是 0 1 3 则 查询  查询 小时相关的 表  并且  柱状图  是 按 4 小时 一个 时间段 进行分段
		//创建 参数的 map 集合  
	  	Integer site = (Integer) request.getSession().getAttribute("currentSiteId");
        if(site!=null){
       	 siteId= site;
        }
		Map params = new HashMap();
		params.put("siteid", siteId);
		params.put("startime", begin);
		params.put("endtime",end);
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");//日期格式 
		String[] category=null; //X轴 标题数组
		long[] pvArr=null; 	//	pv 数组
		long[] uvArr=null; 	//	uv 数组
		long[] ipArr=null;	//	ip 数组
		long[] epvArr=null; 	//	epv 数组
		long[] euvArr=null; 	//	euv 数组
		long[] eipArr=null; 	//	eip 数组
		String[][] source=null;	// 来源列表数据
		
		/*int pv = 0;
		int uv = 0;
		int ip = 0;
		int epv = 0;
		int euv = 0;
		int eip = 0;*/
		//返回的 数据结果
		Map result=new HashMap();		
		if (dayway==2) {
			category=new String[7];
			pvArr=new long[7];
			uvArr=new long[7];
			ipArr=new long[7];
			epvArr=new long[7];
			euvArr=new long[7];
			eipArr=new long[7];
			//List<Total_day> query = dayService.query("select obj from  Total_day obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);			
			List<Ada_access_stat_day_ad>  query = this.iAda_access_stat_day_adService.query("select obj from  Ada_access_stat_day_ad obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);
		//	source = sourceService.source("select obj from  Ada_access_stat_day_source obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);
			
			if (query!=null&&!query.isEmpty()) {
				Map<Long, List<Ada_access_stat_day_ad>> classE = totalDay(query,begin);
				for (int i = 0; i < 7; i++) {
					List<Ada_access_stat_day_ad> preData = classE.get((long)i);//获取 前（6-i）天的 数据
					category[i]=sdf.format(  new Date(end.getTime()-(7-i)*86400000) );
					Map dayNum = dayNum(preData);
					pvArr[i]=Long.valueOf( dayNum.get("pv").toString() );
					uvArr[i]=Long.valueOf( dayNum.get("uv").toString());
					ipArr[i]=Long.valueOf( dayNum.get("ip").toString());
					epvArr[i]=Long.valueOf( dayNum.get("epv").toString());
					euvArr[i]=Long.valueOf( dayNum.get("euv").toString());
					eipArr[i]=Long.valueOf( dayNum.get("eip").toString());	
				/*	pv+=Integer.parseInt(dayNum.get("pv").toString());
					uv+=Integer.parseInt(dayNum.get("uv").toString());
					ip+=Integer.parseInt(dayNum.get("ip").toString());
					epv+=Integer.parseInt(dayNum.get("epv").toString());*/
					
				}							
			};			
		} else {
			category=new String[6];
			pvArr=new long[6];
			uvArr=new long[6];
			ipArr=new long[6];
			epvArr=new long[6];
			euvArr=new long[6];
			eipArr=new long[6];
			List<Ada_access_stat_day_ad> query = iAda_access_stat_day_adService.query("select obj  from  Ada_access_stat_day_ad obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);			
			if (query!=null&&!query.isEmpty()) {	
				Map<Long, List<Ada_access_stat_day_ad>> classE =totalHour(query, begin);
				
				for (int i = 0; i < 6; i++) {
					List<Ada_access_stat_day_ad> preData = classE.get((long)i);
					category[i]=i*4+"至"+4*(i+1)+"时";
					Map dayNum = hourNum(preData);			
					pvArr[i]=Long.valueOf( dayNum.get("pv").toString() );
					uvArr[i]=Long.valueOf( dayNum.get("uv").toString());
					ipArr[i]=Long.valueOf( dayNum.get("ip").toString());
					epvArr[i]=Long.valueOf( dayNum.get("epv").toString());
					euvArr[i]=Long.valueOf( dayNum.get("euv").toString());
					eipArr[i]=Long.valueOf( dayNum.get("eip").toString());							
				}							
			};
			
		}
		result.put("category", category);
		result.put("pv", pvArr);
		result.put("uv", uvArr);
		result.put("ip", ipArr);
		result.put("epv", epvArr);
		result.put("euv", euvArr);
		result.put("eip", eipArr);	
		result.put("pvSum", getSum(pvArr));
		result.put("uvSum", getSum(uvArr));
		result.put("ipSum", getSum(ipArr));
		result.put("epvSum", getSum(epvArr));
		result.put("euvSum", getSum(euvArr));
		result.put("eipSum", getSum(eipArr));
		result.put("source", source);
		String json = Json.toJson(result, JsonFormat.compact());
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
	
	
	
	/**
	 * 渠道 柱状图请求
	 * 
	 * */
	@RequestMapping("/user/channel_zZtu.htm")
	public void channel_zZtu(HttpServletRequest request,HttpServletResponse response,
			Integer userId,Integer siteId,Integer dayway,Date begin,Date end){
		//dayway 的 值 是 0 1 3 则 查询  查询 小时相关的 表  并且  柱状图  是 按 4 小时 一个 时间段 进行分段
		//创建 参数的 map 集合  
	  	Integer site = (Integer) request.getSession().getAttribute("currentSiteId");
        if(site!=null){
       	 siteId= site;
        }		
		Map params = new HashMap();
		params.put("siteid", siteId);
		params.put("startime", begin);
		params.put("endtime",end);
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");//日期格式 
		String[] category=null; //X轴 标题数组
		long[] pvArr=null; 	//	pv 数组
		long[] uvArr=null; 	//	uv 数组
		long[] ipArr=null;	//	ip 数组
		long[] epvArr=null; 	//	epv 数组
		long[] euvArr=null; 	//	euv 数组
		long[] eipArr=null; 	//	eip 数组
		String[][] source=null;	// 来源列表数据
		//返回的 数据结果
		Map result=new HashMap();		
		if (dayway==2) {
			category=new String[7];
			pvArr=new long[7];
			uvArr=new long[7];
			ipArr=new long[7];
			epvArr=new long[7];
			euvArr=new long[7];
			eipArr=new long[7];
			//List<Total_day> query = dayService.query("select obj from  Total_day obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);			
			List<Ada_access_stat_day_channel>  query = this.iAda_access_stat_day_channelService.query("select obj from  Ada_access_stat_day_channel obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);
		//	source = sourceService.source("select obj from  Ada_access_stat_day_source obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);
			
			if (query!=null&&!query.isEmpty()) {
				Map<Long, List<Ada_access_stat_day_channel>> classE = totalDaychannel(query,begin);
				for (int i = 0; i < 7; i++) {
					List<Ada_access_stat_day_channel> preData = classE.get((long)i);//获取 前（6-i）天的 数据
					category[i]=sdf.format(  new Date(end.getTime()-(7-i)*86400000) );
					Map dayNum = dayNumchannel(preData);
					pvArr[i]=Long.valueOf( dayNum.get("pv").toString() );
					uvArr[i]=Long.valueOf( dayNum.get("uv").toString());
					ipArr[i]=Long.valueOf( dayNum.get("ip").toString());
					epvArr[i]=Long.valueOf( dayNum.get("epv").toString());
					euvArr[i]=Long.valueOf( dayNum.get("euv").toString());
					eipArr[i]=Long.valueOf( dayNum.get("eip").toString());							
				}							
			};			
		} else {
			category=new String[6];
			pvArr=new long[6];
			uvArr=new long[6];
			ipArr=new long[6];
			epvArr=new long[6];
			euvArr=new long[6];
			eipArr=new long[6];
			List<Ada_access_stat_day_channel> query = iAda_access_stat_day_channelService.query("select obj  from  Ada_access_stat_day_channel obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);			
			if (query!=null&&!query.isEmpty()) {	
				Map<Long, List<Ada_access_stat_day_channel>> classE =totalHourchannel(query, begin);
				for (int i = 0; i < 6; i++) {
					List<Ada_access_stat_day_channel> preData = classE.get((long)i);
					category[i]=i*4+"至"+4*(i+1)+"时";
					Map dayNum = hourNumchannel(preData);			
					pvArr[i]=Long.valueOf( dayNum.get("pv").toString() );
					uvArr[i]=Long.valueOf( dayNum.get("uv").toString());
					ipArr[i]=Long.valueOf( dayNum.get("ip").toString());
					epvArr[i]=Long.valueOf( dayNum.get("epv").toString());
					euvArr[i]=Long.valueOf( dayNum.get("euv").toString());
					eipArr[i]=Long.valueOf( dayNum.get("eip").toString());							
				}							
			};
			
		}
		result.put("category", category);
		result.put("pv", pvArr);
		result.put("uv", uvArr);
		result.put("ip", ipArr);
		result.put("epv", epvArr);
		result.put("euv", euvArr);
		result.put("eip", eipArr);	
		result.put("pvSum", getSum(pvArr));
		result.put("uvSum", getSum(uvArr));
		result.put("ipSum", getSum(ipArr));
		result.put("epvSum", getSum(epvArr));
		result.put("euvSum", getSum(euvArr));
		result.put("eipSum", getSum(eipArr));
		result.put("source", source);
		String json = Json.toJson(result, JsonFormat.compact());
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
	
	
	
	/**
	 * Ada_access_stat_day_ad连续七天   按最近的天数 分类 
	 * 
	 * */
	private Map<Long, List<Ada_access_stat_day_ad>> totalDay(List<Ada_access_stat_day_ad> query,Date begin){
		//获得结束 日期的 long 
		Long time = begin.getTime();
		// 连续七天 按每天 进行分类
		Map<Long,List<Ada_access_stat_day_ad>> classfy=new HashMap<Long, List<Ada_access_stat_day_ad>>();
		if (query!=null&&!query.isEmpty()) {
			for (Ada_access_stat_day_ad item : query) {
				List<Ada_access_stat_day_ad> templ=classfy.get((item.getEndTime().getTime()-time)/86400000);
				if (templ==null) {
					templ=new ArrayList<Ada_access_stat_day_ad>();
					templ.add(item);
					classfy.put((item.getEndTime().getTime()-time)/86400000,templ);
				} else {
					templ.add(item);	
				} 				
			}		
		}		
		return classfy;
	}
	private  Map dayNum(List<Ada_access_stat_day_ad> query) {
		Map result=new HashMap();
		Integer ip=0;//独立ip数
		Integer uv=0;//独立客户
		Integer pv=0;//总访问数量
		Integer eip=0;//异常ip数
		Integer euv=0;//异常客户数
		Integer epv=0;//异常访问数
		if (query!=null&&!query.isEmpty()) {
			for (Ada_access_stat_day_ad item : query) {
				pv+=item.getPv();
				uv+=item.getUv();
				ip+=item.getIp();
				epv+=item.getEpv();
				euv+=item.getEuv();
				eip+=item.getEip();			
			}
			
		}				
		//保存数据
		result.put("pv",pv);
		result.put("uv",uv);
		result.put("ip",ip);
		result.put("epv",epv);
		result.put("euv",euv);
		result.put("eip",eip);		
		return result;		
	}
	
	/**
	 * Total_hour 连续七天   按最近的天数 分类 
	 * 
	 * */
	private Map<Long, List<Ada_access_stat_day_ad>> totalHour(List<Ada_access_stat_day_ad> query,Date begin){
		//获得结束 日期的 long 
		Long time=begin.getTime();
		// 连续七天 按每天 进行分类
		Map<Long,List<Ada_access_stat_day_ad>> classfy=new HashMap<Long, List<Ada_access_stat_day_ad>>();
		if (query!=null&&!query.isEmpty()) {
			for (Ada_access_stat_day_ad item : query) {	
				List<Ada_access_stat_day_ad> templ=classfy.get(  ( item.getEndTime().getTime()-time)/14400000 );	
				if (templ==null) {
					templ=new ArrayList<Ada_access_stat_day_ad>();
					templ.add(item);
					classfy.put(  ( item.getEndTime().getTime()-time)/14400000 ,templ);
				} else {
					templ.add(item);	
				} 				
			}		
		}
		return classfy;
	}	
	private  Map hourNum(List<Ada_access_stat_day_ad> query) {
		Map result=new HashMap();
		Integer ip=0;//独立ip数
		Integer uv=0;//独立客户
		Integer pv=0;//总访问数量
		Integer eip=0;//异常ip数
		Integer euv=0;//异常客户数
		Integer epv=0;//异常访问数
		if (query!=null&&!query.isEmpty()) {
			for (Ada_access_stat_day_ad item : query) {
				pv+=item.getPv();
				uv+=item.getUv();
				ip+=item.getIp();
				epv+=item.getEpv();
				euv+=item.getEuv();
				eip+=item.getEip();		
			}
			
		}	
		//保存数据
		result.put("pv",pv);
		result.put("uv",uv);
		result.put("ip",ip);
		result.put("epv",epv);
		result.put("euv",euv);
		result.put("eip",eip);
		return result;		
	}
	
	
	
	/**
	 * Ada_access_stat_day_channel连续七天   按最近的天数 分类 
	 * 
	 * */
	private Map<Long, List<Ada_access_stat_day_channel>> totalDaychannel(List<Ada_access_stat_day_channel> query,Date begin){
		//获得结束 日期的 long 
		Long time = begin.getTime();
		// 连续七天 按每天 进行分类
		Map<Long,List<Ada_access_stat_day_channel>> classfy=new HashMap<Long, List<Ada_access_stat_day_channel>>();
		if (query!=null&&!query.isEmpty()) {
			for (Ada_access_stat_day_channel item : query) {
				List<Ada_access_stat_day_channel> templ=classfy.get((item.getEndTime().getTime()-time)/86400000);
				if (templ==null) {
					templ=new ArrayList<Ada_access_stat_day_channel>();
					templ.add(item);
					classfy.put((item.getEndTime().getTime()-time)/86400000,templ);
				} else {
					templ.add(item);	
				} 				
			}		
		}		
		return classfy;
	}
	private  Map dayNumchannel(List<Ada_access_stat_day_channel> query) {
		Map result=new HashMap();
		Integer ip=0;//独立ip数
		Integer uv=0;//独立客户
		Integer pv=0;//总访问数量
		Integer eip=0;//异常ip数
		Integer euv=0;//异常客户数
		Integer epv=0;//异常访问数
		if (query!=null&&!query.isEmpty()) {
			for (Ada_access_stat_day_channel item : query) {
				pv+=item.getPv();
				uv+=item.getUv();
				ip+=item.getIp();
				epv+=item.getEpv();
				euv+=item.getEuv();
				eip+=item.getEip();			
			}
			
		}				
		//保存数据
		result.put("pv",pv);
		result.put("uv",uv);
		result.put("ip",ip);
		result.put("epv",epv);
		result.put("euv",euv);
		result.put("eip",eip);		
		return result;		
	}
	
	/**
	 * Ada_access_stat_day_channel 连续七天   按最近的天数 分类 
	 * 
	 * */
	private Map<Long, List<Ada_access_stat_day_channel>> totalHourchannel(List<Ada_access_stat_day_channel> query,Date begin){
		//获得结束 日期的 long 
		Long time=begin.getTime();
		// 连续七天 按每天 进行分类
		Map<Long,List<Ada_access_stat_day_channel>> classfy=new HashMap<Long, List<Ada_access_stat_day_channel>>();
		if (query!=null&&!query.isEmpty()) {
			for (Ada_access_stat_day_channel item : query) {	
				List<Ada_access_stat_day_channel> templ=classfy.get(  ( item.getEndTime().getTime()-time)/14400000 );	
				if (templ==null) {
					templ=new ArrayList<Ada_access_stat_day_channel>();
					templ.add(item);
					classfy.put(  ( item.getEndTime().getTime()-time)/14400000 ,templ);
				} else {
					templ.add(item);	
				} 				
			}		
		}
		return classfy;
	}	
	private  Map hourNumchannel(List<Ada_access_stat_day_channel> query) {
		Map result=new HashMap();
		Integer ip=0;//独立ip数
		Integer uv=0;//独立客户
		Integer pv=0;//总访问数量
		Integer eip=0;//异常ip数
		Integer euv=0;//异常客户数
		Integer epv=0;//异常访问数
		if (query!=null&&!query.isEmpty()) {
			for (Ada_access_stat_day_channel item : query) {
				pv+=item.getPv();
				uv+=item.getUv();
				ip+=item.getIp();
				epv+=item.getEpv();
				euv+=item.getEuv();
				eip+=item.getEip();		
			}
			
		}	
		//保存数据
		result.put("pv",pv);
		result.put("uv",uv);
		result.put("ip",ip);
		result.put("epv",epv);
		result.put("euv",euv);
		result.put("eip",eip);
		return result;		
	}
	
	
	/**
	 * 计算 总时间 段的 的 num 
	 * 
	 * */
	private long getSum(long[] obj){
		long sum=0L;
		for (Long i : obj) {
			sum=sum+i;
		}		
		return sum;
	}
	
	
}