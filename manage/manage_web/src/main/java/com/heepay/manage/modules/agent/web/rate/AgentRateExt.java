package com.heepay.manage.modules.agent.web.rate;

import com.heepay.manage.modules.agent.entity.rate.AgentRate;

/**
 * Created by 徐超华 on 2017/3/3.
 */
public class AgentRateExt extends AgentRate {
    String agentName;
    String agentCode;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public AgentRateExt() {
    }

    public AgentRateExt(AgentRate agentRate,String agentName,String agentCode) {
        this.agentName=agentName;
        this.agentCode=agentCode;
        this.setAgentId(agentRate.getAgentId());
        this.setProductCode(agentRate.getProductCode());
        this.setProductName(agentRate.getProductName());
        this.setProfitPercent(agentRate.getProfitPercent());
        this.setTransAmountBegin(agentRate.getTransAmountBegin());
        this.setTransAmountEnd(agentRate.getTransAmountEnd());
        this.setDefaultFeeType(agentRate.getDefaultFeeType());
        this.setDefaultFeePercent(agentRate.getDefaultFeePercent());
        this.setDefaultFeeMin(agentRate.getDefaultFeeMin());
        this.setDefaultFeeMax(agentRate.getDefaultFeeMax());
    }

    @Override
    public String toString() {
        return "AgentRateExt{" +
                "agentName='" + agentName + '\'' +
                ", agentCode='" + agentCode + '\'' +
                '}';
    }
}
