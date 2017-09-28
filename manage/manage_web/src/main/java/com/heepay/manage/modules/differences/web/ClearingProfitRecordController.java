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
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingProfitStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.differences.entity.ClearingProfitRecord;
import com.heepay.manage.modules.differences.service.ClearingProfitRecordService;

/***
 * 
 * 
 * 描    述：分润明细查询
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午1:38:03
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
@RequestMapping(value = "${adminPath}/accounting/clearingProfitRecord")
public class ClearingProfitRecordController extends BaseController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private ClearingProfitRecordService clearingProfitRecordService;
	
	@Autowired
	private ClearingProfitRecordExcelExport excelController;//导出方法
	
	
	//分润明细显示的查询方法
	@RequiresPermissions("settle:ClearingProfitRecord:view")
	@RequestMapping(value = { "list", "" })
	public String list(ClearingProfitRecord clearingProfitRecord, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		Page<ClearingProfitRecord> page = clearingProfitRecordService.findPage(new Page<ClearingProfitRecord>(request, response), clearingProfitRecord);
		List<ClearingProfitRecord> clearingCRList = Lists.newArrayList();
		
		logger.info("分润明细查询数据为------>{}"+ page.getList());
		for (ClearingProfitRecord clearingCR : page.getList()) {
				//分润状态(N：未分润 Y：已分润)
				if(StringUtils.isNotBlank(clearingCR.getProfitStatus())){
					clearingCR.setProfitStatus(BillingProfitStatus.labelOf(clearingCR.getProfitStatus()));
				}
				//手续费扣除方式
				if(StringUtils.isNotBlank(clearingCR.getFeeWay())){
					clearingCR.setFeeWay(ChargeDeductType.labelOf(clearingCR.getFeeWay()));
				}
				//交易类型
				if(StringUtils.isNotBlank(clearingCR.getTransType())){
					clearingCR.setTransType(TransType.labelOf(clearingCR.getTransType()));
				}
				
				clearingCRList.add(clearingCR);
			}
			page.setList(clearingCRList);
			
			
			//分润状态(N：未分润 Y：已分润)
			List<EnumBean> billingProfitStatus = Lists.newArrayList();
			for (BillingProfitStatus checkFlg : BillingProfitStatus.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(checkFlg.getValue());
				ct.setName(checkFlg.getContent());
				billingProfitStatus.add(ct);
			}
			model.addAttribute("billingProfitStatus", billingProfitStatus);


			List<EnumBean> chargeDeductType = Lists.newArrayList();
			//手续费扣除方式
			for (ChargeDeductType checkFlg : ChargeDeductType.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(checkFlg.getValue());
				ct.setName(checkFlg.getContent());
				chargeDeductType.add(ct);
			}
			model.addAttribute("chargeDeductType", chargeDeductType);
			
			
			//交易类型
			List<EnumBean> transType = Lists.newArrayList();
			for (TransType checkFlg : TransType.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(checkFlg.getValue());
				ct.setName(checkFlg.getContent());
				transType.add(ct);
			}
			model.addAttribute("transType", transType);
			model.addAttribute("page", page);
			model.addAttribute("clearingProfitRecord", clearingProfitRecord);
		
			logger.info("分润明细查询结束------>{}"+clearingProfitRecord);
			return "modules/accounting/clearingProfitRecord";
	}
	
	
		//记录信息导出
		@RequiresPermissions("settle:ClearingProfitRecord:view")
		@RequestMapping(value = "export")
		public void export(RedirectAttributes redirectAttributes,ClearingProfitRecord ClearingProfitRecord, HttpServletRequest request, HttpServletResponse response) {
			
			List<Map<String,Object>> clearingCR = clearingProfitRecordService.findListExcel(ClearingProfitRecord);
			logger.info("分润明细下载------>{}"+ clearingCR);
			
			//下载的标题行和表格插入对应的字段值
			String[] headerArray = {"商户编码","交易订单号","分润金额","分润明细ID","交易类型","应结算金额","分润手续费","手续费扣除方式","分润批次号","分润时间","分润状态"};
			String[] showField = {"merchantId","transNo","profitAmount","shareDetailId","transType","profitAmountPlan","profitFee","feeWay","profitBath","profitTime","profitStatus"};
			
			try {
				excelController.exportExcel("分润明细记录", headerArray,clearingCR,showField, request, response);
			} catch (Exception e) {
				logger.error("ClearingChannelRecordController list has a error:",e);
			}
		}
		
	
}
