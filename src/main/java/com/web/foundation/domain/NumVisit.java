package com.web.foundation.domain;

import java.io.Serializable;

/**
 * pv 、ip、uv、 epv、eip、euv
 * 
 * */
public class NumVisit  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4685770256330249612L;
	private Long pv;
	private Long ip;
	private Long uv;
	private Long epv;
	private Long eip;
	private Long euv;
	
	public NumVisit(Long pv, Long ip, Long uv, Long epv, Long eip, Long euv) {
		super();
		this.pv = pv;
		this.ip = ip;
		this.uv = uv;
		this.epv = epv;
		this.eip = eip;
		this.euv = euv;
	}
	public Long getPv() {
		return pv;
	}
	public void setPv(Long pv) {
		this.pv = pv;
	}
	public Long getIp() {
		return ip;
	}
	public void setIp(Long ip) {
		this.ip = ip;
	}
	public Long getUv() {
		return uv;
	}
	public void setUv(Long uv) {
		this.uv = uv;
	}
	public Long getEpv() {
		return epv;
	}
	public void setEpv(Long epv) {
		this.epv = epv;
	}
	public Long getEip() {
		return eip;
	}
	public void setEip(Long eip) {
		this.eip = eip;
	}
	public Long getEuv() {
		return euv;
	}
	public void setEuv(Long euv) {
		this.euv = euv;
	}
	


}
