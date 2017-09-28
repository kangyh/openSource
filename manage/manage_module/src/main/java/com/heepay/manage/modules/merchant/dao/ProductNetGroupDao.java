package com.heepay.manage.modules.merchant.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.merchant.vo.ProductNetGroup;

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
@MyBatisDao
public interface ProductNetGroupDao extends CrudDao<ProductNetGroup> {
    ProductNetGroup getByCodeExist(ProductNetGroup productNetGroup);
}
