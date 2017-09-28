package com.heepay.manage.modules.reconciliation.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.reconciliation.entity.ClearingException;

/***
 *
 *
 * 描    述：清算异常表Dao
 *
 * 创 建 者： wangl
 * 创建时间： 2017-08-15 13:32
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
public interface ClearingExceptionDao extends CrudDao<ClearingException> {

    int delete(Long clearId);

    int saveEntity(ClearingException record);

    ClearingException getEntity(Long clearId);

    int updateEntity(ClearingException record);
}