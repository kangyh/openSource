package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.heepay.rpc.payment.service.AccountRecordService;

/**
 * 
 * 
 * 描    述：通道侧和用户侧补账客户端
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月8日上午9:55:45 
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
public class SettleDifferFillBillClient extends BaseClientDistribute {
	private static final String SERVICENAME = "AccountRecordServiceImpl";
	
	private static final String NODENAME = "payment_rpc";
	
	private static final Logger log = LogManager.getLogger();

	@Resource(name = "paymentServerClient")
	private ThriftClientProxy clientProxy;
  
	
	 @Override
	  public void setServiceName(){
	    ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	  }
	  
	  @Override
	  public void setNodename() {
	    ClientThreadLocal.getInstance().setNodename(NODENAME);
	  }
	  
	  @Override
	  public ThriftClientProxy getClientProxy() {
	    return clientProxy;
	  }
	  public AccountRecordService.Client getClient(){
		    this.setServiceName();
		    this.setNodename();
		    this.setTMultiplexedProtocol();
		    return new AccountRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	 }

	  /**
	   * 
	   * @方法说明：充值通道侧补账
	   * @author chenyanming
	   * @param settleDifferChannel
	   * @时间：2016年11月3日下午4:54:03
	   */
	public int doChargeChannelFillBill(String channelCode,String payType,String paymentId, String transNo, String errorBath,
			String supplementPayAmount,String feeAmount) {
		AccountRecordService.Client client = this.getClient();
		try {
			int flag = client.changeLongSupplementChannelAccount(channelCode, payType, paymentId, transNo, errorBath, supplementPayAmount, feeAmount);
			return flag;
		} catch (Exception e) {
			log.error("充值通道侧补账异常！支付单号为:", paymentId, e);
		} finally {
			this.close();
		}
		return 0;
	}

	/**
	 * 
	 * @方法说明：充值商户侧补账
	 * @author chenyanming
	 * @param settleDifferMerchant
	 * @return
	 * @时间：2016年11月8日上午10:19:12
	 */
	public int doChargeMerchantFillBill(long merchantId, String channelCode, String payType, String paymentId, String transNo, String errorBath,
			String supplementPayAmount, String feeAmount) {
		AccountRecordService.Client client = this.getClient();
		try {
			int flag = client.changeLongSupplementMerchantAccount(merchantId, 
					channelCode, payType, paymentId, transNo, errorBath, supplementPayAmount, feeAmount);
			return flag;
		} catch (Exception e) {
			log.error("充值商户侧补账异常！支付单号为:", paymentId, e);
		}finally {
			this.close();
		}
		return 0;
	}
	
	/**
	   * 
	   * @方法说明：消费通道侧补账
	   * @author chenyanming
	   * @param settleDifferChannel
	   * @时间：2016年11月3日下午4:54:03
	   */
	public int doPayChannelFillBill(String channelCode,String paymentId, String transNo, String errorBath, String payType,
			String supplementPayAmount,String feeAmount) {
		AccountRecordService.Client client = this.getClient();
		try {
			int flag = client.payLongSupplementChannelAccount(channelCode, paymentId, transNo, errorBath, payType, supplementPayAmount, feeAmount);
			return flag;
		} catch (Exception e) {
			log.error("消费通道侧补账异常！支付单号为:", paymentId, e);
		} finally {
			this.close();
		}
		return 0;
	}
	
	/**
	 * 
	 * @方法说明：消费商户侧补账
	 * @author chenyanming
	 * @param settleDifferMerchant
	 * @return
	 * @时间：2016年11月8日上午10:19:12
	 */
	public int doPayMerchantFillBill(long merchantId, String channelCode, String paymentId, String transNo, String errorBath, String transType, String payType,  
			String supplementPayAmount, String feeAmount) {
		AccountRecordService.Client client = this.getClient();
		try {
			int flag = client.payLongSupplementMercahntAccount(merchantId, channelCode, 
					paymentId, transNo, errorBath, transType, payType, supplementPayAmount, feeAmount);
			return flag;
		} catch (Exception e) {
			log.error("消费商户侧补账异常！支付单号为:", paymentId, e);
		}finally {
			this.close();
		}
		return 0;
	}
	
	/**
	 * 
	 * @方法说明：出款类通道侧补账
	 * @author chenyanming
	 * @param settleDifferMerchant
	 * @return
	 * @时间：2016年11月8日上午10:19:12
	 */
	public int doExpendChannelFillBill(String paymentId, String transNo, String errorBath, String transType, String payType, String channelCode,   
			String supplementPayAmount, String feeAmount) {
		AccountRecordService.Client client = this.getClient();
		/*try {
			int flag = client.supplementOutChannelAccount(paymentId, transNo, errorBath, transType, payType, channelCode, supplementPayAmount, feeAmount);
			return flag;
		} catch (Exception e) {
			log.error("出款类通道侧补账异常！支付单号为:", paymentId, e);
		}finally {
			this.close();
		}*/
		return 0;
	}
	
	/**
	 * 
	 * @方法说明：代收通道补账
	 * @author chenyanming
	 * @param channelCode
	 * @param paymentId
	 * @param transNo
	 * @param settleId
	 * @param payType
	 * @param supplementAmount
	 * @param feeAmount
	 * @return
	 * @时间：2017年5月17日下午2:00:11
	 */
	public int doBatcolChannelFillBill(String channelCode, String paymentId, String transNo, String settleId,String payType, String supplementAmount, String feeAmount) {
		AccountRecordService.Client client = this.getClient();
		try {
			int flag = client.bacolLongSupplementChannelAccount(channelCode, paymentId, transNo, settleId,payType, supplementAmount, feeAmount);
			return flag;
		} catch (Exception e) {
			log.error("代收通道侧补账异常！支付单号为:", paymentId, e);
		} finally {
			this.close();
		}
		return 0;
		
	}
	
	/**
	 * 
	 * @方法说明：代收商户补账
	 * @author chenyanming
	 * @param merchantId
	 * @param channelCode
	 * @param paymentId
	 * @param transNo
	 * @param settleId
	 * @param transType
	 * @param payType
	 * @param supplementAmount
	 * @param feeAmount
	 * @return
	 * @时间：2017年5月17日下午2:09:10
	 */
	public int doBatcolMerchantFillBill(long merchantId, String channelCode,String paymentId, String transNo, String settleId,String transType, String payType, String supplementAmount, String feeAmount) {
		AccountRecordService.Client client = this.getClient();
		try {
			int flag = client.bacolLongSupplementMerchantAccount(merchantId, channelCode,paymentId, transNo, settleId,transType, payType, supplementAmount, feeAmount);
			return flag;
		} catch (Exception e) {
			log.error("代收商户侧补账异常！支付单号为:", paymentId, e);
		} finally {
			this.close();
		}
		return 0;
	}
	
	
	// 出款类挂账
		public int outHangAccount(Integer merchantId, String transType, String settleBath, String paymentId, String transNo, String channelCode,
				String hangOutAmount, String hangOutFeeAmount) {
			AccountRecordService.Client client = this.getClient();
			try {
				int flag = client.hangOutAccount(merchantId, transType, settleBath, paymentId, transNo, channelCode, hangOutAmount,
						hangOutFeeAmount);
				return flag;
			} catch (Exception e) {
				log.error("出款类挂账异常！", transNo, e);
			} finally {
				this.close();
			}
			return 0;
		}
		

		// 入款类挂账
		public int inHangAccount(Integer merchantId, String transType, String settleBath, String paymentId, String transNo, String channelCode,
				String hangInAmount, String hangInFeeAmount) {
			AccountRecordService.Client client = this.getClient();
			try {
				int flag = client.hangInAccount(merchantId, transType, settleBath, paymentId, transNo, channelCode, hangInAmount,
						hangInFeeAmount);
				return flag;
			} catch (Exception e) {
				e.printStackTrace();
				log.info("入款类挂账异常！", transNo, e);
			} finally {
				this.close();
			}
			return 0;
		}
}
