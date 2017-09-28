package com.heepay.tpds.server;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.heepay.rpc.billing.model.TpdsSettleMerchantModel;
import com.heepay.rpc.billing.model.querySettleMerchantModel;
import com.heepay.tpds.client.WithdrawClient;
import com.heepay.tpds.vo.DepositWithdrawVo;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月13日 下午2:22:52
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

@SuppressWarnings({"unused", "resource"})

public class Run {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		
	}
}

