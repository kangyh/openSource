package com.heepay.manage.modules.merchant.rpc.client;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.AccountType;
import com.heepay.enums.BankcardType;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.ProductType;
import com.heepay.manage.modules.merchant.service.MerchantBankCardCService;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;
import com.heepay.manage.modules.merchant.vo.MerchantBankCardAuthentication;
import com.heepay.manage.modules.payment.producer.QueuePorducerSender;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.payment.model.TransferQualificationRequestVO;
import com.heepay.rpc.payment.model.TransferQualificationResponseVO;
import com.heepay.rpc.payment.service.TransferQualificationService;
import com.heepay.vo.ClearChannelRecordVO;
import com.heepay.vo.ClearMerchantRecordVO;

/**
 * 名称：商户client
 * <p>
 * 创建者  Ly
 * 创建时间 2016-10-19 16:58:32
 * 创建描述：查询商户信息用
 */
@Service
public class TransferQualificationClient extends BaseClient {

    private static final String SERVICENAME = "TransferQualificationService";
    private static final String NODENAME = "payment_rpc";

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private MerchantBankCardCService merchantBankCardCService;

    @Autowired
    private MerchantCService merchantCService;

    @Autowired
    QueuePorducerSender myQueuePorducerSender;

    private TransferQualificationService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new TransferQualificationService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    public String qualification(MerchantBankCardAuthentication merchantBankCardAuthentication) {
        TransferQualificationRequestVO qualification = new TransferQualificationRequestVO();
        TransferQualificationResponseVO qualificationResponseVO = new TransferQualificationResponseVO();
        //获取银行卡信息
        MerchantBankCard merchantBankCard = merchantBankCardCService.getMerchant(merchantBankCardAuthentication.getMerchantId());
        //获取商户信息
        Merchant merchant = merchantCService.get(merchantBankCardAuthentication.getMerchantId());
        //解密银行卡
        String bankNo = Aes.decryptStr(merchantBankCard.getBankNo(), Constants.QuickPay.SYSTEM_KEY);
        //对公
        qualification.setBankId(merchantBankCard.getBankId());
        qualification.setBankcardNo(bankNo);
        qualification.setAssociateLineNumber(merchantBankCard.getAssociateLineNumber());
        qualification.setOpenBankName(merchantBankCard.getOpenBankName());
        qualification.setBankcardType(BankcardType.SAVINGCARD.getValue());
        qualification.setPayAmount(String.valueOf(merchantBankCardAuthentication.getPayAmount()));
        qualification.setMerchantId(merchantBankCardAuthentication.getMerchantId());
        qualification.setMerchantName(merchantBankCardAuthentication.getMerchantCompanyName());
        qualification.setMerchantLoginName(merchant.getEmail());
        qualification.setBankName(merchantBankCard.getBankName());
        qualification.setAccountType(AccountType.PUBLIC.getValue());
//      //对私
//      qualification.setBankId("308");
//      qualification.setBankcardNo("6225881004359815");
//      qualification.setAssociateLineNumber("308100005221");
//      qualification.setOpenBankName("招商银行股份有限公司北京大运村支行");
//      qualification.setBankcardType(BankcardType.SAVINGCARD.getValue());
//      qualification.setPayAmount(String.valueOf(merchantBankCardAuthentication.getPayAmount()));
//      qualification.setMerchantId(merchantBankCardAuthentication.getMerchantId());
//      qualification.setMerchantName("李金徽");
//      qualification.setMerchantLoginName("ljh@9186.com");
//      qualification.setBankName("招商银行");
//      qualification.setAccountType(AccountType.PRIVAT.getValue());
        //对公
//      qualification.setBankId("105");
//      qualification.setBankcardNo("11050161600000000029");
//      qualification.setAssociateLineNumber("308100005221");
//      qualification.setOpenBankName("建行北京工商大厦支行");
//      qualification.setBankcardType(BankcardType.SAVINGCARD.getValue());
//      qualification.setPayAmount(String.valueOf(merchantBankCardAuthentication.getPayAmount()));
//      qualification.setMerchantId(merchantBankCardAuthentication.getMerchantId());
//      qualification.setMerchantName("北京汇元友邦科技有限公司");
//      qualification.setMerchantLoginName("ljh@9186.com");
//      qualification.setBankName("中国建设银行");
        try {
            qualificationResponseVO = getClient().qualification(qualification);
            logger.info("打款认证返回{}", qualificationResponseVO);
            if (String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(qualificationResponseVO.getResponseCode())) {
                try {
                    //通知清结算
                    notifySettle(qualificationResponseVO);
                } catch (Exception e) {
                    logger.error("打款认证,写清结算队列异常，{}", e);
                }
                return Boolean.TRUE.toString();
            } else {
                return qualificationResponseVO.getResponseMsg();
            }
        } catch (TException e) {
            logger.error("打款认证异常，{}", e);
            return "系统异常";
        } finally {
            this.close();
        }
    }

    /**
     * @param qualificationResponseVO
     * @discription 写队列通知清结算
     * @author yanxb
     * @created 2016年11月17日 下午7:40:11
     */
    private boolean notifySettle(TransferQualificationResponseVO qualificationResponseVO) {
        logger.info("打款认证，写队列通知清结算开始");
        //通道侧
        ClearChannelRecordVO clearChannelRecordVO = new ClearChannelRecordVO();
        clearChannelRecordVO.setChannelCode(StringUtil.isBlank(qualificationResponseVO.getPayChannelCode()) ? "" : qualificationResponseVO.getPayChannelCode());
        clearChannelRecordVO.setCurrency(qualificationResponseVO.getCurrency());
        clearChannelRecordVO.setPaymentId(qualificationResponseVO.getPaymentId());
        clearChannelRecordVO.setPayTime(qualificationResponseVO.getPayTime());
        clearChannelRecordVO.setSuccessAmount(qualificationResponseVO.getPayAmount());
        clearChannelRecordVO.setTransNo(qualificationResponseVO.getTransNo());
        clearChannelRecordVO.setTransType(qualificationResponseVO.getTransType());
        clearChannelRecordVO.setPayAmount(String.valueOf(new BigDecimal(qualificationResponseVO.getPayAmount())));
        //商户侧
        ClearMerchantRecordVO clearMerchantRecordVO = new ClearMerchantRecordVO();
        clearMerchantRecordVO.setCurrency(qualificationResponseVO.getCurrency());
        clearMerchantRecordVO.setMerchantCompany(qualificationResponseVO.getMerchantCompany());
        clearMerchantRecordVO.setMerchantId(String.valueOf(qualificationResponseVO.getMerchantId()));
        clearMerchantRecordVO.setMerchantOrderNo(qualificationResponseVO.getMerchantOrderNo());
        clearMerchantRecordVO.setPaymentID(qualificationResponseVO.getPaymentId());
        clearMerchantRecordVO.setPayTime(qualificationResponseVO.getPayTime());
        clearMerchantRecordVO.setProductCode(ProductType.PLAYMONEY.getValue());
        clearMerchantRecordVO.setRequestAmount(String.valueOf(new BigDecimal(qualificationResponseVO.getPayAmount())));
        clearMerchantRecordVO.setTransNo(qualificationResponseVO.getTransNo());
        clearMerchantRecordVO.setTransType(qualificationResponseVO.getTransType());
        clearMerchantRecordVO.setSuccessAmount(qualificationResponseVO.getPayAmount());
        clearMerchantRecordVO.setSuccessTime(qualificationResponseVO.getSuccessTime());
        JsonMapperUtil json = new JsonMapperUtil();
        clearMerchantRecordVO.setFeeAmount("0");
        logger.info("打款认证,通道侧参数，channelCode={},currency={},paymentId={},payTime={},successAmount={},"
                        + "transNo={},transType={},payAmount={}", clearChannelRecordVO.getChannelCode(), clearChannelRecordVO.getCurrency(),
                clearChannelRecordVO.getPaymentId(), clearChannelRecordVO.getPayTime(), clearChannelRecordVO.getSuccessAmount(),
                clearChannelRecordVO.getTransNo(), clearChannelRecordVO.getTransType(), clearChannelRecordVO.getPayAmount());
        logger.info("打款认证,商户侧参数，currency={},merchantCompany={},merchantId={},merchantOrderNo={},"
                        + "paymentId={},payTime={},productCode={},requestAmount={},transNo={},transType={},successAmount={},"
                        + "successTime={}", clearMerchantRecordVO.getCurrency(), clearMerchantRecordVO.getMerchantCompany(),
                clearMerchantRecordVO.getMerchantId(), clearMerchantRecordVO.getMerchantOrderNo(),
                clearMerchantRecordVO.getPaymentID(), clearMerchantRecordVO.getPayTime(), clearMerchantRecordVO.getProductCode(),
                clearMerchantRecordVO.getRequestAmount(), clearMerchantRecordVO.getTransNo(), clearMerchantRecordVO.getTransType(),
                clearMerchantRecordVO.getSuccessAmount(), clearMerchantRecordVO.getSuccessTime());
        try {
            myQueuePorducerSender.sendDataToQueueHyBillingClearChannelqueue(json.toJson(clearChannelRecordVO));
            logger.info("打款认证,通知清结算通道侧完成");
            myQueuePorducerSender.sendDataToQueueHyBillingClearMerchantqueue(json.toJson(clearMerchantRecordVO));
            logger.info("打款认证,通知清结算商户侧完成");
        } catch (Exception e) {
            logger.error("打款认证,写队列通知清结算出错！{}", e.getMessage());
            return false;
        } finally {
            this.close();
        }
        logger.info("打款认证,通知清结算完成");
        return true;
    }

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }
}