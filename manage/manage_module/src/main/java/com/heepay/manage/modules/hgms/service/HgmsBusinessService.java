/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.enums.HgmsStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsBusinessDao;
import com.heepay.manage.modules.hgms.dao.HgmsServiceItemDao;
import com.heepay.manage.modules.hgms.dao.HgmsValidContractDao;
import com.heepay.manage.modules.hgms.entity.HgmsBusiness;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * 描    述：业务管理Service
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-10 15:58:00
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
public class HgmsBusinessService extends CrudService<HgmsBusinessDao, HgmsBusiness> {

    @Autowired
    private HgmsBusinessDao hgmsBusinessDao;
    @Autowired
    private HgmsServiceItemDao hgmsServiceItemDao;
    @Autowired
    private HgmsValidContractDao hgmsValidContractDao;

    public HgmsBusiness get(String id) {
        return super.get(id);
    }

    public List<HgmsBusiness> findList(HgmsBusiness hgmsBusiness) {
        return super.findList(hgmsBusiness);
    }

    public Page<HgmsBusiness> findPage(Page<HgmsBusiness> page, HgmsBusiness hgmsBusiness) {
        return super.findPage(page, hgmsBusiness);
    }

    /**
     * 保存业务类型
     *
     * @param hgmsBusiness
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(HgmsBusiness hgmsBusiness) {
        if (ObjectUtils.isEmpty(hgmsBusiness.getBusinessId())) {
            hgmsBusiness.setInputuserId(UserUtils.getUser().getId());
            hgmsBusiness.setInputuserName(UserUtils.getUser().getName());
            hgmsBusiness.setCreatedTime(new Date());
        }
        hgmsBusiness.setStatus(HgmsStatus.ENABLE.getValue());
        //如果时修改业务类型，同时修改服务项和合约中的业务类型
        if (hgmsBusiness.getBusinessId() != null) {
            //根据businessId修改服务类型中的业务名称
            hgmsServiceItemDao.updateBusiName(hgmsBusiness);
            //根据businessId修改合约中的业务名称
            hgmsValidContractDao.updateBusiName(hgmsBusiness);
        }
        logger.info("保存业务类型成功：" + hgmsBusiness.toString());
        super.save(hgmsBusiness, false);
    }

    /**
     * 更新业务状态--同步修改改业务下面服务项的状态
     *
     * @param hgmsBusiness
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean status(HgmsBusiness hgmsBusiness) {
        boolean updateStatus = 1 == hgmsBusinessDao.update(hgmsBusiness) && 1 == hgmsServiceItemDao.businessChangeStatus(hgmsBusiness.getBusinessId().toString(), hgmsBusiness.getStatus());
        logger.info("更新业务状态是否成功：{}，更新后的业务类型详情：{}",updateStatus, hgmsBusiness.toString());
        return updateStatus;
    }

    /**
     * 根据服务项名称查询是否重复
     *
     * @param businessName
     * @return
     */
    public HgmsBusiness selectByName(String businessName) {
        return hgmsBusinessDao.selectByName(businessName);
    }
}