/**
 *  
 */
package com.heepay.manage.modules.payment.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.WithdrawRecord;

/**
 * 提现管理DAO接口
 * @author hs
 * @version V1.0
 */
@MyBatisDao
public interface WithdrawRecordDao extends CrudDao<WithdrawRecord> {
	
    public void auditCtrl(WithdrawRecord withdrawRecord);

	public WithdrawRecord getWithdrawId();
	
	int updateWithdrawDRAING(String id);
}