package com.heepay.manage.modules.pbc.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcAttachmentQuery;

/***
 * 
* 
* 描    述：附件
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:19:56 PM
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
public interface PbcAttachmentQueryDao  extends CrudDao<PbcAttachmentQuery>{

	/**
	 * 插入
	 */
    int save(PbcAttachmentQuery record);

    /**
   	 * 插入默认使用这个
   	 */
    int saveEntity(PbcAttachmentQuery record);

    /**
     * 更新
     */
    int update(PbcAttachmentQuery record);

    /**
	 * 更新默认使用这个
	 */
    int updateEntity(PbcAttachmentQuery record);

    /**
     * 
     * @方法说明：根据id查询对象
     * @时间：Dec 16, 2016
     * @创建人：wangl
     */
	PbcAttachmentQuery getEntityById(int differId);
    
}