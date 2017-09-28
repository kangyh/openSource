package com.heepay.manage.modules.risk.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.risk.entity.RiskRuleDetail;
/**
 * 
 *
 * 描    述：规则明细Dao
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月29日 下午8:39:41
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
public interface RiskRuleDetailDao extends CrudDao<RiskRuleDetail> {

	/**
	 * 
	 * @方法说明：获取实体类
	 * @时间：2016年12月5日 下午3:07:46
	 * @创建人：wangdong
	 */
	RiskRuleDetail get(Long id);

}
