package com.ozeken.expensecalendar.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String rawPassword = "demo"; // ここに生成したいパスワードを入力
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("元のパスワード: " + rawPassword);
        System.out.println("ハッシュ化されたパスワード: " + encodedPassword);
    }
}