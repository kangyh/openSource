/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.tpds.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.tpds.dao.TpdsBankWithdrawDao;
import com.heepay.manage.modules.tpds.entity.TpdsBankWithdraw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * 描    述：存管银行提现记录Service
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
public class TpdsBankWithdrawService extends CrudService<TpdsBankWithdrawDao, TpdsBankWithdraw> {

    @Autowired
    private TpdsBankWithdrawDao tpdsBankWithdrawDao;

	public TpdsBankWithdraw get(String id) {
		return super.get(id);
	}
	
	public List<TpdsBankWithdraw> findList(TpdsBankWithdraw tpdsBankWithdraw) {
		return super.findList(tpdsBankWithdraw);
	}
	
	public Page<TpdsBankWithdraw> findPage(Page<TpdsBankWithdraw> page, TpdsBankWithdraw tpdsBankWithdraw) {
		return super.findPage(page, tpdsBankWithdraw);
	}
	
	@Transactional(readOnly = false)
	public void save(TpdsBankWithdraw tpdsBankWithdraw) {
		super.save(tpdsBankWithdraw,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(TpdsBankWithdraw tpdsBankWithdraw) {
		super.delete(tpdsBankWithdraw);
	}

    public Map<String,Object> findCountAndSum(TpdsBankWithdraw tpdsBankWithdraw) {
	    return tpdsBankWithdrawDao.findCountAndSum(tpdsBankWithdraw);
    }
}