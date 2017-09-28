package com.heepay.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BillDateUtil {
  private static Log log = LogFactory.getLog(StringUtil.class);

  public static final String DATE_YEAR_MONTH_FORMAT = "yyyyMMdd";

  public static final String YEAR_MONTH_FORMAT = "yyyyMM";

  public static final String MONTH_FORMAT = "yyyy-MM";

  public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";

  public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public static final String TIME_FORMAT = "yyyyMMddHHmmss";

  public static final String FULL_TIME = "yyyyMMddHHmmssSSS";

  public static final String TIME_FORMAT_YY = "yyMMddHHmmss";

  public static final String TIME_FORMAT_YY_HH = "yyMMddHHmm";
  
  public static final String MONTH_FORMAT_CN = "yyyy年MM月";
  
  public static final String YEAR_FORMAT_CN = "yyyy年";

  private static SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat(
      DATE_YEAR_MONTH_FORMAT);
  private static SimpleDateFormat sdf_yyyyMM = new SimpleDateFormat(
      YEAR_MONTH_FORMAT);
  private static SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat(
      DATE_FORMAT_YYYYMMDD);
  private static SimpleDateFormat sdf_yyyy_MM = new SimpleDateFormat(
      MONTH_FORMAT);

  public BillDateUtil() {
  }

  public static String changeDate(String time) throws Exception {
    SimpleDateFormat oldFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = oldFormat.parse(time);
    return newFormat.format(date);
  }

  public static String getTodayYYYYMMDDHHmmss() {
    return dateToString(new Date(), TIME_FORMAT);
  }
  public static String getTodayYYYYMMDDHHmmss(Date date) {
    return dateToString(date, TIME_FORMAT);
  }
  public static String getTodayyyyyMMddHHmmssSSS() {
    return dateToString(new Date(), FULL_TIME);
  }

  public static String getTodayYYYYMMDD() {
    return dateToString(new Date(), DATE_FORMAT_YYYYMMDD);
  }

  // 获得当前时间yyyyMMdd
  public static String getCurrentDate(Date date) {
    return sdf_yyyyMMdd.format(date);
  }

  public static String getCurrentWeekBegin(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek()); // Monday
    return sdf_yyyyMMdd.format(cal.getTime());
  }

  public static String getCurrentWeekEnd(Date date) {
    Calendar cal = new GregorianCalendar();
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
    return sdf_yyyyMMdd.format(cal.getTime());
  }

  public static String getCurrentMonthBegin(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return sdf_yyyyMM.format(date) + "01";
  }

  public static String getCurrentDayBegin(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return sdf_yyyy_MM.format(date) + "-01";
  }

  public static String getCurrentNoYearDay(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return sdf_yyyy_MM_dd.format(date).substring(5);
  }

  public static String getCurrentMonthEnd(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);// 获取当月中天数的最大值
                                                              // 即当月的日期数
    return sdf_yyyyMM.format(date) + maxDate;
  }
  public static String getYesterDay(String dataformat){
    Calendar   cal   =   Calendar.getInstance();
    cal.add(Calendar.DATE,   -1);
    String yesterday = new SimpleDateFormat(dataformat).format(cal.getTime());
    return  yesterday ;
  }
  public static String getYesterDayBefor(String dataformat){
	    Calendar   cal   =   Calendar.getInstance();
	    cal.add(Calendar.DATE,   -2);
	    String yesterday = new SimpleDateFormat(dataformat).format(cal.getTime());
	    return  yesterday ;
	  }
  
  public static String getTodayYYYYMMDD_HHMMSS() {
    return dateToString(new Date(), DATETIME_FORMAT);
  }

  public static String getTodayYYYY_MM() {
    return dateToString(new Date(), MONTH_FORMAT);
  }

  public static String toGB2312String(String stringValue) {
    // 如果参数为null，返回null
    if (stringValue == null) {
      return null;
    }
    String value = null;
    try {
      value = new String(stringValue.getBytes("ISO8859_1"), "GB2312");
    } catch (UnsupportedEncodingException ex) {
      value = stringValue;
    }
    return value;
  }

  public static String toGBKString(String stringValue) {
    // 如果参数为null，返回null
    if (stringValue == null) {
      return null;
    }
    String value = null;
    try {
      value = new String(stringValue.getBytes("ISO8859_1"), "GBK");
    } catch (UnsupportedEncodingException ex) {
      value = stringValue;
    }
    return value;
  }

  public static String toUTFString(String stringValue) {
    // 如果参数为null，返回null
    if (stringValue == null) {
      return null;
    }
    String value = null;
    try {
      value = new String(stringValue.getBytes("GBK"), "UTF-8");
    } catch (UnsupportedEncodingException ex) {
      value = stringValue;
    }
    return value;

  }

  public static String GBKConvert(String str) {
    try {
      return new String(str.getBytes("GBK"));
    } catch (UnsupportedEncodingException e) {
      return null;
    }
  }

  /**
   * 判断是否指定字符串为空字符串(null或者长度为0)
   */
  public static boolean isEmptyString(String stringValue) {
    if (stringValue == null || stringValue.length() < 1) {
      return true;
    } else {
      return false;
    }
  }

  public static int stringToInt(String stringValue) {
    return stringToInt(stringValue, -1);
  }

  public static int stringToInt(String stringValue, int defaultValue) {
    int intValue = defaultValue;
    if (stringValue != null) {
      try {
        intValue = Integer.parseInt(stringValue);
      } catch (NumberFormatException ex) {
        intValue = defaultValue;
      }
    }
    return intValue;
  }

  public static Date stringToDate(String stringValue) {
    return stringToDate(stringValue, DATETIME_FORMAT);
  }

  public static Date stringToDate(String stringValue, String format) {
    Date dateValue = null;
    if (stringValue != null) {
      try {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateValue = dateFormat.parse(stringValue);

      } catch (ParseException ex) {
      }
    }
    return dateValue;
  }

  public static String dateToString(Date dateValue) {
    return dateToString(dateValue, DATETIME_FORMAT);
  }
  
  
  public static String dateToString(Date dateValue, String format) {
    if (dateValue == null) {
      return null;
    } else {
      SimpleDateFormat dateFormat = new SimpleDateFormat(format);
      return dateFormat.format(dateValue);
    }
  }

  public static Date getDate() {
    Calendar c = Calendar.getInstance();
    return stringToDate(dateToString(c.getTime()));
  }

  public static Date getDate(String format) {
    Calendar c = Calendar.getInstance();
    return stringToDate(dateToString(c.getTime(), format));
  }

  public static String getToday() {
    return dateToString(new Date(), "yyyy-MM-dd");
  }

  public static String getToday(String format) {
    String dateStr = dateToString(new Date(), format);
    return dateStr;
  }

  /**
   * 获得当前年
   *
   * @return
   */
  public static String getNowYear() {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    return String.valueOf(year);
  }

  /**
   * 获得当前月
   *
   * @return
   */
  public static String getNowMonth() {
    Calendar calendar = Calendar.getInstance();
    int month = calendar.get(Calendar.MONTH) + 1;
    if (month < 10) {
      return "0" + month;
    } else {
      return String.valueOf(month);
    }
  }

  /**
   * 获得当前日
   *
   * @return
   */
  public static String getNowDay() {
    return dateToString(new Date(), "dd");

  }

  /**
   * 得到两个日期之间的所有天数字符串
   *
   * @param beginDate
   * @param endDate
   * @return
   * @throws ParseException
   */
  public static String[] getTwoDateDiffer(String beginDate, String endDate)
      throws ParseException {

    SimpleDateFormat timeFormatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD);
    Date dateBegin = timeFormatter.parse(beginDate);
    Date dateEnd = timeFormatter.parse(endDate);
    long millionsecondsInOneDay = (24 * 3600 * 1000);
    long distinctionDays = (dateEnd.getTime() - dateBegin.getTime())
        / millionsecondsInOneDay + 1;

    String[] dateArray = new String[new Long(distinctionDays).intValue()];

    if (distinctionDays == 1) {
      dateArray[0] = timeFormatter.format(dateBegin);
    } else {
      for (int i = 0; i < distinctionDays; i++) {
        Date tempDate = new Date(dateBegin.getTime() + i
            * millionsecondsInOneDay);
        dateArray[i] = timeFormatter.format(tempDate);
      }
    }
    return dateArray;

  }

  /**
   * 字符型日期转化 Thu Apr 29 08:57:29 CST 2004转换成2004-05-04 08:57:29
   *
   * @param stringdate
   * @return
   */
  public static String cSTDateTransDate(String stringdate) {
    StringBuffer resultDate = new StringBuffer();
    resultDate.append(stringdate.subSequence(0, 20)).append(
        stringdate.substring(24, 28));

    Date date = new Date(resultDate.toString());
    SimpleDateFormat sf = new SimpleDateFormat(DATETIME_FORMAT);

    return sf.format(date);
  }

  /**
   * 计算某日期与当前日期的相差天数
   *
   */
  public static int dateTransToDayNum(String date) {
    Date date1 = stringToDate(date, DATE_FORMAT_YYYYMMDD);
    int dayNum = date1.getDate() + date1.getMonth() * 30
        + (date1.getYear() + 1900 - 2003) * 365;
    int nowDayNum = getDate().getDate() + getDate().getMonth() * 30
        + (getDate().getYear() + 1900 - 2003) * 365;

    return (nowDayNum - dayNum);

  }

  /**
   * 判断某个日期是否跟另一个日期一样
   */
  public static int dateCompareDate(Date oneDate, Date twodate) {

    SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD);
    return (stringToDate(sf.format(oneDate), DATE_FORMAT_YYYYMMDD))
        .compareTo(stringToDate(sf.format(twodate), DATE_FORMAT_YYYYMMDD));
  }

  /**
   * 返回字符串中的数字
   *
   * @param str
   * @return
   */
  public static String getNumByString(String str) {
    String numbers = null;
    Pattern p = Pattern.compile("([0-9])+");
    Matcher m = p.matcher(str);
    if (m.find()) {
      numbers = m.group();
    }
    return numbers;
  }

  public static String addSlashes(String str, String slashes) {
    StringBuffer buf = null;
    int len = str.length();
    boolean is_change = false;
    for (int i = 0; i < len; i++) {
      char c = str.charAt(i);
      if (is_change == false) {
        if (c == '\\') {
          is_change = true;
          buf = new StringBuffer();
          buf.append(str.substring(0, i));
          buf.append("\\\\");
          continue;
        }
        if (slashes.indexOf(c) != -1) {
          is_change = true;
          buf = new StringBuffer();
          buf.append(str.substring(0, i));
          buf.append('\\');
          buf.append(c);
          continue;
        }
      } else {
        if (c == '\\') {
          buf.append('\\');
        }
        if (slashes.indexOf(c) != -1) {
          buf.append('\\');
        }
        buf.append(c);
      }
    }
    if (is_change == false) {
      return str;
    } else {
      return buf.toString();
    }
  }

  public static String escapeDot(String str) {
    if (str == null) {
      return "";
    }
    String dot = ".";
    StringTokenizer st = new StringTokenizer(str, dot);
    StringBuffer sb = new StringBuffer();
    while (st.hasMoreTokens()) {
      sb.append(st.nextToken());
    }
    return sb.toString();
  }

  public static String escapSlashes(String str) {
    StringBuffer buf = null;
    int len = str.length();
    boolean is_change = false;
    len--;
    for (int i = 0; i < len; i++) {
      char c = str.charAt(i);
      if (is_change) {
        if (c == '\\') {
          i++;
          c = str.charAt(i);
          buf.append(c);
        }
      } else {
        if (c == '\\') {
          is_change = true;
          buf = new StringBuffer();
          buf.append(str.substring(0, i));
          i++;
          c = str.charAt(i);
          buf.append(c);
        }
      }
    }
    if (is_change == false) {
      return str;
    } else {
      return buf.toString();
    }
  }

  public static String convertDateFormat(String strd, String f1, String f2) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(f1);
      SimpleDateFormat sdf2 = new SimpleDateFormat(f2);
      return sdf2.format(sdf.parse(strd));
    } catch (Exception ex) {
      return null;
    }
  }

  /**
   * 检查手机号码,剔除联通用户以及非法手机号码
   *
   * @param phone
   * @return String
   * @deprecated
   */

  public static String checkPhone(String phone) {
    if (phone == null || phone.length() == 0)
      return "error.phone.null";
    else if (phone.length() != 11)
      return "error.phone.length";
    else if (phone.startsWith("130") || phone.startsWith("131")
        || phone.startsWith("133"))
      return "error.phone.unicom";
    return null;
  }

  /**
   * 判断str长度，不足length长度
   * 
   * @param str
   * @param length
   * @return
   */
  public static String adapterStrLength(String str, int length) {
    if (str.length() > length) {
      return str;
    } else {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < length; i++) {
        sb.append("0");
      }
      sb = sb.append(str);
      return sb.substring(sb.length() - length).toString();
    }
  }

  /**
   * 获取固定长度随即数字
   * 
   * @param length
   * @return
   */
  public static String getRandom(int length) {
    String random = "";
    for (int i = 0; i < length; i++) {
      random += String.valueOf(RandomUtils.nextInt(10));
    }
    return random;

  }

  /**
   * 把map转换成url参数格式 例如a=1&b=2&c=3
   *
   * @param param
   * @param needEncode
   *          是否需要对value做encode
   * @return
   */
  public static String mapToUrlPram(Map<String, String> param,
      boolean needEncode) {
    String result = "";
    if (param == null || param.size() == 0) {
      return result;
    }
    StringBuilder str = new StringBuilder();
    if (param != null && param.size() > 0) {
      for (String key : param.keySet()) {
        String value = param.get(key);
        if (!DateUtil.isEmptyString(key) && !DateUtil.isEmptyString(value)) {
          if (needEncode) {
            try {
              value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
              log.error("对paramMap的value编码失败", e);
              continue;
            }
          }
          if (DateUtil.isEmptyString(key) || DateUtil.isEmptyString(value))
            continue;
          str.append("&").append(key).append("=").append(value);
        }
      }
    }
    if (str.length() > 0) {
      result = str.substring(1);
    }
    return result;
  }

  /**
   * 解析url的参数格式 对每个参数的value进行编码，
   * 
   * @param url
   * @param charset
   * @return
   * @throws UnsupportedEncodingException
   */
  public static String encodeUrl(String url, String charset)
      throws UnsupportedEncodingException {
    if (url == null) {
      return "";
    }
    int index = url.indexOf("?");
    if (index <= 0) {
      return url;
    }
    String paramsStr = url.substring(index + 1);
    if (paramsStr == null || paramsStr.trim().length() == 0) {
      return url;
    }

    String[] paramsArray = paramsStr.split("&");
    if (paramsArray.length == 0) {
      return url;
    }

    String urlBegin = url.substring(0, index);
    StringBuilder urlParams = new StringBuilder();

    for (String param : paramsArray) {
      String[] paramArray = param.split("=");
      if (paramArray.length != 2) {
        continue;
      }
      String name = paramArray[0];
      String value = paramArray[1];
      if (name != null && value != null) {
        name = URLEncoder.encode(name, charset);
        value = URLEncoder.encode(value, charset);
        urlParams.append("&").append(name).append("=").append(value);
      }
    }
    String urlNew = urlBegin;
    if (urlParams.length() > 0) {
      urlNew += "?" + urlParams.substring(1);
    }
    return urlNew;
  }

  /**
   * 类似String.valueOf() 当传入参数为null时，返回""而不是"null"
   * 
   * @param object
   * @return
   */
  public static String valueOfEmpty(Object object) {
    if (object == null)
      return "";
    return object.toString();
  }

  /**
   * 将数组转化成以分隔符连接的字符串
   */
  public static String arrayToString(Object[] o, String split) {
    String str = "";
    if (o != null && o.length > 0) {
      for (int i = 0; i < o.length; i++) {
        str = str + String.valueOf(o[i]);
        if (i < o.length - 1) {
          str = str + split;
        }
      }
    }
    return str;
  }

}
