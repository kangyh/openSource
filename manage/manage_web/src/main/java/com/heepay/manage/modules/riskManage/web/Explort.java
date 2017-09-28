package com.heepay.manage.modules.riskManage.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.modules.risk.entity.RiskMerchantFreeze;
import com.heepay.manage.modules.risk.service.RiskMerchantFreezeService;

/***
 * 
* 
* 描    述：商户冻结导出
*
* 创 建 者： wangl
* 创建时间：  Nov 25, 201612:37:23 PM
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
@Component
public class Explort {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private  RiskMerchantFreezeService  riskMerchantFreezeService;
	
	@Autowired
	private MerchentExcelExport riskMerchant;// 下载的方法
	
	// 记录信息导出
	public void export(RedirectAttributes redirectAttributes, RiskMerchantFreeze riskMerchantFreeze,HttpServletRequest request, HttpServletResponse response) {

		logger.info("差异记录下载开始------->",redirectAttributes);
		// 根据条件查询出数据并导出
		List<Map<String, Object>> clearingCR = riskMerchantFreezeService.findListExcel(riskMerchantFreeze);

		// 表格生产的标题行
		String[] headerArray = { "商户编码", "商户账号", "冻结单号","解冻单号", "申请时间", "更新时间","冻结金额", "申请人","更新人", "操作状态", "冻结理由", "冻结申请备注1", "冻结申请备注2","冻结申请备注3", "冻结申请备注4","冻结审核备注","解冻申请备注","解冻审核备注" };
		// 表格插入对应的字段值
		String[] showField = { "merchantCode", "merchantName", "freezeNo", "defreezeNo","createTime","updateTime", "transAmount","createAuthor", "updateAuthor","status", "freezeRemark", "remark1", "remark2", "remark3", "remark4","freezeMessage","thawLog","thawMessages"};

		/**
		 * 导出为excel表格
		 */
		try {
			riskMerchant.exportExcel("商户冻结记录", headerArray, clearingCR, showField, request, response);
		} catch (Exception e) {

			logger.error("ClearingChannelRecordController list has a error:", e);
		}
	}

}
