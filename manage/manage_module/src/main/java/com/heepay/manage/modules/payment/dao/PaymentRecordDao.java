/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.PaymentRecord;

/**
 *
 * 描    述：交易管理DAO接口
 *
 * 创 建 者： @author zjx
 * 创建时间：
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
public interface PaymentRecordDao extends CrudDao<PaymentRecord> {
	
	public List<PaymentRecord> getOneByChargeId(String merchantOrderNo);
	
	public PaymentRecord getOne(String transNo);
	
	public List<PaymentRecord> getOneByGatewayId(String transNo);
	
	public List<PaymentRecord> getFailedByGateWayId(String transNo);

	public int updatePaymentRecordFail(String paymentId);
	public int updatePaymentRecordPaying(String paymentId);
	public PaymentRecord getPaymentId();
	
	public List<PaymentRecord> getPaymentsByTransNo(String transNo);
	public List<PaymentRecord> findListByTransNo(String transNo);

	public List<PaymentRecord> findWithdrawDepositPage(PaymentRecord paymentRecord);

	public List<PaymentRecord> getOriPaymentsForRefundReport(Map<String, Object> map);

	public List<PaymentRecord> findAggrPayPage(PaymentRecord paymentRecord);

	public List<PaymentRecord> findQuickPage(PaymentRecord paymentRecord);

	public List<PaymentRecord> findAggrPayList(@Param("transNo") List<String> transNo);

	public List<PaymentRecord> findListByPayment(PaymentRecord paymentRecord);

}