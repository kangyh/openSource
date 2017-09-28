package com.heepay.manage.modules.reconciliation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.dao.SettleChannelManagerDao;
import com.heepay.manage.modules.reconciliation.dao.SettleRuleControlDao;
import com.heepay.manage.modules.reconciliation.dao.SettleRuleSecondDao;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleControl;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleSecond;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年5月9日 下午1:19:14
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
public class SettleRuleSecondService extends CrudService<SettleRuleSecondDao, SettleRuleSecond> {
	@Autowired
	private SettleChannelManagerDao settleChannelManagerDao;
	
	@Autowired
	private SettleRuleSecondDao settleRuleSecondDao;
	public List<SettleRuleSecond> findList(SettleRuleSecond settleRuleSecond) {
		return super.findList(settleRuleSecond);
	}
	/**
	 * 
	 * @方法说明：根据id获取整个实体对象
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public SettleRuleSecond getEntity(int ruleControlId) {
		
		return settleRuleSecondDao.getEntity(ruleControlId);
	}
	public SettleRuleSecond getEntityByConditon(SettleRuleSecond entity)
	{
		return settleRuleSecondDao.getEntityByConditon(entity);
	}

	/**
	 * 
	 * @方法说明：保存实体对象
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(SettleRuleSecond settleRuleSecond) {
		settleRuleSecondDao.saveEntity(settleRuleSecond);
	}

	/**
	 * 
	 * @方法说明：添加实体对象
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int  addEntity(SettleRuleSecond settleRuleSecond) {
		return settleRuleSecondDao.addEntity( settleRuleSecond);
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
	 * @方法说明：获取通道名称
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public String getChannelName(String channelCode) {
		
		return settleChannelManagerDao.getChannelName(channelCode);
	}
	/**
	 * @方法说明：删除规则
	 * @时间：2017年1月19日上午11:33:09
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int deleteEntity(Long ruleControlId) {
		
		return settleRuleSecondDao.deleteEntity(ruleControlId);
	}
	
}

 