package com.ozeken.expensecalendar.helper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ozeken.expensecalendar.entity.AppUser;
import com.ozeken.expensecalendar.form.RegisterForm;

import lombok.RequiredArgsConstructor;

/**
 * LoginHelperクラス
 */

@Component
@RequiredArgsConstructor
public class LoginHelper {
	
	
	//DI
	private final PasswordEncoder passwordEncoder;
	
	
	/**AppUserとRegisterFormの変換を行うメソッド*/
	public RegisterForm convertToLoginForm(AppUser user) {
		RegisterForm form = new RegisterForm();
		form.setUsername(user.getUsername());
		form.setPassword(user.getPassword());
		return form;
	}
	
	/**appUserをRegisterFormに変換を行うメソッド*/
	public AppUser convertToAppUser(RegisterForm form) {
		AppUser user = new AppUser();
		user.setUsername(form.getUsername());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setRole("USER");
		return user;
	}

}
