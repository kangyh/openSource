package com.heepay.manage.modules.riskChannel.rpc.client;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.risk.service.ChannelRiskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 名称：风险通道thrift client
 * <p>
 * 创建者：yuliang
 * 创建时间：2017-06-26 13:54
 * 创建描述：风险通道thrift client
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Component
public class RiskChannelRpcClient extends BaseClient {

    private static final Logger log = LogManager.getLogger();

    private static final String SERVICENAME = "ChannelQuotaServiceImpl";
    private static final String NODENAME = "risk_rpc";

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }

    /**
     * 获取client
     * @return
     */
    public ChannelRiskService.Client getClient(){
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new ChannelRiskService.Client(ClientThreadLocal.getInstance().getProtocol());

    }

    /**
     * 调用risk thrift方法，获取通道统计日志列表
     * @param paramsMap
     * @return
     */
    public String getChannelLogList(Map<String, String> paramsMap){
        ChannelRiskService.Client client = this.getClient();
        try {
            String paramsJson = JsonMapperUtil.nonEmptyMapper().toJson(paramsMap);
            String result = client.getChannelList(paramsJson);
            return result;
        } catch (TException e) {
            log.error("获取通道日志统计异常！", e);
        }
        return null;
    }

    /**
     * 调用risk thrift方法，获取通道限额列表
     * @param paramsMap
     * @return
     */
    public String getChannelQuotaList(Map<String, String> paramsMap) {
        ChannelRiskService.Client client = this.getClient();
        try {
            String paramsJson = JsonMapperUtil.nonEmptyMapper().toJson(paramsMap);
            String result = client.getChannelQuotaList(paramsJson);
            return result;
        } catch (TException e) {
            log.error("获取通道日志统计异常！", e);
        }
        return null;
    }

    /**
     * 调用risk thrift方法，获取通道成功率列表
     * @param paramsMap
     * @return
     */
    public String getChannelRate(Map<String, String> paramsMap){
        try {
            ChannelRiskService.Client client = this.getClient();
            String paramsJson = JsonMapperUtil.nonEmptyMapper().toJson(paramsMap);
            return client.getChannelRate(paramsJson);
        } catch (TException e) {
            log.error("获取通道成功率client失败", e);
        }finally {
            this.close();
        }
        return null;
    }
}
