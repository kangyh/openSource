package com.heepay.manage.modules.payment.web;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.*;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.vo.MerchantsUser;
import com.heepay.manage.modules.payment.entity.BindCardAuth;
import com.heepay.manage.modules.payment.entity.InnerTransferRecord;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.WalletRecord;
import com.heepay.manage.modules.payment.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * 描    述：汇付宝个人钱包
 *
 * 创 建 者： liudh
 * 创建时间： 2017/6/6 14:00
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
@RequestMapping(value = "${adminPath}/payment/wallet")
public class WalletController extends BaseController{

    private static final Logger log = LogManager.getLogger();
    @Autowired
    private InnerTransferRecordService innerTransferRecordService;
    @Autowired
    private PaymentRecordService paymentRecordService;
    @Autowired
    private BindCardService bindCardService;
    @Autowired
    private MerchantsUserService merchantsUserService;
    @Autowired
    private WalletRecordService walletRecordService;

    /**
     * 转账记录
     * @param innerTransferRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("payment:walletInnerTransfer:view")
    @RequestMapping(value = "transferList")
    public String transferList(InnerTransferRecord innerTransferRecord, HttpServletRequest request, HttpServletResponse response, Model model) {

        //转账
        innerTransferRecord.setProductCode(ProductType.WALLET_TRANSFER.getValue());
        Page<InnerTransferRecord> page = findPage(innerTransferRecord, request, response);
        model.addAttribute("page", page);
        return "modules/payment/walletInnerTransferList";
    }

    /**
     * 余额支付记录
     * @param innerTransferRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("payment:walletPayment:view")
    @RequestMapping(value = "paymentList")
    public String paymentList(InnerTransferRecord innerTransferRecord, HttpServletRequest request, HttpServletResponse response, Model model) {

        //支付
        innerTransferRecord.setProductCode(ProductType.WALLET_PAY.getValue());
        Page<InnerTransferRecord> page = findPage(innerTransferRecord, request, response);
        model.addAttribute("page", page);
        return "modules/payment/walletPaymentList";
    }


    public Page<InnerTransferRecord> findPage(InnerTransferRecord innerTransferRecord, HttpServletRequest request, HttpServletResponse response){
        if("R1".equals(innerTransferRecord.getStatus())){
            innerTransferRecord.setStatus(null);
        }
        Page<InnerTransferRecord> pageInfo = new Page<>(request, response);
        pageInfo.setOrderBy("create_time desc");
        Page<InnerTransferRecord> page = innerTransferRecordService.findPage(pageInfo, innerTransferRecord);
        return page;
    }

    /**
     * 提现记录
     * @param paymentRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("payment:walletWithdrawDeposit:view")
    @RequestMapping(value = "withdrawDepositList")
    public String withdrawDepositList(PaymentRecord paymentRecord, HttpServletRequest request, HttpServletResponse response, Model model) {

        paymentRecord.setProductCode(ProductType.WALLET_WITHDRAW.getValue());
        paymentRecord.setTransType(TransType.WITHDRAW.getValue());
        paymentRecord.setPayType(PayType.BATPAY.getValue());
        if("R1".equals(paymentRecord.getStatus())){
            paymentRecord.setStatus(null);
        }
        Page<PaymentRecord> paymentRecordPage = new Page<>(request, response);
        paymentRecordPage.setOrderBy("create_time desc");
        Page<PaymentRecord> page = paymentRecordService.findWithdrawDepositPage(paymentRecordPage, paymentRecord);
        model.addAttribute("page", page);
        return "modules/payment/walletWithdrawDepositList";
    }

    /**
     * 充值记录
     * @param paymentRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("payment:walletRecharge:view")
    @RequestMapping(value = "rechargeList")
    public String rechargeList(PaymentRecord paymentRecord, HttpServletRequest request, HttpServletResponse response, Model model) {

        paymentRecord.setProductCode(ProductType.WALLET_CHARGE.getValue());
        paymentRecord.setTransType(TransType.CHARGE.getValue());
        if("R1".equals(paymentRecord.getStatus())){
            paymentRecord.setStatus(null);
        }
        Page<PaymentRecord> paymentRecordPage = new Page<>(request, response);
        paymentRecordPage.setOrderBy("create_time desc");
        Page<PaymentRecord> page = paymentRecordService.findWithdrawDepositPage(paymentRecordPage, paymentRecord);
        model.addAttribute("page", page);
        return "modules/payment/walletRechargeList";
    }

    /**
     * 绑卡记录
     * @param bindCardAuth
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("payment:walletBindCard:view")
    @RequestMapping(value = "bindCardList")
    public String bindCardList(BindCardAuth bindCardAuth, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<BindCardAuth> bankcardAuthPage = new Page<>(request, response);
        bankcardAuthPage.setOrderBy("create_time desc");
        //钱包type
        bindCardAuth.setType("WALLET");
        if("R1".equals(bindCardAuth.getStatus())){
            bindCardAuth.setStatus(null);
        }
        Page<BindCardAuth> page = bindCardService.findPage(bankcardAuthPage,bindCardAuth);
        //处理4要素
        page.getList().forEach(cardAuth ->{
            if(!StringUtil.isBlank(cardAuth.getBankcardNo())){
                String bankCardNo = Aes.decryptStr(cardAuth.getBankcardNo(), Constants.QuickPay.SYSTEM_KEY);
                cardAuth.setBankcardNo(StringUtil.getEncryptedCardNo(bankCardNo));
            }
            if(!StringUtil.isBlank(cardAuth.getBankcardOwnerMobile())){
                String bankMobile = Aes.decryptStr(cardAuth.getBankcardOwnerMobile(), Constants.QuickPay.SYSTEM_KEY);
                cardAuth.setBankcardOwnerMobile(bankMobile.replaceFirst(bankMobile.substring(3,bankMobile.length()-4),"****"));
            }
            if(!StringUtil.isBlank(cardAuth.getBankcardOwnerIdcard())){
                String bankIdCard = Aes.decryptStr(cardAuth.getBankcardOwnerIdcard(), Constants.QuickPay.SYSTEM_KEY);
                cardAuth.setBankcardOwnerIdcard(StringUtil.getEncryptedIdcard(bankIdCard));
            }
            if(!StringUtil.isBlank(cardAuth.getBankcardOwnerName())){
                String name = cardAuth.getBankcardOwnerName();
                cardAuth.setBankcardOwnerName(name.replaceFirst(name.substring(name.length()>=3?1:0, name.length()-1), "*"));
            }
        });
        model.addAttribute("page", page);
        return "modules/payment/walletBindCardList";
    }


    /**
     * 商户用户注册记录
     * @param merchantsUser
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("payment:walletMerchantsUser:view")
    @RequestMapping(value = "merchantsUserList")
    public String merchantsUserList(MerchantsUser merchantsUser, HttpServletRequest request, HttpServletResponse response, Model model) {


        Page<MerchantsUser> page = new Page<>(request, response);
        page.setOrderBy("create_time desc");
        Page<MerchantsUser> pageList = merchantsUserService.findPage(page, merchantsUser);
        pageList.getList().forEach(user ->{
            if(StringUtil.notBlank(user.getRealnameAuthSign())){
                user.setRealnameAuthSign(CommonStatus.labelOf(user.getRealnameAuthSign()));
            }
            if(StringUtil.notBlank(user.getBankcardAuthSign())){
                user.setBankcardAuthSign(CommonStatus.labelOf(user.getBankcardAuthSign()));
            }
            if(StringUtil.notBlank(user.getStatus())){
                user.setStatus(SubjectStatus.labelOf(user.getStatus()));
            }
            if(StringUtil.notBlank(user.getPhone())){
                String phone =  user.getPhone();
                user.setPhone(phone.replaceFirst(phone.substring(3,phone.length()-4),"****"));
            }
            if(StringUtil.notBlank(user.getLoginName())){
                String loginName =  user.getLoginName();
                user.setLoginName(loginName.replaceFirst(loginName.substring(3,loginName.length()-4),"****"));
            }
            if(StringUtil.notBlank(user.getRealName())){
                String name = user.getRealName();
                user.setRealName(name.replaceFirst(name.substring(name.length()>=3?1:0, name.length()-1), "*"));
            }
        });

        model.addAttribute("page", page);

        return "modules/payment/walletMerchantsUserList";
    }

    /**
     * 微信、支付宝记录
     * @param paymentRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("payment:walletAggrPay:view")
    @RequestMapping(value = "aAggrPayList")
    public String aggrPayList(PaymentRecord paymentRecord, HttpServletRequest request, HttpServletResponse response, Model model) {

        if("R1".equals(paymentRecord.getStatus())){
            paymentRecord.setStatus(null);
        }
        Page<PaymentRecord> paymentRecordPage = new Page<>(request, response);
        paymentRecordPage.setOrderBy("create_time desc");
        Page<PaymentRecord> pageList = paymentRecordService.findAggrPayPage(paymentRecordPage, paymentRecord);
        for (PaymentRecord record : pageList.getList()) {
            record.setDescription(ProductType.getContentByValue(record.getProductCode()));
        }
        model.addAttribute("page", pageList);
        return "modules/payment/walletAggrPayList";
    }

    /**
     * 银行卡支付记录
     * @param paymentRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("payment:walletBankCardPayList:view")
    @RequestMapping(value = "bankCardPayList")
    public String bankCardPayList(PaymentRecord paymentRecord, HttpServletRequest request, HttpServletResponse response, Model model) {

        if("R1".equals(paymentRecord.getStatus())){
            paymentRecord.setStatus(null);
        }
        Page<PaymentRecord> paymentRecordPage = new Page<>(request, response);
        paymentRecordPage.setOrderBy("create_time desc");
        Page<PaymentRecord> pageList = paymentRecordService.findQuickPage(paymentRecordPage, paymentRecord);
        model.addAttribute("page", pageList);
        return "modules/payment/walletBankCardPayList";
    }

    /**
     * 差异管理
     * @param walletRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("payment:WalletDifferenceBill:view")
    @RequestMapping(value = "differenceBillList")
    public String differenceBillList(WalletRecord walletRecord,HttpServletRequest request, HttpServletResponse response, Model model) {

        List<String> transNo = new ArrayList<>();
        List<String> status = new ArrayList<>();
        List<WalletRecord> resultList = new ArrayList<>();
        if("R1".equals(walletRecord.getStatus())){
            walletRecord.setStatus(null);
        }
        List<WalletRecord> walletList = walletRecordService.findDifBillList(walletRecord);
        for (WalletRecord record : walletList) {
            transNo.add(record.getTransNo());
            status.add(record.getStatus());
        }
        List<WalletRecord> filWalletList = new ArrayList<>();
        if(transNo.size()>0){
            List<PaymentRecord> aggrPayList = paymentRecordService.findAggrPayList(transNo);
            for (WalletRecord record : walletList) {
                for (PaymentRecord paymentRecord : aggrPayList) {
                    if(record.getTransNo().equals(paymentRecord.getTransNo())){
                        if(!record.getStatus().equals(paymentRecord.getStatus())){
                            resultList.add(record);
                        }else{
                            filWalletList.add(record);
                        }
                    }
                }
            }
            List<InnerTransferRecord> payList = innerTransferRecordService.findTransferList(transNo);
            for (WalletRecord record : filWalletList) {
                for (InnerTransferRecord innerTransferRecord : payList) {
                    if(record.getTransNo().equals(innerTransferRecord.getInnerTransferId())){
                        if(!record.getStatus().equals(innerTransferRecord.getStatus())){
                            resultList.add(record);
                        }
                    }
                }
            }
        }
        model.addAttribute("page", resultList);
        return "modules/payment/walletDifferenceBillList";

    }


    /**
     * 跳转页面
     * @param walletRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("payment:WalletDifferenceBill:view")
    @RequestMapping(value = "toEditPage")
    public String toEditPage(WalletRecord walletRecord,HttpServletRequest request, HttpServletResponse response, Model model) {

        List<WalletRecord> list = walletRecordService.findList(walletRecord);
        if(list!=null && list.size()>0){
            walletRecord = list.get(0);
        }
        PaymentRecord payment = new PaymentRecord();
        payment.setTransNo(walletRecord.getTransNo());
        payment.setPayAmount(walletRecord.getPayAmount());
        payment.setProductCode(walletRecord.getProductCode());

        List<PaymentRecord> listByTransNo = paymentRecordService.findListByPayment(payment);
        for (PaymentRecord paymentRecord : listByTransNo) {
            walletRecord.setSuccessTime(paymentRecord.getSuccessTime());
            walletRecord.setSuccessAmount(paymentRecord.getSuccessAmount());
            walletRecord.setStatus(paymentRecord.getStatus());
        }
        log.info("钱包差异管理 查询返回{}",walletRecord);
        model.addAttribute("walletRecord",walletRecord);

        return "modules/payment/walletDifferenceBillEdit";

    }

    /**
     * 修改订单状态
     * @param walletRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("payment:WalletDifferenceBill:edit")
    @RequestMapping(value = "editDifBill")
    public String editDifBill(WalletRecord walletRecord,HttpServletRequest request, HttpServletResponse response, Model model) {

        String successTimes = request.getParameter("successTimes");
        log.info("钱包差异管理 修改订单状态 开始{},成功时间{}",walletRecord,successTimes);
        if(StringUtil.notBlank(successTimes))
        walletRecord.setSuccessTime(DateUtil.stringToDate(successTimes));
        if(walletRecord!=null && walletRecord.getTransNo()!=null){
            walletRecordService.updateStatusByTransNo(walletRecord);
        }
        log.info("钱包差异管理 修改订单状态 结束{}",walletRecord);
        return "redirect:"+ Global.getAdminPath()+"/payment/wallet/differenceBillList";
    }
}
