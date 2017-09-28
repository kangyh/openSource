package com.heepay.manage.modules.reconciliation.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.reconciliation.entity.SettleRegexControl;
import org.apache.ibatis.annotations.Param;

/***
 * 
* 
* 描    述：正则控制表
*
* 创 建 者： wangl
* 创建时间：  2017年1月17日下午4:52:17
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
@MyBatisDao
public interface SettleRegexControlDao extends CrudDao<SettleRegexControl> {
   
	/**
	 * 
	 * @方法说明：删除操作
	 * @时间：2017年1月17日下午4:59:36
	 * @创建人：wangl
	 */
    int deleteEntity(Long regexControlId);

    /**
	 * 
	 * @方法说明：保存操作
	 * @时间：2017年1月17日下午4:59:36
	 * @创建人：wangl
	 */
    int saveEntity(SettleRegexControl record);
    
    /**
	 * 
	 * @方法说明：保存操作
	 * @时间：2017年1月17日下午4:59:36
	 * @创建人：wangl
	 */
    int save(SettleRegexControl record);

   

    /**
	 * 
	 * @方法说明：根据主键查询整个对象
	 * @时间：2017年1月17日下午4:59:36
	 * @创建人：wangl
	 */
    SettleRegexControl selectEntity(Long regexControlId);


    /**
	 * 
	 * @方法说明：更新方法
	 * @时间：2017年1月17日下午4:59:36
	 * @创建人：wangl
	 */
    int update(SettleRegexControl record);

    
    /**
	 * 
	 * @方法说明：更新方法
	 * @时间：2017年1月17日下午4:59:36
	 * @创建人：wangl
	 */
    int updateEntity(SettleRegexControl record);

	/**
	 * @方法说明：
	 * @时间：2017年1月18日上午11:53:28
	 * @创建人：wangl
	 */
	List<SettleRegexControl> getEntityByList(SettleRegexControl settleRegexControl);
	List<SettleRegexControl> getEntityByList2(SettleRegexControl settleRegexControl);

	/**
	 * @方法说明：
	 * @时间：2017年1月19日下午2:14:09
	 * @创建人：wangl
	 */
	int updateList(List<SettleRegexControl> list);

	/**
	 * @方法说明：根据规则主键级联删除
	 * @时间：2017年1月23日上午9:28:40
	 * @创建人：wangl
	 */
	int deleteEntityByRuleId(Long ruleId);
	int deleteEntityByRuleIdAndRuleType(SettleRegexControl settleRegexControl);
	/**
	 * @方法说明：批量插入
	 * @时间：2017年1月23日下午6:53:29
	 * @创建人：wangl
	 */
	void insetList(List<SettleRegexControl> list);

	/**
	 * @方法说明：批量插入时先删除后再插入
	 * @时间： 2017-05-15 10:03 AM
	 * @创建人：wangl
	 */
    int deleteByRule(@Param(value = "ruleId") Long ruleId,@Param(value = "ruleType") String ruleType);
}