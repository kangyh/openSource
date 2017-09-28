/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.*;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.NormProductCService;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.utils.ProductTrxTypeUtils;
import com.heepay.manage.modules.merchant.vo.NormProduct;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
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
import java.util.List;


/**
 *
 * 描    述：标准产品Controller
 *
 * 创 建 者： @author ly
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
@RequestMapping(value = "${adminPath}/merchant/normProduct")
public class NormProductController extends BaseController {

	@Autowired
	private NormProductCService normProductCService;

	@Autowired
	private ProductCService productCService;
	
	@ModelAttribute
	public NormProduct get(@RequestParam(required=false) String id) {
		NormProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = normProductCService.get(id);
		}
		if (entity == null){
			entity = new NormProduct();
		}
		return entity;
	}
	
	@RequiresPermissions("merchant:normProduct:view")
	@RequestMapping(value = {"list", ""})
	public String list(NormProduct normProduct, HttpServletRequest request, HttpServletResponse response, Model model) {

		//使用cookie保存查询条件
		normProduct = (NormProduct) SaveConditions.result(normProduct, "normProducts", request, response);

		Page<NormProduct> page = normProductCService.findPage(new Page<NormProduct>(request, response), normProduct);
		if(null != page.getList() && !page.getList().isEmpty()){
			List<NormProduct> list = Lists.newArrayList();
			for(NormProduct normProductFor : page.getList()){
				if(StringUtils.isNotBlank(normProductFor.getChargeType())) {
					normProductFor.setChargeType(CostType.labelOf(normProductFor.getChargeType()));
				}
				if(StringUtils.isNotBlank(normProductFor.getProductCode())){
					Product product = productCService.selectByCode(normProductFor.getProductCode());
					normProductFor.setProductName(product.getName());
					normProductFor.setBusinessType(RateBusinessType.labelOf(product.getBusinessType()));
					normProductFor.setTrxType(ProductTrxTypeUtils.getProductTrxTypeName(product.getTrxType()));
				}
				if(StringUtils.isNotBlank(normProductFor.getBankCardType())){
					normProductFor.setBankCardType(BankcardType.labelOf(normProductFor.getBankCardType()));
				}
				if (StringUtils.isNotBlank(normProductFor.getChargeCollectionType())) {
					normProductFor
							.setChargeCollectionType(ChargeCollectionType.labelOf(normProductFor.getChargeCollectionType()));
				}
				if (StringUtils.isNotBlank(normProductFor.getChargeDeductType())) {
					normProductFor.setChargeDeductType(ChargeDeductType.labelOf(normProductFor.getChargeDeductType()));
				}
				if (StringUtils.isNotBlank(normProductFor.getChargeSource())) {
					normProductFor.setChargeSource(ChargeSource.labelOf(normProductFor.getChargeSource()));
				}
				list.add(normProductFor);
			}
			page.setList(list);
		}

		model.addAttribute("page", page);
		model.addAttribute("normProduct", normProduct);
		return "modules/merchant/normProductList";
	}

	@RequiresPermissions("merchant:normProduct:view")
	@RequestMapping(value = "form")
	public String form(NormProduct normProduct, Model model) {
		model.addAttribute("normProduct", normProduct);
		return "modules/merchant/normProductForm";
	}

	@RequiresPermissions("merchant:normProduct:edit")
	@RequestMapping(value = "save")
	public String save(NormProduct normProduct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, normProduct)){
			return form(normProduct, model);
		}
		NormProduct normProductExist = normProductCService.selectExist(normProduct);
		if(null != normProductExist){
			model.addAttribute("msg","该产品的标准产品已存在");
			return form(normProduct, model);
		}
		normProductCService.save(normProduct,false);
		addMessage(redirectAttributes, "保存标准产品成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/normProduct?cache=1&repage";
	}
	
	@RequiresPermissions("merchant:normProduct:edit")
	@RequestMapping(value = "delete")
	public String delete(NormProduct normProduct, RedirectAttributes redirectAttributes) {
		normProductCService.delete(normProduct);
		addMessage(redirectAttributes, "删除标准产品成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/normProduct?cache=1&repage";
	}

}