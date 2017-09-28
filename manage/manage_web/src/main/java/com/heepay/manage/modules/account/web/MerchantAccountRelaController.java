/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.web;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.account.entity.MerchantAccountRela;
import com.heepay.manage.modules.account.rpc.IAccountClient;
import com.heepay.manage.modules.account.service.MerchantAccountRelaService;


/**
 *
 * 描    述：账务关联账户Controller
 *
 * 创 建 者： @author zjx
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
@RequestMapping(value = "${adminPath}/account/merchantAccountRela")
public class MerchantAccountRelaController extends BaseController {

	@Autowired
	private MerchantAccountRelaService merchantAccountRelaService;

	@Autowired
	private IAccountClient iAccountClient;

	@ModelAttribute
	public MerchantAccountRela get(@RequestParam(required=false) String id) {
		MerchantAccountRela entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantAccountRelaService.get(id);
		}
		if (entity == null){
			entity = new MerchantAccountRela();
		}
		return entity;
	}
	
	@RequiresPermissions("account:merchantAccountRela:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantAccountRela merchantAccountRela, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<MerchantAccountRela> page = merchantAccountRelaService.findPage(new Page<MerchantAccountRela>(request, response), merchantAccountRela);
		List<MerchantAccountRela> merchantAccountRelaList = merchantAccountRelaService.findAllList();
		Map<String,List<MerchantAccountRela>> merchantAccountRelaMap = new HashMap<String,List<MerchantAccountRela>>();
		for(MerchantAccountRela mrela: merchantAccountRelaList){
			String key = mrela.getTransType().concat("_").concat(mrela.getTransSubType());
			List<MerchantAccountRela> merchantAccountRelas =new ArrayList<MerchantAccountRela>();
			if(merchantAccountRelaMap.get(key) != null){
				List<MerchantAccountRela> ms = merchantAccountRelaMap.get(key);
				ms.add(mrela);
				merchantAccountRelas.addAll(ms);
			}else{
				merchantAccountRelas.add(mrela);
			}
			merchantAccountRelaMap.put(key, merchantAccountRelas);
		}
		model.addAttribute("merchantAccountRelaMap", merchantAccountRelaMap);
		return "modules/account/merchantAccountRelaList";
	}

	@RequiresPermissions("account:merchantAccountRela:view")
	@RequestMapping(value = "form")
	public String form(MerchantAccountRela merchantAccountRela, Model model) {
		model.addAttribute("merchantAccountRela", merchantAccountRela);
		return "modules/account/merchantAccountRelaForm";
	}

	@RequiresPermissions("account:merchantAccountRela:view")
	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String id,@RequestParam(required=false) String transType,
					  @RequestParam(required=false) String transSubType,MerchantAccountRela merchantAccountRela, Model model) {
		return "modules/account/merchantAccountRelaForm";
	}

	@RequiresPermissions("account:merchantAccountRela:edit")
	@RequestMapping(value = "save")
	public String save(MerchantAccountRela merchantAccountRela, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantAccountRela)){
			return form(merchantAccountRela, model);
		}
		merchantAccountRelaService.save(merchantAccountRela);
		iAccountClient.refreshRelationAccount();
		addMessage(redirectAttributes, "保存账务关联账户成功");
		return "redirect:"+Global.getAdminPath()+"/account/merchantAccountRela/?repage";
	}
	
	@RequiresPermissions("account:merchantAccountRela:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantAccountRela merchantAccountRela, RedirectAttributes redirectAttributes) {
		merchantAccountRelaService.delete(merchantAccountRela);
		iAccountClient.refreshRelationAccount();
		addMessage(redirectAttributes, "删除账务关联账户成功");
		return "redirect:"+Global.getAdminPath()+"/account/merchantAccountRela/?repage";
	}

}