/**
 *  
 */
package com.heepay.manage.modules.merchant.web;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.PrintWriterUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.RemitStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.rpc.client.TransferQualificationClient;
import com.heepay.manage.modules.merchant.service.MerchantBankCardAuthenticationCService;
import com.heepay.manage.modules.merchant.vo.MerchantBankCardAuthentication;
import com.heepay.redis.JedisClusterUtil;

/**
 *
 * 描 述：商户打款认证Controller
 *
 * 创 建 者：ly 创建时间：2016-08-23 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者：ljh 审核时间：2016-09-01 审核描述：116行判断无意义方法体永远走false；95/103/123行相同代码可合并
 * 68行需对返回结果做健壮性（非空）处理；缺少每段代码和方法注释；
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/merchantBankCardAuthentication")
public class MerchantBankCardAuthenticationController extends BaseController {

    @Autowired
    private MerchantBankCardAuthenticationCService merchantBankCardAuthenticationCService;

    @Autowired
    private TransferQualificationClient transferQualificationClient;

    private static JsonMapperUtil jsonMapper = JsonMapperUtil.buildNonDefaultMapper();

    /**
     * @discription 根据id获取商户打款认证数据
     * @author ly
     * @created 2016年9月2日 下午3:56:22
     * @param id
     * @return
     */
    @ModelAttribute
    public MerchantBankCardAuthentication get(@RequestParam(required = false) String id) {
        MerchantBankCardAuthentication entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = merchantBankCardAuthenticationCService.get(id);
        }
        if (entity == null) {
            entity = new MerchantBankCardAuthentication();
        }
        return entity;
    }

    /**
     * @discription 获取商户打款认证列表
     * @author ly
     * @created 2016年9月2日 下午3:57:39
     * @param merchantBankCardAuthentication
     * @return
     */
    @RequiresPermissions("merchant:merchantBankCardAuthentication:view")
    @RequestMapping(value = { "list", "" })
    public String list(MerchantBankCardAuthentication merchantBankCardAuthentication,
                       HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        //使用cookie保存查询条件
        merchantBankCardAuthentication = (MerchantBankCardAuthentication) SaveConditions.result(merchantBankCardAuthentication, "merchantBankCardAuthentication", request, response);

        Page<MerchantBankCardAuthentication> page = new Page<>(request, response);
        page.setOrderBy("create_time desc");
        page = merchantBankCardAuthenticationCService.findPage(page, merchantBankCardAuthentication);
        page.setList(EnumView.merchantBankCardAuthenticationController(page.getList()));

        model.addAttribute("page", page);
        model.addAttribute("merchantBankCardAuthentication", merchantBankCardAuthentication);
        return "modules/merchant/merchantBankCardAuthenticationList";
    }

    /**
     * @discription 获取商户打款认证新增修改页面
     * @author ly
     * @created 2016年9月2日 下午4:06:06
     * @param merchantBankCardAuthentication
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantBankCardAuthentication:view")
    @RequestMapping(value = "form")
    public String form(MerchantBankCardAuthentication merchantBankCardAuthentication, Model model) {
        if (StringUtils.isNotBlank(merchantBankCardAuthentication.getPayStatus())) {
            merchantBankCardAuthentication
                    .setPayStatus(RemitStatus.labelOf(merchantBankCardAuthentication.getPayStatus()));
        }
        model.addAttribute("merchantBankCardAuthentication", merchantBankCardAuthentication);
        return "modules/merchant/merchantBankCardAuthenticationForm";
    }

    /**
     * @discription 获取商户打款认证详情
     * @author ly
     * @created 2016年9月2日 下午4:06:30
     * @param merchantBankCardAuthentication
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantBankCardAuthentication:view")
    @RequestMapping(value = "detail")
    public String detail(MerchantBankCardAuthentication merchantBankCardAuthentication, Model model) {
        model.addAttribute("merchantBankCardAuthentication", merchantBankCardAuthentication);
        return "modules/merchant/merchantBankCardAuthenticationDetail";
    }

    /**
     * @discription 保存修改商户打款认证方法
     * @author ly
     * @created 2016年9月2日 下午4:06:58
     * @param merchantBankCardAuthentication
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("merchant:merchantBankCardAuthentication:edit")
    @RequestMapping(value = "save")
    public String save(MerchantBankCardAuthentication merchantBankCardAuthentication, Model model,
            RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, merchantBankCardAuthentication)) {
            return form(merchantBankCardAuthentication, model);
        }
        merchantBankCardAuthenticationCService.save(merchantBankCardAuthentication, false);
        addMessage(redirectAttributes, "保存商户打款认证成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantBankCardAuthentication?cache=1&repage";
    }

    /**
     * @discription 获取随机打款金额的方法
     * @author ly
     * @created 2016年9月2日 下午4:07:42
     * @param merchantId 商户Id
     * @return 
     */
    @RequestMapping(value = "getMoney")
    @ResponseBody
    public String getMoney(String merchantId) {
        String money = RandomUtil.getMoney();
        JedisClusterUtil.getJedisCluster().setex("getMoney"+merchantId, 5*60, money);
        return money;
    }

    /**
     * @discription 打款的方法
     * @author ly
     * @created 2016年9月2日 下午4:08:00
     * @param merchantBankCardAuthentication
     * @param response
     */
    @RequestMapping(value = "payMoney")
    public void payMoney(MerchantBankCardAuthentication merchantBankCardAuthentication, HttpServletResponse response) {
        // 打款流程
        String answer;
        boolean flagMoney = compareMoney(merchantBankCardAuthentication.getPayAmount(),merchantBankCardAuthentication.getMerchantId());
        if(flagMoney){
            String msg = transferQualificationClient.qualification(merchantBankCardAuthentication);
            if (Boolean.TRUE.toString().equals(msg)) {
                // 打款成功 修改状态
                merchantBankCardAuthentication.setPayStatus(RemitStatus.SUCCESS.getValue());
                merchantBankCardAuthentication.setCause("");
                merchantBankCardAuthenticationCService.status(merchantBankCardAuthentication);
                answer = "商户打款成功";
            } else {
                // 打款失败 修改状态 修改银行卡状态
                merchantBankCardAuthentication.setPayStatus(RemitStatus.FAIL.getValue());
                merchantBankCardAuthentication.setCause(msg);
                merchantBankCardAuthenticationCService.status(merchantBankCardAuthentication);
                answer = "商户打款失败";
            }
        }else{
            answer = "error";
        }
        String jsonString = jsonMapper.toJson(answer);
        PrintWriterUtil.reder(response, jsonString);
    }
    
      
    /**     
    * @discription 比较打款金额
    * @author ly     
    * @created 2016年12月17日 下午4:33:51     
    * @param payMoney
    * @param merchantId
    * @return     
    */
    private boolean compareMoney(BigDecimal payMoney,String merchantId){
        String money = JedisClusterUtil.getJedisCluster().get("getMoney"+merchantId);
        if(payMoney.compareTo(new BigDecimal("1")) != 1 && StringUtils.isNotBlank(money)){
            return new BigDecimal(money).compareTo(payMoney) == 0;
        }
        return false;
    }

    @RequiresPermissions("merchant:merchantBankCard:get")
    @RequestMapping(value = "getBankNo")
    @ResponseBody
    private String getBankNo(MerchantBankCardAuthentication merchantBankCardAuthentication){
        return merchantBankCardAuthentication.getBankNo();
    }

}