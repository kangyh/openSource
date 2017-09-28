package com.heepay.manage.modules.pbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcAccountDynamicQueryDao;
import com.heepay.manage.modules.pbc.entity.PbcAccountDynamicQuery;

/***
 * 
* 
* 描    述：账户动态
*
* 创 建 者：wangl
* 创建时间：  Dec 9, 20165:52:32 PM
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
public class PbcAccountDynamicQueryService  extends CrudService<PbcAccountDynamicQueryDao, PbcAccountDynamicQuery>{
   
	@Autowired
	private PbcAccountDynamicQueryDao pbcAccountDynamicQueryDao;

	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcAccountDynamicQuery getEntityById(int differId) {
		
		return pbcAccountDynamicQueryDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(PbcAccountDynamicQuery pbcAccountDynamicQuery) {
		
		pbcAccountDynamicQueryDao.updateEntity(pbcAccountDynamicQuery);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int saveEntity(PbcAccountDynamicQuery pbcAccountDynamicQuery){
		
		return pbcAccountDynamicQueryDao.saveEntity(pbcAccountDynamicQuery);
	}

	/**
	 * 
	 * @方法说明：根据查询信息主键查询对应类型的信息
	 * @时间：2016年12月17日 下午3:49:19
	 * @创建人：wangdong
	 */
	public PbcAccountDynamicQuery getQueryInfoId(String string) {
		return pbcAccountDynamicQueryDao.getQueryInfoId(string);
	}
}