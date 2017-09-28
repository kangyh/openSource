/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.riskManage.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.rpc.client.RiskServiceClient;
import com.heepay.manage.modules.risk.entity.RiskIncomeQuota;
import com.heepay.manage.modules.risk.service.RiskIncomeQuotaService;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 *
 * 描    述：商户出入金限额Controller
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
@RequestMapping(value = "${adminPath}/riskManage/riskIncomeQuota")
public class RiskIncomeQuotaController extends BaseController {

	@Autowired
	private RiskIncomeQuotaService riskIncomeQuotaService;

	@Autowired
	private RiskServiceClient riskServiceClient;
	
	@ModelAttribute
	public RiskIncomeQuota get(@RequestParam(required=false) String id) {
		RiskIncomeQuota entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = riskIncomeQuotaService.get(id);
		}
		if (entity == null){
			entity = new RiskIncomeQuota();
		}
		return entity;
	}
	
	@RequiresPermissions("riskManage:riskIncomeQuota:view")
	@RequestMapping(value = {"list", ""})
	public String list(RiskIncomeQuota riskIncomeQuota, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		model = riskIncomeQuotaService
				.findRiskIncomeQuotaPage(new Page<RiskIncomeQuota>(request, response), riskIncomeQuota,model);

		List<EnumBean> ioList = Lists.newArrayList();  //结算模式 出入金
		String[] iovalue = {"入金","出金"};
		String[] iokey = {"in","out"};
		for(int i = 0; i < iovalue.length; i++){
			EnumBean ct = new EnumBean();
			ct.setValue(iokey[i]);
			ct.setName(iovalue[i]);
			ioList.add(ct);
		}
		model.addAttribute("ioList", ioList);

		return "modules/riskManage/riskIncomeQuotaList";
	}

	@RequiresPermissions("riskManage:riskIncomeQuota:view")
	@RequestMapping(value = "form")
	public String form(RiskIncomeQuota riskIncomeQuota, Model model) {
		if (null != riskIncomeQuota.getQuotaId()){
			riskIncomeQuota = riskIncomeQuotaService.get(riskIncomeQuota);
		}
		List<EnumBean> ioList = Lists.newArrayList();  //结算模式 出入金
		String[] iovalue = {"入金","出金"};
		String[] iokey = {"in","out"};
		for(int i = 0; i < iovalue.length; i++){
			EnumBean ct = new EnumBean();
			ct.setValue(iokey[i]);
			ct.setName(iovalue[i]);
			ioList.add(ct);
		}
		model.addAttribute("ioList", ioList);
		model.addAttribute("riskIncomeQuota", riskIncomeQuota);
		return "modules/riskManage/riskIncomeQuotaForm";
	}

	@RequiresPermissions("riskManage:riskIncomeQuota:edit")
	@RequestMapping(value = "save")
	public String save(RiskIncomeQuota riskIncomeQuota, Model model, RedirectAttributes redirectAttributes) {
		try {
			Integer count = riskIncomeQuotaService.findExistInfo(riskIncomeQuota);
			if (count>0){
				model.addAttribute("message","插入信息已存在");
				List<EnumBean> ioList = Lists.newArrayList();  //结算模式 出入金
				String[] iovalue = {"入金","出金"};
				String[] iokey = {"in","out"};
				for(int i = 0; i < iovalue.length; i++){
					EnumBean ct = new EnumBean();
					ct.setValue(iokey[i]);
					ct.setName(iovalue[i]);
					ioList.add(ct);
				}
				model.addAttribute("ioList", ioList);
				model.addAttribute("riskIncomeQuota", riskIncomeQuota);
				return "modules/riskManage/riskIncomeQuotaForm";
			}else {
				if(null != riskIncomeQuota.getQuotaId()){
					riskIncomeQuota.setUpdateAuthor(UserUtils.getUser().getName());
					riskIncomeQuota.setUpdateTime(DateUtil.getDate());
					riskServiceClient.editRiskIncomeQuota(JsonMapperUtil.nonEmptyMapper().toJson(riskIncomeQuota));
				}else{
					riskIncomeQuota.setCreateAuthor(UserUtils.getUser().getName());
					riskIncomeQuota.setCreateTime(DateUtil.getDate());
					riskServiceClient.addRiskIncomeQuota(JsonMapperUtil.nonEmptyMapper().toJson(riskIncomeQuota));
				}
//				riskIncomeQuotaService.save(riskIncomeQuota);
				if (null != riskIncomeQuota.getQuotaId()){
					addMessage(redirectAttributes, "修改商户出入金限额成功");
				}else {
					addMessage(redirectAttributes, "保存商户出入金限额成功");
				}
				return "redirect:"+Global.getAdminPath()+"/riskManage/riskIncomeQuota/?repage";
			}
		}catch (Exception e){
			logger.error("保存出入金限额 出现错误！{FIND_ERROR}--->{}",e);
		}
		return null;
	}

	@RequiresPermissions("riskManage:riskIncomeQuota:edit")
	@RequestMapping(value = "delete")
	public String delete(RiskIncomeQuota riskIncomeQuota, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (null != riskIncomeQuota.getQuotaId()){
				riskIncomeQuota = riskIncomeQuotaService.get(riskIncomeQuota);
			}
			riskIncomeQuota.setUpdateAuthor(UserUtils.getUser().getName());
			riskIncomeQuota.setUpdateTime(DateUtil.getDate());
			String retStr = riskServiceClient.delRiskIncomeQuota(JsonMapperUtil.nonEmptyMapper().toJson(riskIncomeQuota));
			if(StringUtils.isNotBlank(retStr) && StringUtils.equals(retStr,"1")){
				addMessage(redirectAttributes, "删除商户出入金限额成功【状态置为无效】");
			}
			return "redirect:"+Global.getAdminPath()+"/riskManage/riskIncomeQuota/?repage";
		}catch (Exception e){
			logger.error("删除出入金限额 出现错误！{FIND_ERROR}--->{}",e);
		}
		return null;
	}
}