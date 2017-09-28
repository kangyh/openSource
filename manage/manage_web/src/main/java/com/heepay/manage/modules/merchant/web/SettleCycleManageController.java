/**
 *  
 */
package com.heepay.manage.modules.merchant.web;

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
import org.springframework.web.util.HtmlUtils;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.CommonStatus;
import com.heepay.enums.SettlementTo;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.AmountChangeUtil;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.SettleCycleManageCService;
import com.heepay.manage.modules.merchant.vo.SettleCycleManage;


/**          
* 
* 描    述：结算周期controller
*
* 创 建 者： ly
* 创建时间： 2016年9月2日 下午5:04:18 
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
@RequestMapping(value = "${adminPath}/merchant/settleCycleManage")
public class SettleCycleManageController extends BaseController {

	@Autowired
	private SettleCycleManageCService settleCycleManageService;
	
	  
	/**     
	* @discription 根据id获取结算周期
	* @author ly     
	* @created 2016年9月2日 下午5:04:50     
	* @param id
	* @return     
	*/
	@ModelAttribute
	public SettleCycleManage get(@RequestParam(required=false) String id) {
		SettleCycleManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = settleCycleManageService.get(id);
		}
		if (entity == null){
			entity = new SettleCycleManage();
		}
		return entity;
	}
	
	  
	/**     
	* @discription 获取结算周期列表
	* @author ly     
	* @created 2016年9月2日 下午5:05:08     
	* @param settleCycleManage
	* @param request
	* @param response
	* @param model
	* @return     
	*/
	@RequiresPermissions("merchant:settleCycleManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(SettleCycleManage settleCycleManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SettleCycleManage> page = settleCycleManageService.findPage(new Page<SettleCycleManage>(request, response), settleCycleManage);
		page.setList(EnumView.settleCycleManageList(page.getList()));
		model.addAttribute("page", page);
		return "modules/merchant/settleCycleManageList";
	}

	  
	/**     
	* @discription 获取结算周期新增修改页面
	* @author ly     
	* @created 2016年9月2日 下午5:05:17     
	* @param settleCycleManage
	* @param model
	* @return     
	*/
	@RequiresPermissions("merchant:settleCycleManage:view")
	@RequestMapping(value = "form")
	public String form(SettleCycleManage settleCycleManage, Model model) {
	  if(null != settleCycleManage.getMinSettlementAmount()){
	    settleCycleManage.setMinSettlementAmount(AmountChangeUtil.change(settleCycleManage.getMinSettlementAmount()));
	  }
		model.addAttribute("settleCycleManage", settleCycleManage);
		return "modules/merchant/settleCycleManageForm";
	}
	
    
  /**     
  * @discription 获取结算周期详情
  * @author ly     
  * @created 2016年9月2日 下午5:06:25     
  * @param settleCycleManage
  * @param model
  * @return     
  */
  @RequiresPermissions("merchant:settleCycleManage:view")
  @RequestMapping(value = "detail")
  public String detail(SettleCycleManage settleCycleManage, Model model) {
    if(StringUtils.isNotBlank(settleCycleManage.getStatus())){
      settleCycleManage.setStatus(CommonStatus.labelOf(settleCycleManage.getStatus()));
    }
    if(StringUtils.isNotBlank(settleCycleManage.getSettlementTo())){
      settleCycleManage.setSettlementTo(SettlementTo.labelOf(settleCycleManage.getSettlementTo()));
    }
    if(null != settleCycleManage.getMinSettlementAmount()){
      settleCycleManage.setMinSettlementAmount(AmountChangeUtil.change(settleCycleManage.getMinSettlementAmount()));
    }
    model.addAttribute("settleCycleManage", settleCycleManage);
    return "modules/merchant/settleCycleManageDetail";
  }
	
	  
	/**     
	* @discription 保存修改结算周期
	* @author ly     
	* @created 2016年9月2日 下午5:06:43     
	* @param settleCycleManage
	* @param model
	* @param redirectAttributes
	* @return     
	*/
	@RequiresPermissions("merchant:settleCycleManage:edit")
	@RequestMapping(value = "save")
	public String save(SettleCycleManage settleCycleManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, settleCycleManage)){
			return form(settleCycleManage, model);
		}
		if(StringUtils.isNotBlank(settleCycleManage.getProductName())){
		  settleCycleManage.setProductName(HtmlUtils.htmlUnescape(settleCycleManage.getProductName()));
		}
		SettleCycleManage settleCycleManageExsit = settleCycleManageService.exsit(settleCycleManage);
		if(null != settleCycleManageExsit){
		  model.addAttribute("msg","该商户的"+settleCycleManageExsit.getProductName()+"的结算周期已存在");
		  return form(settleCycleManage, model);
		}
		settleCycleManageService.save(settleCycleManage, false);
		addMessage(redirectAttributes, "保存结算周期成功");
    if(StringUtils.isBlank(settleCycleManage.getId())){
      return "redirect:"+Global.getAdminPath()+"/merchant/merchant/?repage";
    }
		return "redirect:"+Global.getAdminPath()+"/merchant/settleCycleManage/?repage";
	}
	
	  
	/**     
	* @discription 修改结算周期状态
	* @author ly     
	* @created 2016年9月2日 下午5:07:10     
	* @param settleCycleManage
	* @param redirectAttributes
	* @return     
	*/
	@RequiresPermissions("merchant:settleCycleManage:edit")
  @RequestMapping(value = "status")
  public String status(SettleCycleManage settleCycleManage, RedirectAttributes redirectAttributes) {
    settleCycleManageService.status(settleCycleManage);
    addMessage(redirectAttributes, "成功");
    return "redirect:"+Global.getAdminPath()+"/merchant/settleCycleManage/?repage";
  }

}