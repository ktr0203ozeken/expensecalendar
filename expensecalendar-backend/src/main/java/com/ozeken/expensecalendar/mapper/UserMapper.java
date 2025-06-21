package com.ozeken.expensecalendar.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ozeken.expensecalendar.entity.AppUser;

@Mapper
public interface UserMapper {
	
	/**ユーザー名からユーザーを取得*/
	AppUser selectByUsername(@Param("username") String username);
	
	/**ユーザー名からユーザーを取得*/
	void insertUser(AppUser user);

}
