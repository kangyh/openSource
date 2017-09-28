package com.heepay.warning.job;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.warning.client.RiskWarningClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.warning.client.MonitorClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月16日 下午6:01:24
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
@Service
public class WarningNoOrderJob  extends AbstractSimpleElasticJob {

    @Autowired
    MonitorClient monitorClient;
    @Autowired
    private RiskWarningClient riskWarningClient;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void process(JobExecutionMultipleShardingContext shardingContext) {
        String header="正式商户无订单报警";
        StringBuilder sb=new StringBuilder();
        Map sendMap=new HashMap();
        logger.info("正式商户无订单开始");
        String str=riskWarningClient.getNoOrderWarning();
        logger.info("正式商户无订单结果:{}",str);
        Map map=JsonMapperUtil.buildNonDefaultMapper().fromJson(str,Map.class);
        if((int)map.get("status")==1) {

                sb.append("外部正式商户无订单报警");

            if(!StringUtil.isBlank(sb.toString())) {
                String groupEntity = monitorClient.getInfoGroup("noOrder");
                if(!StringUtil.isBlank(groupEntity)) {
                    Map groupMap = JsonMapperUtil.buildNonDefaultMapper().fromJson(groupEntity, Map.class);
                    sendMap.put("groupId", groupMap.get("groupId"));
                    sendMap.put("type", groupMap.get("remark"));
                    sendMap.put("msgHead", header);
                    sendMap.put("msgBody", sb.toString());
                    monitorClient.sendWaringMsg(JsonMapperUtil.buildNonDefaultMapper().toJson(sendMap));
                }
            }
        }
        logger.info("正式商户无订单结束");
    }

}