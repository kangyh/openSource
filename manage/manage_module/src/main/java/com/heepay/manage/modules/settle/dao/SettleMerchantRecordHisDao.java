package com.heepay.manage.modules.settle.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.settle.entity.SettleMerchantRecordHis;
import java.util.List;
import java.util.Map;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月10日下午1:58:39
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
public interface SettleMerchantRecordHisDao extends CrudDao<SettleMerchantRecordHis>{
    int deleteByPrimaryKey(Long hisId);

    int insert(SettleMerchantRecordHis record);

    SettleMerchantRecordHis selectByPrimaryKey(Long hisId);

    List<SettleMerchantRecordHis> selectAll();

    int updateByPrimaryKey(SettleMerchantRecordHis record);

	List<Map<String, Object>> findListToExcel(SettleMerchantRecordHis settleMerchantRecordHis);
}