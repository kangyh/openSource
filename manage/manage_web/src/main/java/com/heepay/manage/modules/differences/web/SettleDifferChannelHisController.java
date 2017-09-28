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
import com.heepay.enums.ChannelType;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingCurrency;
import com.heepay.enums.billing.DifferErrorStatus;
import com.heepay.enums.billing.DifferencesBillType;
import com.heepay.enums.billing.SettleDifferCheckStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.differences.entity.SettleDifferChannel;
import com.heepay.manage.modules.differences.entity.SettleDifferChannelHis;
import com.heepay.manage.modules.differences.service.SettleDifferChannelHisService;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecordHis;
import com.heepay.manage.modules.reconciliation.service.SettleDifferRecordHisService;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.service.BankInfoService;
import com.heepay.manage.modules.sys.utils.DictUtils;

/**
 * 
 *
 * 描    述：通道差错账汇总历史表
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月13日 下午5:40:21
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
@RequestMapping(value = "${adminPath}/accounting/settleDifferChannelHis")
public class SettleDifferChannelHisController extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	
	//通道合作方
    public static final String CHANNEL_PARTNER = "ChannelPartner";
    
	@Autowired
	private SettleDifferChannelHisService settleDifferChannelHisService;

	@Autowired
	private BankInfoService bankInfoService;// 网关的服务

	@Autowired
	private SettleDifferChannelExcelExport excelController;// 下载方法
	
	@Autowired
	private SettleDifferRecordHisService settleDifferRrecordHisService;

	// 通道差错帐显示的查询操作
	@RequiresPermissions("settle:SettleDifferChannelHis:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleDifferChannelHis  settleDifferChannelHis, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Page<SettleDifferChannelHis> page = settleDifferChannelHisService.findPage(new Page<SettleDifferChannelHis>(request, response), settleDifferChannelHis);
		List<SettleDifferChannelHis> clearingCRList = Lists.newArrayList();

		logger.info("通道差错账历史查询数据为------>{}" + page.getList());
		for (SettleDifferChannelHis clearingCR : page.getList()) {

				String transNo = clearingCR.getTransNo();
				/**
				 * 根据交易单号去差错记录表查询交易类型
				 */
				SettleDifferRecordHis settleDifferRecordHis = settleDifferRrecordHisService.getTransTypeByTransNo(transNo);
				String transType =" ";
				if(settleDifferRecordHis !=null){
					transType= settleDifferRecordHis.getTransType();
				}
				
				if(TransType.WITHDRAW.getValue().equals(transType) || 
						TransType.REFUND.getValue().equals(transType) || 
						TransType.BATCHPAY.getValue().equals(transType) ||
						TransType.PLAY_MONEY.getValue().equals(transType)) {//出款类
					
					// 出款类，差错批次数据只入库，不做撤补账处理
					if(clearingCR.getCheckStatus().equals(Constants.STATIC_N)){
						//用于页面比较
						clearingCR.setStatus("Y");
					}
				}
			// 通道编码
			String channelCode = clearingCR.getChannelCode();

			// 判断是否是组合命名
			int of = channelCode.lastIndexOf("-");
			if (of != -1) {
				logger.info("通道差错账历史查询channelCode为------>{}" + channelCode, of);
				String substring = channelCode.substring(of);
				String channelCode1 = channelCode.substring(0, of);
				String bankNo = substring.substring(1);

				//调取交易查询基础数据
				BankInfo bankInfo = null;
				try {
					bankInfo = bankInfoService.getBankNameByBankNo(bankNo);
					String bankName = bankInfo.getBankName();
					// 通道编码 ChannelCode
					if (StringUtils.isNotBlank(channelCode1)) {
						//clearingCR.setBankNameChannel(ChannelPartner.labelOf(channelCode1));
						clearingCR.setBankNameChannel(DictUtils.getDictLabel(channelCode1, CHANNEL_PARTNER, ""));
					}
					String name = clearingCR.getBankNameChannel() + "-" + bankName;
					clearingCR.setChannelCode(name);

				} catch (Exception e) {
					logger.error("通道差错账历史查询getBankNameByBankNo错误------>{}", e);
				}
			} else {
				// 通道编码 ChannelCode
				if (StringUtils.isNotBlank(clearingCR.getChannelCode())) {
					//clearingCR.setChannelCode(ChannelPartner.labelOf(clearingCR.getChannelCode()));
					clearingCR.setChannelCode(DictUtils.getDictLabel(clearingCR.getChannelCode(), CHANNEL_PARTNER, ""));
				}
			}

			// '差错状态（N：未记账 D：记账中 Y：已记账）'
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

			// 审核状态
			if (StringUtils.isNotBlank(clearingCR.getCheckStatus())) {
				clearingCR.setCheckStatus(SettleDifferCheckStatus.labelOf(clearingCR.getCheckStatus()));
			}
						
			// 通道类型
			if (StringUtils.isNotBlank(clearingCR.getChannelType())) {
				clearingCR.setChannelType(ChannelType.labelOf(clearingCR.getChannelType()));
			}
			clearingCRList.add(clearingCR);
		}
		page.setList(clearingCRList);

		// '差错状态（N：未记账 D：记账中 Y：已记账）'
		List<EnumBean> billingBillStatus = Lists.newArrayList();
		for (DifferErrorStatus checkFlg : DifferErrorStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			billingBillStatus.add(ct);
		}
		model.addAttribute("billingBillStatus", billingBillStatus);

		// 账单类型
		List<EnumBean> differencesBillType = Lists.newArrayList();
		for (DifferencesBillType checkFlg : DifferencesBillType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			differencesBillType.add(ct);
		}

		model.addAttribute("differencesBillType", differencesBillType);
		model.addAttribute("page", page);
		model.addAttribute("settleDifferChannelHis", settleDifferChannelHis);

		logger.info("通道差错账历史查询结束------>", settleDifferChannelHis);
		return "modules/accounting/settleDifferChannelsHis";

	}

	// 记录信息导出
	@RequiresPermissions("settle:SettleDifferChannelHis:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes, SettleDifferChannelHis settleDifferChannelHis,
			HttpServletRequest request, HttpServletResponse response) {

		List<Map<String, Object>> clearingCR = settleDifferChannelHisService.findListExcel(settleDifferChannelHis);

		logger.info("通道差错账历史下载------>{}" + clearingCR);
		for (Map<String, Object> map : clearingCR) {
			SettleDifferChannel channelBankName = new SettleDifferChannel();
			// 通道名称
			String channelCode = (String) map.get("channelCode");

			int of = channelCode.lastIndexOf("-");
			if (of != -1) {
				// of = channelCode.lastIndexOf("-");
				String substring = channelCode.substring(of);
				String channelCode1 = channelCode.substring(0, of);
				String bankNo = substring.substring(1);

				BankInfo bankInfo = bankInfoService.getBankNameByBankNo(bankNo);
				String bankName = bankInfo.getBankName();

				// 通道编码 ChannelCode
				if (StringUtils.isNotBlank(channelCode1)) {
					//channelBankName.setBankNameChannel(ChannelPartner.labelOf(channelCode1));
					channelBankName.setBankNameChannel(DictUtils.getDictLabel(channelCode1, CHANNEL_PARTNER, ""));
				}
				// 生成组合名称
				String name = channelBankName.getBankNameChannel() + "-" + bankName;

				channelBankName.setBankName(name);
				map.put("bankName", channelBankName.getBankName());
			} else {
				// 通道编码 ChannelCode
				if (StringUtils.isNotBlank(channelCode)) {
					//channelBankName.setBankName(ChannelPartner.labelOf(channelCode));
					channelBankName.setBankName(DictUtils.getDictLabel(channelCode, CHANNEL_PARTNER, ""));
					map.put("bankName", channelBankName.getBankName());
				}
			}

		}

		// 下载的标题行和表格插入对应的字段值
		String[] headerArray = { "通道合作方", "支付通道类型", "差错批次号", "差错日期",  "交易订单号", "支付单号","币种", "实际支付金额", "交易成本","账单类型",  "差错状态","审核状态", "审核备注","操作人","操作时间"};
		String[] showField = { "bankName", "channelType",  "errorBath", "errorDate", "transNo","paymentId", "currency","successAmount", "cost", "billType", "errorStatus", "checkStatus", "checkMessage" ,"updateAuthor","dealTime"};

		try {
			excelController.exportExcel("通道差错汇总历史记录", headerArray, clearingCR, showField, request, response);
		} catch (Exception e) {
			logger.error("ClearingChannelRecordController list has a error:", e);
		}
	}
		
}
