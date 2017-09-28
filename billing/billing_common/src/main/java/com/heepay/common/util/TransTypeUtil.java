/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年8月4日下午4:25:33
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
package com.heepay.common.util;

import com.heepay.enums.TransType;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年8月4日下午4:25:33
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
public class TransTypeUtil {

	/**
	 * 
	 * @方法说明：是否是入款 // 充值、消费(将充值、消费合并处理、存管充值) 、代收
	 * @author xuangang
	 * @param transType
	 * @return
	 * @时间：2017年8月7日下午1:33:34
	 */
	public static boolean isIncome(String transType){
		
		if (TransType.CHARGE.getValue().equals(transType)
				|| TransType.PAY.getValue().equals(transType)
				|| TransType.DEPOSIT_PAY.getValue().equals(transType)
				|| TransType.BATCHCOLLECTION.getValue().equals(transType)) {
			
              return true;
		}
		return false;
	}
	/**
	 * 
	 * @方法说明： 出款类，包括退款，批付，提现、实名认证（新增实名认证交易类型）、存管提现
	 * @author xuangang
	 * @param transType
	 * @return
	 * @时间：2017年8月7日下午1:42:20
	 */
	public static boolean isOutcome(String transType){
		if(TransType.REFUND.getValue().equals(transType)
				|| TransType.WITHDRAW.getValue().equals(transType)
				|| TransType.BATCHPAY.getValue().equals(transType)
				|| TransType.SHARE.getValue().equals(transType)
				|| TransType.TRANSFER.getValue().equals(transType)
				|| TransType.PLAY_MONEY.getValue().equals(transType)
				|| TransType.REAL_NAME.getValue().equals(transType)
				|| TransType.DEPOSIT_WITHDRAW.getValue().equals(transType)) {
			return true;
		}
		return false;
	}
}
