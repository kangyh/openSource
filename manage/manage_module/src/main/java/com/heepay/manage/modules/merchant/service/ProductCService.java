/**
 *  
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.Product;

/**
 * 产品Service
 * @author ly
 * @version V1.0
 */
public interface ProductCService{

    public Product get(String id);

    public List<Product> findList(Product product);

    public Page<Product> findPage(Page<Product> page, Product product);

    public void save(Product product);

    public void delete(Product product);

    public void status(Product product);

    public Product selectByCode(String code);

    public List<Product> findListChannel(Product product);

    public List<Product> getProductList(String businessType);

    public Product existByName(Product product);

    Page<Product> findCertifyPage(Page<Product> productPage, Product product);
}