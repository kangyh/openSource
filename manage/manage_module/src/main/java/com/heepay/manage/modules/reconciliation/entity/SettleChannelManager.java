package com.heepay.manage.modules.reconciliation.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class SettleChannelManager extends DataEntity<SettleChannelManager> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long channelManangeId;

    private String channelCode;

    private String channelName;
    
    private String Name;

    private String channelType;

    private String costSettleCyc;

    private String effectFlg;

    private String checkFlg;

    private String settleCyc;

    private String remoteAdress;

    private String remoteUserName;

    private String remotePassword;

    private String localFilePath;
    
    private Date createTime;

    private String createAuthor;

    private Date updateTime;

    private String updateAuthor;
    
    private String settleWay;

    private String ruleType;
    
    private String port;


    /**
     * 非映射字段（数据库中没有对应的字段，用于查询）
     */
    private String remoteAdressPath;//新增字段用来保存对账文件地址

    private String deleteStatus;

    private String bankName;

    private String bankNo;


    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}

	public String getRemoteAdressPath() {
		return remoteAdressPath;
	}

	public void setRemoteAdressPath(String remoteAdressPath) {
		this.remoteAdressPath = remoteAdressPath;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getChannelManangeId() {
        return channelManangeId;
    }

    public void setChannelManangeId(Long channelManangeId) {
        this.channelManangeId = channelManangeId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public String getCostSettleCyc() {
        return costSettleCyc;
    }

    public void setCostSettleCyc(String costSettleCyc) {
        this.costSettleCyc = costSettleCyc == null ? null : costSettleCyc.trim();
    }

    public String getEffectFlg() {
        return effectFlg;
    }

    public void setEffectFlg(String effectFlg) {
        this.effectFlg = effectFlg == null ? null : effectFlg.trim();
    }

    public String getCheckFlg() {
        return checkFlg;
    }

    public void setCheckFlg(String checkFlg) {
        this.checkFlg = checkFlg == null ? null : checkFlg.trim();
    }

    public String getSettleCyc() {
        return settleCyc;
    }

    public void setSettleCyc(String settleCyc) {
        this.settleCyc = settleCyc == null ? null : settleCyc.trim();
    }

    public String getRemoteAdress() {
        return remoteAdress;
    }

    public void setRemoteAdress(String remoteAdress) {
        this.remoteAdress = remoteAdress == null ? null : remoteAdress.trim();
    }

    public String getRemoteUserName() {
        return remoteUserName;
    }

    public void setRemoteUserName(String remoteUserName) {
        this.remoteUserName = remoteUserName == null ? null : remoteUserName.trim();
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public void setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword == null ? null : remotePassword.trim();
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath == null ? null : localFilePath.trim();
    }

	public String getSettleWay() {
		return settleWay;
	}

	public void setSettleWay(String settleWay) {
		this.settleWay = settleWay;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}