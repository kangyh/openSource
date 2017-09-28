package com.heepay.boss.client;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.risk.service.GateWayDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 描 述：风控系统es查询数据
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/3 14:05
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
@Component
public class RiskSeviceClient extends BaseClientDistribute {

    private static final String SERVICENAME = "BossReportDataServiceImpl";

    private static final String NODENAME = "risk_rpc";

    private static final Logger log = LogManager.getLogger();

    @Resource(name = "riskClient")
    private ThriftClientProxy clientProxy;

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
        return clientProxy;
    }

    public GateWayDataService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new GateWayDataService.Client(ClientThreadLocal
                .getInstance().getProtocol());
    }

    /**
     * @方法说明：查询boss数据List
     * @时间：2017年2月16日 上午10:31:51
     * @创建人：wangdong
     */
    public List<Map<String,String>> getbossReportDateInfoList(String fields,String equalParam) {
        GateWayDataService.Client client = this.getClient();
        try {
            List<Map<String,String>> dataInfoList = client.getGatewayRecordList(fields,equalParam);
            log.info("查询boss数据【List】THRIFT服务,参数{}，返回结果：{}", equalParam, dataInfoList);
            return dataInfoList;
        } catch (TException e) {
            e.printStackTrace();
            log.info("查询boss数据【List】THRIFT服务异常,参数{}，异常信息：{}", equalParam, e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     * @方法说明：查询数据详情
     * @时间：2017年2月16日 上午10:31:51
     * @创建人：wangdong
     */
    public String getbossReportDateInfoDetail(String fields,String equalParam) {
        GateWayDataService.Client client = this.getClient();
        try {
            String info = client.getBossDataRecordInfo(fields,equalParam);
            log.info("查询数据详情 THRIFT服务,参数{}，返回结果：{}", equalParam, info);
            return info;
        } catch (TException e) {
            e.printStackTrace();
            log.info("查询数据详情 THRIFT服务异常,参数{}，异常信息：{}", equalParam, e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     * @方法说明：查询数据统计
     * @时间：2017年2月16日 上午10:31:51
     * @创建人：wangdong
     */
    public String getbossReportDateInfoTotal(String fields,String equalParam) {
        GateWayDataService.Client client = this.getClient();
        try {
            String infoTotal = client.getBossAggregationInfo(fields,equalParam);
            log.info("查询数据统计 THRIFT服务,参数{}，返回结果：{}", equalParam, infoTotal);
            return infoTotal;
        } catch (TException e) {
            e.printStackTrace();
            log.info("查询数据统计 THRIFT服务异常,参数{}，异常信息：{}", equalParam, e);
        } finally {
            this.close();
        }
        return null;
    }

}
