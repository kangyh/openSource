package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleChannelManager;
import com.heepay.billing.entity.SettleChannelManagerExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SettleChannelManagerMapper {
    int countByExample(SettleChannelManagerExample example);

    int deleteByExample(SettleChannelManagerExample example);

    int deleteByPrimaryKey(Long channelManangeId);

    int insert(SettleChannelManager record);

    int insertSelective(SettleChannelManager record);

    List<SettleChannelManager> selectByExample(SettleChannelManagerExample example);

    SettleChannelManager selectByPrimaryKey(Long channelManangeId);

    int updateByExampleSelective(@Param("record") SettleChannelManager record, @Param("example") SettleChannelManagerExample example);

    int updateByExample(@Param("record") SettleChannelManager record, @Param("example") SettleChannelManagerExample example);

    int updateByPrimaryKeySelective(SettleChannelManager record);

    int updateByPrimaryKey(SettleChannelManager record);
    
    /**
	 * @方法说明：获取要下载的对账文件的通道管理数据
	 * @时间：2016年10月10日
	 * @创建人：wangjie
	 */
	List<SettleChannelManager> querySettleChannelManagerDetail(String checkFlg, String effectFlg);
	
	/**
	 * @方法说明：获取指定条件的通道信息
	 * @时间：2017年1月11日
	 * @创建人：wangjie
	 */
	List<SettleChannelManager> querySettleChannelManagerDetail(String effectFlg);
	

	/**
	 * 
	 * @方法说明：
	 * @author chenyanming
	 * @param channelCode
	 * @param channelType
	 * @param ruleType 
	 * @return
	 * @时间：2016年9月25日下午6:30:24
	 */
	List<SettleChannelManager> checkSettleChannelManagerRule(String channelCode, String channelType, String settleWay, String ruleType);

	/**
	 * @方法说明：根据通道编码获取通道名称
	 * @时间：2016年10月10日
	 * @创建人：wangdong
	 */
	SettleChannelManager findChannelNameBySettleChannelManageChannelCode(String channelCode);

	List<SettleChannelManager> querySettleChannelManagerDetail(Map map);

	List<SettleChannelManager> querySettleChannelManagerWarnDetail(String effectFlg);

	SettleChannelManager getSettleChannelManager(String channelCode, String channelType, String remoteAdress);

}