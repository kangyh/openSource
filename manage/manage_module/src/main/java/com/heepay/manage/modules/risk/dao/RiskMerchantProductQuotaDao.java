package com.heepay.manage.modules.risk.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.risk.entity.RiskMerchantProductQuota;
import com.heepay.manage.modules.risk.entity.RiskOrder;
/**
 * 
 *
 * 描    述：商户产品限额Dao
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月18日 下午4:57:28
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
public interface RiskMerchantProductQuotaDao extends CrudDao<RiskMerchantProductQuota> {

	/**
	 * 
	 * @方法说明：验证是否存在相同商户号，产品编码，账户类型，银行卡类型
	 * @时间：2016年11月22日 上午10:08:58
	 * @创建人：wangdong
	 */
	Integer findRiskMerchantProductQuotaExist(RiskMerchantProductQuota riskMerchantProductQuota);

	/**
	 * 
	 * @方法说明：去重查询更新人
	 * @时间：2016年11月29日 上午11:45:19
	 * @创建人：wangdong
	 */
	List<RiskOrder> findUpdateAuthor();

	/**
	 * 
	 * @方法说明：获取实体类
	 * @时间：2016年12月5日 下午3:09:29
	 * @创建人：wangdong
	 */
	RiskMerchantProductQuota get(Long id);

}
