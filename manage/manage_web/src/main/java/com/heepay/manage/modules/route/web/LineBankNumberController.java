package com.heepay.manage.modules.route.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.CommonStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.route.entity.LineBankNumber;
import com.heepay.manage.modules.route.service.LineBankNumberService;
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


/**          
* 
* 描    述：联行号管理Controller
*
* 创 建 者： 牛文
* 创建时间： 2016年9月9日 下午4:23:23 
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
@RequestMapping(value = "${adminPath}/route/lineBankNumber")
public class LineBankNumberController extends BaseController {

  	@Autowired
  	private LineBankNumberService lineBankNumberService;

  	/**     
  	* @discription 根据id获取联行号信息
  	* @author 牛文
  	* @created 2016年9月9日 下午4:24:02     
  	* @param id
  	* @return     
  	*/
  	@ModelAttribute
  	public LineBankNumber get(@RequestParam(required=false) String id) {
		LineBankNumber entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = lineBankNumberService.get(id);
		}
		if (entity == null){
			entity = new LineBankNumber();
		}
		return entity;
  	}
  	
  	  
  	/**     
  	* @discription 获取联行号信息列表
  	* @author 牛文
  	* @created 2016年9月9日 下午4:24:31     
  	* @param lineBankNumber
  	* @param request
  	* @param response
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:lineBankNumber:view")
  	@RequestMapping(value = {"list", ""})
  	public String list(LineBankNumber lineBankNumber, HttpServletRequest request, HttpServletResponse response, Model model) {

		//使用cookie保存查询条件
		lineBankNumber = (LineBankNumber) SaveConditions.result(lineBankNumber, "lineBankNumbers", request, response);

		model.addAttribute("provinceCodeTemp", lineBankNumber.getProvinceCode());
		model.addAttribute("cityCodeTemp", lineBankNumber.getCityCode());

		model.addAttribute("lineBankNumber", lineBankNumber);
		Page<LineBankNumber> page = lineBankNumberService.findPage(new Page<LineBankNumber>(request, response), lineBankNumber);
		if(null!=page.getList() && !page.getList().isEmpty()) {
			page.setList(EnumView.LineBankNumber(page.getList()));
		}
		model.addAttribute("page", page);
		return "modules/route/lineBankNumberList";
  	}
  
  	  
  	/**     
  	* @discription 获取联行号新增修改页面
  	* @author 牛文
  	* @created 2016年9月9日 下午4:25:01     
  	* @param lineBankNumber
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:lineBankNumber:view")
  	@RequestMapping(value = "form")
  	public String form(LineBankNumber lineBankNumber, Model model) {
		model.addAttribute("lineBankNumber", lineBankNumber);
		return "modules/route/lineBankNumberForm";
  	}
  
  	  
  	/**     
  	* @discription 联行号保存修改方法
  	* @author 牛文
  	* @created 2016年9月9日 下午4:25:25     
  	* @param lineBankNumber
  	* @param model
  	* @param redirectAttributes
  	* @return     
  	*/
  	@RequiresPermissions("route:lineBankNumber:edit")
  	@RequestMapping(value = "save")
  	public String save(LineBankNumber lineBankNumber, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, lineBankNumber)){
			return form(lineBankNumber, model);
		}
		LineBankNumber lineBankNumberInner = lineBankNumberService.selectByLineNumber(lineBankNumber.getAssociateLineNumber());
		if(StringUtils.isBlank(lineBankNumber.getId())) {
			lineBankNumber.setStatus(CommonStatus.ENABLE.getValue());
		}
		if(null==lineBankNumberInner){
			lineBankNumberService.save(lineBankNumber,false);
			addMessage(redirectAttributes, "保存联行号成功");
		}else{
			if(lineBankNumber.getId().equals(lineBankNumberInner.getId())){
				lineBankNumberService.save(lineBankNumber,false);
				addMessage(redirectAttributes, "保存联行号信息成功");
			}else{
				addMessage(redirectAttributes, "此联行号已存在");
			}
		}
		return "redirect:"+Global.getAdminPath()+"/route/lineBankNumber?cache=1&repage";
  	}

	/**
	 * @discription 联行号启用、禁用状态转换
	 * @author 牛文
	 * @created 2016年9月7日 下午5:33:18
	 * @param lineBankNumber
	 * @param redirectAttributes
	 * @return
	 */

	@RequiresPermissions("route:lineBankNumber:edit")
	@RequestMapping(value = "status")
	public String status(LineBankNumber lineBankNumber, RedirectAttributes redirectAttributes) {
		lineBankNumberService.status(lineBankNumber);
		addMessage(redirectAttributes,"成功");
		return "redirect:"+Global.getAdminPath()+"/route/lineBankNumber?cache=1&repage";
	}
  	  
  	/**     
  	* @discription 获取联行号信息详情页面
  	* @author 牛文
  	* @created 2016年9月9日 下午4:27:01     
  	* @param lineBankNumber
  	* @param model
  	* @return     
  	*/
  	@RequestMapping(value = "details")
  	public String details(LineBankNumber lineBankNumber, Model model) {
		model.addAttribute("lineBankNumber", lineBankNumber);
		return "modules/route/lineBankNumberDetails";
  	}

}