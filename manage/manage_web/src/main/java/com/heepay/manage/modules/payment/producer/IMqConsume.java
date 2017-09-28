package com.heepay.manage.modules.payment.producer;

import org.springframework.stereotype.Component;

import com.heepay.rpc.payment.model.MqHandleModel;
import com.heepay.vo.ClearChannelRecordVO;
import com.heepay.vo.ClearMerchantRecordVO;

@Component
public interface IMqConsume {
  public void chargeHandle(ClearChannelRecordVO clearChannelRecordVO,ClearMerchantRecordVO clearMerchantRecordVO);
  public void withdrawHandle(String transType,ClearChannelRecordVO clearChannelRecordVO,ClearMerchantRecordVO clearMerchantRecordVO);
  public void batchPayHandle(ClearChannelRecordVO clearChannelRecordVO,ClearMerchantRecordVO clearMerchantRecordVO);
  public void payHandle(MqHandleModel mqHandleModel);
}
