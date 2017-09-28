/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.contract.web;

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
import com.heepay.manage.modules.contract.entity.SealContractRecord;
import com.heepay.manage.modules.contract.service.SealContractRecordService;

import java.util.Date;


/**
 *
 * 描    述：已签约合同Controller
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
@RequestMapping(value = "${adminPath}/contract/sealContractRecord")
public class SealContractRecordController extends BaseController {

	@Autowired
	private SealContractRecordService sealContractRecordService;

	@ModelAttribute
	public SealContractRecord get(@RequestParam(required=false) String id) {
		SealContractRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sealContractRecordService.get(id);
		}
		if (entity == null){
			entity = new SealContractRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("contract:sealContractRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(SealContractRecord sealContractRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SealContractRecord> page = sealContractRecordService.findPage(new Page<SealContractRecord>(request, response), sealContractRecord); 
		model.addAttribute("page", page);
		return "modules/contract/sealContractRecordList";
	}

	@RequiresPermissions("contract:sealContractRecord:view")
	@RequestMapping(value = "form")
	public String form(SealContractRecord sealContractRecord, Model model) {
		model.addAttribute("sealContractRecord", sealContractRecord);
		return "modules/contract/sealContractRecordForm";
	}

	@RequiresPermissions("contract:sealContractRecord:view")
	@RequestMapping(value = "look")
	public String look(SealContractRecord sealContractRecord, Model model) {
		model.addAttribute("sealContractRecord", sealContractRecord);
		return "modules/contract/sealContractRecordDetail";
	}

	@RequiresPermissions("contract:sealContractRecord:edit")
	@RequestMapping(value = "save")
	public String save(SealContractRecord sealContractRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sealContractRecord)){
			return form(sealContractRecord, model);
		}
		addMessage(redirectAttributes, "保存已签约合同成功");
		return "redirect:"+Global.getAdminPath()+"/contract/sealContractRecord/?repage";
	}
	
	@RequiresPermissions("contract:sealContractRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(SealContractRecord sealContractRecord, RedirectAttributes redirectAttributes) {
		sealContractRecordService.delete(sealContractRecord);
		addMessage(redirectAttributes, "删除已签约合同成功");
		return "redirect:"+Global.getAdminPath()+"/contract/sealContractRecord/?repage";
	}

}