/**
	 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.tpds.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.tpds.dao.TpdsCustomChargeDao;
import com.heepay.manage.modules.tpds.entity.TpdsCustomCharge;

/**
 *
 * 描    述：客户充值信息Service
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
public class TpdsCustomChargeService extends CrudService<TpdsCustomChargeDao, TpdsCustomCharge> {

    @Autowired
	private TpdsCustomChargeDao tpdsCustomChargeDao;

	public TpdsCustomCharge get(String id) {
		return super.get(id);
	}
	
	public List<TpdsCustomCharge> findList(TpdsCustomCharge tpdsCustomCharge) {
		return super.findList(tpdsCustomCharge);
	}
	
	public Page<TpdsCustomCharge> findPage(Page<TpdsCustomCharge> page, TpdsCustomCharge tpdsCustomCharge) {
		return super.findPage(page, tpdsCustomCharge);
	}
	
	@Transactional(readOnly = false)
	public void save(TpdsCustomCharge tpdsCustomCharge) {
		super.save(tpdsCustomCharge,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(TpdsCustomCharge tpdsCustomCharge) {
		super.delete(tpdsCustomCharge);
	}

    public Map<String, Object> findCountAndSum(TpdsCustomCharge tpdsCustomCharge) {
	    return tpdsCustomChargeDao.findCountAndSum(tpdsCustomCharge);
    }
}