package com.ozeken.expensecalendar.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ozeken.expensecalendar.dto.DailyTotal;
import com.ozeken.expensecalendar.dto.ExpenseWithGenre;
import com.ozeken.expensecalendar.entity.Expense;

/**
 * 支出に関するデータアベースアクセスインターフェース
 * 
 * @author ozeken
 */
@Mapper
public interface ExpenseMapper {
	
	
	// ------- 取得処理 (リスト) ------------------------------------------------------
	
    /**
     * 全期間の支出一覧を取得（ジャンル名付き）
     *
     * @param userId ユーザーID
     * @return 支出リスト（ジャンル名付き）
     */
    List<ExpenseWithGenre> selectWithGenreByUserId(@Param("userId") Long userId);
    
    /**
     * 指定年月日での支出一覧を取得（ジャンル名付き）
     *
     * @param userId ユーザーID
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 支出リスト（ジャンル名付き）
     */
    List<ExpenseWithGenre> selectWithGenreByUserIdAndDay(
        @Param("userId") Long userId,
        @Param("year") int year,
        @Param("month") int month,
        @Param("day") int day);

    
    // ------- 取得処理 (一件) ------------------------------------------------------

    /**
     * 支出IDとユーザーIDをもとに、ジャンル名を含む支出を1件取得
     *
     * @param id 支出ID
     * @param userId ユーザーID
     * @return 支出情報（ジャンル名付き）
     */
    ExpenseWithGenre selectWithGenreByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 支出IDとユーザーIDをもとに、支出を1件取得
     *
     * @param id 支出ID
     * @param userId ユーザーID
     * @return 支出情報 （ジャンル名なし）
     */
    Expense selectByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    
    // ------- 合計金額取得処理 ------------------------------------------------------

    /**
     * 合計金額を取得(全期間)
     * 
     * @param userId ユーザーID
     */
    Long selectTotalAmountByUserId(@Param("userId") Long userId);
    
    /**
	 * 指定月の支出合計を取得
	 *
	 * @param userId ユーザーID
	 * @param year 年
	 * @param month 月
	 * @return 支出合計金額
	 */
    Long selectMonthlyTotalByUserId (
			@Param("userId") Long userId, 
			@Param("year") int year, 
			@Param("month") int month);
    
    
    // ------- 日別合計取得処理(リスト) -----------------------------------------
    
    /**
     * 指定月の日別支出合計を取得（1日ごとの合計）
     *
     * @param userId ユーザーID
     * @param year 年
     * @param month 月
     * @return 日別支出合計リスト
     */
    List<DailyTotal> selectDailyTotalByUserIdAndMonth(
    		@Param("userId") Long userId, 
    		@Param("year") int year, 
    		@Param("month") int month);

    
    // ------- 登録・更新・削除 ------------------------------------------------------
    
    /**
     * 支出を新規登録
     *
     * @param expense 支出エンティティ
     */
    void insert(Expense expense);

    /**
     * 支出を更新（userIdによるバリデーションはサービス層で実施）
     *
     * @param expense 支出エンティティ
     */
    void update(Expense expense);

    /**
     * 支出IDとユーザーIDを指定して削除（不正操作防止のため）
     *
     * @param id 支出ID
     * @param userId ユーザーID
     */
    void deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}
