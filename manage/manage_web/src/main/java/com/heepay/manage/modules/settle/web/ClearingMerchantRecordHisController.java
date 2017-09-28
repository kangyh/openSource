package com.heepay.manage.modules.settle.web;

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

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecordHis;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecordHis;
import com.heepay.manage.modules.settle.service.ClearingMerchantRecordHisService;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月10日下午4:05:42
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
@RequestMapping(value = "${adminPath}/settle/clearingMerchantRecordHis")
public class ClearingMerchantRecordHisController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private ClearingMerchantRecordHisService clearingMerchantRecordHisService;

	// 显示列表
	@RequiresPermissions("settle:clearingMerchantRecordHis:view")
	@RequestMapping(value = { "list", "" })
	public String list(ClearingMerchantRecordHis clearingMerchantRecordHis, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		try {
			model = clearingMerchantRecordHisService.findClearingMerchantRecordHisPage(
					new Page<ClearingMerchantRecordHis>(request, response), clearingMerchantRecordHis, model);
			return "modules/settle/clearingMerchantRecordHisList";
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordHisController list has a error:{查询商户清算历史记录List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
	//导出
	@RequiresPermissions("settle:clearingMerchantRecordHis:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,ClearingMerchantRecordHis clearingMerchantRecordHis, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			clearingMerchantRecordHisService.exportClearingMerchantRecordHisExcel(clearingMerchantRecordHis,response,request);
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordHisController export has a error:{商户清算历史记录信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

}