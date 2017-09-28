package com.heepay.manage.modules.differences.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.billingutils.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingCurrency;
import com.heepay.enums.billing.DifferErrorStatus;
import com.heepay.enums.billing.DifferencesBillType;
import com.heepay.enums.billing.SettleDifferCheckStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.differences.entity.SettleDifferMerchant;
import com.heepay.manage.modules.differences.service.SettleDifferMerchantService;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecord;
import com.heepay.manage.modules.reconciliation.service.SettleDifferRecordService;
import com.heepay.manage.modules.settle.web.rpc.client.BillingAccountClient;
import com.heepay.manage.modules.settle.web.rpc.client.SettleDifferFillBillClient;
import com.heepay.manage.modules.sys.utils.UserUtils;

/***
 * 
 * 
 * 描 述：商户差错账汇总查询
 *
 * 创 建 者： wangl 
 * 创建时间： 2016年9月23日下午1:38:03 
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
@RequestMapping(value = "${adminPath}/accounting/SettleDifferMerchant")
public class SettleDifferMerchantController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleDifferMerchantService settleDifferMerchantService;

	@Autowired
	private SettleDifferMerchantExcelExport excelController;// 下载方法
	
	@Autowired
	private SettleDifferRecordService settleDifferRrecordService;
	
	@Autowired
	private SettleDifferFillBillClient settleDifferFillBillClient;
	
	@Autowired
	private BillingAccountClient billingAccountClient;

	// 前台页面显示的查询操作
	@RequiresPermissions("settle:SettleDifferMerchant:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleDifferMerchant settleDifferMerchant, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Page<SettleDifferMerchant> page = settleDifferMerchantService.findPage(new Page<SettleDifferMerchant>(request, response), settleDifferMerchant);
		List<SettleDifferMerchant> clearingCRList = Lists.newArrayList();

		logger.info("商户差错账查询数据为------>{}" + page.getList());
		for (SettleDifferMerchant clearingCR : page.getList()) {

			if(TransType.WITHDRAW.getValue().equals(clearingCR.getTransType()) || 
					TransType.REFUND.getValue().equals(clearingCR.getTransType()) || 
					TransType.BATCHPAY.getValue().equals(clearingCR.getTransType()) ||
					TransType.PLAY_MONEY.getValue().equals(clearingCR.getTransType())) {
				// 出款类，差错批次数据只入库，不做撤补账处理
				if(clearingCR.getCheckStatus().equals(Constants.STATIC_N)){
					clearingCR.setStatus("Y");
				}
			}
			
			if(TransType.REFUND.getValue().equals(clearingCR.getTransType())
					|| TransType.WITHDRAW.getValue().equals(clearingCR.getTransType())
					|| TransType.BATCHPAY.getValue().equals(clearingCR.getTransType())
					|| TransType.SHARE.getValue().equals(clearingCR.getTransType())
					|| TransType.TRANSFER.getValue().equals(clearingCR.getTransType())
					|| TransType.PLAY_MONEY.getValue().equals(clearingCR.getTransType())
					|| TransType.REAL_NAME.getValue().equals(clearingCR.getTransType())
					|| TransType.DEPOSIT_WITHDRAW.getValue().equals(clearingCR.getTransType())) {
				clearingCR.setFlag("Y");
			}
			
			// '差错状态（N：未处理 D：处理中 Y：已处理）'
			if (StringUtils.isNotBlank(clearingCR.getErrorStatus())) {
				clearingCR.setErrorStatus(DifferErrorStatus.labelOf(clearingCR.getErrorStatus()));
			}
			// 账单类型
			if (StringUtils.isNotBlank(clearingCR.getBillType())) {
				clearingCR.setBillType(DifferencesBillType.labelOf(clearingCR.getBillType()));
			}
			// 币种
			if (StringUtils.isNotBlank(clearingCR.getCurrency())) {
				clearingCR.setCurrency(BillingCurrency.labelOf(clearingCR.getCurrency()));
			}
			// 交易类型
			if (StringUtils.isNotBlank(clearingCR.getTransType())) {
				clearingCR.setTransType(TransType.labelOf(clearingCR.getTransType()));
			}
			// 审核状态
			if (StringUtils.isNotBlank(clearingCR.getCheckStatus())) {
				clearingCR.setCheckStatus(SettleDifferCheckStatus.labelOf(clearingCR.getCheckStatus()));
			}

			clearingCRList.add(clearingCR);
		}
		page.setList(clearingCRList);

		// '差错状态（N：未处理 D：处理中 Y：已处理）'
		List<EnumBean> billingBillStatus = Lists.newArrayList();
		for (DifferErrorStatus checkFlg : DifferErrorStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			billingBillStatus.add(ct);
		}
		model.addAttribute("billingBillStatus", billingBillStatus);

		// 审核状态（N：未审核 F：审核失败 S：审核成功）
		List<EnumBean> checkStatus = Lists.newArrayList();
		for (SettleDifferCheckStatus checkFlg : SettleDifferCheckStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			checkStatus.add(ct);
		}
		model.addAttribute("checkStatus", checkStatus);

		// 账单类型
		List<EnumBean> differencesBillType = Lists.newArrayList();
		for (DifferencesBillType checkFlg : DifferencesBillType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			differencesBillType.add(ct);
		}
		model.addAttribute("differencesBillType", differencesBillType);

		// 交易类型
		List<EnumBean> transType = Lists.newArrayList();
		for (TransType checkFlg : TransType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			transType.add(ct);
		}
		model.addAttribute("transType", transType);

		List<EnumBean> chargeDeductType = Lists.newArrayList();
		// 手续费扣除方式
		for (ChargeDeductType checkFlg : ChargeDeductType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			chargeDeductType.add(ct);
		}

		model.addAttribute("chargeDeductType", chargeDeductType);
		model.addAttribute("page", page);
		model.addAttribute("settleDifferMerchant", settleDifferMerchant);

		logger.info("商户差错账查询结束------>{}" + settleDifferMerchant);
		
		
		return "modules/accounting/settleDifferMerchant";
	}

	// 记录信息导出
	@RequiresPermissions("settle:SettleDifferMerchant:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes, SettleDifferMerchant SettleDifferMerchant,
			HttpServletRequest request, HttpServletResponse response) {

		List<Map<String, Object>> clearingCR = settleDifferMerchantService.findListExcel(SettleDifferMerchant);
		logger.info("商户差错账下载------>", clearingCR);

		String[] headerArray = { "商户编码", "交易类型", "币种", "差错批次号", "差错日期", "交易订单号", "实际支付金额", "订单应结算金额", "订单手续费", "账单类型", "差错状态","审核状态", "审核备注","操作人","操作时间"};
		String[] showField = { "merchantId", "transType", "currency", "errorBath", "errorDate","transNo","requestAmount", "settleAmountPlan", "fee", "billType", "errorStatus", "checkStatus", "checkMessage" ,"updateAuthor","dealTime"};

		try {
			excelController.exportExcel("商户侧差错账汇总记录", headerArray, clearingCR, showField, request, response);
		} catch (Exception e) {
			logger.error("商户侧差错账汇总记录 error:", e);
		}
	}

	// 账单类型修改
	@RequiresPermissions("settle:settleChannelManager:view")
	@RequestMapping(value = "checkstatus/{differMerbillId}/{pageNo}")
	public String update(@PathVariable("differMerbillId") int differMerbillId, @PathVariable("pageNo") int pageNo,
			RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {

		String referer = request.getHeader("referer");
		SettleDifferMerchant settleDifferMerchant = settleDifferMerchantService.getEntityById(differMerbillId);

		logger.info("商户差错账查询开始------>{}" + settleDifferMerchant);
		// '差错状态（N：未处理 D：处理中 Y：已处理）'
		if (StringUtils.isNotBlank(settleDifferMerchant.getErrorStatus())) {
			settleDifferMerchant.setErrorStatus(DifferErrorStatus.labelOf(settleDifferMerchant.getErrorStatus()));
		}
		// 账单类型
		if (StringUtils.isNotBlank(settleDifferMerchant.getBillType())) {
			settleDifferMerchant.setBillType(DifferencesBillType.labelOf(settleDifferMerchant.getBillType()));
		}
		// 币种
		if (StringUtils.isNotBlank(settleDifferMerchant.getCurrency())) {
			settleDifferMerchant.setCurrency(BillingCurrency.labelOf(settleDifferMerchant.getCurrency()));
		}
		// 交易类型
		if (StringUtils.isNotBlank(settleDifferMerchant.getTransType())) {
			settleDifferMerchant.setTransType(TransType.labelOf(settleDifferMerchant.getTransType()));
		}
		// 审核状态
		if (StringUtils.isNotBlank(settleDifferMerchant.getCheckStatus())) {
			settleDifferMerchant.setCheckStatus(SettleDifferCheckStatus.labelOf(settleDifferMerchant.getCheckStatus()));
		}

		// 其他差异备注时的选项
		List<EnumBean> checkStatus = Lists.newArrayList();
		for (SettleDifferCheckStatus checkFlg : SettleDifferCheckStatus.values()) {
			EnumBean ct = new EnumBean();

			// 如果是 “未审核”的状态就不插入
			if (!checkFlg.getValue().equals(Constants.STATIC_N)) { // N
				ct.setValue(checkFlg.getValue());
				ct.setName(checkFlg.getContent());
				checkStatus.add(ct);
			}
		}

		model.addAttribute("referer", referer);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("checkStatus", checkStatus);
		model.addAttribute("settleDifferMerchant", settleDifferMerchant);

		logger.info("商户差错账查询结束------>", settleDifferMerchant);
		return "modules/accounting/dealResult";
	}

	// 修改的方法
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "save/{differMerbillId}")
	public String updateEntity(SettleDifferMerchant settleDifferMerchant, RedirectAttributes redirectAttributes,
			HttpServletRequest request, @PathVariable("differMerbillId") String differMerbillId) throws ParseException {

		logger.info("商户差错账更新开始------>{}" + settleDifferMerchant);
		//settleDifferMerchant.setErrorStatus(Constants.STATIC_Y);// 差错状态设置为 Y
		settleDifferMerchant.setDealTime(new Date());
		settleDifferMerchant.setErrorStatus(BillingBillStatus.BBSTATUSD.getValue());
		//操作人
		settleDifferMerchant.setUpdateAuthor(UserUtils.getUser().getName());
		try {
			settleDifferMerchantService.updateEntity(settleDifferMerchant);
			addMessage(redirectAttributes, "操作成功!");
		} catch (Exception e) {
			addMessage(redirectAttributes, "操作失败!"+e.getMessage());
		}

		/*String pageNo = request.getParameter("pageNo");
		String referer = request.getParameter("referer");
		// 用referer转回原来的页面
		String substring = referer.substring(referer.length() - 1, referer.length());
		String toReferer = referer.substring(0, referer.length() - 1);

		logger.info("得到的最后一位是--->" + substring);
		boolean flag = IsNumeric.isNumeric(substring);
		if (flag) {
			addMessage(redirectAttributes, "操作成功!");
			return "redirect:" + referer;
		} else {
			addMessage(redirectAttributes, "操作成功!");
			if (substring.equals("/")) {
				return "redirect:" + toReferer + "?pageNo=" + pageNo;
			} else {
				return "redirect:" + referer + "?pageNo=" + pageNo;
			}
		}*/
		return "redirect:"+Global.getAdminPath()+"/accounting/SettleDifferMerchant";
	}
	
	// 再次记账
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "fillAgain/{differMerbillId}")
	public String fillBillAgain(@PathVariable("differMerbillId") int differMerbillId, 
								RedirectAttributes redirectAttributes,
								HttpServletRequest request) {
		SettleDifferMerchant settleDifferMerchant = settleDifferMerchantService.getEntityById(differMerbillId);
		try {
			logger.info("商户侧再次记账开始------>{}", settleDifferMerchant.getTransNo());
			// 交易类型
			SettleDifferRecord settleDifferRecord = settleDifferRrecordService.getSettleDifferRecordByTransNo(settleDifferMerchant.getTransNo());
			doMerchantFillBill(settleDifferMerchant, settleDifferRecord);
			addMessage(redirectAttributes, "操作成功!");
		} catch (Exception e) {
			logger.info("商户侧再次记账出错------>{}", settleDifferMerchant.getTransNo());
			addMessage(redirectAttributes, "操作失败!"+e.getMessage());
		}

		logger.info("商户侧再次记账结束------>{}", settleDifferMerchant.getTransNo());
		return "redirect:"+Global.getAdminPath()+"/accounting/SettleDifferMerchant";
		
	}

	/**
	 * 
	 * @方法说明：商户侧补账方法
	 * @author chenyanming
	 * @param settleDifferMerchant
	 * @param settleDifferRecord
	 * @时间：2016年11月15日下午5:59:18
	 */
	public void doMerchantFillBill(SettleDifferMerchant settleDifferMerchant, SettleDifferRecord settleDifferRecord) {
		
		if(TransType.CHARGE.getValue().equals(settleDifferRecord.getTransType())) {
			// 充值商户侧补账
			int flag = settleDifferFillBillClient.doChargeMerchantFillBill(settleDifferMerchant.getMerchantId(), settleDifferRecord.getChannelCode(), 
					null, settleDifferRecord.getPaymentId(), settleDifferMerchant.getTransNo(), settleDifferMerchant.getErrorBath(), settleDifferMerchant.getRequestAmount().toString(), 
					settleDifferMerchant.getFee().toString());
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				settleDifferMerchantService.updateErrorStatusById(settleDifferMerchant.getDifferMerbillId());
				logger.info("充值商户侧补账完成！交易单号为{}", settleDifferMerchant.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferMerchantService.updateErrorStatusById(settleDifferMerchant.getDifferMerbillId());
				logger.info("充值商户侧账务系统已记账！交易单号为{}", settleDifferMerchant.getTransNo());
			}else {
				logger.info("充值商户侧补账失败！交易单号和返回状态码为{},{}", settleDifferMerchant.getTransNo(), flag);
			}
		}else if(TransType.PAY.getValue().equals(settleDifferRecord.getTransType())) {
			// 消费商户侧补账
			int flag = settleDifferFillBillClient.doPayMerchantFillBill(settleDifferMerchant.getMerchantId(), settleDifferRecord.getChannelCode(), 
					settleDifferRecord.getPaymentId(), settleDifferMerchant.getTransNo(), settleDifferMerchant.getErrorBath(), settleDifferRecord.getTransType(), 
					null, settleDifferMerchant.getRequestAmount().toString(), settleDifferMerchant.getFee().toString());
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				settleDifferMerchantService.updateErrorStatusById(settleDifferMerchant.getDifferMerbillId());
				logger.info("消费商户侧补账完成！交易单号为{}", settleDifferMerchant.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferMerchantService.updateErrorStatusById(settleDifferMerchant.getDifferMerbillId());
				logger.info("消费商户侧账务系统已记账！交易单号为{}", settleDifferMerchant.getTransNo());
			}else {
				logger.info("消费商户侧补账失败！交易单号和返回状态码为{},{}", settleDifferMerchant.getTransNo(), flag);
			}
		}else if(TransType.BATCHCOLLECTION.getValue().equals(settleDifferRecord.getTransType())) {
			// 代收商户补账
			int flag = settleDifferFillBillClient.doBatcolMerchantFillBill(settleDifferMerchant.getMerchantId(), settleDifferRecord.getChannelCode(), 
					settleDifferRecord.getPaymentId(), settleDifferMerchant.getTransNo(), settleDifferMerchant.getErrorBath(), settleDifferRecord.getTransType(), 
					null, settleDifferMerchant.getRequestAmount().toString(), settleDifferMerchant.getFee().toString());
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				// 代收商户侧补账成功
				settleDifferMerchantService.updateErrorStatusById(settleDifferMerchant.getDifferMerbillId());
				logger.info("代收商户侧补账完成！交易单号为{}", settleDifferMerchant.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferMerchantService.updateErrorStatusById(settleDifferMerchant.getDifferMerbillId());
				logger.info("代收商户侧账务系统已记账！交易单号为{}", settleDifferMerchant.getTransNo());
			}else {
				logger.info("代收商户侧补账失败！交易单号和返回状态码为{},{}", settleDifferMerchant.getTransNo(),flag);
			}
		}else if(TransType.DEPOSIT_PAY.getValue().equals(settleDifferRecord.getTransType())) {
			// 存管充值商户补账
			int flag = billingAccountClient.doDepositPayMerchantFillBill(settleDifferMerchant.getMerchantId(), settleDifferRecord.getChannelCode(), 
					settleDifferRecord.getPaymentId(), settleDifferMerchant.getTransNo(), settleDifferMerchant.getErrorBath(), settleDifferRecord.getTransType(), 
					null, settleDifferMerchant.getRequestAmount().toString(), settleDifferMerchant.getFee().toString());
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				// 存管充值商户侧补账成功
				settleDifferMerchantService.updateErrorStatusById(settleDifferMerchant.getDifferMerbillId());
				logger.info("存管充值商户侧补账完成！交易单号为{}", settleDifferMerchant.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferMerchantService.updateErrorStatusById(settleDifferMerchant.getDifferMerbillId());
				logger.info("存管充值商户侧账务系统已记账！交易单号为{}", settleDifferMerchant.getTransNo());
			}else {
				logger.info("存管充值商户侧补账失败！交易单号和返回状态码为{},{}", settleDifferMerchant.getTransNo(),flag);
			}
		}
	}
	
	
}
