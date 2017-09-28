package com.heepay.manage.modules.monitor.java.vo;

public class MachineResponseVo {
   private int pcNo;
   private String pcName;
   private Boolean dbOrWeb;
   private int groupType;
   private String innerIP;
   private String outerIP;

public int getGroupType() {
	return groupType;
}
public void setGroupType(int groupType) {
	this.groupType = groupType;
}
public String getInnerIP() {
	return innerIP;
}
public void setInnerIP(String innerIP) {
	this.innerIP = innerIP;
}
public String getOuterIP() {
	return outerIP;
}
public void setOuterIP(String outerIP) {
	this.outerIP = outerIP;
}
public String getPcName() {
	return pcName;
}
public void setPcName(String pcName) {
	this.pcName = pcName;
}
public Boolean getDbOrWeb() {
	return dbOrWeb;
}
public void setDbOrWeb(Boolean dbOrWeb) {
	this.dbOrWeb = dbOrWeb;
}
public int getPcNo() {
	return pcNo;
}
public void setPcNo(int pcNo) {
	this.pcNo = pcNo;
}
   
   
}
