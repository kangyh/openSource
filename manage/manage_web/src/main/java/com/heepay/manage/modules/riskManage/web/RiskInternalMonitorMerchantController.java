/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.riskManage.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.google.common.collect.Lists;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ProductType;
import com.heepay.enums.risk.RiskMerChantStatus;
import com.heepay.manage.modules.risk.entity.RiskInternalMonitorMerchant;
import com.heepay.manage.modules.risk.service.RiskInternalMonitorMerchantService;
import com.heepay.manage.modules.riskManage.rpc.client.RiskMoniConfClient;
import com.heepay.manage.modules.sys.utils.UserUtils;


/**
 *
 * 描    述：内部监控商户配制表Controller
 *
 * 创 建 者： @author wj
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
@RequestMapping(value = "${adminPath}/risk/riskInternalMonitorMerchant")
public class RiskInternalMonitorMerchantController extends BaseController {

	@Autowired
	private RiskInternalMonitorMerchantService riskInternalMonitorMerchantService;

	@Autowired
	private RiskMoniConfClient riskMoniConfClient;
	
	private static final Logger log = LogManager.getLogger();
	
	@ModelAttribute
	public RiskInternalMonitorMerchant get(@RequestParam(required=false) String id) {
		RiskInternalMonitorMerchant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = riskInternalMonitorMerchantService.get(id);
		}
		if (entity == null){
			entity = new RiskInternalMonitorMerchant();
		}
		return entity;
	}
	
	@RequiresPermissions("risk:riskInternalMonitorMerchant:view")
	@RequestMapping(value = {"list", ""})
	public String list(RiskInternalMonitorMerchant riskInternalMonitorMerchant, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RiskInternalMonitorMerchant> page = riskInternalMonitorMerchantService.findPage(new Page<RiskInternalMonitorMerchant>(request, response), riskInternalMonitorMerchant); 
		
		List<EnumBean> productNameList = Lists.newArrayList();
		for (ProductType checkFlg : ProductType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			productNameList.add(ct);
		}
		model.addAttribute("productNameList", productNameList);
		model.addAttribute("page", page);
		return "modules/riskManage/riskInternalMonitorMerchantList";
	}

	@RequiresPermissions("risk:riskInternalMonitorMerchant:view")
	@RequestMapping(value = "form")
	public String form(RiskInternalMonitorMerchant riskInternalMonitorMerchant, Model model) {
		
		List<EnumBean> productNameList = Lists.newArrayList();
		for (ProductType checkFlg : ProductType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			productNameList.add(ct);
		}
		model.addAttribute("productNameList", productNameList);
		model.addAttribute("riskInternalMonitorMerchant", riskInternalMonitorMerchant);
		return "modules/riskManage/riskInternalMonitorMerchantForm";
	}

	@RequiresPermissions("risk:riskInternalMonitorMerchant:edit")
	@RequestMapping(value = "save")
	public String save(RiskInternalMonitorMerchant riskInternalMonitorMerchant, Model model, RedirectAttributes redirectAttributes) throws TException {
		if (!beanValidator(model, riskInternalMonitorMerchant)){
			return form(riskInternalMonitorMerchant, model);
		}
		
		riskInternalMonitorMerchant.setOperator(UserUtils.getUser().getName());
		riskInternalMonitorMerchant.setProductName(ProductType.getContentByValue(riskInternalMonitorMerchant.getProductCode()));
		if(riskInternalMonitorMerchant.getInternalMerchantId() != null){ //修改
			String msg = riskMoniConfClient.editMerchantMonitorConfig(JsonMapperUtil.nonEmptyMapper().toJson(riskInternalMonitorMerchant));
			log.info("内部监控商户配置返回{}",msg);
			if("success".equals(msg)){
				addMessage(redirectAttributes, "修改成功");
			}
		}else{//添加
			riskInternalMonitorMerchant.setCreateTime(new Date());
			String msg = riskMoniConfClient.addMerchantMonitorConfig(JsonMapperUtil.nonEmptyMapper().toJson(riskInternalMonitorMerchant));
			log.info("内部监控商户配置返回{}",msg);
			if("success".equals(msg)){
				addMessage(redirectAttributes, "添加成功");
			}
		}
		
		addMessage(redirectAttributes, "保存内部监控商户配制表成功");
		return "redirect:"+Global.getAdminPath()+"/risk/riskInternalMonitorMerchant/?repage";
	}
	
	@RequiresPermissions("risk:riskInternalMonitorMerchant:edit")
	@RequestMapping(value = "delete")
	public String delete(RiskInternalMonitorMerchant riskInternalMonitorMerchant, RedirectAttributes redirectAttributes) throws TException {
		String msg = riskMoniConfClient.delMerchantMonitorConfig(JsonMapperUtil.nonEmptyMapper().toJson(riskInternalMonitorMerchant));
		log.info("内部监控商户配置返回{}",msg);
		if("success".equals(msg)){
			addMessage(redirectAttributes, "删除成功");
		}else{
			addMessage(redirectAttributes, "删除失败");
		}

		return "redirect:"+Global.getAdminPath()+"/risk/riskInternalMonitorMerchant/?repage";
	}
}