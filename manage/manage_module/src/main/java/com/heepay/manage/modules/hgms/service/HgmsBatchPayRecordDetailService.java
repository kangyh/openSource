/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsBatchPayRecordDetailDao;
import com.heepay.manage.modules.hgms.entity.HgmsBatchPayRecordDetail;
import com.heepay.manage.modules.hgms.entity.HgmsSummaryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：批量代付明细表Service
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
@Service
@Transactional(readOnly = true)
public class HgmsBatchPayRecordDetailService extends CrudService<HgmsBatchPayRecordDetailDao, HgmsBatchPayRecordDetail> {
    @Autowired
    private HgmsBatchPayRecordDetailDao hgmsBatchPayRecordDetailDao;

    public HgmsBatchPayRecordDetail get(String id) {
        return super.get(id);
    }

    public List<HgmsBatchPayRecordDetail> findList(HgmsBatchPayRecordDetail hgmsBatchPayRecordDetail) {
        return super.findList(hgmsBatchPayRecordDetail);
    }

    public Page<HgmsBatchPayRecordDetail> findPage(Page<HgmsBatchPayRecordDetail> page, HgmsBatchPayRecordDetail hgmsBatchPayRecordDetail) {
        page.setOrderBy("a.create_time desc");
        return super.findPage(page, hgmsBatchPayRecordDetail);
    }

    @Transactional(readOnly = false)
    public void save(HgmsBatchPayRecordDetail hgmsBatchPayRecordDetail) {
        super.save(hgmsBatchPayRecordDetail, false);
    }

    @Transactional(readOnly = false)
    public void delete(HgmsBatchPayRecordDetail hgmsBatchPayRecordDetail) {
        super.delete(hgmsBatchPayRecordDetail);
    }

    public HgmsSummaryResult getHgmsSummaryResult(HgmsBatchPayRecordDetail hgmsBatchCollectionHistory) {
        return hgmsBatchPayRecordDetailDao.getHgmsSummaryResult(hgmsBatchCollectionHistory);
    }
}