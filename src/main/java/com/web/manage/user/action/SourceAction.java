package com.web.manage.user.action;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.core.mv.JModelAndView;
import com.web.foundation.domain.Ada_access_day_clientsourc;
import com.web.foundation.domain.Ada_access_day_ipsourc;
import com.web.foundation.domain.Ada_access_except_detail;
import com.web.foundation.domain.Ada_access_stat_day_source;
import com.web.foundation.domain.Ada_ad_channel;
import com.web.foundation.domain.Ada_collect_access_log;
import com.web.foundation.domain.Site;
import com.web.foundation.domain.Total_day;
import com.web.foundation.domain.Total_hour;
import com.web.foundation.service.IAda_access_day_clientsourcService;
import com.web.foundation.service.IAda_access_day_ipsourcService;
import com.web.foundation.service.IAda_access_except_detailService;
import com.web.foundation.service.IAda_access_stat_day_sourceService;
import com.web.foundation.service.IAda_ad_channelService;
import com.web.foundation.service.IAda_collect_access_logService;
import com.web.foundation.service.IAda_referersService;
import com.web.foundation.service.ISys_regionService;
import com.web.foundation.service.ITotal_dayService;
import com.web.foundation.service.ITotal_hourService;
import com.web.utils.explorer.ExplorerUtil;
import com.web.utils.response.ResponseData;
import com.web.utils.variable.VariableClass;

/**
 * 来源 分析 数据处理
 * 
 * */
@Controller
@SuppressWarnings("all")
public class SourceAction {
	@Autowired
	private ITotal_hourService hourService;
	@Autowired
	private ITotal_dayService  dayService;
	@Autowired
	private IAda_access_stat_day_sourceService  sourceService;
	@Autowired
	private IAda_access_day_clientsourcService clientsourcService;
	@Autowired
	private IAda_referersService  referersService;
	@Autowired
	private IAda_access_except_detailService  detailService;
	@Autowired
	private ISys_regionService regionService;
	@Autowired
	private IAda_ad_channelService channelService;
	@Autowired
	private IAda_collect_access_logService acessLog;
	@Autowired
	private IAda_access_day_ipsourcService  ipsourcService;
	
	/**
	 * 数据 请求
	 * 
	 * */
	@RequestMapping("/data/source.htm")
	public void sourceData(HttpServletRequest request,HttpServletResponse response,
			Integer userId,Integer dayway,Date begin,Date end){
		//dayway 的 值 是 0 1 3 则 查询  查询 小时相关的 表  并且  柱状图  是 按 4 小时 一个 时间段 进行分段
		//创建 参数的 map 集合  
		Integer siteId = (Integer) request.getSession().getAttribute("currentSiteId");
		List<Site> siteList = (List<Site>) request.getSession().getAttribute("siteList");
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
		Map<String, Map<Integer, String>> map= referersService.map("select obj from Ada_referers obj ", null, -1, -1);//来源 ID → 来源 域名   来源 ID → 来源名称	
		Map<Integer, String> idName = map.get("idName");	//来源 ID → 来源名称
		Map<Integer, String> idDomain = map.get("idDomain");	//来源 ID → 来源名称
		request.getSession().setAttribute("idName", idName); // 保存 后续使用
		request.getSession().setAttribute("idDomain", idDomain); // 保存 后续使用
		Map result=new HashMap();	//返回的 数据结果
		if (dayway==2) {
			category=new String[7];
			pvArr=new long[7];
			uvArr=new long[7];
			ipArr=new long[7];
			epvArr=new long[7];
			euvArr=new long[7];
			eipArr=new long[7];
			List<Total_day> query = dayService.query("select obj from  Total_day obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);						
			source = sourceService.source("select obj from  Ada_access_stat_day_source obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1,request.getContextPath(),idName);			
			if (query!=null&&!query.isEmpty()) {
				Map<Long, List<Total_day>> classE = totalDay(query,begin);
				for (int i = 0; i < 7; i++) {
					List<Total_day> preData = classE.get((long)i);//获取 前（6-i）天的 数据
					category[i]=sdf.format(  new Date(end.getTime()-(7-i)*86400000) );
					Map dayNum = dayNum(preData);
					setData(pvArr, uvArr, ipArr, epvArr, euvArr, eipArr, i,
							dayNum);							
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
			List<Total_hour> query = hourService.query("select obj  from  Total_hour obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);			
			source = sourceService.source("select obj from  Ada_access_stat_day_source obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1,request.getContextPath(),idName);			
			if (query!=null&&!query.isEmpty()) {	
				Map<Long, List<Total_hour>> classE =totalHour(query, begin);
				for (int i = 0; i < 6; i++) {
					List<Total_hour> preData = classE.get((long)i);
					category[i]=i*4+"至"+4*(i+1)+"时";
					Map dayNum = hourNum(preData);			
					setData(pvArr, uvArr, ipArr, epvArr, euvArr, eipArr, i,
							dayNum);							
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
		result.put("siteId", siteId);
		result.put("siteList", siteList);
		ResponseData.responseData(response, result);//向页面 相应 数据				
	}
	/**
	 * 保存 数据
	 * 
	 * */
	private void setData(long[] pvArr, long[] uvArr, long[] ipArr,
			long[] epvArr, long[] euvArr, long[] eipArr, int i, Map dayNum) {
		pvArr[i]=Long.valueOf( dayNum.get("pv").toString() );
		uvArr[i]=Long.valueOf( dayNum.get("uv").toString());
		ipArr[i]=Long.valueOf( dayNum.get("ip").toString());
		epvArr[i]=Long.valueOf( dayNum.get("epv").toString());
		euvArr[i]=Long.valueOf( dayNum.get("euv").toString());
		eipArr[i]=Long.valueOf( dayNum.get("eip").toString());
	} 

	/**
	 * EUV EIP 请求信息
	 * 
	 * 
	 * */
	@RequestMapping("/user/data/source/euveip.htm")
	public ModelAndView euv(HttpServletRequest request,HttpServletResponse response,Integer referersId
			,long starttime,long endtime,Integer way){		
		ModelAndView mv =null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式 
		if (way==1) mv= new JModelAndView("/user/html/source_EUV.html", 0, request, response);
		else mv=new JModelAndView("/user/html/source_EIP.html", 0, request, response);					
		Integer siteId = (Integer) request.getSession().getAttribute("currentSiteId");
		Map params = new HashMap();
		params.put("referersId", referersId);
		params.put("siteId", siteId);
		params.put("starttime",starttime );
		params.put("endtime",endtime);	
		params.put("way",way);	
		mv.addObject("params", params);
		mv.addObject("time", sdf.format( new Date(starttime))+" 至 "+sdf.format( new Date(endtime)));
		return mv;
	}
	/**
	 * EUV EIP 数据信息
	 * 
	 * 
	 * */
	@RequestMapping("/user/data/source/data.htm")
	public void euvData(HttpServletRequest request,HttpServletResponse response,Integer referersId
			,long starttime,long endtime,Integer way){
		Integer siteId = (Integer) request.getSession().getAttribute("currentSiteId");
		Map params=new HashMap();//获取 数据请求的 查询 参数
		
		params.put("referersId", referersId);
		params.put("siteId", siteId);
		params.put("starttime",new Date(starttime) );
		params.put("endtime",new Date( endtime));			
		Map<Integer, String> idName = (Map<Integer, String>) request.getSession().getAttribute("idName") ;	//来源 ID → 来源名称
		Map<String,String> excetype=VariableClass.getExcetype(); //获得  异常类型 的 map 集合
		Map result=new HashMap();	
		String[][] data=null;
		List query=null;
		if (way==1) {
			query=(List<Ada_access_day_clientsourc> ) clientsourcService.query("select obj from  Ada_access_day_clientsourc obj where obj.siteId=:siteId and obj.referersId=:referersId and obj.endTime between :starttime and :endtime", params, -1, -1);			
			if (query!=null&&!query.isEmpty()) {
				data=new String[query.size()][4];	
				for (int i = 0; i < query.size(); i++) {
					String[] arr=new String[4];
					arr[0]=((Ada_access_day_clientsourc) query.get(i)).getClientId();
					arr[1]=((Ada_access_day_clientsourc) query.get(i)).getEpv().toString();
					arr[2]=excetype(  ( (Ada_access_day_clientsourc) query.get(i) ).getExceType(), excetype);
					arr[3]="<a >查看详情</a>";
					data[i]=arr;
				}			
			}		
		
		
		} else {
			query=(List<Ada_access_day_ipsourc> ) ipsourcService.query("select obj from  Ada_access_day_ipsourc obj where obj.siteId=:siteId and obj.referersId=:referersId and obj.endTime between :starttime and :endtime", params, -1, -1);
			if (query!=null&&!query.isEmpty()) {
				data=new String[query.size()][4];	
				for (int i = 0; i < query.size(); i++) {
					String[] arr=new String[4];
					arr[0]=((Ada_access_day_ipsourc) query.get(i)).getRemoteIp();
					arr[1]=((Ada_access_day_ipsourc) query.get(i)).getEpv().toString();
					arr[2]=excetype(  ( (Ada_access_day_ipsourc) query.get(i) ).getExceType(), excetype);
					arr[3]="<a >查看详情</a>";
					data[i]=arr;
				}			
			}	
		
		}
		//List<Ada_access_day_clientsourc> query = clientsourcService.query("select obj from  Ada_access_day_clientsourc obj where obj.siteId=:siteId and obj.referersId=:referersId and obj.endTime between :starttime and :endtime", params, -1, -1);
	
		if (idName!=null) result.put("name", idName.get(params.get("referersId")));
		result.put("data", data);
		
		ResponseData.responseData(response, result);//向页面 相应 数据		
	}

	/**
	 * EUV  明细信息
	 * 
	 * 
	 * */
	@RequestMapping("/user/data/source/details.htm")
	public ModelAndView   details(HttpServletRequest request,HttpServletResponse response,String clientId,String remoteip,Integer referersId
			,long starttime,long endtime ){
		ModelAndView mv = new JModelAndView("/user/html/EachPanleVisitDetails.html", 0, request, response);
		Map<Integer, String> idName = (Map<Integer, String>) request.getSession().getAttribute("idName") ;	//来源 ID → 来源名称
		Map<Integer, String> idDomain = (Map<Integer, String>) request.getSession().getAttribute("idDomain");	//来源 ID → 来源名
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式 
		String likeS = idDomain.get(referersId);
		String qstr="";
		Integer siteId = (Integer) request.getSession().getAttribute("currentSiteId");
		Map params = new HashMap();
		params.put("siteId", siteId);
		params.put("starttime",new Date(starttime) );
		params.put("endtime",new Date( endtime));
		if (clientId!=null) {			
			params.put("clientId", clientId);
			qstr="select obj from Ada_access_except_detail obj  where obj.siteId=:siteId and obj.clientId=:clientId  and obj.accessTime between :starttime and :endtime";		
			mv.addObject("name", idName.get(referersId)+"来源 EUV 明细");
		}else{
			params.put("remoteip", remoteip);
			qstr="select obj from Ada_access_except_detail obj  where obj.siteId=:siteId and obj.remoteIp=:remoteip  and obj.accessTime between :starttime and :endtime";
			mv.addObject("name", idName.get(referersId)+"来源 EIP 明细");
		}

		List<Ada_access_except_detail> query = detailService.query(qstr, params, -1, -1);
		//创建 返回结果 
		List<Ada_access_except_detail> result=new ArrayList<Ada_access_except_detail>(); 
		if (query!=null&&!query.isEmpty()) {
			for (Ada_access_except_detail item : query) {
				String realReferer = item.getRealReferer();
				if (realReferer!=null&&!"".equals(realReferer)&&realReferer.contains(likeS)) result.add(item);
			}
		}		
		String[][] sourceDetail = sourceDetail(result);
		mv.addObject("source", Json.toJson(sourceDetail,JsonFormat.compact()));		
		mv.addObject("time", sdf.format( new Date(starttime))+" 至 "+sdf.format( new Date(endtime)));
		return mv;
	}	
	private  String[][] sourceDetail(List<Ada_access_except_detail> access){
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
	private  Map dayNum(List<Total_day> query) {
		Map result=new HashMap();
		Integer ip=0;//独立ip数
		Integer uv=0;//独立客户
		Integer pv=0;//总访问数量
		Integer eip=0;//异常ip数
		Integer euv=0;//异常客户数
		Integer epv=0;//异常访问数
		if (query!=null&&!query.isEmpty()) {
			for (Total_day item : query) {
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
	
	private  Map hourNum(List<Total_hour> query) {
		Map result=new HashMap();
		Integer ip=0;//独立ip数
		Integer uv=0;//独立客户
		Integer pv=0;//总访问数量
		Integer eip=0;//异常ip数
		Integer euv=0;//异常客户数
		Integer epv=0;//异常访问数
		if (query!=null&&!query.isEmpty()) {
			for (Total_hour item : query) {
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
	/**
	 * Total_day连续七天   按最近的天数 分类 
	 * 
	 * */
	private Map<Long, List<Total_day>> totalDay(List<Total_day> query,Date begin){
		//获得结束 日期的 long 
		Long time = begin.getTime();
		// 连续七天 按每天 进行分类
		Map<Long,List<Total_day>> classfy=new HashMap<Long, List<Total_day>>();
		if (query!=null&&!query.isEmpty()) {
			for (Total_day item : query) {
				List<Total_day> templ=classfy.get((item.getEndTime().getTime()-time)/86400000);
				if (templ==null) {
					templ=new ArrayList<Total_day>();
					templ.add(item);
					classfy.put((item.getEndTime().getTime()-time)/86400000,templ);
				} else {
					templ.add(item);	
				} 				
			}		
		}		
		return classfy;
	}
	/**
	 * Total_hour 连续七天   按最近的天数 分类 
	 * 
	 * */
	private Map<Long, List<Total_hour>> totalHour(List<Total_hour> query,Date begin){
		//获得结束 日期的 long 
		Long time=begin.getTime();
		// 连续七天 按每天 进行分类
		Map<Long,List<Total_hour>> classfy=new HashMap<Long, List<Total_hour>>();
		if (query!=null&&!query.isEmpty()) {
			for (Total_hour item : query) {	
				List<Total_hour> templ=classfy.get(  ( item.getEndTime().getTime()-time)/14400000 );	
				if (templ==null) {
					templ=new ArrayList<Total_hour>();
					templ.add(item);
					classfy.put(  ( item.getEndTime().getTime()-time)/14400000 ,templ);
				} else {
					templ.add(item);	
				} 				
			}		
		}
		return classfy;
	}	
	
	/**
	 * 计算 异常类型
	 * */
	private String excetype(String str,Map<String,String> excetype){
		String result="";
		if (str==null||"".equals(str)) {
			result="无";
		}else{
			String[] split = str.split(",");
			for (String s : split) {
				result=result+excetype.get(s)+",";
			}			
		}			
			return result;
	}
	
}
