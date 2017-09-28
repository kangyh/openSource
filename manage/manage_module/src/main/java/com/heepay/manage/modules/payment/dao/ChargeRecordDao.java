/**
 *  
 */
package com.heepay.manage.modules.payment.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.ChargeRecord;

/**
 * 充值管理DAO接口
 * @author ld
 * @version V1.0
 */
@MyBatisDao
public interface ChargeRecordDao extends CrudDao<ChargeRecord> {

	public ChargeRecord getChargeId();
	int updatechargeRecordCAGING(String id);
}