package com.ozeken.expensecalendar.form;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ExpenseForm {

	// ID
	private Long id;

	// ユーザーID（外部キー）
	private Long userId;

	// 日付
	@NotNull(message = "日付を入力して下さい")
	private LocalDate date;

	// カテゴリ
	@NotBlank(message = "カテゴリを入力してください")
	@Size(max = 50, message = "カテゴリは50文字以内で入力してください")
	private String category;

	// 金額
	@NotNull(message = "金額を入力してください")
	@Min(value = 1, message = "金額は1以上で入力してください")
	@Max(value = 2147483647, message = "金額は2147483647以下で入力してください")
	private Integer amount;

	// 説明
	@Size(max = 255, message = "説明は255文字以内で入力してください")
	private String description;

}
