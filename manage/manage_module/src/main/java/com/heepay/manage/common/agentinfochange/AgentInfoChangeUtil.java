package com.heepay.manage.common.agentinfochange;

import com.heepay.agent.common.enums.AgentType;
import com.heepay.agent.common.enums.BusiType;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.agent.entity.agentinfochange.AgentInfoChange;
import org.apache.commons.lang3.EnumUtils;

/**
 * Created by Tangwei on 2017/3/2.
 */
public class AgentInfoChangeUtil {
    /**
     *返回代理商修改信息详情前预处理
     * @param agentInfoChange
     * @return
     */
    public static AgentInfoChange changeAgentInfoChange(AgentInfoChange agentInfoChange) {
        agentInfoChangeImage(agentInfoChange);
        return agentInfoChange;
    }

    public static void agentInfoChangeImage(AgentInfoChange agentInfoChange){
        if (StringUtils.isNotBlank(agentInfoChange.getIdcardFaceImage())) {
            agentInfoChange.setIdcardFaceImage(RandomUtil.getFastDfs(agentInfoChange.getIdcardFaceImage()));
        }
        if (StringUtils.isNotBlank(agentInfoChange.getIdcardBackImage())) {
            agentInfoChange.setIdcardBackImage(RandomUtil.getFastDfs(agentInfoChange.getIdcardBackImage()));
        }
        if (StringUtils.isNotBlank(agentInfoChange.getAgreementFile())) {
            agentInfoChange.setAgreementFile(RandomUtil.getFastDfs(agentInfoChange.getAgreementFile()));
        }
        if (StringUtils.isNotBlank(agentInfoChange.getBusiImage())) {
            agentInfoChange.setBusiImage(RandomUtil.getFastDfs(agentInfoChange.getBusiImage()));
        }
        if (StringUtils.isNotBlank(agentInfoChange.getTaxRegImage())) {
            agentInfoChange.setTaxRegImage(RandomUtil.getFastDfs(agentInfoChange.getTaxRegImage()));
        }
        if (StringUtils.isNotBlank(agentInfoChange.getOrgInstImage())) {
            agentInfoChange.setOrgInstImage(RandomUtil.getFastDfs(agentInfoChange.getOrgInstImage()));
        }
        if (StringUtils.isNotBlank(agentInfoChange.getOtherGenaImage())) {
            agentInfoChange.setOtherGenaImage(RandomUtil.getFastDfs(agentInfoChange.getOtherGenaImage()));
        }
    }

    public static void agentInfoChangeEnumValue(AgentInfoChange info){
        if(StringUtils.isNotBlank(info.getAgentType())){
            info.setAgentType(EnumUtils.getEnum(AgentType.class,info.getAgentType()).getValue());
        }
        if(StringUtils.isNotBlank(info.getBusiType())){
            info.setBusiType(EnumUtils.getEnum(BusiType.class,info.getBusiType()).getValue());
        }
    }
}
