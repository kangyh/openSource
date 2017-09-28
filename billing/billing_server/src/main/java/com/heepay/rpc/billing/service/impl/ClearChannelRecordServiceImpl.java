package com.heepay.rpc.billing.service.impl;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.rpc.billing.model.ClearChannelRecordModel;
import com.heepay.rpc.billing.service.IClearChannelRecordService;
import com.heepay.rpc.service.RpcService;

/**
 * 
 * 
 * 描    述：通道侧service层基本处理类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月8日上午11:11:16 
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
@RpcService(name="ClearChannelRecordServiceImpl",processor=com.heepay.rpc.billing.service.ClearChannelRecordService.Processor.class)
public class ClearChannelRecordServiceImpl implements com.heepay.rpc.billing.service.ClearChannelRecordService.Iface {
	

	@Autowired
	IClearChannelRecordService channelExpendImpl;
	@Autowired
	IClearChannelRecordService channelChargeImpl;
	@Autowired
	IClearChannelRecordService channelConsumeTimeImpl;
	@Autowired
	IClearChannelRecordService channelConsumePeriodImpl;
	
	@Autowired
	private ClearChannelRecordMapper clearChannelRecordDao;
	
	
	@Override
	public void saveClearChannelRecord(ClearChannelRecordModel clearChannelRecordModel) throws TException {
		String transType = clearChannelRecordModel.getTransType();  //交易类型
		if(transType == null){
			return;
		}
		// 通道侧业务实现类，包括提现，退款，批付，充值，消费
		channelExpendImpl.billingHandle(clearChannelRecordModel);
		/*// 充值
		if (BillingProductType.CHARGE.getValue().equals(channelType)) {
			channelChargeImpl.billingHandle(clearChannelRecordModel);
		}
		// 实时消费
		if (BillingProductType.CONSUMEREALTIME.getValue().equals(channelType)) {
			channelConsumeTimeImpl.billingHandle(clearChannelRecordModel);
		}
		// 周期消费
		if (BillingProductType.CONSUMEPERIOD.getValue().equals(channelType)) {
			channelConsumePeriodImpl.billingHandle(clearChannelRecordModel);
		}*/
		System.out.println("-------------------------------------执行了");
		
	}

	@Override
	public List<ClearChannelRecordModel> query(ClearChannelRecordModel clearChannelRecordModel) throws TException {
		
		return null;
	}

	@Override
	public void update(ClearChannelRecordModel clearChannelRecordModel) throws TException {
		// 修改通道侧对账状态
		clearChannelRecordDao.updateClearChannelRecordStatus();
		
	}

	@Override
	public void deleteBankcard(ClearChannelRecordModel clearChannelRecordModel) throws TException {
		// TODO Auto-generated method stub
		
	}

}
