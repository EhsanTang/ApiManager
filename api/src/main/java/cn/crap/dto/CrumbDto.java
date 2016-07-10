package cn.crap.dto;

import java.io.Serializable;

public class CrumbDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 导航条
	String url;
	String name;
	public CrumbDto(String name, String url) {
		this.name = name;
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
