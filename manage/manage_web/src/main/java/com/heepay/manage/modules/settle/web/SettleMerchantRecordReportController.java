package com.heepay.manage.modules.settle.web;

import java.util.Date;

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

import com.heepay.date.DateUtils;
import com.heepay.enums.ProductType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.entity.SettleMerchantRecord;
import com.heepay.manage.modules.settle.service.ClearingMerchantRecordService;
import com.heepay.manage.modules.settle.service.SettleMerchantRecordService;

/**
 * *
 * 
* 
* 描    述：清结算运营报表
*
* 创 建 者： wangjie
* 创建时间：  2017年3月29日上午10:02:54
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
@RequestMapping(value = "${adminPath}/settle/settleMerchantRecordReportQuery")
public class SettleMerchantRecordReportController extends BaseController {
	
	private static final Logger logger=LogManager.getLogger();

	@Autowired
	private SettleMerchantRecordService settleMerchantRecordService;
	
	
	@Autowired
	private ClearingMerchantRecordService clearingMerchantRecordService;
	
	
	@RequiresPermissions("settle:settleMerchantRecordReport:view")
	@RequestMapping(value = { "list", "" })
	public String list(ClearingMerchantRecord clearingMerchantRecord, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{
		try {
			
			clearingMerchantRecord.setProductCode(clearingMerchantRecord.getProductName());
			
			if(clearingMerchantRecord.getBeginSettleTime()==null){
				clearingMerchantRecord.setBeginSettleTime(DateUtils.getPreDate(new Date()));
			}
			if(clearingMerchantRecord.getEndSettleTime()==null){
				clearingMerchantRecord.setEndSettleTime(DateUtils.getPreDate(new Date()));
			}
			model = clearingMerchantRecordService
					.findClearingMerchantRecordReportDetailPage(new Page<ClearingMerchantRecord>(request, response), clearingMerchantRecord,model);
			
			return "modules/settle/settleMerchantRecordReportList";
		} catch (Exception e) {
			logger.error("SettleMerchantRecordReportController list has a error:{查询商户结算记录List错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	
	
	
	@RequiresPermissions("settle:settleMerchantRecordReport:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,ClearingMerchantRecord clearingMerchantRecord, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			clearingMerchantRecord.setProductCode(clearingMerchantRecord.getProductName());
			clearingMerchantRecordService.exportSettleMerchantReportRecordExcel(clearingMerchantRecord,response, request);
		} catch (Exception e) {
			logger.error("SettleMerchantRecordReportController export has a error:{商户清算记录信息导出错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
