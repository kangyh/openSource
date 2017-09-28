package com.heepay.manage.modules.merchant.service.impl;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.ProductDao;
import com.heepay.manage.modules.merchant.dao.ProductNetGroupDao;
import com.heepay.manage.modules.merchant.service.ProductNetGroupCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.merchant.vo.ProductNetGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述：
 * <p>
 * 创建者  ly
 * 创建时间 2017-04-10-20:22
 * 创建描述：
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Service
@Transactional(readOnly = true)
public class ProductNetGroupCServiceImpl extends CrudService<ProductNetGroupDao, ProductNetGroup> implements ProductNetGroupCService {

    @Autowired
    private ProductNetGroupDao productNetGroupDao;

    @Override
    public ProductNetGroup getByCodeExist(ProductNetGroup productNetGroup) {
        return productNetGroupDao.getByCodeExist(productNetGroup);
    }
}
