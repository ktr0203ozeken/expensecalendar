package com.ozeken.expensecalendar.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ozeken.expensecalendar.dto.DailyTotal;
import com.ozeken.expensecalendar.dto.ExpenseWithGenre;
import com.ozeken.expensecalendar.entity.Expense;

@Mapper
public interface ExpenseMapper {
	
	// ジャンル名付きの支出一覧を取得（JOIN）
	List<ExpenseWithGenre> selectWithGenreByUserId(@Param("userId") Long userId);
	
	// ジャンル名付きの支出を取得（JOIN）
	ExpenseWithGenre selectWithGenreByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
	
	// ID で取得（ユーザー指定）
	Expense selectByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
	
	// 月別取得
	List<ExpenseWithGenre> selectByMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);
	
	//日付合計取得
	List<DailyTotal> selectDailyTotalByMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);
	
	// 新規登録
	void insert(Expense expense);

	// 更新（userIdを含むバリデーションはサービス層で行う）
	void update(Expense expense);

	// IDとuserIdで削除（安全な削除）
	void deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

}
