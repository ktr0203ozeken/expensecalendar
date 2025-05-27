package com.ozeken.expensecalendar.service;

import java.util.List;

import com.ozeken.expensecalendar.dto.DailyTotal;
import com.ozeken.expensecalendar.dto.ExpenseWithGenre;
import com.ozeken.expensecalendar.entity.Expense;

/**
 * 支出に関するサービスインターフェース
 * 
 * @author ozeken
 */
public interface ExpenseService {

	
	// ------- 取得処理 (リスト) ------------------------------------------
	
    /**
     * 指定ユーザーIDの全期間の支出リストを取得します（ジャンル名付き）
     *
     * @param userId ユーザーID
     * @return 支出リスト（ジャンル名付き）
     */
    List<ExpenseWithGenre> findAllWithGenre(Long userId);
    
    /**
	 * 指定ユーザーIDと年月日での支出リストを取得します（ジャンル名付き）
	 *
	 * @param userId ユーザーID
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 支出リスト（ジャンル名付き）
	 */
    List<ExpenseWithGenre> findWithGenreByDay(Long userId, int year, int month, int day);

    // ------- 取得処理 (一件) ------------------------------------------

    /**
     * 支出IDとユーザーIDに紐づく、ジャンル名付き支出を1件取得します（詳細画面用）
     *
     * @param id 支出ID
     * @param userId ユーザーID
     * @return 支出情報（ジャンル名付き）
     */
    ExpenseWithGenre findByIdWithGenre(Long id, Long userId);

    /**
     * 支出IDとユーザーIDに該当する支出を一件取得します (insert, update用)
     *
     * @param id 支出ID
     * @param userId ユーザーID
     * @return 支出情報
     */
    Expense findById(Long id, Long userId);
    
    
    // ------- 日別合計取得処理(リスト) ------------------------------------------
    
    /**
     * 指定ユーザーIDと指定月での日別支出合計リストを取得します
     *
     * @param userId ユーザーID
     * @param year 年
     * @param month 月
     * @return 日別支出合計のリスト
     */
    List<DailyTotal> findDailyTotalByMonth(Long userId, int year, int month);
    
    
    // ------- 合計金額取得処理 ------------------------------------------
    
    /**
     * 指定ユーザーIDの全期間の合計金額を取得します（オーバーフロー防止のためのメソッド）
     * 
     * @param userId ユーザーID
     * @return 合計金額
     */
    Long findTotalAmountByUserId(Long userId);
    
    /**
	 * 指定ユーザーIDと年月での支出金額合計を一件取得します
	 *
	 * @param userId ユーザーID
	 * @param year 年（例：2025）
	 * @param month 月（1〜12）
	 * @return 支出金額合計
	 */
    Long findMonthlyTotalByUserId(Long userId, int year, int month);
    
    
    // ------- 登録・更新・削除 --------------------------------------------

    /**
     * 支出を新規登録します（Expense に userId が設定されている前提）
     *
     * @param expense 支出エンティティ
     */
    void insert(Expense expense);

    /**
     * 支出を更新します
     *
     * @param expense 支出エンティティ
     */
    void update(Expense expense);

    /**
     * 支出IDとユーザーIDを指定して支出を削除します
     *
     * @param id 支出ID
     * @param userId ユーザーID
     */
    void deleteByIdAndUserId(Long id, Long userId);
}
