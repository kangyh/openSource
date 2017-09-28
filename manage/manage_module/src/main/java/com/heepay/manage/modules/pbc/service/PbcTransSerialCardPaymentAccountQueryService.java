package com.heepay.manage.modules.pbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcTransSerialCardPaymentAccountQueryDao;
import com.heepay.manage.modules.pbc.entity.PbcTransSerialCardPaymentAccountQuery;

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
public class PbcTransSerialCardPaymentAccountQueryService  extends CrudService<PbcTransSerialCardPaymentAccountQueryDao, PbcTransSerialCardPaymentAccountQuery>{
  
	@Autowired
	private PbcTransSerialCardPaymentAccountQueryDao pbcTransSerialCardPaymentAccountQueryDao;
	
	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcTransSerialCardPaymentAccountQuery getEntityById(int differId) {
		
		return pbcTransSerialCardPaymentAccountQueryDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(PbcTransSerialCardPaymentAccountQuery PbcTransSerialCardPaymentAccountQuery) {
		
		pbcTransSerialCardPaymentAccountQueryDao.updateEntity(PbcTransSerialCardPaymentAccountQuery);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(PbcTransSerialCardPaymentAccountQuery pbcTransSerialCardPaymentAccountQuery){
		pbcTransSerialCardPaymentAccountQueryDao.saveEntity(pbcTransSerialCardPaymentAccountQuery);
	}

	/**
	 * 
	 * @方法说明：根据查询信息主键查询对应类型的信息
	 * @时间：2016年12月17日 下午3:50:39
	 * @创建人：wangdong
	 */
	public PbcTransSerialCardPaymentAccountQuery getQueryInfoId(String string) {
		return pbcTransSerialCardPaymentAccountQueryDao.getQueryInfoId(string);
	}
	
	
}