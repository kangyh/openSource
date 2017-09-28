/**
 *  
 */
package com.heepay.manage.modules.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.RemitsChargeRecordDao;
import com.heepay.manage.modules.payment.entity.RemitsChargeRecord;

/**
 * 
* 
* 描    述：汇款充值管理Service
*
* 创 建 者： yanxb  
* 创建时间： 2017年4月18日 下午5:01:03 
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
public class RemitsChargeRecordService extends CrudService<RemitsChargeRecordDao, RemitsChargeRecord> {

	@Autowired
	RemitsChargeRecordDao remitsChargeRecordDao;
	
	public RemitsChargeRecord getById(String transNo) {
		return super.get(transNo);
	}
	
}