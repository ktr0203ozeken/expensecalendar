package com.ozeken.expensecalendar.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ExpenseForm {

	// ID
	private Long id;

	// 日付
	@NotNull(message = "日付を入力して下さい")
	private LocalDate date;

	// カテゴリ
	@NotNull(message = "カテゴリを入力してください")
	@Size(max = 50, message = "カテゴリは50文字以内で入力してください")
	private String category;

	// 金額
	@NotNull(message = "金額を入力してください")
	private Integer amount;

	// 説明
	private String description;

}
