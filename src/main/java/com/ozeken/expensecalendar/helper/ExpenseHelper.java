package com.ozeken.expensecalendar.helper;

import com.ozeken.expensecalendar.entity.Expense;
import com.ozeken.expensecalendar.form.ExpenseForm;

/**
 * ExpenseとExpenseFormの変換を行うヘルパークラス
 */
public class ExpenseHelper {

    /**
     *  ExpenseForm → Expense への変換 
     */
    public static Expense convertExpense(ExpenseForm form) {
        Expense expense = new Expense();
        expense.setId(form.getId());
        expense.setDate(form.getDate());
        expense.setCategory(form.getCategory());
        expense.setAmount(form.getAmount());
        expense.setDescription(form.getDescription());
        return expense;
    }

    /** 
     * Expense → ExpenseForm への変換 
     */
    public static ExpenseForm convertExpenseForm(Expense expense) {
        ExpenseForm form = new ExpenseForm();
        form.setId(expense.getId());
        form.setDate(expense.getDate());
        form.setCategory(expense.getCategory());
        form.setAmount(expense.getAmount());
        form.setDescription(expense.getDescription());
        return form;
    }
}