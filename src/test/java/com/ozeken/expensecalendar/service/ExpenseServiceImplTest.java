package com.ozeken.expensecalendar.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ozeken.expensecalendar.dto.ExpenseWithGenre;
import com.ozeken.expensecalendar.entity.Expense;
import com.ozeken.expensecalendar.service.impl.ExpenseServiceImpl;

@SpringBootTest
@ActiveProfiles("test")
class ExpenseServiceImplTest {

	@Autowired
	private ExpenseServiceImpl expenseService;

	// テスト用のユーザーID
	private final Long TEST_USER_ID = 1L;

	@Test
	void shouldGetAllExpenses() {
		// 全件取得
		List<ExpenseWithGenre> expenses = expenseService.findAllWithGenre(TEST_USER_ID);
		// nullでないことを確認
		assertThat(expenses).isNotNull();
	}

	@Test
	void shouldInsertExpense() {
		// 登録
		Expense expense = new Expense();
		expense.setUserId(TEST_USER_ID);
		expense.setDate(LocalDate.now());
		expense.setGenreId(1);
		expense.setAmount(1234L);
		expense.setDescription("テスト登録");

		expenseService.insert(expense);

		List<ExpenseWithGenre> expenses = expenseService.findAllWithGenre(TEST_USER_ID);
		assertThat(expenses)
				.extracting(e -> e.getDescription())
				.contains("テスト登録");
	}

	@Test
	void shouldUpdateExpense() {
		// 更新対象を作成
		Expense expense = new Expense();
		expense.setUserId(TEST_USER_ID);
		expense.setDate(LocalDate.now());
		expense.setGenreId(1);
		expense.setAmount(5678L);
		expense.setDescription("更新前説明");

		expenseService.insert(expense);
		
		List<ExpenseWithGenre> expenses = expenseService.findAllWithGenre(TEST_USER_ID);
		ExpenseWithGenre target = expenses.get(expenses.size() - 1);
		expense.setId(target.getId());
		expense.setDescription("更新後説明");

		// 更新
		expenseService.update(expense);

		Expense updated = expenseService.findById(target.getId(), TEST_USER_ID);
		// カテゴリが更新されていることを確認
		assertThat(expense.getDescription()).isEqualTo(updated.getDescription());
	}

	@Test
	void shouldDeleteExpense() {
		// 削除対象を作成
		Expense expense = new Expense();
		expense.setId(1L);
		expense.setUserId(TEST_USER_ID);
		expense.setDate(LocalDate.now());
		expense.setGenreId(1);
		expense.setAmount(9999L);
		expense.setDescription("削除対象");

		expenseService.insert(expense);

		List<ExpenseWithGenre> expenses = expenseService.findAllWithGenre(TEST_USER_ID);
		ExpenseWithGenre target = expenses.get(expenses.size() - 1);

		// 削除
		expenseService.delete(target.getId(), TEST_USER_ID);

		Expense deleted = expenseService.findById(target.getId(), TEST_USER_ID);
		// 削除されていることを確認
		assertThat(deleted).isNull();
	}
}
