/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.web.rate;

import com.heepay.agent.common.enums.FeeType;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.agent.entity.rate.AgentRate;
import com.heepay.manage.modules.agent.service.rate.AgentRateOtherService;
import com.heepay.manage.modules.agent.service.rate.AgentRateService;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import org.apache.commons.lang3.EnumUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * 描    述：设置代理商费率Controller
 *
 * 创 建 者： @author xch
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
@RequestMapping(value = "${adminPath}/agent/rate/agentRate")
public class AgentRateController extends BaseController {

    @Autowired
    private AgentRateService agentRateService;

    @Autowired
    private AgentRateOtherService agentRateOtherService;

    @Autowired
    private ProductCService productCService;
//	@Autowired
//	private AgentInfoService agentInfoService;

    @ModelAttribute
    public AgentRateExt get(@RequestParam(required = false) String id,@RequestParam(required = false)String agentName,@RequestParam(required = false)String agentCode) {
        AgentRate entity = null;
        AgentRateExt entity1 = null;
        if (StringUtils.isNotBlank(id)) {
            entity = agentRateService.get(id);
            entity1=new AgentRateExt(entity,agentName,agentCode);
        }
        if (entity == null) {
            entity1 = new AgentRateExt();
        }
        return entity1;
    }

    @RequiresPermissions("agent:rate:agentRate:view")
    @RequestMapping(value = {"list", ""})
    public String list(AgentRateExt agentRateExt, HttpServletRequest request, HttpServletResponse response, Model model) {
        AgentRate agentRate1 = agentRateExt;
        Page<AgentRate> page = agentRateService.findPage(new Page<AgentRate>(request, response), agentRate1);
        model.addAttribute("page", page);
        //新增产品名称
        Product product = new Product();
        List<Product> list = productCService.findList(product);
        model.addAttribute("productList",list);
//        List<FeeType> feeTypeList = EnumUtils.getEnumList(FeeType.class);
//        model.addAttribute("feeTypeList", feeTypeList);
//		//根据代理商编号查询代理商名称
//		AgentInfo agentInfo=agentInfoService.get(agentRate.getAgentId());
//		model.addAttribute("agentInfo",agentInfo);

        return "modules/agent/rate/agentRateList";
    }

    @RequiresPermissions("agent:rate:agentRate:view")
    @RequestMapping(value = "form")
    public String form(AgentRateExt agentRateExt, Model model) {
        model.addAttribute("agentRateExt", agentRateExt);

//        List<FeeType> feeTypeList = EnumUtils.getEnumList(FeeType.class);
//        model.addAttribute("feeTypeList", feeTypeList);
        FeeType feeType= EnumUtils.getEnum(FeeType.class,"PERCEN");
        model.addAttribute("feeType", feeType);

        //新增产品名称
        Product product = new Product();
        List<Product> list = productCService.findList(product);
        model.addAttribute("productList",list);

        return "modules/agent/rate/agentRateForm";
    }

    @RequiresPermissions("agent:rate:agentRate:edit")
    @RequestMapping(value = "save")
    public String save(AgentRateExt agentRate, Model model, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        agentRate.setProductName(HtmlUtils.htmlUnescape(agentRate.getProductName()));
        if (!beanValidator(model, agentRate)) {
            return form(agentRate, model);
        }

        //校验交易总金额的最大值和最小值
        if(new BigDecimal(agentRate.getTransAmountBegin()).compareTo(new BigDecimal(agentRate.getTransAmountEnd())) > 0){
            addMessage(model, "交易总额最小金额不能大于最大金额！");
            return form(agentRate, model);
        }

        //校验交易总金额的最大值和最小值
        if(new BigDecimal(agentRate.getDefaultFeeMin()).compareTo(new BigDecimal(agentRate.getDefaultFeeMax())) > 0){
            addMessage(model, "借记卡费率最小值不能大于最大值！");
            return form(agentRate, model);
        }

//        //校验重复添加同一的产品
//        AgentRate agentRate1 = agentRateOtherService.getAgentRate(agentRate);
//
//        if(agentRate1 != null){
//            addMessage(model, "不能重复添加同一的产品！");
//            return form(agentRate, model);
//        }

        agentRateService.save(agentRate);
        addMessage(redirectAttributes, "保存费率配置成功");
        //return "redirect:" + Global.getAdminPath() + "/agent/rate/agentRate/?repage";
        return "redirect:"+ Global.getAdminPath() + "/agent/rate/agentRate/list?agentId=" + agentRate.getAgentId() + "&agentCode=" + agentRate.getAgentCode() + "&agentName=" + java.net.URLEncoder.encode(agentRate.getAgentName(),"UTF-8");
    }

    @RequiresPermissions("agent:rate:agentRate:edit")
    @RequestMapping(value = "delete")
    public String delete(AgentRateExt agentRate, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        agentRateService.delete(agentRate);
        addMessage(redirectAttributes, "删除费率配置成功");
//        return "redirect:" + Global.getAdminPath() + "/agent/rate/agentRate/?repage";
        return "redirect:"+ Global.getAdminPath() + "/agent/rate/agentRate/list?agentId=" + agentRate.getAgentId() + "&agentCode=" + agentRate.getAgentCode()  + "&agentName=" + java.net.URLEncoder.encode(agentRate.getAgentName(),"UTF-8");
    }

}