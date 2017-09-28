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
import com.heepay.manage.modules.payment.entity.SubscribeCollectionRecordDetail;
import com.heepay.manage.modules.payment.service.SubscribeCollectionRecordDetailService;

import java.util.Date;


/**
 *
 * 描    述：订阅支付明细Controller
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
@RequestMapping(value = "${adminPath}/payment/subscribeCollectionRecordDetail")
public class SubscribeCollectionRecordDetailController extends BaseController {

	@Autowired
	private SubscribeCollectionRecordDetailService subscribeCollectionRecordDetailService;
	
	@ModelAttribute
	public SubscribeCollectionRecordDetail get(@RequestParam(required=false) String id) {
		SubscribeCollectionRecordDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = subscribeCollectionRecordDetailService.get(id);
		}
		if (entity == null){
			entity = new SubscribeCollectionRecordDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:subscribeCollectionRecordDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(SubscribeCollectionRecordDetail subscribeCollectionRecordDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(subscribeCollectionRecordDetail.getBeginCreateTime() == null ||
				subscribeCollectionRecordDetail.getEndCreateTime()==null){
			subscribeCollectionRecordDetail.setBeginCreateTime(new Date());
			subscribeCollectionRecordDetail.setEndCreateTime(new Date());
		}
		Page iPage = new Page<SubscribeCollectionRecordDetail>(request, response);
		iPage.setOrderBy("create_time DESC");
		Page<SubscribeCollectionRecordDetail> page = subscribeCollectionRecordDetailService.findPage(iPage, subscribeCollectionRecordDetail);
		model.addAttribute("page", page);
		return "modules/payment/subscribeCollectionRecordDetailList";
	}

	@RequiresPermissions("payment:subscribeCollectionRecordDetail:view")
	@RequestMapping(value = "form")
	public String form(SubscribeCollectionRecordDetail subscribeCollectionRecordDetail, Model model) {
		model.addAttribute("subscribeCollectionRecordDetail", subscribeCollectionRecordDetail);
		return "modules/payment/subscribeCollectionRecordDetail";
	}

	@RequiresPermissions("payment:subscribeCollectionRecordDetail:edit")
	@RequestMapping(value = "save")
	public String save(SubscribeCollectionRecordDetail subscribeCollectionRecordDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, subscribeCollectionRecordDetail)){
			return form(subscribeCollectionRecordDetail, model);
		}
		subscribeCollectionRecordDetailService.save(subscribeCollectionRecordDetail);
		addMessage(redirectAttributes, "保存订阅支付明细成功");
		return "redirect:"+Global.getAdminPath()+"/payment/subscribeCollectionRecordDetail/?repage";
	}
	
	@RequiresPermissions("payment:subscribeCollectionRecordDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(SubscribeCollectionRecordDetail subscribeCollectionRecordDetail, RedirectAttributes redirectAttributes) {
		subscribeCollectionRecordDetailService.delete(subscribeCollectionRecordDetail);
		addMessage(redirectAttributes, "删除订阅支付明细成功");
		return "redirect:"+Global.getAdminPath()+"/payment/subscribeCollectionRecordDetail/?repage";
	}

}