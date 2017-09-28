package com.heepay.rpc.billing.service;

import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.ClearMerchantRecord;

public interface ISendMailService {
	
	public boolean settleDifferRecordChannel(String tranNo,ClearChannelRecord clearChannelRecord);
	
	
	public boolean settleDifferRecordMerchant(String tranNo,ClearMerchantRecord clearMerchantRecord);
	
	public boolean settleDifferRecord(ClearChannelRecord clearChannelRecord,ClearMerchantRecord clearMerchantRecord);

}
