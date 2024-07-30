package ru.job4j.cinema.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleFilmSessionServiceTest {
    private FilmRepository filmRepository;
    private HallRepository hallRepository;
    private FilmSessionRepository filmSessionRepository;
    private FilmSessionService filmSessionService;

    @BeforeEach
    void init() {
        filmRepository = mock(Sql2oFilmRepository.class);
        hallRepository = mock(Sql2oHallRepository.class);
        filmSessionRepository = mock(Sql2oFilmSessionRepository.class);
        filmSessionService = new SimpleFilmSessionService(filmSessionRepository, filmRepository, hallRepository);
    }

    @Test
    void whenGetFilmSessionByIdThenGetFilmSessionDto() {
        var time = LocalDateTime.now();
        var hall = new Hall("HallName", 10, 15, "description");
        hall.setId(1);
        var filmSession = new FilmSession(1, 1, time, time.plusHours(1), 450);
        filmSession.setId(1);
        var film = new Film("title", "description", 2000, 1, 18, 120, 1);
        film.setId(1);
        var expectedFilmSessionDto = new FilmSessionDto(filmSession, film, hall);

        when(filmSessionRepository.findById(1)).thenReturn(Optional.of(filmSession));
        when(filmRepository.findById(1)).thenReturn(Optional.of(film));
        when(hallRepository.findById(1)).thenReturn(Optional.of(hall));

        var actualFilmSessionDto = filmSessionService.getFilmSessionById(filmSession.getId()).get();
        assertThat(actualFilmSessionDto).usingRecursiveComparison().isEqualTo(expectedFilmSessionDto);
    }
}