/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.service;

import java.util.List;

import com.heepay.manage.modules.risk.entity.RiskChartRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.risk.entity.RiskMerchantOrderConversionRatio;
import com.heepay.manage.modules.risk.dao.RiskMerchantOrderConversionRatioDao;

/**
 *
 * 描    述：风控商户统计Service
 *
 * 创 建 者： @author xch
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
@Service
@Transactional(readOnly = true)
public class RiskMerchantOrderConversionRatioService extends CrudService<RiskMerchantOrderConversionRatioDao, RiskMerchantOrderConversionRatio> {

	@Autowired
	private RiskMerchantOrderConversionRatioDao statisticsRecordDao;

	public RiskMerchantOrderConversionRatio get(String id) {
		return super.get(id);
	}
	
	public List<RiskMerchantOrderConversionRatio> findList(RiskMerchantOrderConversionRatio riskMerchantOrderConversionRatio) {
		return super.findList(riskMerchantOrderConversionRatio);
	}

	public List<RiskMerchantOrderConversionRatio> findInTimeList(RiskMerchantOrderConversionRatio riskMerchantOrderConversionRatio) {
		return statisticsRecordDao.findInTimeList(riskMerchantOrderConversionRatio);
	}

	public List<RiskMerchantOrderConversionRatio> findListByDay(RiskMerchantOrderConversionRatio riskMerchantOrderConversionRatio) {
		return statisticsRecordDao.findListByDay(riskMerchantOrderConversionRatio);
	}

	public List<RiskMerchantOrderConversionRatio> findListByMonth(RiskMerchantOrderConversionRatio riskMerchantOrderConversionRatio) {
		return statisticsRecordDao.findListByMonth(riskMerchantOrderConversionRatio);
	}

	public Page<RiskMerchantOrderConversionRatio> findPage(Page<RiskMerchantOrderConversionRatio> page, RiskMerchantOrderConversionRatio riskMerchantOrderConversionRatio) {
		return super.findPage(page, riskMerchantOrderConversionRatio);
	}
	
	@Transactional(readOnly = false)
	public void save(RiskMerchantOrderConversionRatio riskMerchantOrderConversionRatio) {
		super.save(riskMerchantOrderConversionRatio,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(RiskMerchantOrderConversionRatio riskMerchantOrderConversionRatio) {
		super.delete(riskMerchantOrderConversionRatio);
	}
	
}