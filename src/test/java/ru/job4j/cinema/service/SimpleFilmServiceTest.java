package ru.job4j.cinema.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleFilmServiceTest {
    private FilmRepository filmRepository;
    private GenreRepository genreRepository;
    private FilmService filmService;

    @BeforeEach
    void init() {
        filmRepository = mock(Sql2oFilmRepository.class);
        genreRepository = mock(Sql2oGenreRepository.class);
        filmService = new SimpleFilmService(filmRepository, genreRepository);
    }

    @Test
    void whenGetFilmByIdThenGetFilmDto() {
        var genre = new Genre("GenreTitle");
        genre.setId(1);
        var film = new Film("title", "description", 2000, 1, 18, 120, 1);
        film.setId(1);
        var expectedFilmDto = new FilmDto("title", "description", 2000, 18, 120, "GenreTitle", 1);
        expectedFilmDto.setId(1);

        when(filmRepository.findById(1)).thenReturn(Optional.of(film));
        when(genreRepository.findById(1)).thenReturn(Optional.of(genre));

        var actualFilmDto = filmService.getFilmById(1).get();
        assertThat(actualFilmDto).usingRecursiveComparison().isEqualTo(expectedFilmDto);
    }
}