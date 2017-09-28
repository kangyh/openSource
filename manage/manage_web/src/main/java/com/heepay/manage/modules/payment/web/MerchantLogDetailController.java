/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.payment.entity.MerchantLogDetail;
import com.heepay.manage.modules.payment.service.MerchantLogDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;


/**
 *
 * 描    述：paymentController
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
@RequestMapping(value = "${adminPath}/payment/merchantLogDetail")
public class MerchantLogDetailController extends BaseController {

	@Autowired
	private MerchantLogDetailService merchantLogDetailService;
	
	@ModelAttribute
	public MerchantLogDetail get(@RequestParam(required=false) String id) {
		MerchantLogDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantLogDetailService.get(id);
		}
		if (entity == null){
			entity = new MerchantLogDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:merchantLogDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantLogDetail merchantLogDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MerchantLogDetail> page = merchantLogDetailService.findPage(new Page<MerchantLogDetail>(request, response), merchantLogDetail); 
		model.addAttribute("page", page);
		return "modules/payment/merchantLogDetailList";
	}

	@RequiresPermissions("payment:merchantLogDetail:delete")
	@RequestMapping(value = "merchantLogDetailDel")
	public String merchantLogDetailDel(MerchantLogDetail merchantLogDetail, RedirectAttributes redirectAttributes) {
		if(StringUtils.isBlank(merchantLogDetail.getSettleId())){
			addMessage(redirectAttributes, "结算批次号不能为空");
		}else {
			merchantLogDetailService.deleteBySettleId(merchantLogDetail);
			addMessage(redirectAttributes, "删除账务明细成功");
		}
		return "redirect:"+Global.getAdminPath()+"/payment/merchantLogDetail/?repage";
	}
}