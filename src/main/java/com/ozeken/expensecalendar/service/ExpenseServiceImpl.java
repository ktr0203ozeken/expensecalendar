package com.ozeken.expensecalendar.service;

import java.util.List;

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
