package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.film_session.FilmSessionService;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmSessionControllerTest {
    private FilmSessionService filmSessionService;
    private FilmSessionController controller;

    @BeforeEach
    void init() {
        filmSessionService = mock(FilmSessionService.class);
        controller = new FilmSessionController(filmSessionService);
    }

    @Test
    void whenRequestFilmSessionListPageThenGetListWithFilmSessionDto() {
        var filmSessionDto1 = mock(FilmSessionDto.class);
        var filmSessionDto2 = mock(FilmSessionDto.class);
        var filmSessionDto3 = mock(FilmSessionDto.class);
        var expectedList = List.of(filmSessionDto1, filmSessionDto2, filmSessionDto3);
        when(filmSessionService.findAll()).thenReturn(expectedList);

        var model = new ConcurrentModel();
        var view = controller.getAll(model);
        var actualList = model.getAttribute("filmSessions");

        assertThat(view).isEqualTo("sessions/list");
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void whenRequestFilmSessionByIdThenGetFilmSessionDtoAndBuyPage() {
        var hall = new Hall("test", 1, 1, "test");
        var filmSession = new FilmSession(1, 1, now(), now().plusHours(1), 1);
        var filmDto = new FilmDto("test", "test", 1, 1, 1, "test", 1);
        var expected = new FilmSessionDto(filmSession, filmDto, hall);
        when(filmSessionService.getFilmSessionById(any(Integer.class))).thenReturn(Optional.of(expected));

        var model = new ConcurrentModel();
        var view = controller.getFilmSessionById(model, 1);
        var actual = model.getAttribute("filmSession");

        assertThat(view).isEqualTo("tickets/buy");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenRequestByIdNotSuccessThenGetErrorPageWithMessage() {
        when(filmSessionService.getFilmSessionById(any(Integer.class))).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = controller.getFilmSessionById(model, 1);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo("Выбранный сеанс не найден");
    }
}