/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.hgms.entity.HgmsSynDataTaskRecord;

import java.util.List;

/**
 *
 * 描    述：同步数据任务记录DAO接口
 *
 * 创 建 者： @author 郭正新
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
public interface HgmsSynDataTaskRecordDao extends CrudDao<HgmsSynDataTaskRecord> {

    List<HgmsSynDataTaskRecord> checkYesterDayTaskStatus(HgmsSynDataTaskRecord taskRecord);
}