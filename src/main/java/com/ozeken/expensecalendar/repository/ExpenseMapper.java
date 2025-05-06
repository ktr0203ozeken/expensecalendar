package com.ozeken.expensecalendar.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ozeken.expensecalendar.entity.Expense;

@Mapper
public interface ExpenseMapper {

	// ログインユーザーの全件取得
	List<Expense> selectAll(@Param("userId") Long userId);
	
	// 月別取得
	List<Expense> selectByMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);

	// IDとuserIdで取得（安全な取得）
	Expense selectByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

	// 新規登録
	void insert(Expense expense);

	// 更新（userIdを含むバリデーションはサービス層で行う）
	void update(Expense expense);

	// IDとuserIdで削除（安全な削除）
	void deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}
