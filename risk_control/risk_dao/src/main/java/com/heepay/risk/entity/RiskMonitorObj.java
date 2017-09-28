package com.heepay.risk.entity;
/**
* @author 王英雷  E-mail:wangyl@9186.com
* @version 创建时间：2016年12月10日 下午6:24:35
* 类说明
*/
public class RiskMonitorObj {
//返回编号
private String id;
//返回状态
private String value;
//返回风控服务的名称
private String service;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public String getService() {
	return service;
}
public void setService(String service) {
	this.service = service;
}
}
