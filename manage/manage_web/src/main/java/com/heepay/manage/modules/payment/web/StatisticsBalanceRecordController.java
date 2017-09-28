/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.StatisticsBalanceRecord;
import com.heepay.manage.modules.payment.service.StatisticsBalanceRecordService;


/**
 *
 * 描    述：余额统计Controller
 *
 * 创 建 者： @author tyq
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
@RequestMapping(value = "${adminPath}/payment/statisticsBalanceRecord")
public class StatisticsBalanceRecordController extends BaseController {

	@Autowired
	private StatisticsBalanceRecordService statisticsBalanceRecordService;
	
	@ModelAttribute
	public StatisticsBalanceRecord get(@RequestParam(required=false) String id) {
		StatisticsBalanceRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = statisticsBalanceRecordService.get(id);
		}
		if (entity == null){
			entity = new StatisticsBalanceRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:statisticsBalanceRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticsBalanceRecord statisticsBalanceRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(statisticsBalanceRecord.getBeginTransDate()==null&&statisticsBalanceRecord.getEndTransDate()==null){
			statisticsBalanceRecord.setBeginTransDate(DateUtils.getInternalDateByDay(new Date(), -11));
			statisticsBalanceRecord.setEndTransDate(DateUtils.getYesterdayDate());
		}
		
		Page<StatisticsBalanceRecord> page = statisticsBalanceRecordService.findPage(new Page<StatisticsBalanceRecord>(request, response), statisticsBalanceRecord); 
		
		model.addAttribute("page", page);
		return "modules/payment/statisticsBalanceRecordList";
	}

	@RequiresPermissions("payment:statisticsBalanceRecord:view")
	@RequestMapping(value = "form")
	public String form(StatisticsBalanceRecord statisticsBalanceRecord, Model model) {
		model.addAttribute("statisticsBalanceRecord", statisticsBalanceRecord);
		return "modules/payment/statisticsBalanceRecordForm";
	}

	@RequiresPermissions("payment:statisticsBalanceRecord:edit")
	@RequestMapping(value = "save")
	public String save(StatisticsBalanceRecord statisticsBalanceRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, statisticsBalanceRecord)){
			return form(statisticsBalanceRecord, model);
		}
		statisticsBalanceRecordService.save(statisticsBalanceRecord);
		addMessage(redirectAttributes, "保存余额统计成功");
		return "redirect:"+Global.getAdminPath()+"/payment/statisticsBalanceRecord/?repage";
	}
	
	@RequiresPermissions("payment:statisticsBalanceRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(StatisticsBalanceRecord statisticsBalanceRecord, RedirectAttributes redirectAttributes) {
		statisticsBalanceRecordService.delete(statisticsBalanceRecord);
		addMessage(redirectAttributes, "删除余额统计成功");
		return "redirect:"+Global.getAdminPath()+"/payment/statisticsBalanceRecord/?repage";
	}

}