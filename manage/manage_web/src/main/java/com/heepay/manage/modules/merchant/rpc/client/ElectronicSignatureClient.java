/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.rpc.client;

import com.heepay.common.util.Constants;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.*;
import com.heepay.rpc.payment.service.ElectronicsSealService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * 描述：盖商户电子章的client
 *
 * 创建者  ly
 * 创建时间 2017年6月16日13:44:52
 * 创建描述：盖商户电子章的client
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Service
public class ElectronicSignatureClient extends BaseClientDistribute {

    private static final String SERVICENAME = "ElectronicsSealServiceImpl";
    private static final String NODENAME = Constants.RPCNodeName.PAYMENTRPC;

    private static final Logger log = LogManager.getLogger();

    @Resource(name = "paymentClient")
    private ThriftClientProxy clientProxy;

    @Override
    public ThriftClientProxy getClientProxy() {

        return clientProxy;
    }

    private ElectronicsSealService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new ElectronicsSealService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    /**
     * 合同签章并返回地址
     * @param merchantId            商户ID
     * @param product               产品代码
     * @param contractUrl           文件的fastDFS地址
     * @param contractId            合同号
     * @param contractName          合同名称
     * @param signAId               甲方merchantId
     * @param signAName             甲方名称
     * @param signBName             乙方名称
     * @param sealSType              固定SINGLE
     * @param electronicsSealPosModel   electronicsSealPosModel
     * @param sysResource           固定hfb
     * @param sealType              固定hfb0
     * @return
     * @throws TException
     */
    public ElectronicsSealResponse electronicsSeal(String merchantId,String product,String contractUrl,String contractId,
               String contractName,String signAId,String signAName,String signBName,String sealSType,
               ElectronicsSealPosModel electronicsSealPosModel,String sysResource,String sealType) {
        try {
            return getClient().electronicsSealSign(Long.parseLong(merchantId),product,contractUrl,contractId,contractName,
                    signAId,signAName,signBName,sealSType,electronicsSealPosModel,sysResource,sealType);
        } catch (TException e) {
            log.error("thrift异常{}",e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     * 合同签章并返回地址
     * @param sealMerchantReqModel   商户信息
     * @param sealContractReqModel   合同信息
     * @param electronicsSealPosModel  签章位置信息
     * @param sealStyleReqModel  签章信息
     * @param sysResource  来源
     * @param reqType
     * @param signContract
     * @return
     */
    public ElectronicsSealResponse executeSeal(SealMerchantReqModel sealMerchantReqModel,
                                               SealContractReqModel sealContractReqModel,ElectronicsSealPosModel electronicsSealPosModel,
                                               SealStyleReqModel sealStyleReqModel, String sysResource, String reqType, boolean signContract) {
        try {
            return getClient().executeSeal(sealMerchantReqModel,sealContractReqModel,electronicsSealPosModel,
                    sealStyleReqModel,sysResource,reqType,signContract);
        } catch (TException e) {
            log.error("thrift异常{}",e);
        } finally {
            this.close();
        }
        return null;
    }

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }
}
