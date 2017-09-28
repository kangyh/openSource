package com.heepay.manage.modules.pbc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcAccountDetailDao;
import com.heepay.manage.modules.pbc.entity.PbcAccountDetail;

/**
 * 
 *
 * 描    述：账户主体详情
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年12月24日 上午9:39:54
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
public class PbcAccountDetailService  extends CrudService<PbcAccountDetailDao, PbcAccountDetail>{

	@Autowired
	private PbcAccountDetailDao pbcAccountDetailDao;
	
	/**
	 * 
	 * @方法说明：查询账户信息list
	 * @时间：2016年10月20日
	 * @创建人：wangdong
	 */
	public List<PbcAccountDetail> findList(PbcAccountDetail pbcAccountDetail) {
		return super.findList(pbcAccountDetail);
	}
	
	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcAccountDetail getEntityById(Long pbcId) {
		return pbcAccountDetailDao.getEntityById(pbcId);
	}
	
	/**
	 * 
	 * @方法说明：根据业务编码查询账户信息
	 * @时间：2016年12月17日 下午1:23:33
	 * @创建人：wangdong
	 */
	public PbcAccountDetail getFeeBackId(String applicationId){
		return pbcAccountDetailDao.getEntityByApplicationId(applicationId);
	}

	/**
	 * 
	 * @方法说明：账户主体详情存储
	 * @时间：2016年12月26日 上午9:57:50
	 * @创建人：wangdong
	 */
	@Transactional(readOnly = false)
	public int saveMap(Map<String, Object> map) {
		return pbcAccountDetailDao.saveMap(map);
	}
}
