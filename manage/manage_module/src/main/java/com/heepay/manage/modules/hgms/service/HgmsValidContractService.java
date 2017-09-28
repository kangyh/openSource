/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsValidContractDao;
import com.heepay.manage.modules.hgms.entity.HgmsValidContract;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 描    述：有效合约Service
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-06-03 16:49:56
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
public class HgmsValidContractService extends CrudService<HgmsValidContractDao, HgmsValidContract> {

    @Autowired
    private HgmsValidContractDao hgmsValidContractDao;

    public HgmsValidContract get(String id) {
        return super.get(id);
    }

    public List<HgmsValidContract> findList(HgmsValidContract hgmsValidContract) {
        return super.findList(hgmsValidContract);
    }

    public Page<HgmsValidContract> findPage(Page<HgmsValidContract> page, HgmsValidContract hgmsValidContract) {
        return super.findPage(page, hgmsValidContract);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(HgmsValidContract hgmsValidContract) {
        super.save(hgmsValidContract, false);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(HgmsValidContract hgmsValidContract) {
        super.delete(hgmsValidContract);
    }

    /**
     * 修改合约状态
     *
     * @param hgmsValidContract
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void status(HgmsValidContract hgmsValidContract) {
        hgmsValidContractDao.status(hgmsValidContract);
    }

    /**
     * 更新合约状态
     *
     * @param hgmsValidContract
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(HgmsValidContract hgmsValidContract) {
        hgmsValidContractDao.update(hgmsValidContract);
    }

    /**
     * 合约的法务审核
     *
     * @param hgmsValidContract
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean legalAudit(HgmsValidContract hgmsValidContract) {
        hgmsValidContract.setLegalAuditTime(new Date());
        hgmsValidContract.setLegalAuditor(UserUtils.getUser().getId());
        return 1 == hgmsValidContractDao.legalAudit(hgmsValidContract);
    }

    /**
     * 合约法务审核失败修改合约
     *
     * @param hgmsValidContract
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean legalUpdate(HgmsValidContract hgmsValidContract) {
        hgmsValidContract.setOpAuditTime(new Date());
        hgmsValidContract.setOpAuditor(UserUtils.getUser().getId());
        hgmsValidContract.setLegalAuditStatus(RouteStatus.AUDING.getValue());
        return 1 == hgmsValidContractDao.legalUpdate(hgmsValidContract);
    }

    /**
     * 合约的风控审核
     *
     * @param hgmsValidContract
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean rcAudit(HgmsValidContract hgmsValidContract) {
        hgmsValidContract.setRcAuditTime(new Date());
        hgmsValidContract.setRcAuditor(UserUtils.getUser().getId());
        return 1 == hgmsValidContractDao.rcAudit(hgmsValidContract);
    }

    /**
     * 合约风控审核失败修改合约
     *
     * @param hgmsValidContract
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean rcUpdate(HgmsValidContract hgmsValidContract) {
        hgmsValidContract.setOpAuditTime(new Date());
        hgmsValidContract.setOpAuditor(UserUtils.getUser().getId());
        hgmsValidContract.setRcAuditStatus(RouteStatus.AUDING.getValue());
        return 1 == hgmsValidContractDao.rcUpdate(hgmsValidContract);
    }

    public List<HgmsValidContract> getByBusId(Long businessId) {
        HgmsValidContract hgmsValidContract = new HgmsValidContract();
        hgmsValidContract.setBusinessId(businessId);
        return hgmsValidContractDao.findList(hgmsValidContract);
    }
}