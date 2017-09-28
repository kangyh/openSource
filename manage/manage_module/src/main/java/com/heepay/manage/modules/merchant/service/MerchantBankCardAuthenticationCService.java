package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantBankCardAuthentication;

public interface MerchantBankCardAuthenticationCService {

    public MerchantBankCardAuthentication get(String id);

    public List<MerchantBankCardAuthentication> findList(MerchantBankCardAuthentication merchantBankCardAuthentication);

    public Page<MerchantBankCardAuthentication> findPage(Page<MerchantBankCardAuthentication> page,
            MerchantBankCardAuthentication merchantBankCardAuthentication);

    public void save(MerchantBankCardAuthentication merchantBankCardAuthentication, boolean flag);

    public void delete(MerchantBankCardAuthentication merchantBankCardAuthentication);

    public void status(MerchantBankCardAuthentication merchantBankCardAuthentication);

    public void successMerchnat(MerchantBankCardAuthentication merchantBankCardAuthentication);

    public MerchantBankCardAuthentication getByMerchantId(
            MerchantBankCardAuthentication merchantBankCardAuthentication);

    public void statusAut(MerchantBankCardAuthentication merchantBankCardAuthentication);
}
