/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.riskManage.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AccountType;
import com.heepay.enums.BankcardType;
import com.heepay.enums.ProductType;
import com.heepay.enums.TransType;
import com.heepay.enums.risk.MonitorObject;
import com.heepay.enums.risk.QuotaType;
import com.heepay.enums.risk.RegLoginType;
import com.heepay.enums.risk.RiskOrderDealType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.RiskBwCategory;
import com.heepay.manage.common.enums.RiskBwStatus;
import com.heepay.manage.common.enums.RiskBwType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.monitors.entity.InfoMsg;
import com.heepay.manage.modules.risk.entity.RiskBlockInfo;
import com.heepay.manage.modules.risk.service.RiskBlockInfoService;
import com.heepay.manage.modules.riskManage.util.MerchantRiskVo;
import com.heepay.manage.modules.riskManage.util.RegisterAndLoginVo;
import com.heepay.manage.modules.tpds.entity.TpdsMerchantAccount;

/**
 *
 * 描 述：阻断风险操作表Controller
 *
 * 创 建 者： @author wj 创建时间： 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/risk/riskBlockInfo")
public class RiskBlockInfoController extends BaseController {

	@Autowired
	private RiskBlockInfoService riskBlockInfoService;

	@ModelAttribute
	public RiskBlockInfo get(@RequestParam(required = false) String id) {
		RiskBlockInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = riskBlockInfoService.get(id);
		}
		if (entity == null) {
			entity = new RiskBlockInfo();
		}
		return entity;
	}

	@RequiresPermissions("risk:riskBlockInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(RiskBlockInfo riskBlockInfo, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<RiskBlockInfo> page = riskBlockInfoService.findPage(new Page<RiskBlockInfo>(request, response),
				riskBlockInfo);

		List<RiskBlockInfo> list = Lists.newArrayList();
		for (RiskBlockInfo riskBlock : page.getList()) {
			// 通道管理生效标识类型effect_flg
			if (StringUtils.isNotBlank(riskBlock.getBlockType())) {
				riskBlock.setBlockType(RiskOrderDealType.labelOf(riskBlock.getBlockType()));
			}
			// 监控对象
			if (StringUtils.isNotBlank(riskBlock.getMonitorObject())) {
				riskBlock.setMonitorObject(MonitorObject.labelOf(riskBlock.getMonitorObject()));
			}
			// 类型
			if (StringUtils.isNotBlank(riskBlock.getBuziType())) {
				riskBlock.setBuziType(RegLoginType.labelOf(riskBlock.getBuziType()));
			}

			list.add(riskBlock);

		}
		model.addAttribute("list", list);
		
		Map<String,List<EnumBean>> enumMap = null;
		enumMap = this.getEnumMapForQuery();
		model.addAttribute("actionList", enumMap.get("actionList"));
		model.addAttribute("monitorObjectList", enumMap.get("monitorObjectList"));
		model.addAttribute("regLoginTypeList", enumMap.get("regLoginTypeList"));
		model.addAttribute("page", page);
		return "modules/riskManage/riskBlockInfoList";
	}

	@RequiresPermissions("risk:riskBlockInfo:view")
	@RequestMapping(value = "form")
	public String form(RiskBlockInfo riskBlockInfo, Model model) {

		Map<String,List<EnumBean>> enumMap = null;
		enumMap = this.getEnumMapForQuery();
		model.addAttribute("actionList", enumMap.get("actionList"));
		model.addAttribute("monitorObjectList", enumMap.get("monitorObjectList"));
		model.addAttribute("regLoginTypeList", enumMap.get("regLoginTypeList"));
		model.addAttribute("riskBlockInfo", riskBlockInfo);
		return "modules/riskManage/riskBlockInfoForm";
	}

	@RequiresPermissions("risk:riskBlockInfo:edit")
	@RequestMapping(value = "save")
	public String save(RiskBlockInfo riskBlockInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, riskBlockInfo)) {
			return form(riskBlockInfo, model);
		}
		riskBlockInfo.setCreatetime(new Date());
		riskBlockInfoService.save(riskBlockInfo);
		addMessage(redirectAttributes, "保存阻断风险操作表成功");
		return "redirect:" + Global.getAdminPath() + "/risk/riskBlockInfo/?repage";
	}

	@RequiresPermissions("risk:riskBlockInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(RiskBlockInfo riskBlockInfo, RedirectAttributes redirectAttributes) {
		riskBlockInfoService.delete(riskBlockInfo);
		addMessage(redirectAttributes, "删除阻断风险操作表成功");
		return "redirect:" + Global.getAdminPath() + "/risk/riskBlockInfo/?repage";
	}

	@RequiresPermissions("risk:riskBlockInfo:edit")
	@RequestMapping(value = "detail")
	public String detail(RiskBlockInfo riskBlockInfo, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		String blockId = request.getParameter("blockId");
		RiskBlockInfo riskBlock = riskBlockInfoService.get(blockId);
		if (riskBlock != null) { // 注册登录
			if (RegLoginType.MER_REG.getValue().equals(riskBlock.getBuziType())
					|| RegLoginType.MER_LOGIN_BACK.getValue().equals(riskBlock.getBuziType())
					|| RegLoginType.USER_REG.getValue().equals(riskBlock.getBuziType())
					|| RegLoginType.USER_LOGIN_BACK.getValue().equals(riskBlock.getBuziType())) {

				RegisterAndLoginVo registerAndLoginVo = JsonMapperUtil.nonEmptyMapper().fromJson(riskBlock.getFileds(),
						RegisterAndLoginVo.class);
				if (registerAndLoginVo != null) {
					if (registerAndLoginVo.getRegLoginType() != null) {
						registerAndLoginVo.setRegLoginType(RegLoginType.labelOf(registerAndLoginVo.getRegLoginType()));
					}

					if(registerAndLoginVo.getRuleId() == null) {
						String ruleId = "";
						for (Object rule : registerAndLoginVo.getRuleIdList()) {
							ruleId = ruleId + rule.toString() + ",";
						}
						registerAndLoginVo.setRuleId(ruleId.substring(0,ruleId.length()-1));
					}
				}

				model.addAttribute("registerAndLoginVo", registerAndLoginVo);
				logger.info("风控注册登录报文信息{}", registerAndLoginVo);
				return "modules/riskManage/riskBlockInfoDetail";
			} else if (RegLoginType.MER_TRANS.getValue().equals(riskBlock.getBuziType())) { // 交易
				MerchantRiskVo merchantRiskVo = JsonMapperUtil.nonEmptyMapper().fromJson(riskBlock.getFileds(),
						MerchantRiskVo.class);
				model.addAttribute("merchantRiskVo", merchantRiskVo);
				logger.info("风控交易报文信息{}", merchantRiskVo);
				if (merchantRiskVo != null) {

					if (merchantRiskVo.getAccountType() != null) {
						merchantRiskVo.setAccountType(AccountType.labelOf(merchantRiskVo.getAccountType()));
					}
					if (merchantRiskVo.getBankCardType() != null) {
						merchantRiskVo.setBankCardType(BankcardType.labelOf(merchantRiskVo.getBankCardType()));
					}
					if (merchantRiskVo.getTrans_type() != null) {
						merchantRiskVo.setTrans_type(TransType.labelOf(merchantRiskVo.getTrans_type()));
					}
					if (merchantRiskVo.getProductCode() != null) {
						merchantRiskVo.setProductCode(ProductType.labelOf(merchantRiskVo.getProductCode()));
					}

					if(merchantRiskVo.getRuleId() == null) {
						String ruleId = "";
						for (Object rule : merchantRiskVo.getRuleIdList()) {
							ruleId = ruleId + rule.toString() + ",";
						}
						merchantRiskVo.setRuleId(ruleId.substring(0,ruleId.length()-1));
					}
				}

				return "modules/riskManage/riskBlockInfoMerchantDetail";
			}
		}
		return "modules/riskManage/riskBlockInfoMerchantDetail";

	}
	
	private Map<String,List<EnumBean>> getEnumMapForQuery() {
		Map<String,List<EnumBean>> enumMap = null;
		 enumMap =  new HashMap<String,List<EnumBean>>();
		
		List<EnumBean> actionList = Lists.newArrayList();
		String[] actionvalue = { "预警", "阻断" };
		String[] actionkey = { "WARN", "BLOCK" };
		for (int i = 0; i < actionvalue.length; i++) {
			EnumBean ct = new EnumBean();
			ct.setValue(actionkey[i]);
			ct.setName(actionvalue[i]);
			actionList.add(ct);
		}
		 
		 List<EnumBean> monitorObject = Lists.newArrayList();
		 List<EnumBean> regLoginType = Lists.newArrayList();

		for (MonitorObject en : MonitorObject.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			monitorObject.add(ct);
		}
		for (RegLoginType en : RegLoginType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			regLoginType.add(ct);
		}
		
		
		enumMap.put("monitorObjectList", monitorObject);
		enumMap.put("regLoginTypeList", regLoginType);
		enumMap.put("actionList", actionList);
		 return enumMap;
	}

}