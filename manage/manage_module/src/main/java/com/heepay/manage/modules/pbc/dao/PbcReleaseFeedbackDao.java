package com.heepay.manage.modules.pbc.dao;

import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcReleaseFeedback;

/***
 * 
* 
* 描    述：账户动态查询解除反馈
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:20:48 PM
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
public interface PbcReleaseFeedbackDao  extends CrudDao<PbcReleaseFeedback>{

	/**
	 * 插入
	 */
    int save(PbcReleaseFeedback record);

    /**
	 * 插入默认使用这个
	 */
    int saveEntity(PbcReleaseFeedback record);

    /**
     * 更新
     */
    int update(PbcReleaseFeedback record);

    /**
	 * 更新默认使用这个
	 */
    int updateEntity(PbcReleaseFeedback record);

    /**
     * 
     * @方法说明：根据id查询对象
     * @时间：Dec 16, 2016
     * @创建人：wangl
     */
	PbcReleaseFeedback getEntityById(int differId);

	/**
	 * 
	 * @方法说明：根据反馈id获取信息
	 * @时间：2016年12月17日 下午2:21:28
	 * @创建人：wangdong
	 */
	PbcReleaseFeedback getFeeBackId(Long feedBackId);

	/**
	 * 
	 * @方法说明：插入账户动态解除信息
	 * @时间：2016年12月26日 下午3:21:11
	 * @创建人：wangdong
	 */
	int saveMap(Map<String, Object> map);
}