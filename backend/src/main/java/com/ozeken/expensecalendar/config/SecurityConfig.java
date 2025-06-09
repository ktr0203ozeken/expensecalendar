package com.ozeken.expensecalendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	// DI対象が存在すれば、DIして保存する
	private final UserDetailsService userDetailsService;
		

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            		// "/"、"/login"、"/register"、"/css/**"は認証なしでアクセス可能
                .requestMatchers("/", "/login", "/register", "/css/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                //その他のリクエストは認証が必要
                .anyRequest().authenticated()
            )
            
            .userDetailsService(userDetailsService)
            
            .formLogin(form -> form
                .loginPage("/login")
                //すべてのユーザーに"/expenses"にリダイレクト
                .defaultSuccessUrl("/expenses/calendar", true)
                //ログイン失敗時のリダイレクト先
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .rememberMe(remember -> remember
            	    .key("uniqueAndSecret")
            	    // remember-meトークンの有効期限を30日間に設定
            	    .tokenValiditySeconds(60 * 60 * 24 * 30)
            	    .rememberMeCookieName("remember-me")
            	    .rememberMeParameter("remember-me")
			)
    		//ログアウト後、クッキー、トークンを削除して"/"にリダイレクト
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID","remember-me")
                .permitAll());

        return http.build();
    }
}
