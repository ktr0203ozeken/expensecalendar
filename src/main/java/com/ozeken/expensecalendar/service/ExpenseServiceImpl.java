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
	
	@Override
	public List<Expense> findAll() {
		return expenseMapper.selectAll();
	}
	
	@Override
	public List<Expense> findByMonth(int year, int month) {
		return expenseMapper.selectByMonth(year, month);
	}

	@Override
    // カレンダー表示用：日別に支出をまとめる
    public Map<Integer, List<Expense>> groupByDayOfMonth(int year, int month) {
        List<Expense> expenses = findByMonth(year, month);
        //streamでデータを流し込み、getDateで年月日を取得、
        //getDayOfMonthで日にちのみ(1~31の整数)を取得、Collectors.groupingByでグループ化(mapの形式に変換)
        return expenses.stream().collect(Collectors.groupingBy(exp -> exp.getDate().getDayOfMonth()));
    }

	@Override
	public Expense findById(Long id) {
		return expenseMapper.selectById(id);
	}

	@Override
	public void insert(Expense expense) {
		expenseMapper.insert(expense);

	}

	@Override
	public void update(Expense expense) {
		expenseMapper.update(expense);

	}

	@Override
	public void delete(Long id) {
		expenseMapper.delete(id);

	}

	

}
