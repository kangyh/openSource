package com.heepay.manage.modules.pbc.dao;

import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcTransCardPaymentAccount;

/***
 * 
* 
* 描    述：交易流水号查询银行卡/支付帐号
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:20:54 PM
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
public interface PbcTransCardPaymentAccountDao  extends CrudDao<PbcTransCardPaymentAccount>{


	/**
	 * 插入
	 */
	int save(PbcTransCardPaymentAccount record);

	/**
	 * 插入默认使用这个
	 */
	int saveEntity(PbcTransCardPaymentAccount record);

	/**
     * 更新
     */
	int update(PbcTransCardPaymentAccount record);

	/**
	 * 更新默认使用这个
	 */
	int updateEntity(PbcTransCardPaymentAccount record);

	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 16, 2016
	 * @创建人：wangl
	 */
	PbcTransCardPaymentAccount getEntityById(int differId);

	/**
	 * 
	 * @方法说明：根据反馈id获取信息
	 * @时间：2016年12月17日 下午2:22:51
	 * @创建人：wangdong
	 */
	PbcTransCardPaymentAccount getFeeBackId(Long feedBackId);

	/**
	 * 
	 * @方法说明：插入按交易流水查询银行卡或支付账号信息
	 * @时间：2016年12月26日 下午3:21:42
	 * @创建人：wangdong
	 */
	int saveMap(Map<String, Object> map);
}