/**
 *  
 */
package com.heepay.manage.modules.merchant.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.ProductNetGroupCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.merchant.vo.ProductNetGroup;
import com.heepay.manage.modules.merchant.vo.SettleCycleManage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 名称：产品关联.net组
 * 创建者：ly
 * 创建日期：2017年4月10日20:18:24
 * 创建描述：产品关联.net组
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/productNetGroup")
public class ProductNetGroupController extends BaseController {

  	@Autowired
  	private ProductNetGroupCService productNetGroupCService;
  	
  	  
  	/**      
  	* @discription 根据id获取产品通道信息
  	* @author L.M
  	* @created 2016年9月7日 下午5:56:26     
  	* @param id
  	* @return     
  	*/
  	@ModelAttribute
  	public ProductNetGroup get(@RequestParam(required=false) String id) {
		ProductNetGroup entity = null;
  		if (StringUtils.isNotBlank(id)){
  			entity = productNetGroupCService.get(id);
  		}
  		if (entity == null){
  			entity = new ProductNetGroup();
  		}
  		return entity;
  	}

	@RequiresPermissions("merchant:productNetGroup:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductNetGroup productNetGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductNetGroup> page = productNetGroupCService.findPage(new Page<ProductNetGroup>(request, response), productNetGroup);
		page.setList(EnumView.productNetGroupList(page.getList()));
		model.addAttribute("page", page);
		return "modules/merchant/productNetGroupList";
	}

	/**
	 * @discription 获取产品组关联新增修改页面
	 * @author ly
	 * @created 2017年4月11日10:01:11
	 * @param model
	 * @return
	 */
	@RequiresPermissions("merchant:productNetGroup:edit")
	@RequestMapping(value = "form")
	public String form(ProductNetGroup productNetGroup, Model model) {
		model.addAttribute("productNetGroup", productNetGroup);
		return "modules/merchant/productNetGroupForm";
	}

	@RequiresPermissions("merchant:productNetGroup:edit")
	@RequestMapping(value = "save")
	public String save(ProductNetGroup productNetGroup, Model model, RedirectAttributes redirectAttributes) {
		ProductNetGroup productNetGroupExist = productNetGroupCService.getByCodeExist(productNetGroup);
		if (null != productNetGroupExist){
			model.addAttribute("msg","该产品已配置过组");
			return form(productNetGroup,model);
		}
		if (StringUtils.isNotBlank(productNetGroup.getProductName())) {
			productNetGroup.setProductName(HtmlUtils.htmlUnescape(productNetGroup.getProductName()));
		}
		productNetGroupCService.save(productNetGroup,false);
		addMessage(redirectAttributes, "成功");
		return "redirect:" + Global.getAdminPath() + "/merchant/productNetGroup/?repage";
	}
}