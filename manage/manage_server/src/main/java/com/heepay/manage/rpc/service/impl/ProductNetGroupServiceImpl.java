package com.heepay.manage.rpc.service.impl;

import com.heepay.manage.modules.merchant.dao.ProductNetGroupDao;
import com.heepay.manage.modules.merchant.vo.ProductNetGroup;
import com.heepay.manage.rpc.service.HelloService;
import com.heepay.manage.rpc.service.ProductNetGroupService;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcService(name = "productNetGroupService" , processor = ProductNetGroupService.Processor.class)
public class ProductNetGroupServiceImpl implements ProductNetGroupService.Iface{

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ProductNetGroupDao productNetGroupDao;

    @Override
    public String getNetGroup(String productCode) throws TException {
        ProductNetGroup productNetGroup = new ProductNetGroup();
        productNetGroup.setProductCode(productCode);
        ProductNetGroup productNetGroupFind = productNetGroupDao.getByCodeExist(productNetGroup);
        if(null != productNetGroupFind){
            logger.info("获取{}产品组id成功,组id为:{}",productCode,productNetGroupFind.getNetGroup());
            return productNetGroupFind.getNetGroup();
        }else{
            logger.info("没有获取{}到组id",productCode);
            return "";
        }
    }
}
