/**
 *  
 */
package com.heepay.manage.modules.merchant.web;

import java.util.List;

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
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.utils.ListEnum;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantAutographInfoCService;
import com.heepay.manage.modules.merchant.vo.MerchantAutographInfo;

/**
 *
 * 描    述：技术签约Controller
 *
 * 创 建 者：ly
 * 创建时间：2016-08-23
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：ljh
 * 审核时间：2016-09-01
 * 审核描述：删除无用注释代码；73行需对返回结果做健壮性（非空）处理；缺少每段代码和方法注释；
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/merchantAutographInfo")
public class MerchantAutographInfoController extends BaseController {

	@Autowired
	private MerchantAutographInfoCService merchantAutographInfoService;
	
	  
	/**     
	* @discription 根据id获取技术签约数据
	* @author ly       
	* @created 2016年9月2日 下午3:49:36     
	* @param id 技术签约id
	* @return     
	*/
	@ModelAttribute
	public MerchantAutographInfo get(@RequestParam(required=false) String id) {
		MerchantAutographInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantAutographInfoService.get(id);
		}
		if (entity == null){
			entity = new MerchantAutographInfo();
		}
		return entity;
	}
	
	  
	/**     
	* @discription 获取技术签约列表
	* @author ly       
	* @created 2016年9月2日 下午3:50:56     
	* @param merchantAutographInfo 搜索条件
	* @return     
	*/
	@RequiresPermissions("merchant:merchantAutographInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantAutographInfo merchantAutographInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MerchantAutographInfo> page = merchantAutographInfoService.findPage(new Page<MerchantAutographInfo>(request, response), merchantAutographInfo);
		if(page.getList().size()>0){
		  page.setList(EnumView.merchantAutographInfoList(page.getList()));
		}
		model.addAttribute("page", page);
		return "modules/merchant/merchantAutographInfoList";
	}
	
	  
	/**     
	* @discription 获取修改数据
	* @author ly       
	* @created 2016年9月2日 下午3:51:32     
	* @param merchantAutographInfo
	* @param model
	* @return     
	*/
	@RequiresPermissions("merchant:merchantAutographInfo:view")
	@RequestMapping(value = "form")
	public String form(MerchantAutographInfo merchantAutographInfo, Model model) {
	  List<EnumBean> enumList = ListEnum.autographType();
	  List<String> list = ListEnum.stringToList(merchantAutographInfo.getAllowInterfaceType());
	  merchantAutographInfo.setAllowInterfaceTypes(list);
	  model.addAttribute("enumList", enumList);
		model.addAttribute("merchantAutographInfo", merchantAutographInfo);
		return "modules/merchant/merchantAutographInfoForm";
	}
    
	  
	/**     
	* @discription 获取详情数据
	* @author ly       
	* @created 2016年9月2日 下午3:53:31     
	* @param merchantAutographInfo
	* @param model
	* @return     
	*/
	@RequiresPermissions("merchant:merchantAutographInfo:view")
	@RequestMapping(value = "detail")
	public String detail(MerchantAutographInfo merchantAutographInfo, Model model) {
		merchantAutographInfo=EnumView.merchantAutographInfoShow(merchantAutographInfo);
	    model.addAttribute("merchantAutographInfo", merchantAutographInfo);
	    return "modules/merchant/merchantAutographInfoDetail";
	}
	
	  
	/**     
	* @discription 保存修改方法
	* @author ly       
	* @created 2016年9月2日 下午3:53:57     
	* @param merchantAutographInfo 技术签约数据
	* @return     
	*/
	@RequiresPermissions("merchant:merchantAutographInfo:edit")
	@RequestMapping(value = "save")
	public String save(MerchantAutographInfo merchantAutographInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantAutographInfo)){
			return form(merchantAutographInfo, model);
		}
		MerchantAutographInfo merchantAutographInfoExist = merchantAutographInfoService.exist(merchantAutographInfo);
		if(null != merchantAutographInfoExist){
		  model.addAttribute("msg","该商户的"+merchantAutographInfoExist.getProductName()+"的技术签约已存在");
      return form(merchantAutographInfo, model);
		}
		merchantAutographInfoService.save(merchantAutographInfo,false);
		addMessage(redirectAttributes, "保存技术签约成功");
		if(StringUtils.isBlank(merchantAutographInfo.getId())){
		  return "redirect:"+Global.getAdminPath()+"/merchant/merchant/?repage";
		}
		return "redirect:"+Global.getAdminPath()+"/merchant/merchantAutographInfo/?repage";
	}
	
	  
	/**     
	* @discription 修改技术签约状态
	* @author ly       
	* @created 2016年9月2日 下午3:54:29     
	* @param merchantAutographInfo
	* @param redirectAttributes
	* @return     
	*/
	@RequiresPermissions("merchant:merchantAutographInfo:edit")
	@RequestMapping(value = "status")
	public String status(MerchantAutographInfo merchantAutographInfo, RedirectAttributes redirectAttributes) {
		merchantAutographInfoService.status(merchantAutographInfo);
	   addMessage(redirectAttributes, "成功");
	   return "redirect:"+Global.getAdminPath()+"/merchant/merchantAutographInfo/?repage";
	}

}