/**
 *  
 */
package com.heepay.manage.modules.payment.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.GatewayRecord;

/**
 * 交易管理DAO接口
 * @author ld
 * @version V1.0
 */
@MyBatisDao
public interface GatewayRecordDao extends CrudDao<GatewayRecord> {
	
	public void freezeAndThaw(GatewayRecord gatewayRecord);
	
	public GatewayRecord getOne(String merchantOrderNo);

	public GatewayRecord getGatewayId();
	int updateGatewayRecordPAYING(String id);
}