/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年6月30日上午10:45:14
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Map;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年6月30日上午10:45:14
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
public interface Context
{
    public abstract Object getData(String s);

    public abstract String getString(String s);

    public abstract Integer getInteger(String s);

    public abstract BigDecimal getBigDecimal(String s);

    public abstract String[] getStringArray(String s);

	public abstract Object[] getObjectArray(String s, Class class1);

    public abstract void setData(String s, Object obj);

    public abstract Map getDataMap();

    public abstract void setDataMap(Map map);

    public abstract Timestamp getTimestamp();
}