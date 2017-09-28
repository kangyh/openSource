/**
 *  
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
import com.heepay.manage.modules.payment.entity.BatchCollectionRecord;
import com.heepay.manage.modules.payment.service.BatchCollectionRecordService;

/**
 * 委托收款管理Controller
 * @author ld
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/payment/batchCollectionRecord")
public class BatchCollectionRecordController extends BaseController {

	@Autowired
	private BatchCollectionRecordService batchCollectionRecordService;
	
	@ModelAttribute
	public BatchCollectionRecord get(@RequestParam(required=false) String id) {
		BatchCollectionRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = batchCollectionRecordService.get(id);
		}
		if (entity == null){
			entity = new BatchCollectionRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:batchCollectionRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(BatchCollectionRecord batchCollectionRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BatchCollectionRecord> page = batchCollectionRecordService.findPage(new Page<BatchCollectionRecord>(request, response), batchCollectionRecord); 
		model.addAttribute("page", page);
		return "modules/payment/batchCollectionRecordList";
	}

	@RequiresPermissions("payment:batchCollectionRecord:view")
	@RequestMapping(value = "form")
	public String form(BatchCollectionRecord batchCollectionRecord, Model model) {
		model.addAttribute("batchCollectionRecord", batchCollectionRecord);
		return "modules/payment/batchCollectionRecordForm";
	}

	@RequiresPermissions("payment:batchCollectionRecord:edit")
	@RequestMapping(value = "save")
	public String save(BatchCollectionRecord batchCollectionRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, batchCollectionRecord)){
			return form(batchCollectionRecord, model);
		}
		batchCollectionRecordService.save(batchCollectionRecord);
		addMessage(redirectAttributes, "保存委托收款管理成功");
		return "redirect:"+Global.getAdminPath()+"/payment/batchCollectionRecord/?repage";
	}
	
	@RequiresPermissions("payment:batchCollectionRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(BatchCollectionRecord batchCollectionRecord, RedirectAttributes redirectAttributes) {
		batchCollectionRecordService.delete(batchCollectionRecord);
		addMessage(redirectAttributes, "删除委托收款管理成功");
		return "redirect:"+Global.getAdminPath()+"/payment/batchCollectionRecord/?repage";
	}
	
	@RequestMapping(value = "auditPass")
	public String auditPass(BatchCollectionRecord batchCollectionRecord, RedirectAttributes redirectAttributes) {
		batchCollectionRecordService.auditPass(batchCollectionRecord);
		addMessage(redirectAttributes, "审核通过");
		return "redirect:"+Global.getAdminPath()+"/payment/batchCollectionRecord/?repage";
	}
	
	@RequestMapping(value = "auditReject")
	public String auditReject(BatchCollectionRecord batchCollectionRecord, RedirectAttributes redirectAttributes) {
		batchCollectionRecordService.auditReject(batchCollectionRecord);
		addMessage(redirectAttributes, "审核驳回");
		return "redirect:"+Global.getAdminPath()+"/payment/batchCollectionRecord/?repage";
	}

}