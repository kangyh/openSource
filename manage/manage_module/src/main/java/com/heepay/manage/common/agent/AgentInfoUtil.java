package com.heepay.manage.common.agent;

import com.heepay.agent.common.enums.AgentType;
import com.heepay.agent.common.enums.BankAccountType;
import com.heepay.agent.common.enums.BusiType;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.agent.entity.agent.AgentInfo;
import org.apache.commons.lang3.EnumUtils;

/**
 * Created by 徐超华 on 2017/1/18.
 */
public class AgentInfoUtil {
    /**
     *返回代理商信息详情前预处理
     * @param agentInfo
     * @return
     */
    public static AgentInfo changeAgentInfo(AgentInfo agentInfo) {
        agentInfoImage(agentInfo);
        return agentInfo;
    }

    public static void agentInfoImage(AgentInfo agentInfo){
        if (StringUtils.isNotBlank(agentInfo.getIdcardFaceImage())) {
            agentInfo.setIdcardFaceImage(RandomUtil.getFastDfs(agentInfo.getIdcardFaceImage()));
        }
        if (StringUtils.isNotBlank(agentInfo.getIdcardBackImage())) {
            agentInfo.setIdcardBackImage(RandomUtil.getFastDfs(agentInfo.getIdcardBackImage()));
        }
        if (StringUtils.isNotBlank(agentInfo.getAgreementFile())) {
            agentInfo.setAgreementFile(RandomUtil.getFastDfs(agentInfo.getAgreementFile()));
        }
        if (StringUtils.isNotBlank(agentInfo.getBusiImage())) {
            agentInfo.setBusiImage(RandomUtil.getFastDfs(agentInfo.getBusiImage()));
        }
        if (StringUtils.isNotBlank(agentInfo.getTaxRegImage())) {
            agentInfo.setTaxRegImage(RandomUtil.getFastDfs(agentInfo.getTaxRegImage()));
        }
        if (StringUtils.isNotBlank(agentInfo.getOrgInstImage())) {
            agentInfo.setOrgInstImage(RandomUtil.getFastDfs(agentInfo.getOrgInstImage()));
        }
        if (StringUtils.isNotBlank(agentInfo.getOtherGenaImage())) {
            agentInfo.setOtherGenaImage(RandomUtil.getFastDfs(agentInfo.getOtherGenaImage()));
        }
    }

    public static void agentInfoEnumValue(AgentInfo info){
        if(StringUtils.isNotBlank(info.getAgentType())){
            info.setAgentType(EnumUtils.getEnum(AgentType.class,info.getAgentType()).getValue());
        }
        if(StringUtils.isNotBlank(info.getBusiType())){
            info.setBusiType(EnumUtils.getEnum(BusiType.class,info.getBusiType()).getValue());
        }
    }
}
