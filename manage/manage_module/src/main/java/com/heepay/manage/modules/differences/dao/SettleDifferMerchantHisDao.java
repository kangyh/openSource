package com.heepay.manage.modules.differences.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.differences.entity.SettleDifferMerchantHis;

/**
 * 
 *
 * 描    述：商户侧差错账汇总历史表
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月13日 下午5:17:41
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
public interface SettleDifferMerchantHisDao extends CrudDao<SettleDifferMerchantHis> {

	List<Map<String, Object>> findListExcel(SettleDifferMerchantHis settleDifferMerchantHis);

}