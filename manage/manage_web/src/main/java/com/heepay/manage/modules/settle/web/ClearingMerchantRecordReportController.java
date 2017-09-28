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
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.service.ClearingMerchantRecordService;

/**
 *
 * 描 述： 清结算财务报表Controller
 *
 * 创 建 者： @author wangdong 
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
@RequestMapping(value = "${adminPath}/settle/clearingMerchantRecordReportQuery")
public class ClearingMerchantRecordReportController extends BaseController {
	
	private static final Logger logger=LogManager.getLogger();

	@Autowired
	private ClearingMerchantRecordService clearingMerchantRecordService;

	/**
	 * @方法说明：清结算财务报表List
	 * @时间：2016年9月19日 
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:clearingMerchantRecordReport:view")
	@RequestMapping(value = { "list", "" })
	public String list(ClearingMerchantRecord clearingMerchantRecord, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{
		try {
			
			if(null == clearingMerchantRecord.getBeginSettleTime() && null == clearingMerchantRecord.getEndSettleTime()){
				clearingMerchantRecord.setBeginSettleTime(DateUtils.getPreDate(new Date()));
				clearingMerchantRecord.setEndSettleTime(DateUtils.getStrDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			}
			model = clearingMerchantRecordService
					.findClearingMerchantRecordReportPage(new Page<ClearingMerchantRecord>(request, response), clearingMerchantRecord,model);
			
			return "modules/settle/clearingMerchantRecordReportList";
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordReportController list has a error:{清结算财务报表List出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @方法说明：商户清算记录信息导出
	 * @时间：2016年9月19日10:50:33
	 * @param redirectAttributes
	 * @param clearingChannelRecord
	 * @param request
	 * @param response
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:clearingMerchantRecordReport:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,ClearingMerchantRecord clearingMerchantRecord, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			clearingMerchantRecordService.exportClearingMerchantRecordReportExcel(clearingMerchantRecord,response,request);
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordController export has a error:{商户清算记录信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}