/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：单据备份Entity
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
public class BilldiffOrderbak extends DataEntity<BilldiffOrderbak> {
	
	private static final long serialVersionUID = 1L;
	private String bakId;		// 自曾id
	private Long paymentId;		// paymentid
	private String transNo;		// 交易id
	private String content;		// 备份内容json格式
	private Date createTime;		// 备份时间
	private String  qtrans_no;
	private String tableName;
	public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getQtrans_no() {
    return qtrans_no;
  }

  public void setQtrans_no(String qtrans_no) {
    this.qtrans_no = qtrans_no;
  }

  public BilldiffOrderbak() {
		super();
	}

	public BilldiffOrderbak(String id){
		super(id);
	}

	@Length(min=1, max=10, message="自曾id长度必须介于 1 和 10 之间")
	public String getBakId() {
		return bakId;
	}

	public void setBakId(String bakId) {
		this.bakId = bakId;
	}
	
	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	
	@Length(min=0, max=50, message="交易id长度必须介于 0 和 50 之间")
	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	
	@Length(min=0, max=6000, message="备份内容json格式长度必须介于 0 和 6000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}