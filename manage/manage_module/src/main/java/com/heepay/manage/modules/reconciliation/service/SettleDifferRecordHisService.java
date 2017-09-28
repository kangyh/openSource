package com.heepay.manage.modules.reconciliation.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.dao.SettleChannelManagerDao;
import com.heepay.manage.modules.reconciliation.dao.SettleDifferRecordHisDao;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecordHis;

/***
 * 
 * 
 * 描    述：差异记录service
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
public class SettleDifferRecordHisService extends CrudService<SettleDifferRecordHisDao, SettleDifferRecordHis> {

	@Autowired
	SettleDifferRecordHisDao settleDifferRecordHisDao;

	@Autowired
	SettleChannelManagerDao settleChannelManagerDao;
	
	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：2017年3月13日17:27:49
	 * @创建人：wangdong
	 */
	public List<SettleDifferRecordHis> findList(SettleDifferRecordHis settleDifferRecordHis) {
		return super.findList(settleDifferRecordHis);
	}


	/**
	 * 
	 * @方法说明：导出
	 * @时间：2017年3月13日17:28:09
	 * @创建人：wangdong
	 */
	public List<Map<String, Object>> findListExcel(SettleDifferRecordHis settleDifferRecordHis) {
		return settleDifferRecordHisDao.findListExcel(settleDifferRecordHis);
	}
	
	/**
	 * 
	 * @方法说明：根据交易单号去差错记录表查询交易类型
	 * @时间：2017年3月13日17:48:39
	 * @创建人：wangdong
	 */
	public SettleDifferRecordHis getTransTypeByTransNo(String transNo) {
		return settleDifferRecordHisDao.getTransTypeByTransNo(transNo);
	}
	
	/**
	 * 
	 * @方法说明：//对账规则查询出已有的通道名称和通道编码
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<SettleChannelManager> getBatchName() {
		return settleChannelManagerDao.getBatchName();
	}

}
