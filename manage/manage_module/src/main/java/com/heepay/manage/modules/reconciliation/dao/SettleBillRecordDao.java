package com.heepay.manage.modules.reconciliation.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.reconciliation.entity.SettleBillRecord;

import java.util.List;

/***
 * 
* 
* 描    述：账单明细
*
* 创 建 者： wangl
* 创建时间：  2016年10月27日下午3:44:30
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
public interface SettleBillRecordDao extends CrudDao<SettleBillRecord> {

    List<SettleBillRecord> getChannelCodeEnum();
}
