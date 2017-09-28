package com.heepay.manage.modules.reconciliation.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***
 *
 *
 * 描    述：
 *
 * 创 建 者： wangl
 * 创建时间：  2017-05-1607:20 PM
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
public class DateUtil {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /*public static void main(String[] args) throws ParseException {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println(date);
        System.out.println("年份：" + getYear(date));
        System.out.println("月份：" + getMonth(date));
        System.out.println("日期：" + getDay(date));
        System.out.println("月初日期是: " + getMinMonthDate(date));
        System.out.println("月末日期是: " + getMaxMonthDate(date));
        String begin = getMaxMonthDate(date);
        Date parse = dateFormat.parse(begin);
        System.out.println(parse);
    }*/

    /**
     * 获取日期年份
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getYear(String date) throws ParseException{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取日期月份
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getMonth(String date) throws ParseException{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        return (calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取日期号
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getDay(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    /**
     * 获取月份起始日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMinMonthDate(String date) throws ParseException{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取月份起始日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date beginTime(String date) throws ParseException{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        String format = dateFormat.format(calendar.getTime());
        return dateFormat.parse(format);
    }


    /**
     * 获取月份最后日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMaxMonthDate(String date) throws ParseException{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }


    /**
     * 获取月份最后日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date endTime(String date) throws ParseException{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String format = dateFormat.format(calendar.getTime());
        return  dateFormat.parse(format);
    }


}
