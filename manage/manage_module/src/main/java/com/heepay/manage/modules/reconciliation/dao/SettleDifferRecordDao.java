package com.heepay.manage.modules.reconciliation.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecord;

/***
 * 
 * 
 * 描    述：差异记录表
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午3:29:16
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
public interface SettleDifferRecordDao extends CrudDao<SettleDifferRecord> {

	List<Map<String, Object>> findListExcel(SettleDifferRecord settleDifferRecord);

	SettleDifferRecord getEntityById(int differId);

	void updateTime(SettleDifferRecord settleDiffer);

	SettleDifferRecord getTransTypeByTransNo(String transNo);

    List<SettleDifferRecord> getListByPage(SettleDifferRecord settleDifferRecord);

    List<SettleDifferRecord> differRecordPage(SettleDifferRecord settleDifferRecord);

	SettleDifferRecord getSettleDifferRecordByTransNo(String transNo);
}
