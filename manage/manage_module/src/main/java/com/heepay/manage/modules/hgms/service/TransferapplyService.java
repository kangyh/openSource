/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.TransferapplyDao;
import com.heepay.manage.modules.hgms.entity.Transferapply;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描    述：转账审核Service
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
public class TransferapplyService extends CrudService<TransferapplyDao, Transferapply> {

    public Transferapply get(String id) {
        return super.get(id);
    }

    public List<Transferapply> findList(Transferapply transferapply) {
        return super.findList(transferapply);
    }

    public Page<Transferapply> findPage(Page<Transferapply> page, Transferapply transferapply) {
        page.setOrderBy("a.create_time desc");
        return super.findPage(page, transferapply);
    }

    @Transactional(readOnly = false)
    public void save(Transferapply transferapply) {
        super.save(transferapply, false);
    }

    @Transactional(readOnly = false)
    public void delete(Transferapply transferapply) {
        super.delete(transferapply);
    }

}