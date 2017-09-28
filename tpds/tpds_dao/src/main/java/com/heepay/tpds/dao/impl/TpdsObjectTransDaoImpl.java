/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月16日上午10:15:08
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
package com.heepay.tpds.dao.impl;

import org.springframework.stereotype.Component;

import com.heepay.tpds.dao.TpdsObjectTransMapper;
import com.heepay.tpds.entity.TpdsObjectTrans;
import com.heepay.tpds.entity.TpdsObjectTransExample;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月16日上午10:15:08
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
public class TpdsObjectTransDaoImpl extends BaseDaoImpl<TpdsObjectTrans,TpdsObjectTransExample> implements TpdsObjectTransMapper {

	// 默认构造方法设置命名空间
	public TpdsObjectTransDaoImpl() {
		super.setNs("com.heepay.tpds.dao.TpdsObjectTransMapper");
	}
	
	

}
