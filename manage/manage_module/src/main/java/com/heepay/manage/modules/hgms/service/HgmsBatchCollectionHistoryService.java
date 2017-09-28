/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsBatchCollectionHistoryDao;
import com.heepay.manage.modules.hgms.entity.HgmsBatchCollectionHistory;
import com.heepay.manage.modules.hgms.entity.HgmsSummaryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：历史代收明细查询Service
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
public class HgmsBatchCollectionHistoryService extends CrudService<HgmsBatchCollectionHistoryDao, HgmsBatchCollectionHistory> {

    @Autowired
    private HgmsBatchCollectionHistoryDao hgmsBatchCollectionHistoryDao;

    public HgmsBatchCollectionHistory get(String id) {
        return super.get(id);
    }

    public List<HgmsBatchCollectionHistory> findList(HgmsBatchCollectionHistory hgmsBatchCollectionHistory) {
        return super.findList(hgmsBatchCollectionHistory);
    }

    public Page<HgmsBatchCollectionHistory> findPage(Page<HgmsBatchCollectionHistory> page, HgmsBatchCollectionHistory hgmsBatchCollectionHistory) {
        page.setOrderBy("a.create_time desc");
        return super.findPage(page, hgmsBatchCollectionHistory);
    }

    @Transactional(readOnly = false)
    public void save(HgmsBatchCollectionHistory hgmsBatchCollectionHistory) {
        super.save(hgmsBatchCollectionHistory, false);
    }

    @Transactional(readOnly = false)
    public void delete(HgmsBatchCollectionHistory hgmsBatchCollectionHistory) {
        super.delete(hgmsBatchCollectionHistory);
    }

    public HgmsSummaryResult getHgmsSummaryResult(HgmsBatchCollectionHistory hgmsBatchCollectionHistory) {
        return hgmsBatchCollectionHistoryDao.getHgmsSummaryResult(hgmsBatchCollectionHistory);
    }

}