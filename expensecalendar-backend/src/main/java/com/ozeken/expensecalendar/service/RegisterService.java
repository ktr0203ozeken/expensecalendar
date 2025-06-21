package com.ozeken.expensecalendar.service;

import org.springframework.stereotype.Service;

import com.ozeken.expensecalendar.entity.AppUser;
import com.ozeken.expensecalendar.form.RegisterForm;
import com.ozeken.expensecalendar.helper.LoginHelper;
import com.ozeken.expensecalendar.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserMapper userMapper;
    private final LoginHelper loginHelper;

    /** ユーザー名の重複チェック*/
    public boolean isDuplicateUsername(String username) {
        return userMapper.selectByUsername(username) != null;
    }

    /** ユーザー登録処理*/
    public void register(RegisterForm form) {
        AppUser user = loginHelper.convertToAppUser(form);
        userMapper.insertUser(user);
    }
}
