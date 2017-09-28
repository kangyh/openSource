package com.heepay.warning.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.ChannelPartner;
import com.heepay.enums.ChannelType;
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
 * 创建时间： 2017-08-11 10:22
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
public class WarningChannelSuccessRateJob extends AbstractSimpleElasticJob {
    @Autowired
    private RiskWarningClient riskWarningClient;
    @Autowired
    private MonitorClient monitorClient;
    String tableTemplate="<table border=\"1\" cellspacing=\"0\"  style=\"border-collapse:collapse;\"><th>服务器</th><th>通道合作方</th><th>通道支付类型</th><th>总数</th><th>成功数</th><th>通道成功率</th><th>同比</th><th>环比</th><th>阈值</th><th>变化率</th><th>警告级别</th><th>统计时间范围</th>";
    private static final Logger logger = LogManager.getLogger();
    @Override
    public void process(JobExecutionMultipleShardingContext shardingContext) {
        String header="通道成功率报警";
        Map sendMap=new HashMap();
        String body="";
        logger.info("通道成功率开始");
        String str=riskWarningClient.getChannelSuccessRateWarning();
        logger.info("通道成功率结果:{}",str);
        Map map=JsonMapperUtil.buildNonDefaultMapper().fromJson(str,Map.class);
        if((int)map.get("status")==1) {
            List<Map> list=(List)map.get("list");
            String sb=getWarningInfo(list);
            if(!StringUtil.isBlank(sb.toString())) {
                body = sb;
            }
            if(!StringUtil.isBlank(body))
            {
                String groupEntity = monitorClient.getInfoGroup("channelRate");
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
        logger.info("通道成功率结束");
    }
    private String getWarningInfo(List<Map> list)
    {
        StringBuilder sb=new StringBuilder();
        if(list!=null&&list.size()>0) {
            for (Map tempMap : list) {
                for (Map tempMap2 : (List<Map>) tempMap.get("list")) {
                    String host=tempMap2.get("host")==null?"":tempMap2.get("host").toString();
                    sb.append("<tr><td>"+host+"</td><td>"+tempMap.get("channel_partner_code").toString() +"</td><td>"+tempMap.get("channel_type_code").toString()+"</td><td>"+tempMap2.get("totalNum").toString()+"</td><td>"+tempMap2.get("totalSuccessNum").toString()+"</td><td>"+tempMap2.get("rate").toString()+"%</td><td>昨日:"+tempMap2.get("yesterday").toString()+"%<br>前日:"+tempMap2.get("beforeYesterday").toString()+"%</td><td>前一次:"+tempMap2.get("preFirst").toString()+"%<br>前两次:"+tempMap2.get("preSecond").toString()+"%</td><td>"+tempMap.get("threshold")+"%</td><td>"+tempMap.get("changeRate")+"%</td><td>"+tempMap2.get("warningLevel").toString()+"</td><td>"+tempMap.get("beginTime").toString()+"到"+tempMap.get("endTime").toString()+"</td></tr>");
                }
            }
        }
        if(sb.length()==0)
            return "";
        return tableTemplate+sb.toString()+"</table>";
    }

}
