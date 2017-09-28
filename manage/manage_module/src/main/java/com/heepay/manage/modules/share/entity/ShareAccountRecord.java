/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.share.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：分润查看Entity
 *
 * 创 建 者： @author zc
 * 创建时间：
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
public class ShareAccountRecord extends DataEntity<ShareAccountRecord> {
	
	private static final long serialVersionUID = 1L;
	private String shareId;		// 分润id 自增主键
	private Long merchantId;		// 商户id
	private String transNo;		// 要分润的订单号,gateway_record表id
	private Date transCreateTime;		// 原订单创建时间
	private String transPayAmount;		// 原订单总金额
	private String transSuccessAmount;		// 原订单完成金额
	private String shareDate;		// 分润日期
	private String status;		// 状态
	private String shareAmount;		// 分润总额
	private String shareMerchantNum;		// 要分润的商户总数
	private Date createTime;		// 创建日期
	private Date updateTime;		// 修改日期
	private String requestIp;		// 请求ip
	private String operator;		// 操作人
	private String merchantOrderNo;		// merchant_order_no
	private String merchantLoginName;		// merchant_login_name
	private String merchantCompany;		// merchant_company
	private String feeAmount;		// 手续费
	
	private Date beginTransCreateTime;   // 开始 原订单创建时间
  private Date endTransCreateTime;   // 结束 原订单创建时间
  
  private Date beginCreateTime;   // 开始 创建时间
  private Date endCreateTime;   // 结束 创建时间
  
  
	public Date getBeginTransCreateTime() {
    return beginTransCreateTime;
  }

  public void setBeginTransCreateTime(Date beginTransCreateTime) {
    this.beginTransCreateTime = beginTransCreateTime;
  }

  public Date getEndTransCreateTime() {
    return endTransCreateTime;
  }

  public void setEndTransCreateTime(Date endTransCreateTime) {
    this.endTransCreateTime = endTransCreateTime;
  }

  public Date getBeginCreateTime() {
    return beginCreateTime;
  }

  public void setBeginCreateTime(Date beginCreateTime) {
    this.beginCreateTime = beginCreateTime;
  }

  public Date getEndCreateTime() {
    return endCreateTime;
  }

  public void setEndCreateTime(Date endCreateTime) {
    this.endCreateTime = endCreateTime;
  }


	  
	  
	public ShareAccountRecord() {
		super();
	}

	public ShareAccountRecord(String id){
		super(id);
	}

	@Length(min=1, max=18, message="分润id 自增主键长度必须介于 1 和 18 之间")
	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}
	
	@NotNull(message="商户id不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=1, max=50, message="要分润的订单号,gateway_record表id长度必须介于 1 和 50 之间")
	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="原订单创建时间不能为空")
	public Date getTransCreateTime() {
		return transCreateTime;
	}

	public void setTransCreateTime(Date transCreateTime) {
		this.transCreateTime = transCreateTime;
	}
	
	public String getTransPayAmount() {
		return transPayAmount;
	}

	public void setTransPayAmount(String transPayAmount) {
		this.transPayAmount = transPayAmount;
	}
	
	public String getTransSuccessAmount() {
		return transSuccessAmount;
	}

	public void setTransSuccessAmount(String transSuccessAmount) {
		this.transSuccessAmount = transSuccessAmount;
	}
	
	@Length(min=1, max=12, message="分润日期长度必须介于 1 和 12 之间")
	public String getShareDate() {
		return shareDate;
	}

	public void setShareDate(String shareDate) {
		this.shareDate = shareDate;
	}
	
	@Length(min=1, max=6, message="状态长度必须介于 1 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(String shareAmount) {
		this.shareAmount = shareAmount;
	}
	
	@Length(min=1, max=10, message="要分润的商户总数长度必须介于 1 和 10 之间")
	public String getShareMerchantNum() {
		return shareMerchantNum;
	}

	public void setShareMerchantNum(String shareMerchantNum) {
		this.shareMerchantNum = shareMerchantNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建日期不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="修改日期不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=1, max=50, message="请求ip长度必须介于 1 和 50 之间")
	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	
	@Length(min=1, max=6, message="操作人长度必须介于 1 和 6 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=1, max=64, message="merchant_order_no长度必须介于 1 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Length(min=1, max=64, message="merchant_login_name长度必须介于 1 和 64 之间")
	public String getMerchantLoginName() {
		return merchantLoginName;
	}

	public void setMerchantLoginName(String merchantLoginName) {
		this.merchantLoginName = merchantLoginName;
	}
	
	@Length(min=1, max=64, message="merchant_company长度必须介于 1 和 64 之间")
	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
}