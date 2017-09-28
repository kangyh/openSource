/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.prom.common.config.Global;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.utils.EnumBean;
import com.heepay.prom.common.web.BaseController;
import com.heepay.prom.modules.prom.entity.PromMerchantManage;
import com.heepay.prom.modules.prom.entity.PromProfitTemplet;
import com.heepay.prom.modules.prom.entity.PromTeacherManage;
import com.heepay.prom.modules.prom.enums.Status;
import com.heepay.prom.modules.prom.service.PromMerchantManageService;
import com.heepay.prom.modules.prom.service.PromProfitTempletService;
import com.heepay.prom.modules.prom.service.PromTeacherManageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 *
 * 描    述：上下级关系管理Controller
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
@RequestMapping(value = "${adminPath}/prom/promTeacherManage")
public class PromTeacherManageController extends BaseController {

	@Autowired
	private PromTeacherManageService promTeacherManageService;

	@Autowired
    private PromProfitTempletService promProfitTempletService;

	@Autowired
    private PromMerchantManageService promMerchantManageService;
	
	@ModelAttribute
	public PromTeacherManage get(@RequestParam(required=false) String id) {
		PromTeacherManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = promTeacherManageService.get(id);
		}
		if (entity == null){
			entity = new PromTeacherManage();
		}
		return entity;
	}
	
	@RequiresPermissions("prom:promTeacherManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(PromTeacherManage promTeacherManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PromTeacherManage> page = promTeacherManageService.findPage(new Page<PromTeacherManage>(request, response), promTeacherManage); 
		model.addAttribute("page", page);
		return "modules/prom/promTeacherManageList";
	}

	@RequiresPermissions("prom:promTeacherManage:view")
	@RequestMapping(value = "form")
	public String form(PromTeacherManage promTeacherManage, Model model) {
        if (null != promTeacherManage && null != promTeacherManage.getTeId()) {
            promTeacherManage = get(promTeacherManage.getTeId().toString());
            model.addAttribute("promTeacherManage", promTeacherManage);
        }
        List<EnumBean> status = Lists.newArrayList();
        for (Status stats : Status.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(stats.getValue());
            ct.setName(stats.getContent());
            status.add(ct);
        }
        model.addAttribute("status", status);
        List<PromProfitTemplet> profitList = promProfitTempletService.findAllList();
        model.addAttribute("profitList", profitList);
        List<PromMerchantManage> merList = promMerchantManageService.findAllList();
        model.addAttribute("merList", merList);
		return "modules/prom/promTeacherManageForm";
	}

	@RequiresPermissions("prom:promTeacherManage:edit")
	@RequestMapping(value = "save")
	public String save(PromTeacherManage promTeacherManage, Model model, RedirectAttributes redirectAttributes) {
        Integer count = promTeacherManageService.findExit(promTeacherManage);
        if(count > 0){
            model.addAttribute("message","保存上下级关系:"+promTeacherManage.getMerchantCode()+"-"+promTeacherManage.getSuperMerchantCode()+" 重复!");
            return form(promTeacherManage,model);
        }
		promTeacherManageService.save(promTeacherManage);
        if(null != promTeacherManage && promTeacherManage.getTeId() != null){
            addMessage(redirectAttributes, "修改上下级关系成功");
        }else {
            addMessage(redirectAttributes, "保存上下级关系成功");
        }
		return "redirect:"+Global.getAdminPath()+"/prom/promTeacherManage/?repage";
	}

    /**
     * @方法说明：
     * 前提： 关系启用
     * 1.不能同时存在多个上级
     *
     * @时间： 2017/9/15 12:50
     * @创建人：wangdong
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "checkCode")
    public String checkCode(PromTeacherManage promTeacherManage) throws Exception{
        //不能同时存在多个上级
        int superCount = promTeacherManageService.findCountByCode(promTeacherManage);
        if(superCount!=0) {
            return "M";
        }else{
            return "S";
        }
    }

	/**
	 * @方法说明：
     * 前提： 关系启用
     * 1.不能互为上下级关系
     * 2.不能自己为自己的上级
     *
	 * @时间： 2017/9/15 12:50
	 * @创建人：wangdong
	 */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "checkLevel")
    public String checkType(PromTeacherManage promTeacherManage) throws Exception{
        PromTeacherManage superProm = new PromTeacherManage();
        superProm.setMerchantCode(promTeacherManage.getSuperMerchantCode());
        superProm.setSuperMerchantCode(promTeacherManage.getMerchantCode());
        //不能互为上下级关系
        int count = promTeacherManageService.findSuperExit(superProm);
        if (count != 0) {
            return "D";//存在互为上下级关系
        } else {
            int level = 1;
            for (int i = 0; i < 8; i++) {
                if (StringUtils.isNotBlank(promTeacherManage.getSuperMerchantCode())) {
                    promTeacherManage.setMerchantCode(promTeacherManage.getSuperMerchantCode());
                    promTeacherManage = promTeacherManageService.findEntityByCode(promTeacherManage);
                    if (null != promTeacherManage && null != promTeacherManage.getSuperMerchantCode()) {
                        level++;
                        promTeacherManage.setMerchantCode(promTeacherManage.getSuperMerchantCode());
                    }
                }
            }
            if (level < 4) {
                return "S";//可以
            } else if (level > 4) {
                return "X";//不可以，商户存在互为上下级
            } else {
                return "F";//不可以，商户已满3级
            }
        }
    }
}