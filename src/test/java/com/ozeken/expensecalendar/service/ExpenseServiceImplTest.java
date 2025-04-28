package com.ozeken.expensecalendar.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ozeken.expensecalendar.entity.Expense;

@SpringBootTest
class ExpenseServiceImplTest {

	@Autowired
	private ExpenseServiceImpl expenseService;

	@Test
	void shouldGetAllExpenses() {
		List<Expense> expenses = expenseService.findAll();
		// nullでないことを確認
		assertThat(expenses).isNotNull();
	}

	@Test
	void shouldInsertExpense() {

		// 登録
		Expense expense = new Expense();
		expense.setDate(LocalDate.now());
		expense.setCategory("テストカテゴリ");
		expense.setAmount(1234);
		expense.setDescription("テスト登録");

		expenseService.insert(expense);

		List<Expense> expenses = expenseService.findAll();
		assertThat(expenses)
				// 登録したデータが取得できること
				.extracting(Expense::getCategory).contains("テストカテゴリ");
	}

	@Test
	void shouldUpdateExpense() {
		Expense expense = new Expense();
		expense.setDate(LocalDate.now());
		expense.setCategory("更新前カテゴリ");
		expense.setAmount(5678);
		expense.setDescription("更新前説明");

		expenseService.insert(expense);

		List<Expense> expenses = expenseService.findAll();
		Expense target = expenses.get(expenses.size() - 1);
		target.setCategory("更新後カテゴリ");

		expenseService.update(target);

		Expense updated = expenseService.findById(target.getId());
		assertThat(updated.getCategory()).isEqualTo("更新後カテゴリ");
	}

	@Test
	void shouldDeleteExpense() {
		Expense expense = new Expense();
		expense.setDate(LocalDate.now());
		expense.setCategory("削除対象カテゴリ");
		expense.setAmount(9999);
		expense.setDescription("削除対象");

		expenseService.insert(expense);

		List<Expense> expenses = expenseService.findAll();
		Expense target = expenses.get(expenses.size() - 1);

		// 削除
		expenseService.delete(target.getId());

		Expense deleted = expenseService.findById(target.getId());
		assertThat(deleted).isNull();
	}
}
