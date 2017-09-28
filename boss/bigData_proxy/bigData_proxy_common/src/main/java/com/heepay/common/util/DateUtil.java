package com.heepay.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static Date getStrDate(String strDate, String type) throws ParseException {
		Date date = null;
		if ((strDate != null) && (strDate != "")) {
			SimpleDateFormat df = new SimpleDateFormat(type);
			date = df.parse(strDate);
		}
		return date;
	}
	
	
	public static String dateToString(Date date, String type) throws ParseException {
		String dateString=null;
		if(date == null){
			return null;
		}
		if ((date != null)) {
			SimpleDateFormat df = new SimpleDateFormat(type);
			dateString = df.format(date);
		}
		return dateString;
	}
	
	
	public static long dateToMillion(String date,String type) throws ParseException{
		long timeStart=0;
		if(date == null){
			return 0;
		}
		if((date != null)){
			SimpleDateFormat sdf=new SimpleDateFormat(type);
			 timeStart=sdf.parse(date).getTime();
		}
		return timeStart;
	}
	
	public static void main(String[] args) {
		try {
			long date = DateUtil.dateToMillion("2017-06-07 09:24:30","yyyy-MM-dd HH:mm:ss");
			System.out.println(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
