package com.ozeken.expensecalendar.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozeken.expensecalendar.entity.Expense;
import com.ozeken.expensecalendar.repository.ExpenseMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

	/**DI*/
	private final ExpenseMapper expenseMapper;

	// ログインユーザーの全件取得
	@Override
	public List<Expense> findAll(Long userId) {
		return expenseMapper.selectAll(userId);
	}

	// 月別取得 （ユーザー指定）
	@Override
	public List<Expense> findByMonth(Long userId, int year, int month) {
		return expenseMapper.selectByMonth(userId, year, month);
	}

	@Override
    // カレンダー表示用：日別に支出をまとめる
    public Map<Integer, List<Expense>> groupByDayOfMonth(Long userId, int year, int month) {
        List<Expense> expenses = findByMonth(userId, year, month);
        //streamでデータを流し込み、getDateで年月日を取得、
        //getDayOfMonthで日にちのみ(1~31の整数)を取得、Collectors.groupingByでグループ化(mapの形式に変換)
        return expenses.stream().collect(Collectors.groupingBy(exp -> exp.getDate().getDayOfMonth()));
    }

	// IDとuserIdで一件取得（他人のデータ保護）
	@Override
	public Expense findById(Long id, Long userId) {
		return expenseMapper.selectByIdAndUserId(id, userId);
	}

	@Override
	public void insert(Expense expense) {
		expenseMapper.insert(expense);
	}

	@Override
	public void update(Expense expense) {
		expenseMapper.update(expense);
	}

	// IDとuserIdで削除（他人のデータ削除を防止）
	@Override
	public void delete(Long id, Long userId) {
		expenseMapper.deleteByIdAndUserId(id, userId);
	}
}
