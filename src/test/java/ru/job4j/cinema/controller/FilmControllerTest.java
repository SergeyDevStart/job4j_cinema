package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.film.FilmService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmControllerTest {
    private FilmService filmService;
    private FilmController controller;

    @BeforeEach
    void init() {
        filmService = mock(FilmService.class);
        controller = new FilmController(filmService);
    }

    @Test
    void whenRequestFilmsListPageThenGetListWithFilmsDto() {
        var filmDto1 = new FilmDto("name1", "desc1", 1, 1, 1, "genre1", 1);
        var filmDto2 = new FilmDto("name2", "desc2", 2, 2, 2, "genre2", 2);
        var expectedList = List.of(filmDto1, filmDto2);
        when(filmService.findAll()).thenReturn(expectedList);

        var model = new ConcurrentModel();
        var view = controller.getAll(model);
        var actualList = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualList).isEqualTo(expectedList);
    }
}