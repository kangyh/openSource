package com.heepay.manage.modules.risk.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.risk.entity.RiskOrder;
/**
 * 
 *
 * 描    述：风险订单Dao
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月22日 下午2:08:56
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
public interface RiskOrderDao extends CrudDao<RiskOrder> {

	/**
	 * 
	 * @方法说明：风险订单信息导出SQL
	 * @时间：2016年11月22日 下午2:44:30
	 * @创建人：wangdong
	 */
	List<Map<String, Object>> findListExcel(RiskOrder riskOrder);

	/**
	 * 
	 * @方法说明：去重查询商户公司名称
	 * @时间：2016年11月29日 上午11:35:18
	 * @创建人：wangdong
	 */
	List<RiskOrder> findMerchantName();

	/**
	 * 
	 * @方法说明：获取实体类
	 * @时间：2016年12月5日 下午3:09:01
	 * @创建人：wangdong
	 */
	RiskOrder get(Long id);

}
