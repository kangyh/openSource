package com.heepay.manage.modules.differences.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.differences.entity.SettleProfitBath;

/***
 * 
 * 
 * 描    述：
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
public interface SettleProfitBathDao extends CrudDao<SettleProfitBath> {

	List<Map<String, Object>> findListExcel(SettleProfitBath settleProfitBath);


}
