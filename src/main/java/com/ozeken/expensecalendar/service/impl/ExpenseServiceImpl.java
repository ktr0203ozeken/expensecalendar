package com.ozeken.expensecalendar.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozeken.expensecalendar.dto.DailyTotal;
import com.ozeken.expensecalendar.dto.ExpenseWithGenre;
import com.ozeken.expensecalendar.entity.Expense;
import com.ozeken.expensecalendar.repository.ExpenseMapper;
import com.ozeken.expensecalendar.service.ExpenseService;

import lombok.RequiredArgsConstructor;

/**
 * 支出に関するサービス実装クラス
 * 
 * @author ozeken
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    // DI対象（データアクセス層）
    private final ExpenseMapper expenseMapper;

    // ------- 取得処理 -----------------------------------------------

    @Override
    public List<ExpenseWithGenre> findAllWithGenre(Long userId) {
        return expenseMapper.selectWithGenreByUserId(userId);
    }

    @Override
    public ExpenseWithGenre findByIdWithGenre(Long id, Long userId) {
        return expenseMapper.selectWithGenreByIdAndUserId(id, userId);
    }

    @Override
    public Expense findById(Long id, Long userId) {
        return expenseMapper.selectByIdAndUserId(id, userId);
    }

    @Override
    public List<ExpenseWithGenre> findByMonth(Long userId, int year, int month) {
        return expenseMapper.selectByMonth(userId, year, month);
    }

    @Override
    public List<DailyTotal> findDailyTotalByMonth(Long userId, int year, int month) {
        return expenseMapper.selectDailyTotalByMonth(userId, year, month);
    }

    @Override
    public Map<Integer, List<ExpenseWithGenre>> groupByDayOfMonth(Long userId, int year, int month) {
        List<ExpenseWithGenre> expenses = findByMonth(userId, year, month);
        // 日にちごとに支出をグループ化（1〜31をキーにグループ化）
        return expenses.stream()
                       .collect(Collectors.groupingBy(exp -> exp.getDate().getDayOfMonth()));
    }

    
    // ------- 登録・更新・削除 -----------------------------------------

    @Override
    public void insert(Expense expense) {
        expenseMapper.insert(expense);
    }

    @Override
    public void update(Expense expense) {
        expenseMapper.update(expense);
    }

    @Override
    public void delete(Long id, Long userId) {
        expenseMapper.deleteByIdAndUserId(id, userId);
    }
}
