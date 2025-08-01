package com.ozeken.expensecalendar.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ozeken.expensecalendar.dto.ExpenseWithGenre;
import com.ozeken.expensecalendar.entity.Expense;
import com.ozeken.expensecalendar.entity.Genre;
import com.ozeken.expensecalendar.entity.LoginUser;
import com.ozeken.expensecalendar.form.ExpenseForm;
import com.ozeken.expensecalendar.helper.ExpenseHelper;
import com.ozeken.expensecalendar.service.ExpenseService;
import com.ozeken.expensecalendar.service.GenreService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

	/** DI */
	private final ExpenseService expenseService;
	private final GenreService genreService;

	/**
	 *  家計簿一覧表示（ページネーション対応）
	 */
	@GetMapping
	public String listExpenses(@RequestParam(name = "page", defaultValue = "1") int page,
			                                 @RequestParam(name = "size", defaultValue = "20") int size,
			                                 Model model, 
			                                 @AuthenticationPrincipal LoginUser loginUser) { 
		Long userId = loginUser.getAppUser().getId();
		List<ExpenseWithGenre> expenses = expenseService.findPagedExpensesByPage(userId, page, size);
		
		// 1ページより大きいなら、減算する。
		int prevPage = page > 1 ? page - 1 : 1;
		
		// デフォルト値と同じなら、加算する。
		boolean hasNext = expenses.size() == size;
		int nextPage = hasNext ? page +1 : page;
		
		// ジャンルリストを取得
		List<Genre> genres = genreService.findAllGenre();
		
		model.addAttribute("expenses", expenses);
		model.addAttribute("genres", genres);
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("prevPage", prevPage);
		model.addAttribute("nextPage", nextPage);
		model.addAttribute("hasNext", hasNext);
		
		return "expenses/list";
	}

	/** 新規登録フォーム表示 */
	@GetMapping("/new")
	public String showCreateForm(Model model) {
		model.addAttribute("expenseForm", new ExpenseForm());
		model.addAttribute("postUrl", "/expenses");
		model.addAttribute("returnUrl", "/expenses");
		
		return "expenses/form";
	}
	
	/** 
	 * ジャンル別支出一覧表示（ページネーション対応）
	 */
	@GetMapping("/genre")
	public String listExpensesFilterGenre(@RequestParam(name = "page", defaultValue = "1") int page,
	                                                          @RequestParam(name = "size", defaultValue = "20") int size,
										                      @RequestParam("genreId") Integer genreId,
										                      Model model,
										                      @AuthenticationPrincipal LoginUser loginUser) {

		Long userId = loginUser.getAppUser().getId();
		List<ExpenseWithGenre> expenses = expenseService.findPagedExpensesByUserIdAndGenreId(userId, genreId, page,
				size);

		// 1ページより大きいなら、減算する。
		int prevPage = page > 1 ? page - 1 : 1;
		// デフォルト値と同じなら、加算する。
		boolean hasNext = expenses.size() == size;
		int nextPage = hasNext ? page + 1 : page;
		
		Genre genre = genreService.selectGenreById(genreId);
		String genreName = genre.getName();

		model.addAttribute("expenses", expenses);
		model.addAttribute("genreName", genreName);
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("prevPage", prevPage);
		model.addAttribute("nextPage", nextPage);
		model.addAttribute("hasNext", hasNext);
		model.addAttribute("genreId", genreId);

		return "expenses/genre";
	}

	/** 編集フォーム表示 */
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model,
			                                          @AuthenticationPrincipal LoginUser loginUser) {
		Long userId = loginUser.getAppUser().getId();
		Expense expense = expenseService.findById(id, userId);
		if (expense == null) {
			return "redirect:/expenses";
		}
		ExpenseForm expenseForm = ExpenseHelper.convertToExpenseForm(expense);
		model.addAttribute("expenseForm", expenseForm);
		model.addAttribute("postUrl", "/expenses");
		model.addAttribute("returnUrl", "/expenses");
		return "expenses/form";
	}
		
	/** 詳細表示 */
	@GetMapping("/{id}")
	public String showExpenseDetail(@PathVariable("id") Long id, Model model,
	                                @AuthenticationPrincipal LoginUser loginUser) {
	    Long userId = loginUser.getAppUser().getId();
	    ExpenseWithGenre expense = expenseService.findByIdWithGenre(id, userId);
	    model.addAttribute("expense", expense);
	    model.addAttribute("returnUrl", "/expenses");
	    return "expenses/detail";
	}

	/** 登録または編集処理 */
	@PostMapping
	public String saveOrUpdateExpense(
			@Valid @ModelAttribute ExpenseForm expenseForm, 
			BindingResult result,
			Model model,
			@AuthenticationPrincipal LoginUser loginUser) {
		
		if (result.hasErrors()) {
			model.addAttribute("expenseForm", expenseForm);
			model.addAttribute("postUrl", "/expenses");
			model.addAttribute("returnUrl", "/expenses");
			return "expenses/form";
		}

		Long userId = loginUser.getAppUser().getId();
		expenseForm.setUserId(userId);
		Expense expense = ExpenseHelper.convertToExpense(expenseForm);

		try {
		if (expense.getId() == null) {
			expenseService.insert(expense);
		} else {
			expenseService.update(expense);
		}
	}catch (IllegalArgumentException e) {
	//オーバーフローが発生した場合のエラーメッセージを設定
		model.addAttribute("errorMessage", e.getMessage());
		model.addAttribute("expenseForm", expenseForm);
		model.addAttribute("postUrl", "/expenses");
		model.addAttribute("returnUrl", "/expenses");
		return "expenses/form";
		}

		return "redirect:/expenses";
	}

	/** データ削除 */
	@GetMapping("/delete/{id}")
	public String deleteExpense(@PathVariable("id") Long id,
			                                     @AuthenticationPrincipal LoginUser loginUser) {
		Long userId = loginUser.getAppUser().getId();
		expenseService.deleteByIdAndUserId(id,userId);
		return "redirect:/expenses";
	}

}
