/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsBatchPayDetailHistoryDao;
import com.heepay.manage.modules.hgms.entity.HgmsBatchPayDetailHistory;
import com.heepay.manage.modules.hgms.entity.HgmsSummaryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描    述：批量代付明细历史表Service
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
public class HgmsBatchPayDetailHistoryService extends CrudService<HgmsBatchPayDetailHistoryDao, HgmsBatchPayDetailHistory> {
    @Autowired
    private HgmsBatchPayDetailHistoryDao hgmsBatchPayDetailHistoryDao;

    public HgmsBatchPayDetailHistory get(String id) {
        return super.get(id);
    }

    public List<HgmsBatchPayDetailHistory> findList(HgmsBatchPayDetailHistory hgmsBatchPayDetailHistory) {
        return super.findList(hgmsBatchPayDetailHistory);
    }

    public Page<HgmsBatchPayDetailHistory> findPage(Page<HgmsBatchPayDetailHistory> page, HgmsBatchPayDetailHistory hgmsBatchPayDetailHistory) {
        page.setOrderBy("a.create_time desc");
        return super.findPage(page, hgmsBatchPayDetailHistory);
    }

    @Transactional(readOnly = false)
    public void save(HgmsBatchPayDetailHistory hgmsBatchPayDetailHistory) {
        super.save(hgmsBatchPayDetailHistory, false);
    }

    @Transactional(readOnly = false)
    public void delete(HgmsBatchPayDetailHistory hgmsBatchPayDetailHistory) {
        super.delete(hgmsBatchPayDetailHistory);
    }

    public HgmsSummaryResult getHgmsSummaryResult(HgmsBatchPayDetailHistory hgmsBatchCollectionHistory) {
        return hgmsBatchPayDetailHistoryDao.getHgmsSummaryResult(hgmsBatchCollectionHistory);
    }
}