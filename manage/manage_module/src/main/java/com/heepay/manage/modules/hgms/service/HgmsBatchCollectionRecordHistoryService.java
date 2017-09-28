/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsBatchCollectionRecordHistoryDao;
import com.heepay.manage.modules.hgms.entity.HgmsBatchCollectionRecordHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描    述：批量收款汇总历史查询Service
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
public class HgmsBatchCollectionRecordHistoryService extends CrudService<HgmsBatchCollectionRecordHistoryDao, HgmsBatchCollectionRecordHistory> {

    public HgmsBatchCollectionRecordHistory get(String id) {
        return super.get(id);
    }

    public List<HgmsBatchCollectionRecordHistory> findList(HgmsBatchCollectionRecordHistory hgmsBatchCollectionRecordHistory) {
        return super.findList(hgmsBatchCollectionRecordHistory);
    }

    public Page<HgmsBatchCollectionRecordHistory> findPage(Page<HgmsBatchCollectionRecordHistory> page, HgmsBatchCollectionRecordHistory hgmsBatchCollectionRecordHistory) {
        page.setOrderBy("a.create_time desc");
        return super.findPage(page, hgmsBatchCollectionRecordHistory);
    }

    @Transactional(readOnly = false)
    public void save(HgmsBatchCollectionRecordHistory hgmsBatchCollectionRecordHistory) {
        super.save(hgmsBatchCollectionRecordHistory, false);
    }

    @Transactional(readOnly = false)
    public void delete(HgmsBatchCollectionRecordHistory hgmsBatchCollectionRecordHistory) {
        super.delete(hgmsBatchCollectionRecordHistory);
    }

}