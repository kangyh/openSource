package com.heepay.tpds.dao;
import java.util.List;

import com.heepay.tpds.entity.TpdsMerchantAccount;


public interface TpdsMerchantAccountMapper {
   
	TpdsMerchantAccount selectByMerchantId(String MerchantId);
    TpdsMerchantAccount selectBySysNoAndBankNo(TpdsMerchantAccount entity);
    int insert(TpdsMerchantAccount entity);
    int update(TpdsMerchantAccount entity);
    List<TpdsMerchantAccount> getList();
    TpdsMerchantAccount selectBankCardBranchIdBySystemNo(String SystemNo);
    List<TpdsMerchantAccount> selectAccountRecordByStatus(String status);
	TpdsMerchantAccount selectAccountRecordByStatusSystemNo(String status, String SystemNo);
}