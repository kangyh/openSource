package com.heepay.manage.modules.payment.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.BindCardDao;
import com.heepay.manage.modules.payment.entity.BindCardAuth;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * 
 * 
 * 描    述：钱包绑卡
 *
 * 创 建 者：liudh
 * 创建时间：2017/6/8 15:00
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
public class BindCardService extends CrudService<BindCardDao,BindCardAuth> {

    public BindCardAuth get(String id) {
        return super.get(id);
    }

    public List<BindCardAuth> findList(BindCardAuth bindCardAuth) {
        return super.findList(bindCardAuth);
    }

    public Page<BindCardAuth> findPage(Page<BindCardAuth> page, BindCardAuth bindCardAuth) {
        return super.findPage(page, bindCardAuth);
    }

    @Transactional(readOnly = false)
    public void save(BindCardAuth bindCardAuth) {
        super.save(bindCardAuth,false);
    }

    @Transactional(readOnly = false)
    public void delete(BindCardAuth bindCardAuth) {
        super.delete(bindCardAuth);
    }

}
