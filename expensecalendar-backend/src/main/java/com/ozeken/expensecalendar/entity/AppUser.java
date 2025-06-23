package com.ozeken.expensecalendar.entity;

import lombok.Data;

@Data
public class AppUser {

	// ID
    private Long id;

    // ユーザー名
    private String username;
    
    // パスワード
    private String password;
    
    // 権限
    private String role; // "USER" や "ADMIN"
}