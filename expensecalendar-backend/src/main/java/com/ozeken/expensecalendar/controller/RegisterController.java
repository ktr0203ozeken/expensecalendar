package com.ozeken.expensecalendar.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ozeken.expensecalendar.form.RegisterForm;
import com.ozeken.expensecalendar.service.RegisterService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

	//DI
    private final RegisterService registerService;

    /** ユーザー登録画面の表示*/
    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }
    
    /** 登録処理*/
    @PostMapping
    public String register(@ModelAttribute("registerForm") @Valid RegisterForm form,
                           BindingResult result,
                           Model model) {

        if (registerService.isDuplicateUsername(form.getUsername())) {
            result.rejectValue("username", "duplicate", "このユーザー名はすでに使用されています。");
        }

        if (result.hasErrors()) {
            model.addAttribute("registerForm", form);
            return "register";
        }

        registerService.register(form);
        return "redirect:/login";
    }
}