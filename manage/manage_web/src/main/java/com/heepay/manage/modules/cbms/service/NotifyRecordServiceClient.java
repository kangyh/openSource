/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.service;

import com.heepay.cbms.rpc.cbmsweb.module.CbmsNotifyRecordModel;
import com.heepay.cbms.rpc.cbmsweb.service.NotifyRecordService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述： 插入通知表记录
 * <p>
 * 创建者: niujunpeng
 * 创建时间: 2017-08-08
 * 创建描述: 插入通知表记录
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
public class NotifyRecordServiceClient extends BaseClientDistribute {
    private static final String SERVICENAME = "notifyRecordServiceImpl";
    private static final String NODENAME ="cbms_server_web";
    private static final Logger log = LogManager.getLogger();
    @Resource(name="cbmsClient")
    private ThriftClientProxy thriftClientProxy;

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }
    @Override
    public ThriftClientProxy getClientProxy() {
        return thriftClientProxy;
    }

    private NotifyRecordService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new NotifyRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
    }
    /**
     * 插入或更新通知记录
     * @param cbmsNotifyRecordModel
     */
    public Boolean insertOrUpdate(CbmsNotifyRecordModel cbmsNotifyRecordModel){
        Boolean result;
        try {
            result=getClient().insertOrUpdate(cbmsNotifyRecordModel);
        } catch (TException e) {
            log.info("插入或更新通知记录失败",e);
            return false;
        }finally {
            this.close();
        }
        log.info("插入或更新通知记录成功");
        return result;
    }
}
