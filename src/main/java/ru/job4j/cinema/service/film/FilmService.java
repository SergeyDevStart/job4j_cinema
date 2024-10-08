package ru.job4j.cinema.service.film;

import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmService {
    Optional<FilmDto> getFilmById(Integer id);

    Collection<FilmDto> findAll();
}
