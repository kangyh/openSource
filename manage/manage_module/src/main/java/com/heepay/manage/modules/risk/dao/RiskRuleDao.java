package com.heepay.manage.modules.risk.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.risk.entity.RiskRule;
/**
 * 
 *
 * 描    述：规则Dao
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月29日 下午8:53:33
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
public interface RiskRuleDao extends CrudDao<RiskRule> {

	/**
	 * 
	 * @方法说明：查询去重的规则
	 * @时间：2016年11月30日 上午10:17:24
	 * @创建人：wangdong
	 */
	List<RiskRule> findDistructRule();

	/**
	 * 
	 * @方法说明：获取实体类
	 * @时间：2016年12月5日 下午3:06:17
	 * @创建人：wangdong
	 */
	RiskRule get(Long id);

}
