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
import com.heepay.enums.BankcardAuthType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.BankcardAuth;
import com.heepay.manage.modules.payment.service.BankcardAuthService;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * 描    述：协议代扣审批Controller
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
@RequestMapping(value = "${adminPath}/payment/bankcardAuth")
public class BankcardAuthController extends BaseController {

	@Autowired
	private BankcardAuthService bankcardAuthService;
	
	@ModelAttribute
	public BankcardAuth get(@RequestParam(required=false) String id) {
		BankcardAuth entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankcardAuthService.get(id);
		}
		if (entity == null){
			entity = new BankcardAuth();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:bankcardAuth:view")
	@RequestMapping(value = {"list", ""})
	public String list(BankcardAuth bankcardAuth, HttpServletRequest request, HttpServletResponse response, Model model) {
//		bankcardAuth.setType(BankcardAuthType.ENTRUSTED_COLLECTION.getValue());
        List<String> typeList = new ArrayList<String>();
        typeList.add(BankcardAuthType.ENTRUSTED_COLLECTION.getValue());
        typeList.add(BankcardAuthType.SUBSCRIBE_COLLECTION.getValue());
        bankcardAuth.setTypeList(typeList);
		Page<BankcardAuth> page = bankcardAuthService.findPage(new Page<>(request, response), bankcardAuth);
		model.addAttribute("page", page);
		return "modules/payment/bankcardAuthList";
	}

	@RequiresPermissions("payment:bankcardAuth:view")
	@RequestMapping(value = "form")
	public String form(BankcardAuth bankcardAuth, Model model) {
		model.addAttribute("bankcardAuth", bankcardAuth);
		return "modules/payment/bankcardAuthForm";
	}

	@RequiresPermissions("payment:bankcardAuth:view")
    @RequestMapping(value = "toAuth")
    public String toAuth(String authId, Model model) {
        BankcardAuth bankcardAuth = get(authId);
        model.addAttribute("bankcardAuth", bankcardAuth);
        String path = bankcardAuth.getProtocolPath();
        model.addAttribute("imgPath", RandomUtil.getFastDfs(path));
        return "modules/payment/bankcardAuthProtocol";
    }

    @RequiresPermissions("payment:bankcardAuth:edit")
    @RequestMapping(value = "auth")
    public String auth(String authId, String ext1, String authRes, RedirectAttributes redirectAttributes) {
        //TODO 后续需要记录审核人员
//        UserUtils.getUser();
        logger.info("委托代收协议审核：{} # {} # {}", authId, ext1, authRes);
        int res = bankcardAuthService.entrustedCollectionAuth(authId, ext1, authRes);
        if(1 == res) {
            addMessage(redirectAttributes, "审核成功");
        } else {
            addMessage(redirectAttributes, "审核失败");
        }
        return "redirect:" + Global.getAdminPath() + "/payment/bankcardAuth/?repage";
    }

    @RequiresPermissions("payment:bankcardAuth:view")
    @RequestMapping(value = "toLimitAmount")
    public String toLimitAmount(String authId, Model model) {
        BankcardAuth bankcardAuth = get(authId);
        model.addAttribute("bankcardAuth", bankcardAuth);
        return "modules/payment/bankcardAuthLimitAmount";
    }

    @RequiresPermissions("payment:bankcardAuth:edit")
    @RequestMapping(value = "limitAmount")
    public String limitAmount(String authId, String protocolMaxAmount, RedirectAttributes redirectAttributes) {
        //TODO 是否需要记录风控人员变更协议限额的记录

        logger.info("委托代收协议限额变更：{} # {}", authId, protocolMaxAmount);
        int res = bankcardAuthService.setLimitAmount(authId, protocolMaxAmount);
        if(1 == res) {
            addMessage(redirectAttributes, "操作成功");
        } else {
            addMessage(redirectAttributes, "操作失败");
        }
        return "redirect:" + Global.getAdminPath() + "/payment/bankcardAuth/?repage";
    }

    @RequiresPermissions("payment:bankcardAuth:edit")
    @RequestMapping(value = "setEnable")
    public String setEnable(String authId, String enable, RedirectAttributes redirectAttributes) {

        logger.info("委托代收协议设置启用禁用：{} # {}", authId, enable);
        int res = bankcardAuthService.setEnable(authId, enable);
        if(1 == res) {
            addMessage(redirectAttributes, "操作成功");
        } else {
            addMessage(redirectAttributes, "操作失败");
        }
        return "redirect:" + Global.getAdminPath() + "/payment/bankcardAuth/?repage";
    }

//	@RequiresPermissions("payment:bankcardAuth:edit")
//	@RequestMapping(value = "save")
//	public String save(BankcardAuth bankcardAuth, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, bankcardAuth)){
//			return form(bankcardAuth, model);
//		}
//		bankcardAuthService.save(bankcardAuth);
//		addMessage(redirectAttributes, "保存协议代扣审批成功");
//		return "redirect:"+Global.getAdminPath()+"/payment/bankcardAuth/?repage";
//	}
//
//	@RequiresPermissions("payment:bankcardAuth:edit")
//	@RequestMapping(value = "delete")
//	public String delete(BankcardAuth bankcardAuth, RedirectAttributes redirectAttributes) {
//		bankcardAuthService.delete(bankcardAuth);
//		addMessage(redirectAttributes, "删除协议代扣审批成功");
//		return "redirect:"+Global.getAdminPath()+"/payment/bankcardAuth/?repage";
//	}

}