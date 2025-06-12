package com.ozeken.expensecalendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

	/** ログイン画面の表示 */
	@GetMapping
	public String showLogin() {
		return "login";
	}
}
