package com.ozeken.expensecalendar.service;

import java.util.List;
import java.util.Map;

import com.ozeken.expensecalendar.entity.Expense;

public interface ExpenseService {

	// ログインユーザーの全件取得
	List<Expense> findAll(Long userId);
	
	// 月別取得（ユーザー指定）
	List<Expense> findByMonth(Long userId, int year, int month);
	
	// 年月で取得し、日にちでグループ化（ユーザー指定）
	Map<Integer, List<Expense>> groupByDayOfMonth(Long userId, int currentYear, int currentMonth);
	
	// IDで一件取得
	Expense findById(Long id, Long userId);

	// 新規登録（Expense に userId を含む前提）
	void insert(Expense expense);

	// 更新
	void update(Expense expense);

	// 削除
	void delete(Long id, Long userId);
}
