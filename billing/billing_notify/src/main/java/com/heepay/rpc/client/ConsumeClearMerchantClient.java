package com.heepay.rpc.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.model.ClearMerchantRecordModel;
import com.heepay.rpc.billing.service.ClearMerchantRecordService;


/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2016年9月8日下午4:56:27
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
public class ConsumeClearMerchantClient extends BaseClient{

	private static final String SERVICENAME = "ClearMerchantRecordServiceImpl";

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

	public ClearMerchantRecordService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new ClearMerchantRecordService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}
	/**
	 * @描述 将接收到的商户侧清算记录发送到server
	 * @param ob
	 * @return
	 */
	public boolean insertMerchantRecord(ClearMerchantRecordModel ob) {
		ClearMerchantRecordService.Client client = this.getClient();
		try {
			 client.saveClearMerchantRecord(ob);
			 return true;
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return false;
	}
	
	/**
	 * 账务系统返回结果，更新清结算
	 * @param clearMerchantRecordModel
	 */
	public void updateClearMerchantRecord(ClearMerchantRecordModel clearMerchantRecordModel){
		ClearMerchantRecordService.Client client = this.getClient();
		try {
			 client.updateClearMerchantRecord(clearMerchantRecordModel);
			 
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
	}
	
	/**
	 * 
	 * @方法说明：商户、通道消息队列合并处理
	 * @author xuangang
	 * @param message
	 * @时间：2017年5月19日下午3:17:35
	 */
	public void settleDataSave(String message){
		
		ClearMerchantRecordService.Client client = this.getClient();
		try {
			 client.settleDataSave(message);
			 
		} catch (TException e) {			
			logger.error("商户、通道消息队列合并处理失败, thrift 连接异常：{}", message, e);    
			
		}finally {
			this.close();
		}	
		
	}
	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param message
	 * @时间：2017年8月14日下午6:18:44
	 */
    public void saveClearExceptionData(String message){
		
		ClearMerchantRecordService.Client client = this.getClient();
		try {
			 client.saveClearExceptionData(message);
			 
		} catch (TException e) {			
			logger.error("清算数据存在空值, thrift 连接异常：{}", message, e);    
			
		}finally {
			this.close();
		}	
		
	}
}
