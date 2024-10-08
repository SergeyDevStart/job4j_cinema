package ru.job4j.cinema.service.film_session;

import ru.job4j.cinema.dto.FilmSessionDto;

import java.util.Collection;
import java.util.Optional;

public interface FilmSessionService {
    Optional<FilmSessionDto> getFilmSessionById(Integer id);

    Collection<FilmSessionDto> findAll();
}
