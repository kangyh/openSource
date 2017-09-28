package com.heepay.manage.modules.merchant.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.merchant.vo.ProductNetGroup;

import java.util.List;

/**
 * 描述：
 * <p>
 * 创建者  ly
 * 创建时间 2017-04-10-20:20
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
public interface ProductNetGroupCService {

    public ProductNetGroup get(String id);

    public List<ProductNetGroup> findList(ProductNetGroup productNetGroup);

    public Page<ProductNetGroup> findPage(Page<ProductNetGroup> page, ProductNetGroup productNetGroup);

    public void save(ProductNetGroup productNetGroup,boolean flag);

    public void delete(ProductNetGroup productNetGroup);

    public ProductNetGroup getByCodeExist(ProductNetGroup productNetGroup);
}
