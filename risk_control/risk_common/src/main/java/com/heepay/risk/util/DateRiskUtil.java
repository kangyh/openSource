
package com.heepay.risk.util;

import java.util.Calendar;
import java.util.Date;

/**
* @author 王英雷  E-mail:wangyl@9186.com
* @version 创建时间：2016年12月16日 上午10:07:06
* 类说明
*/
public class DateRiskUtil {
	/**
	 * 获取指定天的起始时间
	 * @param date
	 * @return
	 */
	public static Date startTime(Date date,int hour,int minute,int seconds){
	  Calendar todayStart = Calendar.getInstance(); 
	  todayStart.setTime(date);
	  todayStart.set(Calendar.HOUR_OF_DAY, hour);
	  todayStart.set(Calendar.MINUTE, minute);
	  todayStart.set(Calendar.SECOND, seconds);
    return todayStart.getTime();
	}
	/** 
	 * 取得当月天数 
	 * */  
	public static int getCurrentMonthLastDay()  
	{  
	    Calendar a = Calendar.getInstance();  
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	    int maxDate = a.get(Calendar.DATE);  
	    return maxDate;  
	}
}

