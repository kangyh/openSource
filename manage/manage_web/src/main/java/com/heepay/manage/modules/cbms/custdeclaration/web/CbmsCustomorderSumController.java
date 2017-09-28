/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.custdeclaration.web;

import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.CbmsCustOrderSumStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.cbms.entity.*;
import com.heepay.manage.modules.cbms.service.CbmsCustomorderSumService;
import com.heepay.manage.modules.cbms.service.CbmsCustomsettingService;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 描    述：通关申报订单查询Controller
 * <p>
 * 创 建 者： guozx
 * 创建时间： 2017年1月3日09:49:46
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Controller
@RequestMapping(value = "${adminPath}/cbms/cbmsCustomorderSum")
public class CbmsCustomorderSumController extends BaseController {

    @Autowired
    private CbmsCustomorderSumService cbmsCustomorderSumService;

    @Autowired
    private CbmsCustomsettingService cbmsCustomsettingService;

    @Autowired
    private MerchantUserCService merchantUserCService;

    /**
     * @param id
     * @return
     * @discription 根据id获取通关申报订单信息
     * @author guozx
     * @created 2017年1月15日 下午4:48:34
     */
    @ModelAttribute
    public CbmsCustomorderSum get(@RequestParam(required = false) String id) {
        CbmsCustomorderSum entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cbmsCustomorderSumService.get(id);
        }
        if (entity == null) {
            entity = new CbmsCustomorderSum();
        }
        return entity;
    }

    /**
     * @param cbmsCustomorderSum
     * @param request
     * @param response
     * @param model
     * @return
     * @discription 获取通关申报订单列表
     * @author guozx
     * @created 2017年1月15日 下午4:49:26
     */
    @RequiresPermissions("cbms:cbmsCustomorderSum:view")
    @RequestMapping(value = {"list", ""})
    public String list(CbmsCustomorderSum cbmsCustomorderSum, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CbmsCustomorderSum> page = cbmsCustomorderSumService.findPage(new Page<CbmsCustomorderSum>(request, response), cbmsCustomorderSum);
        List<CbmsCustomsetting> customsettinglist = cbmsCustomsettingService.findcustomsettinglist();
        List<CbmsCustomorderSum> list = page.getList();
        for (CbmsCustomorderSum customorderSum : list) {
            //通过海关代码获取对应的海关中文名字，并且封装到CbmsCustomorderSum对象中
            String customCode = customorderSum.getCustomCode();
            if (customCode != null) {
                for (CbmsCustomsetting cbmsCustomsetting : customsettinglist) {
                    if (customCode.equals(cbmsCustomsetting.getCustomNo())) {
                        customorderSum.setCustomName(cbmsCustomsetting.getChinaName());
                        break;
                    }
                }
            }
            //根据merchantNo获取商户的loginName并且分装到CbmsCustomorderSum
            String merchantNo = customorderSum.getMerchantNo();
            MerchantUser merchantUser = merchantUserCService.get(merchantNo);
            if (merchantUser != null) {
                customorderSum.setLoginName(merchantUser.getLoginName());
                logger.info("获取商户的登录名：----->成功");
            }
            //将CbmsCustomorderSum类中的customStatus转换显示中文
            CbmsCustOrderSumStatus status = CbmsCustOrderSumStatus.getBean(customorderSum.getStatus());
            if (status != null) {
                customorderSum.setStatus(status.getContent());
            }
            //转换申报类型为中文格式
            if ("2".equals(customorderSum.getDeclareType())) {
                customorderSum.setDeclareType("API接口");
            } else {
                customorderSum.setDeclareType("文件上传");
            }
        }
        page.setList(list);
        //查询汇总信息,转换对象
        CustdeclarationQuery query = getCustdeclarationQuery(cbmsCustomorderSum);
        //转换时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //将商户审核时间查询时，开始时间和末尾时间分撞到cbmsCustomorderSum对象中
        Date endDate = cbmsCustomorderSum.getEndDeclarationTime();
        Date startDate = cbmsCustomorderSum.getBeginDeclarationTime();
        if (endDate != null) {
            query.setDeclarationTimeEnd(simpleDateFormat.format(endDate));
        } else {
            query.setDeclarationTimeEnd(null);
        }
        if (startDate != null) {
            query.setDeclarationTimeStart(simpleDateFormat.format(startDate));
        } else {
            query.setDeclarationTimeStart(null);
        }
        //查询结果类

        CusdeclarationResult result = cbmsCustomorderSumService.getCusdeclarationResult(query);
        CbmsCustomorderSumSummary cbmsCustomorderSumSummary = changeObject(result);
        model.addAttribute("cbmsCustomorderSumSummary", cbmsCustomorderSumSummary);
        model.addAttribute("customsettinglist", customsettinglist);
        model.addAttribute("page", page);
        return "modules/cbms/cbmsCustomorderSum/cbmsCustomorderSumList";
    }

    /**
     * 转换CbmsCustomorderSum to CustdeclarationQuery
     *
     * @param cbmsCustomorderSum
     * @return
     */
    private CustdeclarationQuery getCustdeclarationQuery(CbmsCustomorderSum cbmsCustomorderSum) {
        CustdeclarationQuery query = new CustdeclarationQuery();
        if (!"".equals(cbmsCustomorderSum.getCustomCode())) {
            query.setCustomCode(cbmsCustomorderSum.getCustomCode());
        } else {
            query.setCustomCode(null);
        }
        if (!"".equals(cbmsCustomorderSum.getDeclarationBatchHumber())) {
            query.setDeclarationBatchHumber(cbmsCustomorderSum.getDeclarationBatchHumber());
        } else {
            query.setDeclarationBatchHumber(null);
        }

        if (!"".equals(cbmsCustomorderSum.getMerchantNo())) {
            query.setMerchantNo(cbmsCustomorderSum.getMerchantNo());
        } else {
            query.setMerchantNo(null);
        }

        if (!"".equals(cbmsCustomorderSum.getStatus())) {
            query.setStatus(cbmsCustomorderSum.getStatus());
        } else {
            query.setStatus(null);
        }
        if (!"".equals(cbmsCustomorderSum.getImportBatchNumber())) {
            query.setImportBatchNumber(cbmsCustomorderSum.getImportBatchNumber());
        } else {
            query.setImportBatchNumber(null);
        }
        if (!"".equals(cbmsCustomorderSum.getDeclareType())) {
            query.setDeclareType(cbmsCustomorderSum.getDeclareType());
        } else {
            query.setDeclareType(null);
        }

        return query;
    }

    /**
     * @param result
     * @return CbmsCustomorderSumSummary
     * @discription 将申报订单查询的详情封装到CbmsCustomorderSumSummary类中
     * @author guozx
     * @created 2017年1月15日 下午4:49:39
     */
    private CbmsCustomorderSumSummary changeObject(CusdeclarationResult result) {
        CbmsCustomorderSumSummary summary = new CbmsCustomorderSumSummary();
        if (result != null) {
            summary.setFeeCount(result.getFeeCount());
            summary.setDeclarationMoneySuccess(result.getDeclarationMoneySuccess());
            summary.setDeclarationNumberFailed(result.getDeclarationNumberFailed());
            summary.setDeclarationMoneyCount(result.getDeclarationMoneyCount());
            summary.setDeclarationNumberSuccess(result.getDeclarationNumberSuccess());
            summary.setDeclarationMoneyFailed(result.getDeclarationMoneyFailed());
            summary.setDeclarationNumberCount(result.getDeclarationNumberCount());
        } else {
            //将申报订单查询的结果初始化
            summary.setFeeCount("0.00");
            summary.setDeclarationMoneySuccess("0.00");
            summary.setDeclarationNumberFailed("0");
            summary.setDeclarationMoneyCount("0.00");
            summary.setDeclarationNumberSuccess("0");
            summary.setDeclarationMoneyFailed("0.00");
            summary.setDeclarationNumberCount("0");
        }
        return summary;
    }


    /**
     * @param cbmsCustomorderSum
     * @param model
     * @return
     * @discription 获取通关申报订单新增修改页面
     * @author guozx
     * @created 2017年1月15日 下午4:49:39
     */
    @RequiresPermissions("cbms:cbmsCustomorderSum:view")
    @RequestMapping(value = "form")
    public String form(CbmsCustomorderSum cbmsCustomorderSum, Model model) {
        model.addAttribute("cbmsCustomorderSum", cbmsCustomorderSum);
        return "modules/cbms/cbmsCustomorderSum/cbmsCustomorderSumForm";
    }

    /**
     * @param cbmsCustomorderSum
     * @param model
     * @param redirectAttributes
     * @return
     * @discription 保存修改通关申报订单
     * @author guozx
     * @created 2017年1月15日 下午4:58:31
     */
    @RequiresPermissions("cbms:cbmsCustomorderSum:edit")
    @RequestMapping(value = "save")
    public String save(CbmsCustomorderSum cbmsCustomorderSum, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, cbmsCustomorderSum)) {
            return form(cbmsCustomorderSum, model);
        }
        cbmsCustomorderSumService.save(cbmsCustomorderSum);
        addMessage(redirectAttributes, "保存通关申报订单成功");
        logger.info("保存申报订单状态：----->成功");
        return "redirect:" + Global.getAdminPath() + "/cbms/cbmsCustomorderSum/?repage";
    }

    /**
     * @param cbmsCustomorderSum
     * @return
     * @discription 删除通关申报订单状态（添加初始产品）
     * @author guozx
     * @created 2017年1月15日 下午4:59:34
     */
    @RequiresPermissions("cbms:cbmsCustomorderSum:edit")
    @RequestMapping(value = "delete")
    public String delete(CbmsCustomorderSum cbmsCustomorderSum, RedirectAttributes redirectAttributes) {
        cbmsCustomorderSumService.delete(cbmsCustomorderSum);
        addMessage(redirectAttributes, "删除通关申报订单成功");
        return "redirect:" + Global.getAdminPath() + "/cbms/cbmsCustomorderSum/?repage";
    }

    /**
     * 申报订单查询统计使用的
     *
     * @param list
     * @return
     */
    private CbmsCustomorderSumSummary getSummaryCbmsCustomorderSumSummary(List<CbmsCustomorderSum> list) {

        CbmsCustomorderSumSummary cbmsCustomorderSumSummary = new CbmsCustomorderSumSummary();
        String tempTotal;
        for (CbmsCustomorderSum customorderSum : list) {
            //申报总笔数
            tempTotal = StringUtil.isBlank(customorderSum.getDeclarationNumber()) ? "0" : customorderSum.getDeclarationNumber();
            cbmsCustomorderSumSummary.setDeclarationNumberCount(String.valueOf(Integer.parseInt(cbmsCustomorderSumSummary.getDeclarationNumberCount() == null ? "0" : cbmsCustomorderSumSummary.getDeclarationNumberCount()) + new Integer(tempTotal)));
//            cbmsCustomorderSumSummary.setDeclarationNumberCount(String.valueOf(new Integer(cbmsCustomorderSumSummary.getDeclarationNumberCount()) + (new Integer(tempTotal))));

            if ("报送成功".equals(customorderSum.getStatus())) {
                //申报成功总笔数
                tempTotal = StringUtil.isBlank(customorderSum.getDeclarationNumber()) ? "0" : customorderSum.getDeclarationNumber();

                cbmsCustomorderSumSummary.setDeclarationNumberSuccess(String.valueOf(Integer.parseInt(cbmsCustomorderSumSummary.getDeclarationNumberSuccess() == null ? "0" : cbmsCustomorderSumSummary.getDeclarationNumberSuccess()) + (new Integer(tempTotal))));
            }
            if ("报送失败".equals(customorderSum.getStatus())) {
                //申报失败总笔数
                tempTotal = StringUtil.isBlank(customorderSum.getDeclarationNumber()) ? "0" : customorderSum.getDeclarationNumber();
                cbmsCustomorderSumSummary.setDeclarationNumberFailed(String.valueOf(new Integer(cbmsCustomorderSumSummary.getDeclarationNumberFailed() == null ? "0" : cbmsCustomorderSumSummary.getDeclarationNumberFailed()) + (new Integer(tempTotal))));
            }
            //转账总金额
            tempTotal = StringUtil.isBlank(customorderSum.getDeclarationMoney()) ? "0.0000" : customorderSum.getDeclarationMoney();
            cbmsCustomorderSumSummary.setDeclarationMoneyCount(String.valueOf(new BigDecimal(cbmsCustomorderSumSummary.getDeclarationMoneyCount() == null ? "0.00" : cbmsCustomorderSumSummary.getDeclarationMoneyCount()).add(new BigDecimal(tempTotal))));

            if ("报送成功".equals(customorderSum.getStatus())) {
                //转账成功金额
                tempTotal = StringUtil.isBlank(customorderSum.getDeclarationMoney()) ? "0.0000" : customorderSum.getDeclarationMoney();
                cbmsCustomorderSumSummary.setDeclarationMoneySuccess(String.valueOf(new BigDecimal(cbmsCustomorderSumSummary.getDeclarationMoneySuccess() == null ? "0.00" : cbmsCustomorderSumSummary.getDeclarationMoneySuccess()).add(new BigDecimal(tempTotal))));
            }
            if ("报送失败".equals(customorderSum.getStatus())) {
                //转账失败总金额
                tempTotal = StringUtil.isBlank(customorderSum.getDeclarationMoney()) ? "0.0000" : customorderSum.getDeclarationMoney();
                cbmsCustomorderSumSummary.setDeclarationMoneyFailed(String.valueOf(new BigDecimal(cbmsCustomorderSumSummary.getDeclarationMoneyFailed() == null ? "0.00" : cbmsCustomorderSumSummary.getDeclarationMoneyFailed()).add(new BigDecimal(tempTotal))));
            }
            //手续费
            cbmsCustomorderSumSummary.setFeeCount(String.valueOf(new BigDecimal(cbmsCustomorderSumSummary.getFeeCount() == null ? "0.00" : cbmsCustomorderSumSummary.getFeeCount()).add(new BigDecimal(customorderSum.getFee()))));
        }

        return cbmsCustomorderSumSummary;
    }
}