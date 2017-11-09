package com.web.foundation.domain;

import java.io.Serializable;




/**
 * 
 * 当前在线 曲线图 数据封装类
 * 
 * */
public class OnlineData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4160004565785377355L;
	//@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	//@JsonFormat(pattern="yyyy-MM-dd hh:mm")
	private String y;	//曲线图 时间
	private Long  a;//曲线图 访问总数
	private Long  b;//曲线图 独立客户端数
	private Long  c;//曲线图 独立IP数
	public OnlineData(String y, Long a, Long b, Long c) {
		super();
		this.y = y;
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public Long getA() {
		return a;
	}
	public void setA(Long a) {
		this.a = a;
	}
	public Long getB() {
		return b;
	}
	public void setB(Long b) {
		this.b = b;
	}
	public Long getC() {
		return c;
	}
	public void setC(Long c) {
		this.c = c;
	}
	
	
}
