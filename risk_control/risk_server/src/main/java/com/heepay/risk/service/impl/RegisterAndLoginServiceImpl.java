package com.heepay.risk.service.impl;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.engine.entity.RegisterAndLoginFact;
import com.heepay.engine.impl.DroolsTemplateRuleEngineService;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.risk.RegLoginType;
import com.heepay.risk.dao.RiskBlockInfoMapper;
import com.heepay.risk.entity.RiskBlockInfo;
import com.heepay.risk.enums.BlockType;
import com.heepay.risk.enums.MonitorObject;
import com.heepay.risk.service.IpQueryService;
import com.heepay.rpc.risk.service.BankFraudService;
import com.heepay.rpc.risk.service.RegisterAndLoginService;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 
 * 
 * 描    述：注册和登陆风控接口
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年7月24日 下午5:01:50
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
@Component
@RpcService(name="RegisterAndLoginServiceImpl",processor=RegisterAndLoginService.Processor.class)
public class RegisterAndLoginServiceImpl implements com.heepay.rpc.risk.service.RegisterAndLoginService.Iface {

    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private DroolsTemplateRuleEngineService droolsTemplateRuleEngineService;
    @Autowired
    RiskBlockInfoMapper riskBlockInfoMapper;
    @Autowired
    IpQueryService ipQueryService;
    @Override
    public String ExecuteRegisterAndLoginRule(String paramJson) throws TException {
        com.heepay.rpc.risk.model.AsyncMsgVO result=new  com.heepay.rpc.risk.model.AsyncMsgVO();
        logger.info("商户登录注册请求参数:"+paramJson);
        RegisterAndLoginFact fact=JsonMapperUtil.buildNonDefaultMapper().fromJson(paramJson, RegisterAndLoginFact.class);
        fact.setMessage(InterfaceStatus.SUCCESS);
        if(!StringUtil.isBlank(fact.getIp()))
        fact.setForeignIp(!ipQueryService.checkIfChineseIp(fact.getIp()));
        else
            fact.setForeignIp(false);
        try {
            droolsTemplateRuleEngineService.handleRuleFact(fact);
            logger.info("返回的结果信息:--->"+JsonMapperUtil.nonDefaultMapper().toJson(fact));
            result.setStatus(fact.getMessage().getValue());
            result.setMsg(fact.getMessage().getContent());
            this.handleIllegal(fact);

        }
        catch(Exception ex)
        {
            logger.error("风控限额出现异常:"+ex.getMessage());
            result.setStatus(InterfaceStatus.SUCCESS.getValue());
        }

        return JsonMapperUtil.buildNonDefaultMapper().toJson(result);
    }
    private void handleIllegal(RegisterAndLoginFact fact)
    {
        if(!StringUtil.isBlank(fact.getRuleId())) {
            RiskBlockInfo info = new RiskBlockInfo();
            info.setBlockType(fact.getMessage().getValue()==1000?BlockType.WARN.getValue():BlockType.BLOCK.getValue());
            info.setBuziType(fact.getRegLoginType());
            info.setRuleId(fact.getRuleId());
            info.setCreatetime(new Date());
            info.setMonitorObject(fact.getRegLoginType().equals(RegLoginType.USER_REG.getValue())||fact.getRegLoginType().equals(RegLoginType.USER_LOGIN_BACK.getValue())?MonitorObject.USER.getValue():MonitorObject.MERCHANT.getValue());
            info.setFileds(JsonMapperUtil.buildNonDefaultMapper().toJson(fact));
            riskBlockInfoMapper.insert(info);
        }
    }
}


