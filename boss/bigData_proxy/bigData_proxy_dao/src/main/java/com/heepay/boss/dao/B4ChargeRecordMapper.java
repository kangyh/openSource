package com.heepay.boss.dao;

import com.heepay.boss.entity.B4ChargeRecord;
import java.util.List;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年6月1日下午2:14:49
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
public interface B4ChargeRecordMapper {
    int deleteByPrimaryKey(Long b4Id);

    int insert(B4ChargeRecord record);

    B4ChargeRecord selectByPrimaryKey(Long b4Id);

    List<B4ChargeRecord> selectAll();

    int updateByPrimaryKey(B4ChargeRecord record);
}