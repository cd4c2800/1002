package com.web.manage.user.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.web.core.domain.virtual.SysMap;
import com.web.core.mv.JModelAndView;
import com.web.core.query.support.IPageList;
import com.web.core.tools.CommUtil;
import com.web.foundation.domain.Ad;
import com.web.foundation.domain.Ada_access_stat_day_ad;
import com.web.foundation.domain.Ada_access_stat_day_channel;
import com.web.foundation.domain.Ada_ad_channel;
import com.web.foundation.domain.Total_day;
import com.web.foundation.domain.query.AdQueryObject;
import com.web.foundation.service.IAdService;
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
	
	/**
     * 广告分析列表
     *(zhou 11.1)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/ad_analysis_list.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public Object registerJson(HttpServletRequest request, HttpSession session){
             JSONObject resultJson = new JSONObject();
             Map params = new HashMap();
     		params.put("M", 1);
     		List<Ada_access_stat_day_ad>  total_days = this.iAda_access_stat_day_adService.query("select obj from Ada_access_stat_day_ad obj where 1=:M", params, -1, -1);
            	  for(Ada_access_stat_day_ad total_days2:total_days){
            		  Map paramsF = new HashMap();
            		  paramsF.put("id", total_days2.getAdId());
               		List<Ad>  ad = this.iAdService.query("select obj from Ad obj where obj.id=:id", paramsF, -1, -1);          
               		total_days2.setNurename(ad.get(0).getName());
            	  }
            	String[][] mm = makeTser(total_days);
            	resultJson.put("makes", mm);
            	resultJson.put("status", "success");
        return resultJson.toString();
    }
	
	public String[][] makeTser(List<Ada_access_stat_day_ad> total_days) {
		String[][]  mm = new  String[total_days.size()][];
		for(int i=0; i<total_days.size() ;i++){
			String[] kk = new String[8];
			kk[0] = total_days.get(i).getNurename();
			kk[1] =total_days.get(i).getPv()+"";
			kk[2] =total_days.get(i).getUv()+"";
			kk[3] =total_days.get(i).getIp()+"";
			kk[4] =total_days.get(i).getEpv()+"";
			kk[5] ="<a href='/adaToshow/user/ad_euv_list.htm'>"+total_days.get(i).getEuv()+"</a>";
			kk[6] ="<a href='/adaToshow/user/ad_eip_list.htm'>"+total_days.get(i).getEip()+"</a>";
			kk[7] ="<a href='/adaToshow/user/ad_details_list.htm?siteId="+total_days.get(i).getSiteId()+"&adId="+total_days.get(i).getAdId()+"&name="+total_days.get(i).getNurename()+"'>查看详情</a>";
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
	@RequestMapping("/user/ad_eip_list.htm")
	public ModelAndView ad_eip_list(HttpServletRequest request,HttpServletResponse response,String currentPage
			) {
		ModelAndView mv = new JModelAndView("/user/html/Ad_EIP.html",0, request,response);
		//mv.addObject("siteId", siteId);
		//mv.addObject("channelId", channelId);
		//mv.addObject("name", name);
		return mv;
	}
	
	/**
	 * 渠道分析详情euv
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param siteid
	 * @return
	 */
	@RequestMapping("/user/ad_euv_list.htm")
	public ModelAndView ad_euv_list(HttpServletRequest request,HttpServletResponse response,String currentPage
			) {
		ModelAndView mv = new JModelAndView("/user/html/Ad_EUV.html",0, request,response);
		//mv.addObject("siteId", siteId);
		//mv.addObject("channelId", channelId);
		//mv.addObject("name", name);
		return mv;
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
			String siteId,String adId,String name) {
		ModelAndView mv = new JModelAndView("/user/html/ad_Channel.html",0, request,response);
		mv.addObject("siteid", siteId);
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
    public Object ad_channel_list(HttpServletRequest request, HttpSession session,String siteId,String adId){
             JSONObject resultJson = new JSONObject();
             Map params = new HashMap();
     		 params.put("siteId", Integer.parseInt(siteId));
     		 params.put("adId",  Integer.parseInt(adId));	 
     		List<Ada_ad_channel>  total_days = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where siteId=:siteId and adId=:adId", params, -1, -1);   
     		List<Ada_access_stat_day_channel> Bao = new ArrayList<Ada_access_stat_day_channel>();
	          for(Ada_ad_channel total_daysM:total_days){
	        	  Map paramK = new HashMap();
	      		 paramK.put("channelId", total_daysM.getId());
	      		List<Ada_access_stat_day_channel>  ada_access_stat_day_channel = this.iAda_access_stat_day_channelService.query("select obj from Ada_access_stat_day_channel obj where channelId=:channelId", paramK, -1, -1);  
	      		for(Ada_access_stat_day_channel ada_access_stat_day_channelR:ada_access_stat_day_channel){
	      			Bao.add(ada_access_stat_day_channelR);
	      		}
	          }     		 
            	String[][] mm = makeTser_channel(Bao);
            	resultJson.put("makes", mm);
            	resultJson.put("status", "success");
        return resultJson.toString();
    }
	
	public String[][] makeTser_channel(List<Ada_access_stat_day_channel> total_days) {
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
			kk[5] =total_days.get(i).getEuv()+"";
			kk[6] =total_days.get(i).getEip()+"";
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
    public Object channelList(HttpServletRequest request, HttpSession session){
             JSONObject resultJson = new JSONObject();
             Map params = new HashMap();
     		params.put("M", 1);
     		List<Ada_access_stat_day_channel>  total_days = this.iAda_access_stat_day_channelService.query("select obj from Ada_access_stat_day_channel obj where 1=:M", params, -1, -1);   	
            	String[][] mm = makeTser_channelList(total_days);
            	resultJson.put("makes", mm);
            	resultJson.put("status", "success");
        return resultJson.toString();
    }
	
	public String[][] makeTser_channelList(List<Ada_access_stat_day_channel> total_days) {
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
			kk[5] ="<a href='/adaToshow/user/adchannel_euv_list.htm'>"+total_days.get(i).getEuv()+"</a>";
			kk[6] ="<a href='/adaToshow/user/adchannel_eip_list.htm'>"+total_days.get(i).getEip()+"</a>";
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
	public ModelAndView adchannel_eip_list(HttpServletRequest request,HttpServletResponse response,String currentPage
			) {
		ModelAndView mv = new JModelAndView("/user/html/channel_EIP.html",0, request,response);
		//mv.addObject("siteId", siteId);
		//mv.addObject("channelId", channelId);
		//mv.addObject("name", name);
		return mv;
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
	public ModelAndView adchannel_euv_list(HttpServletRequest request,HttpServletResponse response,String currentPage
			) {
		ModelAndView mv = new JModelAndView("/user/html/channel_EUV.html",0, request,response);
		//mv.addObject("siteId", siteId);
		//mv.addObject("channelId", channelId);
		//mv.addObject("name", name);
		return mv;
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
			String siteId,String channelId,String name) {
		ModelAndView mv = new JModelAndView("/user/html/channel_Ad.html",0, request,response);
		mv.addObject("siteId", siteId);
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
    public Object channel_ad_list(HttpServletRequest request, HttpSession session,String siteId,String channelId){
             JSONObject resultJson = new JSONObject();
             Map params = new HashMap();
     		 params.put("siteId", Integer.parseInt(siteId));
     		 params.put("channelId",  Integer.parseInt(channelId));	 
     		List<Ada_ad_channel>  total_days = this.iAda_ad_channelService.query("select obj from Ada_ad_channel obj where siteId=:siteId and id=:channelId", params, -1, -1);   
     		List<Ada_access_stat_day_ad> Bao = new ArrayList<Ada_access_stat_day_ad>();
	          for(Ada_ad_channel total_daysM:total_days){
	        	  Map paramK = new HashMap();
	      		 paramK.put("adid", total_daysM.getAdId());
	      		List<Ada_access_stat_day_ad>  ada_access_stat_day_ad = this.iAda_access_stat_day_adService.query("select obj from Ada_access_stat_day_ad obj where adId=:adid", paramK, -1, -1);  
	      		for(Ada_access_stat_day_ad ada_access_stat_day_channelR:ada_access_stat_day_ad){
	      		 Bao.add(ada_access_stat_day_channelR);
	      		}
	          }     		 
            	String[][] mm = makeTser_adchannel(Bao);
            	resultJson.put("makes", mm);
            	resultJson.put("status", "success");
        return resultJson.toString();
    }
	
	public String[][] makeTser_adchannel(List<Ada_access_stat_day_ad> total_days) {
		String[][]  mm = new  String[total_days.size()][];
		for(int i=0; i<total_days.size() ;i++){
			Map params = new HashMap();
	        params.put("adid", total_days.get(i).getAdId());
	        List<Ad>  ad = this.iAdService.query("select obj from Ad obj where obj.id=:adid", params, -1, -1);      			String[] kk = new String[7];
			kk[0] = ad.get(0).getName();
			kk[1] =total_days.get(i).getPv()+"";
			kk[2] =total_days.get(i).getUv()+"";
			kk[3] =total_days.get(i).getIp()+"";
			kk[4] =total_days.get(i).getEpv()+"";
			kk[5] =total_days.get(i).getEuv()+"";
			kk[6] =total_days.get(i).getEip()+"";
			mm[i] =kk;
		}			
		return mm;
	}
	
	
}