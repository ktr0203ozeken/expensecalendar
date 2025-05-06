package com.ozeken.expensecalendar.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ozeken.expensecalendar.entity.Expense;

@SpringBootTest
@ActiveProfiles("test")
class ExpenseServiceImplTest {

	@Autowired
	private ExpenseServiceImpl expenseService;

	// テスト用のユーザーID（テストDBに事前に存在する想定）
	private final Long TEST_USER_ID = 1L;

	@Test
	void shouldGetAllExpenses() {
		// 全件取得
		List<Expense> expenses = expenseService.findAll(TEST_USER_ID);
		// nullでないことを確認
		assertThat(expenses).isNotNull();
	}

	@Test
	void shouldInsertExpense() {
		// 登録
		Expense expense = new Expense();
		expense.setUserId(TEST_USER_ID); // ★ ユーザーIDをセット
		expense.setDate(LocalDate.now());
		expense.setCategory("テストカテゴリ");
		expense.setAmount(1234);
		expense.setDescription("テスト登録");

		expenseService.insert(expense);

		List<Expense> expenses = expenseService.findAll(TEST_USER_ID);
		assertThat(expenses)
				// 登録したデータが取得できること
				.extracting(Expense::getCategory).contains("テストカテゴリ");
	}

	@Test
	void shouldUpdateExpense() {
		// 更新対象を作成
		Expense expense = new Expense();
		expense.setUserId(TEST_USER_ID);
		expense.setDate(LocalDate.now());
		expense.setCategory("更新前カテゴリ");
		expense.setAmount(5678);
		expense.setDescription("更新前説明");

		expenseService.insert(expense);

		List<Expense> expenses = expenseService.findAll(TEST_USER_ID);
		Expense target = expenses.get(expenses.size() - 1);
		target.setCategory("更新後カテゴリ");

		// 更新
		expenseService.update(target);

		Expense updated = expenseService.findById(target.getId(), TEST_USER_ID);
		// カテゴリが更新されていることを確認
		assertThat(updated.getCategory()).isEqualTo("更新後カテゴリ");
	}

	@Test
	void shouldDeleteExpense() {
		// 削除対象を作成
		Expense expense = new Expense();
		expense.setUserId(TEST_USER_ID);
		expense.setDate(LocalDate.now());
		expense.setCategory("削除対象カテゴリ");
		expense.setAmount(9999);
		expense.setDescription("削除対象");

		expenseService.insert(expense);

		List<Expense> expenses = expenseService.findAll(TEST_USER_ID);
		Expense target = expenses.get(expenses.size() - 1);

		// 削除
		expenseService.delete(target.getId(), TEST_USER_ID);

		Expense deleted = expenseService.findById(target.getId(), TEST_USER_ID);
		// 削除されていることを確認
		assertThat(deleted).isNull();
	}
}
