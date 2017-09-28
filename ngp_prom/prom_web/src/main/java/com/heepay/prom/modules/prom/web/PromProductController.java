/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.web;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;
import com.heepay.prom.common.config.Global;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.web.BaseController;
import com.heepay.prom.modules.prom.entity.PromProduct;
import com.heepay.prom.modules.prom.service.PromProductService;
import com.heepay.prom.modules.prom.utils.ImportBathUtils;
import com.heepay.prom.modules.sys.utils.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.Map;


/**
 *
 * 描    述：产品管理Controller
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
@RequestMapping(value = "${adminPath}/prom/promProduct")
public class PromProductController extends BaseController {

	@Autowired
	private PromProductService promProductService;
	private static final Logger logger = LogManager.getLogger();
	private static PropertiesLoader loader = new PropertiesLoader("riskPbc.properties");
	
	@ModelAttribute
	public PromProduct get(@RequestParam(required=false) String id) {
		PromProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = promProductService.get(id);
		}
		if (entity == null){
			entity = new PromProduct();
		}
		return entity;
	}
	
	@RequiresPermissions("prom:promProduct:view")
	@RequestMapping(value = {"list", ""})
	public String list(PromProduct promProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PromProduct> page = promProductService.findPage(new Page<PromProduct>(request, response), promProduct); 
		model.addAttribute("page", page);
		return "modules/prom/promProductList";
	}

	@RequiresPermissions("prom:promProduct:view")
	@RequestMapping(value = "form")
	public String form(PromProduct promProduct, Model model) {
		model.addAttribute("promProduct", promProduct);
		return "modules/prom/promProductForm";
	}

	@RequiresPermissions("prom:promProduct:edit")
	@RequestMapping(value = "save")
	public String save(PromProduct promProduct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, promProduct)){
			return form(promProduct, model);
		}
		promProductService.save(promProduct);
		addMessage(redirectAttributes, "保存产品管理成功");
		return "redirect:"+Global.getAdminPath()+"/prom/promProduct/?repage";
	}
	
	@RequiresPermissions("prom:promProduct:edit")
	@RequestMapping(value = "delete")
	public String delete(PromProduct promProduct, RedirectAttributes redirectAttributes) {
		promProductService.delete(promProduct);
		addMessage(redirectAttributes, "删除产品管理成功");
		return "redirect:"+Global.getAdminPath()+"/prom/promProduct/?repage";
	}
	
	
	@RequiresPermissions("prom:promProduct:edit")
	@RequestMapping(value ="onImport")
	public String onImport(RedirectAttributes redirectAttributes, HttpServletRequest request,
                           HttpServletResponse response){
		return "modules/prom/productAdd";
		
	}
	
	
	//添加产品页
	@RequiresPermissions("prom:promProduct:edit")
	@RequestMapping(value ="upload")
	public String upload(RedirectAttributes redirectAttributes, HttpServletRequest request,
                         HttpServletResponse response){
		    String productJson = request.getParameter("file");
		    JsonMapperUtil jsonUtil = JsonMapperUtil.buildNonDefaultMapper();
		    Map<String, Object> mapMessage1 = JsonMapperUtil.nonEmptyMapper().fromJson(productJson, Map.class);
		    List list = (List) mapMessage1.get("rows");
		    String  importBath = ImportBathUtils.getImportBath();
		    for (Object object : list) {
		    	PromProduct promProduct = new PromProduct();
		    	Map<String, Object> mapMessage2 = JsonMapperUtil.nonEmptyMapper().fromJson(jsonUtil.toJson(object), Map.class);
		    	promProduct.setProductId(mapMessage2.get("productCode").toString()); 
		    	promProduct.setProductName(mapMessage2.get("productName").toString());
		    	promProduct.setSpreadMoney(mapMessage2.get("minPremium").toString());
		    	promProduct.setSpreadScale(mapMessage2.get("defCommissionRate").toString());
		    	promProduct.setCreateTime(new Date());
		    	promProduct.setCreator(UserUtils.getUser().getName());
		    	promProduct.setImportBath(importBath);
		    	promProductService.insert(promProduct);
		    }
			addMessage(redirectAttributes, "产品数据导入成功");
		return "redirect:"+Global.getAdminPath()+"/prom/promProduct?repage";
		
	}

}