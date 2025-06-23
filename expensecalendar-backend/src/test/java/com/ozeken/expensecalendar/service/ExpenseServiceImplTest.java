package com.ozeken.expensecalendar.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.ozeken.expensecalendar.dto.ExpenseWithGenre;
import com.ozeken.expensecalendar.entity.Expense;
import com.ozeken.expensecalendar.service.impl.ExpenseServiceImpl;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ExpenseServiceImplTest {
	
	// ------- 定数 -------
	private final Long TEST_USER_ID = 1L;

	@Autowired
	private ExpenseServiceImpl expenseService;

	@Test
	void shouldGetAllExpenses() {
		List<ExpenseWithGenre> expenses = expenseService.findPagedExpensesByPage(TEST_USER_ID, 1, 100);
		assertThat(expenses).isNotNull();
	}

	@Test
	void shouldInsertExpense() {
		Expense expense = new Expense();
		expense.setUserId(TEST_USER_ID);
		expense.setDate(LocalDate.now());
		expense.setGenreId(1);
		expense.setAmount(1234L);
		expense.setDescription("テスト登録");

		expenseService.insert(expense);

		List<ExpenseWithGenre> expenses = expenseService.findPagedExpensesByPage(TEST_USER_ID, 1, 100);
		assertThat(expenses)
			.extracting(ExpenseWithGenre::getDescription)
			.contains("テスト登録");
	}

	@Test
	void shouldUpdateExpense() {
		Expense expense = new Expense();
		expense.setUserId(TEST_USER_ID);
		expense.setDate(LocalDate.now());
		expense.setGenreId(1);
		expense.setAmount(5678L);
		expense.setDescription("更新前説明");

		expenseService.insert(expense);

		ExpenseWithGenre target = expenseService.findPagedExpensesByPage(TEST_USER_ID, 1, 100).stream()
			.filter(e -> "更新前説明".equals(e.getDescription()))
			.findFirst()
			.orElseThrow(() -> new AssertionError("更新前説明が見つかりません"));

		expense.setId(target.getId());
		expense.setDescription("更新後説明");
		expenseService.update(expense);

		Expense updated = expenseService.findById(target.getId(), TEST_USER_ID);
		assertThat(updated.getDescription()).isEqualTo("更新後説明");
	}

	@Test
	void shouldDeleteExpense() {
		Expense expense = new Expense();
		expense.setUserId(TEST_USER_ID);
		expense.setDate(LocalDate.now());
		expense.setGenreId(1);
		expense.setAmount(9999L);
		expense.setDescription("削除対象");

		expenseService.insert(expense);

		ExpenseWithGenre target = expenseService.findPagedExpensesByPage(TEST_USER_ID, 1, 100).stream()
			.filter(e -> "削除対象".equals(e.getDescription()))
			.findFirst()
			.orElseThrow(() -> new AssertionError("削除対象が見つかりません"));

		expenseService.deleteByIdAndUserId(target.getId(), TEST_USER_ID);

		Expense deleted = expenseService.findById(target.getId(), TEST_USER_ID);
		assertThat(deleted).isNull();
	}
}
