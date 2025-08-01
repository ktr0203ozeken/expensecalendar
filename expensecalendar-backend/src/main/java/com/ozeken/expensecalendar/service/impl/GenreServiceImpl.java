package com.ozeken.expensecalendar.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ozeken.expensecalendar.entity.Genre;
import com.ozeken.expensecalendar.mapper.GenreMapper;
import com.ozeken.expensecalendar.service.GenreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
	
	// DI
	private final GenreMapper genreMapper;

	@Override
	public List<Genre> findAllGenre() {
		return genreMapper.selectAllGenre();
	}

	@Override
	public Genre selectGenreById(Integer id) {
		return genreMapper.selectGenreById(id);
	}

}
