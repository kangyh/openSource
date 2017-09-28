package com.heepay.manage.modules.adjust.web;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.trial.entity.TrialBalanceTrans;
import com.heepay.manage.modules.trial.service.TrialBalanceTransService;


@Controller
@RequestMapping(value = "${adminPath}/adjust/adjustTradeAccountQuery")
public class AdjustTradeAccountQueryController extends BaseController {
  
  @Autowired
  private TrialBalanceTransService trialBalanceTransService;
  
  @ModelAttribute
  public TrialBalanceTrans get(@RequestParam(required=false) String id) {
    TrialBalanceTrans entity = null;
    if (StringUtils.isNotBlank(id)){
      entity = trialBalanceTransService.get(id);
    }
    if (entity == null){
      entity = new TrialBalanceTrans();
    }
    return entity;
  }
  
  @RequiresPermissions("adjust:adjustTradeAccountQuery:view")
  @RequestMapping(value = {"list", ""})
  public String list(TrialBalanceTrans trialBalanceTrans, HttpServletRequest request, HttpServletResponse response, Model model) {
    Page<TrialBalanceTrans> page = trialBalanceTransService.findPage(new Page<TrialBalanceTrans>(request, response), trialBalanceTrans); 
    List<TrialBalanceTrans> list = page.getList();
    if(list != null && !list.isEmpty()){
      for(TrialBalanceTrans tBalanceTrans : list){
        BigDecimal diffAmount = new BigDecimal(tBalanceTrans.getCreditCurrentAmount())
            .subtract(new BigDecimal(tBalanceTrans.getDebitCurrentAmount()));
        tBalanceTrans.setDiffAmount(String.valueOf(Math.abs(diffAmount.doubleValue())));
      }
    }
    model.addAttribute("page", page);
    page.setOrderBy("create_time desc");
    return "modules/adjust/adjustAccountQueryList";
  }
  
  
  @RequestMapping(value="isAdminUser")
  public @ResponseBody boolean isAdminUser(){
    return UserUtils.getUser().isAdmin();
  }
  
}
