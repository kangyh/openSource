/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;


import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsMerchantFuctionsDao;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantFuctions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描    述：商户权限Service
 * <p>
 * 创 建 者：guozx@9186.com
 * 创建时间：2017-07-31 16:41:39
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Service
@Transactional(readOnly = true)
public class HgmsMerchantFuctionsService extends CrudService<HgmsMerchantFuctionsDao, HgmsMerchantFuctions> {

}