package com.ozeken.expensecalendar.controller;

import java.time.LocalDate;
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
import com.ozeken.expensecalendar.entity.LoginUser;
import com.ozeken.expensecalendar.form.ExpenseForm;
import com.ozeken.expensecalendar.helper.ExpenseHelper;
import com.ozeken.expensecalendar.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/expenses/day")
@RequiredArgsConstructor
public class DayExpenseController{
	
	/** DI */
	private final ExpenseService expenseService;

    /**
     * 指定年月日（1日分）の支出一覧（ジャンル名付き）を表示
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @param loginUser ログインユーザー情報
     * @param model モデル
     * @return 詳細表示テンプレート
     */
    @GetMapping
    public String showExpensesByDay(
    		@RequestParam int year,
    		@RequestParam int month,
    		@RequestParam int day,
    		@AuthenticationPrincipal LoginUser loginUser,
    		Model model) {
        if (loginUser.getAppUser() == null) {
            return "redirect:/login";
        }

        Long userId = loginUser.getAppUser().getId();
        List<ExpenseWithGenre> expenses = expenseService.findWithGenreByDay(userId, year, month, day);

        model.addAttribute("expenses", expenses);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);

        return "expenses/day";
    }
    
    	/**
    	 * 新規登録フォーム表示
    	 * 
    	 * @param year 年
    	 * @param month 月
    	 * @param day 日
    	 * @param model モデル
    	 * @return 新規登録フォーム
    	 */
	@GetMapping("/new")
	public String showCreateFormByDay (
			@RequestParam int year,
			@RequestParam int month,
			@RequestParam int day,
			Model model ) {
		
		ExpenseForm form = new ExpenseForm();
		form.setDate(LocalDate.of(year, month, day));
		String returnUrl = String.format("/expenses/day?year=%d&month=%d&day=%d", year, month, day);
	    
		model.addAttribute("expenseForm",form);
		model.addAttribute("postUrl", "/expenses/day");
		model.addAttribute("returnUrl", returnUrl);
		
		return "expenses/form";
	}
	
	/** 
	 * 編集フォーム表示
	 * 
	 * @param id 支出ID
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param model モデル
	 * @return 編集フォーム
	 */
	@GetMapping("/edit/{id}")
	public String showUpdateFormByDay (
			@PathVariable("id") Long id,
			@RequestParam int year,
			@RequestParam int month,
			@RequestParam int day,
			Model model,
			@AuthenticationPrincipal LoginUser loginUser) {
		
		Long userId = loginUser.getAppUser().getId();
		Expense expense = expenseService.findById(id, userId);
		
		if (expense==null) {
			model.addAttribute("year", year);
			model.addAttribute("month", month);
			model.addAttribute("day", day);
			
			return "redirect:expenses/day";
		}
		ExpenseForm expenseForm = ExpenseHelper.convertToExpenseForm(expense);
		String returnUrl = String.format("/expenses/day?year=%d&month=%d&day=%d", year, month, day);
		
		model.addAttribute("expenseForm", expenseForm);
		model.addAttribute("postUrl", "/expenses/day");
		model.addAttribute("returnUrl", returnUrl);
		return "expenses/form";
	}
	/**
	 * 詳細表示
	 * 
	 * @param id 支出ID
	 * @param model モデル
	 * @param loginUser ログインユーザー
	 * 
	 * @return 詳細表示画面
	 */
	@GetMapping("/{id}")
	public String showExpenseDetailByDay (
			@PathVariable("id") Long id,
			@RequestParam int year,
			@RequestParam int month,
			@RequestParam int day,
			Model model,
			@AuthenticationPrincipal LoginUser loginUser) {
		
		Long userId = loginUser.getAppUser().getId();
		ExpenseWithGenre expense = expenseService.findByIdWithGenre(id, userId);
		
		String returnUrl = String.format("/expenses/day?year=%d&month=%d&day=%d", year, month, day);
		
		model.addAttribute("expense", expense);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);
		model.addAttribute("returnUrl", returnUrl);
		
		return "expenses/detail";
	}
	
	/**
	 * 
	 * 登録または更新処理
	 * 
	 * @param form フォーム
	 * @param result バインディング結果
	 * @param model モデル
	 * @param loginUser ログインユーザー
	 * 
	 * @return リダイレクト先URL
	 */
	@PostMapping
	public String saveOrUpdateExpenseByDay (
	        @Valid @ModelAttribute ExpenseForm expenseForm,
	        BindingResult result,
	        Model model,
	        @AuthenticationPrincipal LoginUser loginUser) {

		// フォームから日付を取得
	    LocalDate date = expenseForm.getDate();
	    int year = date.getYear();
	    int month = date.getMonthValue();
	    int day = date.getDayOfMonth();
	    // リダイレクト先URLを生成
	    String returnUrl = String.format("/expenses/day?year=%d&month=%d&day=%d", year, month, day);

	    if (result.hasErrors()) {
	        model.addAttribute("expenseForm", expenseForm);
	        model.addAttribute("postUrl", "/expenses/day");
	        model.addAttribute("returnUrl", returnUrl);
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
		 model.addAttribute("postUrl", "/expenses/day");
		 model.addAttribute("returnUrl", returnUrl);
		 return "expenses/form";
	    }
	

	    return "redirect:" + returnUrl;
	}
	
	/**
	 * 削除処理
	 * 
	 * @param id 支出ID
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param model モデル
	 * @param loginUser ログインユーザー
	 * 
	 * @return リダイレクト先URL
	 */
	@GetMapping("/delete/{id}")
	public String deleteExpenseByDay (
			@PathVariable("id") Long id,
			@RequestParam int year,
			@RequestParam int month,
			@RequestParam int day,
			Model model,
			@AuthenticationPrincipal LoginUser loginUser) {
		
		Long userId = loginUser.getAppUser().getId();
		expenseService.delete(id, userId);
		
		String returnUrl = String.format("/expenses/day?year=%d&month=%d&day=%d", year, month, day);
		
		return "redirect:" + returnUrl;
	}
	
	
}