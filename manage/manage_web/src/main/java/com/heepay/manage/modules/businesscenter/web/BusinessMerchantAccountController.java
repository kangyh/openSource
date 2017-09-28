/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.businesscenter.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.BankcardType;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.deposit.entity.DepositWithdrawRecord;
import com.heepay.manage.modules.deposit.service.DepositWithdrawRecordService;
import com.heepay.manage.modules.util.ExcelUtil2007;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 * 描    述：商务中心
 *
 * 创 建 者： @author 王亚洪
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
@RequestMapping(value = "${adminPath}/business/businessMerchantAccount")
public class BusinessMerchantAccountController extends BaseController {

	@Autowired
	private MerchantAccountService merchantAccountService;
	
	
	@ModelAttribute
    public MerchantAccount get(@RequestParam(required=false) String id) {
        MerchantAccount entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = merchantAccountService.get(id);
        }
        if (entity == null){
            entity = new MerchantAccount();
        }
        return entity;
    }
	
	
	@RequiresPermissions("business:merchantAccount:view")
	@RequestMapping(value="getMerchantAccountList")
    public String getMerchantAccountList(MerchantAccount merchantAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
	    if(merchantAccount.getSortOrder() == null){
            //默认按照创建时间降序
            merchantAccount.setSortOrder(SortOrderType.DESC.getValue());
        }
        String accountCodesHidden = merchantAccount.getAccountCodesHidden();
        List<String> accCodesList = Lists.newArrayList();
        if(StringUtil.notBlank(accountCodesHidden)){
          String[] accountCodes = accountCodesHidden.split(",");
          accCodesList = Arrays.asList(accountCodes);
        }
        merchantAccount.setAccCodes(accCodesList);

        List<Long> merchantIdList = RandomUtil.getMerchantIdList();
        merchantAccount.setMerchantIds(merchantIdList);
        Page<MerchantAccount> page = merchantAccountService.findPage(new Page<MerchantAccount>(request, response), merchantAccount);
        model.addAttribute("page", page);
        return "modules/businesscenter/merchantAccountList";
    }
	
	
	@RequiresPermissions("business:merchantAccount:view")
    @RequestMapping(value = "form")
    public String form(MerchantAccount merchantAccount, Model model) {
        model.addAttribute("merchantAccount", merchantAccount);
        return "modules/businesscenter/merchantAccountDetail";
    }
	

}