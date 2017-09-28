package com.heepay.prom.modules.prom.utils;

import com.heepay.date.DateUtils;

import java.util.Date;

public class BindingId {
	
	public static String getBindingId(){
		StringBuilder sb = new StringBuilder();
		sb.append("BM");
		String dateStr = DateUtils.getDateStr(new Date(), "yyyyMMddHHmmss");
		sb.append(dateStr);
		int n = 6;
		String numberStr = getNumberStr(n);
		sb.append(numberStr);
		return sb.toString();
	}
	
	private static String getNumberStr(int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			String randChar = String.valueOf(Math.round(Math.random() * 9));
			sb.append(randChar);
		}
		return sb.toString();

	}
	
	public static String getTempletId(){
		StringBuilder sb = new StringBuilder();
		sb.append("MBH");
		String dateStr = DateUtils.getDateStr(new Date(), "yyyyMMddHHmmss");
		sb.append(dateStr);
		int n = 6;
		String numberStr = getNumberStr(n);
		sb.append(numberStr);
		return sb.toString();
	}

}
