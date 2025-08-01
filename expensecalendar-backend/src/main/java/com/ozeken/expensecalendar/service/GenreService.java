package com.ozeken.expensecalendar.service;

import java.util.List;

import com.ozeken.expensecalendar.entity.Genre;

public interface GenreService {

	/**
	 * すべてのジャンルを"リスト"で取得するメソッド
	 * 主にジャンル別絞り込みで呼び出して使用する。
	 * 
	 * @return ジャンルリスト
	 */
	List<Genre> findAllGenre();
	
	/**
	 * IDでジャンルを"一件"取得するメソッド。
	 * 主にジャンル別絞り込みで呼び出して使用する。
	 * 
	 * @return
	 */
	Genre selectGenreById(Integer id);
	
}
