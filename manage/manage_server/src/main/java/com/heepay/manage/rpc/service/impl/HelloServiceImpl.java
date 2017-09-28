package com.heepay.manage.rpc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.manage.rpc.service.HelloService;
import com.heepay.rpc.service.RpcService;

@Service
@RpcService(name = "helloServiceImpl" , processor = HelloService.Processor.class)
public class HelloServiceImpl implements HelloService.Iface{

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();
    
    @Override
    public String sayHello() throws TException {
        logger.info("ManageServer心跳检测");
        return "OK";
    }

}
