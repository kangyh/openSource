/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.account.entity.MerchantAccount;

/**
 *
 * 描    述：账户管理DAO接口
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
public interface MerchantAccountDao extends CrudDao<MerchantAccount> {

    int updateStatus(MerchantAccount merchantAccount);
    List<MerchantAccount> getByMerchantId(String merchantId);
    List<MerchantAccount> getMerchantByType(Map<String, Object> paramsMap);
    List<MerchantAccount> getReserveAccount(Map<String, Object> paramsMap);
    List<MerchantAccount> getMerchantAccountByAccountIdAndType(Map<String, Object> paramsMap);
    List<Long> getSelectMerchantIds(Map<String, Object> paramsMap);
    List<MerchantAccount> getAllMerchantAccount();
    List<String> getMerchantIdGroupByMerchantId();
    List<MerchantAccount> getAllMerchantAccountInMerchantId(Map<String, Object> paramsMap);

    int updateMerchantAccount(MerchantAccount merchantAccount);
	MerchantAccount getAccountId();
}