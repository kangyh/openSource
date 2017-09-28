/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.route.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.route.entity.IntegrationChannel;

import java.util.List;
import java.util.Map;

/**
 *
 * 描    述：聚合通道DAO接口
 *
 * 创 建 者： @author 马振
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
public interface IntegrationChannelDao extends CrudDao<IntegrationChannel> {

	/**
	 * 查找产品已添加的.net聚合通道
	 * @param params
	 * @return
	 */
	public List<IntegrationChannel> selectIntegraChannel(Map<String,Object> params);

	/**
	 * 更新数据库，有则更新，无则插入
	 * @param integrationChannel
	 */
	public void insertOnDuplicateKey(IntegrationChannel integrationChannel);
	
}