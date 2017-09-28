/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.enums.BankcardAuthStatus;
import com.heepay.enums.CommonStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.BankcardAuthDao;
import com.heepay.manage.modules.payment.entity.BankcardAuth;

/**
 *
 * 描    述：协议代扣审批Service
 *
 * 创 建 者： @author ld
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
public class BankcardAuthService extends CrudService<BankcardAuthDao, BankcardAuth> {

	@Autowired
	BankcardAuthDao bankcardAuthDao;

	public BankcardAuth getAuthId() {
		return bankcardAuthDao.getAuthId();
	}

	public BankcardAuth get(String id) {
		return super.get(id);
	}
	
	public List<BankcardAuth> findList(BankcardAuth bankcardAuth) {
		return super.findList(bankcardAuth);
	}
	
	public Page<BankcardAuth> findPage(Page<BankcardAuth> page, BankcardAuth bankcardAuth) {
		return super.findPage(page, bankcardAuth);
	}
	
	@Transactional(readOnly = false)
	public void save(BankcardAuth bankcardAuth) {
		super.save(bankcardAuth,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(BankcardAuth bankcardAuth) {
		super.delete(bankcardAuth);
	}

	@Transactional(readOnly = false)
    public int entrustedCollectionAuth(String authId, String authDesc, String authResult) {
        Map<String, Object> param = new HashMap<>();
        param.put("authId", authId);
        param.put("authDesc", authDesc);
        param.put("authResult", authResult);
        if(BankcardAuthStatus.SUCCES.getValue().equals(authResult)) {
            param.put("enable", CommonStatus.ENABLE.getValue());
        } else {
            param.put("enable", CommonStatus.DISABLE.getValue());
        }

        return bankcardAuthDao.entrustedCollectionAuth(param);
    }

    @Transactional(readOnly = false)
    public int setEnable(String authId, String enable) {
        Map<String, Object> param = new HashMap<>();
        param.put("authId", authId);
        param.put("enable", enable);

        return bankcardAuthDao.setEnable(param);
    }

    @Transactional(readOnly = false)
    public int setLimitAmount(String authId, String maxAmount) {
        Map<String, Object> param = new HashMap<>();
        param.put("authId", authId);
        param.put("maxAmount", maxAmount);

        return bankcardAuthDao.setLimitAmount(param);
    }
	
}