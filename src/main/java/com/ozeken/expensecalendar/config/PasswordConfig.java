package com.ozeken.expensecalendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** パスワードエンコーダーの設定 */
@Configuration
public class PasswordConfig {
	
	/** BCrypt を使ったパスワードエンコーダーを提供 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
