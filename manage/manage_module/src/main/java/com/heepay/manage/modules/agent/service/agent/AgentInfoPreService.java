package com.heepay.manage.modules.agent.service.agent;

import com.heepay.common.util.StringUtil;
import org.springframework.stereotype.Service;

import com.heepay.manage.modules.agent.entity.agent.AgentInfo;

/**
 * Created by 徐超华 on 2017/1/17.
 */
@Service
public class AgentInfoPreService {
    public void preAgentInfoRequest(AgentInfo info){
        if(StringUtil.isBlank(info.getId())){
            info.setAgentLevel("1");
            info.setAgentStatus("INITIA");
            info.setAgentType("COMPAN");
        }
    }
}
