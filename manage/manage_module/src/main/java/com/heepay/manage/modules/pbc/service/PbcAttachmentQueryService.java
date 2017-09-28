package com.heepay.manage.modules.pbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcAttachmentQueryDao;
import com.heepay.manage.modules.pbc.entity.PbcAttachmentQuery;


/***
 * 
* 
* 描    述：附件
*
* 创 建 者：wangl
* 创建时间：  Dec 9, 20165:55:25 PM
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
@Service
@Transactional(readOnly = true)
public class PbcAttachmentQueryService  extends CrudService<PbcAttachmentQueryDao, PbcAttachmentQuery>{
    
	@Autowired
	private PbcAttachmentQueryDao pbcAttachmentQueryDao;

	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcAttachmentQuery getEntityById(int differId) {
		
		return pbcAttachmentQueryDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(PbcAttachmentQuery pbcAttachmentQuery) {
		
		pbcAttachmentQueryDao.updateEntity(pbcAttachmentQuery);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(PbcAttachmentQuery pbcAttachmentQuery){
		
		pbcAttachmentQueryDao.saveEntity(pbcAttachmentQuery);
	}
}