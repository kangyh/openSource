package com.heepay.rpc.billing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.date.DateUtils;
import com.heepay.rpc.billing.model.SettleBatchMsgModel;
import com.heepay.rpc.billing.model.SettleChannelModel;

/**
 * 
 * 
 * 描    述：根据结算批次号分页查询结算明细数据
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年7月6日下午5:11:58 
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
public class QueryChannelSettleMsgServiceImpl {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	ClearChannelRecordMapper clearChannelRecordDao;
	
	/**
	 * 
	 * @方法说明：根据结算批次号分页查询结算明细数据
	 * @author chenyanming
	 * @param settleBatch
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @时间：2017年7月6日下午5:16:12
	 */
	public SettleBatchMsgModel querySettleBathMsg(String settleBath, int pageNum, int pageSize) {
		logger.info("通道侧分页查询结算明细开始，结算批次号、页码和每页条数为:{},{},{}",settleBath, pageNum, pageSize);
		SettleBatchMsgModel settleBatchMsgModel = new SettleBatchMsgModel();
		settleBatchMsgModel.setSettleBatch(settleBath);
		settleBatchMsgModel.setPageNum(pageNum);
		settleBatchMsgModel.setPageSize(pageSize);
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			if(pageNum == -1) {
				// 查询全部明细
				paraMap.put("settleBath", settleBath);
			}else {
				// 分页查询结算明细
				paraMap.put("settleBath", settleBath);
				paraMap.put("pageSize", pageSize);			
				paraMap.put("start",(pageNum-1)*pageSize);
			}
			List<ClearChannelRecord> clearChannelRecordList = clearChannelRecordDao.querySettleBathMsgBySettleBath(paraMap);
			List<SettleChannelModel> settleChannelList = new ArrayList<SettleChannelModel>();
			for (ClearChannelRecord clearChannelRecord : clearChannelRecordList) {
				SettleChannelModel settleChannelModel = new SettleChannelModel();
				settleChannelModel.setSuccessAmount(clearChannelRecord.getSuccessAmount().toString());
				settleChannelModel.setCostAmount(clearChannelRecord.getCostAmount().toString());
				settleChannelModel.setTransNo(clearChannelRecord.getTransNo());
				settleChannelModel.setTransType(clearChannelRecord.getTransType());
				settleChannelModel.setPaymentId(clearChannelRecord.getPaymentId());
				settleChannelModel.setSettleTime(DateUtils.getDateStr(clearChannelRecord.getSettleTime(), "yyyy-MM-dd"));
				settleChannelList.add(settleChannelModel);
			}
			settleBatchMsgModel.setSettleChannelList(settleChannelList);
			logger.info("通道侧分页查询结算明细结束，结算批次号和查询到的条数为:{},{}",settleBath, settleChannelList.size());
			return settleBatchMsgModel;
		} catch (Exception e) {
			logger.error("通道侧账务系统查询清结算系统异常，结算批次：{}", settleBath, e);
		}
		return settleBatchMsgModel;
	}

}
