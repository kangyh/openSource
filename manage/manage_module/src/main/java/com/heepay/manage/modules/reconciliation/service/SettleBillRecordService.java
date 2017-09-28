package com.heepay.manage.modules.reconciliation.service;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.dao.SettleBillRecordDao;
import com.heepay.manage.modules.reconciliation.entity.SettleBillRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * 
 * 
 * 描    述：账单明细service
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
public class SettleBillRecordService extends CrudService<SettleBillRecordDao, SettleBillRecord> {

	@Autowired
	SettleBillRecordDao settleBillRecordDao;

	/**
	 *
	 * @方法说明：//查询所有记录list
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<SettleBillRecord> findList(SettleBillRecord settleBillRecord) {
		return super.findList(settleBillRecord);
	}

	/**
	 * @方法说明：查询条件
	 * @时间： 2017-05-08 02:53 PM
	 * @创建人：wangl
	 */
	public List<SettleBillRecord> getChannelCodeEnum(){
		return settleBillRecordDao.getChannelCodeEnum();
	}
}
