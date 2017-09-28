package com.heepay.manage.modules.reconciliation.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecordHis;

/**
 * 
 *
 * 描    述：差异单历史表
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月13日 下午5:26:49
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
public interface SettleDifferRecordHisDao extends CrudDao<SettleDifferRecordHis> {

	List<Map<String, Object>> findListExcel(SettleDifferRecordHis settleDifferRecordHis);

	SettleDifferRecordHis getTransTypeByTransNo(String transNo);

}
