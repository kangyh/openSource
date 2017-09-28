/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年8月10日下午3:15:26
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
package com.heepay.billing.dao.impl;

import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearingExceptionMapper;
import com.heepay.billing.entity.ClearingException;
import com.heepay.billing.entity.ClearingExceptionExample;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年8月10日下午3:15:26
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
@Component
public class ClearingExceptionImpl extends BaseDaoImpl<ClearingException, ClearingExceptionExample>
		implements ClearingExceptionMapper{

	public ClearingExceptionImpl() {
		super.setNs("com.heepay.billing.dao.ClearingExceptionMapper");
	}
}
