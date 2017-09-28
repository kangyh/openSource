package com.heepay.manage.modules.cbms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.cbms.dao.CbmsCustomCodeSuperDao;
import com.heepay.manage.modules.cbms.entity.CbmsCustomCodeSuper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/1/4.
 */
@Service
@Transactional(readOnly = true)
public class CbmsCustomCodeSuperService extends CrudService<CbmsCustomCodeSuperDao, CbmsCustomCodeSuper> {

    public Page<CbmsCustomCodeSuper> findPage(Page<CbmsCustomCodeSuper> page, CbmsCustomCodeSuper cbmsCustomCodeSuper) {

        return super.findPage(page, cbmsCustomCodeSuper);
    }
}
