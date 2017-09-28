/**
 *  
 */
package com.heepay.manage.modules.sys.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heepay.billingutils.payment.common.Global_Redis_Key;
import com.heepay.common.util.WebUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.payment.entity.BankcardAuth;
import com.heepay.manage.modules.payment.entity.BatchPayRecordDetail;
import com.heepay.manage.modules.payment.entity.CertificationRecord;
import com.heepay.manage.modules.payment.entity.ChargeRecord;
import com.heepay.manage.modules.payment.entity.GatewayRecord;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.entity.NotifyRecord;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.QualificationRecord;
import com.heepay.manage.modules.payment.entity.RefundRecord;
import com.heepay.manage.modules.payment.entity.WechatRecord;
import com.heepay.manage.modules.payment.entity.WithdrawRecord;
import com.heepay.manage.modules.payment.service.BankcardAuthService;
import com.heepay.manage.modules.payment.service.BatchPayRecordDetailService;
import com.heepay.manage.modules.payment.service.CertificationRecordService;
import com.heepay.manage.modules.payment.service.ChargeRecordService;
import com.heepay.manage.modules.payment.service.GatewayRecordService;
import com.heepay.manage.modules.payment.service.MerchantLogService;
import com.heepay.manage.modules.payment.service.NotifyRecordService;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.manage.modules.payment.service.QualificationRecordService;
import com.heepay.manage.modules.payment.service.RefundRecordService;
import com.heepay.manage.modules.payment.service.WechatRecordService;
import com.heepay.manage.modules.payment.service.WithdrawRecordService;
import com.heepay.manage.modules.share.entity.ShareAccountRecord;
import com.heepay.manage.modules.share.service.ShareAccountRecordService;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.redis.JedisClusterUtil;

import redis.clients.jedis.JedisCluster;

/**
 * 
* 
* 描    述：缓存设置菜单controller
*
* 创 建 者： yanxb  
* 创建时间： 2017年2月10日 下午1:44:54 
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
@Controller
@RequestMapping(value = "${adminPath}/sys/redis")
public class RedisController extends BaseController {

	@Autowired
	private PaymentRecordService paymentRecordService;
	
	@Autowired
	private ChargeRecordService chargeRecordService;
	
	@Autowired
	private WithdrawRecordService withdrawRecordService;
	
	@Autowired
	private BatchPayRecordDetailService batchPayRecordDetailService;
	
	@Autowired
	private GatewayRecordService gatewayRecordService;
	
	@Autowired
	private RefundRecordService refundRecordService;
	
	@Autowired
	private QualificationRecordService qualificationRecordService;
	
	@Autowired
	private ShareAccountRecordService shareAccountRecordService;
	
	@Autowired
	private CertificationRecordService certificationRecordService;
	
	@Autowired
	private WechatRecordService wechatRecordService;
	
	@Autowired
	private NotifyRecordService notifyRecordService;
	
	@Autowired
	private MerchantLogService merchantLogService;
	
	@Autowired
	private MerchantAccountService merchantAccountService;
	
	@Autowired
	private BankcardAuthService bankcardAuthService;
	
	private static Logger log = LogManager.getLogger();
	
	private static JedisCluster jedis = JedisClusterUtil.getJedisCluster();
	
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/redisForm";
	}
	
	/**
	 * 
	* @discription 重新设置缓存
	* @author yanxb       
	* @created 2017年2月13日 下午3:55:30     
	* @return
	 */
	@RequestMapping(value = {"resetRedis", ""})
	public void resetRedis(HttpServletRequest request, HttpServletResponse response) throws TException {
		User user = UserUtils.getUser();
		log.info("用户{}操作缓存设置开始！开始时间={}",user.getName(),DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		//payment_record  payment_id
		PaymentRecord paymentRecord = paymentRecordService.getPaymentId();
		if(null == paymentRecord){
			log.info("缓存设置，paymentRecord=null");
		}else{
			log.info("缓存设置，paymentRecord={}",paymentRecord.toString());
			jedis.set(Global_Redis_Key.PAYMENT_ID, paymentRecord.getPaymentId());
			log.info("缓存设置，payment_id缓存设置完成");
			log.info("缓存设置，当前payment_id最大值是{}",jedis.get(Global_Redis_Key.PAYMENT_ID));
		}
		//charge  charge_id
		ChargeRecord chargeRecord = chargeRecordService.getChargeId();
		if(null == chargeRecord){
			log.info("缓存设置，chargeRecord=null");
		}else{
			log.info("缓存设置，chargeRecord={}",chargeRecord.toString());
			jedis.set(Global_Redis_Key.CHARGE_ID, chargeRecord.getChargeId());
			log.info("缓存设置，charge_id缓存设置完成");
			log.info("缓存设置，当前charge_id最大值是{}",jedis.get(Global_Redis_Key.CHARGE_ID));
		}
		//withdraw  withdraw_id
		WithdrawRecord withdrawRecord = withdrawRecordService.getWithdrawId();
		if(null == withdrawRecord){
			log.info("缓存设置，withdrawRecord=null");
		}else{
			log.info("缓存设置，withdrawRecord={}",withdrawRecord.toString());
			jedis.set(Global_Redis_Key.WITHDRAW_ID, withdrawRecord.getWithdrawId());
			log.info("缓存设置，withdraw_id缓存设置完成");
			log.info("缓存设置，当前withdraw_id最大值是{}",jedis.get(Global_Redis_Key.WITHDRAW_ID));
		}
		//batch_pay  batch_pay_id batch_id
		BatchPayRecordDetail detail = batchPayRecordDetailService.getBtachPayId();
		if(null == detail){
			log.info("缓存设置，BatchPayRecordDetail=null");
		}else{
			log.info("缓存设置，BatchPayRecordDetail={}",detail.toString());
			jedis.set(Global_Redis_Key.BATCH_PAY_ID, detail.getBatchPayId());
			jedis.set("batch_id", detail.getBatchId());
			log.info("缓存设置，batch_pay_id缓存设置完成");
			log.info("缓存设置，当前batch_pay_id最大值是{}",jedis.get(Global_Redis_Key.BATCH_PAY_ID));
		}
		//GatewayRecord  gateway_id
		GatewayRecord gatewayRecord = gatewayRecordService.getGatewayId();
		if(null == gatewayRecord){
			log.info("缓存设置，gatewayRecord=null");
		}else{
			log.info("缓存设置，gatewayRecord={}",gatewayRecord.toString());
			jedis.set(Global_Redis_Key.GATEWAY_ID, gatewayRecord.getGatewayId());
			log.info("缓存设置，gateway_id缓存设置完成");
			log.info("缓存设置，当前gateway_id最大值是{}",jedis.get(Global_Redis_Key.GATEWAY_ID));
		}
		//RefundRecord  refund_id
		RefundRecord refundRecord = refundRecordService.getRefundId();
		if(null == refundRecord){
			log.info("缓存设置，RefundRecord=null");
		}else{
			log.info("缓存设置，RefundRecord={}",refundRecord.toString());
			jedis.set(Global_Redis_Key.REFUND_ID, refundRecord.getRefundId());
			log.info("缓存设置，refund_id缓存设置完成");
			log.info("缓存设置，当前refund_id最大值是{}",jedis.get(Global_Redis_Key.REFUND_ID));
		}
		//QualificationRecord  qualify_id
		QualificationRecord qualificationRecord = qualificationRecordService.getQualifyId();
		if(null == qualificationRecord){
			log.info("缓存设置，qualificationRecord=null");
		}else{
			log.info("缓存设置，qualificationRecord={}",qualificationRecord.toString());
			jedis.set("qualify_id", qualificationRecord.getQualifyId());
			log.info("缓存设置，qualify_id缓存设置完成");
			log.info("缓存设置，当前qualify_id最大值是{}",jedis.get("qualify_id"));
		}
		//ShareAccountRecord  share_id
		ShareAccountRecord shareAccountRecord = shareAccountRecordService.getShareId();
		if(null == shareAccountRecord){
			log.info("缓存设置，shareAccountRecord=null");
		}else{
			log.info("缓存设置，shareAccountRecord={}",shareAccountRecord.toString());
			jedis.set("share_id", shareAccountRecord.getShareId());
			log.info("缓存设置，share_id缓存设置完成");
			log.info("缓存设置，当前share_id最大值是{}",jedis.get("share_id"));
		}
		//CertificationRecord  certification_id
		CertificationRecord certificationRecord = certificationRecordService.getCertificationId();
		if(null == certificationRecord){
			log.info("缓存设置，certificationRecord=null");
		}else{
			log.info("缓存设置，certificationRecord={}",certificationRecord.toString());
			jedis.set("certification_id", certificationRecord.getCertificationId());
			log.info("缓存设置，certification_id缓存设置完成");
			log.info("缓存设置，当前certification_id最大值是{}",jedis.get("certification_id"));
		}
		//WechatRecord  wechat_id
		WechatRecord wechatRecord = wechatRecordService.getWechatId();
		if(null == wechatRecord){
			log.info("缓存设置，wechatRecord=null");
		}else{
			log.info("缓存设置，wechatRecord={}",wechatRecord.toString());
			jedis.set("wechat_id", wechatRecord.getWechatId());
			log.info("缓存设置，wechat_id缓存设置完成");
			log.info("缓存设置，当前wechat_id最大值是{}",jedis.get("wechat_id"));
		}
		//NotifyRecord  notify_id
		NotifyRecord notifyRecord = notifyRecordService.getNotifyId();
		if(null == notifyRecord){
			log.info("缓存设置，notifyRecord=null");
		}else{
			log.info("缓存设置，notifyRecord={}",notifyRecord.toString());
			jedis.set("notify_id", String.valueOf(notifyRecord.getNotifyId()));
			log.info("缓存设置，notify_id缓存设置完成");
			log.info("缓存设置，当前notify_id最大值是{}",jedis.get("notify_id"));
		}
		//MerchantLog  merchant_log_id
		MerchantLog merchantLog = merchantLogService.getLogId();
		if(null == merchantLog){
			log.info("缓存设置，merchantLog=null");
		}else{
			log.info("缓存设置，merchantLog={}",merchantLog.toString());
			jedis.set("merchant_log_id", String.valueOf(merchantLog.getLogId()));
			log.info("缓存设置，merchant_log_id缓存设置完成");
			log.info("缓存设置，当前merchant_log_id最大值是{}",jedis.get("merchant_log_id"));
		}
		//MerchantLog  merchant_log_id
		MerchantAccount merchantAccount = merchantAccountService.getAccountId();
		if(null == merchantAccount){
			log.info("缓存设置，merchantAccount=null");
		}else{
			log.info("缓存设置，merchantAccount={}",merchantAccount.toString());
			String accountId = String.valueOf(merchantAccount.getAccountId());
			accountId = accountId.substring(4, 12);
			jedis.set("merchant_account_id", accountId);
			log.info("缓存设置，merchant_account_id缓存设置完成");
			log.info("缓存设置，当前merchant_account_id最大值是{}",jedis.get("merchant_account_id"));
		}
		//BankCardAuth  sign_id
		BankcardAuth bankcardAuth = bankcardAuthService.getAuthId();
		if(null == bankcardAuth){
			log.info("缓存设置，bankcardAuth=null");
		}else{
			log.info("缓存设置，bankcardAuth={}",bankcardAuth.toString());
			jedis.set("sign_id", String.valueOf(bankcardAuth.getAuthId()));
			log.info("缓存设置，sign_id缓存设置完成");
			log.info("缓存设置，当前sign_id最大值是{}",jedis.get("sign_id"));
		}
		log.info("用户{}操作缓存设置结束！结束时间={}",user.getName(),DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		String result = "缓存更新完成";
		WebUtil.outputJson("{\"result\":\""+result+"\"}",response);
	}

}
