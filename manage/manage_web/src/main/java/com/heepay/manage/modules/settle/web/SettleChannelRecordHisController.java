package com.heepay.manage.modules.settle.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.enums.billing.BillingYCheckStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecordHis;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecordHis;
import com.heepay.manage.modules.settle.entity.SettleChannelRecord;
import com.heepay.manage.modules.settle.entity.SettleChannelRecordHis;
import com.heepay.manage.modules.settle.service.SettleChannelRecordHisService;
import com.heepay.manage.modules.settle.service.SettleChannelRecordService;

/**
 * *
 * 
 * 
 * 描 述：
 *
 * 创 建 者： wangjie
 * 创建时间： 2017年3月10日下午4:06:26
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
@RequestMapping(value = "${adminPath}/settle/settleChannelRecordHis")
public class SettleChannelRecordHisController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleChannelRecordHisService settleChannelRecordHisService;

	// 显示列表
	@RequiresPermissions("settle:settleChannelRecordHis:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleChannelRecordHis settleChannelRecordHis, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		try {
			model = settleChannelRecordHisService.findSettleChannelRecordHisPage(
					new Page<SettleChannelRecordHis>(request, response), settleChannelRecordHis, model);
			return "modules/settle/settleChannelRecordHisList";
		} catch (Exception e) {
			logger.error("SettleChannelRecordHisController list has a error:{查询通道结算历史记录List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
	//导出
	@RequiresPermissions("settle:settleChannelRecordHis:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,SettleChannelRecordHis settleChannelRecordHis, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			settleChannelRecordHisService.exportSettleChannelRecordHisExcel(settleChannelRecordHis,response, request);
		} catch (Exception e) {
			logger.error("SettleChannelRecordHisController export has a error:{通道结算历史记录信息导出错误FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	//查看明细
	@RequiresPermissions("settle:settleChannelRecordHis:view")
	@RequestMapping(value = "toDetail")
	public String toDetail(SettleChannelRecordHis settleChannelRecordHis,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		try {
			String settleBath = request.getParameter("settleBath");
			ClearingChannelRecordHis clearingChannelRecordHis = new ClearingChannelRecordHis();
			clearingChannelRecordHis.setSettleBath(settleBath);//结算单据
			clearingChannelRecordHis.setCheckFlg(BillingYCheckStatus.BCFQSTS.getValue());//对账标记：平账
			model = settleChannelRecordHisService.findSettleChannelRecordHisDetailPage(clearingChannelRecordHis,new Page<ClearingChannelRecordHis>(request, response),model);
			model.addAttribute("settleBath", settleBath);
		    return "modules/settle/settleChannelRecordHisToDetail";
		} catch (Exception e) {
			logger.error("SettleChannelRecordHisController toDetail has a error:{通道结算历史记录详细信息错误FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}