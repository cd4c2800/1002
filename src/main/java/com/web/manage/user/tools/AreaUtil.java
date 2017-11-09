package com.web.manage.user.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.web.core.tools.CommUtil;
import com.web.foundation.domain.Sys_region;
import com.web.foundation.service.ISys_regionService;
@Component
public class AreaUtil {

	@Autowired
	private  ISys_regionService sys_regionService;
	
	/**
	 * 获取国家名称
	 * @param id
	 * @return
	 */
	public  String getCountryById(String id){
		
		Map params = new HashMap();
		params.put("countryId", CommUtil.null2Int(id));
		List<Sys_region> list = this.sys_regionService.query("select obj from Sys_region obj where obj.countryId=:countryId", params, 0, 1);
		if(list!=null && list.size()>0){
			return list.get(0).getCountryName();
		}
		return "无信息";
	}
	
	/**
	 * 获取省份名称
	 * @param id
	 * @return
	 */
	public String getProvinceById(String id){
		Map params = new HashMap();
		params.put("provinceId", CommUtil.null2Int(id));
		List<Sys_region> list = this.sys_regionService.query("select obj from Sys_region obj where obj.provinceId=:provinceId", params, 0, 1);
		if(list!=null && list.size()>0){
			return list.get(0).getProvinceName();
		}
		return "无信息";
	}
}
