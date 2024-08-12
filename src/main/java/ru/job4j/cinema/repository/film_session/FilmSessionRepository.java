package ru.job4j.cinema.repository.film_session;

import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

public interface FilmSessionRepository {
    Optional<FilmSession> findById(Integer id);

    Collection<FilmSession> findAll();
}
