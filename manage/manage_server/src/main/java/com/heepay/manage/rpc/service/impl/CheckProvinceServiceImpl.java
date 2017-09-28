package com.heepay.manage.rpc.service.impl;

import com.heepay.manage.modules.route.service.LineBankNumberService;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * 
* 
* 描    述：根据联行号查询省份
*
* 创 建 者： wangl
* 创建时间： 2017-07-28 17:29
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
@Component
@RpcService(name = "checkProvinceServiceImpl" , processor = com.heepay.manage.rpc.service.CheckProvinceService.Processor.class)
public class CheckProvinceServiceImpl implements com.heepay.manage.rpc.service.CheckProvinceService.Iface{

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private LineBankNumberService lineBankNumberService;

    /**
     * @方法说明：根据联行号查询省份
     * @时间： 2017-07-28 17:30
     * @创建人：wangl
     */
    @Override
    public String selectProvince(String value) throws TException {

        logger.info("根据联行号查询省份--->{条件}",value);
        //根据联行号查询省份
        String lineBankNumber = lineBankNumberService.selectProvince(value);
        logger.info("根据联行号查询省份--->{结果}",lineBankNumber);
        return lineBankNumber;
    }
    
}
