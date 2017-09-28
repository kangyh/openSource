package com.heepay.manage.modules.payment.entity;

import java.util.List;

public class ChartRecord {

	private String time;
	private List<StatisticsRecord> records;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<StatisticsRecord> getRecords() {
		return records;
	}
	public void setRecords(List<StatisticsRecord> records) {
		this.records = records;
	}
	
	
}
