/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.risk.entity.RiskChannelOrderConversionRatio;
import com.heepay.manage.modules.risk.dao.RiskChannelOrderConversionRatioDao;

/**
 *
 * 描    述：风控通道转化率Service
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
public class RiskChannelOrderConversionRatioService extends CrudService<RiskChannelOrderConversionRatioDao, RiskChannelOrderConversionRatio> {

	@Autowired
	private RiskChannelOrderConversionRatioDao statisticsRecordDao;

	public RiskChannelOrderConversionRatio get(String id) {
		return super.get(id);
	}
	
	public List<RiskChannelOrderConversionRatio> findList(RiskChannelOrderConversionRatio riskChannelOrderConversionRatio) {
		return super.findList(riskChannelOrderConversionRatio);
	}

	public List<RiskChannelOrderConversionRatio> findInTimeList(RiskChannelOrderConversionRatio riskMerchantOrderConversionRatio) {
		return statisticsRecordDao.findInTimeList(riskMerchantOrderConversionRatio);
	}

	public List<RiskChannelOrderConversionRatio> findListByDay(RiskChannelOrderConversionRatio riskMerchantOrderConversionRatio) {
		return statisticsRecordDao.findListByDay(riskMerchantOrderConversionRatio);
	}

	public List<RiskChannelOrderConversionRatio> findListByMonth(RiskChannelOrderConversionRatio riskMerchantOrderConversionRatio) {
		return statisticsRecordDao.findListByMonth(riskMerchantOrderConversionRatio);
	}
	public Page<RiskChannelOrderConversionRatio> findPage(Page<RiskChannelOrderConversionRatio> page, RiskChannelOrderConversionRatio riskChannelOrderConversionRatio) {
		return super.findPage(page, riskChannelOrderConversionRatio);
	}
	
	@Transactional(readOnly = false)
	public void save(RiskChannelOrderConversionRatio riskChannelOrderConversionRatio) {
		super.save(riskChannelOrderConversionRatio,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(RiskChannelOrderConversionRatio riskChannelOrderConversionRatio) {
		super.delete(riskChannelOrderConversionRatio);
	}
	
}