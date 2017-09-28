/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.hgms.entity.HgmsBatchPayRecordDetail;
import com.heepay.manage.modules.hgms.entity.HgmsSummaryResult;

/**
 *
 * 描    述：批量代付明细表DAO接口
 *
 * 创 建 者： @author 牛俊鹏
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
public interface HgmsBatchPayRecordDetailDao extends CrudDao<HgmsBatchPayRecordDetail> {
    HgmsSummaryResult getHgmsSummaryResult(HgmsBatchPayRecordDetail hgmsBatchPayRecordDetail);
}