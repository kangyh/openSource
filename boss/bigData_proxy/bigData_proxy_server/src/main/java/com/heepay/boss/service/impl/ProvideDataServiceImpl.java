package com.heepay.boss.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.boss.client.BillingAPIClient;
import com.heepay.boss.client.BossRpcCLient;
import com.heepay.boss.dao.B4ChargeRecordMapper;
import com.heepay.boss.dao.ClearChannelRecordMapper;
import com.heepay.boss.dao.ClearMerchantRecordMapper;
import com.heepay.boss.dao.SettleDifferRecordMapper;
import com.heepay.boss.entity.ClearChannelRecord;
import com.heepay.boss.entity.ClearMerchantRecord;
import com.heepay.boss.entity.SettleDifferRecord;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.vo.BossRule;
import com.heepay.enums.risk.JobStatus;
import com.heepay.rpc.boss.service.ProvidedDataService;
import com.heepay.rpc.service.RpcService;

/**
 * *
 * 
 * 
 * 描 述：提供数据源实现类
 *
 * 创 建 者： wangjie 创建时间： 2017年5月31日下午3:57:11 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
@RpcService(name = "ProvideDataServiceImpl", processor = ProvidedDataService.Processor.class)
public class ProvideDataServiceImpl implements com.heepay.rpc.boss.service.ProvidedDataService.Iface {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private BillingAPIClient billingAPIClient;

	@Autowired
	private B4ChargeRecordMapper b4ChargeRecordDao;

	@Autowired
	private ClearChannelRecordMapper clearChannelRecordDaoImpl;

	@Autowired
	private ClearMerchantRecordMapper clearMerchantRecordDaoImpl;

	@Autowired
	private SettleDifferRecordMapper settleDifferRecordDaoImpl;

	@Autowired
	private DataStoreServer dataStoreServer;

	@Autowired
	private BossRpcCLient bossRpcCLient;

	@Override
	public void queryDate() throws TException {
		logger.info("提供数据源任务开始执行");
		JsonMapperUtil jsonUtil = new JsonMapperUtil();
		// 从清结算收集数据
		try {
			String bossRuleEntity = bossRpcCLient.selectRule(JobStatus.PREDO.getValue());
			logger.info("boss规则{}", bossRuleEntity);
			Map<String, Object> mapMessage1 = JsonMapperUtil.nonEmptyMapper().fromJson(bossRuleEntity, Map.class);
			if (mapMessage1 != null && mapMessage1.containsKey("startDate") && mapMessage1.containsKey("endDate")) {

				BossRule bossRule = new BossRule();
				bossRule.setRuleId((Long.valueOf(mapMessage1.get("ruleId").toString())));
				bossRule.setJobStartTime(new Date());
				long begin = System.currentTimeMillis();
				bossRule.setJobStatus(JobStatus.DOING.getValue());
				String msg1 = bossRpcCLient.updateRule(jsonUtil.toJson(bossRule));
				if ("1".equals(msg1)) {
					logger.info("更新成功");
				} else {
					logger.info("更新失败");
				}
				if (mapMessage1.get("startDate") != null && mapMessage1.get("endDate") != null) {
					
					Date startTime = new Date((Long.valueOf(mapMessage1.get("startDate").toString())));
					Date endDate = new Date((Long.valueOf(mapMessage1.get("endDate").toString())));
					logger.info("boss规则日期{},{}", startTime,endDate);
					
					List<ClearMerchantRecord> merchantRecordList = clearMerchantRecordDaoImpl.queryForBigData(startTime,
							endDate);
					logger.info("清结算商户测数据merchantRecordList几条{}", merchantRecordList.size());
					if (!merchantRecordList.isEmpty()) {
						for (ClearMerchantRecord clearMerchantRecord : merchantRecordList) {
							// 根据交易单号通道信息
							ClearChannelRecord clearChannelRecord = clearChannelRecordDaoImpl
									.selectByTranNo(clearMerchantRecord.getTransNo());
							if (null == clearChannelRecord) {
								// 查询差错表
								SettleDifferRecord settleDifferRecord = settleDifferRecordDaoImpl
										.selectByTransNo(clearMerchantRecord.getTransNo());
								dataStoreServer.convertObject(clearMerchantRecord, settleDifferRecord);
							} else {
								dataStoreServer.convertObject(clearMerchantRecord, clearChannelRecord);
							}
						}
						//
						bossRule.setJobEndTime(new Date());
						bossRule.setJobStatus(JobStatus.SUCCES.getValue());
						long end = System.currentTimeMillis();
						long result = end - begin;
						bossRule.setTakeTime(String.valueOf(result));
						String msg = bossRpcCLient.updateRule(jsonUtil.toJson(bossRule));
						if ("1".equals(msg)) {
							logger.info("更新成功");
						} else {
							logger.info("更新失败");
						}
					}
				}

			}
		} catch (Exception e) {
			logger.error("提供数据源任务出错{}", e);
		}

	}

}
