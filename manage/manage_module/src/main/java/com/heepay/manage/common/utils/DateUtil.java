package com.heepay.manage.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtil {

    public static final long SECOND_TO__MILLISECOND = 1000;// 秒转毫秒

    public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @discription 获取当前时间戳Long
     * @author ly
     * @created 2016年10月21日 下午2:52:10
     * @return
     */
    public static Long getNowTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);// 日期减1 
        return calendar.getTimeInMillis();
    }

    /**
     * @discription 获取当前时间戳i年后Long
     * @author ly
     * @created 2016年10月21日 下午2:53:24
     * @param i
     * @return
     */
    public static Long getNowTimeAfterYear(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, i);
        return calendar.getTimeInMillis();
    }

    public static Long SecondToMillisecond(Long validityDateBegin) {
        return validityDateBegin * SECOND_TO__MILLISECOND;
    }

    public static Long SecondToMillisecondNextDay(Long validityDateEnd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(validityDateEnd * SECOND_TO__MILLISECOND);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        return calendar.getTimeInMillis();
    }

    public static Long MillisecondNextDay(Long validityDateEnd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(validityDateEnd);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        return calendar.getTimeInMillis();
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(Long s, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static Long dateToStamp(String s, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(s);
        return date.getTime();
    }

    /**
     * @discription dateFormat
     * @author ly
     * @created 2016年12月6日 下午8:27:47
     * @param dateValue
     * @param format
     * @return
     */
    public static String dateToString(Date dateValue, String format) {
        if (dateValue == null) {
            return null;
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(dateValue);
        }
    }
    
    
    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String format){
      if (StringUtils.isEmpty(strDate)) {
        return null;
      }
      SimpleDateFormat dateFormat = new SimpleDateFormat(format);
      try {
        return dateFormat.parse(strDate);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return null;
    }


    /**
     * @discription 获取date i年后的date
     * @author ly
     * @created 2016年10月21日 下午2:53:24
     * @param i
     * @return
     */
    public static Date getNowTimeAfterYear(Date date,int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, i);
        return calendar.getTime();
    }

}
