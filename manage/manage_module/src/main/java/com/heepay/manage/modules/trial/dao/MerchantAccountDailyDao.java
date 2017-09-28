/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.trial.entity.MerchantAccountDaily;

/**
 *
 * 描    述：试算平衡-账户日汇总DAO接口
 *
 * 创 建 者： @author 杨春龙
 * 创建时间：
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
public interface MerchantAccountDailyDao extends CrudDao<MerchantAccountDaily> {
	//根据商户id和账户类型查询账户
	public List<MerchantAccountDaily> findByMerchantIds(Map<String,Object> map);
	//查询所有账户的本期 ：期初余额  期末余额
	public List<MerchantAccountDaily> findSubjectFinanicalState(Map<String,Object> map);
	//根据科目类型 查询所有的数据
	public List<MerchantAccountDaily> findListBySubject(Map<String,Object> map);
	//根据科目类型 查询合计数据
	public List<MerchantAccountDaily> findSumListBySubject(MerchantAccountDaily merchantAccountDaily);
	
	
}