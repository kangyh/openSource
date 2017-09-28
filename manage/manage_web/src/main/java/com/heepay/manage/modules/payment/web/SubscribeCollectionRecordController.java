/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

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

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.payment.entity.SubscribeCollectionRecord;
import com.heepay.manage.modules.payment.service.SubscribeCollectionRecordService;


/**
 *
 * 描    述：订阅支付申请Controller
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
@RequestMapping(value = "${adminPath}/payment/subscribeCollectionRecord")
public class SubscribeCollectionRecordController extends BaseController {

	@Autowired
	private SubscribeCollectionRecordService subscribeCollectionRecordService;
	
	@ModelAttribute
	public SubscribeCollectionRecord get(@RequestParam(required=false) String id) {
		SubscribeCollectionRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = subscribeCollectionRecordService.get(id);
		}
		if (entity == null){
			entity = new SubscribeCollectionRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:subscribeCollectionRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(SubscribeCollectionRecord subscribeCollectionRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SubscribeCollectionRecord> page = subscribeCollectionRecordService.findPage(new Page<SubscribeCollectionRecord>(request, response), subscribeCollectionRecord); 
		model.addAttribute("page", page);
		return "modules/payment/subscribeCollectionRecordList";
	}

	@RequiresPermissions("payment:subscribeCollectionRecord:view")
	@RequestMapping(value = "form")
	public String form(SubscribeCollectionRecord subscribeCollectionRecord, Model model) {
		model.addAttribute("subscribeCollectionRecord", subscribeCollectionRecord);
		return "modules/payment/subscribeCollectionRecordForm";
	}

	@RequiresPermissions("payment:subscribeCollectionRecord:edit")
	@RequestMapping(value = "save")
	public String save(SubscribeCollectionRecord subscribeCollectionRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, subscribeCollectionRecord)){
			return form(subscribeCollectionRecord, model);
		}
		subscribeCollectionRecordService.save(subscribeCollectionRecord);
		addMessage(redirectAttributes, "保存订阅支付申请成功");
		return "redirect:"+Global.getAdminPath()+"/payment/subscribeCollectionRecord/?repage";
	}
	
	@RequiresPermissions("payment:subscribeCollectionRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(SubscribeCollectionRecord subscribeCollectionRecord, RedirectAttributes redirectAttributes) {
		subscribeCollectionRecordService.delete(subscribeCollectionRecord);
		addMessage(redirectAttributes, "删除订阅支付申请成功");
		return "redirect:"+Global.getAdminPath()+"/payment/subscribeCollectionRecord/?repage";
	}

}