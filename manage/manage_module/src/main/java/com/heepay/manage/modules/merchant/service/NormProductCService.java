/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.NormProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * 描    述：标准产品Service
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
public interface NormProductCService {

	public NormProduct get(String id);
	
	public List<NormProduct> findList(NormProduct normProduct);
	
	public Page<NormProduct> findPage(Page<NormProduct> page, NormProduct normProduct);
	
	public void save(NormProduct normProduct,boolean flag);
	
	public void delete(NormProduct normProduct);

    NormProduct selectExist(NormProduct normProduct);
}