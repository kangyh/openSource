package com.heepay.warning.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.ProductType;
import com.heepay.warning.client.MonitorClient;
import com.heepay.warning.client.RiskWarningClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描    述：风控系统
 * <p>
 * 创 建 者：dongzhengjiang E-mail:dongzj@9186.com
 * 创建时间： 2017-08-11 10:21
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
public class WarningOrderSuccessRateJob extends AbstractSimpleElasticJob {
    @Autowired
    private RiskWarningClient riskWarningClient;
    @Autowired
    private MonitorClient monitorClient;
    String tableTemplate="<table border=\"1\" cellspacing=\"0\"  style=\"border-collapse:collapse;\"><th>服务器</th><th>商户号</th><th>产品</th><th>总订单数</th><th>成功订单数</th><th>订单成功率</th><th>同比</th><th>环比</th><th>阈值</th><th>变化率</th><th>警告级别</th><th>统计时间范围</th>";
    private static final Logger logger = LogManager.getLogger();
    @Override
    public void process(JobExecutionMultipleShardingContext shardingContext) {
        String header="正式商户订单成功率报警";
        String body="";
        Map sendMap=new HashMap();
        logger.info("正式商户订单成功率开始");
        String str=riskWarningClient.getOrderSuccessRateWarning();
        logger.info("订单成功率结果:{}",str);
        Map map=JsonMapperUtil.buildNonDefaultMapper().fromJson(str,Map.class);
        if((int)map.get("status")==1) {
            List<Map> list=(List)map.get("list");
            String sb=getWarningInfo(list);
            if(!StringUtil.isBlank(sb.toString())) {
                body=sb;
            }
            if(!StringUtil.isBlank(body)) {
                String groupEntity = monitorClient.getInfoGroup("orderRate");
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
        logger.info("正式商户订单成功率结束");
    }
    private String getWarningInfo(List<Map> list)
    {
        StringBuilder sb=new StringBuilder();
        if(list!=null&&list.size()>0) {
            for (Map tempMap : list) {
                for (Map tempMap2 : (List<Map>) tempMap.get("list")) {
                    String productName=tempMap.get("productType")==null?"":ProductType.getContentByValue(tempMap.get("productType").toString());
                    String host=tempMap2.get("host")==null?"":tempMap2.get("host").toString();
                    sb.append("<tr><td>"+host+"</td><td>"+tempMap.get("merchantId")+"</td><td>"+productName+"</td><td>"+tempMap2.get("allCount").toString()+"</td><td>"+tempMap2.get("sucCount").toString()+"</td><td>"+tempMap2.get("rate").toString()+"%</td><td>昨日:"+tempMap2.get("yesterday").toString()+"%<br>前日:"+tempMap2.get("beforeYesterday").toString()+"%</td><td>前一次:"+tempMap2.get("preFirst").toString()+"%<br>前两次:"+tempMap2.get("preSecond").toString()+"%</td><td>"+tempMap.get("threshold")+"%</td><td>"+tempMap.get("changeRate")+"%</td><td>"+tempMap2.get("warningLevel").toString()+"</td><td>"+tempMap.get("beginTime").toString()+"到"+tempMap.get("endTime").toString()+"</td></tr>");
                }
            }
        }
        if(sb.length()==0)
            return "";
        return tableTemplate+sb.toString()+"</table>";
    }
    private String getChangeRateStr(int changeRate)
    {
        if(changeRate<0)
            return "<font color='red'>↓</font>"+String.valueOf(-changeRate);
        else if(changeRate>0)
            return "<font color='green'>↑</font>"+String.valueOf(changeRate);
        else
            return String.valueOf(changeRate);

    }
}
