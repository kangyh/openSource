/**
 *  
 */
package com.heepay.manage.modules.payment.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.AccountMark;
import com.heepay.enums.ChargeRecordStatus;
import com.heepay.enums.DifferRecordConsts;
import com.heepay.enums.FeeDeductType;
import com.heepay.enums.GatewayRecordStatus;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.PayType;
import com.heepay.enums.PaymentRecordStatus;
import com.heepay.enums.RefundType;
import com.heepay.enums.TransType;
import com.heepay.enums.gateway.CCBBatchPayStatus;
import com.heepay.gateway.thrift.chinabank.ChinabankQryRequestVO;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.rpc.client.AdvQuickPayGatewayClient;
import com.heepay.manage.modules.merchant.rpc.client.BatchPayQueryClient;
import com.heepay.manage.modules.merchant.rpc.client.ExceptionRecordClient;
import com.heepay.manage.modules.merchant.rpc.client.QueryBankGatewayClient;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.payment.dao.NotifyRecordDao;
import com.heepay.manage.modules.payment.dao.RefundRecordDao;
import com.heepay.manage.modules.payment.entity.BatchPayRecordDetail;
import com.heepay.manage.modules.payment.entity.ChargeRecord;
import com.heepay.manage.modules.payment.entity.ExceptionRecord;
import com.heepay.manage.modules.payment.entity.GatewayRecord;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.entity.NotifyRecord;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.RefundRecord;
import com.heepay.manage.modules.payment.entity.RemitsChargeRecord;
import com.heepay.manage.modules.payment.entity.WithdrawRecord;
import com.heepay.manage.modules.payment.producer.IMqConsume;
import com.heepay.manage.modules.payment.rpc.client.DifferRecordClient;
import com.heepay.manage.modules.payment.service.BatchPayRecordDetailService;
import com.heepay.manage.modules.payment.service.ChargeRecordService;
import com.heepay.manage.modules.payment.service.ExceptionRecordService;
import com.heepay.manage.modules.payment.service.GatewayRecordService;
import com.heepay.manage.modules.payment.service.MerchantLogService;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.manage.modules.payment.service.RefundRecordService;
import com.heepay.manage.modules.payment.service.RemitsChargeRecordService;
import com.heepay.manage.modules.payment.service.WithdrawRecordService;
import com.heepay.manage.modules.share.dao.ShareAccountRecordDetailDao;
import com.heepay.manage.modules.share.entity.ShareAccountRecordDetail;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.util.ExcelUtil2007;
import com.heepay.route.thrift.quickpay.BatchPayQueryRequestVO;
import com.heepay.route.thrift.quickpay.BatchPayQueryResponseVO;
import com.heepay.route.thrift.quickpay.QueryOneResponseVO;
import com.heepay.route.thrift.quickpay.SingleQueryReq;
import com.heepay.route.thrift.quickpay.SingleQueryRes;
import com.heepay.rpc.payment.model.AsyncMsgModel;
import com.heepay.rpc.payment.model.MqHandleModel;
import com.heepay.vo.ClearChannelRecordVO;
import com.heepay.vo.ClearMerchantRecordVO;

/**
 * 异常处理Controller
 * @author yanxb
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/payment/exceptionRecord")
public class ExceptionRecordController extends BaseController {
	
	@Autowired
	private ExceptionRecordService exceptionRecordService;

	@Autowired
	private WithdrawRecordService withdrawRecordService;
	
	@Autowired
	private ChargeRecordService chargeRecordService;
	
	@Autowired
	private RemitsChargeRecordService remitsChargeRecordService;
	
	@Autowired
	private BatchPayRecordDetailService detailRecordService;
	
	@Autowired
	private GatewayRecordService gatewayRecordService;

	@Autowired
	private RefundRecordService refundRecordService;

	@Autowired
	private NotifyRecordDao notifyRecrodDao;
	
	@Autowired
	private PaymentRecordService paymentRecordService;

	@Autowired
	private MerchantLogService merchantLogService;

//	@Autowired
//	private QueryOneGatewayClient queryOneGatewayClient;

    @Autowired
    private AdvQuickPayGatewayClient queryOneQuickpayClient;
	
	@Autowired
	private QueryBankGatewayClient queryBankGatewayClient;
	
	@Autowired
	private BatchPayQueryClient batchPayQueryClient;
	
	@Autowired
	private NotifyRecordDao notifyRecordDao;
	
	@Autowired
	private RefundRecordDao refundDao;
	
	@Autowired
	private ShareAccountRecordDetailDao shareAccountDetailDao;
	
	@Autowired
	private ExceptionRecordClient exceptionRecordClient;

    @Autowired
    private DifferRecordClient differRecordClient;

	@Autowired
	private MerchantCService merchantCService;
	
	private static Logger log = LogManager.getLogger();
	
	@Autowired
	IMqConsume iMqConsume;
	
	@RequestMapping(value = {"list", ""})
	public String list(ExceptionRecord exceptionRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/payment/exceptionProcess2";
	}
	
	@RequestMapping(value = "list2")
	public String list2(ExceptionRecord exceptionRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String exceptionId = exceptionRecord.getExceptionId();
        String transNo = exceptionRecord.getTransNo();

		log.info("异常处理列表查询，参数：paymentId={}，transNo={}",exceptionId,transNo);
//		if(StringUtil.isBlank(exceptionId) ){
//			model.addAttribute("page", null);
//			return "modules/payment/exceptionProcess";
//		}
		PaymentRecord paymentRecord = null;
        if(!StringUtil.isBlank(exceptionId)) {
            paymentRecord = paymentRecordService.get(exceptionId);
        } else if(!StringUtil.isBlank(transNo)) {
            paymentRecord = paymentRecordService.getOne(transNo);
        }
		if(null==paymentRecord){
			model.addAttribute("page", null);
			return "modules/payment/exceptionProcess";
		}
		model.addAttribute("exception", "exception");
		model.addAttribute("paymentRecord", paymentRecord);
		return "modules/payment/complainDetail";

//		if(TransType.CHARGE.getValue().equals(transType)){//充值详情
//			ChargeRecord chargeRecord = chargeRecordService.get(paymentRecord.getTransNo());
//			if(PayType.REMITS.getValue().equals(chargeRecord.getPayType())){
//				RemitsChargeRecord remitsRecord = remitsChargeRecordService.get(paymentRecord.getTransNo());
//				chargeRecord.setPayBankName(remitsRecord.getPayBankName());
//			}
//			model.addAttribute("chargeRecord", chargeRecord);
//
//			model.addAttribute("paymentRecord", paymentRecord);
//			if(paymentRecord!=null){
//				NotifyRecord notifyRecord = notifyRecordDao.getByTransNo(paymentRecord.getTransNo());
//				model.addAttribute("notifyRecord", notifyRecord);
//			}
//			return "modules/payment/chargeRecordDetail";
//		}
//		if(TransType.WITHDRAW.getValue().equals(transType)){//提现详情
//			WithdrawRecord withdrawRecord = withdrawRecordService.get(paymentRecord.getTransNo());
//			model.addAttribute("withdrawRecord", withdrawRecord);
//			return "modules/payment/withdrawRecordOrderDetail";
//		}
//		if(TransType.BATCHPAY.getValue().equals(transType)){//转账详情
//			BatchPayRecordDetail detail = detailRecordService.get(paymentRecord.getTransNo());
//			if(detail != null){
//
//				if(paymentRecord != null){
//					detail.setChannelCode(paymentRecord.getChannelCode());
//					detail.setChannelName(paymentRecord.getChannelName());
//				}
//			}
//			model.addAttribute("batchPayRecordDetail", detail);
//			return "modules/payment/batchPayDetailRecordForm";
//		}
//		if(TransType.PAY.getValue().equals(transType)){//支付详情
//			GatewayRecord gatewayRecord = gatewayRecordService.get(paymentRecord.getTransNo());
//			if(gatewayRecord != null){
//
//				if(paymentRecord != null){
//					NotifyRecord notifyRecord = notifyRecordDao.getByTransNo(paymentRecord.getTransNo());
//					model.addAttribute("notifyRecord", notifyRecord);
//					//退款信息
//					RefundRecord entity = new RefundRecord();
//					entity.setTransNo(paymentRecord.getTransNo());
//					List<RefundRecord> refundList = refundDao.findList(entity);
//					model.addAttribute("refundList",refundList);
//
//					ShareAccountRecordDetail detail = new ShareAccountRecordDetail();
//					detail.setTransNo(paymentRecord.getTransNo());
//					List<ShareAccountRecordDetail>  shareAccountDetails = shareAccountDetailDao.findList(detail);
//					model.addAttribute("shareAccountDetails",shareAccountDetails);
//				}else{
//				    paymentRecord = paymentRecordService.getFailedByGateWayId(gatewayRecord.getGatewayId());
//				}
//				model.addAttribute("paymentRecord", paymentRecord);
//			}
//			model.addAttribute("gatewayRecord", gatewayRecord);
//			return "modules/payment/gatewayRecordForm";
//		}
//		if(TransType.REFUND.getValue().equals(transType)){//退款详情
//			RefundRecord refundRecord = refundRecordService.get(paymentRecord.getTransNo());
//			PaymentRecord originPayment = paymentRecordService.get(refundRecord.getOriginPaymentId());
//			paymentRecord = paymentRecordService.getOne(refundRecord.getRefundId());
//			if(paymentRecord!=null){
//				if(!StringUtil.isBlank(paymentRecord.getBankcardNo())) {
//					String cipherCardNo = StringUtil.getEncryptedCardNo(Aes.decryptStr(paymentRecord.getBankcardNo(),
//							Constants.QuickPay.SYSTEM_KEY));
//					paymentRecord.setBankcardNo(cipherCardNo);
//				}
//				model.addAttribute("paymentRecord", paymentRecord);
//				NotifyRecord notifyRecord =notifyRecrodDao.getByPaymentId(paymentRecord.getPaymentId());
//				model.addAttribute("notifyRecord", notifyRecord);
//			}
//			model.addAttribute("refundRecord", refundRecord);
//			model.addAttribute("originPayment", originPayment);
//
//			String refundStepOne = "";
//			String refundStepTwo = "";
//			if(RefundType.CHARGE.getValue().equals(refundRecord.getType())){
//				refundStepOne = AccountMark.REFUND_STEP_FIRST.getValue();
//				refundStepTwo = AccountMark.REFUND_STEP_SECOND.getValue();
//			} else if(RefundType.CONSUM.getValue().equals(refundRecord.getType())){
//				refundStepOne = AccountMark.REFUND_LONG_SUPPLEMENT_DIFF_STEP1.getValue();
//				refundStepTwo = AccountMark.REFUND_LONG_SUPPLEMENT_DIFF_STEP2.getValue();
//			} else {
//				log.info("{}退款单号refundType有误：{}", exceptionId, refundRecord.getType());
//			}
//			List<MerchantLog> merchantAccountList = merchantLogService.checkMerchantLog(refundRecord.getRefundId(),
//					TransType.REFUND.getValue(), refundStepOne);
//			String merchantAccountStatus = "未记账";
//			if(null != merchantAccountList && merchantAccountList.size() > 0){
//				merchantAccountStatus = "已记账";
//			}
//			List<MerchantLog> channelAccountList = merchantLogService.checkMerchantLog(refundRecord.getRefundId(),
//					TransType.REFUND.getValue(), refundStepTwo);
//			String channelAccountStatus = "未记账";
//			if(null != channelAccountList && channelAccountList.size() > 0){
//				channelAccountStatus = "已记账";
//			}
//			model.addAttribute("merchantAccountStatus", merchantAccountStatus);
//			model.addAttribute("channelAccountStatus", channelAccountStatus);
//
//			return "modules/payment/refundRecordForm";
//		}
//		return "modules/payment/exceptionProcess";
	}

	/**
	 * 
	* @discription 详情页面
	* @author yanxb       
	* @created 2016年11月15日 上午9:22:07     
	* @param exceptionRecord
	* @param model
	* @return
	 */
	@RequestMapping(value = "form")
	public String form(ExceptionRecord exceptionRecord, Model model) {
		if(null == exceptionRecord){
			log.info("异常处理，进入详情页面，参数错误！exceptionRecord={}",exceptionRecord);
			return "modules/payment/exceptionProcess";
		}
		String exceptionId = exceptionRecord.getExceptionId();
		String transType = exceptionRecord.getTransType();
		model.addAttribute("exception", "exception");
		if(TransType.CHARGE.getValue().equals(transType)){//充值详情
			ChargeRecord chargeRecord = chargeRecordService.get(exceptionId);
			if(PayType.REMITS.getValue().equals(chargeRecord.getPayType())){
				RemitsChargeRecord remitsRecord = remitsChargeRecordService.get(exceptionId);
				chargeRecord.setPayBankName(remitsRecord.getPayBankName());
			}
			model.addAttribute("chargeRecord", chargeRecord);
			PaymentRecord paymentRecord = paymentRecordService.get(chargeRecord.getPaymentId());
			model.addAttribute("paymentRecord", paymentRecord);
			if(paymentRecord!=null){
				NotifyRecord notifyRecord = notifyRecordDao.get(paymentRecord.getPaymentId());
				model.addAttribute("notifyRecord", notifyRecord);
			}
			return "modules/payment/chargeRecordDetail";
		}
		if(TransType.WITHDRAW.getValue().equals(transType)){//提现详情
			WithdrawRecord withdrawRecord = withdrawRecordService.get(exceptionId);
			model.addAttribute("withdrawRecord", withdrawRecord);
			return "modules/payment/withdrawRecordOrderDetail";
		}
		if(TransType.BATCHPAY.getValue().equals(transType)){//转账详情
			BatchPayRecordDetail detail = detailRecordService.get(exceptionId);
			if(detail != null){
				PaymentRecord paymentRecord = paymentRecordService.getOne(detail.getBatchPayId());
				if(paymentRecord != null){
					detail.setChannelCode(paymentRecord.getChannelCode());
					detail.setChannelName(paymentRecord.getChannelName());
				}
			}
			model.addAttribute("batchPayRecordDetail", detail);
			return "modules/payment/batchPayDetailRecordForm";
		}
		if(TransType.PAY.getValue().equals(transType)){//支付详情
			GatewayRecord gatewayRecord = gatewayRecordService.get(exceptionId);
			if(gatewayRecord != null){
				PaymentRecord paymentRecord = paymentRecordService.getOneByGatewayId(gatewayRecord.getGatewayId());
				if(paymentRecord != null){
					NotifyRecord notifyRecord = notifyRecordDao.getByTransNo(paymentRecord.getTransNo());
					model.addAttribute("notifyRecord", notifyRecord);
					//退款信息
					RefundRecord entity = new RefundRecord();
					entity.setTransNo(exceptionId);
					List<RefundRecord> refundList = refundDao.findList(entity);
					model.addAttribute("refundList",refundList);
					
					ShareAccountRecordDetail detail = new ShareAccountRecordDetail();
					detail.setTransNo(exceptionId);
					List<ShareAccountRecordDetail>  shareAccountDetails = shareAccountDetailDao.findList(detail);
					model.addAttribute("shareAccountDetails",shareAccountDetails);
				}else{
				    paymentRecord = paymentRecordService.getFailedByGateWayId(gatewayRecord.getGatewayId());
				}
				model.addAttribute("paymentRecord", paymentRecord);
			}
			model.addAttribute("gatewayRecord", gatewayRecord);
			return "modules/payment/gatewayRecordForm";
		}
		if(TransType.REFUND.getValue().equals(transType)){//退款详情
			RefundRecord refundRecord = refundRecordService.get(exceptionId);
			PaymentRecord originPayment = paymentRecordService.get(refundRecord.getOriginPaymentId());
			PaymentRecord paymentRecord = paymentRecordService.getOne(refundRecord.getRefundId());
			if(paymentRecord!=null){
				if(!StringUtil.isBlank(paymentRecord.getBankcardNo())) {
					String cipherCardNo = StringUtil.getEncryptedCardNo(Aes.decryptStr(paymentRecord.getBankcardNo(),
							Constants.QuickPay.SYSTEM_KEY));
					paymentRecord.setBankcardNo(cipherCardNo);
				}
				model.addAttribute("paymentRecord", paymentRecord);
				NotifyRecord notifyRecord =notifyRecrodDao.getByPaymentId(paymentRecord.getPaymentId());
				model.addAttribute("notifyRecord", notifyRecord);
			}
			model.addAttribute("refundRecord", refundRecord);
			model.addAttribute("originPayment", originPayment);

			String refundStepOne = "";
			String refundStepTwo = "";
			if(RefundType.CHARGE.getValue().equals(refundRecord.getType())){
				refundStepOne = AccountMark.REFUND_STEP_FIRST.getValue();
				refundStepTwo = AccountMark.REFUND_STEP_SECOND.getValue();
			} else if(RefundType.CONSUM.getValue().equals(refundRecord.getType())){
				refundStepOne = AccountMark.REFUND_LONG_SUPPLEMENT_DIFF_STEP1.getValue();
				refundStepTwo = AccountMark.REFUND_LONG_SUPPLEMENT_DIFF_STEP2.getValue();
			} else {
				log.info("{}退款单号refundType有误：{}", exceptionId, refundRecord.getType());
			}
			List<MerchantLog> merchantAccountList = merchantLogService.checkMerchantLog(refundRecord.getRefundId(),
					TransType.REFUND.getValue(), refundStepOne);
			String merchantAccountStatus = "未记账";
			if(null != merchantAccountList && merchantAccountList.size() > 0){
				merchantAccountStatus = "已记账";
			}
			List<MerchantLog> channelAccountList = merchantLogService.checkMerchantLog(refundRecord.getRefundId(),
					TransType.REFUND.getValue(), refundStepTwo);
			String channelAccountStatus = "未记账";
			if(null != channelAccountList && channelAccountList.size() > 0){
				channelAccountStatus = "已记账";
			}
			model.addAttribute("merchantAccountStatus", merchantAccountStatus);
			model.addAttribute("channelAccountStatus", channelAccountStatus);

			return "modules/payment/refundRecordForm";
		}
		return "modules/payment/exceptionProcess";
	}

	@RequestMapping(value = "queryRefundBankResult")
	public void queryRefundBankResult(HttpServletRequest request, HttpServletResponse response) throws TException {
		String paymentId = request.getParameter("paymentId");
		String payType = request.getParameter("payType");
		if(StringUtil.isBlank(paymentId)){
			log.error("退款异常处理单笔查询，paymentId为空");
			WebUtil.outputJson("{\"result\":\"paymentId为空!\"}",response);
			return ;
		}

		PaymentRecord paymentRecord = paymentRecordService.get(paymentId);
		if(null == paymentRecord){
			log.error("退款单笔查询,找不到支付记录：paymentId={}",paymentId);
			WebUtil.outputJson("{\"result\":\"没有找到支付记录!\"}",response);
			return ;
		}
		if(StringUtil.isBlank(paymentRecord.getChannelCode())){
			log.error("paymentId={}的退款记录channelCode为空!", paymentId);
			WebUtil.outputJson("{\"result\":\"没有找到通道编码!\"}",response);
			return ;
		}
		paymentRecord.setPayType(payType);

		QueryOneResponseVO gatewayResponse = quaryGateway(paymentRecord,DateUtils.format(paymentRecord.getPayTime(),
				DateUtils.DATE_FORMAT_DATE), TransType.REFUND.getValue());
		if(null == gatewayResponse){
			log.error("退款{}异常处理,调用网关查询接口出错！gatewayResponse=null",paymentRecord.getTransNo());
			String message = "银行处理结果未知，请线下核对之后再处理！";
			WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
			return;
		}
		log.info("退款单笔查询,网关返回信息={}",gatewayResponse.toString());
		//根据银行结果提示运营人员
		String responseCode = gatewayResponse.getResponseCode();
		String bankStatus = gatewayResponse.getTxnStatus();
		if(!String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(responseCode)){
			String errorMessage = "请求查询接口失败："+gatewayResponse.getResponseTextMessage();
			WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
			return;
		}
		if(GatewayRecordStatus.SUCCESS.getValue().equals(bankStatus)){
			String errorMessage = "银行处理结果为：成功";
			WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
		} else if(GatewayRecordStatus.PAYING.getValue().equals(bankStatus)){
			String errorMessage = "银行处理结果为：处理中";
			WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
		} else if(GatewayRecordStatus.FAILED.getValue().equals(bankStatus)){
			String errorMessage = "银行处理结果为：失败";
			WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
		} else {
			String errorMessage = "银行处理结果未知："+bankStatus;
			WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
		}

	}
	
	/**
	 * 
	* @discription 批付查询银行处理结果
	* @author yanxb       
	* @created 2017年1月6日 下午2:35:34     
	* @param request
	* @param response
	* @throws TException
	 */
	@RequestMapping(value = "queryBatchPayBankResult")
	public void queryBatchPayBankResult(HttpServletRequest request, HttpServletResponse response) throws TException {
		String exceptionId = request.getParameter("exceptionId");
		if(StringUtils.isBlank(exceptionId)){
			log.error("批付单笔查询,参数不完整：exceptionId={}",exceptionId);
			WebUtil.outputJson("{\"result\":\""+InterfaceStatus.PARAMSERROR.getContent()+"\"}",response);
			return;
		}
		log.info("批付单笔查询,交易单号：transNo={}",exceptionId);
		//批付查询  网关查询 交易状态
		//查询支付单记录
		PaymentRecord paymentRecord = paymentRecordService.getOne(exceptionId);
		if(null == paymentRecord){
			log.info("批付单笔查询,找不到支付记录：paymentRecord={}",exceptionId,paymentRecord);
			WebUtil.outputJson("{\"result\":\""+InterfaceStatus.CURRENTTRANSERROR.getContent()+"\"}",response);
			return;
		}
		//没有channelCode就不用去查，默认为失败
		if(StringUtil.isBlank(paymentRecord.getChannelCode())){
			log.info("批付{}单笔查询,没有找到通道编码",exceptionId);
			WebUtil.outputJson("{\"result\":\"没有找到通道编码!\"}",response);
			return;
		}
		BatchPayQueryResponseVO gatewayResponse = quaryGatewayBatchPay(paymentRecord);
		if(null == gatewayResponse){
			log.info("转账{}异常处理,调用网关查询接口出错！gatewayResponse={}",paymentRecord.getTransNo(),gatewayResponse);
			String message = "银行处理结果未知，请线下核对之后再处理！";
			WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
			return;
		}
		log.info("批付单笔查询,网关返回信息={}",gatewayResponse.toString());
        if(!String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(gatewayResponse.getResponseCode())){
            String errorMessage = "调用查询接口失败："+gatewayResponse.getResponseMsg();
            WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
            return;
        }
		//根据银行结果提示运营人员
		String bankStatus = gatewayResponse.getResult();
		log.info("批付单笔查询,查询银行结果={}",CCBBatchPayStatus.getContentByValue(bankStatus));
		if(String.valueOf(CCBBatchPayStatus._2.getValue()).equals(bankStatus)
				|| String.valueOf(CCBBatchPayStatus._3.getValue()).equals(bankStatus)
				|| String.valueOf(CCBBatchPayStatus._4.getValue()).equals(bankStatus)){//银行成功
			String errorMessage = "银行处理结果：成功";
			WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
		} else if(String.valueOf(CCBBatchPayStatus._5.getValue()).equals(bankStatus)
				|| String.valueOf(CCBBatchPayStatus._A.getValue()).equals(bankStatus)){
			String errorMessage = "银行处理结果：失败";
			WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
		} else {
			String errorMessage = "银行处理结果：处理中";
			WebUtil.outputJson("{\"result\":\"" + errorMessage + "\"}", response);
		}
	}
	
	/**
	 * 
	* @discription 充值消费单笔查询
	* @author yanxb       
	* @created 2017年1月9日 下午4:26:23     
	* @param request
	* @param response
	* @throws TException
	 */
	@RequestMapping(value = "queryChargeResult")
	public void queryChargeResult(HttpServletRequest request, HttpServletResponse response) throws TException {
		String exceptionId = request.getParameter("exceptionId");
		String transType = request.getParameter("transType");
		log.info("充值消费单笔查询,参数：transNo={},transType={}",exceptionId,transType);
		if(StringUtils.isBlank(exceptionId)){
			log.error("充值消费单笔查询,参数不完整：exceptionId={}",exceptionId);
			WebUtil.outputJson("{\"result\":\""+InterfaceStatus.PARAMSERROR.getContent()+"\"}",response);
			return;
		}
		GatewayRecord gatewayRecord = null;
		if(TransType.CHARGE.getValue().equals(transType)){
			gatewayRecord = gatewayRecordService.getOne(exceptionId);
		}else{
			gatewayRecord = gatewayRecordService.get(exceptionId);
		}
		if(null == gatewayRecord){
			log.error("充值消费{}单笔查询,找不到gateway_record记录：gatewayRecord={}",exceptionId,gatewayRecord);
			WebUtil.outputJson("{\"result\":\""+InterfaceStatus.CURRENTTRANSERROR.getContent()+"\"}",response);
			return;
		}
		//查询支付单记录
		List<PaymentRecord> list = paymentRecordService.getPaymentsByTransNo(gatewayRecord.getGatewayId());
		PaymentRecord paymentRecord = null;
		if(list == null || list.isEmpty()){
			log.error("充值消费{}单笔查询,找不到支付记录：transNo={}",exceptionId,gatewayRecord.getGatewayId());
			WebUtil.outputJson("{\"result\":\""+InterfaceStatus.CURRENTTRANSERROR.getContent()+"\"}",response);
			return;
		}
		if(list.size()>1){
			for(PaymentRecord record : list){
				if(GatewayRecordStatus.SUCCESS.getValue().equals(record.getStatus())){
					paymentRecord = record;
					break;
				}
				paymentRecord = list.get(0);
			}
		}else{
			paymentRecord = list.get(0);
		}
		log.info("充值消费{}单笔查询,支付记录：paymentRecord={}",exceptionId,paymentRecord.toString());
		//没有channelCode就不用去查，默认为失败
		if(StringUtil.isBlank(paymentRecord.getChannelCode())){
			log.info("充值消费{}单笔查询,没有找到通道编码",exceptionId);
			WebUtil.outputJson("{\"result\":\"没有找到通道编码!\"}",response);
			return;
		}
		QueryOneResponseVO gatewayResponse = quaryGateway(paymentRecord,DateUtils.format(gatewayRecord.getCreateDate(), DateUtils.DATE_FORMAT_DATE));
		if(null == gatewayResponse){
			log.error("充值消费{}异常处理,调用网关查询接口出错！gatewayResponse=null",paymentRecord.getTransNo());
			String message = "银行处理结果未知，请线下核对之后再处理！";
			WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
			return;
		}
		log.info("充值消费单笔查询,网关返回信息={}",gatewayResponse.toString());
		//根据银行结果提示运营人员
		String responseCode = gatewayResponse.getResponseCode();
		String bankStatus = gatewayResponse.getTxnStatus();
		if(!String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(responseCode)){
			String errorMessage = "银行处理结果未知或失败，请线下核对之后再处理！";
			WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
			return;
		}
		if(!ChargeRecordStatus.SUCCESS.getValue().equals(bankStatus)){//非银行成功
			String errorMessage = "银行处理结果未知或失败，请线下核对之后再处理！";
			WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
			return;
		}
		log.info("充值消费单笔查询,查询银行结果={}",InterfaceStatus.getContentByValue(Integer.valueOf(responseCode)));
		String message = "银行处理结果为成功，请根据结果进行相应处理！";
		WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
		return;
	}
	
	/**
	 * 
	* @discription 投诉 异常处理
	* @author yanxb       
	* @created 2016年11月10日 下午4:03:31     
	* @param request
	* @param response
	* @return
	 * @throws TException 
	 */
	@RequestMapping(value = "processException")
	public void processException(HttpServletRequest request, HttpServletResponse response) throws TException {
		if(UserUtils.getUser().isAdmin()){
			log.error("异常处理,超级管理员没有操作权限");
			String errorMessage = "您没有权限";
			WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
			return;
		}
		String paymentId = request.getParameter("paymentId");
		String transType = request.getParameter("transType");
		String processDesc = request.getParameter("processDesc");
		String processResult = request.getParameter("processResult");
		String operator = UserUtils.getUser().getName();
		if(StringUtils.isBlank(paymentId) || StringUtils.isBlank(transType)
				|| StringUtils.isBlank(processDesc) || StringUtils.isBlank(processResult)){
			log.error("异常处理,参数不完整：paymentId={}，transType={},processDesc={},processResult={}",
                    paymentId,transType,processDesc,processResult);
			WebUtil.outputJson("{\"result\":\""+InterfaceStatus.PARAMSERROR.getContent()+"\"}",response);
			return;
		}
		log.info("异常处理，接收到请求参数：paymentId={},transType={},processDesc={},processResult={}",
                paymentId,transType,processDesc,processResult);
        //开始异常处理
        if(String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(processResult)) {
            AsyncMsgModel res = differRecordClient.suppleTransForComplaint(paymentId, operator, processDesc);
            WebUtil.outputJson("{\"result\":\""+res.getMsg()+"\"}",response);
        } else if(String.valueOf(InterfaceStatus.FAILED.getValue()).equals(processResult)) {
            AsyncMsgModel res = differRecordClient.cancelTrans(paymentId, operator, DifferRecordConsts.COMPLAINT.getValue(),
                    processDesc);
            WebUtil.outputJson("{\"result\":\""+res.getMsg()+"\"}",response);
        }

//		if(TransType.CHARGE.getValue().equals(transType)){//充值异常处理
//			log.info("异常处理，进入充值{}异常处理流程！",exceptionId);
//			GatewayRecord gatewayRecord = gatewayRecordService.getOne(exceptionId);
//			if(null == gatewayRecord){
//				log.info("异常处理，充值{}异常处理，gatewayRecord=null！",exceptionId);
//				WebUtil.outputJson("{\"result\":\""+String.valueOf(InterfaceStatus.CURRENTTRANSERROR.getContent())+"\"}",response);
//				return;
//			}
//			PaymentRecord paymentRecord = paymentRecordService.getOne(gatewayRecord.getGatewayId());
//			if(null == paymentRecord){
//				log.info("异常处理，充值{}异常处理，paymentRecord=null！",exceptionId);
//				WebUtil.outputJson("{\"result\":\""+String.valueOf(InterfaceStatus.CURRENTTRANSERROR.getContent())+"\"}",response);
//				return;
//			}
//			String feeType = paymentRecord.getFeeType();
//			String message = exceptionRecordClient.processChargeException(exceptionId, processDesc, processResult, operator);
//			log.info("异常处理，充值{}异常处理，返回信息={}！",exceptionId,message);
//			if(!String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(message)){
//				WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//				return;
//			}
//			message = "处理完成";
//			if(String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(processResult)){
//				paymentRecord.setTransNo(gatewayRecord.getMerchantOrderNo());
//				notifySettel(transType,paymentRecord.getFeeAmount(), paymentRecord, processResult,feeType);
//				log.info("充值{}异常处理,通知清结算完成！",exceptionId);
//			}
//			WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//			return;
//		}
//		if(TransType.WITHDRAW.getValue().equals(transType)){//提现异常处理
//			log.info("异常处理，进入提现{}异常处理流程！",exceptionId);
//			PaymentRecord paymentRecord = paymentRecordService.getOne(exceptionId);
//			if(null == paymentRecord){
//				log.info("异常处理，提现{}异常处理，paymentRecord=null",exceptionId);
//				WebUtil.outputJson("{\"result\":\""+String.valueOf(InterfaceStatus.CURRENTTRANSERROR.getValue())+"\"}",response);
//				return;
//			}
//			if(StringUtil.isBlank(paymentRecord.getChannelCode())
//					&& String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(processResult)){
//				WebUtil.outputJson("{\"result\":\""+"找不到通道代码，不能处理为成功！"+"\"}",response);
//				return;
//			}
//			String feeType = paymentRecord.getFeeType();
//			String message = exceptionRecordClient.processWithdrawException(exceptionId, processDesc, processResult, operator);
//			log.info("异常处理，提现{}异常处理，返回信息={}！",exceptionId,message);
//			if(!String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(message)){
//				WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//				return;
//			}
//			message = "处理完成";
//			if(String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(processResult)){
//				notifySettel(transType,paymentRecord.getFeeAmount(), paymentRecord, processResult,feeType);
//				log.info("提现{}异常处理,通知清结算完成！",exceptionId);
//			}
//			WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//			return;
//		}
//		if(TransType.BATCHPAY.getValue().equals(transType)){//转账异常处理
//			log.info("异常处理，进入转账{}异常处理流程！",exceptionId);
//			PaymentRecord paymentRecord = paymentRecordService.getOne(exceptionId);
//			if(null == paymentRecord){
//				WebUtil.outputJson("{\"result\":\""+String.valueOf(InterfaceStatus.CURRENTTRANSERROR.getValue())+"\"}",response);
//				return;
//			}
//			if(StringUtil.isBlank(paymentRecord.getChannelCode())
//					&& String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(processResult)){
//				WebUtil.outputJson("{\"result\":\""+"找不到通道代码，不能处理为成功！"+"\"}",response);
//				return;
//			}
//			String feeType = paymentRecord.getFeeType();
//			String message = exceptionRecordClient.processBatchPayException(exceptionId, processDesc, processResult, operator);
//			log.info("异常处理，转账{}异常处理，返回信息={}！",exceptionId,message);
//			if(!String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(message)){
//				WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//				return;
//			}
//			message = "处理完成";
//			if(String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(processResult)){
//				notifySettel(transType,paymentRecord.getFeeAmount(), paymentRecord, processResult,feeType);
//				log.info("转账{}异常处理,通知清结算完成！",exceptionId);
//			}
//			WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//			return;
//		}
//		if(TransType.PAY.getValue().equals(transType)){//支付异常处理
//			log.info("异常处理，进入支付{}异常处理流程！",exceptionId);
//			PaymentRecord paymentRecord = paymentRecordService.getOne(exceptionId);
//			if(null == paymentRecord){
//				log.info("异常处理，支付{}异常处理，找不到支付记录！",exceptionId);
//				WebUtil.outputJson("{\"result\":\""+String.valueOf(InterfaceStatus.CURRENTTRANSERROR.getValue())+"\"}",response);
//				return;
//			}
//			GatewayRecord gatewayRecord = gatewayRecordService.get(exceptionId);
//			if(null == gatewayRecord){
//				log.info("异常处理，支付{}异常处理，找不到交易记录！",exceptionId);
//				WebUtil.outputJson("{\"result\":\""+String.valueOf(InterfaceStatus.CURRENTTRANSERROR.getContent())+"\"}",response);
//				return;
//			}
//			String bankStatus = request.getParameter("bankStatus");
//			String feeType = paymentRecord.getFeeType();
//			if(String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(bankStatus)
//					&& String.valueOf(InterfaceStatus.FAILED.getValue()).equals(processResult)){
//				log.info("异常处理，支付{}异常处理，银行交易是成功，不能处理为失败！",exceptionId);
//				String message = "银行交易是成功，不能处理为失败！";
//				WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//				return;
//			}
//			QueryOneResponseVO gatewayResponse = quaryGateway(paymentRecord,DateUtils.format(gatewayRecord.getCreateDate(), DateUtils.DATE_FORMAT_DATE));
//			if(null != gatewayResponse
//					&& GatewayRecordStatus.SUCCESS.getValue().equals(gatewayResponse.getResponseCode())
//					&& String.valueOf(InterfaceStatus.FAILED.getValue()).equals(processResult)){
//				log.info("消费{}单笔查询,网关返回信息={}",paymentRecord.getTransNo(),gatewayResponse.toString());
//				log.info("异常处理，支付{}异常处理，银行交易是成功，不能处理为失败！",exceptionId);
//				String message = "银行交易是成功，不能处理为失败！";
//				WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//				return;
//			}
//			String message = exceptionRecordClient.processPayException(exceptionId, processDesc, processResult, operator);
//			log.info("异常处理，支付{}异常处理，返回信息={}！",exceptionId,message);
//			if(!String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(message)){
//				WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//				return;
//			}
//			if(String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(processResult)){
//				notifySettel(transType,paymentRecord.getFeeAmount(), paymentRecord, processResult,feeType);
//				log.info("支付{}异常处理,通知清结算完成！",exceptionId);
//			}
//			//通知商户端
//			String notifyResult = notifyMerchant(paymentRecord,processResult);
//			if(!String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(notifyResult)){
//				log.info("支付{}异常处理,通知商户端失败！",exceptionId);
//				message = "处理完成，通知商户端失败！";
//				WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//				return;
//			}
//			message = "处理完成";
//			WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//			return;
//		}
//		if(TransType.REFUND.getValue().equals(transType)){//退款异常处理
//			log.info("异常处理，进入退款{}异常处理流程！",exceptionId);
//			String message = exceptionRecordClient.processRefundException(exceptionId,processDesc,processResult,UserUtils.getUser().getName());
//			WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
//			return;
//		}
	}

	/**
	 * 
	* @discription消费处理完成之后需要通知商户端(异步通知会消费此消息，然后通知商户端)
	* @author yanxb       
	* @created 2017年3月7日 下午4:22:16     
	* @param paymentRecord
	* @param processResult
	* @return
	 */
	private String notifyMerchant(PaymentRecord paymentRecord, String processResult) {
		try{
			MqHandleModel mqHandleModel = new MqHandleModel();
	        mqHandleModel.setSuccessAmount(paymentRecord.getSuccessAmount());
	        mqHandleModel.setPayAmount(paymentRecord.getPayAmount());
	        mqHandleModel.setBankSerialNo(paymentRecord.getBankSerialNo());
	        mqHandleModel.setTransNo(paymentRecord.getTransNo());
	        mqHandleModel.setPaymentId(paymentRecord.getPaymentId());
	        mqHandleModel.setResult(processResult);
	        mqHandleModel.setNotifyUrl(paymentRecord.getNotifyUrl());
	        mqHandleModel.setMerchantId(paymentRecord.getMerchantId());
	        mqHandleModel.setMerchantOrderNo(paymentRecord.getMerchantOrderNo());
	        mqHandleModel.setProductCode(paymentRecord.getProductCode());
	        mqHandleModel.setMerchantCompany(paymentRecord.getMerchantCompany());
	        mqHandleModel.setMerchantLoginName(paymentRecord.getMerchantLoginName());
	        mqHandleModel.setErrCodeDes(String.valueOf(InterfaceStatus.getContentByValue(Integer.valueOf(processResult))));
	        log.info("消费{}异常处理，写消息进入通知队列,mqHandleModel={}",paymentRecord.getTransNo(),mqHandleModel.toString());
	        iMqConsume.payHandle(mqHandleModel);
		}catch(Exception e){
			log.info("消费{}异常处理，写消息进入通知队列出错！",paymentRecord.getTransNo(),e.getMessage());
			return String.valueOf(InterfaceStatus.FAILED.getValue());
		}
		return String.valueOf(InterfaceStatus.SUCCESS.getValue());
	}

	/**
	 * 
	* @discription 异常处理，通知清结算
	* @author yanxb       
	* @created 2017年1月16日 上午9:48:52     
	* @param feeAmount
	* @param paymentRecord
	* @param result
	* @return
	 */
	private boolean notifySettel(String transType,String feeAmount, PaymentRecord paymentRecord, String result,String feeType) {
		if(null == feeAmount || null == paymentRecord || null == result){
			log.info("异常处理，写队列通知清结算，参数有误！feeAmount={}，paymentRecord={}，result={}",
					feeAmount,paymentRecord,result);
			return false;
		}
		log.info(TransType.getContentByValue(transType)+"异常处理，手续费收取方式={}",feeType);
		ClearChannelRecordVO clearChannelRecordVO = new ClearChannelRecordVO();
	    clearChannelRecordVO.setChannelCode(paymentRecord.getChannelCode());
	    clearChannelRecordVO.setCurrency(paymentRecord.getCurrency());
	    clearChannelRecordVO.setPaymentId(paymentRecord.getPaymentId());
	    clearChannelRecordVO.setPayTime(DateUtils.format(paymentRecord.getPayTime(), DateUtils.DATE_FORMAT_DATE_TIME));
	    if(String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(result)){
	    	clearChannelRecordVO.setSuccessAmount(paymentRecord.getPayAmount());
	    }else{
	    	clearChannelRecordVO.setSuccessAmount("0");
	    }
	    clearChannelRecordVO.setPayAmount(paymentRecord.getPayAmount());
	    clearChannelRecordVO.setTransNo(paymentRecord.getTransNo());
	    clearChannelRecordVO.setTransType(paymentRecord.getTransType());
	    clearChannelRecordVO.setCostWay(paymentRecord.getFeeType());
	    clearChannelRecordVO.setSettleCyc(paymentRecord.getSettleCyc());
	    
	    ClearMerchantRecordVO clearMerchantRecordVO = new ClearMerchantRecordVO();
	    clearMerchantRecordVO.setCurrency(paymentRecord.getCurrency());
	    clearMerchantRecordVO.setMerchantCompany(paymentRecord.getMerchantCompany());
	    clearMerchantRecordVO.setMerchantId(String.valueOf(paymentRecord.getMerchantId()));
	    clearMerchantRecordVO.setMerchantOrderNo(paymentRecord.getMerchantOrderNo());
	    clearMerchantRecordVO.setPaymentID(String.valueOf(paymentRecord.getMerchantId()));
	    clearMerchantRecordVO.setPayTime(DateUtils.format(paymentRecord.getPayTime(), DateUtils.DATE_FORMAT_DATE_TIME));
	    clearMerchantRecordVO.setProductCode(paymentRecord.getProductCode());
	    clearMerchantRecordVO.setRequestAmount(paymentRecord.getPayAmount());
	    clearMerchantRecordVO.setTransNo(paymentRecord.getTransNo());
	    clearMerchantRecordVO.setTransType(paymentRecord.getTransType());
	    if(String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(result)){
	    	clearMerchantRecordVO.setSuccessAmount(paymentRecord.getPayAmount());
	    }else{
	    	clearMerchantRecordVO.setSuccessAmount("0");
	    }
	    if(StringUtil.isBlank(String.valueOf(paymentRecord.getSuccessTime()))){
	    	clearMerchantRecordVO.setSuccessTime(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_DATE_TIME));
	    }else{
	    	clearMerchantRecordVO.setSuccessTime(DateUtils.format(paymentRecord.getSuccessTime(), DateUtils.DATE_FORMAT_DATE_TIME));
	    }
	    if(FeeDeductType.INNER.getValue().equals(feeType)
	    		|| StringUtil.isBlank(feeType)){
	    	clearMerchantRecordVO.setFeeAmount(feeAmount);
	    }else{
	    	clearMerchantRecordVO.setFeeAmount("0");
	    }
	    clearMerchantRecordVO.setFeeWay(paymentRecord.getFeeType());
	    clearMerchantRecordVO.setSettleCyc(paymentRecord.getSettleCyc());
	    log.info(TransType.getContentByValue(transType)+"异常处理，写队列通知清结算参数:clearChannelRecordVO={},clearMerchantRecordVO={}",
	    		clearChannelRecordVO.toString(),clearMerchantRecordVO.toString());
	    iMqConsume.withdrawHandle(transType,clearChannelRecordVO, clearMerchantRecordVO);
	    log.info(TransType.getContentByValue(transType)+"异常处理，写队列通知清结算完成！");
	    return true;
	}

	@RequestMapping(value = "queryBankResult")
	public void queryBankResult(HttpServletRequest request, HttpServletResponse response) throws TException {
		String paymentId = request.getParameter("paymentId");
		if(StringUtil.isBlank(paymentId)){
			log.error("投诉处理单笔查询，paymentId为空");
			WebUtil.outputJson("{\"result\":\"paymentId为空!\"}",response);
			return ;
		}
		log.info("开始查询银行处理结果，paymentId={}", paymentId);
		PaymentRecord paymentRecord = paymentRecordService.get(paymentId);
		log.info("开始查询银行处理结果，paymentRecord={}", JSON.toJSONString(paymentRecord));
		if(null == paymentRecord){
			log.error("投诉处理单笔查询,找不到支付记录：paymentId={}",paymentId);
			WebUtil.outputJson("{\"result\":\"没有找到支付记录!\"}",response);
			return ;
		}
		if(StringUtil.isBlank(paymentRecord.getChannelCode())){
			log.error("paymentId={}的退款记录channelCode为空!", paymentId);
			WebUtil.outputJson("{\"result\":\"没有找到通道编码!\"}",response);
			return ;
		}
		if(TransType.CHARGE.getValue().equals(paymentRecord.getTransType()) ||
				TransType.PAY.getValue().equals(paymentRecord.getTransType())) {
			QueryOneResponseVO gatewayResponse = quaryGateway(paymentRecord,DateUtils.format(paymentRecord.getPayTime(), DateUtils.DATE_FORMAT_DATE));
			if(null == gatewayResponse){
				log.error("充值消费{}异常处理,调用网关查询接口出错！gatewayResponse=null",paymentRecord.getTransNo());
				String message = "银行处理结果未知，请线下核对之后再处理！";
				WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
				return;
			}
			log.info("充值消费单笔查询,网关返回信息={}",gatewayResponse.toString());
			//根据银行结果提示运营人员
			String responseCode = gatewayResponse.getResponseCode();
			String bankStatus = gatewayResponse.getTxnStatus();
			if(!String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(responseCode)){
				String errorMessage = "调用网关查询接口异常！";
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
				return;
			}
			if(GatewayRecordStatus.SUCCESS.getValue().equals(bankStatus)){
				String errorMessage = "银行处理结果为：成功";
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
				return;
			} else if(GatewayRecordStatus.PAYING.getValue().equals(bankStatus)){
				String errorMessage = "银行处理结果为：处理中";
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
				return;
			} else if(GatewayRecordStatus.FAILED.getValue().equals(bankStatus)){
				String errorMessage = "银行处理结果为：失败";
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
				return;
			} else {
				String errorMessage = "银行处理结果未知："+bankStatus;
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
				return;
			}
//			log.info("充值消费单笔查询,查询银行结果={}",InterfaceStatus.getContentByValue(Integer.valueOf(responseCode)));
//			String message = "银行处理结果为成功，请根据结果进行相应处理！";
//			WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
		} else if(TransType.REFUND.getValue().equals(paymentRecord.getTransType())) {
			RefundRecord refund = refundDao.get(paymentRecord.getPaymentId());
			if(null == refund) {
				String errorMessage = "找不到对应的退款单";
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
				return;
			}
			PaymentRecord oriPayment = paymentRecordService.get(refund.getOriginPaymentId());
			if(null == oriPayment) {
				String errorMessage = "找不到对应的原支付单";
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
				return;
			}
			paymentRecord.setPayType(oriPayment.getPayType());
			QueryOneResponseVO gatewayResponse = quaryGateway(paymentRecord,DateUtils.format(paymentRecord.getPayTime(),
					DateUtils.DATE_FORMAT_DATE), TransType.REFUND.getValue());
			if(null == gatewayResponse){
				log.error("退款{}异常处理,调用网关查询接口出错！gatewayResponse=null",paymentRecord.getTransNo());
				String message = "调用网关查询接口异常";
				WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
				return;
			}
			log.info("退款单笔查询,网关返回信息={}",gatewayResponse.toString());
			//根据银行结果提示运营人员
			String responseCode = gatewayResponse.getResponseCode();
			String bankStatus = gatewayResponse.getTxnStatus();
			if(!String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(responseCode)){
				String errorMessage = "请求查询接口失败："+gatewayResponse.getResponseTextMessage();
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
				return;
			}
			if(GatewayRecordStatus.SUCCESS.getValue().equals(bankStatus)){
				String errorMessage = "银行处理结果为：成功";
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
			} else if(GatewayRecordStatus.PAYING.getValue().equals(bankStatus)){
				String errorMessage = "银行处理结果为：处理中";
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
			} else if(GatewayRecordStatus.FAILED.getValue().equals(bankStatus)){
				String errorMessage = "银行处理结果为：失败";
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
			} else {
				String errorMessage = "银行处理结果未知："+bankStatus;
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
			}
		} else if(TransType.BATCHPAY.getValue().equals(paymentRecord.getTransType()) ||
				TransType.WITHDRAW.getValue().equals(paymentRecord.getTransType()) ||
				TransType.DEPOSIT_WITHDRAW.getValue().equals(paymentRecord.getTransType())) {
			BatchPayQueryResponseVO gatewayResponse = quaryGatewayBatchPay(paymentRecord);
			if(null == gatewayResponse){
				log.info("转账{}异常处理,调用网关查询接口出错！gatewayResponse={}",paymentRecord.getTransNo(),gatewayResponse);
				String message = "银行处理结果未知，请线下核对之后再处理！";
				WebUtil.outputJson("{\"result\":\""+message+"\"}",response);
				return;
			}
			log.info("批付单笔查询,网关返回信息={}",gatewayResponse.toString());
			if(!String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(gatewayResponse.getResponseCode())){
				String errorMessage = "调用查询接口失败："+gatewayResponse.getResponseMsg();
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
				return;
			}
			//根据银行结果提示运营人员
			String bankStatus = gatewayResponse.getResult();
			log.info("批付单笔查询,查询银行结果={}",CCBBatchPayStatus.getContentByValue(bankStatus));
			if(String.valueOf(CCBBatchPayStatus._2.getValue()).equals(bankStatus)
					|| String.valueOf(CCBBatchPayStatus._3.getValue()).equals(bankStatus)
					|| String.valueOf(CCBBatchPayStatus._4.getValue()).equals(bankStatus)){//银行成功
				String errorMessage = "银行处理结果：成功";
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
			} else if(String.valueOf(CCBBatchPayStatus._5.getValue()).equals(bankStatus)
					|| String.valueOf(CCBBatchPayStatus._A.getValue()).equals(bankStatus)){
				String errorMessage = "银行处理结果：失败";
				WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
			} else {
				String errorMessage = "银行处理结果：处理中";
				WebUtil.outputJson("{\"result\":\"" + errorMessage + "\"}", response);
			}
		} else {
			String errorMessage = "暂不支持该交易类型的查询接口";
			WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
		}

	}

	/**
	 * 
	* @discription 批付网关查询
	* @author yanxb       
	* @created 2016年11月14日 下午3:19:49     
	* @param paymentRecord
	* @return
	 */
	private BatchPayQueryResponseVO quaryGatewayBatchPay(PaymentRecord paymentRecord) {
	  BatchPayQueryRequestVO requestVO = new BatchPayQueryRequestVO();
	  String paymentId = "";
	  synchronized (requestVO) {
		  paymentId = String.valueOf(new Date().getTime());
	  }
	  requestVO.setPaymentId(paymentId);
	  requestVO.setPayChannelCode(paymentRecord.getChannelCode());
	  requestVO.setOriginalPaymentId(paymentRecord.getPaymentId());
	  requestVO.setCreditNo(paymentRecord.getBankSerialNo());
      try{
        logger.info("网关批付查询网银接口，请求参数:{}", requestVO.toString());       
        BatchPayQueryResponseVO response =  batchPayQueryClient.batchPayQuery(requestVO);
        if(null == response){
          logger.info("网关批付{}查询网银接口失败!返回结果=null", paymentRecord.getTransNo()); 
          return null;
        }
        logger.info("{}网关批付查询网银接口，返回信息={}", paymentRecord.getPaymentId(),response.toString());
        return response;
      }catch(Exception e){
        logger.error("网关批付{}查询网银接口失败!错误信息={}",paymentRecord.getTransNo(),e.getMessage()); 
        return null;
      }
	}

	/**
	 * 
	* @discription 查询充值结果
	* @author yanxb       
	* @created 2016年11月10日 下午2:29:28     
	* @param paymentRecord
	* @return
	 */
	@SuppressWarnings("unchecked")
	private QueryOneResponseVO quaryGateway(PaymentRecord paymentRecord,String createDate,String... queryType) {
		if(PayType.GATEWAYPAY.getValue().equals(paymentRecord.getPayType())){
		  	JsonMapperUtil json = new JsonMapperUtil();
		  	ChinabankQryRequestVO requestVO = new ChinabankQryRequestVO();
		  	requestVO.setChannelCode(paymentRecord.getChannelCode());
	      	requestVO.setOriginPaymentId(paymentRecord.getPaymentId());
	      	requestVO.setOriOrderDate(createDate);
			requestVO.setOriBankSerialNo(paymentRecord.getBankSerialNo());
		  	if(queryType.length!=0){
		  		requestVO.setQueryType(queryType[0]);
		  	}
		  	Merchant merchant = merchantCService.get(paymentRecord.getMerchantId().toString());
			if(null == merchant){
				log.info("获取merchant表信息失败，merchantId={}", paymentRecord.getMerchantId());
				merchant = new Merchant();
			}
			requestVO.setMerchantNo(paymentRecord.getMerchantId().toString());
			requestVO.setMerchantAbbr(merchant.getShortName());
			requestVO.setMerchantName(merchant.getCompanyName());
			requestVO.setMerchantMCCNo(merchant.getMccDetail());

	      	String jsonStr = "";
	      	try{
	        	logger.info("网关单笔查询网银接口，请求参数:{}", requestVO.toString());
	        	jsonStr =  queryBankGatewayClient.queryOne(requestVO);
	        	logger.info("{}网关单笔查询网银接口，返回:{}", paymentRecord.getPaymentId(),jsonStr);
	        	if(StringUtil.isBlank(jsonStr)){
	        	  	logger.info("网关单笔{}查询网银接口失败!返回结果为空",paymentRecord.getTransNo());
	          		return null;
	        	}
	        	Map<String,Object> map = json.fromJson(jsonStr, Map.class);
	        	String responseCode = String.valueOf(map.get("responseCode"));
	        	String responseMsg = String.valueOf(map.get("responseMsge"));
	        	String bankStatus = String.valueOf(map.get("status"));
	        	String bankSerialNo = String.valueOf(map.get("bankSerialNo"));
	        	QueryOneResponseVO response = new QueryOneResponseVO();
	        	response.setRefNumber(bankSerialNo);
	        	response.setResponseCode(responseCode);
	        	response.setResponseTextMessage(responseMsg);
	        	response.setTxnStatus(bankStatus);
	        	return response;
	      	}catch(Exception e){
	        	logger.error("网关单笔{}查询网银接口失败!错误信息={}", paymentRecord.getTransNo(),e.getMessage());
	        	return null;
	      	}
		}else{
//			QueryOneRequestVO queryOneRequestVO = new QueryOneRequestVO();
//			queryOneRequestVO.setSettleMerchantId("");
//			queryOneRequestVO.setPayChannelCode(paymentRecord.getChannelCode());
//			queryOneRequestVO.setBankId(paymentRecord.getBankId());
//			queryOneRequestVO.setCardTypeCode(paymentRecord.getBankcardType());
//			queryOneRequestVO.setAccountType(AccountType.PRIVAT.getValue());
//			queryOneRequestVO.setBusinessType(BusinessType.SPNBAK.getValue());
//			queryOneRequestVO.setPaymentId(paymentRecord.getPaymentId());
//			queryOneRequestVO.setRefNumber(paymentRecord.getChannelRefNo());
//			queryOneRequestVO.setMerchantId(String.valueOf(paymentRecord.getMerchantId()));
//			queryOneRequestVO.setAmount(paymentRecord.getPayAmount());

			try{
//				log.info("单笔查询transNo={}，调用快捷查询接口请求参数={}",paymentRecord.getTransNo(),queryOneRequestVO.toString());
//				QueryOneResponseVO response = queryOneGatewayClient.queryOne(queryOneRequestVO);
//				if(response == null){
//					log.info("单笔查询transNo={}，调用快捷查询接口返回结果=null",paymentRecord.getTransNo());
//					return null;
//				}
//				String bankcardNo = Aes.decryptStr(paymentRecord.getBankcardNo(), Constants.QuickPay.SYSTEM_KEY);
				String entryDatetime = DateUtil.dateToString(paymentRecord.getCreateTime(), DateUtil.TIME_FORMAT);

				SingleQueryReq req = new SingleQueryReq();
				req.setPaymentId(paymentRecord.getPaymentId());
				req.setCardNo(paymentRecord.getBankcardNo());
				req.setCardType(paymentRecord.getBankcardType());
				req.setEntryDate(entryDatetime.substring(0,8));
				req.setEntryTime(entryDatetime.substring(8,14));
				req.setBankSeriNo(paymentRecord.getBankSerialNo());
				req.setBankSeriTime(DateUtil.dateToString(paymentRecord.getBankSerialTime(), DateUtil.TIME_FORMAT));
				req.setPayChannelCode(paymentRecord.getChannelCode());
				req.setReqType("0");
				req.setExtra(paymentRecord.getExt1());

                log.info("快捷支付、网关快捷单笔查询接口请求参数：{}", req);
                SingleQueryRes res = queryOneQuickpayClient.gatewayQueryOne(req);
                log.info("快捷支付、网关快捷单笔查询接口返回参数：{}", res);

                QueryOneResponseVO response = new QueryOneResponseVO();
                response.setResponseCode(res.getResponseCode());
                response.setResponseTextMessage(res.getResponseMsg());
                response.setTxnStatus(res.getStatus());
				return response;
			}catch(Exception e){
				log.error("单笔查询transNo={},调用网关查询接口出错！{}",paymentRecord.getTransNo(),e.getMessage());
				return null;
			}
		}
	}

	private SingleQueryRes callGatewayQuickPayQuery(PaymentRecord payment) {
		String bankcardNo = Aes.decryptStr(payment.getBankcardNo(), Constants.QuickPay.SYSTEM_KEY);
		String entryDatetime = DateUtil.dateToString(payment.getCreateTime(), DateUtil.TIME_FORMAT);

		SingleQueryReq req = new SingleQueryReq();
		req.setPaymentId(payment.getPaymentId());
		req.setCardNo(bankcardNo);
		req.setCardType(payment.getBankcardType());
		req.setEntryDate(entryDatetime.substring(0,8));
		req.setEntryTime(entryDatetime.substring(8,14));
		req.setBankSeriNo(payment.getBankSerialNo());
		req.setBankSeriTime(DateUtil.dateToString(payment.getBankSerialTime(), DateUtil.TIME_FORMAT));
		req.setPayChannelCode(payment.getChannelCode());
		req.setReqType("0");
		req.setExtra(payment.getExt1());

		log.info("快捷支付、网关快捷单笔查询接口请求参数：{}", req);
		SingleQueryRes res = queryOneQuickpayClient.gatewayQueryOne(req);
		log.info("快捷支付、网关快捷单笔查询接口返回参数：{}", res);
		return res;
	}
	
	/**
	 * 
	* @discription 投诉处理记录查询
	* @author yanxb       
	* @created 2017年2月15日 上午11:16:49     
	* @param exceptionRecord
	* @param request
	* @param response
	* @param model
	* @return
	* @throws ParseException
	 */
	@RequestMapping(value = "getProcessList")
	public String getProcessList(ExceptionRecord exceptionRecord, HttpServletRequest request, 
			HttpServletResponse response, Model model) throws ParseException {
		if(StringUtils.isEmpty(exceptionRecord.getGroupType())){
			exceptionRecord.setGroupType("1");
		}
		Page<ExceptionRecord> page = exceptionRecordService.findPage(new Page<ExceptionRecord>(request, response), exceptionRecord);
		model.addAttribute("groupType", exceptionRecord.getGroupType());
		model.addAttribute("page", page);

		return "modules/payment/exceptionProcessList";
	}

	@RequestMapping(value = "exportExcel")
	public void exportExcel(ExceptionRecord exceptionRecord, HttpServletRequest request,
							HttpServletResponse response) throws Exception{
		String title = "投诉处理数据统计:";
		if(exceptionRecord.getBeginCreateTime()!=null && exceptionRecord.getEndCreateTime()!=null){
			title = "投诉处理数据统计:"+ DateUtils.getDateStr(exceptionRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(exceptionRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
		}
		String[] headers = new String[] { "商户编码", "商户公司名称", "商户账号", "交易订单号", "创建时间", "交易金额", "交易状态", "交易类型", "处理人", "处理时间", "处理备注"};

		Page<ExceptionRecord> page = new Page<ExceptionRecord>(request, response);
		page = exceptionRecordService.findPage(page, exceptionRecord);
		int pageSize = 500;
		int totalCount = (int)page.getCount();
		int curPage = 1;//从第一页开始
		int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
		List<String[]> contents = new ArrayList<String[]>();
		for(int i=1;i<=totalpage;i++) {
			page.setPageNo(curPage);
			page.setPageSize(pageSize);
			page = exceptionRecordService.findPage(page, exceptionRecord);
			List<ExceptionRecord> exceptionRecordList = page.getList();
			for(ExceptionRecord record : exceptionRecordList){
				String[] content = new String[headers.length];
				content[0] = String.valueOf(record.getMerchantId());
				content[1] = record.getMerchantCompany();
				content[2] = record.getMerchantLoginName();
				content[3] = record.getTransNo();
				content[4] = DateUtil.dateToString(record.getCreateTime());
				content[5] = record.getTransAmount();
				content[6] = DictUtils.getDictLabel(record.getStatus(), "PaymentRecordStatus", "");
				content[7] = DictUtils.getDictLabel(record.getTransType(), "TransType", "");
				content[8] = record.getOperator();
				content[9] = DateUtil.dateToString(record.getProcessTime());
				content[10] = record.getProcessDesc();
				contents.add(content);
			}
			curPage++;
		}

		String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
		ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
	}

	@RequestMapping(value="getStatisticsList")
	@ResponseBody
	public void getStatisticsList(ExceptionRecord exceptionRecord, HttpServletResponse response) throws ParseException {
		List<ExceptionRecord> exceptionRecordList = exceptionRecordService.findList(exceptionRecord);
		//汇总金额
		BigDecimal exceptionTotalAmount = BigDecimal.ZERO;
		BigDecimal failedTotalAmount = BigDecimal.ZERO;
		BigDecimal succesTotalAmount = BigDecimal.ZERO;
		if(exceptionRecordList != null && !exceptionRecordList.isEmpty()){
			for(ExceptionRecord record : exceptionRecordList){
				BigDecimal exceptionAmount = new BigDecimal(StringUtils.isEmpty(record.getTransAmount())?"0.00":record.getTransAmount());
				exceptionTotalAmount = exceptionTotalAmount.add(exceptionAmount).setScale(2, RoundingMode.HALF_UP);
				if(StringUtils.equals(record.getStatus(), PaymentRecordStatus.FAILED.getValue())){
					//成功金额
					BigDecimal failedAmount = new BigDecimal(StringUtils.isEmpty(record.getTransAmount())?"0.00":record.getTransAmount());
					failedTotalAmount = failedTotalAmount.add(failedAmount).setScale(2, RoundingMode.HALF_UP);
				}else if(StringUtils.equals(record.getStatus(), PaymentRecordStatus.SUCCESS.getValue())){
					//待审核金额
					BigDecimal succesAmount = new BigDecimal(StringUtils.isEmpty(record.getTransAmount())?"0.00":record.getTransAmount());
					succesTotalAmount = succesTotalAmount.add(succesAmount).setScale(2, RoundingMode.HALF_UP);
				}
			}
		}
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		df.applyPattern("￥#,##0.00");
		Map<String, String> map = new HashMap<String, String>();
		map.put("exceptionTotalAmount", df.format(exceptionTotalAmount));
		map.put("failedTotalAmount", df.format(failedTotalAmount));
		map.put("succesTotalAmount", df.format(succesTotalAmount));

		WebUtil.outputJson(map, response);
	}

	@RequestMapping(value = "reset")
	public String reset(String resetId, String resetDesc, ExceptionRecord exceptionRecord, HttpServletRequest request,
            HttpServletResponse response, Model model) {
	    log.info("投诉处理重置：参数：{} # {}，操作员：{}", resetId, resetDesc, UserUtils.getUser().getName());
        int res = exceptionRecordService.reset(resetId, resetDesc, UserUtils.getUser().getName());
        log.info("投诉处理重置：resetId={}，res={}", resetId, res);

        if(StringUtils.isEmpty(exceptionRecord.getGroupType())){
            exceptionRecord.setGroupType("1");
        }
        Page<ExceptionRecord> page = exceptionRecordService.findPage(new Page<>(request, response), exceptionRecord);
        model.addAttribute("groupType", exceptionRecord.getGroupType());
        model.addAttribute("page", page);

        return "modules/payment/exceptionProcessList";

    }

}