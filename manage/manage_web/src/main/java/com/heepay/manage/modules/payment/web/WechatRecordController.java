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

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.WechatRecord;
import com.heepay.manage.modules.payment.service.WechatRecordService;


/**
 *
 * 描    述：微信支付订单Controller
 *
 * 创 建 者： @author 张晨
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
@RequestMapping(value = "${adminPath}/wechat/wechatRecord")
public class WechatRecordController extends BaseController {

	@Autowired
	private WechatRecordService wechatRecordService;
	
	@ModelAttribute
	public WechatRecord get(@RequestParam(required=false) String id) {
		WechatRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wechatRecordService.get(id);
		}
		if (entity == null){
			entity = new WechatRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("wechat:wechatRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(WechatRecord wechatRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WechatRecord> page = wechatRecordService.findPage(new Page<WechatRecord>(request, response), wechatRecord); 
		model.addAttribute("page", page);
		return "modules/payment/wechatRecordList";
	}

	@RequiresPermissions("wechat:wechatRecord:view")
	@RequestMapping(value = "form")
	public String form(WechatRecord wechatRecord, Model model) {
		model.addAttribute("wechatRecord", wechatRecord);
		return "modules/payment/wechatRecordForm";
	}

	@RequiresPermissions("wechat:wechatRecord:edit")
	@RequestMapping(value = "save")
	public String save(WechatRecord wechatRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wechatRecord)){
			return form(wechatRecord, model);
		}
		wechatRecordService.save(wechatRecord);
		addMessage(redirectAttributes, "保存微信支付订单成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wechatRecord/?repage";
	}
	
	@RequiresPermissions("wechat:wechatRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(WechatRecord wechatRecord, RedirectAttributes redirectAttributes) {
		wechatRecordService.delete(wechatRecord);
		addMessage(redirectAttributes, "删除微信支付订单成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wechatRecord/?repage";
	}

}