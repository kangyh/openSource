/**
 *  
 */
package com.heepay.manage.modules.route.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.route.entity.PayChannel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 支付通道管理DAO接口
 * @author lm
 * @version V1.0
 */
@MyBatisDao
public interface PayChannelDao extends CrudDao<PayChannel> {
    public List<PayChannel> selectList();

    /**
     * @param ChannelCode
     * @return支付通道
     */
    public PayChannel selectByChannelCode(String ChannelCode);
    public PayChannel selectByPayChannel(PayChannel payChannel);
    public List<PayChannel> selectProductChannel(Map<String,Object> params);
    public List<PayChannel> selectChannel();
    public PayChannel selectChannel(@Param(value = "bankNo")String bankNo,@Param(value = "channelTypeCode")String channelTypeCode,@Param(value = "cardTypeCode")String cardTypeCode,@Param(value = "accountType")String accountType,@Param(value = "businessType")String businessType,@Param(value = "merchantId")String merchantId);
    public List<PayChannel> selectRateList();
    public List<PayChannel> selectRateList(@Param(value = "bankNo")String bankNo,@Param(value = "channelTypeCode") String channelTypeCode,@Param(value = "cardTypeCode") String cardTypeCode,@Param(value = "accountType") String accountType);
    public List<PayChannel> selectCountList();
    public List<PayChannel> selectCountList(@Param(value = "bankNo")String bankNo,@Param(value = "channelTypeCode") String channelTypeCode,@Param(value = "cardTypeCode")String cardTypeCode,@Param(value = "accountType")String accountType);
    public List<PayChannel> selectChannelType();
    public List<PayChannel> selectChannelType(@Param(value = "channelTypeCode")String channelTypeCode);
    public List<PayChannel> selectSortChannelOwnbak(PayChannel payChannel);
    public List<PayChannel> selectSortChannelSpnbak(PayChannel payChannel);
    public List<PayChannel> selectSpnChannel();
    public List<PayChannel> selectSpnChannel(@Param(value = "channelTypeCode")String channelTypeCode,@Param(value = "cardTypeCode")String cardTypeCode,@Param(value = "accountType")String accountType);
    public List<PayChannel> selectSpnChannelCount();
    public List<PayChannel> selectSpnChannelCount(@Param(value = "channelTypeCode")String channelTypeCode,@Param(value = "cardTypeCode")String cardTypeCode,@Param(value = "accountType")String accountType);
    void changeSort(PayChannel payChannel);
    public void status(PayChannel payChannel);
    public void updateChannelCode(PayChannel payChannel);
    public List<PayChannel> findAllList();
    public List<PayChannel> findListByBankNoAndChannelTypeCode(PayChannel payChannel);
    public List<PayChannel> selectMerchantChannel(Map<String,Object> params);
    public List<PayChannel> selectByMerchantId(PayChannel payChannel);
    public List<PayChannel> findChannelGroupPage(PayChannel payChannel);


    /**
     * @方法说明：多条插入去重查询
     * @时间： 2017-09-13 09:21
     * @创建人：wangl
     */
    int sumNum(PayChannel payChannel);
}