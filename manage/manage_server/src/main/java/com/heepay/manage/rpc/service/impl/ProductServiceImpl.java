package com.heepay.manage.rpc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.manage.modules.merchant.dao.ProductDao;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.rpc.service.ProductService;
import com.heepay.manage.rpc.service.ProductThrift;
import com.heepay.rpc.service.RpcService;

@Component
@RpcService(name = "productServiceImpl", processor = ProductService.Processor.class)
public class ProductServiceImpl implements ProductService.Iface{

	@Autowired
	private ProductDao productDao;
	
	@Override
	public int insert(ProductThrift product) throws TException {
		Product productVo = new Product();
		BeanUtils.copyProperties(product, productVo);
		productVo.setId(null);
		return productDao.insert(productVo);
	}

	@Override
	public List<ProductThrift> selectList() throws TException {
		List<Product> productVos = productDao.selectList();
		List<ProductThrift> products = new ArrayList<ProductThrift>();
		for(Product productVo : productVos){
		  ProductThrift product = new ProductThrift();
			BeanUtils.copyProperties(productVo, product);
			products.add(product);
			
		}
		return products;
	}

	@Override
	public int deleteByPrimaryKey(String id) throws TException {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ProductThrift product) throws TException {
		Product productVo = new Product();
		BeanUtils.copyProperties(product, productVo);
		productVo.setCreateDate(null);
		return productDao.update(productVo);
	}

	@Override
	public ProductThrift selectByPrimaryKey(String id) throws TException {
		Product productVo = productDao.get(id);
		ProductThrift product = new ProductThrift();
		BeanUtils.copyProperties(productVo, product);
		return product;
	}

}
