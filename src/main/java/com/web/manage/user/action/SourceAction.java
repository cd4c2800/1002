package com.web.manage.user.action;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.foundation.domain.Total_day;
import com.web.foundation.domain.Total_hour;
import com.web.foundation.service.ITotal_dayService;
import com.web.foundation.service.ITotal_hourService;

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
	/**
	 * 数据 请求
	 * 
	 * */
	@RequestMapping("/data/source.htm")
	public void sourceData(HttpServletRequest request,HttpServletResponse response,
			Integer siteId,Integer dayway,Date begin,Date end){
		//dayway 的 值 是 0 1 3 则 查询  查询 小时相关的 表  并且  柱状图  是 按 4 小时 一个 时间段 进行分段
		//创建 参数的 map 集合  
		Map params = new HashMap();
		params.put("siteid", siteId);
		params.put("startime", begin);
		params.put("endtime",end);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");//日期格式 
		String[] category=null; //X轴 标题数组
		String[] pvArr=null; 	//	pv 数组
		String[] uvArr=null; 	//	uv 数组
		String[] ipArr=null;	//	ip 数组
		String[] epvArr=null; 	//	epv 数组
		String[] euvArr=null; 	//	euv 数组
		String[] eipArr=null; 	//	eip 数组
		//返回的 数据结果
		Map result=new HashMap();		
		if (dayway==2) {
			category=new String[7];
			pvArr=new String[7];
			uvArr=new String[7];
			ipArr=new String[7];
			epvArr=new String[7];
			euvArr=new String[7];
			eipArr=new String[7];
			List<Total_day> query = dayService.query("select obj  Total_day obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);
			Map<Long, List<Total_day>> classE = totalDay(query,end);
			for (int i = 0; i < 7; i++) {
				List<Total_day> preData = classE.get(6-i);//获取 前（6-i）天的 数据
				if (preData==null||preData.isEmpty()) continue;
				
				
				
				
				
							
			}			
		} else {
			category=new String[6];
			pvArr=new String[6];
			uvArr=new String[6];
			ipArr=new String[6];
			epvArr=new String[6];
			euvArr=new String[6];
			eipArr=new String[6];
			List<Total_hour> query = hourService.query("select obj  Total_hour obj where obj.siteId=:siteid and obj.endTime between :startime and :endtime", params, -1, -1);
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
	 * Total_day连续七天   按最近的天数 分类 
	 * 
	 * */
	private Map<Long, List<Total_day>> totalDay(List<Total_day> query,Date end){
		//获得结束 日期的 long 
		Long time = end.getTime();
		// 连续七天 按每天 进行分类
		Map<Long,List<Total_day>> classfy=new HashMap<Long, List<Total_day>>();
		if (query!=null&&!query.isEmpty()) {
			for (Total_day item : query) {
				List<Total_day> templ=classfy.get( (time-item.getEndTime().getTime())/86400000);
				if (templ==null) {
					templ=new ArrayList<Total_day>();
					templ.add(item);
					classfy.put((time-item.getEndTime().getTime())/86400000,templ);
				} else {
					templ.add(item);	
				} 				
			}		
		}		
		return classfy;
	}
	
	
	
}
