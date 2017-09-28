package com.heepay.manage.modules.differences.web;

import com.google.common.collect.Lists;
import com.heepay.billingutils.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.*;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.differences.entity.SettleDifferChannel;
import com.heepay.manage.modules.differences.service.SettleDifferChannelService;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecord;
import com.heepay.manage.modules.reconciliation.service.SettleDifferRecordService;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.service.BankInfoService;
import com.heepay.manage.modules.settle.web.rpc.client.BillingAccountClient;
import com.heepay.manage.modules.settle.web.rpc.client.SettleDifferFillBillClient;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 *
 *
 * 描 述：通道差错账汇总
 *
 * 创 建 者： wangl 
 * 创建时间： 2016年9月23日下午1:38:03 
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
@RequestMapping(value = "${adminPath}/accounting/settleDifferChannels")
public class SettleDifferChannelController extends BaseController {

    private static final Logger logger = LogManager.getLogger();

    //通道合作方
    public static final String CHANNEL_PARTNER = "ChannelPartner";

    @Autowired
    private SettleDifferChannelService settleDifferChannelService;

    @Autowired
    private BankInfoService bankInfoService;// 网关的服务

    @Autowired
    private SettleDifferChannelExcelExport excelController;// 下载方法

    @Autowired
    private SettleDifferRecordService settleDifferRrecordService;

    @Autowired
    private SettleDifferFillBillClient settleDifferFillBillClient;

    @Autowired
    private BillingAccountClient billingAccountClient;

    // 通道差错帐显示的查询操作
    @RequiresPermissions("settle:SettleDifferChannel:view")
    @RequestMapping(value = {"list", ""})
    public String list(SettleDifferChannel settleDifferChannel, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        Page<SettleDifferChannel> page = settleDifferChannelService.findPage(new Page<SettleDifferChannel>(request, response), settleDifferChannel);
        List<SettleDifferChannel> clearingCRList = Lists.newArrayList();

        //查询通道合作方的内容
        List<EnumBean> dataEntityChannelType = DictList.channelType();
        //支付通道类型
        EnumBean de = new EnumBean();
        de.setName(TransType.REAL_NAME.getContent());//"实名认证"
        de.setValue(TransType.REAL_NAME.getValue());//RENAME
        dataEntityChannelType.add(de);

        logger.info("通道差错账查询数据为------>{}" + page.getList());
        for (SettleDifferChannel clearingCR : page.getList()) {

            String transNo = clearingCR.getTransNo();
            /**
             * 根据交易单号去差错记录表查询交易类型
             */
            SettleDifferRecord settleDifferRecord = settleDifferRrecordService.getTransTypeByTransNo(transNo);
            String transType = " ";
            if (settleDifferRecord != null) {
                transType = settleDifferRecord.getTransType();
            }

            if (TransType.WITHDRAW.getValue().equals(transType) ||
                    TransType.REFUND.getValue().equals(transType) ||
                    TransType.BATCHPAY.getValue().equals(transType) ||
                    TransType.PLAY_MONEY.getValue().equals(transType)) {//出款类

                // 出款类，差错批次数据只入库，不做撤补账处理
                if (clearingCR.getCheckStatus().equals(Constants.STATIC_N)) {
                    //用于页面比较
                    clearingCR.setStatus("Y");
                }
            }
            if (TransType.REFUND.getValue().equals(transType)
                    || TransType.WITHDRAW.getValue().equals(transType)
                    || TransType.BATCHPAY.getValue().equals(transType)
                    || TransType.SHARE.getValue().equals(transType)
                    || TransType.TRANSFER.getValue().equals(transType)
                    || TransType.PLAY_MONEY.getValue().equals(transType)
                    || TransType.REAL_NAME.getValue().equals(transType)
                    || TransType.DEPOSIT_WITHDRAW.getValue().equals(transType)) {
                clearingCR.setFlag("Y");
            }
            // 通道编码
            String channelCode = clearingCR.getChannelCode();

            // 判断是否是组合命名
            int of = channelCode.lastIndexOf("-");
            if (of != -1) {
                logger.info("通道差错账查询channelCode为------>{}" + channelCode, of);
                String substring = channelCode.substring(of);
                String channelCode1 = channelCode.substring(0, of);
                String bankNo = substring.substring(1);

                //调取交易查询基础数据
                BankInfo bankInfo = null;
                try {
                    bankInfo = bankInfoService.getBankNameByBankNo(bankNo);
                    String bankName = bankInfo.getBankName();
                    // 通道编码 ChannelCode
                    if (StringUtils.isNotBlank(channelCode1)) {
                        //clearingCR.setBankNameChannel(ChannelPartner.labelOf(channelCode1));
                        clearingCR.setBankNameChannel(DictUtils.getDictLabel(channelCode1, CHANNEL_PARTNER, ""));
                    }
                    String name = clearingCR.getBankNameChannel() + "-" + bankName;
                    clearingCR.setChannelCode(name);

                } catch (Exception e) {
                    logger.error("通道差错账查询getBankNameByBankNo错误------>{}", e);
                }
            } else {
                // 通道编码 ChannelCode
                if (StringUtils.isNotBlank(clearingCR.getChannelCode())) {
                    //clearingCR.setChannelCode(ChannelPartner.labelOf(clearingCR.getChannelCode()));
                    clearingCR.setChannelCode(DictUtils.getDictLabel(clearingCR.getChannelCode(), CHANNEL_PARTNER, ""));

                }
            }

            // '差错状态（N：未记账 D：记账中 Y：已记账）'
            if (StringUtils.isNotBlank(clearingCR.getErrorStatus())) {
                clearingCR.setErrorStatus(DifferErrorStatus.labelOf(clearingCR.getErrorStatus()));
            }

            // 账单类型
            if (StringUtils.isNotBlank(clearingCR.getBillType())) {
                clearingCR.setBillType(DifferencesBillType.labelOf(clearingCR.getBillType()));
            }
            // 币种
            if (StringUtils.isNotBlank(clearingCR.getCurrency())) {
                clearingCR.setCurrency(BillingCurrency.labelOf(clearingCR.getCurrency()));
            }

            // 审核状态
            if (StringUtils.isNotBlank(clearingCR.getCheckStatus())) {
                clearingCR.setCheckStatus(SettleDifferCheckStatus.labelOf(clearingCR.getCheckStatus()));
            }

            // 通道类型
            if (StringUtils.isNotBlank(clearingCR.getChannelType())) {
                dataEntityChannelType.forEach(p -> {
                    if (p.getValue().equals(clearingCR.getChannelType())) {
                        clearingCR.setChannelType(p.getName());
                    }
                });
            }
            clearingCRList.add(clearingCR);
        }
        page.setList(clearingCRList);

        // '差错状态（N：未记账 D：记账中 Y：已记账）'
        List<EnumBean> billingBillStatus = Lists.newArrayList();
        for (DifferErrorStatus checkFlg : DifferErrorStatus.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            billingBillStatus.add(ct);
        }
        model.addAttribute("billingBillStatus", billingBillStatus);

        // 账单类型
        List<EnumBean> differencesBillType = Lists.newArrayList();
        for (DifferencesBillType checkFlg : DifferencesBillType.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            differencesBillType.add(ct);
        }

        model.addAttribute("differencesBillType", differencesBillType);
        model.addAttribute("page", page);
        model.addAttribute("settleDifferChannel", settleDifferChannel);

        logger.info("通道差错账查询结束------>", settleDifferChannel);
        return "modules/accounting/settleDifferChannels";

    }

    // 记录信息导出
    @RequiresPermissions("settle:SettleDifferChannel:view")
    @RequestMapping(value = "export")
    public void export(RedirectAttributes redirectAttributes, SettleDifferChannel SettleDifferChannel,
                       HttpServletRequest request, HttpServletResponse response) {

        List<Map<String, Object>> clearingCR = settleDifferChannelService.findListExcel(SettleDifferChannel);

        logger.info("通道差错账下载------>{}" + clearingCR);
        for (Map<String, Object> map : clearingCR) {
            SettleDifferChannel channelBankName = new SettleDifferChannel();
            // 通道名称
            String channelCode = (String) map.get("channelCode");

            int of = channelCode.lastIndexOf("-");
            if (of != -1) {
                // of = channelCode.lastIndexOf("-");
                String substring = channelCode.substring(of);
                String channelCode1 = channelCode.substring(0, of);
                String bankNo = substring.substring(1);

                BankInfo bankInfo = bankInfoService.getBankNameByBankNo(bankNo);
                String bankName = bankInfo.getBankName();

                // 通道编码 ChannelCode
                if (StringUtils.isNotBlank(channelCode1)) {
                    //channelBankName.setBankNameChannel(ChannelPartner.labelOf(channelCode1));
                    channelBankName.setBankNameChannel(DictUtils.getDictLabel(channelCode1, CHANNEL_PARTNER, ""));

                }
                // 生成组合名称
                String name = channelBankName.getBankNameChannel() + "-" + bankName;

                channelBankName.setBankName(name);
                map.put("bankName", channelBankName.getBankName());
            } else {
                // 通道编码 ChannelCode
                if (StringUtils.isNotBlank(channelCode)) {
                    //channelBankName.setBankName(ChannelPartner.labelOf(channelCode));
                    channelBankName.setBankName(DictUtils.getDictLabel(channelCode, CHANNEL_PARTNER, ""));
                    map.put("bankName", channelBankName.getBankName());
                }
            }

        }

        // 下载的标题行和表格插入对应的字段值
        String[] headerArray = {"通道合作方", "支付通道类型", "差错批次号", "差错日期", "交易订单号", "支付单号", "币种", "实际支付金额", "交易成本", "账单类型", "差错状态", "审核状态", "审核备注", "操作人", "操作时间"};
        String[] showField = {"bankName", "channelType", "errorBath", "errorDate", "transNo", "paymentId", "currency", "successAmount", "cost", "billType", "errorStatus", "checkStatus", "checkMessage", "updateAuthor", "dealTime"};

        try {
            excelController.exportExcel("通道差错汇总记录", headerArray, clearingCR, showField, request, response);
        } catch (Exception e) {
            logger.error("ClearingChannelRecordController list has a error:", e);
        }
    }


    // 账单类型修改
    @RequiresPermissions("settle:SettleDifferChannel:view")
    @RequestMapping(value = "toupdate/{differChanbillId}/{pageNo}")
    public String update(@PathVariable("differChanbillId") int differChanbillId,
                         @PathVariable("pageNo") int pageNo,
                         RedirectAttributes redirectAttributes,
                         Model model,
                         HttpServletRequest request) {

        //查询通道合作方的内容
        List<EnumBean> dataEntityChannelType = DictList.channelType();
        //支付通道类型
        EnumBean de = new EnumBean();
        de.setName(TransType.REAL_NAME.getContent());//"实名认证"
        de.setValue(TransType.REAL_NAME.getValue());//RENAME
        dataEntityChannelType.add(de);

        String referer = request.getHeader("referer");
        SettleDifferChannel settleDifferChannel = settleDifferChannelService.getEntityById(differChanbillId);

        logger.info("商户差错账查询开始------>{}" + settleDifferChannel);
        // '差错状态（N：未处理 D：处理中 Y：已处理）'
        if (StringUtils.isNotBlank(settleDifferChannel.getErrorStatus())) {
            settleDifferChannel.setErrorStatus(DifferErrorStatus.labelOf(settleDifferChannel.getErrorStatus()));
        }
        // 账单类型
        if (StringUtils.isNotBlank(settleDifferChannel.getBillType())) {
            settleDifferChannel.setBillType(DifferencesBillType.labelOf(settleDifferChannel.getBillType()));
        }
        // 币种
        if (StringUtils.isNotBlank(settleDifferChannel.getCurrency())) {
            settleDifferChannel.setCurrency(BillingCurrency.labelOf(settleDifferChannel.getCurrency()));
        }

        // 审核状态
        if (StringUtils.isNotBlank(settleDifferChannel.getCheckStatus())) {
            settleDifferChannel.setCheckStatus(SettleDifferCheckStatus.labelOf(settleDifferChannel.getCheckStatus()));
        }

        // 通道类型
        if (StringUtils.isNotBlank(settleDifferChannel.getChannelType())) {
            dataEntityChannelType.forEach(p -> {
                if (p.getValue().equals(settleDifferChannel.getChannelType())) {
                    settleDifferChannel.setChannelType(p.getName());
                }
            });
        }

        // 其他差异备注时的选项
        List<EnumBean> checkStatus = Lists.newArrayList();
        for (SettleDifferCheckStatus checkFlg : SettleDifferCheckStatus.values()) {
            EnumBean ct = new EnumBean();

            // 如果是 “未审核”的状态就不插入
            if (!checkFlg.getValue().equals(Constants.STATIC_N)) { // N
                ct.setValue(checkFlg.getValue());
                ct.setName(checkFlg.getContent());
                checkStatus.add(ct);
            }
        }

        model.addAttribute("referer", referer);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("checkStatus", checkStatus);
        model.addAttribute("settleDifferChannel", settleDifferChannel);

        logger.info("商户差错账查询结束------>", settleDifferChannel);
        return "modules/accounting/settleDifferChannelsDealResult";
    }

    // 修改的方法
    @RequiresPermissions("settle:SettleDifferChannel:edit")
    @RequestMapping(value = "save/{differMerbillId}")
    public String updateEntity(SettleDifferChannel SettleDifferChannel, RedirectAttributes redirectAttributes,
                               HttpServletRequest request, @PathVariable("differMerbillId") long differMerbillId) throws ParseException {

        logger.info("商户差错账更新开始------>{}" + SettleDifferChannel);

        SettleDifferChannel.setDifferChanbillId(differMerbillId);
        SettleDifferChannel.setDealTime(new Date());
        SettleDifferChannel.setErrorStatus(BillingBillStatus.BBSTATUSD.getValue());
        //操作人
        SettleDifferChannel.setUpdateAuthor(UserUtils.getUser().getName());

        try {
            settleDifferChannelService.updateEntity(SettleDifferChannel);
            addMessage(redirectAttributes, "操作成功!");
        } catch (Exception e) {
            addMessage(redirectAttributes, "操作失败!" + e.getMessage());
        }

        return "redirect:" + Global.getAdminPath() + "/accounting/settleDifferChannels";
    }

    // 再次记账
    @RequiresPermissions("settle:SettleDifferChannel:edit")
    @RequestMapping(value = "fillAgain/{differChanbillId}/{pageNo}")
    public String fillBillAgain(@PathVariable("differChanbillId") int differChanbillId,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {
        SettleDifferChannel settleDifferChannel = settleDifferChannelService.getEntityById(differChanbillId);
        try {
            logger.info("通道侧再次记账开始------>{}", settleDifferChannel.getPaymentId());
            // 交易类型
            SettleDifferRecord settleDifferRecord = settleDifferRrecordService.getSettleDifferRecordByTransNo(settleDifferChannel.getTransNo());
            doChannelFillBill(settleDifferChannel, settleDifferRecord);
            addMessage(redirectAttributes, "操作成功!");
        } catch (Exception e) {
            logger.info("通道侧再次记账出错------>{}", settleDifferChannel.getPaymentId());
            addMessage(redirectAttributes, "操作失败!" + e.getMessage());
        }

        logger.info("通道侧再次记账结束------>{}", settleDifferChannel.getPaymentId());
        return "redirect:" + Global.getAdminPath() + "/accounting/settleDifferChannels";

    }

    /**
     * @param settleDifferChannel
     * @param settleDifferRecord
     * @方法说明：通道侧补账方法
     * @author chenyanming
     * @时间：2016年11月15日下午5:47:39
     */
    public void doChannelFillBill(SettleDifferChannel settleDifferChannel, SettleDifferRecord settleDifferRecord) {
        String feeAmount = null;
        // 银行对账文件中没有手续费字段，如果出现差异账，通道侧调补账接口手续费字段默认传0值
        if (settleDifferChannel.getCost() == null) {
            feeAmount = "0";
        } else {
            feeAmount = settleDifferChannel.getCost().toString();
        }
        if (TransType.CHARGE.getValue().equals(settleDifferRecord.getTransType())) {
            // 充值通道侧补账
            int flag = settleDifferFillBillClient.doChargeChannelFillBill(settleDifferChannel.getChannelCode(), null, settleDifferChannel.getPaymentId(),
                    settleDifferChannel.getTransNo(), settleDifferChannel.getErrorBath(), settleDifferChannel.getSuccessAmount().toString(), feeAmount);
            if (InterfaceStatus.SUCCESS.getValue() == flag) {
                // 充值通道侧补账成功
                settleDifferChannelService.updateErrorStatusById(settleDifferChannel.getDifferChanbillId());
                logger.info("充值通道侧补账完成！交易单号为{}", settleDifferChannel.getTransNo());
            } else if (InterfaceStatus.COMPLETED.getValue() == flag) {
                // 账务系统已记账
                settleDifferChannelService.updateErrorStatusById(settleDifferChannel.getDifferChanbillId());
                logger.info("充值通道侧账务系统已记账！交易单号为{}", settleDifferChannel.getTransNo());
            } else {
                logger.info("充值通道侧补账失败！交易单号和返回状态码为{},{}", settleDifferChannel.getTransNo(), flag);
            }
        } else if (TransType.PAY.getValue().equals(settleDifferRecord.getTransType())) {
            // 消费通道侧补账
            int flag = settleDifferFillBillClient.doPayChannelFillBill(settleDifferChannel.getChannelCode(), settleDifferChannel.getPaymentId(),
                    settleDifferChannel.getTransNo(), settleDifferChannel.getErrorBath(), null, settleDifferChannel.getSuccessAmount().toString(), feeAmount);
            if (InterfaceStatus.SUCCESS.getValue() == flag) {
                // 消费通道侧补账成功
                settleDifferChannelService.updateErrorStatusById(settleDifferChannel.getDifferChanbillId());
                logger.info("消费通道侧补账完成！交易单号为{}", settleDifferChannel.getTransNo());
            } else if (InterfaceStatus.COMPLETED.getValue() == flag) {
                // 账务系统已记账
                settleDifferChannelService.updateErrorStatusById(settleDifferChannel.getDifferChanbillId());
                logger.info("消费通道侧账务系统已记账！交易单号为{}", settleDifferChannel.getTransNo());
            } else {
                logger.info("消费通道侧补账失败！交易单号和返回状态码为{},{}", settleDifferChannel.getTransNo(), flag);
            }
        } else if (TransType.BATCHCOLLECTION.getValue().equals(settleDifferRecord.getTransType())) {
            // 代收通道补账
            int flag = settleDifferFillBillClient.doBatcolChannelFillBill(settleDifferChannel.getChannelCode(), settleDifferChannel.getPaymentId(),
                    settleDifferChannel.getTransNo(), settleDifferChannel.getErrorBath(), null, settleDifferChannel.getSuccessAmount().toString(), feeAmount);
            if (InterfaceStatus.SUCCESS.getValue() == flag) {
                // 代收通道侧补账成功
                settleDifferChannelService.updateErrorStatusById(settleDifferChannel.getDifferChanbillId());
                logger.info("代收通道侧补账完成！交易单号为{}", settleDifferChannel.getTransNo());
            } else if (InterfaceStatus.COMPLETED.getValue() == flag) {
                // 账务系统已记账
                settleDifferChannelService.updateErrorStatusById(settleDifferChannel.getDifferChanbillId());
                logger.info("代收通道侧账务系统已记账！交易单号为{}", settleDifferChannel.getTransNo());
            } else {
                logger.info("代收通道侧补账失败！交易单号和返回状态码为{},{}", settleDifferChannel.getTransNo(), flag);
            }
        } else if (TransType.DEPOSIT_PAY.getValue().equals(settleDifferRecord.getTransType())) {
            // 存管充值通道补账
            int flag = billingAccountClient.doDepositPayChannelFillBill(settleDifferChannel.getChannelCode(), settleDifferChannel.getPaymentId(),
                    settleDifferChannel.getTransNo(), settleDifferChannel.getErrorBath(), null, settleDifferChannel.getSuccessAmount().toString(), feeAmount);
            if (InterfaceStatus.SUCCESS.getValue() == flag) {
                // 存管充值通道侧补账成功
                settleDifferChannelService.updateErrorStatusById(settleDifferChannel.getDifferChanbillId());
                logger.info("存管充值通道侧补账完成！交易单号为{}", settleDifferChannel.getTransNo());
            } else if (InterfaceStatus.COMPLETED.getValue() == flag) {
                // 账务系统已记账
                settleDifferChannelService.updateErrorStatusById(settleDifferChannel.getDifferChanbillId());
                logger.info("存管充值通道侧账务系统已记账！交易单号为{}", settleDifferChannel.getTransNo());
            } else {
                logger.info("存管充值通道侧补账失败！交易单号和返回状态码为{},{}", settleDifferChannel.getTransNo(), flag);
            }
        }

    }

}







