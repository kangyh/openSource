/**
 *
 */
package com.heepay.manage.modules.hgms.service;


import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsMerchantEmployeeDao;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantEmployee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描    述：商户员工MerchantEmployeeCServiceImpl
 * <p>
 * 创 建 者：guozx@9186.com
 * 创建时间：2017-07-31 16:41:26
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
public class HgmsMerchantEmployeeService extends CrudService<HgmsMerchantEmployeeDao, HgmsMerchantEmployee> {

}