package com.ozeken.expensecalendar.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * 表示専用のDTOクラス	
 */
@Data
public class ExpenseWithGenre {
	
	// ID
    private Long id;
    
    // ユーザーID (外部キー)
    private Long userId;

    // 日付
    private LocalDate date;
    
    // ジャンル (外部キー)
    private Integer genreId;
    
    // ジャンル名
    private String genreName;
    
    // 金額
    private Long amount;
    
    // 説明
    private String description;
    
}