package com.web.manage.user.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
import com.web.core.beans.BeanUtils;
import com.web.core.beans.BeanWrapper;
import com.web.core.mv.JModelAndView;
import com.web.core.query.support.IPageList;
import com.web.core.tools.CommUtil;
import com.web.core.tools.WebForm;
import com.web.foundation.service.ISysConfigService;
import com.web.foundation.service.IAda_collect_access_logService;
import com.web.foundation.service.ISys_regionService;
import com.web.foundation.domain.Ada_access_day_ip_adchannel;
import com.web.foundation.domain.Ada_collect_access_log;
import com.web.foundation.domain.Sys_region;
import com.web.foundation.domain.query.Ada_collect_access_logQueryObject;

@Controller
public class Ada_collect_access_logManageAction {
	@Autowired
	private ISys_regionService iSys_regionService;
	@Autowired
	private IAda_collect_access_logService ada_collect_access_logService;	
	
	
	/**
     * 采集访问日志
     *(zhou 11.8)
     * @param request
     * @return
     */
   @RequestMapping(value = "/user/Ada_collect_access_logManage_list.htm" ,method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public void Ada_collect_access_logManage_list(HttpServletRequest request, HttpSession session,Integer userId,Date begin,Date end,String ip,HttpServletResponse response){
	   Integer siteId = (Integer)request.getSession().getAttribute("currentSiteId");
	   		JSONObject resultJson = new JSONObject();
             Map params = new HashMap();
     		 params.put("siteId", siteId); 
     		 params.put("startime", begin);
    		 params.put("endtime",end);
     		 List<Ada_collect_access_log> total_days = new ArrayList<Ada_collect_access_log>();
     		 if(ip.equals("")){
     		   total_days = this.ada_collect_access_logService.query("select obj from Ada_collect_access_log obj where siteId=:siteId and obj.accessTime between :startime and :endtime", params, -1, -1);   	   		 	 
     		 }else{
     		 params.put("remoteIp", ip);	 
     	      total_days = this.ada_collect_access_logService.query("select obj from Ada_collect_access_log obj where siteId=:siteId and remoteIp=:remoteIp and  obj.accessTime between :startime and :endtime", params, -1, -1);   	   		 	 	 
     		 }
     		            	String[][] mm = makeTser_Ada_collect_access_log(total_days);
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
	
	
       public String[][] makeTser_Ada_collect_access_log(List<Ada_collect_access_log> total_days) {
		String[][]  mm = new  String[total_days.size()][];		 
		for(int i=0; i<total_days.size() ;i++){
			String[] kk = new String[6];
			kk[0] = total_days.get(i).getAccessTime()+"";
			kk[1] = total_days.get(i).getRemoteIp();
			 Map params = new HashMap();
	     	 params.put("countryId", total_days.get(i).getCountryId()); //国家
	     	 params.put("provinceId", total_days.get(i).getProvinceId());  //省
	     	 params.put("cityId", total_days.get(i).getCityId());  //市	    	
			 List<Sys_region>  sys_regions = this.iSys_regionService.query("select obj from Sys_region obj where countryId=:countryId and provinceId=:provinceId and cityId=:cityId", params, -1, -1);
			 if(sys_regions.size()>0){
			 kk[2] = sys_regions.get(0).getCountryName()+","+sys_regions.get(0).getProvinceName();
			 }else{
			 kk[2] = "无";	 
			 }
			 kk[3] = total_days.get(i).getDomain();						 						 
			 kk[4] = total_days.get(i).getClientId();
			 kk[5] = total_days.get(i).getReferer();
			 mm[i] =kk;
		}			
		return mm;
	}
	
	
}