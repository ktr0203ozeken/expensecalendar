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
     * 全期間の支出一覧をページネーションで取得（ジャンル名付き）
     * 
     * @param userId ユーザーID
     * @param limit  取得する最大件数（1ページあたりの件数）
     * @param offset 取得開始位置（例：0なら先頭から、10なら11件目から）
     * @return 支出リスト（ジャンル名付き、ページネーション対応）
     */
    List<ExpenseWithGenre> findPagedExpenses(Long userId, int limit, int offset);
    
    /**
     * ページ番号とページサイズから支出一覧を取得（ページ番号は1から開始）
     * 
     * @param userId ユーザーID
     * @param page  現在のページ番号（1から始まる）
     * @param size 1ページあたりの表示件数
     * @return 支出リスト（ジャンル名付き、ページネーション対応）
     * 
     */
    List<ExpenseWithGenre> findPagedExpensesByPage(Long userId, int page, int size);
    

     /**
     * 指定ユーザーIDとジャンルIDでの支出一覧をページネーションで取得します（ジャンル名付き）
     * ジャンル名絞り込み機能用。
     * 
     * @param userId ユーザーID
     * @param genreId ジャンルID
     * @param limit  取得する最大件数（1ページあたりの件数）
     * @param offset 取得開始位置（例：0なら先頭から、10なら11件目から）
     * @return 支出リスト (ジャンル名付き、ページネーション対応)
     */
    List<ExpenseWithGenre> findExpensesByUserIdAndGenreId(
        Long userId,
        Integer genreId,
        int limit,
        int offset
        );

    /**
     * ページ番号とページサイズから支出一覧を取得（ページ番号は1から開始）
     * 
     * @param userId ユーザーID
     * @param ganreId ジャンルID
     * @param page  現在のページ番号（1から始まる）
     * @param size 1ページあたりの表示件数
     * @return 支出リスト（ジャンル名付き、ページネーション対応）
     * 
     */
    List<ExpenseWithGenre> findPagedExpensesByUserIdAndGenreId(Long userId, Integer genreId, int page, int size);

    
    /**
	 * 指定ユーザーIDと年月日での支出一覧をページネーションで取得します（ジャンル名付き）
	 *
	 * @param userId ユーザーID
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param limit 取得する最大件数（1ページあたりの件数）
	 * @param offset 取得開始位置（例：0なら先頭から、10なら11件目から）
	 * @return 支出リスト（ジャンル名付き）
	 */
    List<ExpenseWithGenre> findPagedExpensesByDay(Long userId, int year, int month, int day, int limit, int offset);
    
    /**
     * ページ番号とページサイズから支出一覧を取得（ページ番号は1から開始）
     * 
     * @param userId ユーザーID
     * @param year 年
	 * @param month 月
	 * @param day 日
     * @param page  現在のページ番号（1から始まる）
     * @param size 1ページあたりの表示件数
     * @return 支出リスト（ジャンル名付き、ページネーション対応）
     * 
     */
    List<ExpenseWithGenre> findPagedExpensesByDayAndPage(Long userId, int year, int month, int day, int page, int size);

    
    
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
