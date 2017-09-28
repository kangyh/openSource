/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsTemporaryCollectionRecordDetailDao;
import com.heepay.manage.modules.hgms.entity.HgmsTemporaryCollectionRecordDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：临时代收明细Service
 *
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-31 09:56:02
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
public class HgmsTemporaryCollectionRecordDetailService extends CrudService<HgmsTemporaryCollectionRecordDetailDao, HgmsTemporaryCollectionRecordDetail> {

    public HgmsTemporaryCollectionRecordDetail get(String id) {
        return super.get(id);
    }

    public List<HgmsTemporaryCollectionRecordDetail> findList(HgmsTemporaryCollectionRecordDetail hgmsTemporaryCollectionRecordDetail) {
        return super.findList(hgmsTemporaryCollectionRecordDetail);
    }

    public Page<HgmsTemporaryCollectionRecordDetail> findPage(Page<HgmsTemporaryCollectionRecordDetail> page, HgmsTemporaryCollectionRecordDetail hgmsTemporaryCollectionRecordDetail) {
        return super.findPage(page, hgmsTemporaryCollectionRecordDetail);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(HgmsTemporaryCollectionRecordDetail hgmsTemporaryCollectionRecordDetail) {
        super.save(hgmsTemporaryCollectionRecordDetail, false);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(HgmsTemporaryCollectionRecordDetail hgmsTemporaryCollectionRecordDetail) {
        super.delete(hgmsTemporaryCollectionRecordDetail);
    }

}