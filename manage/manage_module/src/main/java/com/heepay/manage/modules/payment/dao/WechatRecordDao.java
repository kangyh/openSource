/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.WechatRecord;

/**
 *
 * 描    述：微信支付订单DAO接口
 *
 * 创 建 者： @author 张晨
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
@MyBatisDao
public interface WechatRecordDao extends CrudDao<WechatRecord> {

	WechatRecord getWechatId();
	int updatewechatRecordPAYING(String id);
}