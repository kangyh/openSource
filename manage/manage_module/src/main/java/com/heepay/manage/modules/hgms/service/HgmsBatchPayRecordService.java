/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsBatchPayRecordDao;
import com.heepay.manage.modules.hgms.entity.HgmsBatchPayRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：批量代付汇总表Service
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
public class HgmsBatchPayRecordService extends CrudService<HgmsBatchPayRecordDao, HgmsBatchPayRecord> {

    public HgmsBatchPayRecord get(String id) {
        return super.get(id);
    }

    public List<HgmsBatchPayRecord> findList(HgmsBatchPayRecord hgmsBatchPayRecord) {
        return super.findList(hgmsBatchPayRecord);
    }

    public Page<HgmsBatchPayRecord> findPage(Page<HgmsBatchPayRecord> page, HgmsBatchPayRecord hgmsBatchPayRecord) {
        page.setOrderBy("a.create_time desc");
        return super.findPage(page, hgmsBatchPayRecord);
    }

    @Transactional(readOnly = false)
    public void save(HgmsBatchPayRecord hgmsBatchPayRecord) {
        super.save(hgmsBatchPayRecord, false);
    }

    @Transactional(readOnly = false)
    public void delete(HgmsBatchPayRecord hgmsBatchPayRecord) {
        super.delete(hgmsBatchPayRecord);
    }

}