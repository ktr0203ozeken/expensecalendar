package com.ozeken.expensecalendar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.ozeken.expensecalendar.entity.Genre;

@Mapper
public interface GenreMapper {

	/**
	 *  すべてのジャンルをリストで取得するメソッド。
	 *  
	 * @return ジャンルリスト
	 */
	List<Genre> selectAllGenre();
	
	/**
	 * IDで一件ジャンルを取得するメソッド。
	 * 
	 * @return
	 */
	Genre selectGenreById(@RequestParam("id") Integer id);
}
