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
import com.heepay.common.util.StringUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingCurrency;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.differences.entity.SettleProfitBath;
import com.heepay.manage.modules.differences.service.SettleProfitBathService;

/***
 * 
 * 
 * 描 述：分润汇总查询
 *
 * 创 建 者： wangl 创建时间： 2016年9月23日下午1:38:03 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/settleProfitBath")
public class SettleProfitBathController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleProfitBathService settleProfitBathService;

	@Autowired
	private SettleProfitBathExcelExport excelController;// 下载方法

	// 前台页面显示的查询操作
	@RequiresPermissions("settle:SettleProfitBath:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleProfitBath settleProfitBath, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		Page<SettleProfitBath> page = settleProfitBathService.findPage(new Page<SettleProfitBath>(request, response),
				settleProfitBath);

		logger.info("分润汇总查询数据为------>{}" + page.getList());
		List<SettleProfitBath> clearingCRList = Lists.newArrayList();

		for (SettleProfitBath clearingCR : page.getList()) {
			// 通道业务类型
			if (StringUtils.isNotBlank(clearingCR.getProfitStatus())) {
				clearingCR.setProfitStatus(BillingBillStatus.labelOf(clearingCR.getProfitStatus()));
			}
			// 人民币类型
			if (StringUtils.isNotBlank(clearingCR.getCurrency())) {
				clearingCR.setCurrency(BillingCurrency.labelOf(clearingCR.getCurrency()));
			}
			// 业务类型
			if (StringUtils.isNotBlank(clearingCR.getTransType())) {
				clearingCR.setTransType(TransType.labelOf(clearingCR.getTransType()));
			}
			clearingCRList.add(clearingCR);
		}

		page.setList(clearingCRList);
		List<EnumBean> billingBillStatus = Lists.newArrayList();
		// 查询文件格式
		for (BillingBillStatus checkFlg : BillingBillStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			billingBillStatus.add(ct);
		}

		model.addAttribute("billingBillStatus", billingBillStatus);
		model.addAttribute("page", page);
		model.addAttribute("settleProfitBath", settleProfitBath);

		logger.info("分润汇总查询结束------>{}" + settleProfitBath);
		return "modules/accounting/settleProfitBath";

	}

	// 记录信息导出
	@RequiresPermissions("settle:SettleProfitBath:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes, SettleProfitBath settleProfitBath,
			HttpServletRequest request, HttpServletResponse response) {

		List<Map<String, Object>> clearingCR = settleProfitBathService.findListExcel(settleProfitBath);

		logger.info("分润汇总下载------>{}" + clearingCR);

		String[] headerArray = { "商户编码", "交易类型", "币种", "分润批次号", "分润日期", "处理时间", "交易订单号", "订单金额", "处理状态" };
		String[] showField = { "merchantId", "transType", "currency", "profitBath", "profitDate", "dealTime", "transNo","profitAmount", "profitStatus" };

		try {
			excelController.exportExcel("分润汇总记录", headerArray, clearingCR, showField, request, response);
		} catch (Exception e) {
			logger.error("ClearingChannelRecordController list has a error:", e);
		}
	}

}
