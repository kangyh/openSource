package com.heepay.manage.modules.agent.web.report;

import com.google.common.collect.Lists;
import com.heepay.agent.common.enums.AgentStatus;
import com.heepay.agent.common.enums.AgentType;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.agent.entity.agent.AgentInfo;
import com.heepay.manage.modules.agent.entity.profit.AgentProfit;
import com.heepay.manage.modules.agent.service.profit.AgentProfitService;
import com.heepay.manage.modules.payment.entity.WithdrawRecord;
import com.heepay.manage.modules.util.ExcelUtil2007;
import org.apache.commons.lang3.EnumUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 徐超华 on 2017/9/4.
 */
@Controller
@RequestMapping(value = "${adminPath}/agent/report")
public class AgentReportController {
    @Autowired
    AgentProfitService agentProfitService;

    @ModelAttribute
    public AgentProfit get(@RequestParam(required=false) String id) {
        AgentProfit entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = agentProfitService.get(id);
        }
        if (entity == null){
            entity = new AgentProfit();
        }
        return entity;
    }
    @RequiresPermissions("agent:report:view")
    @RequestMapping(value = {"profitSum", ""})
    public String profitSum(AgentProfit agentProfit, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AgentProfit> page = new Page<AgentProfit>(request, response);
        page.setOrderBy("profit_date desc");
        page = agentProfitService.findPageTotal(page, agentProfit);
        model.addAttribute("page", page);
        return "modules/agent/report/agentProfitReport";
    }

    @RequiresPermissions("agent:report:view")
    @RequestMapping(value = {"detail", ""})
    public String detail(AgentProfit agentProfit, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AgentProfit> page = new Page<AgentProfit>(request, response);
        //page.setOrderBy("update_time desc");
        page = agentProfitService.findPage(page, agentProfit);
        model.addAttribute("page", page);
        return "modules/agent/report/agentProfitReportDetail";
    }

    @RequiresPermissions("agent:report:view")
    @RequestMapping(value = {"pay", ""})
    public String pay(AgentProfit agentProfit, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AgentProfit> page = new Page<AgentProfit>(request, response);
        //page.setOrderBy("update_time desc");
        agentProfitService.updatePayStatus(agentProfit);
        return "redirect:"+ Global.getAdminPath()+"/agent/report/profitSum?repage";
    }

    @RequiresPermissions("agent:report:view")
    @RequestMapping(value = "exportExcel")
    public void exportExcel(AgentProfit agentProfit, HttpServletResponse response, HttpServletRequest request) throws Exception{
        Page<AgentProfit> page = new Page<AgentProfit>(request, response);
        page.setOrderBy("profit_date desc");

        List<String[]> contents = new ArrayList<String[]>();
        String title = "分润报表:";
        if(agentProfit.getProfitDateBegin()!=null && agentProfit.getProfitDateEnd()!=null){
            title = "分润报表:"+ agentProfit.getProfitDateBegin()+"-"+agentProfit.getProfitDateEnd();
        }
        String[] headers = new String[] { "结算日期", "交易总笔数", "交易总商户数", "交易总金额", "手续费总金额",
                "一级代理数", "总分润金额" };
        page = agentProfitService.findPageTotal(page, agentProfit);
        int pageSize = 500;
        int totalCount = (int)page.getCount();
        int curPage = 1;//从第一页开始
        int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
        for(int i=1;i<=totalpage;i++) {
            page.setPageNo(curPage);
            page.setPageSize(pageSize);
            page = agentProfitService.findPageTotal(page, agentProfit);
            List<AgentProfit> agentProfitList= page.getList();
            for(AgentProfit record : agentProfitList){
                String[] content = new String[headers.length];
                content[0] = String.valueOf(record.getProfitDate());
                content[1] = record.getTransTotalCount();
                content[2] = record.getMerchantNum();
                content[3] = record.getTransTotalAmount();
                content[4] = record.getFeeTotalAmount();
                content[5] = record.getAgentId();
                content[6] = record.getProfitTotalAmount();
                contents.add(content);
            }
            curPage++;
        }
        String fileName =title.concat("-" + DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
        ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
    }

    @RequiresPermissions("agent:report:view")
    @RequestMapping(value = "exportExcelDetail")
    public void exportExcelDetail(AgentProfit agentProfit, HttpServletResponse response, HttpServletRequest request) throws Exception{
        Page<AgentProfit> page = new Page<AgentProfit>(request, response);
        page.setOrderBy("agent_id desc");

        List<String[]> contents = new ArrayList<String[]>();
        String title = "分润报表明细:";
        if(agentProfit.getProfitDate()!=null){
            title = "分润报表明细:"+ agentProfit.getProfitDate();
        }
        String[] headers = new String[] { "结算日期", "一级代理商", "所属商户数", "交易总金额", "手续费总金额",
                "总分润金额" };
        page = agentProfitService.findPage(page, agentProfit);
        int pageSize = 500;
        int totalCount = (int)page.getCount();
        int curPage = 1;//从第一页开始
        int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
        for(int i=1;i<=totalpage;i++) {
            page.setPageNo(curPage);
            page.setPageSize(pageSize);
            page = agentProfitService.findPage(page, agentProfit);
            List<AgentProfit> agentProfitList= page.getList();
            for(AgentProfit record : agentProfitList){
                String[] content = new String[headers.length];
                content[0] = String.valueOf(record.getProfitDate());
                content[1] = record.getAgentName();
                content[2] = record.getMerchantNum();
                content[3] = record.getTransTotalAmount();
                content[4] = record.getFeeTotalAmount();
                content[5] = record.getProfitTotalAmount();
                contents.add(content);
            }
            curPage++;
        }
        String fileName =title.concat("-" + DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
        ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
    }
}
