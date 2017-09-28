/**     
*   Copyright © since 2008. All Rights Reserved 
*   汇元银通（北京）在线支付技术有限公司   www.heepay.com    
*/
    
package com.heepay.manage.common.cache;

import redis.clients.jedis.JedisCluster;

import com.heepay.billingutils.payment.common.Global_Redis_Key;
import com.heepay.redis.JedisClusterUtil;

/**          
* 
* 描    述：业务表主键生成器
*
* 创 建 者： 刘栋  
* 创建时间： 2016年9月9日 上午11:10:25 
* 创建描述：生成业务表主键，定义各种主键生成规则
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

public class PrimaryKeyCreator {

	private static JedisCluster jc = JedisClusterUtil.getJedisCluster();

	//商户账户流水ID
	public static final String MERCCHANT_ACCOUNT_ID="merchant_account_id";

	//账务明细ID
	public static final String  MERCHANT_LOG_DETAIL_ID = "merchant_log_detail_id";

	//商户待结算账户流水ID
	public static final String MERCCHANT_SETTLEMENT_ACCOUNT_ID = "merchant_settlement_account_id";

	/**
	 *
	 * @discription  商户账户生成规则
	 * @author houjc@9186.com
	 * @created 2016年10月10日 下午1:40:02
	 * @return
	 */


	public static Long getMerchantAccountId(){
		Long merchantAccountId = jc.incr(MERCCHANT_ACCOUNT_ID);
		if (merchantAccountId==null || merchantAccountId < 0 || merchantAccountId < 20000000) {
			jc.incrBy(MERCCHANT_ACCOUNT_ID, 20000000);
			merchantAccountId=20000000L;
		}
		return 6802000000000000L+merchantAccountId*10000+1156;
	}

	public static Long getMerchantLogDetailId(){
		Long merchantLogDetialId = jc.incr(MERCHANT_LOG_DETAIL_ID);
		if (merchantLogDetialId==null || merchantLogDetialId < 0 || merchantLogDetialId < 1000000) {
			jc.incrBy(MERCHANT_LOG_DETAIL_ID, 1000000);
			merchantLogDetialId=1000000L;
		}
		return merchantLogDetialId;
	}

	/**
	 * Notify key
	* @discription
	* @author zhangchen       
	* @created 2017年3月16日 下午3:08:21     
	* @return
	 */
	public static Long getNotifyId(){
    Long notifyId = jc.incr(Global_Redis_Key.NOTIFY_ID);
    if (notifyId==null || notifyId < 0 || notifyId < 1000000) {
      jc.incrBy(Global_Redis_Key.NOTIFY_ID, 1000000);
      notifyId=1000000L;
    }
    return notifyId;
  }

	/**
	 * 商户待结算账户生成规则
	 * @return
	 */
	public static Long getMerchanSettlementAccountId(Long accountId){
		return accountId+1010000000000L;
	}

	/**
	 * 手动商户待结算账户生成规则
	 * @param accountId
	 * @return
	 */
	public static Long getManualMerchanSettlementAccountId(Long accountId){
		return accountId+1020000000000L;
	}

	public static Long getDepositoryMerchantAccountId(Long accountId){
		return accountId+1030000000000L;
}

    /**
     * 获取银行卡签约授权表--引用payment_server--的payment_common的方法，获取的bankcard_auth表的主键
     * @return authId
     * @author 郭正新
     */
    public static Long getAuthId(){
        Long authId = jc.incr(Global_Redis_Key.AUTH_ID);
        if (authId==null || authId < 0 || authId < 1000000) {
            jc.incrBy(Global_Redis_Key.AUTH_ID, 1000000);
            authId=1000000L;
        }
        return authId;
    }


}
