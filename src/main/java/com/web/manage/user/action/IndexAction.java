package com.web.manage.user.action;


import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



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

import com.web.foundation.domain.Ada_access_stat_day_source;
import com.web.foundation.domain.Site;
import com.web.foundation.domain.Total_day;
import com.web.foundation.domain.Total_hour;
import com.web.foundation.service.IAda_access_stat_day_sourceService;
import com.web.foundation.service.ITotal_dayService;
import com.web.foundation.service.ITotal_hourService;

/**
 * 首页 流量统计  数据处理
 * 
 * */
@Controller
@SuppressWarnings("all")
public class IndexAction {
	private static final String Integer = null;
	@Autowired
	private ITotal_hourService hourService;
	@Autowired
	private ITotal_dayService  dayService;
	/**
	 * 数据 请求
	 * 
	 * */
	@RequestMapping("/data/index.htm")
	public void indexData(HttpServletRequest request,HttpServletResponse response,
			Integer userId,Integer dayway,Date begin,Date end){
		//dayway 的 值 是 0 1 3 则 查询  查询 小时相关的 表  并且  柱状图  是 按 4 小时 一个 时间段 进行分段
		//创建 参数的 map 集合  
		Integer siteId = (Integer)request.getSession().getAttribute("currentSiteId");
		System.out.println(siteId);
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
			List<Total_day> query = dayService.query("select obj from  Total_day obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);			
			
			//source = sourceService.source("select obj from  Ada_access_stat_day_source obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);
			
			if (query!=null&&!query.isEmpty()) {
				Map<Long, List<Total_day>> classE = totalDay(query,begin);
				for (int i = 0; i < 7; i++) {
					List<Total_day> preData = classE.get((long)i);//获取 前（6-i）天的 数据
					category[i]=sdf.format(  new Date(end.getTime()-(7-i)*86400000) );
					Map dayNum = dayNum(preData);
					pvArr[i]=Long.valueOf( dayNum.get("pv").toString());
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
			List<Total_hour> query = hourService.query("select obj  from  Total_hour obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);			
			if (query!=null&&!query.isEmpty()) {	
				Map<Long, List<Total_hour>> classE =totalHour(query, begin);
				for (int i = 0; i < 6; i++) {
					List<Total_hour> preData = classE.get((long)i);
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
		result.put("siteList",(List<Site>)request.getSession().getAttribute("siteList"));
		result.put("opValue", siteId);
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
		//异常占比量
		NumberFormat formatter = new DecimalFormat("0%");
		String epv_pv = null;
		String euv_uv = null;
		String eip_ip = null;
		if(getSum(pvArr)==0){
			epv_pv = formatter.format(0);
		}else{
			epv_pv = formatter.format(getSum(epvArr)*1.0/getSum(pvArr));
		}
		
		if(getSum(uvArr)==0){
			euv_uv = formatter.format(0);
		}else{
			euv_uv = formatter.format(getSum(euvArr)*1.0/getSum(uvArr));
		}
		
		if(getSum(ipArr)==0){
			eip_ip = formatter.format(0);
		}else{
			eip_ip = formatter.format(getSum(eipArr)*1.0/getSum(ipArr));
		}
		result.put("epv_pv", epv_pv);
		result.put("euv_uv", euv_uv);
		result.put("eip_ip", eip_ip);
		
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
	//处理下拉列表站点的变更请求
	@RequestMapping("/data/siteChange.htm")
	public void siteChangeData(HttpServletRequest request,HttpServletResponse response,
			Integer siteId){
		request.getSession().setAttribute("currentSiteId", siteId);
		System.out.println(siteId);
		try {
			response.getWriter().print(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

