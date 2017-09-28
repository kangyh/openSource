/**
 *
 */
package com.heepay.manage.modules.merchant.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.Merchant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商户Service
 *
 * @author ly
 * @version V1.0
 */
public interface MerchantCService {

    public Merchant get(String id);

    public List<Merchant> findList(Merchant merchant);

    public Page<Merchant> findPage(Page<Merchant> page, Merchant merchant);

    public void save(Merchant merchant, boolean flag);

    public void delete(Merchant merchant);

    public void status(Merchant merchant);

    public void legalAuditStatus(Merchant merchant);

    public String rcAuditStatus(Merchant merchant);

    public String getMerchantPosCode(String userId, String mcc);

    void exportExcel(Merchant merchant, HttpServletRequest request, HttpServletResponse response);

    Merchant selectMerchant(Merchant merchant);

    List<Merchant> queryInternalMerchantsList(List<String> ids);

    List<Merchant> queryInternalMerchantsByFlag();

    void updateAuth(String merchantId, String status, String s);

    void updatePhoneAuth(String merchantId, String succes, String s);
}