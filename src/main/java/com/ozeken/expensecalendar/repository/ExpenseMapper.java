package com.ozeken.expensecalendar.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ozeken.expensecalendar.entity.Expense;

@Mapper
public interface ExpenseMapper {

	// 全件取得
	List<Expense> selectAll();

	// IDで一件取得
	Expense selectById(Long id);

	// 新規登録
	void insert(Expense expense);

	// 更新
	void update(Expense expense);

	// 削除
	void delete(Long id);

}
