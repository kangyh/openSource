package com.heepay.manage.modules.reconciliation.service;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.dao.ClearingExceptionDao;
import com.heepay.manage.modules.reconciliation.dao.SettleDifferBillRecordDao;
import com.heepay.manage.modules.reconciliation.entity.ClearingException;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferBillRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *
 *
 * 描    述：清算异常表
 *
 * 创 建 者： wangl
 * 创建时间： 2017-07-28 14:18
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
public class ClearingExceptionService extends CrudService<ClearingExceptionDao, ClearingException> {

    @Autowired
    private ClearingExceptionDao clearingExceptionDao;

    /**
     * @方法说明：分页查询
     * @时间： 2017-07-28 14:20
     * @创建人：wangl
     */
    public List<ClearingException> findList(ClearingException clearingException) {
        return super.findList(clearingException);
    }


    /**
     * @方法说明：查询单个对象
     * @时间： 2017-08-15 16:21
     * @创建人：wangl
     */
    public ClearingException getEntity(Long clearId) {
      return   clearingExceptionDao.getEntity(clearId);
    }
}
