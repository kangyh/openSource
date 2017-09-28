/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.service;

import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.service.CrudService;
import com.heepay.prom.modules.prom.dao.PromAccountInfoDao;
import com.heepay.prom.modules.prom.entity.PromAccountInfo;
import com.heepay.prom.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：账户管理Service
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
public class PromAccountInfoService extends CrudService<PromAccountInfoDao, PromAccountInfo> {

    @Autowired
    private PromAccountInfoDao promAccountInfoDao;

	public PromAccountInfo get(String id) {
		return super.get(id);
	}
	
	public List<PromAccountInfo> findList(PromAccountInfo promAccountInfo) {
		return super.findList(promAccountInfo);
	}
	
	public Page<PromAccountInfo> findPage(Page<PromAccountInfo> page, PromAccountInfo promAccountInfo) {
		return super.findPage(page, promAccountInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(PromAccountInfo promAccountInfo) {
	    if(null != promAccountInfo && null != promAccountInfo.getAccountId()){
            promAccountInfo.setUpdateAuthor(UserUtils.getUser().getName());
            promAccountInfoDao.update(promAccountInfo);
        }else {
            promAccountInfo.setCreateAuthor(UserUtils.getUser().getName());
            promAccountInfoDao.insert(promAccountInfo);
        }
    }

    public Integer findExit(PromAccountInfo promAccountInfo) {
	    return promAccountInfoDao.findExit(promAccountInfo);
    }
}