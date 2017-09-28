/**
 *  
 */
package com.heepay.manage.modules.merchant.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import com.heepay.manage.common.persistence.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.ProductDao;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.sys.utils.BusinessInfoUtils;

/**
 *
 * 描 述：产品管理功能
 *
 * 创 建 者：ly 创建时间：2016-08-23 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者：ljh 审核时间：2016-09-01
 * 审核描述：47/55/58如果直接使用父类方法，子类中可不用重写，直接调用父类。本类中所有调用super的方法可不用重写；
 *
 */
@Service
@Transactional(readOnly = true)
public class ProductCServiceImpl extends CrudService<ProductDao, Product> implements ProductCService {

    @Autowired
    private ProductDao productDao;

    public Product selectByCode(String code) {
        return productDao.selectByCode(code);
    }

    @Transactional(readOnly = false)
    public void save(Product product) {
        if (StringUtils.isBlank(product.getCode())) {
            int size = productDao.count() + 1;
            DecimalFormat df = new DecimalFormat("00");
            String str2 = df.format(size);
            String code = "CP" + str2;
            product.setCode(code);
            product.setAuditStatus(RouteStatus.AUDING.getValue());
        }
        super.save(product, false);
        BusinessInfoUtils.delProductList();
    }

    @Transactional(readOnly = false)
    public void delete(Product product) {
        super.delete(product);
    }

    @Transactional(readOnly = false)
    public void status(Product product) {
        productDao.status(product);
        BusinessInfoUtils.delProductList();
        RedisUtil.getRedisUtil().saveProductVoRedis(product.getCode());
    }

    public List<Product> findListChannel(Product product) {
        return productDao.findListChannel(product);
    }

    public List<Product> getProductList(String businessType) {
        return productDao.getProductList(businessType);
    }

    @Override
    public Product existByName(Product product) {
        return productDao.existByName(product);
    }

    @Override
    public Page<Product> findCertifyPage(Page<Product> productPage, Product product) {
        product.setPage(productPage);
        productPage.setList(dao.findCertifyList(product));
        return productPage;
    }

}