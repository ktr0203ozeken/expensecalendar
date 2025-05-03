package com.ozeken.expensecalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpensecalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpensecalendarApplication.class, args);
		
		// プロファイル確認用ログ
        String profile = System.getProperty("spring.profiles.active");
        System.out.println(">>> 起動時プロファイル: " + profile);
	}

}
