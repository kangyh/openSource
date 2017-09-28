package com.heepay.manage.modules.cbms.service;


import com.heepay.common.util.StringUtils;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.service.AccountRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 描    述：通关申报订单审核调用撤手续费接口Service
 * <p>
 * 创 建 者： @author 牛俊鹏
 * 创建时间：
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
public class AccountRecordServiceImpl extends BaseClientDistribute {

    private static final Logger log = LogManager.getLogger();
    private static final String SERVICENAME = "AccountRecordServiceImpl";//服务名
    private static final String NODENAME = "payment_rpc";//节点名
    @Resource(name = "paymentInfo")//对应配置文件spring-context-thrift.xml中的bean
    private ThriftClientProxy thriftClientProxy;

    public ThriftClientProxy getClientProxy() {
        return thriftClientProxy;
    }

    private AccountRecordService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new AccountRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }

    /**
     * 调手续费接口
     *
     * @param
     * @return 1000为成功，其他数字为失败
     */
    public int recordFeeAccount(String merchantId, String transId) {
        //返回结果
        int result = 0;
        try {
            //退商户手续费
            log.info("开始调用撤手续费接口,merchantid{},transId{}", merchantId, transId);//传两个参数  merchantId商户id  counts原支付流水号     其他写死
            result = getClient().revokeAccount(Long.parseLong(merchantId), transId, "CACNCE", "");
            log.info("返回退款手续费结果：" + result);
            return result;
        } catch (TException e) {
            log.error("调用退款手续费接口", e);
        } finally {
            this.close();
        }
        return result;
    }

    /**
     *判断交易流水号为空则过滤退款操作
     * @param merchantId
     * @param transId
     * @return
     */
    public boolean refund(String merchantId, String transId) {
        if (StringUtils.isEmpty(transId)) {
            return true;
        }
        int result = recordFeeAccount(merchantId, transId);
        if (result == 1000) {
            return true;
        }
        return false;
    }
}
