/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.service;

import com.heepay.common.util.StringUtils;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.service.CrudService;
import com.heepay.prom.modules.prom.dao.PromManageDao;
import com.heepay.prom.modules.prom.entity.PromManage;
import com.heepay.prom.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：推广位管理Service
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
public class PromManageService extends CrudService<PromManageDao, PromManage> {

    @Autowired
    private PromManageDao promManageDao;

	public PromManage get(String id) {
		return super.get(id);
	}
	
	public List<PromManage> findList(PromManage promManage) {
		return super.findList(promManage);
	}
	
	public Page<PromManage> findPage(Page<PromManage> page, PromManage promManage) {
		return super.findPage(page, promManage);
	}
	
	@Transactional(readOnly = false)
	public void save(PromManage promManage) {
		if(null != promManage && promManage.getProId() != null) {
            promManage.setUpdateAuthor(UserUtils.getUser().getName());
            if(StringUtils.isBlank(promManage.getProductName())){
                promManage.setProductName("全部产品");
            }
            promManageDao.update(promManage);
        }else{
		    if(StringUtils.isBlank(promManage.getProductName())){
		        promManage.setProductName("全部产品");
            }
            promManage.setCreateAuthor(UserUtils.getUser().getName());
            promManageDao.insert(promManage);
        }
	}
	
	@Transactional(readOnly = false)
	public void delete(PromManage promManage) {
		super.delete(promManage);
	}

    public Integer findExit(PromManage promManage) {
	    return promManageDao.findExit(promManage);
    }
}