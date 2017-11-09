package com.web.manage.user.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.utils.ExplorerUtil;
import com.web.core.domain.virtual.SysMap;
import com.web.core.mv.JModelAndView;
import com.web.core.query.support.IPageList;
import com.web.core.tools.CommUtil;
import com.web.foundation.domain.Ada_ad_channel;
import com.web.foundation.domain.Ada_collect_access_log;
import com.web.foundation.domain.NumVisit;
import com.web.foundation.domain.OnlineData;
import com.web.foundation.domain.Site;
import com.web.foundation.domain.Sys_region;
import com.web.foundation.domain.User;
import com.web.foundation.domain.query.SiteQueryObject;
import com.web.foundation.service.IAda_ad_channelService;
import com.web.foundation.service.IAda_collect_access_logService;
import com.web.foundation.service.ISys_regionService;
/**
 * 
 * 当前在线 数据处理
 * 
 * */
@Controller
@SuppressWarnings("all")
public class OnlineAciton {
	@Autowired
	private IAda_collect_access_logService acessLog;
	@Autowired
	private ISys_regionService regionService;
	@Autowired
	private IAda_ad_channelService channelService;
	/**
	 * 当前在线 页面请求数据 
	 * */
	@RequestMapping("/data/online.htm")
	public void list(HttpServletRequest request,HttpServletResponse response,
			String currentPage,Integer userId,Integer siteId,Date timePre,Date timeCurrent){
		//当前用户 、当前站点  当前时间段的 访问记录
		Map params = new HashMap();
		params.put("siteid", siteId);
		params.put("timePre", timePre);
		params.put("timeCurrent", timeCurrent);	
		Map result=new HashMap();
		long start = timePre.getTime();
		long end = timeCurrent.getTime();	
		Date page=new Date(timePre.getTime());
		// <th>访问时间</th><th>IP</th><th>地区</th><th>渠道</th><th>客户端</th><th>受访URL</th>
		List<Ada_collect_access_log> access =  acessLog.query("select obj from Ada_collect_access_log obj  where obj.siteId=:siteid and obj.accessTime between :timePre and :timeCurrent", params, -1, -1);
		
		Long pv=acessLog.getSum("select count(obj.siteId) from Ada_collect_access_log obj  where obj.siteId=:siteid and obj.accessTime between :timePre and :timeCurrent", params);
		// obj.remoteIp 为空的时候
		Long ip=acessLog.getSum("select count( DISTINCT obj.remoteIp ) from Ada_collect_access_log obj  where obj.siteId=:siteid and obj.accessTime between :timePre and :timeCurrent", params);
		// obj.userAgent 为空需要解决
		Long uv=acessLog.getSum("select count( DISTINCT obj.userAgent) from Ada_collect_access_log obj  where obj.siteId=:siteid and obj.accessTime between :timePre and :timeCurrent", params);
		//计算  当前在线的 pv
		result.put("pv",pv);
		//计算  当前在线的 ip
		result.put("ip",ip);
		//计算  当前在线的 uv
		result.put("uv", uv);  //SUBDATE(now(),interval 120 second);
		request.getSession().setAttribute("visit", new NumVisit(pv, ip, uv, null, null, null));
		params.remove("timeCurrent");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<OnlineData> data=new ArrayList<OnlineData>();		
		for (int i = 0; i <=15; i++) {
			page=new Date(page.getTime()+2*60*1000);
			params.put("page", page);
			Long spv=acessLog.getSum("select count(obj.siteId) from Ada_collect_access_log obj  where obj.siteId=:siteid and obj.accessTime between  :timePre and :page  ", params);
			Long sip=acessLog.getSum("select count( DISTINCT obj.remoteIp) from Ada_collect_access_log obj  where obj.siteId=:siteid and obj.accessTime between :timePre and :page", params);
			Long suv=acessLog.getSum("select count( DISTINCT obj.userAgent) from Ada_collect_access_log obj  where obj.siteId=:siteid and obj.accessTime between  :timePre and :page ", params);
			OnlineData onlineData = new OnlineData(sdf.format(timePre), spv, suv, sip);
			data.add(onlineData );	
			timePre=new Date(page.getTime());			
			params.put("timePre", timePre);			
		}		
		result.put("page",data);	
		String[][] onlineDetail = onlineDetail(access);
		result.put("onlineDetail",onlineDetail);	
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

	private  String[][] onlineDetail(List<Ada_collect_access_log> access){
		String [][] online=new String[access.size()][];
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map params = new HashMap();
		 if (access!=null&&!access.isEmpty()) {
			 for (int i = 0; i < access.size(); i++) {
				 String[] item=new String [6];
				//计算访问时间
				 item[0]=sdf.format(access.get(i).getAccessTime());
				 item[1]=access.get(i).getRemoteIp();
				 //设置参数 
				 //select obj from Ada_collect_access_log obj  where obj.siteId=:siteid and obj.accessTime between :timePre and :timeCurrent"
				 params.put("countryId", access.get(i).getCountryId());
				 String  contryName=regionService.getName("select obj.countryName from Sys_region obj  where obj.countryId=:countryId",params);
				 params.remove("countryId");
				 params.put("provinceId", access.get(i).getProvinceId());
				 String  provinceName=regionService.getName("select obj.provinceName from Sys_region obj where obj.provinceId=:provinceId",params);
				 //获得国家 和省份
				 item[2]=contryName+","+provinceName;
				 params.remove("provinceId");
				 //计算广告 id
				 params.put("siteId", access.get(i).getSiteId());
				 params.put("referer", access.get(i).getReferer());
				 Integer adId = Integer.valueOf( acessLog.getSum("select obj.id from Ad obj  where obj.siteId=:siteId and obj.prefix =:referer ", params).toString() );
				 params.remove("referer");
				 params.put("adId", adId);
				 //计算渠道 
				 String realReferer = access.get(i).getRealReferer();
				 List<Ada_ad_channel> chanle = channelService.query("select obj from Ada_ad_channel obj  where obj.siteId=:siteId and obj.adId=:adId", params, -1, -1);	
				 if (realReferer==null||"".equals(realReferer)) item[3]="无访问";
				 	else if (chanle!=null&&!chanle.isEmpty()) {
				 		for (Ada_ad_channel ad : chanle) {
				 			if (realReferer.contains(ad.getContent())) {
				 				item[3]=ad.getName();
				 				break;
				 			}else item[3]="无";
				 		}	 
				 	}else item[3]="无";
				 //计算客户端
				 item[4]=ExplorerUtil.checkBrowse(access.get(i).getUserAgent());
				 item[5]=access.get(i).getReferer();
				 params.remove("adId");
				 params.remove("siteId");
				 online[i]=item;
			}		 
		}		
		return online;
	}	
	
	
	
	

	
	
}