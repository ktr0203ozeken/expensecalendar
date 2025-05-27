package com.ozeken.expensecalendar.service.impl;

import java.util.List;

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
    
    // ------- 合計金額取得処理 -----------------------------------------

    @Override
    public Long findTotalAmountByUserId(Long userId) {
		return expenseMapper.selectTotalAmountByUserId(userId);
	}
    @Override
    public Long findMonthlyTotal(Long userId, int year, int month) {
    	return expenseMapper.selectMonthlyTotal(userId, year, month);
	}
    
    // ------- 月別支出取得処理 -----------------------------------------
		
    @Override
    public List<ExpenseWithGenre> findByMonth(Long userId, int year, int month) {
        return expenseMapper.selectByMonth(userId, year, month);
    }
    
    @Override
	public List<ExpenseWithGenre> findWithGenreByDay(Long userId, int year, int month, int day) {
		return expenseMapper.selectWithGenreByDay(userId, year, month, day);
	}
    
    // ------- 日別合計取得処理(リスト) -----------------------------------------

    @Override
    public List<DailyTotal> findDailyTotalByMonth(Long userId, int year, int month) {
        return expenseMapper.selectDailyTotalByMonth(userId, year, month);
    }

    
    // ------- 登録・更新・削除 -----------------------------------------

    @Override
    public void insert(Expense expense) {
        Long currentTotal = expenseMapper.selectTotalAmountByUserId(expense.getUserId());
        if (currentTotal == null) {
			currentTotal = 0L; // 初期値として0を設定
		}
        
        // 足すとオーバーフローするかを事前にチェック
        if (willOverflow(currentTotal, expense.getAmount())) {
            throw new IllegalArgumentException("合計金額が上限を超えるため、登録できません。");
        }

        expenseMapper.insert(expense);
    }
    
    @Override
    public void update(Expense expense) {
    	Long currentTotal = expenseMapper.selectTotalAmountByUserId(expense.getUserId());
    	if (currentTotal == null) {
    		currentTotal = 0L; // 初期値として0を設定
    	}
    	// 更新前の金額を取得
    	Expense existingExpense = expenseMapper.selectByIdAndUserId(expense.getId(), expense.getUserId());
    	if (existingExpense == null) {
    		throw new IllegalArgumentException("更新対象の支出が存在しません。");
    	}
    	Long amountDifference = expense.getAmount() - existingExpense.getAmount();
    	// 足すとオーバーフローするかを事前にチェック
    	if (willOverflow(currentTotal, amountDifference)) {
			throw new IllegalArgumentException("合計金額が上限を超えるため、更新できません。");
		}
        expenseMapper.update(expense);
    }
    /**
     * 合計金額がオーバーフローするかをチェックします。
     * @param currentTotal
     * @param amountToAdd
     * @return オーバーフローする場合はtrue
     */
    private boolean willOverflow(Long currentTotal, Long amountToAdd) {
    	// 現在の合計金額と追加する金額を足して、longの範囲を超えるかをチェック
		return (amountToAdd > 0 && currentTotal > Long.MAX_VALUE - amountToAdd) ||
				// 負の値を足す場合、現在の合計金額がlongの最小値を下回るかをチェック
			   (amountToAdd < 0 && currentTotal < Long.MIN_VALUE - amountToAdd);
	}

    @Override
    public void delete(Long id, Long userId) {
        expenseMapper.deleteByIdAndUserId(id, userId);
    }
}
