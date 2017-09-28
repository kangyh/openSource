package com.heepay.rpc.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.risk.dao.impl.RiskControlLimitAmountDaoImpl;
import com.heepay.risk.dao.impl.RiskTransInfoDaoImpl;
import com.heepay.risk.entity.MerchantLimitAmountObj;
import com.heepay.risk.entity.MerchantLimitAmountParam;
import com.heepay.risk.entity.TransInfoObj;
import com.heepay.rpc.client.MerchantProductServiceClient;
import com.heepay.rpc.client.MerchantRedisServiceClient;
import com.heepay.rpc.risk.model.FreezeResponse;
import com.heepay.rpc.risk.model.TransOrderRiskModel;
import com.heepay.rpc.risk.model.TransactionModel;
import com.heepay.vo.TransOrderRiskVO;
@SuppressWarnings({"unused", "resource"})
public class Run {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
	}
}
