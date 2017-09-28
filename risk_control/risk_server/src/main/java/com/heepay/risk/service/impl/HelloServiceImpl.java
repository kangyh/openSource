package com.heepay.risk.service.impl;

import com.heepay.rpc.risk.service.HelloService;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**
 * 描    述：风控系统
 * <p>
 * 创 建 者：dongzhengjiang E-mail:dongzj@9186.com
 * 创建时间： 2017-09-20 10:12
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
@RpcService(name = "helloServiceImpl" , processor = HelloService.Processor.class)
public class HelloServiceImpl implements HelloService.Iface {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String sayHello() throws TException {

        //long ts = System.currentTimeMillis();

        //logger.info("PaymentServer心跳检测");

        //long te = System.currentTimeMillis();
        //logger.info("统计HelloServiceImpl.sayHello 运行时间:{}", (te - ts));

        return "OK";
    }
}
