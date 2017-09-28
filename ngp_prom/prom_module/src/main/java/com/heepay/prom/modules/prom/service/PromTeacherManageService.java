/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.service;

import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.service.CrudService;
import com.heepay.prom.modules.prom.dao.PromTeacherManageDao;
import com.heepay.prom.modules.prom.entity.PromTeacherManage;
import com.heepay.prom.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：上下级关系管理Service
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
public class PromTeacherManageService extends CrudService<PromTeacherManageDao, PromTeacherManage> {

    @Autowired
    private PromTeacherManageDao promTeacherManageDao;

	public PromTeacherManage get(String id) {
		return super.get(id);
	}
	
	public List<PromTeacherManage> findList(PromTeacherManage promTeacherManage) {
		return super.findList(promTeacherManage);
	}
	
	public Page<PromTeacherManage> findPage(Page<PromTeacherManage> page, PromTeacherManage promTeacherManage) {
		return super.findPage(page, promTeacherManage);
	}
	
	@Transactional(readOnly = false)
	public void save(PromTeacherManage promTeacherManage) {
        if(null != promTeacherManage && promTeacherManage.getTeId() != null) {
            promTeacherManage.setUpdateAuthor(UserUtils.getUser().getName());
            promTeacherManageDao.update(promTeacherManage);
        }else{
            promTeacherManage.setCreateAuthor(UserUtils.getUser().getName());
            promTeacherManageDao.insert(promTeacherManage);
        }
	}
	
	@Transactional(readOnly = false)
	public void delete(PromTeacherManage promTeacherManage) {
		super.delete(promTeacherManage);
	}

    public Integer findExit(PromTeacherManage promTeacherManage) {
	    return promTeacherManageDao.findExit(promTeacherManage);
    }

    public PromTeacherManage findEntityByCode(PromTeacherManage promTeacherManage) {
        return promTeacherManageDao.findEntityByCode(promTeacherManage);
    }

    public Integer findSuperExit(PromTeacherManage superProm) {
        return promTeacherManageDao.findSuperExit(superProm);
    }

    public Integer findCountByCode(PromTeacherManage promTeacherManage) {
        return promTeacherManageDao.findCountByCode(promTeacherManage);
    }
}