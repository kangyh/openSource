/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.hgms.entity.HgmsOrderTaskRecord;

/**
 * 描    述：汇聚财定时任务DAO接口
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-31 09:54:53
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@MyBatisDao
public interface HgmsOrderTaskRecordDao extends CrudDao<HgmsOrderTaskRecord> {

    /**
     * 判断昨天的执行订单是否成功
     *
     * @param taskRecord
     * @return
     */
    HgmsOrderTaskRecord checkYesterDayExTaskStatus(HgmsOrderTaskRecord taskRecord);
}