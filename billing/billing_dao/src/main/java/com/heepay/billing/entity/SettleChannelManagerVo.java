package com.heepay.billing.entity;


public class SettleChannelManagerVo {
	
	private String channelCode;
	private String channelName;
	private String channelType;
	private String remoteAdress;       
	private String remoteUserName;    
	private String remotePassword;    
	private String localFilePath;
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getRemoteAdress() {
		return remoteAdress;
	}
	public void setRemoteAdress(String remoteAdress) {
		this.remoteAdress = remoteAdress;
	}
	public String getRemoteUserName() {
		return remoteUserName;
	}
	public void setRemoteUserName(String remoteUserName) {
		this.remoteUserName = remoteUserName;
	}
	public String getRemotePassword() {
		return remotePassword;
	}
	public void setRemotePassword(String remotePassword) {
		this.remotePassword = remotePassword;
	}
	public String getLocalFilePath() {
		return localFilePath;
	}
	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}
	
	
}
