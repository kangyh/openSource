package com.heepay.manage.modules.merchant.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heepay.enums.AuthenticationStatus;
import com.heepay.manage.modules.merchant.rpc.client.BillingServiceClient;
import com.heepay.manage.modules.merchant.rpc.client.GatewayServiceClient;
import com.heepay.manage.modules.merchant.rpc.client.RiskServiceClient;
import com.heepay.manage.modules.merchant.service.impl.ManageMonitorCService;

/**          
* 
* 描    述：监控服务
*
* 创 建 者： ly
* 创建时间： 2016年12月12日 下午3:05:14 
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
    
@Controller
@RequestMapping(value="/static")
public class MonitorController {
    
    private static final String SUCCESS = "SUCCESS";
    
    @Autowired
    private ManageMonitorCService monitorCService;
    
    @Autowired
    private GatewayServiceClient gatewayClient;
    
    @Autowired
    private BillingServiceClient billingServiceClient;
    
    @Autowired
    private RiskServiceClient riskServiceClient;
    
    private final Logger logger = LogManager.getLogger();
    
    @RequestMapping(value="/monitor")
    @ResponseBody
    public String getMonitor(){
        try {
            boolean flag = monitorCService.getMonitor();
            String gateway = gatewayClient.queryGatewayService();
            String billing = billingServiceClient.queryBillingService();
            String risk = riskServiceClient.queryRiskService();
            logger.info("监控服务状态manage:{},gateway:{},billing:{},risk:{}",flag,gateway,billing,risk);
            boolean gatewayFlag = SUCCESS.equals(gateway);
            boolean billingFlag = AuthenticationStatus.SUCCESS.getValue().equals(billing);
            boolean riskFlag = !"null".equals(risk);
            if(flag && gatewayFlag && billingFlag && riskFlag){
                return "1";
            }
            return "0";
        } catch (Exception e) {
            logger.error("监控服务状态错误{}",e);
            return "0";
        }
    }
}
