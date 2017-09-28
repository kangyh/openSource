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
import com.heepay.manage.modules.share.entity.ShareAccountRecordDetail;
import com.heepay.manage.modules.share.service.ShareAccountRecordDetailService;


/**
 *
 * 描    述：分润明细Controller
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
@RequestMapping(value = "${adminPath}/share/shareAccountRecordDetail")
public class ShareAccountRecordDetailController extends BaseController {

	@Autowired
	private ShareAccountRecordDetailService shareAccountRecordDetailService;
	
	@ModelAttribute
	public ShareAccountRecordDetail get(@RequestParam(required=false) String id) {
		ShareAccountRecordDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shareAccountRecordDetailService.get(id);
		}
		if (entity == null){
			entity = new ShareAccountRecordDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("share:shareAccountRecordDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShareAccountRecordDetail shareAccountRecordDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShareAccountRecordDetail> page = shareAccountRecordDetailService.findPage(new Page<ShareAccountRecordDetail>(request, response), shareAccountRecordDetail); 
		model.addAttribute("page", page);
		return "modules/payment/shareAccountRecordDetailList2";
	}


}