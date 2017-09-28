/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.prom.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * 描    述：账户管理Entity
 *
 * 创 建 者： @author wangdong
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
public class PromAccountInfo extends DataEntity<PromAccountInfo> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 主键
	private String bankNo;		// 银行账号
	private String openName;		// 银行开户名
	private String openAdress;		// 开户所在地
	private String openAllName;		// 开户银行全称
	private String openBranchName;		// 开户支行全称
	private String bankNum;		// 联行号
	private Date createTime;		// 创建时间
	private String createAuthor;		// 创建人
	private Date updateTime;		// 修改时间
	private String updateAuthor;		// 修改人
    private Long merchantId;          //商户编码

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

	public PromAccountInfo() {
		super();
	}

	public PromAccountInfo(String id){
		super(id);
	}

	@NotNull(message="主键不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=25, message="银行账号长度必须介于 0 和 25 之间")
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@Length(min=0, max=100, message="银行开户名长度必须介于 0 和 100 之间")
	public String getOpenName() {
		return openName;
	}

	public void setOpenName(String openName) {
		this.openName = openName;
	}
	
	@Length(min=0, max=200, message="开户所在地长度必须介于 0 和 200 之间")
	public String getOpenAdress() {
		return openAdress;
	}

	public void setOpenAdress(String openAdress) {
		this.openAdress = openAdress;
	}
	
	@Length(min=0, max=200, message="开户银行全称长度必须介于 0 和 200 之间")
	public String getOpenAllName() {
		return openAllName;
	}

	public void setOpenAllName(String openAllName) {
		this.openAllName = openAllName;
	}
	
	@Length(min=0, max=200, message="开户支行全称长度必须介于 0 和 200 之间")
	public String getOpenBranchName() {
		return openBranchName;
	}

	public void setOpenBranchName(String openBranchName) {
		this.openBranchName = openBranchName;
	}
	
	@Length(min=0, max=30, message="联行号长度必须介于 0 和 30 之间")
	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=20, message="创建人长度必须介于 0 和 20 之间")
	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=20, message="修改人长度必须介于 0 和 20 之间")
	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}
	
}