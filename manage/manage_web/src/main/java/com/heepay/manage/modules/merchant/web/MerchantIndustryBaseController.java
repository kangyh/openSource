/**
 *  
 */
package com.heepay.manage.modules.merchant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.modules.merchant.service.MerchantIndustryBaseCService;
import com.heepay.manage.modules.merchant.vo.MerchantIndustryBase;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.route.entity.BankCardBin;
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

/**
 * mcc基础数据Controller
 * @author ly
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/merchantIndustryBase")
public class MerchantIndustryBaseController extends BaseController {

	@Autowired
	private MerchantIndustryBaseCService merchantIndustryBaseService;
	
	@ModelAttribute
	public MerchantIndustryBase get(@RequestParam(required=false) String id) {
		MerchantIndustryBase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantIndustryBaseService.get(id);
		}
		if (entity == null){
			entity = new MerchantIndustryBase();
		}
		return entity;
	}
	
	@RequiresPermissions("merchant:merchantIndustryBase:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantIndustryBase merchantIndustryBase, HttpServletRequest request, HttpServletResponse response, Model model) {

		//使用cookie保存查询条件
		merchantIndustryBase = (MerchantIndustryBase) SaveConditions.result(merchantIndustryBase, "merchantIndustryBases", request, response);

		Page<MerchantIndustryBase> page = merchantIndustryBaseService.findPage(new Page<MerchantIndustryBase>(request, response), merchantIndustryBase); 
		model.addAttribute("page", page);
		model.addAttribute("merchantIndustryBase", merchantIndustryBase);

		return "modules/merchant/merchantIndustryBaseList";
	}

	@RequiresPermissions("merchant:merchantIndustryBase:view")
	@RequestMapping(value = "form")
	public String form(MerchantIndustryBase merchantIndustryBase, Model model) {
		model.addAttribute("merchantIndustryBase", merchantIndustryBase);
		return "modules/merchant/merchantIndustryBaseForm";
	}

	@RequiresPermissions("merchant:merchantIndustryBase:edit")
	@RequestMapping(value = "save")
	public String save(MerchantIndustryBase merchantIndustryBase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantIndustryBase)){
			return form(merchantIndustryBase, model);
		}
		MerchantIndustryBase merchantIndustryBaseExist = merchantIndustryBaseService.getMcc(merchantIndustryBase.getMcc());
		if(null != merchantIndustryBaseExist){
			model.addAttribute("msg","商户类别码重复");
			return form(merchantIndustryBase, model);
		}
		merchantIndustryBaseService.save(merchantIndustryBase,false);
		addMessage(redirectAttributes, "保存mcc基础数据成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/merchantIndustryBase?cache=1&repage";
	}
	
	@RequiresPermissions("merchant:merchantIndustryBase:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantIndustryBase merchantIndustryBase, RedirectAttributes redirectAttributes) {
		merchantIndustryBaseService.delete(merchantIndustryBase);
		addMessage(redirectAttributes, "删除mcc基础数据成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/merchantIndustryBase?cache=1&repage";
	}

}