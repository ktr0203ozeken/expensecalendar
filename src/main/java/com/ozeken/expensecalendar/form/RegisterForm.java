package com.ozeken.expensecalendar.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterForm {
	
    @NotBlank(message = "ユーザー名は必須です。")
    @Size(min = 4, max = 20, message = "ユーザー名は4~20文字で入力してください。")
    @Pattern(
    		regexp = "^[a-zA-Z0-9_.]+$", 
    		message = "ユーザー名は半角英数字、アンダースコア(_)、ドット(.)のみ使用できます。"
    		)
    private String username;

    @NotBlank(message = "パスワードは必須です。")
    @Size(min = 8, max = 30, message = "パスワードは8~30文字で入力してください。")
    @Pattern(
    	    regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,30}$",
    	    message = "パスワードは半角英数字のみで、英字と数字をそれぞれ1文字以上含めてください。"
    	)
    private String password;
}