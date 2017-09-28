package com.heepay.manage.modules.monitor.java.vo;

public class FileListVo {
	/**
	 * 机器码
	 */
    private int pcNo;
    /**
	 * 目录类型
	 */
    private int folderType;
    /**
	 * 时间戳
	 */
    private long time;
    /**
	 * 签名
	 */
    private String sign;

	public int getPcNo() {
		return pcNo;
	}

	public void setPcNo(int pcNo) {
		this.pcNo = pcNo;
	}

	public int getFolderType() {
		return folderType;
	}

	public void setFolderType(int folderType) {
		this.folderType = folderType;
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
}
