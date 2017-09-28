package com.heepay.boss.dao;

import java.util.List;

import com.heepay.boss.entity.ClearChannelRecord;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年6月1日下午2:15:05
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
 */
public interface ClearChannelRecordMapper {

	List<ClearChannelRecord> queryForBigData();

	ClearChannelRecord selectByTranNo(String transNo);
}