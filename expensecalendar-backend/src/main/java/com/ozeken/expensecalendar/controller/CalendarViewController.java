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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ozeken.expensecalendar.dto.DailyTotal;
import com.ozeken.expensecalendar.entity.Expense;
import com.ozeken.expensecalendar.entity.LoginUser;
import com.ozeken.expensecalendar.form.ExpenseForm;
import com.ozeken.expensecalendar.helper.ExpenseHelper;
import com.ozeken.expensecalendar.service.ExpenseService;

import lombok.RequiredArgsConstructor;

/**
 * 家計簿カレンダー表示専用コントローラ
 */

@Controller
@RequestMapping("/expenses/calendar")
@RequiredArgsConstructor
public class CalendarViewController {

	// ---------------- DI -------------------------
    private final ExpenseService expenseService;
    
    //---------------- 定数 ------------------------
    // リダイレクト先URL
    private static final String CALENDAR_BASE_URL = "/expenses/calendar";
    private static final String CALENDAR_URL_WITH_QUERY = CALENDAR_BASE_URL + "?year=%d&month=%d";

    /**
	 * 家計簿カレンダー表示
	 * 
	 * @param year  年
	 * @param month 月
	 * @param model モデル
	 * @param loginUser ログインユーザー
	 * @return カレンダー表示画面
	 */
    @GetMapping
    public String showCalendar(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            Model model,
            @AuthenticationPrincipal LoginUser loginUser) {

    	//nullの場合は,今日の年月を取得
        LocalDate today = LocalDate.now();
        int currentYear = (year != null) ? year : today.getYear();
        int currentMonth = (month != null) ? month : today.getMonthValue();
        
        /**  前月・次月計算用 */
        //指定された年月の初日を取得
        LocalDate firstDay = LocalDate.of(currentYear, currentMonth, 1);
        //指定された年月の前月・次月を取得
        LocalDate prevMonth = firstDay.minusMonths(1);
        LocalDate nextMonth = firstDay.plusMonths(1);

     // 指定された年月の家計簿を取得
        if (loginUser.getAppUser() == null) {
            return "redirect:/login";
        }
        
        Long userId = loginUser.getAppUser().getId();
        
        //指定された年月の月間合計を取得
        Long monthlyTotal = expenseService.findMonthlyTotalByUserId(userId, currentYear, currentMonth);
        //指定された年月の日別合計を取得(日付と金額のリスト)
        List<DailyTotal> dailyTotals = expenseService.findDailyTotalByMonth(userId, currentYear, currentMonth);
        
        //月初日を取得し,日曜始まりへと変換
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue();
        firstDayOfWeek = (firstDayOfWeek == 7) ? 0 : firstDayOfWeek;
        //その月が何日あるか計算
        int daysInMonth = firstDay.lengthOfMonth();

        model.addAttribute("year", currentYear);
        model.addAttribute("month", currentMonth);
        model.addAttribute("monthlyTotal", monthlyTotal);
        model.addAttribute("dailyTotals", dailyTotals);
        model.addAttribute("firstDayOfWeek", firstDayOfWeek);
        model.addAttribute("daysInMonth", daysInMonth);
        // 前月・次月の年月を渡す
        model.addAttribute("prevYear", prevMonth.getYear());
        model.addAttribute("prevMonth", prevMonth.getMonthValue());
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", nextMonth.getMonthValue());

        return "expenses/calendar";
    }
    
    /**
     * 新規登録フォーム表示
     * 
     * @param year 年
     * @param month 月
     * @param model モデル
     * @return 新規登録フォーム
     */
    @GetMapping("/new")
    public String showCreateFormFromCalendar (
    		@RequestParam int year,
    		@RequestParam int month,
    		Model model ) {

    	ExpenseForm form = new ExpenseForm();

    	LocalDate today = LocalDate.now();
    	int nowYear = today.getYear();
    	int nowMonth = today.getMonthValue();
    	int nowDay = today.getDayOfMonth();

    	if (year == nowYear && month == nowMonth) {
    		form.setDate(LocalDate.of(year, month ,nowDay));
    	} else {
    		form.setDate(LocalDate.of(year, month,1));
    	}

    	// リダイレクト先URLを生成（意図的に分割しています。）
    	String returnUrl = String.format(CALENDAR_URL_WITH_QUERY , year, month);
    	String postUrl = String.format(CALENDAR_URL_WITH_QUERY , year, month);

    	model.addAttribute("expenseForm",form);
    	model.addAttribute("postUrl", postUrl);
    	model.addAttribute("returnUrl", returnUrl);

    	return "expenses/form";
    }
    
    /**
	 * 
	 * 登録処理
	 * 
	 * @param form フォーム
	 * @param result バリデーション結果
	 * @param model モデル
	 * @param loginUser ログインユーザー
	 * @return リダイレクト先URL
	 */
	@PostMapping
	public String createExpenseFromCalendar (
	        @Valid @ModelAttribute ExpenseForm expenseForm,
	        BindingResult result,
	        Model model,
	        @AuthenticationPrincipal LoginUser loginUser) {

		// フォームから日付を取得
	    LocalDate date = expenseForm.getDate();
	    int year = date.getYear();
	    int month = date.getMonthValue();
	    
	    // リダイレクト先URLを生成（意図的に分割しています。）
	    String returnUrl = String.format(CALENDAR_URL_WITH_QUERY , year, month);
	    String postUrl = String.format(CALENDAR_URL_WITH_QUERY , year, month);

	    if (result.hasErrors()) {
	    	// バリデーションエラーがあればfフォームに戻す
	        model.addAttribute("expenseForm", expenseForm);
	        model.addAttribute("postUrl", postUrl);
	        model.addAttribute("returnUrl", returnUrl);
	        return "expenses/form";
	        }
	    
	    Long userId = loginUser.getAppUser().getId();
	    expenseForm.setUserId(userId);
	    Expense expense = ExpenseHelper.convertToExpense(expenseForm);

	    try {
	        expenseService.insert(expense);
	 } catch (IllegalArgumentException e) {
		 //オーバーフローが発生した場合のエラーメッセージを設定
		 model.addAttribute("errorMessage", e.getMessage());
		 model.addAttribute("expenseForm", expenseForm);
		 model.addAttribute("postUrl", postUrl);
		 model.addAttribute("returnUrl", returnUrl);
		 return "expenses/form";
	 }
	    return "redirect:" + returnUrl;
	}
	
}
