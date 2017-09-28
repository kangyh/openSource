/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.OnlineContractInfoDao;
import com.heepay.manage.modules.merchant.vo.OnlineContractInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：线上签约Service
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
public interface OnlineContractInfoCService {

	public OnlineContractInfo get(String id);
	
	public List<OnlineContractInfo> findList(OnlineContractInfo onlineContractInfo);

	public Page<OnlineContractInfo> findPage(Page<OnlineContractInfo> page, OnlineContractInfo onlineContractInfo);
	
	public void save(OnlineContractInfo onlineContractInfo,boolean flag);
	
	public void delete(OnlineContractInfo onlineContractInfo);

	OnlineContractInfo selectOnlineContractInfo(String userId);

	int status(OnlineContractInfo onlineContractInfo);

	void legalAudit(OnlineContractInfo onlineContractInfo);

	void rcAudit(OnlineContractInfo onlineContractInfo);

	List<OnlineContractInfo> findProductList(OnlineContractInfo onlineContractInfo);

	OnlineContractInfo queryProductsBybatchNoAndUserId(String batchNo, String userId);
}