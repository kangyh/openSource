package com.heepay.billing.client;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.heepay.common.util.Constants;
import com.heepay.enums.billing.BillingDifferType;
import com.heepay.rpc.billing.model.DoneSettleDifferRecordModel;
import com.heepay.rpc.billing.service.DoneSettleDifferRecordService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;

/**
 * *
 * 
 * 
 * 描 述：差异单处理客户端
 *
 * 创 建 者： wangjie 
 * 创建时间： 2016年9月25日下午3:38:14 
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
public class DoneSettleDifferRecordClient extends BaseClient {

	private static final String SERVICENAME = "DoneSettleDifferRecordServiceImpl";

	private static final String NODENAME = Constants.Clear.BILLINGRPC;

	private static final Logger logger = LogManager.getLogger();

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);

	}

	public DoneSettleDifferRecordService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new DoneSettleDifferRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	}
	
	/**
	 * 查找差异类型为未知的差异
	 * @return
	 */
	public List<DoneSettleDifferRecordModel> selectSettleDifferRecord() {
		DoneSettleDifferRecordService.Client client = this.getClient();
		List<DoneSettleDifferRecordModel> result = null;
		try {
			result = client.getSettleDifferRecord(BillingDifferType.BDTYPEW.getValue());

			logger.info("差异单处理定时任务：" + result.size());
		} catch (Exception e) {
			logger.error("差异单处理定时任务出错" + e.getMessage());
		} finally {
			this.close();
		}
		return result;
	}

	/**
	 * 调用交易 系统查询接口后回填差异表
	 * @param sdr
	 * @return
	 */
	public int updateselectSettleDifferRecord(DoneSettleDifferRecordModel sdr) {
		DoneSettleDifferRecordService.Client client = this.getClient();
		int result = 0;
		try {
			result = client.updateSettleDifferRecor(sdr);
		} catch (Exception e) {
			logger.error("差异单处理后更新差异表数据定时任务出错" + e.getMessage());
		} finally {
			this.close();
		}
		return result;
	}

}
