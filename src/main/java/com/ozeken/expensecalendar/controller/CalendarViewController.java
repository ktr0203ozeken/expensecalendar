package com.ozeken.expensecalendar.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ozeken.expensecalendar.dto.DailyTotal;
import com.ozeken.expensecalendar.dto.ExpenseWithGenre;
import com.ozeken.expensecalendar.entity.LoginUser;
import com.ozeken.expensecalendar.service.ExpenseService;

import lombok.RequiredArgsConstructor;

/**
 * 家計簿カレンダー表示専用コントローラ
 */

@Controller
@RequestMapping("/expenses/calendar")
@RequiredArgsConstructor
public class CalendarViewController {

    private final ExpenseService expenseService;

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
        //指定された年月の家計簿を取得
        Long userId = loginUser.getAppUser().getId();
        List<ExpenseWithGenre> expenses = expenseService.findByMonth(userId,currentYear, currentMonth);
        //指定された年月の日別合計を取得
        List<DailyTotal> dailyTotals = expenseService.findDailyTotalByMonth(userId, currentYear, currentMonth);
        
        //月初日を取得し,日曜始まりへと変換
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue();
        firstDayOfWeek = (firstDayOfWeek == 7) ? 0 : firstDayOfWeek;
        //その月が何日あるか計算
        int daysInMonth = firstDay.lengthOfMonth();

        model.addAttribute("year", currentYear);
        model.addAttribute("month", currentMonth);
        model.addAttribute("expenses", expenses);
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
}
