/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.service;

import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.service.CrudService;
import com.heepay.prom.modules.prom.dao.PromProductDao;
import com.heepay.prom.modules.prom.entity.PromProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：产品管理Service
 *
 * 创 建 者： @author wj
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
public class PromProductService extends CrudService<PromProductDao, PromProduct> {
	
	@Autowired
	private PromProductDao promProductDao;
	
	public PromProduct get(String id) {
		return super.get(id);
	}
	
	public List<PromProduct> findList(PromProduct promProduct) {
		return super.findList(promProduct);
	}
	
	public Page<PromProduct> findPage(Page<PromProduct> page, PromProduct promProduct) {
		return super.findPage(page, promProduct);
	}
	
	public void save(PromProduct promProduct) {
		super.save(promProduct,false);
	}
	
	public void delete(PromProduct promProduct) {
		super.delete(promProduct);
	}
	
	@Transactional(readOnly = false)
	public void insert(PromProduct promProduct){
		promProductDao.insert(promProduct);
	}
	
}