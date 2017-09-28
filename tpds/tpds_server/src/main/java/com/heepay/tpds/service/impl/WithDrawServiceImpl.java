package com.heepay.tpds.service.impl;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.rpc.service.RpcService;
import com.heepay.rpc.tpds.model.DepositMsgVO;
import com.heepay.tpds.client.WithdrawClient;
import com.heepay.tpds.dao.impl.TpdsBankMsgDaoImpl;
import com.heepay.tpds.dao.impl.TpdsBankWithdrawDaoImpl;
import com.heepay.tpds.dao.impl.TpdsMerchantAccountDaoImpl;
import com.heepay.tpds.entity.TpdsMerchantAccount;
import com.heepay.tpds.enums.DepositStatus;
import com.heepay.tpds.vo.DepositWithdrawVo;
/**
 * 
 *
 * 描    述：客户资金提现【实现】
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月8日 下午6:20:34
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
@RpcService(name = "WithDrawServiceImpl", processor = com.heepay.rpc.tpds.service.WithDrawService.Processor.class)
public class WithDrawServiceImpl implements com.heepay.rpc.tpds.service.WithDrawService.Iface{
    @Autowired
	private WithdrawClient withdrawClient;
    @Autowired
	private TpdsBankWithdrawDaoImpl tpdsBankWithdrawDaoImpl;
    @Autowired
	private TpdsBankMsgDaoImpl tpdsBankMsgDaoImpl;
    @Autowired
    private TpdsMerchantAccountDaoImpl tpdsMerchantAccountDaoImpl;
    
	/*
	 * 客户资金提现【接口存管银行的异步通知】
	 * (non-Javadoc)
	 * @see com.heepay.tpds.service.ICustomMoneyAndPrepaidphoneWithDrawService#customWithDraw(java.lang.String)
	 */
	
	public String customWithDraw(String message) {

		//1.根据restlet接口传来的提现信息
		
		//2.异步通知
		
		return null;
	}

	/*
	 * 平台资金提现
	 * (non-Javadoc)
	 * @see com.heepay.tpds.service.ICustomMoneyAndPrepaidphoneWithDrawService#prepaidphoneWithDraw(java.lang.String)
	 */
	
	public String prepaidPhoneWithDraw(String message) {
		
		//1.根据restlet接口传来的提现信息
		
		//2.调用交易系统的服务
		
		//3.异步通知

		return null;
	}

	/*
	 * 存管银行资金提现【日终结算】
	 * (non-Javadoc)
	 * @see com.heepay.tpds.service.IWithDrawService#bankWithDraw(java.lang.String)
	 */
	
	public String bankWithDraw(String message) {

		//1.定时任务触发
		
		//2.调用交易系统的服务
		
		//3.异步通知
		
		return null;
	}
     /**
      * 
       * 
       * @方法说明：
       * @author dongzhengjiang
       * @param msg com.heepay.tpds.vo.DepositWithdrawVo转换成json格式
       * @return
       * @时间：2017年2月17日 上午11:24:50
      */
	@Override
	public DepositMsgVO depositWithDraw(String msg) throws TException {
		DepositWithdrawVo model=JsonMapperUtil.buildNonDefaultMapper().fromJson(msg, DepositWithdrawVo.class);
		
		TpdsMerchantAccount select=new TpdsMerchantAccount();
		select.setSystemNo(model.getSystemNo());
		TpdsMerchantAccount MerchantAccount=tpdsMerchantAccountDaoImpl.selectBySysNoAndBankNo(select);
		if(MerchantAccount==null)
		{
			DepositMsgVO tempVo=new DepositMsgVO();
			tempVo.setCode(DepositStatus.INVALID_MERCHANT.getValue());
			tempVo.setMsg(DepositStatus.INVALID_MERCHANT.getContent());
			return tempVo;
		}
		model.setMerchantId(MerchantAccount.getMerchantNo());	
		DepositMsgVO vo= withdrawClient.DepositWithdraw(model);
		return vo;
	}
    /**
     * 
      * 
      * @方法说明：
      * @author dongzhengjiang
      * @param msg：oldbusinessSeqNo:原业务流水号
      * @return
      * @时间：2017年2月17日 上午11:25:45
     */
	@Override
	public DepositMsgVO depositWithDrawQuery(String msg) throws TException {
		DepositWithdrawVo model=JsonMapperUtil.buildNonDefaultMapper().fromJson(msg, DepositWithdrawVo.class);
		TpdsMerchantAccount select=new TpdsMerchantAccount();
		select.setSystemNo(model.getSystemNo());
		TpdsMerchantAccount MerchantAccount=tpdsMerchantAccountDaoImpl.selectBySysNoAndBankNo(select);
		if(MerchantAccount==null)
		{
			DepositMsgVO tempVo=new DepositMsgVO();
			tempVo.setCode(DepositStatus.INVALID_MERCHANT.getValue());
			tempVo.setMsg(DepositStatus.INVALID_MERCHANT.getContent());
			return tempVo;
		}
		DepositMsgVO vo= withdrawClient.DepositWithdrawQuery(MerchantAccount.getMerchantNo(),model.getBusinessSeqNo());
		return vo;
	}

}
