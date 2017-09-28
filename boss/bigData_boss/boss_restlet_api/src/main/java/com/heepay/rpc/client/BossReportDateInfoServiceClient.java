package com.heepay.rpc.client;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.rpc.boss.service.BossReportDateInfoService;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 描 述：
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/1 15:07
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
public class BossReportDateInfoServiceClient extends BaseClientDistribute{

    private static final String SERVICENAME = "BossReportDateInfoServiceImpl";

    private static final String NODENAME = "boss_rpc";

    private static final Logger log = LogManager.getLogger();

    @Resource(name = "bossClient")
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

    public BossReportDateInfoService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new BossReportDateInfoService.Client(ClientThreadLocal
                .getInstance().getProtocol());
    }

    /**
     *
     * @方法说明：查询boss数据List
     * @时间：2017年2月16日 上午10:31:51
     * @创建人：wangdong
     */
    public String getbossReportDateInfoList(String params) {
        BossReportDateInfoService.Client client = this.getClient();
        try {
            String dataInfo = client.getbossReportDateInfoList(params);
            log.info("查询boss数据【List】THRIFT服务,参数{}，返回结果：{}", params, dataInfo);
            return dataInfo;
        } catch (TException e) {
            e.printStackTrace();
            log.info("查询boss数据【List】THRIFT服务异常,参数{}，异常信息：{}", params, e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     *
     * @方法说明：查询boss数据详情
     * @时间：2017年2月16日 上午10:31:51
     * @创建人：wangdong
     */
    public String getbossReportDateInfoDetail(String params) {
        BossReportDateInfoService.Client client = this.getClient();
        try {
            String dataInfo = client.getbossReportDateInfoDetail(params);
            log.info("查询boss数据【详情】THRIFT服务,参数{}，返回结果：{}", params, dataInfo);
            return dataInfo;
        } catch (TException e) {
            e.printStackTrace();
            log.info("查询boss数据【详情】THRIFT服务异常,参数{}，异常信息：{}", params, e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     *
     * @方法说明：查询boss数据统计
     * @时间：2017年2月16日 上午10:31:51
     * @创建人：wangdong
     */
    public String getbossReportDateInfoTimeTotal(String params) {
        BossReportDateInfoService.Client client = this.getClient();
        try {
            String dataInfo = client.getbossReportDateInfoTotal(params);
            log.info("查询boss数据【时间段统计】THRIFT服务,参数{}，返回结果：{}", params, dataInfo);
            return dataInfo;
        } catch (TException e) {
            e.printStackTrace();
            log.info("查询boss数据【时间段统计】THRIFT服务异常,参数{}，异常信息：{}", params, e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     *
     * @方法说明：查询boss数据统计
     * @时间：2017年2月16日 上午10:31:51
     * @创建人：wangdong
     */
    public String getbossReportDateInfoDayTotal(String params) {
        BossReportDateInfoService.Client client = this.getClient();
        try {
            Map<String, String> paramsMap = JsonMapperUtil.nonEmptyMapper().fromJson(params, Map.class);
            log.info("查询boss数据【日统计】THRIFT服务,参数{}，", params);
            paramsMap.put("beginTime",paramsMap.get("day"));
            paramsMap.put("endTime",paramsMap.get("day"));
            paramsMap.remove("day");
            String dataInfo = client.getbossReportDateInfoTotal(JSONObject.fromObject(paramsMap).toString());
            log.info("查询boss数据【日统计】THRIFT服务,参数{}，返回结果：{}", JSONObject.fromObject(paramsMap), dataInfo);
            return dataInfo;
        } catch (TException e) {
            e.printStackTrace();
            log.info("查询boss数据【日统计】THRIFT服务异常,参数{}，异常信息：{}", params, e);
        } finally {
            this.close();
        }
        return null;
    }

}
