package ru.job4j.cinema.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleFilmSessionServiceTest {
    private FilmService filmService;
    private HallRepository hallRepository;
    private FilmSessionRepository filmSessionRepository;
    private FilmSessionService filmSessionService;

    @BeforeEach
    void init() {
        filmService = mock(SimpleFilmService.class);
        hallRepository = mock(Sql2oHallRepository.class);
        filmSessionRepository = mock(Sql2oFilmSessionRepository.class);
        filmSessionService = new SimpleFilmSessionService(filmSessionRepository, filmService, hallRepository);
    }

    @Test
    void whenGetFilmSessionByIdThenGetFilmSessionDto() {
        var time = LocalDateTime.now();
        var hall = new Hall("HallName", 10, 15, "description");
        hall.setId(1);
        var filmSession = new FilmSession(1, 1, time, time.plusHours(1), 450);
        filmSession.setId(1);
        var filmDto = new FilmDto("title", "description", 2000, 18, 120, "HallName", 1);
        filmDto.setId(1);
        var expectedFilmSessionDto = new FilmSessionDto(filmSession, filmDto, hall);

        when(filmSessionRepository.findById(1)).thenReturn(Optional.of(filmSession));
        when(filmService.getFilmById(1)).thenReturn(Optional.of(filmDto));
        when(hallRepository.findById(1)).thenReturn(Optional.of(hall));

        var actualFilmSessionDto = filmSessionService.getFilmSessionById(filmSession.getId()).get();
        assertThat(actualFilmSessionDto).usingRecursiveComparison().isEqualTo(expectedFilmSessionDto);
    }
}