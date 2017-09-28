package com.heepay.tpds.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.enums.TransType;
import com.heepay.rpc.service.RpcService;
import com.heepay.tpds.dao.TpdsCustomerAccountMapper;
import com.heepay.tpds.dao.TpdsMerchantAccountMapper;
import com.heepay.tpds.entity.TpdsCustomerAccount;
import com.heepay.tpds.entity.TpdsMerchantAccount;
import com.heepay.tpds.enums.Customerstatus;
/**
 * *
 * 
* 
* 描    述： 对账文件入口
*
* 创 建 者： wangjie
* 创建时间：  2017年4月18日下午5:38:10
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
@RpcService(name = "CreateBillFileServiceImpl", processor = com.heepay.rpc.tpds.service.CreateBillFileService.Processor.class)
public class CreateBillFileServiceImpl implements com.heepay.rpc.tpds.service.CreateBillFileService.Iface{
	
	@Autowired
	AccountCheckServiceImpl accoountCheckServiceImpl;
	
	@Autowired
	TpdsCustomerAccountMapper tpdsCustomerAccountDaoImpl;
	
	@Autowired
	TpdsMerchantAccountMapper tpdsMerchantAccountMapperDaoImpl;
	
	@Autowired
	DptwzdAccountCheckImpl dptwzdAccountCheckImpl;
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void query() throws TException {
		
		TpdsMerchantAccount tpdsMerchantAccount = tpdsMerchantAccountMapperDaoImpl.selectAccountRecordByStatusSystemNo(Customerstatus.ENABLE.getValue(),"JCT");
		if(tpdsMerchantAccount != null){
				logger.info("用户" + tpdsMerchantAccount.getMerchantNo() + "开始生成充值对账文件");
				//掉清结算查询结算信息(充值)
				accoountCheckServiceImpl.chargeAccountCheck(tpdsMerchantAccount.getMerchantNo(), tpdsMerchantAccount.getSystemNo(),null, null, TransType.DEPOSIT_PAY.getValue(), "Y");
				//掉清结算查询结算信息(提现)    
				//dptwzdAccountCheckImpl.dptwzdAccountCheck(tpdsMerchantAccount.getMerchantNo(), tpdsMerchantAccount.getSystemNo(),null, null, TransType.DEPOSIT_WITHDRAW.getValue(), "Y");
		}
		
	}

}
