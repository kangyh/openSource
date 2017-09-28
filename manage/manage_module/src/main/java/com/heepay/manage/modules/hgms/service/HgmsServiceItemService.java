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
import com.heepay.manage.modules.hgms.entity.HgmsProduct;
import com.heepay.manage.modules.hgms.entity.HgmsServiceItem;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描    述：业务服务项Service
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-10 15:57:50
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
public class HgmsServiceItemService extends CrudService<HgmsServiceItemDao, HgmsServiceItem> {

    @Autowired
    private HgmsServiceItemDao hgmsServiceItemDao;
    @Autowired
    private HgmsBusinessDao hgmsBusinessDao;
    @Autowired
    private HgmsValidContractDao hgmsValidContractDao;

    public HgmsServiceItem get(String id) {
        return super.get(id);
    }

    public List<HgmsServiceItem> findList(HgmsServiceItem hgmsServiceItem) {
        return super.findList(hgmsServiceItem);
    }

    public Page<HgmsServiceItem> findPage(Page<HgmsServiceItem> page, HgmsServiceItem hgmsServiceItem) {
        return super.findPage(page, hgmsServiceItem);
    }

    /**
     * 保存服务项
     *
     * @param hgmsServiceItem
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(HgmsServiceItem hgmsServiceItem) {
        if (ObjectUtils.isEmpty(hgmsServiceItem.getServiceId())) {
            if (!StringUtils.isEmpty(hgmsServiceItem.getBusinessId())) {
                HgmsBusiness hgmsBusiness = hgmsBusinessDao.get(hgmsServiceItem.getBusinessId());
                hgmsServiceItem.setBusinessName(hgmsBusiness.getBusinessName());
                hgmsServiceItem.setBusinessStatus(hgmsBusiness.getStatus());
            }
            hgmsServiceItem.setInputuserId(UserUtils.getUser().getId());
            hgmsServiceItem.setInputuserName(UserUtils.getUser().getName());
            hgmsServiceItem.setCreatedTime(new Date());
            hgmsServiceItem.setStatus(HgmsStatus.ENABLE.getValue());
            //如果时修改服务类型，同时修改合约中的服务类型
        } else if (!ObjectUtils.isEmpty(hgmsServiceItem.getServiceId())) {
            //根据businessId修改合约中的业务名称
            hgmsValidContractDao.updateServiceName(hgmsServiceItem);
        }
        super.save(hgmsServiceItem, false);

    }

    /**
     * 将product类中的name和code提取出来
     *
     * @param list
     * @return
     */
    public List<HgmsProduct> transProduct(List<Product> list) {
        List<HgmsProduct> productList = new ArrayList<>();
        for (Product product : list) {
            HgmsProduct hgmsProduct = new HgmsProduct();
            hgmsProduct.setProductCode(product.getCode());
            hgmsProduct.setProductName(product.getName());
            productList.add(hgmsProduct);
        }
        return productList;
    }

    /**
     * 更新服务项
     *
     * @param hgmsServiceItem
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean status(HgmsServiceItem hgmsServiceItem) {
        return 1 == hgmsServiceItemDao.update(hgmsServiceItem);
    }

    /**
     * 更具businessId和servicName查询 该业务类型下面是否有重复的服务名
     *
     * @param hgmsServiceItem
     * @return
     */
    public HgmsServiceItem selectByName(HgmsServiceItem hgmsServiceItem) {
        return hgmsServiceItemDao.selectByName(hgmsServiceItem);
    }
}