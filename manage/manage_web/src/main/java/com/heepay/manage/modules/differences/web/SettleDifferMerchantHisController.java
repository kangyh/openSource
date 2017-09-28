package com.heepay.manage.modules.differences.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.billingutils.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingCurrency;
import com.heepay.enums.billing.DifferErrorStatus;
import com.heepay.enums.billing.DifferencesBillType;
import com.heepay.enums.billing.SettleDifferCheckStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.differences.entity.SettleDifferMerchantHis;
import com.heepay.manage.modules.differences.service.SettleDifferMerchantHisService;

/**
 * 
 *
 * 描    述：商户差错账汇总历史查询
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月13日 下午6:07:10
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
@RequestMapping(value = "${adminPath}/accounting/settleDifferMerchantHis")
public class SettleDifferMerchantHisController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleDifferMerchantHisService settleDifferMerchantHisService;

	@Autowired
	private SettleDifferMerchantExcelExport excelController;// 下载方法

	// 前台页面显示的查询操作
	@RequiresPermissions("settle:SettleDifferMerchantHis:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleDifferMerchantHis settleDifferMerchantHis, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Page<SettleDifferMerchantHis> page = settleDifferMerchantHisService.findPage(new Page<SettleDifferMerchantHis>(request, response), settleDifferMerchantHis);
		List<SettleDifferMerchantHis> clearingCRList = Lists.newArrayList();

		logger.info("商户差错账历史查询数据为------>{}" + page.getList());
		for (SettleDifferMerchantHis clearingCR : page.getList()) {

			if(TransType.WITHDRAW.getValue().equals(clearingCR.getTransType()) || 
					TransType.REFUND.getValue().equals(clearingCR.getTransType()) || 
					TransType.BATCHPAY.getValue().equals(clearingCR.getTransType()) ||
					TransType.PLAY_MONEY.getValue().equals(clearingCR.getTransType())) {
				// 出款类，差错批次数据只入库，不做撤补账处理
				if(clearingCR.getCheckStatus().equals(Constants.STATIC_N)){
					clearingCR.setStatus("Y");
				}
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
		model.addAttribute("settleDifferMerchantHis", settleDifferMerchantHis);

		logger.info("商户差错账历史查询结束------>{}" + settleDifferMerchantHis);
		
		
		return "modules/accounting/settleDifferMerchantHis";
	}

	// 记录信息导出
	@RequiresPermissions("settle:SettleDifferMerchantHis:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes, SettleDifferMerchantHis SettleDifferMerchantHis,
			HttpServletRequest request, HttpServletResponse response) {

		List<Map<String, Object>> clearingCR = settleDifferMerchantHisService.findListExcel(SettleDifferMerchantHis);
		logger.info("商户差错账历史下载------>", clearingCR);

		String[] headerArray = { "商户编码", "交易类型", "币种", "差错批次号", "差错日期", "交易订单号", "实际支付金额", "订单应结算金额", "订单手续费", "账单类型", "差错状态","审核状态", "审核备注","操作人","操作时间"};
		String[] showField = { "merchantId", "transType", "currency", "errorBath", "errorDate","transNo","requestAmount", "settleAmountPlan", "fee", "billType", "errorStatus", "checkStatus", "checkMessage" ,"updateAuthor","dealTime"};

		try {
			excelController.exportExcel("商户侧差错账汇总历史记录", headerArray, clearingCR, showField, request, response);
		} catch (Exception e) {
			logger.error("商户侧差错账汇总历史记录 error:", e);
		}
	}

}
