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
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecordHis;
import com.heepay.manage.modules.settle.entity.SettleChannelRecordHis;
import com.heepay.manage.modules.settle.entity.SettleMerchantRecord;
import com.heepay.manage.modules.settle.entity.SettleMerchantRecordHis;
import com.heepay.manage.modules.settle.service.SettleMerchantRecordHisService;

/**
 * *
 * 
 * 
 * 描 述：
 *
 * 创 建 者： wangjie
 * 创建时间： 2017年3月10日下午4:10:28
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
@RequestMapping(value = "${adminPath}/settle/settleMerchantRecordHis")
public class SettleMerchantRecordHisController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleMerchantRecordHisService settleMerchantRecordHisService;

	// 显示列表
	@RequiresPermissions("settle:settleMerchantRecordHis:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleMerchantRecordHis settleMerchantRecordHis, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		try {
			model = settleMerchantRecordHisService.findSettleMerchantRecordHisPage(
					new Page<SettleMerchantRecordHis>(request, response), settleMerchantRecordHis, model);
			return "modules/settle/settleMerchantRecordHisList";
		} catch (Exception e) {
			logger.error("SettleMerchantRecordHisController list has a error:{查询商户结算历史记录List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
	//导出
	@RequiresPermissions("settle:settleMerchantRecordHis:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,SettleMerchantRecordHis settleMerchantRecordHis, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			settleMerchantRecordHisService.exportSettleMerchantRecordHisExcel(settleMerchantRecordHis,response, request);
		} catch (Exception e) {
			logger.error("SettleMerchantRecordHisController export has a error:{商户清算历史记录信息导出错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	//查看明细
	@RequiresPermissions("settle:settleMerchantRecordHis:view")
	@RequestMapping(value = "toDetail")
	public String toDetail(SettleMerchantRecordHis settleMerchantRecordHis,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		try {
			ClearingMerchantRecordHis clearingMerchantRecordHis = new ClearingMerchantRecordHis();
			String settleBath = request.getParameter("settleBath");
			//因为  商户侧存在T+0的支付  所以查看详情，根据结算批次查询  2016年12月2日15:09:10
			clearingMerchantRecordHis.setSettleBath(settleBath);//结算批次
			model = settleMerchantRecordHisService.findSettleMerchantRecordHisDetailPage(new Page<ClearingMerchantRecordHis>(request, response),clearingMerchantRecordHis,model);
			model.addAttribute("settleBath", settleBath);
		    return "modules/settle/settleMerchantRecordHisToDetail";
		} catch (Exception e) {
			logger.error("SettleMerchantRecordHisController toDetail has a error:{商户结算历史记录详细信息错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	

}