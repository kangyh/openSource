/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsRegularRuleDao;
import com.heepay.manage.modules.hgms.entity.HgmsRegularRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描    述：规则表Service
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-08-10 21:48:58
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
public class HgmsRegularRuleService extends CrudService<HgmsRegularRuleDao, HgmsRegularRule> {

    @Autowired
    private HgmsRegularRuleDao hgmsRegularRuleDao;

    public HgmsRegularRule get(String id) {
        return super.get(id);
    }

    public List<HgmsRegularRule> findList(HgmsRegularRule hgmsRegularRule) {
        return super.findList(hgmsRegularRule);
    }

    public Page<HgmsRegularRule> findPage(Page<HgmsRegularRule> page, HgmsRegularRule hgmsRegularRule) {
        return super.findPage(page, hgmsRegularRule);
    }

    @Transactional(readOnly = false)
    public void save(HgmsRegularRule hgmsRegularRule) {
        super.save(hgmsRegularRule, false);
    }

    @Transactional(readOnly = false)
    public void delete(HgmsRegularRule hgmsRegularRule) {
        super.delete(hgmsRegularRule);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int update(HgmsRegularRule hgmsRegularRule) {
        return hgmsRegularRuleDao.update(hgmsRegularRule);
    }

    /**
     * 修改规则管理员状态
     *
     * @param hgmsRegularRule
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int status(HgmsRegularRule hgmsRegularRule) {
        return hgmsRegularRuleDao.status(hgmsRegularRule);
    }
}