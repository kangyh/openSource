/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.web;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.prom.common.config.Global;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.web.BaseController;
import com.heepay.prom.modules.prom.entity.PromAccountInfo;
import com.heepay.prom.modules.prom.service.PromAccountInfoService;
import com.heepay.prom.modules.prom.service.PromMerchantManageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * 描    述：账户管理Controller
 *
 * 创 建 者： @author wangdong
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
@RequestMapping(value = "${adminPath}/prom/promAccountInfo")
public class PromAccountInfoController extends BaseController {

	@Autowired
	private PromAccountInfoService promAccountInfoService;

	@Autowired
    private PromMerchantManageService promMerchantManageService;
	
	@ModelAttribute
	public PromAccountInfo get(@RequestParam(required=false) String id) {
		PromAccountInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = promAccountInfoService.get(id);
		}
		if (entity == null){
			entity = new PromAccountInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("prom:promAccountInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(PromAccountInfo promAccountInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PromAccountInfo> page = promAccountInfoService.findPage(new Page<PromAccountInfo>(request, response), promAccountInfo); 
		model.addAttribute("page", page);
		return "modules/prom/promAccountInfoList";
	}

	@RequiresPermissions("prom:promAccountInfo:view")
	@RequestMapping(value = "form")
	public String form(PromAccountInfo promAccountInfo, Model model, HttpServletRequest request, HttpServletResponse response) {
	    Long merchantId = promAccountInfo.getMerchantId();
        if (null != promAccountInfo && null != promAccountInfo.getMerchantId()) {
            promAccountInfo = get(promAccountInfo.getMerchantId().toString());
            if(null != promAccountInfo && null != promAccountInfo.getBankNo()) {
                String bankNo = Aes.decryptStr(promAccountInfo.getBankNo(), Constants.QuickPay.SYSTEM_KEY);
                promAccountInfo.setBankNo(StringUtil.getEncryptedCardNo(bankNo));
            }else{
                promAccountInfo.setMerchantId(merchantId);
            }
        }
        model.addAttribute("promAccountInfo", promAccountInfo);
        return "modules/prom/promAccountInfoForm";
	}

	@RequiresPermissions("prom:promAccountInfo:edit")
	@RequestMapping(value = "save")
	public String save(PromAccountInfo promAccountInfo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        Integer count = promAccountInfoService.findExit(promAccountInfo);
        if(count > 0){
            model.addAttribute("message","绑定银行卡:"+promAccountInfo.getBankNo()+" 重复!");
            return form(promAccountInfo,model,request,response);
        }
        promAccountInfo.setBankNo(Aes.encryptStr(promAccountInfo.getBankNo(), Constants.QuickPay.SYSTEM_KEY));
        promAccountInfoService.save(promAccountInfo);
        if(null != promAccountInfo && promAccountInfo.getAccountId() != null){
            addMessage(redirectAttributes, "修改绑卡成功");
        }else {
            addMessage(redirectAttributes, "保存绑卡成功");
        }
		return "redirect:"+Global.getAdminPath()+"/prom/promMerchantManage/?repage";
	}

}