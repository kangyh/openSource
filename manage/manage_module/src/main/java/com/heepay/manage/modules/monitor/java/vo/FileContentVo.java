package com.heepay.manage.modules.monitor.java.vo;

public class FileContentVo {
	private int pcNo;
	private int isGetDiff ;
	private long createTime;
	private int lastLookLogLine;
	private long time;
	private String sign;
	private String fullName;
	public int getPcNo() {
		return pcNo;
	}
	public void setPcNo(int pcNo) {
		this.pcNo = pcNo;
	}
	public int getIsGetDiff() {
		return isGetDiff;
	}
	public void setIsGetDiff(int isGetDiff) {
		this.isGetDiff = isGetDiff;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public int getLastLookLogLine() {
		return lastLookLogLine;
	}
	public void setLastLookLogLine(int lastLookLogLine) {
		this.lastLookLogLine = lastLookLogLine;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
