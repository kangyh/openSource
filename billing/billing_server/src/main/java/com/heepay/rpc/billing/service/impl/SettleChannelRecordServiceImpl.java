package com.heepay.rpc.billing.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.SettleChannelRecordMapper;
import com.heepay.rpc.billing.model.SettleChannelRecordModel;
import com.heepay.rpc.billing.producer.SettleMerchatProducerSender;
import com.heepay.rpc.service.RpcService;
/**
 * 
 * 
 * 描 述：通道侧汇总结算批次和清算明细
 *
 * 创 建 者：chenyanming 
 * 创建时间： 2016年9月9日下午2:03:47 
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
@RpcService(name = "SettleChannelRecordServiceImpl", processor = com.heepay.rpc.billing.service.SettleChannelRecordService.Processor.class)
@Component
public class SettleChannelRecordServiceImpl implements com.heepay.rpc.billing.service.SettleChannelRecordService.Iface {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	SettleChannelRecordMapper settleChannelRecordDao;
	@Autowired
	ClearChannelRecordMapper clearChannelRecordDao;
	@Autowired
	SettleMerchatProducerSender queueProducerSender;

	@Override
	public List<SettleChannelRecordModel> saveSettleChannelRecord(SettleChannelRecordModel settleChannelRecordModel)
			throws TException {

		return null;
	}

	@Override
	public List<SettleChannelRecordModel> query(SettleChannelRecordModel settleChannelRecordModel) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void updateSettleChannelRecord(SettleChannelRecordModel settleChannelRecordModel) throws TException {
		try {
			// 核账完成，修改结算记录中结算状态为已结算（chen）
			settleChannelRecordDao.updateSettleChannelRecordStatus(settleChannelRecordModel.getSettleBath());
			// 核账完成，修改清算表中完成时间和结算状态
			clearChannelRecordDao.updateClearChannelRecord(settleChannelRecordModel.getSettleBath());
		} catch (Exception e) {
			logger.error("修改状态失败", e);
		}
	}

	@Override
	public void deleteSettleChannelRecord(SettleChannelRecordModel settleChannelRecordModel) throws TException {
		// TODO Auto-generated method stub

	}

	/**
	 * 生成通道侧结算批次表
	 */
	@Override
	//@Transactional()
	public String getSettleChannelRecordMessage() throws TException {
		
		return null;
	}

}
