package com.heepay.billing.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.heepay.billing.entity.SettleChannelLog;
import com.heepay.billing.entity.SettleChannelLogExample;

public interface SettleChannelLogMapper {
    int countByExample(SettleChannelLogExample example);

    int deleteByExample(SettleChannelLogExample example);

    int deleteByPrimaryKey(Long logId);

    int insert(SettleChannelLog record);

    int insertSelective(SettleChannelLog record);

    List<SettleChannelLog> selectByExample(SettleChannelLogExample example);

    SettleChannelLog selectByPrimaryKey(Long logId);

    int updateByExampleSelective(@Param("record") SettleChannelLog record, @Param("example") SettleChannelLogExample example);

    int updateByExample(@Param("record") SettleChannelLog record, @Param("example") SettleChannelLogExample example);

    int updateByPrimaryKeySelective(SettleChannelLog record);

    int updateByPrimaryKey(SettleChannelLog record);

    /**
     * @方法说明：根据对账状态查询对账日志表中对账批次号
     * @时间：2016年9月23日
     * @创建人：wangdong
     */
	List<SettleChannelLog> findSettleChannelLogCheckBathByCheckStatus(Map<String,Object> map);

	/**
	 * @方法说明：根据对账的结果更新对账日志表
	 * @时间：2016年9月23日
	 * @创建人：wangdong
	 */
	void updateSettleChannelLog(SettleChannelLog settleChannelLog);


	SettleChannelLog selectByCheckBathNo(String checkBathNo);

	boolean fingSettleChannelLog(String file);
	
	/**
	 * 查询已对账最大日期
	 * @return
	 */
	String maxPayTime();

	/**
	 * 
	 * @方法说明：查询出上传对账文件的新纪录
	 * @时间：2016年10月13日
	 * @创建人：wangl
	 */
	List<SettleChannelLog> selectListEntity(SettleChannelLog record);

	/**
	 * 
	 * @方法说明： 查询对账日志，判断是否有符合条件的对账文件
	 * @author chenyanming
	 * @param channelCode
	 * @param channelType
	 * @param checkBathno
	 * @param value
	 * @return
	 * @时间：2016年10月13日下午6:03:42
	 */
	SettleChannelLog querySettleChannelLog(String channelCode, String channelType, String checkBathno, String value);
	
	/**
	 * 
	 * @方法说明：通过对账状态，查询对账日志
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2016年11月23日上午11:44:40
	 */
	public List<SettleChannelLog> querySettleChannelLogByCheckStatus(Map<String, Object> map);
	
	/**
	 * 查找当日通道记录
	 * @param string
	 * @param string2
	 * @param string3
	 * @return  
	 */
	List<SettleChannelLog> selectByCodeAndType(String channelCode, String channelType, String data);
	
	/**
	 * 
	 * @param string
	 * @param string2
	 * @return
	 */
	List<SettleChannelLog> selectByCheckStatus(String checkStatus, String operBeginTime);
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	List<SettleChannelLog> selectByOperBeginTime(String operBeginTime);
}