package com.heepay.billing.core;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * 
 * 
 * 描 述：
 *
 * 创 建 者： xuangang 
 * 
 * 创建时间： 2017年6月30日上午10:47:30 
 * 
 * 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
public abstract class AbstractContext implements Context {

	private Timestamp timeInt;
	
	private Map<String, Object> dataMap;		
	
	private static final Logger logger = LogManager.getLogger();

	public AbstractContext(String s) {			
				
		dataMap = new HashMap<String, Object>();
	}

	public AbstractContext(String s, Map<String, Object> map) {
	
		timeInt = new Timestamp(System.currentTimeMillis());
		dataMap = map;
	}

	public Object getData(String s) {
		Object obj = dataMap.get(s);
		return obj;
	}

	public void setData(String s, Object obj) {
		if (obj == null)
			dataMap.remove(s);
		else
			dataMap.put(s, obj);
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public final Map<String, Object> getInnerMap() {
		return dataMap;
	}

	@SuppressWarnings("unchecked")
	public void setDataMap(Map map) {
		dataMap.putAll(map);
	}

	public Integer getInteger(String s) {
		Object obj = getData(s);
		if (obj == null)
			return null;
		if (obj instanceof Integer)
			return (Integer) obj;
		if (obj instanceof String)
			if (((String) obj).equals(""))
				return null;
			else
				return new Integer((String) obj);
		if (obj instanceof Number)
			return new Integer(((Number) obj).intValue());
		else
			throw new RuntimeException("unsupported_integer_convert");
	}

	public String getString(String s) {
		Object obj = getData(s);
		if (obj == null)
			return null;
		if (obj instanceof String)
			return (String) obj;
		else
			return obj.toString();
	}

	public BigDecimal getBigDecimal(String s) {
		Object obj = getData(s);
		if (obj == null)
			return null;
		if (obj instanceof BigDecimal)
			return (BigDecimal) obj;
		if (obj instanceof String)
			return new BigDecimal((String) obj);
		if (obj instanceof Number)
			return new BigDecimal(((Number) obj).doubleValue());
		else
			throw new RuntimeException("unsupported_bigdecimal_convert");
	}

	public String[] getStringArray(String s) {
		Object obj = getData(s);
		if (obj == null)
			return new String[0];
		if (obj.getClass().isArray()) {
			if (obj.getClass().getComponentType() == java.lang.String.class)
				return (String[]) obj;
			int i = Array.getLength(obj);
			String as[] = new String[i];
			for (int j = 0; j < i; j++) {
				Object obj1 = Array.get(obj, j);
				if (obj1 == null)
					as[j] = null;
				else
					as[j] = String.valueOf(Array.get(obj, j));
			}

			return as;
		} else {
			return (new String[] { (String) obj });
		}
	}

	public Object[] getObjectArray(String s, Class class1) {
		Object obj = getData(s);
		if (obj == null)
			return (Object[]) Array.newInstance(class1, 0);
		if (obj.getClass().isArray())
			if (obj.getClass().getComponentType() == class1)
				return (Object[]) obj;
			else
				throw new RuntimeException("unsupported_array_convert");
		if (class1.isAssignableFrom(obj.getClass())) {
			Object aobj[] = (Object[]) Array.newInstance(class1, 1);
			aobj[0] = obj;
			return aobj;
		} else {
			throw new RuntimeException("unsupported_array_convert");
		}
	}
	
    public Timestamp getTimestamp()
    {
        return timeInt;
    }

	public String toString() {
		return getClass().getName() +  " Timestamp:" + timeInt	+ " Inner dataMap:" + dataMap;
	}

}
