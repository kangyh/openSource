package com.heepay.manage.modules.riskLogs.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.riskLogs.entity.RiskLogs;


/***
 * 
* 
* 描    述：操作日志表
*
* 创 建 者：wangl
* 创建时间：  Dec 30, 20161:08:20 PM
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
public interface RiskLogsDao extends CrudDao<RiskLogs> {


    /**
     * 
     * @方法说明：入库操作
     * @时间：Dec 30, 2016
     * @创建人：wangl
     */
    int saveEntity(RiskLogs riskLogs);
}