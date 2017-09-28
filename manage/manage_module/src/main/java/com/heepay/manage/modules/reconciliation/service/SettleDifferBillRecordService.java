package com.heepay.manage.modules.reconciliation.service;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.dao.SettleDifferBillRecordDao;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferBillRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *
*
* 描    述：异常明细表
*
* 创 建 者： wangl
* 创建时间： 2017-07-28 14:18
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
public class SettleDifferBillRecordService extends CrudService<SettleDifferBillRecordDao, SettleDifferBillRecord> {

	@Autowired
	private SettleDifferBillRecordDao settleDifferBillRecordDao;

	/**
	 * @方法说明：分页查询
	 * @时间： 2017-07-28 14:20
	 * @创建人：wangl
	 */
	public List<SettleDifferBillRecord> findList(SettleDifferBillRecord settleDifferBillRecord) {
		return super.findList(settleDifferBillRecord);
	}


}
