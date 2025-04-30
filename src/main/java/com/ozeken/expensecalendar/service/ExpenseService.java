package com.ozeken.expensecalendar.service;

import java.util.List;
import java.util.Map;

import com.ozeken.expensecalendar.entity.Expense;

public interface ExpenseService {

	// 全件取得
	List<Expense> findAll();
	
	// 月別取得
	List<Expense> findByMonth(int year, int month);
	
	//年月で取得し、日にちでグループ化
	Map<Integer, List<Expense>> groupByDayOfMonth(int currentYear, int currentMonth);
	
	// IDで一件取得
	Expense findById(Long id);

	// 新規登録
	void insert(Expense expense);

	// 更新
	void update(Expense expense);

	// 削除
	void delete(Long id);

}
