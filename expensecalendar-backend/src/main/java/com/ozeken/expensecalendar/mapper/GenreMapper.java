package com.ozeken.expensecalendar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ozeken.expensecalendar.entity.Genre;

@Mapper
public interface GenreMapper {

	/**
	 *  すべてのジャンルをリストで取得するSQL。
	 *  
	 * @return ジャンルリスト
	 */
	List<Genre> selectAllGenre ();
}
