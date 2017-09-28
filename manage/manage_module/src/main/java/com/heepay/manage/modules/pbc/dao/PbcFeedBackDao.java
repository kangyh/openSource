package com.heepay.manage.modules.pbc.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;

/***
 * 
* 
* 描    述：反馈表
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:20:05 PM
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
public interface PbcFeedBackDao  extends CrudDao<PbcFeedBack>{

	/**
	 * 插入
	 */
    int save(PbcFeedBack record);

    /**
	 * 插入默认使用这个
	 */
    int saveEntity(PbcFeedBack record);

    /**
     * 更新
     */
    int update(PbcFeedBack record);

    /**
   	 * 更新默认使用这个
   	 */
    int updateEntity(PbcFeedBack record);

    /**
     * 
     * @方法说明：获取反馈表信息用主键
     * @时间：2016年12月16日 下午8:21:40
     * @创建人：wangdong
     */
	PbcFeedBack getEntityById(Long feedBackId);

	/**
	 * 
	 * @方法说明：插入反馈信息
	 * @时间：2016年12月26日 下午3:15:58
	 * @创建人：wangdong
	 */
	@SuppressWarnings("rawtypes")
	int insertValue(List<Map> readExcel2003);

	/**
	 * 
	 * @方法说明：根据业务申请编码获取实体
	 * @时间：2016年12月21日 上午10:44:35
	 * @创建人：wangdong
	 */
	PbcFeedBack getEntityByApplicationCode(String applicationId);

	/**
	 * 
	 * @方法说明：反馈信息导出
	 * @时间：2016年12月23日 下午5:06:17
	 * @创建人：wangdong
	 */
	List<Map<String, Object>> findListExcel(PbcFeedBack pbcFeedBack);

	/**
	 * @方法说明：
	 * @时间：Jan 12, 20175:01:34 PM
	 * @创建人：wangl
	 */
	int deleteData(long feedBackId);

}