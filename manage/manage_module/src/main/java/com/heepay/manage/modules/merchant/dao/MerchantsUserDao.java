/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantsUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * userDAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantsUserDao extends CrudDao<MerchantsUser> {

    int insert(MerchantsUser merchantsUser);

    int resetPayPassword(MerchantsUser merchantsUser);
    int resetLoginPassword(MerchantsUser merchantsUser);
    int resetLoginNameAndPhone(MerchantsUser merchantsUser);
    
    MerchantsUser queryByLoginNameAndMerchantId(MerchantsUser merchantsUser);

    MerchantsUser select(@Param("id")String userId, @Param("merchantId")String merchantId);

    List<MerchantsUser> queryMerchantsUser(Map<String, Object> params);

    int queryMerchantsUserCount(Map<String, Object> params);
}