/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import java.util.List;

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
import com.heepay.manage.modules.share.entity.ShareAccountRecord;
import com.heepay.manage.modules.share.entity.ShareAccountRecordDetail;
import com.heepay.manage.modules.share.service.ShareAccountRecordDetailService;
import com.heepay.manage.modules.share.service.ShareAccountRecordService;

/**
 *
 * 描    述：分润查看Controller
 *
 * 创 建 者： @author zc
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
@RequestMapping(value = "${adminPath}/share/shareAccountRecord")
public class ShareAccountRecordController extends BaseController {

	@Autowired
	private ShareAccountRecordService shareAccountRecordService;
	
	@Autowired
  private ShareAccountRecordDetailService shareAccountRecordDetailService;
	@ModelAttribute
	public ShareAccountRecord get(@RequestParam(required=false) String id) {
		ShareAccountRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shareAccountRecordService.get(id);
		}
		if (entity == null){
			entity = new ShareAccountRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("share:shareAccountRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShareAccountRecord shareAccountRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShareAccountRecord> page = shareAccountRecordService.findPage(new Page<ShareAccountRecord>(request, response), shareAccountRecord); 
		model.addAttribute("page", page);
		return "modules/payment/shareAccountRecordList";
	}

	@RequiresPermissions("share:shareAccountRecord:view")
  @RequestMapping(value = "detaillist")
  public String detaillist(ShareAccountRecord shareAccountRecord, HttpServletRequest request, HttpServletResponse response,Model model) {
	 
	  List<ShareAccountRecordDetail> list = shareAccountRecordDetailService.getShareAccountRecordDetailListByShareId(shareAccountRecord.getShareId());
    model.addAttribute("list", list);
    return "modules/payment/shareAccountRecordDetailList";
  }
	
	
	@RequiresPermissions("share:shareAccountRecord:view")
	@RequestMapping(value = "form")
	public String form(ShareAccountRecord shareAccountRecord, Model model) {
		model.addAttribute("shareAccountRecord", shareAccountRecord);
		return "modules/payment/shareAccountRecordForm";
	}

	@RequiresPermissions("share:shareAccountRecord:edit")
	@RequestMapping(value = "save")
	public String save(ShareAccountRecord shareAccountRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shareAccountRecord)){
			return form(shareAccountRecord, model);
		}
		shareAccountRecordService.save(shareAccountRecord);
		addMessage(redirectAttributes, "保存分润查看成功");
		return "redirect:"+Global.getAdminPath()+"/payment/shareAccountRecord/?repage";
	}
	
	@RequiresPermissions("share:shareAccountRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ShareAccountRecord shareAccountRecord, RedirectAttributes redirectAttributes) {
		shareAccountRecordService.delete(shareAccountRecord);
		addMessage(redirectAttributes, "删除分润查看成功");
		return "redirect:"+Global.getAdminPath()+"/payment/shareAccountRecord/?repage";
	}

}