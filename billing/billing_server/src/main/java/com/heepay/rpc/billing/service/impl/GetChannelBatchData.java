package com.heepay.rpc.billing.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.entity.SettleChannelRecordVo;
import com.heepay.rpc.billing.service.IGetChannelBatchDataService;

/**
 * 
 * 
 * 描    述：生成结算数据和清算明细数据
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月28日下午9:25:06 
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
public class GetChannelBatchData {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	ClearChannelRecordMapper clearChannelRecordDao;
	@Autowired
	IGetChannelBatchDataService getChannelBatchDataServiceImpl;
	
	/**
	 * 
	 * @方法说明：生成结算数据和清算明细数据
	 * @author chenyanming
	 * @param checkBathno
	 * @return
	 * @throws TException
	 * @时间：2016年10月20日下午2:23:03
	 */
	public void getSettleChannelRecordMessage() {
		try {
			logger.info("通道侧汇总清算记录开始");
			// 查询结算相关数据
			List<SettleChannelRecordVo> settleChannelRecordVoList = clearChannelRecordDao.queryClearChannelRecordVo();
			if(settleChannelRecordVoList == null || settleChannelRecordVoList.size() == 0) {
				logger.info("没有需要结算的数据");
				return;
			}

			// 完善结算表，入库
			for (SettleChannelRecordVo model : settleChannelRecordVoList) {
				try {
					// 生成结算数据入库，并把结算批次和明细数据发送给账务系统
					getChannelBatchDataServiceImpl.getSettleChannelRecordAndSend(model);
				} catch (Exception e) {
					logger.error("通道侧汇总清算记录失败:{}" , model,e);
				}
			}
		} catch (Exception e) {
			logger.error("通道侧结算记录入库失败", e);
		}
		return;
	}

}
