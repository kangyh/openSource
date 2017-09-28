package com.heepay.manage.modules.pbc.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcTransCardPaymentAccountDao;
import com.heepay.manage.modules.pbc.entity.PbcTransCardPaymentAccount;

/***
 * 
* 
* 描    述：交易流水号查询银行卡/支付帐号
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
public class PbcTransCardPaymentAccountService  extends CrudService<PbcTransCardPaymentAccountDao, PbcTransCardPaymentAccount>{
  
	@Autowired
	private PbcTransCardPaymentAccountDao pbcTransCardPaymentAccountDao;
	
	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcTransCardPaymentAccount getEntityById(int differId) {
		
		return pbcTransCardPaymentAccountDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(PbcTransCardPaymentAccount pbcTransCardPaymentAccount) {
		
		pbcTransCardPaymentAccountDao.updateEntity(pbcTransCardPaymentAccount);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(PbcTransCardPaymentAccount pbcTransCardPaymentAccount){
		pbcTransCardPaymentAccountDao.saveEntity(pbcTransCardPaymentAccount);
	}

	/**
	 * 
	 * @方法说明：根据反馈id获取信息
	 * @时间：2016年12月17日 下午2:22:31
	 * @创建人：wangdong
	 */
	public PbcTransCardPaymentAccount getFeeBackId(Long feedBackId) {
		return pbcTransCardPaymentAccountDao.getFeeBackId(feedBackId);
	}

	/**
	 * 
	 * @方法说明：按交易流水查询银行卡或支付账号信息存储
	 * @时间：2016年12月26日 上午9:58:44
	 * @创建人：wangdong
	 */
	@Transactional(readOnly = false)
	public int saveMap(Map<String, Object> map) {
		return pbcTransCardPaymentAccountDao.saveMap(map);
	}
	
	
}