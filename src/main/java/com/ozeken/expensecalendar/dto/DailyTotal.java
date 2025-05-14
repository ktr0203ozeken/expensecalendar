package com.ozeken.expensecalendar.dto;

import lombok.Data;

@Data
public class DailyTotal {
	
	// 日付
	private int day;
	
	// 合計金額
	private Integer total;
	
}