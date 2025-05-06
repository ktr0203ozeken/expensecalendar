package com.ozeken.expensecalendar.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Expense {

	// ID
	private Long id;

	// ユーザーID (外部キー)
	private Long userId;

	// 日付
	private LocalDate date;

	// カテゴリ
	private String category;

	// 金額
	private Integer amount;

	// 説明
	private String description;

}
