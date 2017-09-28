/**
 *  
 */
package com.heepay.manage.modules.merchant.web;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantBankCardCService;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 描    述：商户银行信息controller
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
 * 审核描述：65行需对返回结果做健壮性（非空）处理；缺少每段代码和方法注释；注释头部认真描述该类作用
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/merchantBankCard")
public class MerchantBankCardController extends BaseController {

	@Autowired
	private MerchantBankCardCService merchantBankCardService;
	
	  
	/**     
	* @discription 根据id获取商户银行信息
	* @author ly       
	* @created 2016年9月2日 下午4:08:47     
	* @param id
	* @return     
	*/
	@ModelAttribute
	public MerchantBankCard get(@RequestParam(required=false) String id) {
		MerchantBankCard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantBankCardService.get(id);
		}
		if (entity == null){
			entity = new MerchantBankCard();
		}
		return entity;
	}
	
	  
	/**     
	* @discription 获取商户银行信息列表
	* @author ly       
	* @created 2016年9月2日 下午4:09:36     
	* @param merchantBankCard
	* @param request
	* @param response
	* @param model
	* @return     
	*/
	@RequiresPermissions("merchant:merchantBankCard:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantBankCard merchantBankCard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MerchantBankCard> page = merchantBankCardService.findPage(new Page<MerchantBankCard>(request, response), merchantBankCard);
		page.setList(EnumView.changeMerchantBankCard(page.getList()));
		model.addAttribute("page", page);
		return "modules/merchant/merchantBankCardList";
	}

	  
	/**     
	* @discription 获取商户银行信息新增修改页面
	* @author ly       
	* @created 2016年9月2日 下午4:09:49     
	* @param merchantBankCard
	* @param model
	* @return     
	*/
	@RequiresPermissions("merchant:merchantBankCard:view")
	@RequestMapping(value = "form")
	public String form(MerchantBankCard merchantBankCard, Model model) {
		model.addAttribute("merchantBankCard", merchantBankCard);
		return "modules/merchant/merchantBankCardForm";
	}

	  
	/**     
	* @discription 保存修改商户银行信息
	* @author ly       
	* @created 2016年9月2日 下午4:10:09     
	* @param merchantBankCard
	* @param model
	* @param redirectAttributes
	* @return     
	*/
	@RequiresPermissions("merchant:merchantBankCard:edit")
	@RequestMapping(value = "save")
	public String save(MerchantBankCard merchantBankCard, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantBankCard)){
			return form(merchantBankCard, model);
		}
		merchantBankCardService.save(merchantBankCard, false);
		addMessage(redirectAttributes, "保存merchant成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/merchantBankCard/?repage";
	}

	/**
	 * @discription 查看银行卡号
	 * @author ly
	 * @created 2017年6月29日14:17:01
	 */
	@RequiresPermissions("merchant:merchantBankCard:get")
	@RequestMapping(value = "getBankNo")
	@ResponseBody
	public String getBankNo(MerchantBankCard merchantBankCard) {
		String bankNo = Aes.decryptStr(merchantBankCard.getBankNo(), Constants.QuickPay.SYSTEM_KEY);
		return bankNo;
	}

}