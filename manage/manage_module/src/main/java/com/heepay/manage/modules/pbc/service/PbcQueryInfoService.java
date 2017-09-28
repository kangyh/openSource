package com.heepay.manage.modules.pbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcQueryInfoDao;
import com.heepay.manage.modules.pbc.entity.PbcQueryInfo;

/***
 * 
* 
* 描    述：查询信息主表
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
public class PbcQueryInfoService  extends CrudService<PbcQueryInfoDao, PbcQueryInfo>{
  
	@Autowired
	private PbcQueryInfoDao pbcQueryInfoDao;
	
	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public List<PbcQueryInfo> findList(PbcQueryInfo pbcQueryInfo) {
		return super.findList(pbcQueryInfo);
	}
	
	

	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcQueryInfo getEntityById(int differId) {
		
		return pbcQueryInfoDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void update(PbcQueryInfo PbcQueryInfo) {
		
		pbcQueryInfoDao.update(PbcQueryInfo);
	}
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(PbcQueryInfo pbcQueryInfo) {
		
		pbcQueryInfoDao.updateEntity(pbcQueryInfo);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void save(PbcQueryInfo pbcQueryInfo){
		pbcQueryInfoDao.save(pbcQueryInfo);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(PbcQueryInfo pbcQueryInfo){
		pbcQueryInfoDao.saveEntity(pbcQueryInfo);
	}

	/**
	 * 
	 * @方法说明：根据业务申请编码获取查询主表信息
	 * @时间：2016年12月17日 下午2:59:20
	 * @创建人：wangdong
	 */
	public PbcQueryInfo getEntityByApplicationId(String applicationId) {
		return pbcQueryInfoDao.getEntityByApplicationId(applicationId);
	}
	
	
}