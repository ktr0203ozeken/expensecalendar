package com.ozeken.expensecalendar.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.ozeken.expensecalendar.entity.AppUser;
import com.ozeken.expensecalendar.entity.LoginUser;
import com.ozeken.expensecalendar.service.ExpenseService;

@WebMvcTest(CalendarViewController.class)
@ActiveProfiles("test")
class CalendarViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Test
    @WithMockUser // 認証を通すために必要
    void testShowCalendar() throws Exception {
    	AppUser mockUser = new AppUser();
    	mockUser.setId(1L);
    	mockUser.setUsername("testuser");
    	mockUser.setPassword("password");
    	mockUser.setRole("USER");
    	LoginUser loginUser = new LoginUser(mockUser);
    	
    	UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(loginUser, null, Collections.emptyList());
    	SecurityContextHolder.getContext().setAuthentication(authentication);

        // 認証されたユーザーのIDをモック
        Mockito.when(expenseService.findByMonth(1L, 2025, 5)).thenReturn(List.of());
        Mockito.when(expenseService.findDailyTotalByMonth(1L, 2025, 5)).thenReturn(List.of());

        mockMvc.perform(get("/expenses/calendar")
                .param("year", "2025")
                .param("month", "5"))
            .andExpect(status().isOk())
            .andExpect(view().name("expenses/calendar"))
            .andExpect(model().attributeExists("expenses", "dailyTotals"));
    }
}
