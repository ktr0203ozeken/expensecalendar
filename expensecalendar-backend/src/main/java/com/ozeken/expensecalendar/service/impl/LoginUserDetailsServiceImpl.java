package com.ozeken.expensecalendar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ozeken.expensecalendar.entity.AppUser;
import com.ozeken.expensecalendar.entity.LoginUser;
import com.ozeken.expensecalendar.repository.UserMapper;

@Service
public class LoginUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        // ユーザー名からDB検索
        AppUser user = userMapper.selectByUsername(username);
        
        // ユーザーが存在しない場合は例外をスロー
        		if (user == null) {
			throw new UsernameNotFoundException("ユーザーが存在しません: " + username);
        		}
        		
        // LoginUserはUserDetailsを実装を実装したUserクラスを継承している	
        return new LoginUser(user);
    }
}
