package com.heepay.risk.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 王英雷  E-mail:wangyl@9186.com
* @version 创建时间：2016年12月3日 下午4:23:13
* 类说明
*/
public class TransInfoObj {
	private String totalsize="";
	private List<Map> transinfo = new ArrayList<Map>();
	public String getTotalsize() {
		return totalsize;
	}
	public void setTotalsize(String totalsize) {
		this.totalsize = totalsize;
	}
	public List<Map> getTransinfo() {
		return transinfo;
	}
	public void setTransinfo(List<Map> transinfo) {
		this.transinfo = transinfo;
	}
}
