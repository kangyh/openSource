/**
 *  
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantAutographInfo;

/**
 * 技术签约Service
 * 
 * @author ly
 * @version V1.0
 */
public interface MerchantAutographInfoCService {

	public MerchantAutographInfo get(String id);

	public List<MerchantAutographInfo> findList(MerchantAutographInfo merchantAutographInfo);

	public Page<MerchantAutographInfo> findPage(Page<MerchantAutographInfo> page,
			MerchantAutographInfo merchantAutographInfo);

	public void save(MerchantAutographInfo merchantAutographInfo, boolean flag);

	public void status(MerchantAutographInfo merchantAutographInfo);

  public MerchantAutographInfo exist(MerchantAutographInfo merchantAutographInfo);
	
}