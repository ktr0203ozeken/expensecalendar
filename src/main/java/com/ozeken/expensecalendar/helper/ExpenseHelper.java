package com.ozeken.expensecalendar.helper;

import com.ozeken.expensecalendar.dto.ExpenseWithGenre;
import com.ozeken.expensecalendar.entity.Expense;
import com.ozeken.expensecalendar.form.ExpenseForm;

/**
 * Expense と ExpenseForm の相互変換を行うヘルパークラス
 */
public class ExpenseHelper {

    /**
     * ExpenseForm → Expense への変換（登録・更新用）
     */
    public static Expense convertToExpense(ExpenseForm form) {
        Expense expense = new Expense();
        expense.setId(form.getId());
        expense.setUserId(form.getUserId());
        expense.setDate(form.getDate());
        expense.setGenreId(form.getGenreId());
        expense.setAmount(form.getAmount());
        expense.setDescription(form.getDescription());
        return expense;
    }

    /**
     * Expense → ExpenseForm への変換（編集フォーム表示用）
     */
    public static ExpenseForm convertToExpenseForm(Expense expense) {
        ExpenseForm form = new ExpenseForm();
        form.setId(expense.getId());
        form.setUserId(expense.getUserId());
        form.setDate(expense.getDate());
        form.setGenreId(expense.getGenreId());
        form.setAmount(expense.getAmount());
        form.setDescription(expense.getDescription());
        return form;
    }

    /**
     * ExpenseWithGenre → ExpenseForm への変換（JOIN結果からフォーム表示用）
     */
    public static ExpenseForm convertToExpenseForm(ExpenseWithGenre dto) {
        ExpenseForm form = new ExpenseForm();
        form.setId(dto.getId());
        form.setUserId(dto.getUserId());
        form.setDate(dto.getDate());
        form.setGenreId(dto.getGenreId());
        form.setAmount(dto.getAmount());
        form.setDescription(dto.getDescription());
        return form;
    }
}
