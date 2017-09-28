package com.heepay.rpc.billing.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.SettleChannelRecordMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.SettleChannelRecord;
import com.heepay.billing.entity.SettleChannelRecordVo;
import com.heepay.billingutils.IdBatch;
import com.heepay.billingutils.util.Constants;
import com.heepay.common.util.TransTypeUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.TransType;
import com.heepay.rpc.billing.producer.SettleMerchatProducerSender;
import com.heepay.rpc.billing.service.IGetChannelBatchDataService;
import com.heepay.vo.ClearDetailMessage;
import com.heepay.vo.SettleChannelMessage;
import com.heepay.vo.SettleChannelRecordPo;

/**
 * 
 * 
 * 描    述：将结算批次数据入库，并将结算批次和明细数据发给账务系统
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月23日下午4:13:21 
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
@Service
public class SettleChannelRecordAndSend implements IGetChannelBatchDataService {
	
    private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	SettleChannelRecordMapper settleChannelRecordDao;
	@Autowired
	ClearChannelRecordMapper clearChannelRecordDao;
	@Autowired
	SettleMerchatProducerSender queueProducerSender;

	/**
	 * 
	 * @方法说明： 将结算批次数据入库，并将结算批次和明细数据发给账务系统
	 * @author chenyanming
	 * @时间：2016年11月23日下午4:14:58
	 */
	@Override
	@Transactional()
	public void getSettleChannelRecordAndSend(SettleChannelRecordVo model) {
		try {
			String transType = model.getTransType();
			SettleChannelRecord settleChannelRecord = new SettleChannelRecord();
			settleChannelRecord.setChannelCode(model.getChannelCode());// 通道编码
			settleChannelRecord.setChannelName(model.getChannelName());// 通道名称
			settleChannelRecord.setChannelType(model.getChannelType());// 通道类型
			settleChannelRecord.setCheckStatus(Constants.CHECK_STATUS_Y); // 对账状态是已对账
			settleChannelRecord.setCostAmount(model.getCostAmount()); // 总成本
			settleChannelRecord.setCurrency(Constants.CURRENCY_RMB);// 币种
			settleChannelRecord.setPayNum(model.getPayNum());// 交易总笔数
			settleChannelRecord.setSettleBath(IdBatch.getRandomString(0));// 结算流水号
			if(TransTypeUtil.isOutcome(transType)) {
				settleChannelRecord.setSettleStatus(Constants.SETTLE_STATUS_Y);// 出款类结算状态是已结算
			}else {
				settleChannelRecord.setSettleStatus(Constants.SETTLE_STATUS_D); // 结算状态是结算中
			}
			settleChannelRecord.setTotalAmount(model.getTotalAmount());// 总金额
			settleChannelRecord.setSettleTime(DateUtils.getDate());// 结算时间
			settleChannelRecord.setSettleCyc(model.getSettleCyc());// 计算周期
			settleChannelRecord.setCostTime(DateUtils.getDate());// 成本结算日期
			settleChannelRecord.setCostSettleCyc(model.getCostSettleCyc());// 成本结算周期

			// 用作返回数据用
			SettleChannelRecordPo settleChannelRecordPo = new SettleChannelRecordPo();
			settleChannelRecordPo.setChannelCode(model.getChannelCode());
			settleChannelRecordPo.setChannelName(model.getChannelName());
			settleChannelRecordPo.setChannelType(model.getChannelType());
			settleChannelRecordPo.setCheckStatus(Constants.CHECK_STATUS_Y); // 对账状态是已对账
			settleChannelRecordPo.setCostAmount(model.getCostAmount()); // 总成本
			settleChannelRecordPo.setCurrency(Constants.CURRENCY_RMB);
			settleChannelRecordPo.setPayNum(model.getPayNum());
			settleChannelRecordPo.setSettleBath(settleChannelRecord.getSettleBath());
			if(TransTypeUtil.isOutcome(transType)) {
				settleChannelRecordPo.setSettleStatus(Constants.SETTLE_STATUS_Y);// 出款类结算状态是已结算
			}else {
				settleChannelRecordPo.setSettleStatus(Constants.SETTLE_STATUS_D); // 结算状态是结算中
			}
			settleChannelRecordPo.setTotalAmount(model.getTotalAmount());
			settleChannelRecordPo.setSettleTime(DateUtils.getDate());
			settleChannelRecordPo.setSettleCyc(model.getSettleCyc());
			settleChannelRecordPo.setCostTime(settleChannelRecord.getCostTime());
			settleChannelRecordPo.setCostSettleCyc(model.getCostSettleCyc());
			settleChannelRecordPo.setTransType(transType);
			
			// 查询清算明细
			List<ClearChannelRecord> clearChannelRecords = clearChannelRecordDao.queryClearChannelRecords(settleChannelRecord, transType);
			if(clearChannelRecords == null || clearChannelRecords.size() == 0) {
				return;
			}
			// 结算数据入库
			settleChannelRecordDao.insert(settleChannelRecord);
			logger.info("结算批次数据入库成功!,结算批次号为:{}" + settleChannelRecord.getSettleBath());

			// 将结算批次号更新到清算表中,消费和充值清算记录结算状态改为结算中
			//for (ClearChannelRecord clearChannelRecord : clearChannelRecords) {
			String settleStatus = null;
			if(TransTypeUtil.isIncome(transType)) {
				settleStatus = Constants.SETTLE_STATUS_D;
			}
			clearChannelRecordDao.updateSettleNoToClearChannelRecord(settleChannelRecord, transType, settleStatus);
			//}
			
			// 将结算批次数据和对应的结算明细数据整合
			SettleChannelMessage settleChannelMessage = this.getMessageList(settleChannelRecordPo, clearChannelRecords);
			
			
			/*if (settleChannelRecordMap.get(model.getChannelCode()) != null) { // 已经有记录
				List<SettleChannelMessage> messageList = settleChannelRecordMap.get(model.getChannelCode());
				// 整合结算数据和清算明细
				this.getMessageList(messageList, settleChannelRecordPo, clearChannelRecords);
				settleChannelRecordMap.put(settleChannelRecord.getChannelCode(), messageList);
			} else { // 无记录
				List<SettleChannelMessage> messageList = new ArrayList<SettleChannelMessage>();
				this.getMessageList(messageList, settleChannelRecordPo, clearChannelRecords);
				settleChannelRecordMap.put(settleChannelRecord.getChannelCode(), messageList);

			}*/

			// 出款类不会将结算信息发送到消息队列
			if(TransTypeUtil.isOutcome(transType)) {
				logger.info("出款类 只生成结算批次，不发消息给账务系统，结算批次号为:{}" + settleChannelRecord.getSettleBath());
				return;
			}
			// 将结算批次和明细发送到消息队列
			queueProducerSender.sendChannelDataToQueue(settleChannelMessage);
		} catch (Exception e) {
			logger.error("通道侧结算记录入库失败{},{}", e, model.toString());
			throw new RuntimeException();
		}
	
	}

	/**
	 * 
	 * @方法说明：整合结算数据和清算明细
	 * @author chenyanming
	 * @param messageList
	 * @param settleChannelRecordPo
	 * @param clearChannelRecords
	 * @时间：2016年10月20日上午10:59:30
	 */
	public SettleChannelMessage getMessageList(SettleChannelRecordPo settleChannelRecordPo,
			List<ClearChannelRecord> clearChannelRecords) {
		try {
			SettleChannelMessage settleChannelMessage = new SettleChannelMessage();
			settleChannelMessage.setSettleChannelRecordPo(settleChannelRecordPo);// 结算批次
			List<ClearDetailMessage> list = new ArrayList<ClearDetailMessage>();
			// 清算明细数据
			/*for (ClearChannelRecord clearChannelRecord : clearChannelRecords) {
				ClearDetailMessage clearDetailMessage = new ClearDetailMessage();
				clearDetailMessage.setPaymentId(clearChannelRecord.getPaymentId());
				clearDetailMessage.setSuccessAmount(clearChannelRecord.getSuccessAmount());
				clearDetailMessage.setTransType(clearChannelRecord.getTransType());
				if(ChargeDeductType.INTERNAL_DEDUCT.getValue().equals(clearChannelRecord.getCostWay())) { // 手续费扣除方法是 坐扣
					clearDetailMessage.setCostAmount(clearChannelRecord.getCostAmount());
				}else if(ChargeDeductType.EXTERNAL_DEDUCT.getValue().equals(clearChannelRecord.getCostWay())) { // 手续费扣除方法是 外扣
					clearDetailMessage.setCostAmount(new BigDecimal(0));
				}else {
					clearDetailMessage.setCostAmount(new BigDecimal(0));
				}
				
				clearDetailMessage.setTransNo(clearChannelRecord.getTransNo());
				list.add(clearDetailMessage);
			}*/
			settleChannelMessage.setClearDetailMessage(list);
			return settleChannelMessage;
		} catch (Exception e) {
			logger.error("整合结算数据和清算明细失败:{}",settleChannelRecordPo, e);
			throw new RuntimeException();
		}
		
	}

}
