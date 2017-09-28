/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.rpc.client;

import com.heepay.hgms.rpc.hgmsweb.module.CutDataRequest;
import com.heepay.hgms.rpc.hgmsweb.module.CutDataResult;
import com.heepay.hgms.rpc.hgmsweb.service.CutDataByDayService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述：手动进行日切更新
 * <p>
 * 创建者: yangzd
 * 创建时间: 2017-04-21-16:14
 * 创建描述: 虚拟账户互转绑定上下级关系
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
@Service
public class CutDataByDayClient extends BaseClientDistribute {

    private static final String SERVICENAME = "cutDataByDayServiceImpl";
    private static final String NODENAME = "hgms_server_web";
    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "hgmsClient")
    private ThriftClientProxy clientProxy;
    @Override
    public ThriftClientProxy getClientProxy() {
        return clientProxy;
    }

    @Override
    public void setServiceName(){
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }

    public CutDataByDayService.Client getClient(){
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new CutDataByDayService.Client(ClientThreadLocal.getInstance().getProtocol());
    }


    /**
     * 进行日切操作，日切实现方法
     * @param cutDataRequest
     *
    */
   public CutDataResult doCutData(CutDataRequest cutDataRequest){
        CutDataResult result = new CutDataResult();
        try {
            logger.info("进行日切操作开始,参数为{}",cutDataRequest.toString());
            result=  getClient().doCutData(cutDataRequest);
            logger.info("进行日切操作返回消息:"+result == null ? "":result.toString());
        } catch (TException e) {
            logger.error("进行日切操作失败:{}",e);
            e.printStackTrace();
        }finally {
           this.close();
        }
        return result;
    }




}
