package com.heepay.warning.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.warning.client.MonitorClient;
import com.heepay.warning.client.RiskWarningClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 描    述：风控系统
 * <p>
 * 创 建 者：dongzhengjiang E-mail:dongzj@9186.com
 * 创建时间： 2017-08-11 10:20
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
public class WarningNoLog  extends AbstractSimpleElasticJob {
    @Autowired
    private RiskWarningClient riskWarningClient;
    @Autowired
    private MonitorClient monitorClient;
    private static final Logger logger = LogManager.getLogger();
    @Override
    public void process(JobExecutionMultipleShardingContext shardingContext) {
        Map sendMap=new HashMap();
        String header="没有日志生成报警";
        String body="";
        logger.info("没有日志生成报警开始");
        String str=riskWarningClient.getPaymentNoLogWarning();
        logger.info("没有日志生成结果:{}",str);
        logger.info("没有日志生成报警结束");
        if(!StringUtil.isBlank(str))
        {
            Map resultMap=JsonMapperUtil.buildNonDefaultMapper().fromJson(str,Map.class);
            if(resultMap!=null) {
                for (Object temp : resultMap.keySet()) {
                    String str1 = resultMap.get(temp).toString();
                    String serverName=temp.toString().equals("payment_server")?"交易":"网关";
                    String[] arrHost = str1.split(",");
                    for (String host : arrHost) {
                        if(!StringUtil.isBlank(host))
                        body += "服务名:"+serverName+" 服务器:" + host + "没有日志生成<br>";
                    }
                }

            }

        }
        if(!StringUtil.isBlank(body)) {
            String groupEntity = monitorClient.getInfoGroup("noLog");
            if(!StringUtil.isBlank(groupEntity)) {
                Map groupMap = JsonMapperUtil.buildNonDefaultMapper().fromJson(groupEntity, Map.class);
                sendMap.put("groupId", groupMap.get("groupId"));
                sendMap.put("type", groupMap.get("remark"));
                sendMap.put("msgHead", header);
                sendMap.put("msgBody", body);
                monitorClient.sendWaringMsg(JsonMapperUtil.buildNonDefaultMapper().toJson(sendMap));
            }
        }
    }
}
