package ru.job4j.cinema.service.film_session;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.repository.film_session.FilmSessionRepository;
import ru.job4j.cinema.repository.hall.HallRepository;
import ru.job4j.cinema.service.film.FilmService;
import ru.job4j.cinema.service.film_session.FilmSessionService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final FilmService filmService;
    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository, FilmService filmService,
                                    HallRepository hallRepository) {
        this.filmSessionRepository = filmSessionRepository;
        this.filmService = filmService;
        this.hallRepository = hallRepository;
    }

    @Override
    public Optional<FilmSessionDto> getFilmSessionById(Integer id) {
        var filmSessionOptional = filmSessionRepository.findById(id);
        if (filmSessionOptional.isEmpty()) {
            return Optional.empty();
        }
        var filmSession = filmSessionOptional.get();
        var filmDto = filmService.getFilmById(filmSession.getFilmId()).orElseThrow();
        var hall = hallRepository.findById(filmSession.getHallId()).orElseThrow();
        var filmSessionDto = new FilmSessionDto(filmSession, filmDto, hall);
        return Optional.of(filmSessionDto);
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        return filmSessionRepository.findAll().stream().map(filmSession -> {
            var filmDto = filmService.getFilmById(filmSession.getFilmId()).orElseThrow();
            var hall = hallRepository.findById(filmSession.getHallId()).orElseThrow();
            return new FilmSessionDto(filmSession, filmDto, hall);
        }).collect(Collectors.toList());
    }
}
