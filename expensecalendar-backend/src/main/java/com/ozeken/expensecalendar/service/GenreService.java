package com.ozeken.expensecalendar.service;

import java.util.List;

import com.ozeken.expensecalendar.entity.Genre;

public interface GenreService {

	/**
	 * すべてのジャンルをリストで取得するメソッド
	 * 
	 * @return ジャンルリスト
	 */
	List<Genre> findAllGenre();
	
}
