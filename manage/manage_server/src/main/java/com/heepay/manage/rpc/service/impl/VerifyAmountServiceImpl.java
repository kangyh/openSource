package com.heepay.manage.rpc.service.impl;

import java.math.BigDecimal;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.DateUtil;
import com.heepay.manage.common.utils.AmountChangeUtil;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.service.MerchantBankCardAuthenticationCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantBankCardAuthentication;
import com.heepay.manage.rpc.service.VerifyAmountService;
import com.heepay.manage.rpc.service.VerifyAmountThrift;
import com.heepay.rpc.service.RpcService;

@Component
@RpcService(name = "VerifyAmountServiceImpl", processor = VerifyAmountService.Processor.class)
public class VerifyAmountServiceImpl implements VerifyAmountService.Iface {

  @Autowired
  private MerchantBankCardAuthenticationCService merchantBankCardAuthenticationCService;
  
  @Autowired
  private MerchantDao merchantDao;
  
  @Override
  public String verifyAmount(String amount, String merchantId) throws TException {
    MerchantBankCardAuthentication merchantBankCardAuthentication = new MerchantBankCardAuthentication();
    merchantBankCardAuthentication.setMerchantId(merchantId);
    merchantBankCardAuthentication = merchantBankCardAuthenticationCService.getByMerchantId(merchantBankCardAuthentication);
    BigDecimal a = AmountChangeUtil.change(merchantBankCardAuthentication.getPayAmount());
    BigDecimal b = new BigDecimal(amount);
    return a.compareTo(b) == 0 ? "TRUE":"FALSE";
  }

  @Override
  public String editStatus(String merchantId, String status, String amount) throws TException {
    MerchantBankCardAuthentication merchantBankCardAuthentication = new MerchantBankCardAuthentication();
    merchantBankCardAuthentication.setMerchantId(merchantId);
    merchantBankCardAuthentication.setAuthenticationStatus(status);
    merchantBankCardAuthentication.setAuthenticationAmount(new BigDecimal(amount));
    merchantBankCardAuthenticationCService.statusAut(merchantBankCardAuthentication);
    //修改银行卡状态
    return "true";
  }

  @Override
  public VerifyAmountThrift getVerifyAmount(String merchantId) throws TException {
    VerifyAmountThrift verifyAmountThrift = new VerifyAmountThrift();
    MerchantBankCardAuthentication merchantBankCardAuthentication = new MerchantBankCardAuthentication();
    merchantBankCardAuthentication.setMerchantId(merchantId);
    merchantBankCardAuthentication = merchantBankCardAuthenticationCService.getByMerchantId(merchantBankCardAuthentication);
    verifyAmountThrift.setBankCardNo(merchantBankCardAuthentication.getBankNo());
    verifyAmountThrift.setOpenBankName(merchantBankCardAuthentication.getOpenBankName());
    verifyAmountThrift.setCreateTime(
        DateUtil.dateToString(merchantBankCardAuthentication.getCreateDate(),"yyyy年MM月dd日"));
    return verifyAmountThrift;
  }

}
