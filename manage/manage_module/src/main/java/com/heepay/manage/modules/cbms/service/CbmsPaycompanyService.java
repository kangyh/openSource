/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.cbms.entity.CbmsPaycompany;
import com.heepay.manage.modules.cbms.dao.CbmsPaycompanyDao;

/**
 *
 * 描    述：支付机构设置Service
 *
 * 创 建 者： @author 牛俊鹏
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
public class CbmsPaycompanyService extends CrudService<CbmsPaycompanyDao, CbmsPaycompany> {

	public CbmsPaycompany get(String id) {
		return super.get(id);
	}
	
	public List<CbmsPaycompany> findList(CbmsPaycompany cbmsPaycompany) {
		return super.findList(cbmsPaycompany);
	}
	/**
	 * 修改支付机构信息表中的指定数据（需要判断新增还是修改）
	 * @param
	 * @return
	 */
	@Transactional(readOnly = false)
	public void save(CbmsPaycompany cbmsPaycompany) {
		super.save(cbmsPaycompany,false);
	}
	/**
	 * 修改支付机构信息表中的指定数据（不需要判断新增还是修改）
	 * @param
	 * @return
	 */
	@Transactional(readOnly = false)
	public void update(CbmsPaycompany cbmsPaycompany) {
		super.update(cbmsPaycompany,false);
	}
	/**
	 * 查询支付机构表中的所有数据
	 * @param
	 * @return
	 */
	public List<CbmsPaycompany> findAllList(){
		return super.findAllList();
	}
}