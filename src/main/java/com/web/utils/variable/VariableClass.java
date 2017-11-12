package com.web.utils.variable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 变量 常用类
 * 
 * */
public class VariableClass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5893584750751869357L;
	
	
	private static final Map<String,String> exceType=new HashMap<String, String>();
	static{		
		exceType.put("1", "天天访问");
		exceType.put("2", "高频访问");
		exceType.put("3", "频繁切换ip");
		exceType.put("5", "疑似作弊软件");
		exceType.put("6", "直接访问");
		exceType.put("8", "只访问广告");		
	}
	/**
	 * 获得  异常类型的 map 集合
	 * 
	 * */
	public static Map<String, String> getExcetype() {
		return exceType;
	}
	
}






