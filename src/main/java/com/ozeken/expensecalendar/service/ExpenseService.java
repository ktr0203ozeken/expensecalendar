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

	
		// ------- 取得処理 ------------------------------------------
	
    /**
     * ユーザーIDに紐づく、ジャンル名を含んだ支出一覧を取得します（一覧画面用）
     *
     * @param userId ユーザーID
     * @return 支出リスト（ジャンル名付き）
     */
    List<ExpenseWithGenre> findAllWithGenre(Long userId);

    /**
     * 指定された支出IDとユーザーIDに紐づく、ジャンル名付き支出を1件取得します（詳細画面用）
     *
     * @param id 支出ID
     * @param userId ユーザーID
     * @return 支出情報（ジャンル名付き）
     */
    ExpenseWithGenre findByIdWithGenre(Long id, Long userId);

    /**
     * 指定された支出IDとユーザーIDに該当する支出を取得します
     *
     * @param id 支出ID
     * @param userId ユーザーID
     * @return 支出情報
     */
    Expense findById(Long id, Long userId);

    /**
     * 指定年月（一か月分）に該当する支出一覧を取得します（ジャンル名付き）
     *
     * @param userId ユーザーID
     * @param year 年（例：2025）
     * @param month 月（1〜12）
     * @return 支出リスト（ジャンル名付き）
     */
    List<ExpenseWithGenre> findByMonth(Long userId, int year, int month);
    
    /**
	 * 指定年月日（一日分）の支出を取得します（ジャンル名付き）
	 *
	 * @param userId ユーザーID
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 支出リスト（ジャンル名付き）
	 */
    List<ExpenseWithGenre> findWithGenreByDay(Long userId, int year, int month, int day);

    /**
     * 指定月の日別支出合計を取得します
     *
     * @param userId ユーザーID
     * @param year 年
     * @param month 月
     * @return 日別支出合計のリスト
     */
    List<DailyTotal> findDailyTotalByMonth(Long userId, int year, int month);
    
    
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
    void delete(Long id, Long userId);
}
