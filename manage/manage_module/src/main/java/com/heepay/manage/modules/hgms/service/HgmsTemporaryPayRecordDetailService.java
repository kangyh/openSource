/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsTemporaryPayRecordDetailDao;
import com.heepay.manage.modules.hgms.entity.HgmsTemporaryPayRecordDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描    述：临时代付明细Service
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-31 09:56:11
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
public class HgmsTemporaryPayRecordDetailService extends CrudService<HgmsTemporaryPayRecordDetailDao, HgmsTemporaryPayRecordDetail> {

    public HgmsTemporaryPayRecordDetail get(String id) {
        return super.get(id);
    }

    public List<HgmsTemporaryPayRecordDetail> findList(HgmsTemporaryPayRecordDetail hgmsTemporaryPayRecordDetail) {
        return super.findList(hgmsTemporaryPayRecordDetail);
    }

    public Page<HgmsTemporaryPayRecordDetail> findPage(Page<HgmsTemporaryPayRecordDetail> page, HgmsTemporaryPayRecordDetail hgmsTemporaryPayRecordDetail) {
        return super.findPage(page, hgmsTemporaryPayRecordDetail);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(HgmsTemporaryPayRecordDetail hgmsTemporaryPayRecordDetail) {
        super.save(hgmsTemporaryPayRecordDetail, false);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(HgmsTemporaryPayRecordDetail hgmsTemporaryPayRecordDetail) {
        super.delete(hgmsTemporaryPayRecordDetail);
    }

}