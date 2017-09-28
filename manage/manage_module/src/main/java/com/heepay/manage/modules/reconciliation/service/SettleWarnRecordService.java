package com.heepay.manage.modules.reconciliation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.dao.SettleWarnRecordDao;
import com.heepay.manage.modules.reconciliation.entity.SettleWarnRecord;

/***
 * 
 * 
 * 描    述：告警记录service
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午4:09:20
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
@Service
@Transactional(readOnly = true)
public class SettleWarnRecordService extends CrudService<SettleWarnRecordDao, SettleWarnRecord> {

	@Autowired
	SettleWarnRecordDao settleWarnRecordDao;
}
