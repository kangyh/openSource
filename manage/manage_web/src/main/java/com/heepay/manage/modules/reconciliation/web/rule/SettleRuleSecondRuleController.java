package com.heepay.manage.modules.reconciliation.web.rule;
/**
 * 描 述：对账二代规则
 *
 * 创 建 者：wangl
 * 创建时间： 2017年5月9日 下午1:14:21
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */

import com.google.common.collect.Lists;
import com.heepay.billingutils.Base64Utils;
import com.heepay.billingutils.FileType;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingBecomeEffect;
import com.heepay.enums.billing.CheckWay;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.RuleType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleRegexControl;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleControl;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleSecond;
import com.heepay.manage.modules.reconciliation.service.SettleRegexControlService;
import com.heepay.manage.modules.reconciliation.service.SettleRuleControlService;
import com.heepay.manage.modules.reconciliation.service.SettleRuleSecondService;
import com.heepay.manage.modules.reconciliation.web.util.PatternUtils;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.reconciliation.web.util.UnitType;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/reconciliation/SettleRuleSecond")
public class SettleRuleSecondRuleController extends BaseController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private SettleRuleSecondService settleRuleSecondService;

    @Autowired
    private SettleRuleControlService settleRuleControlService;

    //正则表达式
    @Autowired
    private SettleRegexControlService settleRegexControlService;

    //更新保存
    List<SettleRegexControl> saveList = null;

    /**
     * @方法说明：二代规则显示
     * @时间： 2017-05-26 04:08 PM
     * @创建人：wangl
     */
    @RequiresPermissions("settle:settleRuleSecond:view")
    @RequestMapping(value = {"list", ""})
    public String list(SettleRuleSecond settleRuleSecond,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       Model model) {

        //使用cookie保存查询条件
        settleRuleSecond = (SettleRuleSecond) SaveConditions.result(settleRuleSecond, "settleRuleSecond", request, response);

        Page<SettleRuleSecond> page = settleRuleSecondService.findPage(new Page<SettleRuleSecond>(request, response),settleRuleSecond);
        logger.info("对账二代规则控制处理开始--->", settleRuleSecond);
        //查询通道合作方的内容
        List<EnumBean> dataEntityChannelType = DictList.channelType();
        for (SettleRuleSecond clearingCR : page.getList()) {
            String channelCode = clearingCR.getChannelCode();
            String channelName = settleRuleSecondService.getChannelName(channelCode);
            clearingCR.setChannelName(channelName);
            // 通道业务类型
            if (StringUtils.isNotBlank(clearingCR.getChannelType())) {
                dataEntityChannelType.forEach(p -> {
                    if(p.getValue().equals(clearingCR.getChannelType())){
                        clearingCR.setChannelType(p.getName());
                    }

                });
            }
            // status
            if (StringUtils.isNotBlank(clearingCR.getStatus())) {
                clearingCR.setStatus(BillingBecomeEffect.labelOf(clearingCR.getStatus()));
            }
            //对账方式
            if (StringUtils.isNotBlank(clearingCR.getSettleWay())) {
                clearingCR.setSettleWay(CheckWay.labelOf(clearingCR.getSettleWay()));
            }
        }
        resultModel(model);

        model.addAttribute("page", page);
        model.addAttribute("settleRuleSecond", settleRuleSecond);
        logger.info("规则控制处理结束------>", settleRuleSecond);
        return "modules/reconciliation/SettleRuleSecondList";
    }


    /**
     * @方法说明：对账二代规则添加和修改的跳转
     * @时间： 2017-05-26 04:01 PM
     * @创建人：wangl
     */
    @RequiresPermissions("settle:settleRuleSecond:view")
    @RequestMapping(value = "/updateAndAdd")
    public String updateAndAdd(SettleRuleSecond settleRuleSecond,
                               @RequestParam(value = "ruleId", required = false) Integer ruleId,
                               Model model) {

        if(null != ruleId){
            logger.info("对账二代规则修改的跳转--->", settleRuleSecond);
            settleRuleSecond = settleRuleSecondService.getEntity(ruleId);
            //支付单号
            if(settleRuleSecond.getPaymentId() != null){
                String paymentId = settleRuleSecond.getPaymentId();
                boolean flag = PatternUtils.isNaN(paymentId);
                if(flag){
                    //保存查询条件
                    SettleRegexControl regex_paymentId=new SettleRegexControl();
                    long ruleIdRu= ruleId;
                    regex_paymentId.setRuleId(ruleIdRu);//规则主键
                    regex_paymentId.setRuleKey(Long.parseLong(paymentId));//规则key
                    regex_paymentId.setRuleType(com.heepay.manage.modules.reconciliation.web.util.RuleType.SPECIAL.getValue());//规则类型 SPECIAL：二代规则
                    List<SettleRegexControl> list_paymentId=settleRegexControlService.getEntityByList(regex_paymentId);
                    model.addAttribute("list_paymentId", list_paymentId);
                }

            }

            //银行流水号位置
            if(settleRuleSecond.getBankSeq() != null){
                String bankSeq = settleRuleSecond.getBankSeq();
                boolean flag = PatternUtils.isNaN(bankSeq);
                if(flag) {
                    //保存查询条件
                    SettleRegexControl regex_paymentId = new SettleRegexControl();
                    long ruleIdRu = ruleId;
                    regex_paymentId.setRuleId(ruleIdRu);//规则主键
                    regex_paymentId.setRuleKey(Long.parseLong(bankSeq));//规则key
                    regex_paymentId.setRuleType(com.heepay.manage.modules.reconciliation.web.util.RuleType.SPECIAL.getValue());//规则类型 SPECIAL：二代规则
                    List<SettleRegexControl> list_bankSeq = settleRegexControlService.getEntityByList(regex_paymentId);
                    model.addAttribute("list_bankSeq", list_bankSeq);
                }
            }

            //服务费位置
            if(StringUtils.isNoneBlank(settleRuleSecond.getFeeService())){
               String feeService = settleRuleSecond.getFeeService();
                int index = feeService.indexOf(".");
                if(index != -1){
                    String begin = feeService.substring(0,index);
                    settleRuleSecond.setFeeService(begin);
                    String end = feeService.substring(index+1,feeService.length());
                    settleRuleSecond.setFeeServiceAdd(end);
                }
            }


            // 优惠金额位置
            if(StringUtils.isNoneBlank(settleRuleSecond.getFeeFree())){
                String feeFree = settleRuleSecond.getFeeFree();
                int index = feeFree.indexOf(".");
                if(index != -1){
                    String begin = feeFree.substring(0,index);
                    settleRuleSecond.setFeeFree(begin);
                    String end = feeFree.substring(index+1,feeFree.length());
                    settleRuleSecond.setFeeFreeAdd(end);
                }

            }

            //发卡行手续费位置
            if(StringUtils.isNoneBlank(settleRuleSecond.getFeeBank())){
                String feeBank = settleRuleSecond.getFeeBank();
                int index = feeBank.indexOf(".");
                if(index != -1){
                    String begin = feeBank.substring(0,index);
                    settleRuleSecond.setFeeBank(begin);
                    String end = feeBank.substring(index+1,feeBank.length());
                    settleRuleSecond.setFeeBankAdd(end);
                }
            }

            //结算金额位置
            if(StringUtils.isNoneBlank(settleRuleSecond.getSuccessAmount())){
                String successAmount = settleRuleSecond.getSuccessAmount();
                int index = successAmount.indexOf(".");
                if(index != -1){
                    String begin = successAmount.substring(0,index);
                    settleRuleSecond.setSuccessAmount(begin);
                    String end = successAmount.substring(index+1,successAmount.length());
                    settleRuleSecond.setSuccessAmountAdd(end);
                }
            }

            //交易状态位置
            if(StringUtils.isNoneBlank(settleRuleSecond.getTransStatus())){
                String transStatus = settleRuleSecond.getTransStatus();
                int index = transStatus.indexOf(".");
                if(index != -1){
                    String begin = transStatus.substring(0,index);
                    settleRuleSecond.setTransStatus(begin);
                    String end = transStatus.substring(index+1,transStatus.length());
                    settleRuleSecond.setTransStatusAdd(end);
                }
            }


        }else{
            settleRuleSecond = new SettleRuleSecond();
            logger.info("对账二代规则添加的跳转--->", settleRuleSecond);
        }

        resultModel(model);
        model.addAttribute("settleRuleSecond", settleRuleSecond);

        return  "modules/reconciliation/rule/settleRuleSecondAddUpdate";

    }



    /**
     * @方法说明：对账二代规则添加和修改
     * @时间： 2017-05-26 04:01 PM
     * @创建人：wangl
     */
    @RequiresPermissions("settle:settleRuleSecond:view")
    @RequestMapping(value = "/save")
    public String save(SettleRuleSecond settleRuleSecond,
                       RedirectAttributes redirectAttributes,
                       @RequestParam(value = "ruleId", required = false) Integer ruleId) {

        //判断是否和二代规则冲突
        SettleRuleControl control=new SettleRuleControl();
        control.setChannelCode(settleRuleSecond.getChannelCode());
        control.setChannelType(settleRuleSecond.getChannelType());
        control.setBillType(settleRuleSecond.getBillType());
        control.setSettleWay(settleRuleSecond.getSettleWay());
        int result = settleRuleControlService.repeat(control);
        if(result > 0){
            addMessage(redirectAttributes, "保存失败,不能添加和一代重复的规则");
            return "redirect:" + Global.getAdminPath() + "/reconciliation/SettleRuleSecond?cache=1";
        }

        if(StringUtils.isNoneBlank(settleRuleSecond.getSplitFlg())){
            //对base64加密的正则表达式进行解密
            settleRuleSecond.setSplitFlg(Base64Utils.decodeData(settleRuleSecond.getSplitFlg()));
        }
        //服务费位置
        if(StringUtils.isNoneBlank(settleRuleSecond.getFeeService())){
            settleRuleSecond.setFeeService(settleRuleSecond.getFeeService()+"."+settleRuleSecond.getFeeServiceAdd());
        }


        // 优惠金额位置
        if(StringUtils.isNoneBlank(settleRuleSecond.getFeeFree())){
            settleRuleSecond.setFeeFree(settleRuleSecond.getFeeFree()+"."+settleRuleSecond.getFeeFreeAdd());
        }

        //发卡行手续费位置
        if(StringUtils.isNoneBlank(settleRuleSecond.getFeeBank())){
            settleRuleSecond.setFeeBank(settleRuleSecond.getFeeBank()+"."+settleRuleSecond.getFeeBankAdd());
        }

        //结算金额位置
        if(StringUtils.isNoneBlank(settleRuleSecond.getSuccessAmount())){
            settleRuleSecond.setSuccessAmount(settleRuleSecond.getSuccessAmount()+"."+settleRuleSecond.getSuccessAmountAdd());
        }

        //交易状态位置
        if(StringUtils.isNoneBlank(settleRuleSecond.getTransStatus())){
            settleRuleSecond.setTransStatus(settleRuleSecond.getTransStatus()+"."+settleRuleSecond.getTransStatusAdd());
        }

        Long ruleIdRule = settleRuleSecond.getRuleId();
        if(null != ruleIdRule){
            logger.info("对账二代规则修改--->", ruleId);
            settleRegexControlService.deleteByRule(ruleIdRule, com.heepay.manage.modules.reconciliation.web.util.RuleType.SPECIAL.getValue());
            settleRuleSecond.setUpdateTime(new Date());
            settleRuleSecond.setUpdateAuthor(UserUtils.getUser().getName());
            settleRuleSecondService.saveEntity(settleRuleSecond);

        }else{
            try {
                settleRuleSecond.setCreateTime(new Date());
                settleRuleSecond.setCreateAuthor(UserUtils.getUser().getName());
                settleRuleSecondService.addEntity(settleRuleSecond);
                ruleIdRule = settleRuleSecond.getRuleId();
                logger.info("对账二代规则添加--->", settleRuleSecond);
            } catch (Exception e) {
                logger.error("对账二代规则添加/修改--->{异常}"+e.getMessage());
            }
        }

        //批量插入
        int num = breachAddRegex(settleRuleSecond, ruleIdRule);
        if(num == 1){
            addMessage(redirectAttributes, "操作成功");
        }else{
            addMessage(redirectAttributes, "操作失败");
        }
        return "redirect:" + Global.getAdminPath() + "/reconciliation/SettleRuleSecond?cache=1";

    }


    /**
     * @方法说明:批量保存插入的条件
     * @时间： 2017-05-15 10:43 AM
     * @创建人：wangl
     */
    private int breachAddRegex(SettleRuleSecond settleRuleSecond, Long ruleId) {
        if(null == saveList){
            saveList = new ArrayList<>();
        }

        //批量插入正则控制表
        //支付单号
        if(settleRuleSecond.getPaymentId() !=null){
            //规则key
            String paymentId = settleRuleSecond.getPaymentId();
            boolean flag = PatternUtils.isNaN(paymentId);
            if(flag){
                //交易成本
                String paymentIdName = settleRuleSecond.getPaymentIdName();
                String paymentIdRule = settleRuleSecond.getPaymentIdRule();

                if(StringUtils.isNoneBlank(paymentIdName) && StringUtils.isNoneBlank(paymentIdRule)){
                    saveRegex(ruleId, paymentIdName, paymentIdRule,Long.parseLong(paymentId));
                }
            }

        }

        //银行流水号
        if(settleRuleSecond.getBankSeq() !=null){
            //规则key
            String bankSeq = settleRuleSecond.getBankSeq();
            boolean flag = PatternUtils.isNaN(bankSeq);
            if(flag){
                //交易成本
                String bankSeqName = settleRuleSecond.getBankSeqName();
                String bankSeqRule = settleRuleSecond.getBankSeqRule();
                if(StringUtils.isNoneBlank(bankSeqName) && StringUtils.isNoneBlank(bankSeqRule)){
                    saveRegex(ruleId, bankSeqName, bankSeqRule,Long.parseLong(bankSeq));
                }
            }

        }

        //批量插入
        if(saveList != null && !saveList.isEmpty()){
            try {
                settleRegexControlService.insetList(saveList);
                logger.info("规则控表批量插入-----{成功}");
                saveList = null;
                return 1;
            } catch (Exception e) {
                logger.error("规则控表批量插入失败------->{}", e.getMessage());
                saveList = null;
                return 0;
            }
        }else{
            return 1;
        }
    }

    /**
     * @方法说明：删除正则表达式方法
     * @时间： 2017-05-26 06:29 PM
     * @创建人：wangl
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "/delete")
    public boolean deleteEntityValue(@RequestParam(value = "deleteId", required = false) Long deleteId) {
        if(deleteId !=null){
            try {
                int num=settleRegexControlService.deleteEntity(deleteId);
                logger.info("删除规则方法执行了------->{成功}",num);
                return true;
            } catch (Exception e) {
                logger.error("删除规则方法执行了------->{失败}", e.getMessage());
                return false;
            }
        }
        return false;
    }



    /**
     * @方法说明：删除规则方法
     * @时间： 2017-05-26 06:29 PM
     * @创建人：wangl
     */
    @RequiresPermissions("settle:settleRuleSecond:edit")
    @RequestMapping(value = "/delete/{ruleId}")
    public String deleteEntity(@PathVariable(value = "ruleId") Long ruleId, RedirectAttributes redirectAttributes) {

        try {

            SettleRegexControl regex=new SettleRegexControl();
            regex.setRuleId(ruleId);
            regex.setRuleType(RuleType.SPECIAL.getValue());
            //级联删除子数据
            settleRegexControlService.deleteEntityByRuleIdAndRuleType(regex);
            int num=settleRuleSecondService.deleteEntity(ruleId);
            logger.info("删除规则方法执行了------->{成功}",num);
            addMessage(redirectAttributes, "删除成功");
        } catch (Exception e) {
            logger.error("删除规则方法执行了------->{失败}", e.getMessage());
            addMessage(redirectAttributes, "删除成功");
        }

        return "redirect:" + Global.getAdminPath() + "/reconciliation/SettleRuleSecond?cache=1";
    }

    /**
     * @方法说明：正则表达式入库操作
     * @throws
     * @时间：2017年1月18日上午10:01:00
     * @创建人：wangl
     */
    private void saveRegex(Long ruleId, String breachName,String breachRule,Long id) {

        if (StringUtils.isNotBlank(breachName) && StringUtils.isNotBlank(breachRule)) {

            String[] splitEntityName = breachName.split(",");
            String[] splitEntityRule = breachRule.split(",");
            for (int i = 0, length = splitEntityName.length; i < length; i++) {
                //获取规则名称
                String regexName = splitEntityName[i];
                //获取规则表达式
                String regexShow = splitEntityRule[i];

                if(StringUtils.isNoneBlank(regexName) && StringUtils.isNoneBlank(regexShow)){
                    //对base64加密的正则表达式进行解密
                    String decodeData = Base64Utils.decodeData(regexShow);
                    //保存入库
                    SettleRegexControl settleRegexControl = new SettleRegexControl();
                    settleRegexControl.setRegexName(regexName);//规则名称
                    settleRegexControl.setRegexShow(decodeData);//正则表达式
                    settleRegexControl.setRuleId(ruleId);//规则主键
                    settleRegexControl.setCreateTime(new Date());//创建时间
                    settleRegexControl.setCreateAuthor(UserUtils.getUser().getName());//创建时间
                    settleRegexControl.setRuleKey(id);//规则key
                    settleRegexControl.setRuleType(com.heepay.manage.modules.reconciliation.web.util.RuleType.SPECIAL.getValue());
                    //将正则表达式入库
                    try {
                        saveList.add(settleRegexControl);
                    } catch (Exception e) {
                        logger.info("正则控制表入库异常------{}", e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * @方法说明：返回查询条件
     * @时间： 2017-05-26 03:30 PM
     * @创建人：wangl
     */
    private void resultModel(Model model) {

        List<SettleChannelManager> list = settleRuleSecondService.getBatchName();
        // 通道合作方
        if (list.isEmpty()) {
            model.addAttribute("batchList", null);
        } else {
            //通道合作方
            List<SettleRuleSecond> channelCode = Lists.newArrayList();
            for (SettleChannelManager settleChannelManager : list) {
                SettleRuleSecond settleRule = new SettleRuleSecond();
                settleRule.setChannelCode(settleChannelManager.getChannelCode());
                settleRule.setChannelName(settleChannelManager.getChannelName());
                channelCode.add(settleRule);
            }
            model.addAttribute("channelCode", channelCode);

            // 用来判断能不能添加
            model.addAttribute("batchList", channelCode);
        }

        //查询通道合作方的内容
        List<EnumBean> dataEntityChannelType = DictList.channelType();
        //支付通道类型
        EnumBean de = new EnumBean();
        de.setName(TransType.REAL_NAME.getContent());//"实名认证"
        de.setValue(TransType.REAL_NAME.getValue());//RENAME
        dataEntityChannelType.add(de);
        model.addAttribute("channelType", dataEntityChannelType);

        // 生效标识
        List<EnumBean> status = Lists.newArrayList();
        for (BillingBecomeEffect checkFlg : BillingBecomeEffect.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            status.add(ct);
        }
        model.addAttribute("status", status);

        //账单类型
        List<EnumBean> billType = Lists.newArrayList();
        for (FileType checkFlg : FileType.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            billType.add(ct);
        }
        model.addAttribute("billType", billType);

        //对账方式
        List<EnumBean> settleWay = Lists.newArrayList();
        for (CheckWay checkFlg : CheckWay.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            settleWay.add(ct);
        }
        model.addAttribute("settleWay", settleWay);

        // 状态
        List<EnumBean> statusRegx = Lists.newArrayList();
        for (BillingBecomeEffect checkFlg : BillingBecomeEffect.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            statusRegx.add(ct);
        }
        model.addAttribute("statusRegx", statusRegx);

        //对账方式
        List<EnumBean> unitType = Lists.newArrayList();
        for (UnitType checkFlg : UnitType.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            unitType.add(ct);
        }
        model.addAttribute("unitType", unitType);
    }


}

 