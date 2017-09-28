/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.vo.MerchantVO;
import org.apache.ibatis.annotations.Param;

/**
 * 商户DAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantDao extends CrudDao<Merchant> {
 
    List<Merchant> selectList();

    void status(Merchant merchant);
    
    void legalAuditStatus(Merchant merchant);
    
    void rcAuditStatus(Merchant merchant);
    
    MerchantVO getMerchantVo(String merchantId);

    Integer count(String merchantPosCode);

    List<Map<String, Object>> findExcel(Merchant merchant);

    Merchant selectMerchant(Merchant merchant);

    List<Merchant> getMerchantByMerchantIdsAndSource(Map<String, Object> map);

    List<Merchant> queryInternalMerchantsList(List<String> ids);

    List<Merchant> queryInternalMerchantsByFlag();
    void updateAuth(@Param("userId") String merchantId, @Param("authenticationStatus")String status,
                    @Param("authenticationReason")String s);

    void updatePhoneAuth(@Param("userId") String merchantId, @Param("phoneAuthStatus")String status,
                         @Param("phoneAuthReason")String s);

    List<Integer> getUserId(String merchantFlag);
}