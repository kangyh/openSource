package com.heepay.engine.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.engine.AbstractRiskFact;
import com.heepay.risk_es_engine.ESearchService;

import net.sf.json.JSONObject;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月11日 下午5:36:52
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
public class ChannelRiskFact extends AbstractRiskFact{
	
	ESearchService essearch;
	/**
     * 通道代码
     */
    private String channelCode;

  
  

    /**
     * 单日限额
     */
    private String daylimitAmount;
    /**
     * 单月限额
     */
    private String monlimitAmount;


    /**
     * 订单金额
     */
    private String amount;



    
    
    private BigDecimal perDayAmount; //单日金额
	private BigDecimal perMonthAmount; //单月金额

    public String getChannelCode() {
        return channelCode;
    }
   
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    public String getDaylimitAmount() {
        return daylimitAmount;
    }

    public void setDaylimitAmount(String daylimitAmount) {
        this.daylimitAmount = daylimitAmount;
    }

    public String getMonlimitAmount() {
        return monlimitAmount;
    }

    public void setMonlimitAmount(String monlimitAmount) {
        this.monlimitAmount = monlimitAmount;
    }

   

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    

    

    

    

	public BigDecimal getPerDayAmount() {
		return perDayAmount;
	}

	public void setPerDayAmount(BigDecimal perDayAmount) {
		this.perDayAmount = perDayAmount;
	}

	public BigDecimal getPerMonthAmount() {
		return perMonthAmount;
	}

	public void setPerMonthAmount(BigDecimal perMonthAmount) {
		this.perMonthAmount = perMonthAmount;
	}
	 public void getBatchAmount()
	    {
	    	if (essearch == null)
	    	{
	    		essearch = new ESearchService();	
	    		essearch.initESClient();
	    	}
	    	Map paramap=new HashMap();
	    	paramap.put("channel_code",this.getChannelCode());
	    	JSONObject json=new JSONObject();
			json.fromObject(paramap);
	    	Map map = essearch.getChannelDayAndMonthLimitAmount(json);
	    	perDayAmount = new BigDecimal(map.get("day").toString());
	    	perMonthAmount = new BigDecimal(map.get("month").toString());
	    	essearch.closeESClient();
	    }

}


