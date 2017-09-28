/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.rpc.client;

import com.heepay.common.util.Constants;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.InnerTransRecordModel;
import com.heepay.rpc.payment.model.InnerTransResult;
import com.heepay.rpc.payment.service.InnerTransRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述：
 * <p>
 * 创建者: yangzd
 * 创建时间: 2017-04-11-13:47
 * 创建描述: 虚拟账户互转接口
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
public class InnerTransferClient extends BaseClientDistribute {
    private static final String SERVICENAME = "InnerTransRecordServiceImpl";

    private static final Logger log = LogManager.getLogger();
    @Resource(name = "paymentClient")
    private ThriftClientProxy thriftClientProxy;

    @Override
    public ThriftClientProxy getClientProxy() {
        return thriftClientProxy;
    }

    private InnerTransRecordService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new InnerTransRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.PAYMENTRPC);
    }

    /**
     * 内部转账接口
     * @param innerTrans  转账请求参数
     *
     */
    public InnerTransResult innerTransfer(InnerTransRecordModel innerTrans)   {
        log.info("转账请求参数:"+innerTrans.toString());
        InnerTransResult result =new InnerTransResult();
        try {
             result = getClient().innerTrans(innerTrans);
        } catch (Exception e) {
            log.error("调用内部转账接口失败", e);
            return result.setMsg("调用内部转账接口失败,断开连接");
        }

        log.info("返回值为:"+result == null ? "":result.toString());
        return result;
    }

    /**
     * 虚拟账户代收代付接口
     * @param transModel 请求参数
     */
   /* public void  hjcAccountTrans(HJCAccountTransModel transModel) throws HgmsBusinessException {
        try {
            InnerTransResult result = getClient().HJCAccountTrans(transModel);
            if (result == null) {
                log.error("调用虚拟账户代收代付接口失败");
                throw new HgmsBusinessException("调用虚拟账户代收代付接口失败");
            }
            if (result.getCode() != 1000) {
                log.error("调用虚拟账户代收代付接口失败, 失败原因:{}", result.getMsg());
                throw new HgmsBusinessException("调用虚拟账户代收代付接口失败");
            }
        } catch (Exception e) {
            log.error("调用转账失败", e);
            throw new HgmsBusinessException("调用虚拟账户代收代付接口失败");
        }

    }
*/

}
