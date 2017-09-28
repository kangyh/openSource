package com.heepay.manage.modules.settle.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.settle.entity.SettleChannelRecordHis;
import java.util.List;
import java.util.Map;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月10日下午1:58:12
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
public interface SettleChannelRecordHisDao extends CrudDao<SettleChannelRecordHis>{
    int deleteByPrimaryKey(Long hisId);

    int insert(SettleChannelRecordHis record);

    SettleChannelRecordHis selectByPrimaryKey(Long hisId);

    List<SettleChannelRecordHis> selectAll();

    int updateByPrimaryKey(SettleChannelRecordHis record);

	List<Map<String, Object>> findListToExcel(SettleChannelRecordHis settleChannelRecordHis);
}