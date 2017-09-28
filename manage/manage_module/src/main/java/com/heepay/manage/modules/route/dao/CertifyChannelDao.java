/**
 *  
 */
package com.heepay.manage.modules.route.dao;

import java.math.BigDecimal;
import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.route.entity.CertifyChannel;
import org.apache.ibatis.annotations.Param;

/**
 * 实名认证通道管理DAO接口
 * @author lm
 * @version V1.0
 */
@MyBatisDao
public interface CertifyChannelDao extends CrudDao<CertifyChannel> {
  
    public CertifyChannel selectByPartnerCode(@Param(value="channelPartnerCode")String channelPartnerCode,
             @Param(value="productCode")String productCode,@Param(value="channelTypeCode")String channelTypeCode);
    void changeSort(CertifyChannel certifyChannel);

    List<CertifyChannel> getOrderBySort(@Param(value="productCode")String productCode);

    public CertifyChannel selectByChannelCode(String channelPartnerCode);

    CertifyChannel getCertifyByType(@Param(value="channelTypeCode")String channelTypeCode);

    BigDecimal getMaxCost(@Param(value = "productCode")String productCode);
}