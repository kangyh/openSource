package com.heepay.manage.modules.risk.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.risk.entity.RiskProductQuota;
/**
 * 
 *
 * 描    述：产品限额Dao
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月18日 下午4:58:04
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
public interface RiskProductQuotaDao extends CrudDao<RiskProductQuota> {

	/**
	 * 
	 * @方法说明：查询是否存在相同产品和银行卡持卡人类型
	 * @时间：2016年11月21日 上午11:31:45
	 * @创建人：wangdong
	 */
	Integer findByRiskProductQuotaExist(RiskProductQuota riskProductQuota);

	/**
	 * 
	 * @方法说明：获取实体类
	 * @时间：2016年12月5日 下午3:08:19
	 * @创建人：wangdong
	 */
	RiskProductQuota get(Long id);

}
