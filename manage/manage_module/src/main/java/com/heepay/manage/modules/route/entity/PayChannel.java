/**
 *  
 */
package com.heepay.manage.modules.route.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 支付通道Entity
 * @author lm
 * @version V1.0
 */
public class PayChannel extends DataEntity<PayChannel> implements Cloneable {
	
	private static final long serialVersionUID = 1L;
	private String channelCode;		// 支付通道代码
	private String channelName;		// 支付通道名称
	private String bankName;		// 银行名称
	private String bankNo;   // 银行代码
	private String channelPartnerName;		// 通道合作方
	private String channelPartnerCode;   // 通道合作方代码
	private String channelTypeName;		// 支付通道类型
	private String channelTypeCode;    // 支付通道类型代码
	private String merchantNumber;		//通道侧商户号
	private String cardTypeName;		// 银行卡类型
	private String cardTypeCode;   // 银行卡类型代码
	private String accountType;   // 对公对私标识代码
  	private String businessType;   // 付款类型代码
	private String chargeDeductType; //手续费扣除方式
	private String chargeReturnTag; // 是否退还手续费
	private Date effectStartDate;		// 有效开始时间
	private Date effectEndDate;		// 有效结束时间
	private String costType;		// 成本类型
	private String costRate;		// 成本按比例
	private String costCount;    // 成本按笔数
	private String status;		// 状态
	private String updateName; //更新人
	private Date contractDate;		// 合约时间
	private String settleType;		// 本金结算类型
	private String settlePeriod;		// 本金结算周期
	private String sort;		// 优先级别
	private String perlimitAmount;   // 单笔限额
	private String daylimitAmount;   // 单日限额
	private String monlimitAmount;   // 单月限额
	private String merchantId;   //商户ID
	private String productDetailId;
	private String productCode;
	private String orderSettlePeriod; //通道结算周期
	private String merchantPayChannelId;
	private String selectStatus; //页面显示的查询状态
	private String merchantSubject; //所属主体
	private String channelTag; //渠道标识
	private String groupId;//通道合作方组Id

	public PayChannel() {
		super();
	}

	public PayChannel(String id){
		super(id);
	}

	@Length(min=0, max=34, message="支付通道代码长度必须介于 0 和34 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Length(min=0, max=50, message="支付通道名称长度必须介于 0 和 50 之间")
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Length(min=0, max=50, message="银行名称长度必须介于 0 和 50 之间")
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
  
	@Length(min=0, max=20, message="通道合作方长度必须介于 0 和 20 之间")
	public String getChannelPartnerName() {
		return channelPartnerName;
	}

	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}
	
	@Length(min=0, max=6, message="通道合作方代码长度必须介于 0 和6之间")
  	public String getChannelPartnerCode() {
    return channelPartnerCode;
  }

  	public void setChannelPartnerCode(String channelPartnerCode) {
    this.channelPartnerCode = channelPartnerCode;
  }
	
	@Length(min=0, max=20, message="支付通道类型长度必须介于 0 和 20 之间")
	public String getChannelTypeName() {
		return channelTypeName;
	}

	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}
	
	@Length(min=0, max=20, message="支付通道类型长度必须介于 0 和6 之间")
  	public String getChannelTypeCode() {
    return channelTypeCode;
  }

  	public void setChannelTypeCode(String channelTypeCode) {
    this.channelTypeCode = channelTypeCode;
  }
	
	@Length(min=0, max=6, message="银行卡类型长度必须介于 0 和 6 之间")
	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}
	
	@Length(min=0, max=6, message="对公对私标识代码长度必须介于 0 和 6 之间")
  	public String getAccountType() {
    return accountType;
  }

  	public void setAccountType(String accountType) {
    this.accountType = accountType;
  }
	
  	@Length(min=0, max=6, message="付款类型代码长度必须介于 0 和 6 之间")
  	public String getBusinessType() {return businessType;}

  	public void setBusinessType(String businessType) {this.businessType = businessType;}

	@Length(min=0, max=6, message="手续费扣除方式代码长度必须介于 0 和 6 之间")
	public String getChargeDeductType() {return chargeDeductType;}

	public void setChargeDeductType(String chargeDeductType) {this.chargeDeductType = chargeDeductType;}

	@Length(min=0, max=6, message="是否退还手续费长度必须介于 0 和 6 之间")
	public String getChargeReturnTag() {return chargeReturnTag;}

	public void setChargeReturnTag(String chargeReturnTag) {this.chargeReturnTag = chargeReturnTag;}
  
  	@Length(min=0, max=6, message="银行卡类型代码长度必须介于 0 和 6 之间")
  	public String getCardTypeCode() {
    return cardTypeCode;
  }

  	public void setCardTypeCode(String cardTypeCode) {
    this.cardTypeCode = cardTypeCode;
  }

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEffectStartDate() {
		return effectStartDate;
	}

	public void setEffectStartDate(Date effectStartDate) {
		this.effectStartDate = effectStartDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEffectEndDate() {
		return effectEndDate;
	}

	public void setEffectEndDate(Date effectEndDate) {
		this.effectEndDate = effectEndDate;
	}
	
	@Length(min=0, max=6, message="成本类型长度必须介于 0 和 6 之间")
	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}
	
	public String getCostRate() {
		return costRate;
	}

	public void setCostRate(String costRate) {
		this.costRate = costRate;
	}
	
	public String getCostCount() {
    return costCount;
  }

  	public void setCostCount(String costCount) {this.costCount = costCount;}

  	public String getPerlimitAmount() {
	return perlimitAmount;
  }

  	public void setPerlimitAmount(String perlimitAmount) {this.perlimitAmount = perlimitAmount;}

	public String getDaylimitAmount() {return daylimitAmount;}

  	public void setDaylimitAmount(String daylimitAmount) {
	this.daylimitAmount = daylimitAmount;
  }

  	public String getMonlimitAmount() {
	return monlimitAmount;
  }

  	public void setMonlimitAmount(String monlimitAmount) {
	this.monlimitAmount = monlimitAmount;
  }
  
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	
	@Length(min=0, max=6, message="本金结算类型长度必须介于 0 和 6 之间")
	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	
	@Length(min=0, max=6, message="本金结算周期长度必须介于 0 和 6 之间")
	public String getSettlePeriod() {
		return settlePeriod;
	}

	public void setSettlePeriod(String settlePeriod) {
		this.settlePeriod = settlePeriod;
	}
	
	@Length(min=0, max=6, message="优先级别长度必须介于 0 和 6 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getProductDetailId() {
    return productDetailId;
  }

  	public void setProductDetailId(String productDetailId) {
    this.productDetailId = productDetailId;
  }	
  
  	public String getProductCode() {
    return productCode;
  }

  	public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

	@Length(min=0, max=6, message="通道结算周期长度必须介于 0 和 6 之间")
	public String getOrderSettlePeriod() {
		return orderSettlePeriod;
	}

	public void setOrderSettlePeriod(String orderSettlePeriod) {
		this.orderSettlePeriod = orderSettlePeriod;
	}

	@Length(min=0, max=50, message="通道侧商户号长度必须介于 0 和 50 之间")
	public String getMerchantNumber() {
		return merchantNumber;
	}

	public void setMerchantNumber(String merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public String getMerchantPayChannelId() {
		return merchantPayChannelId;
	}

	public void setMerchantPayChannelId(String merchantPayChannelId) {
		this.merchantPayChannelId = merchantPayChannelId;
	}
	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSelectStatus() {
		return selectStatus;
	}

	public void setSelectStatus(String selectStatus) {
		this.selectStatus = selectStatus;
	}

	@Length(min=0, max=50, message="所属主体长度必须介于 0 和 50 之间")
	public String getMerchantSubject() {
		return merchantSubject;
	}

	public void setMerchantSubject(String merchantSubject) {
		this.merchantSubject = merchantSubject;
	}
	@Length(min=0, max=200, message="渠道标识长度必须介于 0 和 200 之间")
	public String getChannelTag() {
		return channelTag;
	}

	public void setChannelTag(String channelTag) {
		this.channelTag = channelTag;
	}

	@Override
	public PayChannel clone() throws CloneNotSupportedException {
		return (PayChannel)super.clone();
	}
}