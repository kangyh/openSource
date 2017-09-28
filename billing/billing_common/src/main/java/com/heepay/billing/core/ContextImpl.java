/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年6月30日上午11:23:26
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
package com.heepay.billing.core;

import java.util.HashMap;
import java.util.Map;

/***
 * 
 * 
 * 描 述：
 *
 * 创 建 者： xuangang 创建时间： 2017年6月30日上午11:23:26 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
public class ContextImpl extends AbstractContext {

	public ContextImpl() {
		super(null);
	}

	public ContextImpl(String s) {
		super(s);
	}

	public Map<String, Object> getDataMap() {
		return new HashMap(super.getDataMap());
	}

}
