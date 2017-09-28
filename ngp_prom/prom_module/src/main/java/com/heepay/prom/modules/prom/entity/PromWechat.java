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
 * 描    述：用户管理（微信）Entity
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
public class PromWechat extends DataEntity<PromWechat> {
	
	private static final long serialVersionUID = 1L;
	private Long weId;		// 主键
	private String openid;		// 用户openID(微信号)
	private String nickname;		// 昵称
	private String headimgurl;		// 头像
	private String unionid;		// Unionid
	private String appId;		// 公众号APPID
	private String subscribe;		// 是否已关注 0=未关注，1=已关注
	private String sex;		// 性别 1=男 2=女
	private String language;		// 语言
	private String city;		// 城市
	private String province;		// 省份
	private String country;		// 国家
	private String subscribeTime;		// 关注时间：时间戳
	private String userId;		// 用户ID
	private String createAuthor;		// 操作人
	private Date createTime;		// 创建时间
	private String updateAuthor;		// 修改人
	private Date updateTime;		// 修改时间
	
	public PromWechat() {
		super();
	}

	public PromWechat(String id){
		super(id);
	}

	@NotNull(message="主键不能为空")
	public Long getWeId() {
		return weId;
	}

	public void setWeId(Long weId) {
		this.weId = weId;
	}
	
	@Length(min=0, max=50, message="用户openID(微信号)长度必须介于 0 和 50 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=0, max=50, message="昵称长度必须介于 0 和 50 之间")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=200, message="头像长度必须介于 0 和 200 之间")
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	@Length(min=0, max=20, message="Unionid长度必须介于 0 和 20 之间")
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	@Length(min=0, max=50, message="公众号APPID长度必须介于 0 和 50 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=0, max=1, message="是否已关注 0=未关注，1=已关注长度必须介于 0 和 1 之间")
	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	
	@Length(min=0, max=1, message="性别 1=男 2=女长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=20, message="语言长度必须介于 0 和 20 之间")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	@Length(min=0, max=20, message="城市长度必须介于 0 和 20 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=20, message="省份长度必须介于 0 和 20 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=20, message="国家长度必须介于 0 和 20 之间")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=20, message="关注时间：时间戳长度必须介于 0 和 20 之间")
	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=20, message="操作人长度必须介于 0 和 20 之间")
	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=20, message="修改人长度必须介于 0 和 20 之间")
	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}