/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.service;

import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.service.CrudService;
import com.heepay.prom.modules.prom.dao.PromMerchantManageDao;
import com.heepay.prom.modules.prom.entity.PromMerchantManage;
import com.heepay.prom.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：商户管理Service
 *
 * 创 建 者： @author wangdong
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
public class PromMerchantManageService extends CrudService<PromMerchantManageDao, PromMerchantManage> {

    @Autowired
    private PromMerchantManageDao promMerchantManageDao;

	public PromMerchantManage get(String id) {
		return super.get(id);
	}
	
	public List<PromMerchantManage> findList(PromMerchantManage promMerchantManage) {
		return super.findList(promMerchantManage);
	}
	
	public Page<PromMerchantManage> findPage(Page<PromMerchantManage> page, PromMerchantManage promMerchantManage) {
		return super.findPage(page, promMerchantManage);
	}
	
	@Transactional(readOnly = false)
	public void save(PromMerchantManage promMerchantManage) {
	    if(null != promMerchantManage && null != promMerchantManage.getMerchantId()) {
            promMerchantManage.setUpdateAuthor(UserUtils.getUser().getName());
            promMerchantManageDao.update(promMerchantManage);
        }else{
            promMerchantManage.setOperator(UserUtils.getUser().getName());
            promMerchantManageDao.insert(promMerchantManage);
        }
	}
	
	@Transactional(readOnly = false)
	public void delete(PromMerchantManage promMerchantManage) {
		super.delete(promMerchantManage);
	}

    public Integer findExit(PromMerchantManage promMerchantManage) {
	    return promMerchantManageDao.findExit(promMerchantManage);
    }
    
    public String selectByMerchantId(String merchantId){
    	 return promMerchantManageDao.selectByMerchantId(merchantId);
    }

	public PromMerchantManage getWechatNo(PromMerchantManage promMerchantManage) {
		return promMerchantManageDao.getWechatNo(promMerchantManage);
	}

	@Transactional(readOnly = false)
	public void autoSave(PromMerchantManage promMerchantManage) {
		if(null != promMerchantManage && null != promMerchantManage.getMerchantId()) {
			promMerchantManage.setUpdateAuthor("APP自动录入");
			promMerchantManageDao.update(promMerchantManage);
		}else{
			promMerchantManage.setOperator("APP自动录入");
			promMerchantManageDao.insert(promMerchantManage);
		}
	}
}