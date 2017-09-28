package com.heepay.manage.modules.reconciliation.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferBillRecord;
/**
 * @方法说明：异常明细表
 * @时间： 2017-07-28 14:05
 * @创建人：wangl
 */
@MyBatisDao
public interface SettleDifferBillRecordDao extends CrudDao<SettleDifferBillRecord> {

    int deleteByPrimaryKey(Long billDifferId);

    int saveEntity(SettleDifferBillRecord record);

    SettleDifferBillRecord getEntity(Long billDifferId);

    int updateEntity(SettleDifferBillRecord record);

}