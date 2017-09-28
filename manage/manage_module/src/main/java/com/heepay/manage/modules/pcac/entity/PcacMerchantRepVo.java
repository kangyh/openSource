/**
 * 
 */
package com.heepay.manage.modules.pcac.entity;

/**
 * @author Administrator
 *
 */
public class PcacMerchantRepVo extends PcacPersonRepVo{
	
	
    private String legDocName;
    private String legDocType;
    private String legDocCode;
    private String shareHolder;
    
	
	public String getLegDocName() {
		return legDocName;
	}
	public void setLegDocName(String legDocName) {
		this.legDocName = legDocName;
	}
	public String getLegDocType() {
		return legDocType;
	}
	public void setLegDocType(String legDocType) {
		this.legDocType = legDocType;
	}
	public String getLegDocCode() {
		return legDocCode;
	}
	public void setLegDocCode(String legDocCode) {
		this.legDocCode = legDocCode;
	}
	public String getShareHolder() {
		return shareHolder;
	}
	public void setShareHolder(String shareHolder) {
		this.shareHolder = shareHolder;
	}
	
}
