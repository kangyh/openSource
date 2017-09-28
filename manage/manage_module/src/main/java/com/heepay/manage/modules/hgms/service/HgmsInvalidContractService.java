/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsInvalidContractDao;
import com.heepay.manage.modules.hgms.entity.HgmsInvalidContract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描    述：无效合约Service
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-06-03 16:50:01
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
public class HgmsInvalidContractService extends CrudService<HgmsInvalidContractDao, HgmsInvalidContract> {

    public HgmsInvalidContract get(String id) {
        return super.get(id);
    }

    public List<HgmsInvalidContract> findList(HgmsInvalidContract hgmsInvalidContract) {
        return super.findList(hgmsInvalidContract);
    }

    public Page<HgmsInvalidContract> findPage(Page<HgmsInvalidContract> page, HgmsInvalidContract hgmsInvalidContract) {
        return super.findPage(page, hgmsInvalidContract);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(HgmsInvalidContract hgmsInvalidContract) {
        super.save(hgmsInvalidContract, false);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(HgmsInvalidContract hgmsInvalidContract) {
        super.delete(hgmsInvalidContract);
    }

}