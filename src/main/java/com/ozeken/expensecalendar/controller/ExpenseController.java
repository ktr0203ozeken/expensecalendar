package com.ozeken.expensecalendar.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ozeken.expensecalendar.entity.Expense;
import com.ozeken.expensecalendar.form.ExpenseForm;
import com.ozeken.expensecalendar.helper.ExpenseHelper;
import com.ozeken.expensecalendar.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

	/** DI */
	private final ExpenseService expenseService;

	/** 家計簿一覧表示 */
	@GetMapping
	public String listExpenses(Model model) {
		List<Expense> expenses = expenseService.findAll();
		model.addAttribute("expenses", expenses);
		return "expenses/list";
	}

	/** 新規登録フォーム表示 */
	@GetMapping("/new")
	public String showCreateForm(Model model) {
		model.addAttribute("expenseForm", new ExpenseForm());
		return "expenses/form";
	}

	/** 編集フォーム表示 */
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		Expense expense = expenseService.findById(id);
		ExpenseForm expenseForm = ExpenseHelper.convertExpenseForm(expense);
		model.addAttribute("expenseForm", expenseForm);
		return "expenses/form";
	}
	
	/** 詳細表示 */
	@GetMapping("/{id}")
	public String showExpenseDetail(@PathVariable("id") Long id, Model model) {
	    Expense expense = expenseService.findById(id);
	    model.addAttribute("expense", expense);
	    return "expenses/detail";
	}

	/** 登録または更新 */
	@PostMapping
	public String saveOrUpdateExpense(
			@Valid @ModelAttribute ExpenseForm expenseForm, 
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("expenseForm", expenseForm);
			return "expenses/form";
		}

		Expense expense = ExpenseHelper.convertExpense(expenseForm);

		if (expense.getId() == null) {
			expenseService.insert(expense);
		} else {
			expenseService.update(expense);
		}

		return "redirect:/expenses";
	}

	/** データ削除 */
	@GetMapping("/delete/{id}")
	public String deleteExpense(@PathVariable("id") Long id) {
		expenseService.delete(id);
		return "redirect:/expenses";
	}

}
