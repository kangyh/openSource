/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.MerchantRateLogDao;
import com.heepay.manage.modules.merchant.service.MerchantRateLogCService;
import com.heepay.manage.modules.merchant.vo.MerchantRateLog;

/**
 *
 * 描    述：费率操作日志Service
 *
 * 创 建 者： @author ly
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
public class MerchantRateLogCServiceImpl extends CrudService<MerchantRateLogDao, MerchantRateLog> implements MerchantRateLogCService {

}