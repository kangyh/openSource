package com.heepay.manage.modules.reconciliation.service;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.dao.SettleRegexControlDao;
import com.heepay.manage.modules.reconciliation.entity.SettleRegexControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/***
 * 
* 
* 描    述：正则控制表
*
* 创 建 者： wangl
* 创建时间：  2017年1月17日下午5:01:44
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
public class SettleRegexControlService extends CrudService<SettleRegexControlDao, SettleRegexControl> {

	@Autowired
	SettleRegexControlDao settleRegexControlDao;

	/**
	 * 
	 * @方法说明：入库操作
	 * @时间：2017年1月17日下午5:10:00
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int saveEntity(SettleRegexControl record){
		
		return settleRegexControlDao.saveEntity(record);
		
	}
	/**
	 * @方法说明：根据主键和key查询出符合的条件
	 * @时间：2017年1月18日上午11:50:21
	 * @创建人：wangl
	 */
	public List<SettleRegexControl> getEntityByList(SettleRegexControl settleRegexControl) {
		
		return settleRegexControlDao.getEntityByList(settleRegexControl);
	}
public List<SettleRegexControl> getEntityByList2(SettleRegexControl settleRegexControl) {
		
		return settleRegexControlDao.getEntityByList2(settleRegexControl);
	}
	/**
	 * @方法说明：保存入库
	 * @时间：2017年1月18日下午5:01:45
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntity(SettleRegexControl settleRegexControl) {
		
		return settleRegexControlDao.updateEntity(settleRegexControl);
	}
	
	
	/**
	 * @方法说明：删除操作
	 * @时间：2017年1月19日上午10:25:58
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int deleteEntity(Long deleteId) {
		
		return settleRegexControlDao.deleteEntity(deleteId);
	}

	/**
	 * @方法说明：批量更新
	 * @时间：2017年1月19日下午2:10:11
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateList(List<SettleRegexControl> list) {
		
		return settleRegexControlDao.updateList(list);
	}
	
	
	/**
	 * @方法说明：根据规则主键级联删除
	 * @时间：2017年1月23日上午9:25:30
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int deleteEntityByRuleId(Long ruleId) {
		
		return settleRegexControlDao.deleteEntityByRuleId(ruleId);
	}
	@Transactional(readOnly = false)
	public int deleteEntityByRuleIdAndRuleType(SettleRegexControl settleRegexControl) {
		
		return settleRegexControlDao.deleteEntityByRuleIdAndRuleType(settleRegexControl);
	}
	
	/**
	 * @方法说明：批量插入
	 * @时间：2017年1月23日下午6:52:46
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void insetList(List<SettleRegexControl> list) {
		
		settleRegexControlDao.insetList(list);
	}

	/**
	 * @方法说明：批量插入时先删除后再插入
	 * @时间： 2017-05-15 10:03 AM
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int deleteByRule(Long ruleId, String ruleType) {

		return settleRegexControlDao.deleteByRule(ruleId,ruleType);
	}

}
