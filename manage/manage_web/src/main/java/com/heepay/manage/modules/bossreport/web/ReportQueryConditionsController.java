/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.bossreport.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.enums.PayType;
import com.heepay.enums.bossreport.PayTypeReport;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.bossreport.entity.ReportQueryConditions;
import com.heepay.manage.modules.bossreport.service.ReportQueryConditionsService;
import com.heepay.manage.modules.risk.entity.SettleIncomeInfo;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.service.BankInfoService;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.utils.DictUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;

import java.util.List;


/**
 *
 * 描    述：报表条件配置Controller
 *
 * 创 建 者： @author wangdong
 * 创建时间：2017年5月31日11:40:36
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
@RequestMapping(value = "${adminPath}/bossreport/reportQueryConditions")
public class ReportQueryConditionsController extends BaseController {

	@Autowired
	private ReportQueryConditionsService reportQueryConditionsService;

	@Autowired
	private BankInfoService bankInfoService;
	
	@ModelAttribute
	public ReportQueryConditions get(@RequestParam(required=false) String id) {
		ReportQueryConditions entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportQueryConditionsService.get(id);
		}
		if (entity == null){
			entity = new ReportQueryConditions();
		}
		return entity;
	}
	
	@RequiresPermissions("bossreport:reportQueryConditions:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReportQueryConditions reportQueryConditions, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		model = reportQueryConditionsService
				.findReportQueryConditionsPage(new Page<ReportQueryConditions>(request, response), reportQueryConditions,model);

		return "modules/bossreport/reportQueryConditionsList";
	}

	@RequiresPermissions("bossreport:reportQueryConditions:view")
	@RequestMapping(value = "form")
	public String form(ReportQueryConditions reportQueryConditions, Model model) {
		try {
			if (null != reportQueryConditions.getReportId()){
				reportQueryConditions = reportQueryConditionsService.get(reportQueryConditions);
			}

			getQueryEmnus(model);

			model.addAttribute("reportQueryConditions", reportQueryConditions);
			return "modules/bossreport/reportQueryConditionsForm";
		}catch (Exception e){
			logger.error("跳转出入报表条件配置 出现错误！{FIND_ERROR}--->{}",e);
		}
		return null;
	}

	@RequiresPermissions("bossreport:reportQueryConditions:edit")
	@RequestMapping(value = "save")
	public String save(ReportQueryConditions reportQueryConditions, Model model, RedirectAttributes redirectAttributes) {
		try {
			Integer count = reportQueryConditionsService.findExistInfo(reportQueryConditions);
			if (count>0){
				model.addAttribute("message","插入信息已存在");
				model.addAttribute("reportQueryConditions", reportQueryConditions);

				getQueryEmnus(model);

				return "modules/bossreport/reportQueryConditionsForm";
			}else {
				reportQueryConditions.setPayTypeName(PayTypeReport.labelOf(reportQueryConditions.getPayType()));
				reportQueryConditionsService.save(reportQueryConditions);
				if (null != reportQueryConditions.getReportId()){
					addMessage(redirectAttributes, "修改报表条件配置成功");
				}else {
					addMessage(redirectAttributes, "保存报表条件配置成功");
				}
				return "redirect:"+Global.getAdminPath()+"/bossreport/reportQueryConditions/?repage";
			}
		}catch (Exception e){
			logger.error("保存出入报表条件配置 出现错误！{FIND_ERROR}--->{}",e);
		}
		return null;
	}

	private Model getQueryEmnus(Model model){
		List<EnumBean> payTypeList = Lists.newArrayList();  //支付类型
		for (PayTypeReport type : PayTypeReport.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(type.getValue());
			ct.setName(type.getContent());
			payTypeList.add(ct);
		}
		model.addAttribute("payTypeList", payTypeList);

		List<EnumBean> payType = Lists.newArrayList();  //java支付类型
		for (PayType type : PayType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(type.getValue());
			ct.setName(type.getContent());
			payType.add(ct);
		}
		model.addAttribute("payType", payType);

		List<EnumBean> bankList = Lists.newArrayList();  //java银行
		List<BankInfo> list = bankInfoService.selectList();
		for (BankInfo info : list) {
			EnumBean ct = new EnumBean();
			ct.setValue(info.getBankNo());
			ct.setName(info.getBankName());
			bankList.add(ct);
		}
		model.addAttribute("bankList", bankList);

		List<EnumBean> channelList = Lists.newArrayList();  //java通道合作方
		for(Dict dict : DictUtils.getDictList("ChannelPartner")){
			EnumBean ct = new EnumBean();
			ct.setValue(dict.getValue());
			ct.setName(dict.getLabel());
			channelList.add(ct);
		}
		model.addAttribute("channelList", channelList);

		return model;
	}
	
}