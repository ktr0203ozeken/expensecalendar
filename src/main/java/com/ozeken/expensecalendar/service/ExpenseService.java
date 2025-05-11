package com.ozeken.expensecalendar.service;

import java.util.List;
import java.util.Map;

import com.ozeken.expensecalendar.dto.ExpenseWithGenre;
import com.ozeken.expensecalendar.entity.Expense;

public interface ExpenseService {

	// ジャンル名付きの支出一覧を取得（一覧画面用）
	List<ExpenseWithGenre> findAllWithGenre(Long userId);
		
	// ジャンル名付きの支出を取得（詳細画面用）
	ExpenseWithGenre findByIdWithGenre(Long id, Long userId);
	
	// ID で取得（ユーザー指定）
	Expense findById(Long id, Long userId);
	
	// 月別取得（ユーザー指定）
	List<ExpenseWithGenre> findByMonth(Long userId, int year, int month);
	
	// 年月で取得し、日にちでグループ化（ユーザー指定）
	Map<Integer, List<ExpenseWithGenre>> groupByDayOfMonth(Long userId, int currentYear, int currentMonth);
	
	// 新規登録（Expense に userId を含む前提）
	void insert(Expense expense);

	// 更新
	void update(Expense expense);

	// 削除
	void delete(Long id, Long userId);
	
}
