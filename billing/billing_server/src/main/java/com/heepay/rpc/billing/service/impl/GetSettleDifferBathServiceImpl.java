package com.heepay.rpc.billing.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.SettleDifferChannelMapper;
import com.heepay.billing.dao.SettleDifferMerchantMapper;
import com.heepay.billing.dao.SettleDifferRecordMapper;
import com.heepay.billing.entity.SettleDifferRecord;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.rpc.billing.service.ISettleDifferFillAndWithdrawBillService;
import com.heepay.rpc.service.RpcService;

/**
 * 
 * 
 * 描    述：汇总差错批次，做撤补账处理
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年10月27日下午5:34:08 
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
@RpcService(name = "GetSettleDifferBathServiceImpl", processor = com.heepay.rpc.billing.service.SettleDifferBathService.Processor.class)
@Component
public class GetSettleDifferBathServiceImpl implements com.heepay.rpc.billing.service.SettleDifferBathService.Iface{
	
	@Autowired
	SettleDifferMerchantMapper settleDifferMerchantDaoImpl;
	@Autowired
	SettleDifferChannelMapper settleDifferChannelDaoImpl;
	@Autowired
	SettleDifferRecordMapper settleDifferRecordDaoImpl;
	@Autowired
	ISettleDifferFillAndWithdrawBillService settleDifferFillAndWithdrawBillServiceImpl;
	
	private static final Logger logger = LogManager.getLogger();

	/**
	 * 
	 * @方法说明： 汇总差错批次数据，做撤账和补账操作
	 * @author chenyanming
	 * @时间：2016年11月10日下午2:10:20
	 */
	@Override
	public void getSettleDifferBath() {
		try {
			logger.info("差错批次数据入库，补撤账开始");
			// 查询所有的未记账的数据 ， 非分润单，记账状态为未记账，补单或撤单的数据
			List<SettleDifferRecord> settleDifferRecordList = settleDifferRecordDaoImpl.querySettleDifferRecordData(ClearingCheckStatus.CHECKSTATUSN.getValue());
			if(settleDifferRecordList == null || settleDifferRecordList.size() == 0) {
				logger.info("没有需要撤账或补账的数据");
				return;
			}
			logger.info("共汇总到" + settleDifferRecordList.size() + "条差错批次数据");
			for (SettleDifferRecord settleDifferRecord : settleDifferRecordList) {
				try {
					// 差错批次数据入库，补账和撤账逻辑
					settleDifferFillAndWithdrawBillServiceImpl.fillAndWithdrawBillMethod(settleDifferRecord);
				} catch (Exception e) {
					logger.error("差错批次数据入库，补撤账失败！:{}" ,settleDifferRecord,e);
				}
			}
			logger.info("差错批次数据入库，撤账补账结束");
		} catch (Exception e) {
			logger.error("汇总差错批次数据失败！" ,e);
			
		}
	}
	
	/**
	 * 
	 * @方法说明： 出款类差错批次数据处理，只处理审核通过的数据 (已无用)
	 * @author chenyanming
	 * @throws TException
	 * @时间：2016年11月15日上午11:19:15
	 */
	@Override
	public void doExpendDifferBath() {
		
	}

}
















