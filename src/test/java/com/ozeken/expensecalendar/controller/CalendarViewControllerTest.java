package com.ozeken.expensecalendar.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ozeken.expensecalendar.service.ExpenseService;

@WebMvcTest(CalendarViewController.class)
public class CalendarViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Test
    public void testShowCalendar() throws Exception {
        // groupByDayOfMonthの戻り値をダミーにする
        org.mockito.Mockito.when(expenseService.groupByDayOfMonth(2025, 5))
            .thenReturn(Collections.emptyMap());

        mockMvc.perform(get("/expenses/calendar")
                .param("year", "2025")
                .param("month", "5"))
            .andExpect(status().isOk())
            .andExpect(view().name("expenses/calendar"));
    }
}
