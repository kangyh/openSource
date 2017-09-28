package com.heepay.manage.modules.route.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.route.entity.BankCardBin;
import com.heepay.manage.modules.route.service.BankCardBinService;
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
* 描    述：银行卡bin管理Controller
*
* 创 建 者： lgk
* 创建时间： 2016年9月9日 下午4:16:30 
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
@RequestMapping(value = "${adminPath}/route/bankCardBin")
public class BankCardBinController extends BaseController {

  	@Autowired
  	private BankCardBinService bankCardBinService;
  
  	  
  	/**     
  	* @discription 根据id获取银行卡bin信息
  	* @author lgk
  	* @created 2016年9月9日 下午4:17:35     
  	* @param id
  	* @return     
  	*/
  	@ModelAttribute
  	public BankCardBin get(@RequestParam(required=false) String id) {
		BankCardBin entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankCardBinService.get(id);
		}
		if (entity == null){
			entity = new BankCardBin();
		}
		return entity;
  	}
  	
  	  
  	/**     
  	* @discription 获取银行卡bin列表
  	* @author lgk
  	* @created 2016年9月9日 下午4:18:05     
  	* @param bankCardBin
  	* @param request
  	* @param response
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:bankCardBin:view")
  	@RequestMapping(value = {"list", ""})
  	public String list(BankCardBin bankCardBin, HttpServletRequest request, HttpServletResponse response, Model model) {

		//使用cookie保存查询条件
		bankCardBin = (BankCardBin) SaveConditions.result(bankCardBin, "bankCardBins", request, response);

		Page<BankCardBin> page = bankCardBinService.findPage(new Page<BankCardBin>(request, response), bankCardBin);
		if(null!=page.getList() && !page.getList().isEmpty()) {
			page.setList(EnumView.bankCardBin(page.getList()));
		}
		model.addAttribute("page", page);
		model.addAttribute("bankCardBin", bankCardBin);

		return "modules/route/bankCardBinList";
  	}
  
  	  
  	/**     
  	* @discription 获取银行卡bin信息新增修改页面
  	* @author lgk
  	* @created 2016年9月9日 下午4:18:35     
  	* @param bankCardBin
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:bankCardBin:view")
  	@RequestMapping(value = "form")
  	public String form(BankCardBin bankCardBin, Model model) {
		model.addAttribute("bankCardBin", bankCardBin);
		return "modules/route/bankCardBinForm";
  	}
  
  	  
  	/**     
  	* @discription 保存修改银行卡bin信息
  	* @author lgk
  	* @created 2016年9月9日 下午4:19:06     
  	* @param bankCardBin
  	* @param model
  	* @param redirectAttributes
  	* @return     
  	*/
  	@RequiresPermissions("route:bankCardBin:edit")
  	@RequestMapping(value = "save")
  	public String save(BankCardBin bankCardBin, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bankCardBin)){
			return form(bankCardBin, model);
		}
		BankCardBin bankCardBinNew = bankCardBinService.selectBankCardNote(bankCardBin);
		if(StringUtils.isBlank(bankCardBin.getId())){
				bankCardBin.setStatus("ENABLE");
		}
		if(null==bankCardBinNew){
			bankCardBinService.save(bankCardBin,false);
			addMessage(redirectAttributes, "保存银行卡bin信息成功");
		}else{
			if(bankCardBin.getId().equals(bankCardBinNew.getId())){
				bankCardBinService.save(bankCardBin,false);
				addMessage(redirectAttributes, "保存银行卡bin信息成功");
			}else{
				addMessage(redirectAttributes, "该发卡行标识已存在");
			}
		}
		return "redirect:"+Global.getAdminPath()+"/route/bankCardBin?cache=1&repage";
  	}
  	
      
    /**     
    * @discription 获取银行卡bin信息详情页面
    * @author lgk
    * @created 2016年9月9日 下午4:21:23     
    * @param bankCardBin
    * @param model
    * @return     
    */
    @RequestMapping(value = "details")
    public String details(BankCardBin bankCardBin, Model model) {
		bankCardBin = EnumView.bankCardBinShow(bankCardBin);
        model.addAttribute("bankCardBin", bankCardBin);
        return "modules/route/bankCardBinDetails";
    }

	/**
	 * @discription 银行卡bin启用、禁用状态转换
	 * @author lgk
	 * @created 2016年9月7日 下午5:33:18
	 * @param bankCardBin
	 * @param redirectAttributes
	 * @return
	 */

	@RequiresPermissions("route:bankCardBin:edit")
	@RequestMapping(value = "status")
	public String status(BankCardBin bankCardBin, RedirectAttributes redirectAttributes) {
		bankCardBinService.status(bankCardBin);
		addMessage(redirectAttributes,"成功");
		return "redirect:"+Global.getAdminPath()+"/route/bankCardBin?cache=1&repage";
	}
  	   
  	/**     
  	* @discription 银行卡bin信息缓存
  	* @author lgk
  	* @created 2016年9月9日 下午4:22:29     
  	* @param redirectAttributes
  	* @return     
  	*/
  	@RequestMapping(value = "updateCache")
  	 public String updateCache(RedirectAttributes redirectAttributes){
         bankCardBinService.updateCache();
         addMessage(redirectAttributes, "更新缓存成功");
         return "redirect:"+Global.getAdminPath()+"/route/bankCardBin?cache=1&repage";
  	  }
}