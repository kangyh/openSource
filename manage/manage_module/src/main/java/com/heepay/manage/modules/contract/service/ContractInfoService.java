/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.contract.entity.ContractInfo;
import com.heepay.manage.modules.contract.dao.ContractInfoDao;

/**
 *
 * 描    述：合同信息Service
 *
 * 创 建 者： @author ly
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
public class ContractInfoService extends CrudService<ContractInfoDao, ContractInfo> {

	public ContractInfo get(String id) {
		return super.get(id);
	}
	
	public List<ContractInfo> findList(ContractInfo contractInfo) {
		return super.findList(contractInfo);
	}
	
	public Page<ContractInfo> findPage(Page<ContractInfo> page, ContractInfo contractInfo) {
		return super.findPage(page, contractInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractInfo contractInfo) {
		super.save(contractInfo,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractInfo contractInfo) {
		super.delete(contractInfo);
	}
	
}