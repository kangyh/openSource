package com.heepay.manage.modules.pbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcTransDetailQueryDao;
import com.heepay.manage.modules.pbc.entity.PbcTransDetailQuery;

/***
 * 
* 
* 描    述：支付账户交易明细
*
* 创 建 者：wangl
* 创建时间：  Dec 9, 20165:49:53 PM
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
public class PbcTransDetailQueryService  extends CrudService<PbcTransDetailQueryDao, PbcTransDetailQuery>{
  
	@Autowired
	private PbcTransDetailQueryDao pbcTransDetailQueryDao;
	
	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcTransDetailQuery getEntityById(int differId) {
		
		return pbcTransDetailQueryDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(PbcTransDetailQuery pbcTransDetailQuery) {
		
		pbcTransDetailQueryDao.updateEntity(pbcTransDetailQuery);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(PbcTransDetailQuery pbcTransDetailQuery){
		pbcTransDetailQueryDao.saveEntity(pbcTransDetailQuery);
	}
	
	/**
	 * 
	 * @方法说明：根据查询信息主键查询对应类型的信息
	 * @时间：2016年12月17日 下午3:45:04
	 * @创建人：wangdong
	 */
	public PbcTransDetailQuery getQueryInfoId(String queryInfoId) {
		return pbcTransDetailQueryDao.getQueryInfoId(queryInfoId);
	}
	
	
}