/**
 *  
 */
package com.heepay.manage.modules.payment.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.ExceptionRecordDao;
import com.heepay.manage.modules.payment.entity.ExceptionRecord;

/**
 * 
* 
* 描    述：投诉处理
*
* 创 建 者： yanxb  
* 创建时间： 2016年11月29日 下午6:28:46 
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
public class ExceptionRecordService extends CrudService<ExceptionRecordDao, ExceptionRecord> {

	@Autowired
	ExceptionRecordDao exceptionRecordDao;
	
	public Page<ExceptionRecord> findPage(Page<ExceptionRecord> page, ExceptionRecord exceptionRecord) {
		return super.findPage(page, exceptionRecord);
	}

	public ExceptionRecord getRecordByTransNo(String transNo) {
		return exceptionRecordDao.getRecordByTransNo(transNo);
	}

	@Transactional(readOnly = false)
	public int reset(String resetId, String resetDesc, String operator) {
		Map<String, Object> map = new HashMap<>();
		map.put("resetId", resetId);
		map.put("resetDesc", resetDesc);
		map.put("resetOperator", operator);
		return exceptionRecordDao.reset(map);
	}
}