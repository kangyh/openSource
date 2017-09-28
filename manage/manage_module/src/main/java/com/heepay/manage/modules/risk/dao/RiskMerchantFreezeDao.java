package com.heepay.manage.modules.risk.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.risk.entity.RiskMerchantFreeze;

/***
 * 
* 
* 描    述：商户冻结/解冻表
*
* 创 建 者：wangl
* 创建时间：  Nov 18, 20167:02:45 PM
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
public interface RiskMerchantFreezeDao extends CrudDao<RiskMerchantFreeze> {

	/**
	 * 
	 * @方法说明：商户冻结规则添加
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	int saveEntity(RiskMerchantFreeze riskMerchantFreeze);

	int updateEntity(RiskMerchantFreeze riskMerchantFreeze);

	/**
	 * 
	 * @方法说明：根据id查询整个对象发给交易
	 * @时间：Nov 23, 2016
	 * @创建人：wangl
	 */
	RiskMerchantFreeze getEntity(int freezeId);

	/**
	 * 
	 * @方法说明：文件导出
	 * @时间：Nov 25, 2016
	 * @创建人：wangl
	 */
	List<Map<String, Object>> findListExcel(RiskMerchantFreeze riskMerchantFreeze);

	/**
	 * 
	 * @方法说明：将取出的交易订单号查询看有没有冻结记录
	 * @时间：Dec 5, 2016
	 * @创建人：wangl
	 */
	boolean getRemark1(String transNo2);
   
}