package com.heepay.manage.modules.reconciliation.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.dao.SettleChannelManagerDao;
import com.heepay.manage.modules.reconciliation.dao.SettleDifferRecordDao;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecord;
import com.heepay.manage.modules.sys.utils.UserUtils;

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
public class SettleDifferRecordService extends CrudService<SettleDifferRecordDao, SettleDifferRecord> {

	@Autowired
	SettleDifferRecordDao settleDifferRecordDao;
	@Autowired
	SettleChannelManagerDao settleChannelManagerDao;
	
	

	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<SettleDifferRecord> findList(SettleDifferRecord settleDifferRecord) {
		return super.findList(settleDifferRecord);
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

	/**
	 * 
	 * @方法说明：导出
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<Map<String, Object>> findListExcel(SettleDifferRecord settleDifferRecord) {
		
		return settleDifferRecordDao.findListExcel(settleDifferRecord);
	}

	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public SettleDifferRecord getEntityById(int differId) {
		
		return settleDifferRecordDao.getEntityById(differId);
	}
	
	
	/**
	 * 
	 * @方法说明：更新时间
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateTime(SettleDifferRecord settleDiffer) {
		//更新人
		settleDiffer.setUpdateAuthor(UserUtils.getUser().getName());
		settleDifferRecordDao.updateTime(settleDiffer);
	}

	/**
	 * 
	 * @方法说明：根据交易单号去差错记录表查询交易类型
	 * @时间：Nov 22, 2016
	 * @创建人：wangl
	 */
	public SettleDifferRecord getTransTypeByTransNo(String transNo) {
		
		return settleDifferRecordDao.getTransTypeByTransNo(transNo);
	}

	/**
	 * @方法说明：分页查询
	 * @时间： 2017-05-09 04:16 PM
	 * @创建人：wangl
	 */
	public List<SettleDifferRecord> getListByPage(SettleDifferRecord entity){

		return settleDifferRecordDao.getListByPage(entity);
	}

	/**
	 * @方法说明：分页查询
	 * @时间： 2017-05-09 04:16 PM
	 * @创建人：wangl
	 */
    public List<SettleDifferRecord> differRecordPage(SettleDifferRecord settleDifferRecord) {

		return settleDifferRecordDao.differRecordPage(settleDifferRecord);
    }
    
    public SettleDifferRecord getSettleDifferRecordByTransNo(String transNo) {
		
		return settleDifferRecordDao.getSettleDifferRecordByTransNo(transNo);
	}
}
