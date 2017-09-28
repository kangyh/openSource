/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;


import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsMerchantFeeManagementDao;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantFeeManagementObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class HgmsMerchantFeeManagementService extends CrudService<HgmsMerchantFeeManagementDao, HgmsMerchantFeeManagementObj> {
    @Autowired
    HgmsMerchantFeeManagementDao hgmsMerchantFeeManagementDao;

    public Page<HgmsMerchantFeeManagementObj> findPage(Page<HgmsMerchantFeeManagementObj> page, HgmsMerchantFeeManagementObj hgmsMerchantFeeManagementObj) {
        hgmsMerchantFeeManagementObj.setPage(page);
        page.setList(hgmsMerchantFeeManagementDao.findMonthlyList(hgmsMerchantFeeManagementObj));
        return page;
    }

    public HgmsMerchantFeeManagementObj findPaySummery(HgmsMerchantFeeManagementObj hgmsMerchantFeeManagementObj) {
        return hgmsMerchantFeeManagementDao.findPaySummery(hgmsMerchantFeeManagementObj);
    }

    public HgmsMerchantFeeManagementObj findCollectionSummery(HgmsMerchantFeeManagementObj hgmsMerchantFeeManagementObj) {
        return hgmsMerchantFeeManagementDao.findCollectionSummery(hgmsMerchantFeeManagementObj);
    }
}