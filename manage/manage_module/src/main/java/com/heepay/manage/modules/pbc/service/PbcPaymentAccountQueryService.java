package com.heepay.manage.modules.pbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcPaymentAccountQueryDao;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccountQuery;

/***
 * 
* 
* 描    述：关联全支付账号
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
public class PbcPaymentAccountQueryService  extends CrudService<PbcPaymentAccountQueryDao, PbcPaymentAccountQuery>{
  
	@Autowired
	private PbcPaymentAccountQueryDao pbcPaymentAccountQueryDao;
	
	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcPaymentAccountQuery getEntityById(int differId) {
		return pbcPaymentAccountQueryDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(PbcPaymentAccountQuery pbcPaymentAccountQuery) {
		
		pbcPaymentAccountQueryDao.updateEntity(pbcPaymentAccountQuery);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(PbcPaymentAccountQuery pbcPaymentAccountQuery){
		pbcPaymentAccountQueryDao.saveEntity(pbcPaymentAccountQuery);
	}

	/**
	 * 
	 * @方法说明：根据查询信息主键查询对应类型的信息
	 * @时间：2016年12月17日 下午3:51:56
	 * @创建人：wangdong
	 */
	public PbcPaymentAccountQuery getQueryInfoId(String string) {
		return pbcPaymentAccountQueryDao.getQueryInfoId(string);
	}
	
	
}