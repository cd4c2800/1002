package com.web.utils.response;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

/**
 * 响应 数据
 * 
 * */
@SuppressWarnings("all")
public class ResponseData {
	/**
	 * 向 页面相应 数据
	 * */
	public static void responseData(HttpServletResponse response, Map result) {
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
}
