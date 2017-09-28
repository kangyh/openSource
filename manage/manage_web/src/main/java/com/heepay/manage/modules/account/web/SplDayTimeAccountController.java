/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.WebUtil;
import com.heepay.enums.*;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.account.rpc.IAccountClient;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.service.MerchantLogService;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.rpc.account.model.PayMentSplDayTimeModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * 描    述： 补日间账务Controller
 *
 * 创 建 者： @author zjx
 * 创建时间：
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
@RequestMapping(value = "${adminPath}/account/splDayTime")
public class SplDayTimeAccountController extends BaseController {

	@Autowired
	private PaymentRecordService paymentRecordService;

	@Autowired
	private MerchantLogService merchantLogService;

	@Autowired
	private IAccountClient iAccountClient;

	private static Logger logger = LogManager.getLogger();

	/**
	 * 跳转至补日间账页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:splDayTime:view")
	@RequestMapping(value = "toSplDayTime")
	public String toSplDayTime(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/account/supplementDayTimeAccountList";
	}

	/**
	 * 查询交易数据
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 */
	@RequiresPermissions("account:splDayTime:view")
	@RequestMapping(value = "findPaymentFundData")
	public void findPaymentFundData(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String transNo = request.getParameter("transNo");
		List<PaymentRecord> paymentRecords = paymentRecordService.findListByTransNo(transNo);
		PaymentRecord simpleRecord = null;
		for (PaymentRecord precord : paymentRecords){
			if(PaymentRecordStatus.SUCCESS.getValue().equals(precord.getStatus())){
				simpleRecord = precord;
				break;
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("transNo", transNo);
		if(simpleRecord == null){
			map.put("retCode","0");
			map.put("retMsg","没有查询到成功交易数据");
		}else{
			map.put("retCode","1");
			map.put("paymentId",simpleRecord.getPaymentId());
			map.put("transNo",simpleRecord.getTransNo());
			map.put("merchantId",""+simpleRecord.getMerchantId());
			map.put("merchantCompany",simpleRecord.getMerchantCompany());
			map.put("payAmount",simpleRecord.getPayAmount());
			map.put("feeAmount",simpleRecord.getFeeAmount());
			map.put("successTime",DateUtil.dateToString(simpleRecord.getSuccessTime(), DateUtil.DATETIME_FORMAT));
			map.put("channelCode",simpleRecord.getChannelCode());
			map.put("feeType",simpleRecord.getFeeType());
			map.put("transType",simpleRecord.getTransType());

			List<MerchantLog> merchantAccountList = merchantLogService.checkMerchantLog(transNo,
					simpleRecord.getTransType(), AccountMark.getSettleCodeDayTimeByTransType(simpleRecord.getTransType()));
			if(merchantAccountList.size()> 0){
				map.put("merchantLogFlag","Y");
			}else{
				map.put("merchantLogFlag","N");
			}
		}
		WebUtil.outputJson(map, response);
	}

	/**
	 * 补日间账务
	 * @param merchantLog
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:splDayTime:edit")
	@RequestMapping(value = "splDayTimeAccount")
	public String splDayTimeAccount(MerchantLog merchantLog,HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String transNo =  request.getParameter("transNo");
		logger.info("补日间账务开始,transNo:{}",transNo);
		List<PaymentRecord> paymentRecords = paymentRecordService.findListByTransNo(transNo);
		PaymentRecord simpleRecord = null;
		for (PaymentRecord precord : paymentRecords){
			if(PaymentRecordStatus.SUCCESS.getValue().equals(precord.getStatus())){
				simpleRecord = precord;
				break;
			}
		}

		logger.info("补日间账务:transNo:{},simpleRecord;{}",transNo,simpleRecord);
		if(simpleRecord == null){
			logger.info("补日间账务,transNo:{}不存在", transNo);
			addMessage(redirectAttributes, "交易数据(" + transNo + ")不存在");
		}else{
			if(StringUtil.isBlank(simpleRecord.getFeeType())){
				logger.info("补日间账务,transNo:{},手续费收取方式不能为空.", transNo);
				addMessage(redirectAttributes, "交易数据(" + transNo + ")手续费收取方式不能为空");
			}else{
				PayMentSplDayTimeModel payMentSplDayTimeModel = new PayMentSplDayTimeModel();
				payMentSplDayTimeModel.setMerchantId(simpleRecord.getMerchantId());
				payMentSplDayTimeModel.setChannelCode(simpleRecord.getChannelCode());
				payMentSplDayTimeModel.setTransType(simpleRecord.getTransType());
				payMentSplDayTimeModel.setPayType(simpleRecord.getPayType());
				payMentSplDayTimeModel.setPaymentId(simpleRecord.getPaymentId());
				payMentSplDayTimeModel.setTransNo(simpleRecord.getTransNo());
				payMentSplDayTimeModel.setPayAmount(simpleRecord.getPayAmount());
				payMentSplDayTimeModel.setFeeAmount(simpleRecord.getFeeAmount());
				payMentSplDayTimeModel.setFeeSource(FeeSourceType.MERCHANT.getValue());
				payMentSplDayTimeModel.setFeeOptionType(simpleRecord.getFeeType());
				payMentSplDayTimeModel.setAppointDate(DateUtil.dateToString(new Date(), DateUtil.DATETIME_FORMAT));
				logger.info("补日间账务,transNo:{},请求补账参数;{}",payMentSplDayTimeModel);
				String result = iAccountClient.splPaymentDaytime(payMentSplDayTimeModel);
				logger.info("补日间账务,transNo:{},返回补账结果:{}",transNo,result);
			}
		}
		return "redirect:"+ Global.getAdminPath()+"/account/splDayTime/toSplDayTime?repage";
	}

}