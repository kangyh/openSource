package com.heepay.manage.modules.pbc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcReleaseFeedbackDao;
import com.heepay.manage.modules.pbc.entity.PbcReleaseFeedback;

/***
 * 
* 
* 描    述：账户动态查询解除反
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
public class PbcReleaseFeedbackService  extends CrudService<PbcReleaseFeedbackDao, PbcReleaseFeedback>{
  
	@Autowired
	private PbcReleaseFeedbackDao pbcReleaseFeedbackDao;
	
	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public List<PbcReleaseFeedback> findList(PbcReleaseFeedback pbcReleaseFeedback) {
		return super.findList(pbcReleaseFeedback);
	}
	
	

	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcReleaseFeedback getEntityById(int differId) {
		
		return pbcReleaseFeedbackDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void update(PbcReleaseFeedback pbcReleaseFeedback) {
		
		pbcReleaseFeedbackDao.update(pbcReleaseFeedback);
	}
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(PbcReleaseFeedback pbcReleaseFeedback) {
		
		pbcReleaseFeedbackDao.updateEntity(pbcReleaseFeedback);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void save(PbcReleaseFeedback pbcReleaseFeedback){
		pbcReleaseFeedbackDao.save(pbcReleaseFeedback);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(PbcReleaseFeedback pbcReleaseFeedback){
		pbcReleaseFeedbackDao.saveEntity(pbcReleaseFeedback);
	}

	/**
	 * 
	 * @方法说明：根据反馈id获取信息
	 * @时间：2016年12月17日 下午2:20:25
	 * @创建人：wangdong
	 */
	public PbcReleaseFeedback getFeeBackId(Long feedBackId) {
		return pbcReleaseFeedbackDao.getFeeBackId(feedBackId);
	}

	/**
	 * 
	 * @方法说明：保存动态解除信息
	 * @时间：2016年12月26日 上午10:44:59
	 * @创建人：wangdong
	 */
	@Transactional(readOnly = false)
	public int saveMap(Map<String, Object> map) {
		return pbcReleaseFeedbackDao.saveMap(map);
	}
	
	
}