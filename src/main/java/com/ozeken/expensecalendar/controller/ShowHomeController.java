package com.ozeken.expensecalendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowHomeController {

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }
}
