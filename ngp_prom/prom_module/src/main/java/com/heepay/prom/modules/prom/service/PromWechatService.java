/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.service;

import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.service.CrudService;
import com.heepay.prom.modules.prom.dao.PromWechatDao;
import com.heepay.prom.modules.prom.entity.PromWechat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：用户管理（微信）Service
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
public class PromWechatService extends CrudService<PromWechatDao, PromWechat> {

    @Autowired
    private PromWechatDao promWechatDao;

	public PromWechat get(String id) {
		return super.get(id);
	}
	
	public List<PromWechat> findList(PromWechat promWechat) {
		return super.findList(promWechat);
	}
	
	public Page<PromWechat> findPage(Page<PromWechat> page, PromWechat promWechat) {
		return super.findPage(page, promWechat);
	}
	
	@Transactional(readOnly = false)
	public void save(PromWechat promWechat) {
        promWechat.setCreateAuthor("APP自动录入");
        promWechatDao.insert(promWechat);
	}

    @Transactional(readOnly = false)
    public int update(PromWechat promWechat) {
        promWechat.setUpdateAuthor("APP自动录入");
        return promWechatDao.update(promWechat);
    }
	
	@Transactional(readOnly = false)
	public void delete(PromWechat promWechat) {
		super.delete(promWechat);
	}

    public Integer findExit(PromWechat promWechat) {
	    return promWechatDao.findExit(promWechat);
    }
}