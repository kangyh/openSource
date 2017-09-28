/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsBatchCollectionRecordDetailDao;
import com.heepay.manage.modules.hgms.entity.HgmsBatchCollectionRecordDetail;
import com.heepay.manage.modules.hgms.entity.HgmsSummaryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描    述：批量收款记录明细表Service
 * <p>
 * 创 建 者： @author 牛俊鹏
 * 创建时间：
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
@Service
@Transactional(readOnly = true)
public class HgmsBatchCollectionRecordDetailService extends CrudService<HgmsBatchCollectionRecordDetailDao, HgmsBatchCollectionRecordDetail> {
    @Autowired
    private HgmsBatchCollectionRecordDetailDao hgmsBatchCollectionRecordDetailDao;

    public HgmsBatchCollectionRecordDetail get(String id) {
        return super.get(id);
    }

    public List<HgmsBatchCollectionRecordDetail> findList(HgmsBatchCollectionRecordDetail hgmsBatchCollectionRecordDetail) {
        return super.findList(hgmsBatchCollectionRecordDetail);
    }

    public Page<HgmsBatchCollectionRecordDetail> findPage(Page<HgmsBatchCollectionRecordDetail> page, HgmsBatchCollectionRecordDetail hgmsBatchCollectionRecordDetail) {
        page.setOrderBy("a.create_time desc");
        return super.findPage(page, hgmsBatchCollectionRecordDetail);
    }

    @Transactional(readOnly = false)
    public void save(HgmsBatchCollectionRecordDetail hgmsBatchCollectionRecordDetail) {
        super.save(hgmsBatchCollectionRecordDetail, false);
    }

    @Transactional(readOnly = false)
    public void delete(HgmsBatchCollectionRecordDetail hgmsBatchCollectionRecordDetail) {
        super.delete(hgmsBatchCollectionRecordDetail);
    }

    public HgmsSummaryResult getHgmsSummaryResult(HgmsBatchCollectionRecordDetail hgmsBatchCollectionHistory) {
        return hgmsBatchCollectionRecordDetailDao.getHgmsSummaryResult(hgmsBatchCollectionHistory);
    }
}