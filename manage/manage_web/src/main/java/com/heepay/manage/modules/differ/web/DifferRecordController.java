/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.differ.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.enums.InterfaceStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.differ.entity.DifferRecord;
import com.heepay.manage.modules.differ.service.DifferRecordService;
import com.heepay.manage.modules.payment.rpc.client.DifferRecordClient;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.rpc.payment.model.AsyncMsgModel;


/**
 *
 * 描    述：差异处理平台Controller
 *
 * 创 建 者： @author ld
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
@RequestMapping(value = "${adminPath}/differ/differRecord")
public class DifferRecordController extends BaseController {

	@Autowired
	private DifferRecordService differRecordService;

    @Autowired
    private DifferRecordClient differRecordClient;

    private static Logger log = LogManager.getLogger();
	
	@ModelAttribute
	public DifferRecord get(@RequestParam(required=false) String id) {
		DifferRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = differRecordService.get(id);
		}
		if (entity == null){
			entity = new DifferRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("differ:differRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(DifferRecord differRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DifferRecord> queryPage = new Page<>(request, response);
		queryPage.setOrderBy("differ_status desc");
		queryPage.setOrderBy("create_time desc");
		Page<DifferRecord> page = differRecordService.findPage(new Page<>(request, response), differRecord);
		model.addAttribute("page", page);
		return "modules/differ/differRecordList";
	}

	@RequiresPermissions("differ:differRecord:view")
	@RequestMapping(value = "form")
	public String form(String differId, Model model) {
		DifferRecord differRecord = differRecordService.get(differId);
		model.addAttribute("differRecord", differRecord);
		return "modules/differ/differRecordForm";
	}

	@RequiresPermissions("differ:differRecord:edit")
	@RequestMapping(value = "supple")
	public String supple(String paymentId, RedirectAttributes redirectAttributes) {

	    String operator = UserUtils.getUser().getName();
        log.info("paymentId={}，operator={}，开始进行差异补单处理", paymentId, operator);
        AsyncMsgModel res = differRecordClient.suppleTransForDiffer(paymentId, operator);
        if(InterfaceStatus.SUCCESS.getValue() != res.getStatus()) {
            addMessage(redirectAttributes, "补单处理失败："+res.getMsg());
        } else {
            addMessage(redirectAttributes, "补单处理成功");
        }
		return "redirect:"+ Global.getAdminPath()+"/differ/differRecord/?repage";
	}

	@RequiresPermissions("differ:differRecord:edit")
	@RequestMapping(value = "batchSupple")
	public String batchSupple(String paymentIds, HttpServletResponse response) {

		String operator = UserUtils.getUser().getName();
		log.info("paymentIds={}，operator={}，开始进行批量差异补单", paymentIds, operator);
		String[] payments = paymentIds.split(",");
		String resStr = "本次批量处理共"+payments.length+"笔，";
		int success = 0;
		int fails = 0;
		String failReason = "";
		for(String paymentId : payments) {
			AsyncMsgModel res = differRecordClient.suppleTransForDiffer(paymentId, operator);
			if(InterfaceStatus.SUCCESS.getValue() != res.getStatus()) {
				fails++;
				failReason = failReason + paymentId + " : " + res.getMsg() + "\n";
			} else {
				success++;
			}
		}
		resStr = resStr + "成功" + success +"笔，失败" + fails + "笔。\n" + failReason;
		WebUtil.outputJson(resStr, response);

//		AsyncMsgModel res = differRecordClient.suppleTransForDiffer(paymentIds, operator);
//		if(InterfaceStatus.SUCCESS.getValue() != res.getStatus()) {
//			addMessage(redirectAttributes, "补单处理失败："+res.getMsg());
//		} else {
//			addMessage(redirectAttributes, "补单处理成功");
//		}
		return "redirect:"+ Global.getAdminPath()+"/differ/differRecord/?repage";
	}

//	@RequiresPermissions("differ:differRecord:edit")
//	@RequestMapping(value = "save")
//	public String save(DifferRecord differRecord, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, differRecord)){
//			return form(differRecord, model);
//		}
//		differRecordService.save(differRecord);
//		addMessage(redirectAttributes, "保存差异处理平台成功");
//		return "redirect:"+Global.getAdminPath()+"/differ/differRecord/?repage";
//	}
//
//	@RequiresPermissions("differ:differRecord:edit")
//	@RequestMapping(value = "delete")
//	public String delete(DifferRecord differRecord, RedirectAttributes redirectAttributes) {
//		differRecordService.delete(differRecord);
//		addMessage(redirectAttributes, "删除差异处理平台成功");
//		return "redirect:"+Global.getAdminPath()+"/differ/differRecord/?repage";
//	}

}